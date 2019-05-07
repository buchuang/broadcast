package com.vr.commonutils.exception;

import com.vr.commonutils.utils.ErrorEnum;

public class ThisException {

    public static void exception(ErrorEnum errorEnum){
        throw new MyException(errorEnum);
    }
}
