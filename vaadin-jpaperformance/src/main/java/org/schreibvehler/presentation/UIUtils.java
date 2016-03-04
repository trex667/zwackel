/*
 * UIUtils.java
 *
 * created at 18.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.presentation;

import java.util.Collection;

import org.schreibvehler.boundary.*;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.*;

public class UIUtils {
    public MTable<User> createUserTable() {
        MTable<User> table = new MTable<>(User.class).withHeight("700px").withWidth("500px")
                .withProperties("id", "name", "birthdate").withColumnHeaders("id", "name", "birth date");
        table.setMultiSelectMode(MultiSelectMode.SIMPLE);
        return table;
    }

    public MTable<Address> createaddressTable(Collection<Address> addresses) {
        MTable<Address> table = new MTable<>(Address.class).withHeight("400px").withWidth("400px")
                .withProperties("id", "street", "postCode", "city", "country")
                .withColumnHeaders("id", "Street", "post code", "City", "Country");
        table.setBeans(addresses);
        return table;
    }

    public MTable<Organization> createOrganizationTable(Collection<Organization> orgs) {
        MTable<Organization> table = new MTable<>(Organization.class).withHeight("400px").withWidth("400px")
                .withProperties("id", "name").withColumnHeaders("id", "Organization name");
        table.setBeans(orgs);
        return table;
    }

    public Layout createFieldAndButton(UserService userService, MTable<User> userTable) {
        VerticalLayout leftPart = new VerticalLayout();
        leftPart.setMargin(true);
        leftPart.setSpacing(true);
        TextField numberOfUsers = new TextField("Number of users to create", "200");
        leftPart.addComponent(numberOfUsers);
        Button createDataButton = new Button("create new users (can take some time...)");
        createDataButton.addClickListener(e -> {
            userTable.addBeans(userService.createTestData(Integer.parseInt(numberOfUsers.getValue())).getList());
        });
        leftPart.addComponent(createDataButton);

        return leftPart;
    }
}
