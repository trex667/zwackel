/*
 * UIUtils.java
 *
 * created at 18.02.2016 by froehlich <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package org.schreibvehler.presentation;


import org.schreibvehler.boundary.User;
import org.vaadin.viritin.fields.MTable;


public class UIUtils
{
    public static MTable<User> createUserTable()
    {
        return new MTable<>(User.class).withHeight("800px").withWidth("500px").withProperties("name", "birthdate").withColumnHeaders("name", "birth date");
    }
}
