package org.schreibvehler.v1;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;


@CDIView("V1")
public class UserViewV1 extends HorizontalLayout implements View
{

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    private UserService userService;

     @PostConstruct
    public void init()
    {
        setMargin(true);
        setSpacing(true);

    }


    @Override
    public void enter(ViewChangeEvent event)
    {
        MTable<User> userTable = UIUtils.createUserTable();
        
        Layout leftPart = UIUtils.createFieldAndButton(userService, userTable);
        
        userTable.setBeans(userService.findAllUsers());

        addComponent(userTable);
        addComponent(leftPart);
    }

}
