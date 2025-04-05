package pl.derleta.nebula.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class JwtTokenResponse implements ResponseWithCookieHeaders {

    @JsonIgnore
    private String cookieHeaders;

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;

    @Override
    public void setCookieHeaders(String headers) {
        cookieHeaders = headers;
    }

    @Override
    public String getCookieHeaders() {
        return cookieHeaders;
    }

}
