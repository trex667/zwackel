package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest extends TestBase {

    @Test
    public void testEquals() throws Exception {
        User user = new User();
        user.setId(42);

        User anOtherUser = new User();
        anOtherUser.setId(42);

        assertNotEquals(user, new Object());
        assertNotEquals(user, null);
        assertEquals(user, user);
        assertThat(user, is(equalTo(anOtherUser)));
        assertThat(anOtherUser, is(equalTo(user)));
    }
    
    @Test
    public void thatThatToStringIncludesIdAndShortName() throws Exception {
        User user = new User();
        int id = 42;
        user.setId(id);
        String shortName = "zwackel";
        user.setShortName(shortName);
        
        assertThat(user.toString(), containsString(Integer.toString(id)));
        assertThat(user.toString(), containsString(shortName));
    }
    
    @Test
    public void createUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setShortName("zwackel");
        
        entityManager.persist(user);
        
        User userByFind = entityManager.find(User.class, 1);
        
        assertThat(user, is(equalTo(userByFind)));
        
    }
}
