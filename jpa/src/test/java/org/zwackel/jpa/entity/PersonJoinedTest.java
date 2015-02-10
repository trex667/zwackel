package org.zwackel.jpa.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class PersonJoinedTest extends TestBase {
    @Test
    public void persistOfHumanBeing() throws Exception {
        HumanBeingJoined person = new HumanBeingJoined();
        person.setFirstName("Petrosilius");
        person.setLastName("Zwackelmann");
        person.setBirthdate(DateUtils.parseDate("14.12.1969", "dd.mm.yyyy"));

        entityManager.persist(person);

        LOG.debug("nach dem persist: " + person);
        assertThat(person.getId(), is(notNullValue()));

        HumanBeingJoined personByFind = entityManager.find(HumanBeingJoined.class, person.getId());

        assertThat(person, is(equalTo(personByFind)));
    } 

    @Test
    public void persistOfCompany() throws Exception {
        CompanyJoined company = new CompanyJoined();
        company.setName("name of company");

        entityManager.persist(company);
        
        LOG.debug("nach dem persist: " + company);
        assertThat(company.getId(), is(notNullValue()));
        
        CompanyJoined companyByFind = entityManager.find(CompanyJoined.class, company.getId());
        
        assertThat(company, is(equalTo(companyByFind)));
    }
    
    @Test
    public void queryOfAllPersons() throws Exception {
        List<PersonJoined> result = entityManager.createQuery("select p from PersonJoined p", PersonJoined.class).getResultList();
        for(PersonJoined element : result){
            LOG.debug(element.toString());
        }
    }
}
