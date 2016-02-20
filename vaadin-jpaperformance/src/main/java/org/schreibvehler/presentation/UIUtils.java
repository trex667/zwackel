/*
 * UIUtils.java
 *
 * created at 18.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.presentation;

import java.util.List;

import org.schreibvehler.boundary.*;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.*;

public class UIUtils {
    public static MTable<User> createUserTable() {
        MTable<User> table = new MTable<>(User.class).withHeight("700px").withWidth("500px")
                .withProperties("name", "birthdate").withColumnHeaders("name", "birth date");
        table.setMultiSelectMode(MultiSelectMode.SIMPLE);
        return table;
    }

    public static MTable<Address> createaddressTable(List<Address> addresses) {
        for (Address a : addresses) {
            System.out.println("city: " + a.getCity());
        }
        MTable<Address> table = new MTable<>(Address.class).withHeight("400px").withWidth("400px")
                .withProperties("street", "postCode", "city", "country")
                .withColumnHeaders("Street", "post code", "City", "Country");
        table.setBeans(addresses);
        return table;
    }

    public static MTable<Organization> createOrganizationTable(List<Organization> orgs) {
        MTable<Organization> table = new MTable<>(Organization.class).withHeight("400px").withWidth("400px")
                .withProperties("name").withColumnHeaders("Organization name");
        table.setBeans(orgs);
        return table;
    }

    public static Layout createFieldAndButton(UserService userService, MTable<User> userTable) {
        VerticalLayout leftPart = new VerticalLayout();
        leftPart.setMargin(true);
        leftPart.setSpacing(true);
        TextField numberOfUsers = new TextField("Number of users to create", "200");
        leftPart.addComponent(numberOfUsers);
        Button createDataButton = new Button("create new users (can take some time...)");
        createDataButton.addClickListener(e -> {
            userTable.addBeans(userService.createTestData(Integer.parseInt(numberOfUsers.getValue())));
        });
        leftPart.addComponent(createDataButton);

        return leftPart;
    }
}
