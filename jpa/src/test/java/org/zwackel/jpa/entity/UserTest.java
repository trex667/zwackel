package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.hamcrest.core.IsNot;
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
    public void testEqualsWithNullId() throws Exception {
        User user = new User();
        user.setId(42);
        
        User anOtherUser = new User();
        
        assertThat(user, is(not(equalTo(anOtherUser))));
        assertThat(anOtherUser, is(not(equalTo(user))));
        
        assertThat(anOtherUser, is(equalTo(new User())));
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
    public void createUser_generateIDautomatically_isSetInTheEntityFromTheEntityManagerPersist() throws Exception {
        User user = new User();
        user.setShortName("zwackel");
        
        LOG.debug("vor dem persist: " + user);
        // das eigentliche insert des users wird erst beim commit der Transaktion gemacht. Hier also in der after() methode!
        entityManager.persist(user);
        LOG.debug("nach dem persist: " + user);
        assertThat(user.getId(), is(not(equalTo(0))));
        
        User userByFind = entityManager.find(User.class, user.getId());
        
        assertThat(user, is(equalTo(userByFind)));
        
    }
}
