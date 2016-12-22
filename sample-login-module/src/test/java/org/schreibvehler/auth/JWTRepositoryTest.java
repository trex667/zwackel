package org.schreibvehler.auth;

import org.junit.Before;
import org.junit.Test;

import javax.ejb.BeforeCompletion;

import static org.junit.Assert.*;

/**
 * Created by awpwb on 27.04.2016.
 */
public class JWTRepositoryTest {
    private JWTRepository jwtRepository;

    private final String token = "ToKeNsTrInG";

    @Before
    public void startup() {
        jwtRepository = new JWTRepository();
    }

    @Test(expected = NullPointerException.class)
    public void testThatGetTokenThrowsExceptionWhenParameterIsNull() throws Exception {
        jwtRepository.getToken(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetTokenThrowsExceptionWhenParameterIsEmpty() throws Exception {
        jwtRepository.getToken("");
    }

    @Test
    public void testThatGetTokenReturnTokenSubstring() throws Exception {

        assertEquals(token, jwtRepository.getToken(token));
        assertEquals(token, jwtRepository.getToken("Basic " + token));
        assertEquals(token, jwtRepository.getToken("BASIC " + token));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetTokenThrowsExceptionIfAuthStringIsInvalid() throws Exception {
        jwtRepository.getToken("Invalid " + token);
    }
}