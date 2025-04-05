package pl.derleta.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import pl.derleta.authorization.controller.mapper.JwtTokenApiMapper;
import pl.derleta.authorization.domain.entity.JwtTokenEntity;
import pl.derleta.authorization.domain.model.JwtToken;
import pl.derleta.authorization.repository.impl.JwtTokenRepository;

import java.util.List;

/**
 * Service class that provides functionality for managing and interacting with JWT tokens.
 * It includes methods for retrieving, saving, and deleting tokens, as well as filtering
 * and sorting capabilities for paginated data.
 */
@Service
public class JwtTokenService {

    private JwtTokenRepository repository;

    @Autowired
    public void setRepository(JwtTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a paginated and filtered list of JwtToken objects.
     *
     * @param page           the page number starting from 0
     * @param size           the number of items per page
     * @param sortBy         the field by which the results should be sorted
     * @param sortOrder      the sort order, either "asc" for ascending or "desc" for descending
     * @param usernameFilter a string to filter results by username
     * @param emailFilter    a string to filter results by email
     * @return a Page of JwtToken objects that match the specified filters and sorting criteria
     */
    public Page<JwtToken> getPage(final int page, final int size, final String sortBy, final String sortOrder, final String usernameFilter, final String emailFilter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        int offset = page * size;
        String sortByParam = getSortByParam(sortBy);
        String sortOrderParam = sortOrder.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        List<JwtToken> collection = JwtTokenApiMapper.toJwtTokens(
                repository.getSortedPageWithFilters(offset, size, sortByParam, sortOrderParam, usernameFilter, emailFilter)
        );
        long filteredColSize = repository.getFiltersCount(usernameFilter, emailFilter);
        return PageableExecutionUtils.getPage(collection, pageable, () -> filteredColSize);
    }

    /**
     * Retrieves a paginated list of valid JWT tokens, applying sorting and pagination parameters.
     *
     * @param page      the page number to retrieve (0-based index)
     * @param size      the number of items per page
     * @param sortBy    the attribute by which to sort the results
     * @param sortOrder the order in which to sort (e.g., "asc" for ascending or "desc" for descending)
     * @return a page of valid JWT tokens
     */
    public Page<JwtToken> getValid(final int page, final int size, final String sortBy, final String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        int offset = page * size;
        String sortByParam = getSortByParam(sortBy);
        String sortOrderParam = sortOrder.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        List<JwtToken> collection = JwtTokenApiMapper.toJwtTokens(
                repository.findValid(offset, size, sortByParam, sortOrderParam)
        );
        long filteredColSize = repository.getValidCount();
        return PageableExecutionUtils.getPage(collection, pageable, () -> filteredColSize);
    }

    /**
     * Retrieves a JWT token based on its unique identifier.
     *
     * @param tokenId the unique identifier of the token to retrieve
     * @return the JWT token as a JwtToken object
     */
    public JwtToken get(final long tokenId) {
        return JwtTokenApiMapper.toJwtToken(
                repository.findById(tokenId)
        );
    }

    /**
     * Saves a JWT token associated with a specific user and retrieves the saved token.
     *
     * @param userId the unique identifier of the user associated with the token
     * @param token  the JWT token to be saved
     * @return the saved JWT token as a JwtToken object
     */
    public JwtToken save(final long userId, final String token) {
        long tokenId = repository.getNextId();
        repository.save(tokenId, userId, token);
        return this.get(tokenId);
    }

    /**
     * Deletes a JWT token identified by its token ID and associated user ID.
     *
     * @param tokenId the unique identifier of the token to be deleted
     * @param userId  the unique identifier of the user associated with the token
     * @return true if the token was successfully deleted, false otherwise
     */
    public boolean delete(final long tokenId, final long userId) {
        JwtTokenEntity entity = repository.findById(tokenId);
        if (entity != null && entity.getTokenId() > 0) {
            repository.deleteById(tokenId, userId);
            return true;
        }
        return false;
    }

    /**
     * Translates a given sort key to its corresponding database column name.
     *
     * @param sortBy the sort key provided in request, such as "username" or "email".
     * @return the corresponding database column name, for example "u.username" for "username",
     * "u.email" for "email", or a default value "u.user_id" if the provided key does not match any known sort key.
     */
    private String getSortByParam(String sortBy) {
        if ("username".equalsIgnoreCase(sortBy)) return "u.username";
        else if ("email".equalsIgnoreCase(sortBy)) return "u.email";
        return "u.user_id";
    }

}
