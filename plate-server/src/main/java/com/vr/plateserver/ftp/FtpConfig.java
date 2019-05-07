package com.vr.plateserver.ftp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * fileName:FtpConfig
 * description:
 * author:hcq
 * createTime:2019-03-15 15:04
 */

@Component
@ConfigurationProperties(prefix = "ftp")
@PropertySource("classpath:ftp.properties")
public class FtpConfig {
    private String Host;
    private int Port;
    private String UserName;
    private String PassWord;
    private String workDir;
    private String encoding;
    private String root;
    private int MaxTotal;
    private int MinIdel;
    private int MaxIdle;
    private int MaxWaitMillis;

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getMaxTotal() {
        return MaxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        MaxTotal = maxTotal;
    }

    public int getMinIdel() {
        return MinIdel;
    }

    public void setMinIdel(int minIdel) {
        MinIdel = minIdel;
    }

    public int getMaxIdle() {
        return MaxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        MaxIdle = maxIdle;
    }

    public int getMaxWaitMillis() {
        return MaxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        MaxWaitMillis = maxWaitMillis;
    }
}
