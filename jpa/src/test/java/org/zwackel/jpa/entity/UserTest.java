package org.zwackel.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class UserTest extends TestBase {

    @Test
    public void testEquals() throws Exception {
        User user = new User();
        user.setId(42);

        User anOtherUser = new User();
        anOtherUser.setId(42);

        assertThat(user).isNotEqualTo(new Object());
        assertThat(user).isNotEqualTo(null);
        assertThat(user).isEqualTo(user);
        assertThat(user).isEqualTo(anOtherUser);
        assertThat(anOtherUser).isEqualTo(user);
    }

    @Test
    public void testEqualsWithNullId() throws Exception {
        User user = new User();
        user.setId(42);

        User anOtherUser = new User();

        assertThat(user).isNotEqualTo(anOtherUser);
        assertThat(anOtherUser).isNotEqualTo(user);

        assertThat(anOtherUser).isEqualTo(new User());
    }

    @Test
    public void thatThatToStringIncludesIdAndShortName() throws Exception {
        User user = new User();
        int id = 42;
        user.setId(id);
        String shortName = RandomStringUtils.randomAlphanumeric(20);
        user.setShortName(shortName);

        assertThat(user.toString()).contains(Integer.toString(id));
        assertThat(user.toString()).contains(shortName);
    }

    @Test
    public void createUser_generateIDautomatically_isSetInTheEntityFromTheEntityManagerPersist() throws Exception {
        User user = new User();
        String shortName = RandomStringUtils.randomAlphanumeric(20);
        user.setShortName(shortName);

        LOG.debug("vor dem persist: " + user);
        // das eigentliche insert des users wird erst beim commit der
        // Transaktion gemacht. Hier also in der after() methode!
        entityManager.persist(user);
        LOG.debug("nach dem persist: " + user);
        assertThat(user.getId()).isNotEqualTo(0);

        User userByFind = entityManager.find(User.class, user.getId());

        assertThat(user).isEqualTo(userByFind);

    }

    @Test
    public void createUserWithAllAttributes() throws Exception {
        User user = new User();
        String shortName = RandomStringUtils.randomAlphanumeric(20);
        user.setShortName(shortName);
        user.setFirstName("Petrosilius");
        user.setLastName("Zwackelmann");
        user.setBirthdate(DateUtils.parseDate("14.12.1969", "dd.mm.yyyy"));

        Address address = new Address();
        address.setCity("Trier");
        address.setStreet("Monaiser Straße 11");
        address.setZipCode("54294");
        user.setAddress(address);

        Accessibility email = new Accessibility();
        email.setType(AccessibilityType.email);
        email.setValue("petrosilius.zwackelmann@blub.de");
        user.addAccessibility(email);

        Accessibility mobile = new Accessibility();
        mobile.setType(AccessibilityType.mobilePhone);
        mobile.setValue("+49 1511 1234567");
        user.addAccessibility(mobile);

        user.addSkill("jpa Guru");
        user.addSkill("cdi beginner");

        entityManager.persist(mobile);
        entityManager.persist(email);
        entityManager.persist(user);

        LOG.debug("nach dem persist: " + user);
        assertThat(user.getId()).isNotEqualTo(0);

        User userByFind = entityManager.find(User.class, user.getId());

        assertThat(user).isEqualTo(userByFind);

    }
}
