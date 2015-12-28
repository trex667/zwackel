package org.schreibvehler.boundary;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class UserService {

    @Inject
    UserRepository userRepo;

    public void createUser(User user) {
        userRepo.save(user);
    }

    public User findUser(Long id) {
        return userRepo.findBy(id);
    }

    public void removeUser(Long id) {
        userRepo.remove(findUser(id));
    }

    public void removeAllUsers() {
        for (User u : findAll()) {
            userRepo.remove(u);
        }
    }

    public Collection<User> findAll() {
        return userRepo.findAll();
    }

    public User findbyShortName(String shortName) {
        User result = null;
        try {
            result = userRepo.findByShortName(shortName);
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }

    public void ensureDemoData() {
        User user = new User();
        user.setShortName("zwackel");
        user.setFirstName("Petrosilius");
        user.setLastName("Zwackelmann");
        user.setBirthdate(DateUtils.addYears(new Date(), -99));
        user.setSkills(Arrays.asList("zaubern, schimpfen, fliegen"));

        if (findbyShortName("zwackel") == null) {
            createUser(user);
        }

        user = new User();
        user.setShortName("hotzenplotz");
        user.setFirstName("Räuber");
        user.setLastName("Hotzenplotz");
        user.setBirthdate(DateUtils.addYears(new Date(), -42));
        user.setSkills(Arrays.asList("stehlen, schießen, essen"));

        if (findbyShortName("hotzenplotz") == null) {
            createUser(user);
        }
    }
}
