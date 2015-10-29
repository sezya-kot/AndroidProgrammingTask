package com.cat.serge.androidprogrammingtask.model;

/**
 * Credentials
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class Credentials{
    protected String login;
    protected String password;
    protected String deviceType;
    protected String deviceId;

    @SuppressWarnings("unused")
    public String getLogin() {
        return login;
    }

    @SuppressWarnings("unused")
    public void setLogin(final String login) {
        this.login = login;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void setPassword(final String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public String getDeviceType() {
        return deviceType;
    }

    @SuppressWarnings("unused")
    public void setDeviceType(final String deviceType) {
        this.deviceType = deviceType;
    }

    @SuppressWarnings("unused")
    public String getDeviceId() {
        return deviceId;
    }

    @SuppressWarnings("unused")
    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    public String toRequest() {
        return "login="+login+"&password="+password+"&deviceType=\""+deviceType+"\"&deviceId="+deviceId;
    }


}
