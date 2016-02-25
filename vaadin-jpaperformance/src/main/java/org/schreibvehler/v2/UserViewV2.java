package org.schreibvehler.v2;


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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


@CDIView("V2")
public class UserViewV2 extends HorizontalLayout implements View
{

    private static final long serialVersionUID = 7277988489611347314L;

    @EJB(beanName = "UserServiceV2")
    private UserService userService;

    @Inject
    private UIUtils uiUtils;

    private Result<User> userResult;


    @PostConstruct
    public void init()
    {
        setMargin(true);
        setSpacing(true);

    }


    @Override
    public void enter(ViewChangeEvent event)
    {
        userResult = userService.findAllUsers();
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
        User user = userResult.getList().stream().filter(u -> u.getId().equals(userId)).findFirst().get();
        //TODO
        Label addressTimeInterval = new Label("<h3>Addresses are already fetched with findAllUsers()</h3>", ContentMode.HTML);
        Label organizationTimeInterval = new Label("<h3>Organizations  are already fetched with findAllUsers()</h3>", ContentMode.HTML);

        Window dialog = new Window("User details");
        dialog.setModal(true);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        TabSheet sheet = new TabSheet();
        MTable<Address> addressTable = uiUtils.createaddressTable(user.getAddresses());
        sheet.addTab(new VerticalLayout(addressTimeInterval, addressTable), "Addresses");

        MTable<Organization> organizationTable = uiUtils.createOrganizationTable(user.getOrganizations());
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
