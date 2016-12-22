package org.schreibvehler.auth;

import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.util.Objects;

/**
 * Created by awpwb on 27.04.2016.
 */
public class JWTRepository {

    /**
     * The JACC PolicyContext key for the current Subject
     */
    public static final String WEB_REQUEST_KEY = "javax.servlet.http.HttpServletRequest";

    String getToken(String headerString) {
        Objects.requireNonNull(headerString, "Parameter mustn't be null!");
        if (headerString.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameter musnt't be blank!");
        }

        String authMethod = getAuthMethod(headerString);

        if (isValidAuthorizationMethod(authMethod)) {
            return headerString.substring(headerString.indexOf(" ") + 1);
        }
        return headerString;
    }

    private String getAuthMethod(String headerString) {
        int endIndex = headerString.indexOf(" ");
        if (endIndex > 0) {
            String authMethod = headerString.substring(0, endIndex);
            if (authMethod == null || authMethod.trim().isEmpty()) {
                return null;
            }
            return authMethod;
        }
        return null;
    }

    private boolean isValidAuthorizationMethod(String authMethod) {
        if (authMethod == null) {
            return false;
        }
        if ("Basic".equals(authMethod) || "BASIC".equals(authMethod) || "Bearer".equals(authMethod) || "BEARER".equals(authMethod)) {
            return true;
        }
        throw new IllegalArgumentException("Illegal authorization method " + authMethod);
    }

    public String getTokenOfHttpRequest() throws PolicyContextException {
        return getToken(getAuthorizationHeader());
    }

    private String getAuthorizationHeader() throws PolicyContextException {
        HttpServletRequest request;
        request = (HttpServletRequest) PolicyContext.getContext(WEB_REQUEST_KEY);
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
