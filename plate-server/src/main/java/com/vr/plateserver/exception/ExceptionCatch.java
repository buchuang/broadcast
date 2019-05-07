package com.vr.plateserver.exception;

import com.vr.commonutils.exception.MyException;
import com.vr.commonutils.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionCatch {

    @ExceptionHandler(MyException.class)
    public R SelfException(HttpServletRequest request, MyException ex) throws Exception{
        return R.error(ex.getCode(),ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public R unKnownExcepton(HttpServletRequest request,Exception ex){
        ex.printStackTrace();
        return R.error();
    }
}
