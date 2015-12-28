package org.schreibvehler.presentation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.layouts.*;

import com.vaadin.cdi.*;
import com.vaadin.data.util.*;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;

@UIScoped
@CDIView("users")
@ViewMenuItem(order = ViewMenuItem.BEGINNING, icon = FontAwesome.USER)
public class UserView extends CssLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    UserService userService;

    private Grid grid;

    @Inject
    UserDetailForm form;

    @PostConstruct
    public void init() {
        userService.removeAllUsers();
        userService.ensureDemoData();
        grid = createUserGrid();

        addComponents(new MVerticalLayout(new MHorizontalLayout(grid, form)));
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    private Grid createUserGrid() {
        BeanItemContainer<User> dataSource = new BeanItemContainer<User>(User.class, userService.findAll());
        Grid grid = new Grid("users", dataSource);
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addItemClickListener(e -> {
            entrySelected(e);
        });
        return grid;
    }

    private void entrySelected(ItemClickEvent e) {
        BeanItem<User> item = (BeanItem<User>) e.getItem();
        form.setEntity(item.getBean());

    }

}
