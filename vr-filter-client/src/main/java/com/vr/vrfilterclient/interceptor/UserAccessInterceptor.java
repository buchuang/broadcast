package com.vr.vrfilterclient.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.vr.commonutils.exception.ThisException;
import com.vr.commonutils.utils.ErrorEnum;
import com.vr.commonutils.utils.JsonUtil2;
import com.vr.commonutils.utils.R;
import com.vr.userserviceapi.entity.UserInfoDto;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public abstract class UserAccessInterceptor extends HandlerInterceptorAdapter {
    private static Cache<String, UserInfoDto> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3, TimeUnit.MINUTES).build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                boolean b = accessLimit.needLogin();
                if (!b) {
                    return true;
                }
            }
        }
        String token = null;
        token = getToken(request, token);
        if (StringUtils.isBlank(token)) {
            rander(response);
            return false;
        }
        UserInfoDto userInfoDto = cache.getIfPresent(token);
        if (userInfoDto == null) {
            userInfoDto = getUserInfoFromAuthentication(token);
            if (userInfoDto == null) {
                rander(response);
                return false;
            } else {
                cache.put("token", userInfoDto);
            }
        }
        flushLoginTime(token);
        login(request, response, userInfoDto);
        return true;
    }

    private void flushLoginTime(String token) {
        String url = "http://" + getServiceNam() + "/server/flushLoginTime";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("token", token);
        try {
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                ThisException.exception(ErrorEnum.REQUEST_FAILED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getServiceNam();

    public abstract void login(HttpServletRequest request, HttpServletResponse response, UserInfoDto userInfoDto);

    private UserInfoDto getUserInfoFromAuthentication(String token) {
        String url = "http://" + getServiceNam() + "/server/authentication";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                ThisException.exception(ErrorEnum.REQUEST_FAILED);
            }
            inputStream = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuilder builder = new StringBuilder();
            while ((len = inputStream.read(bytes)) > 0) {
                builder.append(new String(bytes, 0, len));
            }
            if (StringUtils.isBlank(builder.toString())) {
                return null;
            }
            UserInfoDto o = (UserInfoDto) JsonUtil2.fromJson(builder.toString(), UserInfoDto.class);
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private void rander(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            String json = JsonUtil2.toJson(R.error(ErrorEnum.TOKEN_VAILD));
            outputStream.write(json.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getTokenFromCookie(Cookie[] cookies, String token) {
        if (!StringUtils.isBlank(token)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("token".equals(name)) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    private String getToken(HttpServletRequest request, String token) throws IOException {
        token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            token = getTokenFromCookie(cookies, token);
        }
        return token;
    }
}
