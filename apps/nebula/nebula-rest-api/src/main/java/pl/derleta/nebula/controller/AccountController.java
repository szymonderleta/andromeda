package pl.derleta.nebula.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.derleta.nebula.controller.request.AccountRegistrationRequest;
import pl.derleta.nebula.controller.request.AuthEmailRequest;
import pl.derleta.nebula.controller.request.PasswordUpdateRequest;
import pl.derleta.nebula.controller.request.UserConfirmationRequest;
import pl.derleta.nebula.controller.response.AccountResponse;
import pl.derleta.nebula.controller.response.JwtTokenResponse;
import pl.derleta.nebula.controller.response.Response;
import pl.derleta.nebula.service.AccountUpdater;
import pl.derleta.nebula.service.TokenProvider;

/**
 * The AccountController provides RESTful endpoints for user account management.
 * This includes registration, account confirmation, unlocking accounts,
 * password management, and JWT token generation.
 * The controller operates on version "v1" of API endpoints prefixed with "/api/v1",
 * and enforces cross-origin resource sharing (CORS) settings for specified origins.
 * <p>
 * All endpoints return appropriate HTTP responses for successful operations or failure cases.
 */
@RestController
@CrossOrigin(origins = "https://milkyway.local:8555", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public final class AccountController {

    public static final String DEFAULT_PATH = "account";

    private final AccountUpdater updater;
    private final TokenProvider tokenProvider;

    /**
     * Handles user registration by processing the request and returning the appropriate response.
     *
     * @param request the account registration request containing user details such as login, email, password, etc.
     * @return a {@code ResponseEntity} containing an {@code AccountResponse} if registration is successful,
     * or a {@code ResponseEntity} containing an error {@code Response} in case of a server error.
     */
    @PostMapping(value = DEFAULT_PATH + "/register", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Response> register(@RequestBody AccountRegistrationRequest request) {
        var response = updater.register(request);
        if (response instanceof AccountResponse instance && instance.isSuccess()) {
            return ResponseEntity.ok(instance);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Handles the confirmation of a user account or related process.
     *
     * @param confirmation the request body containing user confirmation details
     * @return a ResponseEntity containing the response, with HTTP status 200 (OK) if
     * the confirmation is successful, or HTTP status 500 (Internal Server Error)
     * if the operation fails
     */
    @PatchMapping(value = DEFAULT_PATH + "/confirm", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Response> confirm(@RequestBody UserConfirmationRequest confirmation) {
        var response = updater.confirm(confirmation);
        if (response instanceof AccountResponse instance && instance.isSuccess()) {
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Unlocks the account associated with the specified ID.
     *
     * @param id the identifier of the account to be unlocked
     * @return a {@code ResponseEntity} containing an {@code AccountResponse} if the unlock operation is successful,
     * or a {@code ResponseEntity} with an internal server error status and the response details in case of failure.
     */
    @PatchMapping(value = "/" + DEFAULT_PATH + "/unlock/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<AccountResponse> unlock(@PathVariable Long id) {
        var response = updater.unlock(id);
        if (response instanceof AccountResponse instance && instance.isSuccess()) {
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Resets the password for the user with the provided email address.
     *
     * @param email the email address of the user whose password needs to be reset
     * @return a {@code ResponseEntity} containing an {@code AccountResponse} if the password reset operation is successful,
     * or a {@code ResponseEntity} with an error status in case of a server issue.
     */
    @PatchMapping(value = "/" + DEFAULT_PATH + "/reset-password/{email}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<AccountResponse> resetPassword(@PathVariable String email) {
        var response = updater.resetPassword(email);
        if (response instanceof AccountResponse instance && instance.isSuccess()) {
            return ResponseEntity.ok(response);
        } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Generates a JWT token based on the provided authentication credentials.
     * This method is used to authenticate a user and return a token that can be used for subsequent requests.
     *
     * @param authRequest The authentication request containing the user's credentials.
     * @return A ResponseEntity containing the JWT token response if authentication is successful,
     * or an internal server error status if the token could not be generated.
     */
    @PostMapping(value = "/" + DEFAULT_PATH + "/token", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<JwtTokenResponse> getToken(@RequestBody AuthEmailRequest authRequest) {
        var response = updater.generateToken(authRequest);
        if (response instanceof JwtTokenResponse instance) {
            return ResponseEntity.ok().header("Set-Cookie", response.getCookieHeaders()).body(instance);
        } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * Handles the request to change the password for a user account. This endpoint
     * validates the provided JWT token and updates the password if the token is valid.
     *
     * @param jwtToken              the JWT token extracted from the "jwtToken" cookie. Used to authenticate the request.
     * @param passwordUpdateRequest the request body containing the password update details,
     *                              including the userId and the new password.
     * @return a ResponseEntity containing an AccountResponse object. If the operation is successful,
     * it returns a response with HTTP status 200 and success information. If the token is
     * invalid, it returns HTTP status 403. If there is an internal server error, it returns
     * HTTP status 500 along with the response.
     */
    @PostMapping(value = "/" + DEFAULT_PATH + "/change-password", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<AccountResponse> changePassword(@CookieValue("jwtToken") String jwtToken,
                                                          @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        if (tokenProvider.isValid(jwtToken, passwordUpdateRequest.getUserId())) {
            var response = updater.updatePassword(jwtToken, passwordUpdateRequest);
            if (response instanceof AccountResponse instance && instance.isSuccess()) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}
