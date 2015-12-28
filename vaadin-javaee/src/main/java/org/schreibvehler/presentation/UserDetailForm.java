package org.schreibvehler.presentation;

import javax.inject.Inject;

import org.schreibvehler.boundary.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.*;

import com.vaadin.ui.*;

public class UserDetailForm extends AbstractForm<User> {

    private static final long serialVersionUID = 637692556847519915L;

    private TextField shortName = new MTextField("shortName");
    private TextField firstName = new MTextField("First name");
    private TextField lastName = new MTextField("Last name");
    private DateField birthDate = new PopupDateField("Birth date");

    public static class AddressRow {
        EnumSelect<?> type = new EnumSelect<>().withWidth("6em");
        TextField street = new MTextField().withInputPrompt("street");
        TextField city = new MTextField().withInputPrompt("city").withWidth("6em");
        TextField zip = new MTextField().withInputPrompt("zip").withWidth("4em");
    }

    public static class SkillRow {
        TextField skill = new MTextField().withInputPrompt("skill");
    }

    ElementCollectionField<Address> addresses = new ElementCollectionField<>(Address.class, AddressRow.class)
            .withCaption("Addressess");

    @Inject
    UserService userService;

    @Override
    protected Component createContent() {
        return new MVerticalLayout(getToolbar(),
                new MHorizontalLayout(new MFormLayout(shortName, firstName, lastName, birthDate).withMargin(false)),
                addresses).withMargin(new MMarginInfo(false, true));
    }

}
