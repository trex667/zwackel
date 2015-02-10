package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class PersonSingleTableTest extends TestBase {
@Test
public void persistOfHumanBeing() throws Exception {
    HumanBeingSingleTable person = new HumanBeingSingleTable();
    person.setFirstName("Petrosilius");
    person.setLastName("Zwackelmann");
    person.setBirthdate(DateUtils.parseDate("14.12.1969", "dd.mm.yyyy"));
    
    entityManager.persist(person);

    LOG.debug("nach dem persist: " + person);
    assertThat(person.getId(), is(notNullValue()));

    PersonSingleTable personByFind = entityManager.find(PersonSingleTable.class, person.getId());

    assertThat(person, is(equalTo(personByFind)));
}

@Test
public void persistOfCompany() throws Exception {
    CompanySingleTable company = new CompanySingleTable();
    company.setName("Name of the company");
    
    entityManager.persist(company);
    
    LOG.debug("nach dem persist: " + company);
    assertThat(company.getId(), is(notNullValue()));
    
    CompanySingleTable companyByFind = entityManager.find(CompanySingleTable.class, company.getId());
    
    assertThat(company, is(equalTo(companyByFind)));
}
}
