package pl.derleta.nebula.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.derleta.nebula.controller.request.*;
import pl.derleta.nebula.controller.response.AccountResponse;
import pl.derleta.nebula.controller.response.JwtTokenResponse;
import pl.derleta.nebula.controller.response.Response;
import pl.derleta.nebula.controller.response.ResponseWithCookieHeaders;
import pl.derleta.nebula.domain.rest.UserRoles;
import pl.derleta.nebula.exceptions.HttpRequestException;
import pl.derleta.nebula.exceptions.MissingHeaderException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * HttpAuthClient is responsible for interacting with authentication API endpoints.
 * It provides methods for user account management, token generation, and handling
 * HTTP requests with JSON bodies and authorization headers.
 */
@Component
public final class HttpAuthClient {

    @Value("${header.for.auth.serv}")
    private String appHeader;

    @Value(("${auth.serv.api.v1.url.account}"))
    private String authApiAccountUrl;

    @Value(("${auth.serv.api.v1.url.account.register}"))
    private String authApiPostRegisterUrl;

    @Value(("${auth.serv.api.v1.url.account.confirm}"))
    private String authApiAccountConfirmUrl;

    @Value(("${auth.serv.api.v1.url.account.unlock}"))
    private String authApiAccountUnlockUrl;

    @Value(("${auth.serv.api.v1.url.account.reset}"))
    private String authApiAccountResetPasswdUrl;

    @Value(("${auth.serv.api.v1.url.account.changepass}"))
    private String authApiAccountChangePasswdUrl;

    @Value(("${auth.serv.api.v1.url.account.token}"))
    private String authApiAccountTokenUrl;

    /**
     * Registers a new user by sending the registration request to the authentication API.
     *
     * @param request the registration request containing user details required for registration
     * @return an AccountResponse object containing the response data from the registration API
     */
    public AccountResponse registerUser(AuthServRegistrationRequest request) {
        String body = getBodyForRegistrationRequest(request);
        return postRequest(body, authApiPostRegisterUrl);
    }

    /**
     * Confirms a user account based on the provided confirmation request.
     *
     * @param confirmation the user confirmation request containing necessary details for account confirmation
     * @return an AccountResponse object representing the result of the account confirmation process
     */
    public AccountResponse confirmAccount(UserConfirmationRequest confirmation) {
        String body = getBodyForConfirmAccountRequest(confirmation);
        return patchRequest(body, authApiAccountConfirmUrl);
    }

    /**
     * Unlocks a user account by its unique identifier.
     *
     * @param id The unique identifier of the user account to be unlocked.
     * @return AccountResponse object containing details about the account's updated state after the unlock operation.
     */
    public AccountResponse unlockAccount(Long id) {
        String url = authApiAccountUnlockUrl + "/" + id;
        return patchRequest(url);
    }

    /**
     * Resets the password for the account associated with the given email address.
     * Sends a patch request to the authentication API endpoint for resetting passwords.
     *
     * @param email the email address of the account for which the password needs to be reset
     * @return an AccountResponse object containing the outcome of the password reset request
     */
    public AccountResponse resetPassword(String email) {
        String url = authApiAccountResetPasswdUrl + "/" + email;
        return patchRequest(url);
    }

    /**
     * Updates the account password by submitting a password update request using a provided JWT token.
     *
     * @param jwtToken the JSON Web Token used for authenticating the request
     * @param request  the request object containing the details required for updating the password
     * @return an AccountResponse object containing the result of the password update operation
     */
    public AccountResponse updatePassword(String jwtToken, PasswordUpdateRequest request) {
        String body = getBodyForUpdatePasswordRequest(request);
        return postRequestWithBearerToken(body, jwtToken, authApiAccountChangePasswdUrl);

    }

    /**
     * Generates a JWT token by making a request to the authentication API.
     *
     * @param authRequest the authentication request containing email and other required fields
     * @return a JwtTokenResponse containing the generated JWT token and additional token details
     */
    public JwtTokenResponse generateToken(AuthEmailRequest authRequest) {
        String body = getBodyForTokenRequest(authRequest);
        return postRequestForJwtToken(body, authApiAccountTokenUrl);
    }

    /**
     * Retrieves the user roles associated with an account based on the provided account registration request.
     *
     * @param request the account registration request containing the necessary details to fetch user roles
     * @return the user roles corresponding to the account information in the provided request
     */
    public UserRoles getAccount(AccountRegistrationRequest request) {
        String uri = getUriForUserRoleRequest(request);
        return getRequest(uri);
    }

