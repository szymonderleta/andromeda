package pl.derleta.authorization.domain.builder;

import pl.derleta.authorization.domain.model.JwtToken;
import pl.derleta.authorization.domain.model.User;

import java.sql.Timestamp;

/**
 * A builder interface for constructing instances of {@link JwtToken}.
 * This interface provides methods to set the properties of a JWT token, enabling
 * the creation of a fully initialized {@link JwtToken} object through method chaining.
 */
public interface JwtTokenBuilder {

    JwtTokenBuilder tokenId(long tokenId);

    JwtTokenBuilder user(User user);

    JwtTokenBuilder token(String token);

    JwtTokenBuilder expirationDate(Timestamp expirationDate);

    JwtToken build();

}
