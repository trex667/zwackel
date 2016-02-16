package org.schreibvehler.v1;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.schreibvehler.boundary.*;

@Stateless
public class UserServiceV1 implements UserService {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<User> findAllUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV1 u", User.class);
        
        return query.getResultList();
    }

    @Override
    public void ensureTestData() {
        for(int i=0; i<1000;i++){
            UserV1 user = new UserV1();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setAddresses(createAddresses());
            user.setOrganizations(getOrganizations());
            
            em.persist(user);
        }
    }

    private List<OrganizationV1> getOrganizations() {
        // TODO Auto-generated method stub
        return null;
    }

    private List<AddressV1> createAddresses() {
        List<AddressV1> result = new ArrayList<>();
        
        for(int i=0; i<10; i++){
            AddressV1 address = new AddressV1();
            address.setCity(DataUtils.getRandomCity());
            address.setCountry("Germany");
            address.setStreet(RandomStringUtils.randomAlphabetic(14));
            address.setPostCode(new Integer(RandomStringUtils.random(5, "123456789")));
            result.add(address);
        }
        return result;
    }

}
