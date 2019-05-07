package com.vr.userserver;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServerApplicationTests {
    @Autowired
    StringEncryptor encryptor;
    @Test
    public void contextLoads() {
        String password = encryptor.encrypt("94450");
        System.out.println(password+"----------------");
        Assert.assertTrue(password.length() > 0);
    }

}
