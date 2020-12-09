package com.udacity.jwdnd.course1.cloudstorage.model.forms;

public class CredentialModalForm {
    String url;
    String username;
    String password;
    String credentialid;
    String decryptedpassword;

    public CredentialModalForm(String url, String username, String password, String credentialid, String decryptedpassword) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.credentialid = credentialid;
        this.decryptedpassword = decryptedpassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(String credentialid) {
        this.credentialid = credentialid;
    }
}
