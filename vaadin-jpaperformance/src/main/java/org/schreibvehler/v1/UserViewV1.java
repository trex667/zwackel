package org.schreibvehler.v1;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.schreibvehler.boundary.Address;
import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.Result;
import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


@CDIView("V1")
public class UserViewV1 extends HorizontalLayout implements View
{

    private static final long serialVersionUID = 7277988489611347314L;

    @EJB(beanName="UserServiceV1")
    private UserService userService;

    @Inject
    private UIUtils uiUtils;


    @PostConstruct
    public void init()
    {
        setMargin(true);
        setSpacing(true);


    }


    @Override
    public void enter(ViewChangeEvent event)
    {
        Page.getCurrent().setTitle("V1");
        Result<User> userResult = userService.findAllUsers();
        Label timeInterval = new Label(String.format("<h3>findAllUsers() of %d datasets needs %d [ms]</h3>", userResult.getList().size(), userResult.getTimeInterval().getEnd() - userResult.getTimeInterval().getStart()), ContentMode.HTML);
        MTable<User> userTable = uiUtils.createUserTable();

        Layout leftPart = uiUtils.createFieldAndButton(userService, userTable);

        userTable.setBeans(userResult.getList());
        userTable.addItemClickListener(e -> {
            openDetailDialog(e);
        });
        addComponent(new VerticalLayout(timeInterval, userTable));
        addComponent(leftPart);
    }


    private void openDetailDialog(ItemClickEvent e)
    {
        Integer userId = (Integer)e.getItem().getItemProperty("id").getValue();
        Result<Address> addressResult = userService.findAllAddresses(userId);
        Result<Organization> organizationResult = userService.findAllOrganizations(userId);

        Label addressTimeInterval = new Label(String.format("<h3>findAllAddresses() of %d datasets needs %d [ms]</h3>", addressResult.getList().size(), addressResult.getTimeInterval().getEnd() - addressResult.getTimeInterval().getStart()), ContentMode.HTML);
        Label organizationTimeInterval = new Label(String.format("<h3>findAllOrganizations() of %d datasets needs %d [ms]</h3>", organizationResult.getList().size(), organizationResult.getTimeInterval().getEnd() - organizationResult.getTimeInterval().getStart()), ContentMode.HTML);

        Window dialog = new Window("User details");
        dialog.setModal(true);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        TabSheet sheet = new TabSheet();
        MTable<Address> addressTable = uiUtils.createaddressTable(addressResult.getList());
        sheet.addTab(new VerticalLayout(addressTimeInterval, addressTable), "Addresses");

        MTable<Organization> organizationTable = uiUtils.createOrganizationTable(organizationResult.getList());
        sheet.addTab(new VerticalLayout(organizationTimeInterval, organizationTable), "Organizations");

        layout.addComponent(new Label(String.format("Details of user (birth date): %s (%s)", e.getItem().getItemProperty("name").getValue().toString(), e.getItem().getItemProperty("birthdate").getValue().toString()), ContentMode.HTML));
        layout.addComponent(sheet);
        layout.addComponent(new Button("close", clickEvent -> {
            dialog.close();
        }));
        dialog.setContent(layout);
        UI.getCurrent().addWindow(dialog);
    }

}
