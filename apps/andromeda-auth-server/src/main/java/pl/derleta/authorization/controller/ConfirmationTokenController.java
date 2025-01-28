package pl.derleta.authorization.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.derleta.authorization.AndromedaAuthorizationServerApplication;
import pl.derleta.authorization.controller.assembler.ConfirmationTokenModelAssembler;
import pl.derleta.authorization.domain.model.ConfirmationToken;
import pl.derleta.authorization.domain.response.ConfirmationTokenResponse;
import pl.derleta.authorization.service.ConfirmationTokenService;

/**
 * Rest Controller responsible for handling HTTP requests related to confirmation tokens.
 * Provides functionality for retrieving, adding, and deleting confirmation tokens,
 * with support for pagination, filtering, and sorting of tokens.
 */
@RestController
@RequestMapping("/api/v1")
public class ConfirmationTokenController {

    public static final String DEFAULT_PATH = "table/confirmations";

    private final ConfirmationTokenService service;
    private final ConfirmationTokenModelAssembler tokenModelAssembler;
    private final PagedResourcesAssembler<ConfirmationToken> pagedResourcesAssembler;

    @Autowired
    public ConfirmationTokenController(ConfirmationTokenService service, ConfirmationTokenModelAssembler tokenModelAssembler, PagedResourcesAssembler<ConfirmationToken> pagedResourcesAssembler) {
        this.service = service;
        this.tokenModelAssembler = tokenModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves a paginated and optionally filtered list of confirmation tokens.
     *
     * @param page           the page number to retrieve, defaults to 0
     * @param size           the number of items per page, defaults to the application default page size
     * @param sortBy         the field to sort the results by, defaults to "userId"
     * @param sortOrder      the order of sorting, can be "asc" for ascending or "desc" for descending, defaults to "asc"
     * @param usernameFilter an optional filter to narrow results by username, defaults to an empty string
     * @param emailFilter    an optional filter to narrow results by email, defaults to an empty string
     * @return a {@code ResponseEntity} containing a paginated model of {@code ConfirmationTokenResponse} objects
     */
    @RolesAllowed({"ROLE_USER", "ROLE_TESTER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    @GetMapping(value = "/" + DEFAULT_PATH, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<ConfirmationTokenResponse>> getPage(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "" + AndromedaAuthorizationServerApplication.DEFAULT_PAGE_SIZE) int size,
                                                                         @RequestParam(defaultValue = "userId") String sortBy,
                                                                         @RequestParam(defaultValue = "asc") String sortOrder,
                                                                         @RequestParam(defaultValue = "") String usernameFilter,
                                                                         @RequestParam(defaultValue = "") String emailFilter) {
        Page<ConfirmationToken> tokensPage = service.getPage(page, size, sortBy, sortOrder, usernameFilter, emailFilter);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(tokensPage, tokenModelAssembler));
    }

    /**
     * Retrieves a paginated list of valid confirmation tokens based on the specified parameters.
     *
     * @param page      the page number to retrieve, default is 0
     * @param size      the number of records per page, default is the application's default page size
     * @param sortBy    the field by which to sort the results, default is "userId"
     * @param sortOrder the order of sorting, either "asc" for ascending or "desc" for descending, default is "asc"
     * @return a ResponseEntity containing the paginated and assembled resources of valid confirmation tokens
     */
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping(value = "/" + DEFAULT_PATH + "/valid", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<ConfirmationTokenResponse>> getValid(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "" + AndromedaAuthorizationServerApplication.DEFAULT_PAGE_SIZE) int size,
                                                                          @RequestParam(defaultValue = "userId") String sortBy,
                                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<ConfirmationToken> tokensPage = service.getValid(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(tokensPage, tokenModelAssembler));
    }

    /**
     * Retrieves a confirmation token by its ID.
     *
     * @param id the identifier of the confirmation token to be retrieved
     * @return a ResponseEntity containing the confirmation token response and HTTP status OK
     */
    @RolesAllowed({"ROLE_USER", "ROLE_TESTER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    @GetMapping(value = "/" + DEFAULT_PATH + "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ConfirmationTokenResponse> get(@PathVariable Integer id) {
        ConfirmationTokenResponse response = tokenModelAssembler.toModel(
                service.get(id)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Adds a new confirmation token for the specified user.
     *
     * @param userId the ID of the user for whom the token is being added
     * @param token  the confirmation token to be added
     * @return a ResponseEntity containing the created ConfirmationTokenResponse and an HTTP status code of CREATED
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_TESTER", "ROLE_USER"})
    @PostMapping("/" + DEFAULT_PATH + "/{userId}")
    public ResponseEntity<ConfirmationTokenResponse> add(@PathVariable Long userId, @RequestBody String token) {
        ConfirmationTokenResponse response = tokenModelAssembler.toModel(
                service.save(userId, token)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Deletes a confirmation token specified by its token ID and user ID.
     *
     * @param tokenId the ID of the confirmation token to be deleted
     * @param userId  the ID of the user associated with the confirmation token
     * @return a ResponseEntity with HTTP status OK if the deletion is successful,
     * or HTTP status NOT_FOUND if the token does not exist
     */
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/" + DEFAULT_PATH + "/{tokenId}/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long tokenId, @PathVariable Long userId) {
        boolean success = service.delete(tokenId, userId);
        return success ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