    /**
     * Sends an HTTP GET request to the specified URI and retrieves user roles in the response.
     *
     * @param uri the URI to which the HTTP GET request is sent
     * @return the UserRoles object obtained from the response
     * @throws RuntimeException if an IOException occurs during the request execution
     */
    private UserRoles getRequest(String uri) {
        HttpGet httpGet = new HttpGet(uri);
        try {
            return executeForUserRoles(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a POST request to the specified remote service URL with the given JSON body
     * to retrieve a JWT token response.
     *
     * @param jsonBody         the JSON string containing the request body to be sent as part of the POST request
     * @param remoteServiceURL the URL of the remote service endpoint to which the POST request will be sent
     * @return the JwtTokenResponse object containing the response from the remote service
     */
    private JwtTokenResponse postRequestForJwtToken(String jsonBody, String remoteServiceURL) {
        return sendRequestWithBody(new HttpPost(remoteServiceURL), jsonBody, this::executeForJwtTokenResponse);
    }

    /**
     * Sends an HTTP POST request to the specified remote service URL with the provided JSON body and Bearer token.
     *
     * @param jsonBody         the JSON string to be sent as the request body
     * @param jwtToken         the Bearer token to be included in the HTTP Authorization header
     * @param remoteServiceURL the URL of the remote service to which the POST request will be sent
     * @return an AccountResponse object containing the response from the remote service
     */
    public AccountResponse postRequestWithBearerToken(String jsonBody, String jwtToken, String remoteServiceURL) {
        return sendRequestWithBodyAndBearerToken(
                new HttpPost(remoteServiceURL), jwtToken, jsonBody, this::executeForAccountResponse
        );
    }

    /**
     * Sends a POST request to the specified remote service URL with the provided JSON body
     * and processes the response to return an AccountResponse object.
     *
     * @param jsonBody         the JSON-formatted string representing the request body
     * @param remoteServiceURL the URL of the remote service to which the request is sent
     * @return an AccountResponse object representing the response from the remote service
     */
    public AccountResponse postRequest(String jsonBody, String remoteServiceURL) {
        return sendRequestWithBody(new HttpPost(remoteServiceURL), jsonBody, this::executeForAccountResponse);
    }

    /**
     * Sends a PATCH request to the specified remote service URL with the given JSON body
     * and processes the response into an AccountResponse.
     *
     * @param jsonBody         the JSON formatted string to be sent as the request body
     * @param remoteServiceURL the URL of the remote service to which the PATCH request will be sent
     * @return an AccountResponse object representing the processed response from the remote service
     */
    public AccountResponse patchRequest(String jsonBody, String remoteServiceURL) {
        return sendRequestWithBody(new HttpPatch(remoteServiceURL), jsonBody, this::executeForAccountResponse);
    }

    /**
     * Sends a PATCH request to the specified remote service URL and processes the response.
     *
     * @param remoteServiceURL the URL of the remote service to which the PATCH request is sent
     * @return an AccountResponse object containing the response data from the remote service
     */
    public AccountResponse patchRequest(String remoteServiceURL) {
        return sendRequest(new HttpPatch(remoteServiceURL), this::executeForAccountResponse);
    }

    /**
     * Sends an HTTP request with a JSON body and a Bearer token for authentication.
     * Adds the Bearer token as an Authorization header to the request
     * and includes the provided JSON body in the request.
     *
     * @param requestBase     the HTTP request to be executed
     * @param jwtToken        the JSON Web Token (JWT) to be added as a Bearer token in the Authorization header
     * @param jsonBody        the JSON body to be included in the request
     * @param responseHandler the response handler to process the response and return the result
     * @param <T>             the type of response expected, which extends Response
     * @return the processed response of type T
     */
    private <T extends Response> T sendRequestWithBodyAndBearerToken(HttpUriRequestBase requestBase, final String jwtToken, final String jsonBody, ResponseHandler<T> responseHandler) {
        Map<String, String> headers = createAuthHeader(jwtToken);
        headers.forEach((key, value) -> requestBase.addHeader(new BasicHeader(key, value)));
        return sendRequestWithBody(requestBase, jsonBody, responseHandler);
    }

    /**
     * Sends an HTTP request with a JSON body and processes the response using the provided response handler.
     *
     * @param requestBase     the HTTP request to be sent
     * @param jsonBody        the JSON body to be included in the request; can be null or blank
     * @param responseHandler a handler to process the HTTP response
     * @param <T>             the type of the response expected
     * @return the processed response of type T
     */
    private <T extends Response> T sendRequestWithBody(HttpUriRequestBase requestBase, final String jsonBody, ResponseHandler<T> responseHandler) {
        if (jsonBody != null && !jsonBody.isBlank()) {
            requestBase.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        }
        return sendRequest(requestBase, responseHandler);
    }

    /**
     * Sends an HTTP request using the provided {@code HttpUriRequestBase} and processes the response
     * using the given {@code ResponseHandler}. Handles any I/O exceptions that occur during the
     * request execution or response processing by throwing a custom {@code HttpRequestException}.
     *
     * @param <T>             the type of the response, which extends {@code Response}
     * @param requestBase     the HTTP request to be executed
     * @param responseHandler the handler responsible for processing the HTTP response and
     *                        returning the corresponding response object
     * @return the processed response as an object of type {@code T}
     * @throws HttpRequestException if an I/O error occurs during the execution of the request
     *                              or processing of the response
     */
    private <T extends Response> T sendRequest(final HttpUriRequestBase requestBase, ResponseHandler<T> responseHandler) {
        try {
            return responseHandler.handleResponse(requestBase);
        } catch (IOException e) {
            throw new HttpRequestException("Failed to execute account request for URL: " + requestBase.getRequestUri(), e);
        }
    }

    /**
     * Creates an authorization header map containing a single key-value pair where the key is "Authorization"
     * and the value is "Bearer " followed by the provided JWT token.
     *
     * @param jwtToken the JWT token to be included in the authorization header
     * @return a map containing the authorization header
     */
    private Map<String, String> createAuthHeader(String jwtToken) {
        return Map.of("Authorization", "Bearer " + jwtToken);
    }

    /**
     * Executes the given HTTP request and returns a deserialized {@code JwtTokenResponse} object, which contains the
     * authentication token and related details. This method processes the HTTP request and parses the response.
     *
     * @param requestBase the HTTP request to be executed
     * @return a {@code JwtTokenResponse} object containing the access token, username, and email details
     * @throws IOException if an I/O error occurs during the request execution or response processing
     */
    public JwtTokenResponse executeForJwtTokenResponse(HttpUriRequestBase requestBase) throws IOException {
        return executeRequest(requestBase, JwtTokenResponse.class);
    }

    /**
     * Executes the given HTTP request and processes its response, returning a deserialized {@code AccountResponse} object.
     * This method handles the HTTP request execution and response parsing to retrieve account-related details.
     *
     * @param requestBase the HTTP request to be executed
     * @return a {@code AccountResponse} object containing the success status and account response type
     * @throws IOException if an I/O error occurs during the request execution or response processing
     */
    public AccountResponse executeForAccountResponse(HttpUriRequestBase requestBase) throws IOException {
        return executeRequest(requestBase, AccountResponse.class);
    }

    /**
     * Executes the given HTTP request and processes its response, returning a deserialized {@code UserRoles} object.
     * This method incorporates HTTP request execution and response parsing to retrieve user role details.
     *
     * @param requestBase the HTTP request to be executed
     * @return a {@code UserRoles} object containing user account and assigned roles
     * @throws IOException if an I/O error occurs during the execution of the request or processing of the response
     */
    public UserRoles executeForUserRoles(HttpUriRequestBase requestBase) throws IOException {
        return executeRequest(requestBase, UserRoles.class);
    }

    /**
     * Executes the given HTTP request and processes its response, returning a deserialized object of the specified type.
     * This method incorporates the handling of HTTP request execution and response parsing.
     *
     * @param requestBase   the HTTP request to be executed
     * @param responseClass the class type into which the response body should be deserialized
     * @return the deserialized response body as an object of the specified class type
     * @throws IOException if an I/O error occurs during the request execution or response processing
     */
    public <T> T executeRequest(HttpUriRequestBase requestBase, Class<T> responseClass) throws IOException {
        return executeForResponse(requestBase, responseClass);
    }

    /**
     * Executes an HTTP request and processes the response into the desired response object type.
     *
     * @param requestBase the HTTP request to be executed; this request must be configured with
     *                    the required URL, headers, and request parameters.
     * @param responseClass the target class type for deserializing the response body.
     * @param <T> the type of the response object to be returned.
     * @return a response object of the specified type.
     * @throws IOException if an I/O error occurs during the request or response handling.
     */
    private <T> T executeForResponse(HttpUriRequestBase requestBase, Class<T> responseClass) throws IOException {
        addHeadersToRequest(requestBase);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpClientResponseHandler<T> responseHandler = response -> {

                validateHttpResponse((CloseableHttpResponse) response);
                String jsonResult = EntityUtils.toString(response.getEntity());
                T responseObject = parseJsonResponse(jsonResult, responseClass);

                if (responseObject instanceof ResponseWithCookieHeaders responseWithCookieHeaders) {
                    String cookieHeaders = Arrays.stream(response.getHeaders("Set-Cookie"))
                            .map(Header::getValue)
                            .filter(cookie -> cookie != null && !cookie.isBlank())
                            .findFirst()
                            .orElseThrow(() -> new MissingHeaderException("Missing header Set-Cookie"));
                    responseWithCookieHeaders.setCookieHeaders(cookieHeaders);
                }
                return responseObject;
            };
            return httpClient.execute(requestBase, responseHandler);
        }
    }

    /**
     * Validates the given HTTP response by checking the status code.
     * If the status code indicates a failure (not in the range 200-299),
     * an IOException is thrown with details about the failure.
     *
     * @param response The CloseableHttpResponse object to be validated.
     * @throws IOException If the HTTP request fails with a status code outside of the 2xx range,
     *                     or if an error occurs while reading the response entity.
     */
    private void validateHttpResponse(CloseableHttpResponse response) throws IOException {
        int statusCode = response.getCode();
        if (statusCode < 200 || statusCode >= 300) {
            String responseDetails;
            try {
                responseDetails = EntityUtils.toString(response.getEntity());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.err.println("Response details: " + responseDetails);
            throw new IOException("HTTP request failed with status code: " + statusCode + ", reason: " + response.getReasonPhrase());
        }
    }

    /**
     * Adds predefined headers to the given HTTP request.
     * The headers are constructed using the {@code getHeaders()} method.
     *
     * @param requestBase the HTTP request to which the headers will be added
     */
    private void addHeadersToRequest(HttpUriRequestBase requestBase) {
        getHeaders().forEach(requestBase::addHeader);
    }

    /**
     * Parses a JSON string into an object of the specified class type.
     *
     * @param jsonResult    the JSON string to be parsed
     * @param responseClass the class of the object to be created from the JSON string
     * @return an object of the specified class type, deserialized from the JSON string
     * @throws JsonProcessingException if the JSON string cannot be processed or deserialized
     */
    private <T> T parseJsonResponse(String jsonResult, Class<T> responseClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonResult, responseClass);
    }

    /**
     * Constructs and returns a list of headers required for HTTP requests.
     * The headers include a "Content-Type" header with value "application/json"
     * and an "X-Requesting-App" header with the value of the appHeader field.
     *
     * @return a list of Header objects containing the required HTTP headers
     */
    private List<Header> getHeaders() {
        return List.of(
                createHeader(org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE, "application/json"),
                createHeader("X-Requesting-App", appHeader)
        );
    }

    /**
     * Creates a header with the specified name and value.
     *
     * @param name  the name of the header
     * @param value the value of the header
     * @return a Header object containing the specified name and value
     */
    private Header createHeader(String name, String value) {
        return new BasicHeader(name, value);
    }

    /**
     * Constructs a JSON-formatted request body for user registration.
     *
     * @param request an AuthServRegistrationRequest object containing the username,
     *                email, and encrypted password of the user to be registered
     * @return a JSON string representing the request body with the user's
     * username, encrypted password, and email information
     */
    private String getBodyForRegistrationRequest(AuthServRegistrationRequest request) {
        return String.format("""
                            {
                                "username": "%s",
                                "password": "%s",
                                "email": "%s"
                            }
                        """,
                request.getUsername(),
                request.getEncryptedPassword(),
                request.getEmail());
    }

    /**
     * Constructs a JSON-formatted request body for confirming an account.
     *
     * @param confirmation the UserConfirmationRequest containing the token ID and the token string
     * @return a JSON string representing the request body with the token ID and token details
     */
    private String getBodyForConfirmAccountRequest(UserConfirmationRequest confirmation) {
        return String.format("""
                            {
                                "tokenId": %s,
                                "token": "%s"
                            }
                        """,
                confirmation.tokenId(),
                confirmation.token());
    }

    /**
     * Constructs the body for a token request using the provided AuthEmailRequest object.
     * The body is formatted as a JSON string containing the login and password.
     *
     * @param request the AuthEmailRequest object containing the email and password to be used in the token request
     * @return the formatted JSON string representing the body of the token request
     */
    private String getBodyForTokenRequest(AuthEmailRequest request) {
        return String.format("""
                            {
                                "login": "%s",
                                "password": "%s"
                            }
                        """,
                request.getEmail(),
                request.getPassword());
    }

    /**
     * Creates a JSON-formatted request body for updating a user's password.
     *
     * @param request the PasswordUpdateRequest object containing user ID, email,
     *                actual password, and new password.
     * @return a JSON string representing the request body with the details
     * required to update the password.
     */
    private String getBodyForUpdatePasswordRequest(PasswordUpdateRequest request) {
        return String.format("""
                            {
                                "userId": %s,
                                "email": "%s",
                                "actualPassword": "%s",
                                "newPassword": "%s"
                            }
                        """,
                request.getUserId(),
                request.getEmail(),
                request.getActualPassword(),
                request.getNewPassword());
    }

    /**
     * Constructs and returns the URI for making a user role request based on the provided account registration details.
     *
     * @param request the account registration request containing the user's login and email information
     * @return the constructed URI as a string, including the username and email query parameters
     */
    private String getUriForUserRoleRequest(AccountRegistrationRequest request) {
        return String.format("%s?username=%s&email=%s",
                authApiAccountUrl,
                request.getLogin(),
                request.getEmail());
    }

}
