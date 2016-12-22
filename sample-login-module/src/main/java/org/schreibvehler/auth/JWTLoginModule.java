package org.schreibvehler.auth;

import io.jsonwebtoken.Jwts;
import org.jboss.security.auth.callback.ObjectCallback;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by awpwb on 22.04.2016.
 */
public class JWTLoginModule extends AbstractServerLoginModule {

    Logger log = Logger.getLogger(JWTLoginModule.class.getName());

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        log.info("initialize");
        super.initialize(subject, callbackHandler, sharedState, options);
    }

    @Override
    public boolean login() throws LoginException {
        log.info("login()");
        if (super.login()) {
            log.info("super.login()==true");
            return true;
        }

        try {
            //Key key = MacProvider.generateKey();
            //String s = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, key).compact();

            String token = new JWTRepository().getTokenOfHttpRequest();

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedByteArray = decoder.decode(token);
            //Verify the decoded string
            String decodedToken = new String(decodedByteArray);
            System.out.println(decodedToken);

//            token = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxNjExMTdiZi0wZTkwLTRmMmYtOGU2My0wNjM4ZGU4MmNiYjAiLCJleHAiOjE0NjE3NDMyOTcsIm5iZiI6MCwiaWF0IjoxNDYxNzQyOTk3LCJpc3MiOiJodHRwOi8vMTkyLjE2OC45OS4xMDA6ODA4MS9hdXRoL3JlYWxtcy9vYWYiLCJhdWQiOiJsb2dpbi1hcHAiLCJzdWIiOiI4YzFkN2FhMC01ZmExLTQxZGMtYTBiOC1mZTU5NWIzZDg5MWYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsb2dpbi1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiYWY5Nzk3ZGYtZjBjNC00ZWQ0LWIwMzItNjJjYTc4NWEyMGY5IiwiY2xpZW50X3Nlc3Npb24iOiJjZmQ3OWE5YS0wMWZmLTQ1Y2YtYWZiOS1hYTc2ZWY3MDdjYTkiLCJhbGxvd2VkLW9yaWdpbnMiOlsiL2xvZ2luLWFwcCJdLCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sIm5hbWUiOiJPUkJJUy1pbnRlcm4gT1JCSVMtaW50ZXJuIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2NodWx1bmciLCJnaXZlbl9uYW1lIjoiT1JCSVMtaW50ZXJuIiwiZmFtaWx5X25hbWUiOiJPUkJJUy1pbnRlcm4ifQ.MeIOgc1luZ_ZZgHBWnCvaVuHl18-FMdQueqrSeRlViPTOjBCw8Y-S4ybGHGA24LJndR5oy-z0htqVNn8HPwKK5QT7SRKeO9H5ddN5IdHH513G1mHesSAUIRaeMg-KA0j3GbwDtUEUqgvpDFCGgHsyjKJzvj_c0IX3F9aIHAia3Zrs9WELJDRZu-NYMyc7-xjBoUjuEx1h-dVNVMtBEFXMx5YUK5q_Co10-bK5EvnpViyo0R3R2DljpWSvYEM-Bjfv5Qlrvoe-2u0fCaYQi11V7Js7cVzcJD2H8rZ2lScO3fBcs59HvDdjtoKc6q4xPfZR2msAAVs3vmXPuzBJzG9sw";
            String signingKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjcjuF9JeHkSi0CK9p8avVMCS/tAy6eTBgmT/ICZm+BRtInunaCKFLxEJahyMCixDYBtsIXBA5Q2tbM42jSqJnjHJre+6t3LGZHFZOpWmRKbbV3qqtil2B2ojHDt7MwQwU4mDUIozKP/rfIX+CP0Abhk0doAzI3KvH0g97enQFWachr6EaqQIkucj8ullCZpomZ4XPm6KyqZvUXJKqA0MI3itDOdaeLk3evj2A+QRhlA2G2nw2PXXeOBN2unPJ/kFQhVEVe5sKkJZ3z9dRTaQNBByprizG4buerObgUHVloRnKMOGg03J2AG6ta8Pia3dMkGFIx5Bg6xIc8cWRsQkuQIDAQAB";
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(decodedToken);
            //OK, we can trust this JWT
        } catch (Throwable e) {
            //don't trust the JWT!
            String msg = "invalid javascript web token!";
            LoginException loginException = new LoginException(msg);
            log.log(Level.FINE, msg, e);
            //loginException.initCause(e);
            throw loginException;
        }

        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        log.info("commit");
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        log.info("abort");
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        log.info("logout");
        return true;
    }

    @Override
    protected Principal getIdentity() {
        return null;
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        return new Group[0];
    }
}
