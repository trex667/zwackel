package org.schreibvehler.v1;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;


@CDIView("V1")
public class UserViewV1 extends VerticalLayout implements View
{

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    private UserService userService;

    private MTable<User> userTable;

    private Button createDataButton;


    @PostConstruct
    public void init()
    {
        setMargin(true);
        setSpacing(true);

    }


    @Override
    public void enter(ViewChangeEvent event)
    {
        userTable = UIUtils.createUserTable();

        createDataButton = new Button("create 1000 new users (can take some time...)");
        createDataButton.addClickListener(e -> {
            userTable.addBeans(userService.createTestData());
        });

        userTable.setBeans(userService.findAllUsers());

        addComponent(createDataButton);
        addComponent(userTable);
    }

}
