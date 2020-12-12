package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {

    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;
    private String decryptedPassword;

    /*public Credentials(Integer credentialid, String url, String username,  String password, String key,Integer userid, String decryptedPassword) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.password = password;
        this.key = key;
        this.userid = userid;
        this.decryptedPassword = decryptedPassword;
    }*/

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}