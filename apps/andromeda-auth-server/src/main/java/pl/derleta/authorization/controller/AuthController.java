package pl.derleta.authorization.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.derleta.authorization.config.model.UserSecurity;
import pl.derleta.authorization.config.security.api.*;
import pl.derleta.authorization.config.security.jwt.JwtTokenUtil;

import java.util.Objects;

/**
 * Controller handling user authentication and JWT token management.
 * Provides endpoints for login authentication and manages the creation,
 * storage, and issuance of JWT tokens for authenticated users.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String COOKIE_NAME = "jwtToken";

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final AuthApiService authApiService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, AuthApiService authApiService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.authApiService = authApiService;
    }


    /**
     * Authenticates a user using the provided login credentials and returns an appropriate response.
     * On successful authentication, builds a success response.
     * On failure, returns an unauthorized response.
     *
     * @param request the authentication request containing user login and password
     * @param response the HTTP response used to add additional information (e.g., cookies)
     * @return a ResponseEntity containing authentication success details or an error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
            );
            return buildSuccessResponse(authentication, response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }

    /**
     * Builds a successful authentication response containing user information and access token.
     * Additionally, the access token is saved and a cookie with the token is added to the HTTP response.
     *
     * @param authentication the authentication object containing the user's principal (UserSecurity)
     * @param httpServletResponse the HttpServletResponse used to add the access token as a cookie
     * @return a ResponseEntity containing the AuthResponse with user details and access token,
     * or an unauthorized response if the user details are missing
     */
    @NotNull
    private ResponseEntity<?> buildSuccessResponse(Authentication authentication, HttpServletResponse httpServletResponse) {
        UserSecurity user = (UserSecurity) authentication.getPrincipal();
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        addCookieToResponse(httpServletResponse, accessToken);
        this.authApiService.save(user.getId(), accessToken);
        AuthResponse response = new AuthResponse(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a cookie containing the provided access token to the HTTP response.
     *
     * @param response the HttpServletResponse to which the cookie will be added
     * @param accessToken the access token to be stored in the cookie
     */
    private void addCookieToResponse(HttpServletResponse response, String accessToken) {
        Cookie cookie = new Cookie(COOKIE_NAME, accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtUtil.JWT_EXPIRATION / 1000);
        response.addCookie(cookie);
    }

}
