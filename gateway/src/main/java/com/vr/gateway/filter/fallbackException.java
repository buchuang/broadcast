package com.vr.gateway.filter;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class fallbackException implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {

                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {

                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {

                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            /**
             * 当 springms-provider-user 微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
             * <p>
             * 但对于复杂一点的微服务，我们这里就得好好琢磨该怎么友好提示给用户了；
             * <p>
             * 如果请求用户服务失败，返回什么信息给消费者客户端
             *
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                JSONObject r = new JSONObject();
                //System.out.println(cause.getCause().getMessage().equals("java.net.SocketTimeoutException: Read timed out"));
                if(cause!=null &&cause.getCause()!=null){
                    if(cause.getCause().getMessage().equals("java.net.SocketTimeoutException: Read timed out")){
                        try {
                            r.put("code", 10000);
                            r.put("msg", "请求超时，请重新再试");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return new ByteArrayInputStream(r.toString().getBytes("UTF-8"));
                    }
                }
                try {
                    r.put("code", 9999);
                    r.put("msg", "系统错误，请求失败");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return new ByteArrayInputStream(r.toString().getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和body中的内容编码一致，否则容易乱码
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }


        };
    }
}
