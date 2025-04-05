package pl.derleta.nebula.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.derleta.nebula.controller.assembler.TokenModelAssembler;
import pl.derleta.nebula.controller.response.TokenDataResponse;
import pl.derleta.nebula.domain.rest.Role;
import pl.derleta.nebula.service.TokenProvider;

import java.util.Set;

/**
 * A REST controller for managing operations related to JWT tokens.
 * Provides endpoints to retrieve token details, validate tokens,
 * and access user-related information based on the token.
 */
@RestController
@CrossOrigin(origins = "https://milkyway.local:8555", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public final class TokenController {

    public static final String DEFAULT_PATH = "token";
    private final TokenProvider provider;
    private final TokenModelAssembler modelAssembler;

    /**
     * Retrieves token data based on the provided JWT token stored as a cookie.
     * The response is converted into a HATEOAS-compliant {@link TokenDataResponse} representation.
     *
     * @param jwtToken the JWT token extracted from the "jwtToken" cookie
     * @return a {@link ResponseEntity} containing the token data wrapped in a {@link TokenDataResponse},
     * with HTTP status 200 (OK)
     */
    @GetMapping(value = "/" + DEFAULT_PATH, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<TokenDataResponse> get(@CookieValue("jwtToken") String jwtToken) {
        var response = modelAssembler.toModel(
                provider.getTokenData(jwtToken)
        );
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Validates the provided JWT token extracted from the "jwtToken" cookie.
     *
     * @param jwtToken the JWT token extracted from the "jwtToken" cookie
     * @return a {@link ResponseEntity} containing a {@link Boolean} indicating whether the token is valid
     */
    @GetMapping(value = "/" + DEFAULT_PATH + "/valid", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Boolean> isValid(@CookieValue("jwtToken") String jwtToken) {
        var result = provider.isValid(jwtToken);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves the set of roles associated with the provided JWT token.
     *
     * @param jwtToken the JWT token extracted from the "jwtToken" cookie
     * @return a {@link ResponseEntity} containing a {@link Set} of {@link Role} objects if the token is valid,
     * or an HTTP 400 (Bad Request) status if the token is invalid
     */
    @GetMapping(value = "/" + DEFAULT_PATH + "/roles", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Set<Role>> getRoles(@CookieValue("jwtToken") String jwtToken) {
        var valid = provider.isValid(jwtToken);
        if (valid) {
            Set<Role> roles = provider.getRoles(jwtToken);
            return ResponseEntity.ok(roles);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Retrieves the email address associated with the provided JWT token.
     *
     * @param jwtToken the JWT token extracted from the "jwtToken" cookie
     * @return a {@link ResponseEntity} containing the email address as a {@link String} if the token is valid,
     * or an HTTP 400 (Bad Request) status if the token is invalid
     */
    @GetMapping(value = "/" + DEFAULT_PATH + "/email", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> getEmail(@CookieValue("jwtToken") String jwtToken) {
        var valid = provider.isValid(jwtToken);
        if (valid) {
            String email = provider.getEmail(jwtToken);
            return ResponseEntity.ok(email);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Retrieves the user ID associated with the provided JWT token.
     *
     * @param jwtToken the JWT token extracted from the "jwtToken" cookie
     * @return a {@link ResponseEntity} containing the user ID as a {@link Long} if the token is valid,
     * or an HTTP 400 (Bad Request) status if the token is invalid
     */
    @GetMapping(value = "/" + DEFAULT_PATH + "/id", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Long> getId(@CookieValue("jwtToken") String jwtToken) {
        var valid = provider.isValid(jwtToken);
        if (valid) {
            Long id = provider.getUserId(jwtToken);
            return ResponseEntity.ok(id);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
