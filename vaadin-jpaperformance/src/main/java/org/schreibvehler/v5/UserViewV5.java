package org.schreibvehler.v5;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

@CDIView("V5")
public class UserViewV5 extends HorizontalLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @EJB(beanName = "UserServiceV5")
    private UserService userService;

    @Inject
    private UIUtils uiUtils;

    private Result<User> userResult;

    @PostConstruct
    public void init() {
        setMargin(true);
        setSpacing(true);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        Page.getCurrent().setTitle("V5");
        userResult = userService.findAllUsers();
        Label timeInterval = new Label(
                String.format("<h3>findAllUsers() of %d datasets needs %d [ms]</h3>", userResult.getList().size(),
                        userResult.getTimeInterval().getEnd() - userResult.getTimeInterval().getStart()),
                ContentMode.HTML);
        MTable<User> userTable = uiUtils.createUserTable();

        Layout leftPart = uiUtils.createFieldAndButton(userService, userTable);

        userTable.setBeans(userResult.getList());
        userTable.addItemClickListener(e -> {
            openDetailDialog(e);
        });
        addComponent(new VerticalLayout(timeInterval, userTable));
        addComponent(leftPart);
    }

    private void openDetailDialog(ItemClickEvent e) {
        Integer userId = (Integer) e.getItem().getItemProperty("id").getValue();

        Result<Organization> organizationResult = userService.findAllOrganizations(userId);

        User user = userResult.getList().stream().filter(u -> u.getId().equals(userId)).findFirst().get();
        Label addressTimeInterval = new Label(
                String.format("<h3>Addresses of %d datasets are already fetched with findAllUsers()</h3>",
                        user.getAddresses() == null ? 0 : user.getAddresses().size()),
                ContentMode.HTML);
        Label organizationTimeInterval = new Label(String.format(
                "<h3>findAllOrganizations() of %d datasets needs %d [ms]</h3>", organizationResult.getList().size(),
                organizationResult.getTimeInterval().getEnd() - organizationResult.getTimeInterval().getStart()),
                ContentMode.HTML);

        Window dialog = new Window("User details");
        dialog.setModal(true);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        TabSheet sheet = new TabSheet();
        MTable<Address> addressTable = uiUtils.createaddressTable(user.getAddresses());
        sheet.addTab(new VerticalLayout(addressTimeInterval, addressTable), "Addresses");

        MTable<Organization> organizationTable = uiUtils.createOrganizationTable(organizationResult.getList());
        sheet.addTab(new VerticalLayout(organizationTimeInterval, organizationTable), "Organizations");

        layout.addComponent(new Label(String.format("Details of user (birth date): %s (%s)",
                e.getItem().getItemProperty("name").getValue().toString(),
                e.getItem().getItemProperty("birthdate").getValue().toString()), ContentMode.HTML));
        layout.addComponent(sheet);
        layout.addComponent(new Button("close", clickEvent -> {
            dialog.close();
        }));
        dialog.setContent(layout);
        UI.getCurrent().addWindow(dialog);
    }

}
