package pl.derleta.authorization.domain.builder.impl;

import pl.derleta.authorization.domain.builder.JwtTokenBuilder;
import pl.derleta.authorization.domain.model.JwtToken;
import pl.derleta.authorization.domain.model.User;

import java.sql.Timestamp;

/**
 * Implementation of the {@link JwtTokenBuilder} interface.
 * This class provides a concrete implementation for building {@link JwtToken} objects.
 * It supports the fluent builder pattern, enabling the step-by-step creation of a {@link JwtToken} instance
 * by setting its properties such as the token ID, user, token, and expiration date.
 * <p>
 * This builder implementation ensures that a fully initialized instance of {@link JwtToken} can be created
 * by chaining method calls to set desired properties before invoking the build method.
 */
public class JwtTokenBuilderImpl implements JwtTokenBuilder {

    private long tokenId;
    private User user;
    private String token;
    private Timestamp expirationDate;

    @Override
    public JwtTokenBuilder tokenId(long tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    @Override
    public JwtTokenBuilder user(User user) {
        this.user = user;
        return this;
    }

    @Override
    public JwtTokenBuilder token(String token) {
        this.token = token;
        return this;
    }

    @Override
    public JwtTokenBuilder expirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @Override
    public JwtToken build() {
        return new JwtToken(tokenId, token, user, expirationDate);
    }

}
