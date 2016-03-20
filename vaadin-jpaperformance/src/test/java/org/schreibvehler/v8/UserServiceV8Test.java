package org.schreibvehler.v8;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by christian on 20.03.16.
 */
public class UserServiceV8Test {

    private UserServiceV8 service;

    @Before
    public void startup() {
        service = new UserServiceV8();
    }

    @Test
    public void testThat_isPaging_IsFalseForCrazyInput() {
        assertFalse(service.isPaging(null, null));
        assertFalse(service.isPaging(null, 42));
        assertFalse(service.isPaging(42, null));
        assertFalse(service.isPaging(-1, 42));
        assertFalse(service.isPaging(0, -42));
        assertFalse(service.isPaging(0, 0));
    }

    @Test
    public void testThat_isPaging_IsTrueForValidInput() {
        assertTrue(service.isPaging(0, 42));
        assertTrue(service.isPaging(100, 42));
    }

}