package org.schreibvehler.v1;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.fields.*;

import com.vaadin.cdi.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

@UIScoped
@CDIView("users V1")
@ViewMenuItem(order = ViewMenuItem.BEGINNING, icon = FontAwesome.USER)
public class UserViewV1 extends CssLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    private UserService userService;

    private MTable<User> entryList;
    
    @PostConstruct
    public void init() {
//        userService.removeAllUsers();
        userService.ensureTestData();
        entryList = createUserTable();
        
        addComponent(entryList);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    private MTable<User> createUserTable() {
        entryList = new MTable<>(User.class)
                .withHeight("450px")
                .withFullWidth()
                .withProperties("name", "birthdate")
                .withColumnHeaders("name", "birth date");
        
        loadList();
        return entryList;
    }

    private void loadList() {
        entryList.setBeans(userService.findAllUsers());
    }
    
}
