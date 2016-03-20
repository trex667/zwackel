package org.schreibvehler.v8;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.schreibvehler.boundary.*;
import org.schreibvehler.presentation.UIUtils;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.RichText;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView("V8")
public class UserViewV8 extends VerticalLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @EJB(beanName = "UserServiceV8")
    private UserService userService;

    @Inject
    private UIUtils uiUtils;

    private int startPosition;
    private static final int FETCH_SIZE = 50;

    @PostConstruct
    public void init() {
        setMargin(true);
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        removeAllComponents();
        startPosition = 0;
        Page.getCurrent().setTitle("V8");
        Result<User> userResult = userService.findAllUsers();
        Label timeInterval = new Label(
                getHeaderText(userResult),
                ContentMode.HTML);
        MTable<User> userTable = uiUtils.createUserTable();

        Layout rightPart = uiUtils.createFieldAndButton(userService, userTable);
        userTable.setBeans(userResult.getList());

        Button lastPage = new Button(String.format("Show last %d users", FETCH_SIZE));
        lastPage.setEnabled(false);
        lastPage.addClickListener(e -> {
            if (startPosition - FETCH_SIZE >= 0) {
                startPosition = startPosition - FETCH_SIZE;
            } else {
                startPosition = 0;
            }
            Result<User> pagingResult = userService.findAllUsers(startPosition, FETCH_SIZE);
            if (pagingResult.getList().size() > 0) {
                userTable.setBeans(pagingResult.getList());
                timeInterval.setValue(getHeaderTextForPaging(pagingResult));
            }
            if (startPosition == 0) {
                setEnabled(false);
            }
        });

        Button nextPage = new Button(String.format("Show next %d users", FETCH_SIZE));
        nextPage.setEnabled(false);
        nextPage.addClickListener(e -> {
            Result<User> pagingResult = userService.findAllUsers(startPosition, FETCH_SIZE);
            if (pagingResult.getList().size() > 0) {
                userTable.setBeans(pagingResult.getList());
                timeInterval.setValue(getHeaderTextForPaging(pagingResult));
                if (pagingResult.getList().size() < FETCH_SIZE) {
                    setEnabled(false);
                }
            } else {
                setEnabled(false);
            }
            startPosition = startPosition + FETCH_SIZE;
            if (startPosition > 0) {
                lastPage.setEnabled(true);
            }
        });

        CheckBox isPaging = new CheckBox(String.format("enable paging for table? (fetch size %d)", FETCH_SIZE), false);
        isPaging.addValueChangeListener(e -> {
            if (isPaging.getValue()) {
                Result<User> pagingResult = userService.findAllUsers(startPosition, FETCH_SIZE);
                timeInterval.setValue(getHeaderTextForPaging(pagingResult));
                userTable.setBeans(pagingResult.getList());
                if (startPosition > 0) {
                    lastPage.setEnabled(true);
                }
                nextPage.setEnabled(true);
                startPosition = startPosition + FETCH_SIZE;
            } else {
                startPosition = 0;
                Result<User> allUsers = userService.findAllUsers();
                timeInterval.setValue(getHeaderText(allUsers));
                userTable.setBeans(allUsers.getList());
                nextPage.setEnabled(false);
                lastPage.setEnabled(false);
            }
        });


        rightPart.addComponents(isPaging, new HorizontalLayout(lastPage, nextPage));

        userTable.addItemClickListener(e -> {
            openDetailDialog(e);
        });

        addComponents(new RichText().withMarkDownResource("/V8.md"), timeInterval);
        addComponents(new HorizontalLayout(userTable, rightPart));
    }

    private String getHeaderText(Result<User> userResult) {
        return String.format("findAllUsers() of %d datasets needs %d [ms]", userResult.getList().size(),
                userResult.getTimeInterval().getEnd() - userResult.getTimeInterval().getStart());
    }

    private String getHeaderTextForPaging(Result<User> pagingResult) {
        return String.format("findAllUsers(startPosition=%d, fetchSize=%d) need %d [ms]", startPosition, FETCH_SIZE, pagingResult.getTimeInterval().getEnd() - pagingResult.getTimeInterval().getStart());
    }

    private void openDetailDialog(ItemClickEvent e) {
        Integer userId = (Integer) e.getItem().getItemProperty("id").getValue();
        Result<Address> addressResult = userService.findAllAddresses(userId);
        Result<Organization> organizationResult = userService.findAllOrganizations(userId);

        Label addressTimeInterval = new Label(
                String.format("<h3>findAllAddresses() of %d datasets needs %d [ms]</h3>",
                        addressResult.getList().size(),
                        addressResult.getTimeInterval().getEnd() - addressResult.getTimeInterval().getStart()),
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
        MTable<Address> addressTable = uiUtils.createaddressTable(addressResult.getList());
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
