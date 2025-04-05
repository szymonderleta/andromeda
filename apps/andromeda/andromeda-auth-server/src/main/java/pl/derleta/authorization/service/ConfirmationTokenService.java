package pl.derleta.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import pl.derleta.authorization.controller.mapper.ConfirmationTokenApiMapper;
import pl.derleta.authorization.domain.entity.ConfirmationTokenEntity;
import pl.derleta.authorization.domain.model.ConfirmationToken;
import pl.derleta.authorization.repository.impl.ConfirmationTokenRepository;

import java.util.List;

/**
 * Service class for managing confirmation tokens.
 * Provides functionality for retrieving, saving, and deleting confirmation tokens,
 * as well as pagination and sorting capabilities.
 */
@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository repository;

    @Autowired
    public void setRepository(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a paginated list of ConfirmationTokens based on the specified filters and sorting options.
     *
     * @param page           The current page number, zero-based.
     * @param size           The number of items per page.
     * @param sortBy         The field to sort by.
     * @param sortOrder      The sorting order, either "asc" for ascending or "desc" for descending.
     * @param usernameFilter The filter for usernames, can be partial or empty.
     * @param emailFilter    The filter for email addresses, can be partial or empty.
     * @return A paginated list of ConfirmationTokens matching the specified criteria.
     */
    public Page<ConfirmationToken> getPage(final int page, final int size, final String sortBy, final String sortOrder, final String usernameFilter, final String emailFilter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        int offset = page * size;
        String sortByParam = getSortByParam(sortBy);
        String sortOrderParam = sortOrder.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        List<ConfirmationToken> collection = ConfirmationTokenApiMapper.toConfirmationTokens(
                repository.getSortedPageWithFilters(offset, size, sortByParam, sortOrderParam, usernameFilter, emailFilter)
        );
        long filteredColSize = repository.getFiltersCount(usernameFilter, emailFilter);
        return PageableExecutionUtils.getPage(collection, pageable, () -> filteredColSize);
    }

    /**
     * Retrieves a paginated and sorted list of valid confirmation tokens.
     *
     * @param page      the page number to retrieve (zero-based index)
     * @param size      the number of items per page
     * @param sortBy    the field by which the results should be sorted
     * @param sortOrder the sort order, either "asc" for ascending or "desc" for descending
     * @return a page containing the valid confirmation tokens based on the specified pagination and sorting parameters
     */
    public Page<ConfirmationToken> getValid(final int page, final int size, final String sortBy, final String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        int offset = page * size;
        String sortByParam = getSortByParam(sortBy);
        String sortOrderParam = sortOrder.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        List<ConfirmationToken> collection = ConfirmationTokenApiMapper.toConfirmationTokens(
                repository.findValid(offset, size, sortByParam, sortOrderParam)
        );
        long filteredColSize = repository.getValidCount();
        return PageableExecutionUtils.getPage(collection, pageable, () -> filteredColSize);
    }

    /**
     * Retrieves a confirmation token by its unique token ID.
     *
     * @param tokenId the unique identifier of the confirmation token to be retrieved
     * @return the confirmation token corresponding to the provided token ID
     */
    public ConfirmationToken get(final long tokenId) {
        return ConfirmationTokenApiMapper.toConfirmationToken(
                repository.findById(tokenId)
        );
    }

    /**
     * Saves a confirmation token associated with a specific user ID and retrieves the saved token.
     *
     * @param userId the unique identifier of the user for whom the token is being created
     * @param token  the token string to be associated with the user
     * @return the saved confirmation token
     */
    public ConfirmationToken save(final long userId, final String token) {
        long tokenId = repository.getNextId();
        repository.save(tokenId, userId, token);
        return this.get(tokenId);
    }

    /**
     * Deletes a confirmation token identified by the given token ID and associated with the specified user ID.
     *
     * @param tokenId the unique identifier of the confirmation token to be deleted
     * @param userId  the unique identifier of the user associated with the confirmation token
     * @return true if the token was successfully deleted; false if the token does not exist or could not be deleted
     */
    public boolean delete(final long tokenId, final long userId) {
        ConfirmationTokenEntity entity = repository.findById(tokenId);
        if (entity != null && entity.getTokenId() > 0) {
            repository.deleteById(tokenId, userId);
            return true;
        }
        return false;
    }

    /**
     * Maps the provided sort key to the corresponding database column name.
     *
     * @param sortBy the string representing the field by which the sorting should be performed (e.g., "username", "email").
     * @return the corresponding database column for the provided sort key. Defaults to "u.user_id" if no match is found.
     */
    private String getSortByParam(String sortBy) {
        if ("username".equalsIgnoreCase(sortBy)) return "u.username";
        else if ("email".equalsIgnoreCase(sortBy)) return "u.email";
        return "u.user_id";
    }

}
