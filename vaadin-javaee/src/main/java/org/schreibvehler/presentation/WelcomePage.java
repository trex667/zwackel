package org.schreibvehler.presentation;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

import com.vaadin.cdi.CDIUI;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@CDIUI
public class WelcomePage extends UI {

    private static final long serialVersionUID = 1572152680961504715L;

    @Inject
    UserService userService;
    @Inject
    AddressService addressService;

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        HorizontalLayout content = new HorizontalLayout();

        Button newButton = new Button("create 100 new users", e -> {
            createUsers();
        });
        Button removeButton = new Button("remove all users", e -> {
            removeUsers();
        });
        content.addComponent(newButton);
        content.addComponent(removeButton);

        Table table = new Table("Users", getUserContainer());
        content.addComponent(table);
        setContent(content);
    }

    private Container getUserContainer() {
        BeanItemContainer<User> data = new BeanItemContainer<>(User.class);
        data.addAll(userService.findAll());
        return data;
    }

    private void removeUsers() {
        addressService.removeAllAdresses();
        userService.removeAllUsers();
    }

    private void createUsers() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setFirstName(RandomStringUtils.randomAlphabetic(10));
            user.setLastName(RandomStringUtils.randomAlphabetic(10));
            user.setShortName(RandomStringUtils.randomAlphanumeric(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.valueOf(RandomStringUtils.randomNumeric(2)) * -1));

            userService.createUser(user);

            for (int k = 0; k < 5; k++) {
                Address address = new Address();
                address.setUser(user);
                address.setCity(RandomStringUtils.randomAlphabetic(10));
                address.setStreet(RandomStringUtils.randomAlphabetic(10));
                address.setZipCode(RandomStringUtils.randomNumeric(5));

                addressService.createAddress(address);  
            }
        }
    }
}
