package org.zwackel.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class PersonTablePerClassTest extends TestBase {
    @Test
    public void persistOfHumanBeing() throws Exception {
        HumanBeingTablePerClass person = new HumanBeingTablePerClass();
        person.setFirstName("Petrosilius");
        person.setLastName("Zwackelmann");
        person.setBirthdate(DateUtils.parseDate("14.12.1969", "dd.mm.yyyy"));

        entityManager.persist(person);

        LOG.debug("nach dem persist: " + person);
        assertThat(person.getId()).isNotNull();

        HumanBeingTablePerClass personByFind = entityManager.find(HumanBeingTablePerClass.class, person.getId());

        assertThat(person).isEqualTo(personByFind);
    }

    @Test
    public void persistOfCompany() throws Exception {
        CompanyTablePerClass company = new CompanyTablePerClass();
        company.setName("name of company");

        entityManager.persist(company);

        LOG.debug("nach dem persist: " + company);
        assertThat(company.getId()).isNotNull();

        CompanyTablePerClass companyByFind = entityManager.find(CompanyTablePerClass.class, company.getId());

        assertThat(company).isEqualTo(companyByFind);
    }

    @Test
    public void queryOfAllPersons() throws Exception {
        // das geht! Hier wird ein union all über die beiden Kindsklassen gemacht
        List<PersonTablePerClass> result = entityManager.createQuery("select p from PersonTablePerClass p",
                PersonTablePerClass.class).getResultList();
        for (PersonTablePerClass element : result) {
            LOG.debug(element.toString());
        }
    }
}
