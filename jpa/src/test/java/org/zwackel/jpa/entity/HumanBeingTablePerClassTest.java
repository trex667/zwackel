package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class HumanBeingTablePerClassTest extends TestBase {
@Test
public void persistOfNaturalPerson() throws Exception {
    HumanBeingTablePerClass person = new HumanBeingTablePerClass();
    person.setFirstName("Petrosilius");
    person.setLastName("Zwackelmann");
    person.setBirthdate(DateUtils.parseDate("14.12.1969", "dd.mm.yyyy"));
    
    entityManager.persist(person);

    LOG.debug("nach dem persist: " + person);
    assertThat(person.getId(), is(notNullValue()));

    HumanBeingTablePerClass personByFind = entityManager.find(HumanBeingTablePerClass.class, person.getId());

    assertThat(person, is(equalTo(personByFind)));
}
}
