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
import pl.derleta.authorization.controller.assembler.JwtTokenModelAssembler;
import pl.derleta.authorization.domain.model.JwtToken;
import pl.derleta.authorization.domain.response.JwtTokenResponse;
import pl.derleta.authorization.service.JwtTokenService;

/**
 * Controller responsible for managing JWT tokens for users, providing operations
 * such as retrieval, creation, and deletion of tokens. It supports pagination,
 * filtering, and sorting of token data.
 */
@RestController
@RequestMapping("/api/v1")
public class JwtTokenController {

    public static final String DEFAULT_PATH = "table/tokens";

    private final JwtTokenService service;
    private final JwtTokenModelAssembler jwtTokenModelAssembler;
    private final PagedResourcesAssembler<JwtToken> pagedResourcesAssembler;

    @Autowired
    public JwtTokenController(JwtTokenService service, JwtTokenModelAssembler jwtTokenModelAssembler, PagedResourcesAssembler<JwtToken> pagedResourcesAssembler) {
        this.service = service;
        this.jwtTokenModelAssembler = jwtTokenModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves a paginated list of JWT tokens based on the provided filters and sorting options.
     *
     * @param page the page index to retrieve, defaults to 0.
     * @param size the number of items per page, defaults to the application-defined page size.
     * @param sortBy the field by which to sort the results, defaults to "userId".
     * @param sortOrder the sorting order; either "asc" for ascending or "desc" for descending, defaults to "asc".
     * @param usernameFilter optional filter to filter tokens based on the username.
     * @param emailFilter optional filter to filter tokens based on the email.
     * @return a {@link ResponseEntity} containing a paginated model of {@link JwtTokenResponse}.
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TESTER"})
    @GetMapping(value = "/" + DEFAULT_PATH, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<JwtTokenResponse>> getPage(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "" + AndromedaAuthorizationServerApplication.DEFAULT_PAGE_SIZE) int size,
                                                                @RequestParam(defaultValue = "userId") String sortBy,
                                                                @RequestParam(defaultValue = "asc") String sortOrder,
                                                                @RequestParam(defaultValue = "") String usernameFilter,
                                                                @RequestParam(defaultValue = "") String emailFilter) {
        Page<JwtToken> tokensPage = service.getPage(page, size, sortBy, sortOrder, usernameFilter, emailFilter);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(tokensPage, jwtTokenModelAssembler));
    }

    /**
     * Retrieves a paginated list of valid JWT tokens.
     *
     * @param page      the page number to retrieve, defaults to 0 if not specified
     * @param size      the number of items per page, defaults to the application's default page size
     * @param sortBy    the field to sort the results by, defaults to "userId" if not specified
     * @param sortOrder the sort order, either "asc" for ascending or "desc" for descending, defaults to "asc"
     * @return a ResponseEntity containing a PagedModel of JwtTokenResponse representing the valid JWT tokens
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TESTER"})
    @GetMapping(value = "/" + DEFAULT_PATH + "/valid", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<JwtTokenResponse>> getValid(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "" + AndromedaAuthorizationServerApplication.DEFAULT_PAGE_SIZE) int size,
                                                                 @RequestParam(defaultValue = "userId") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<JwtToken> tokensPage = service.getValid(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(tokensPage, jwtTokenModelAssembler));
    }

    /**
     * Retrieves a JwtTokenResponse for the provided id.
     *
     * @param id the identifier of the JwtToken to retrieve
     * @return a ResponseEntity containing the JwtTokenResponse and an HTTP status code of OK
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TESTER"})
    @GetMapping(value = "/" + DEFAULT_PATH + "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<JwtTokenResponse> get(@PathVariable Integer id) {
        JwtTokenResponse response = jwtTokenModelAssembler.toModel(
                service.get(id)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Adds a new token for the specified user and returns the created resource.
     *
     * @param userId the ID of the user for whom the token is being added
     * @param token the token to be added
     * @return a ResponseEntity containing the created JwtTokenResponse and HTTP status code
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_TESTER"})
    @PostMapping("/" + DEFAULT_PATH + "/{userId}")
    public ResponseEntity<JwtTokenResponse> add(@PathVariable Long userId, @RequestBody String token) {
        JwtTokenResponse response = jwtTokenModelAssembler.toModel(
                service.save(userId, token)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Deletes a resource identified by the given token ID and user ID.
     *
     * @param tokenId the ID of the token to be deleted
     * @param userId the ID of the user associated with the token
     * @return a ResponseEntity with HTTP status OK if the deletion was successful,
     *         or NOT_FOUND if the resource could not be found
     */
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/" + DEFAULT_PATH + "/{tokenId}/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long tokenId, @PathVariable Long userId) {
        boolean success = service.delete(tokenId, userId);
        return success ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
