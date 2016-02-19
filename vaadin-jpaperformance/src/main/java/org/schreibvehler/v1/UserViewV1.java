package org.schreibvehler.v1;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.DynaBeanItem;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
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
        userTable.addItemClickListener(e -> {
            openDetailDialog(e);
        });
        addComponent(userTable);
        addComponent(leftPart);
    }
    
    private void openDetailDialog(ItemClickEvent e) {
     for(Object o : e.getItem().getItemPropertyIds()){
         System.out.println("huhu: " + o.toString());
     }
        Integer userId = (Integer) e.getItem().getItemProperty("id").getValue();
     
        Window dialog = new Window("User details");
        dialog.setModal(true);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        
        TabSheet sheet = new TabSheet();
        MTable<Address> addressTable = UIUtils.createaddressTable(userService.findAllAddresses(userId)); 
        sheet.addTab(addressTable, "Addresses");
        
        layout.addComponent(new Label(String.format("Details of user (birth date): %s (%s)", e.getItem().getItemProperty("name").getValue().toString(), e.getItem().getItemProperty("birthdate").getValue().toString()), ContentMode.HTML));
        layout.addComponent(sheet);
        layout.addComponent(new Button("close", clickEvent -> {dialog.close();}));
        dialog.setContent(layout);
        UI.getCurrent().addWindow(dialog);
    }

}
