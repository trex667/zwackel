package org.schreibvehler.presentation;

import com.vaadin.cdi.CDIUI;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

@CDIUI
public class WelcomePage extends UI {

    @Inject
    UserService userService;

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
        userService.removeAllUsers();
    }

    private void createUsers() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setFirstName(RandomStringUtils.randomAlphabetic(10));
            user.setLastName(RandomStringUtils.randomAlphabetic(10));
            user.setShortName(RandomStringUtils.randomAlphanumeric(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.valueOf(RandomStringUtils.randomNumeric(2)) * -1));

            Address address = new Address();
            address.setCity("Trier");
            address.setStreet("Gartenstr. 7a");
            address.setZipCode("54293");

            user.setAddress(address);
            
//            Accessibility acc1 = new Accessibility();
//            acc1.setType(AccessibilityType.email);
//            acc1.setValue("blub@gmail.com");
//            user.addAccessibility(acc1);
//            acc1 = new Accessibility();
//            acc1.setType(AccessibilityType.phone);
//            acc1.setValue("+49 651 123456789-" + i);
//            user.addAccessibility(acc1);

            userService.createUser(user);
        }
    }
}
