/*
 * UIUtils.java
 *
 * created at 18.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.presentation;


import org.schreibvehler.boundary.*;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.ui.*;


public class UIUtils
{
    public static MTable<User> createUserTable()
    {
        return new MTable<>(User.class).withHeight("700px").withWidth("500px").withProperties("name", "birthdate").withColumnHeaders("name", "birth date");
    }
    
    public static Layout createFieldAndButton(UserService userService, MTable<User> userTable){
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
