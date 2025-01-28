package pl.derleta.nebula.controller.response;

public interface ResponseWithCookieHeaders extends Response {

    void setCookieHeaders(String headers);

    String getCookieHeaders();

}
