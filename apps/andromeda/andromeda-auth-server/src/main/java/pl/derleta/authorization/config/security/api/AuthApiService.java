package pl.derleta.authorization.config.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing authentication-related operations.
 * This service interacts with the {@code AuthApiRepository} to perform
 * operations such as saving tokens and validating them against the stored data.
 */
@Service
public class AuthApiService {

    private AuthApiRepository repository;

    @Autowired
    public void setRepository(AuthApiRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a token into the database for a specific user and validates the saved token.
     * If the token is not saved successfully, an {@code ObjectNotSavedException} is thrown.
     *
     * @param userId the unique identifier of the user for whom the token is being created
     * @param token  the token to be saved in the database
     * @return {@code true} if the token is successfully saved and validated
     * @throws ObjectNotSavedException if the token could not be saved in the database
     */
    public boolean save(final long userId, final String token) {
        long tokenId = repository.getNextId();
        repository.save(tokenId, userId, token);
        boolean result = this.isValidToken(tokenId, token);
        if (!result) throw new ObjectNotSavedException("Token not saved in database");
        return true;
    }

    /**
     * Method get token from database and validate with value from parameter
     *
     * @param tokenId  id of database object which validation will be checked
     * @param expected expected value of token
     * @return compare result between founded in database and expected parameter
     */
    public boolean isValidToken(final long tokenId, final String expected) {
        String found = repository.findById(tokenId);
        return found.equalsIgnoreCase(expected);
    }

}



