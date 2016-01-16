package org.schreibvehler.presentation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.layouts.*;

import com.vaadin.cdi.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

@UIScoped
@CDIView("users")
@ViewMenuItem(order = ViewMenuItem.BEGINNING, icon = FontAwesome.USER)
public class UserView extends CssLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    UserService userService;

    private MTable<User> entryList;

    @Inject
    UserDetailForm form;

    @PostConstruct
    public void init() {
//        userService.removeAllUsers();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        userService.ensureDemoData();
        entryList = createUserTable();
        
        entryList.addMValueChangeListener(this::entrySelected);
        form.setSavedHandler(this::entrySaved);
        
        Button addNew = new MButton(FontAwesome.PLUS, this::addNew);
        
        addComponents(new MVerticalLayout(new MHorizontalLayout(entryList, form)));

    }

    private MTable<User> createUserTable() {
        entryList = new MTable<>(User.class)
                .withHeight("450px")
                .withFullWidth()
                .withProperties("shortName", "firstName", "lastName", "birthdate")
                .withColumnHeaders("short", "first name", "last name", "birth date");
        
        loadList();
        return entryList;
    }

    private void loadList() {
        entryList.setBeans(userService.findAll());
    }
    
    private void addNew(Button.ClickEvent e) {
        entryList.setValue(null);
        editEntry(new User());
    }

    private void entrySelected(MValueChangeEvent<User> event) {
        editEntry(event.getValue());
    }
    
    public void entrySaved(User value) {
        try {
            userService.createUser(value);
            form.setVisible(false);
        } catch (Exception e) {
            Notification.show("Saving entity failed!", e.
                    getLocalizedMessage(), Notification.Type.WARNING_MESSAGE);
        }
        entryList.setValue(null);
        loadList();
    }

    private void editEntry(User entry) {
        if (entry == null) {
            form.setVisible(false);
        } else {
            boolean persisted = entry.getId() != null;
            if (persisted) {
                // reattach (in case Hibernate is in use)
//                entry = userService.loadFully(entry);
            }
            form.setEntity(entry);
            form.focusFirst();
        }
    }
}
