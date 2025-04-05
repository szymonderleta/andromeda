package pl.derleta.nebula.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.derleta.nebula.config.JwtTokenUtil;
import pl.derleta.nebula.domain.rest.Role;
import pl.derleta.nebula.domain.token.TokenData;
import pl.derleta.nebula.service.TokenProvider;

import java.util.Set;

/**
 * An implementation of the {@link TokenProvider} interface, responsible for handling
 * operations related to JWT tokens, such as extracting information from tokens,
 * validating tokens, and managing token-related data.
 * <p>
 * This class utilizes a {@link JwtTokenUtil} instance to perform the underlying
 * operations for interacting with JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Retrieves the token data associated with the provided JWT token.
     *
     * @param token the JWT token from which to extract the token data
     * @return a {@code TokenData} object containing details such as validity, user ID, email, token, and roles
     */
    @Override
    public TokenData getTokenData(String token) {
        return jwtTokenUtil.getTokenData(token);
    }

    /**
     * Validates the provided JWT token for its validity.
     *
     * @param token the JWT token to be checked
     * @return true if the token is not expired; false otherwise
     */
    @Override
    public boolean isValid(String token) {
        return !jwtTokenUtil.isTokenExpired(token);
    }

    /**
     * Validates whether the given JWT token is not expired and corresponds to the provided user ID.
     *
     * @param jwtToken the JWT token to validate
     * @param userId the user ID to check against the ID contained in the token
     * @return true if the token is not expired and the user ID matches the ID in the token; false otherwise
     */
    public boolean isValid(String jwtToken, long userId) {
        if (jwtTokenUtil.isTokenExpired(jwtToken)) return false;
        long userIdInToken = getUserId(jwtToken);
        return userId == userIdInToken;
    }

    /**
     * Extracts the user ID from the provided JWT token.
     *
     * @param token the JWT token from which to extract the user ID
     * @return the extracted user ID as a Long
     */
    @Override
    public Long getUserId(String token) {
        return jwtTokenUtil.getUserId(token);
    }

    /**
     * Retrieves the roles associated with the given JWT token.
     *
     * @param token the JWT token from which to extract the roles
     * @return a {@code Set} of {@code Role} objects representing the roles available in the token
     */
    @Override
    public Set<Role> getRoles(String token) {
        return jwtTokenUtil.getRoles(token);
    }

    /**
     * Retrieves the email address from the given JWT token.
     *
     * @param token the JWT token from which to extract the email address
     * @return the extracted email address as a String
     */
    @Override
    public String getEmail(String token) {
        return jwtTokenUtil.getEmail(token);
    }

}
