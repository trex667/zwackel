package org.schreibvehler.complexV1;


import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.Result;
import org.vaadin.viritin.label.RichText;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


@CDIView("OrganizationStructureView")
public class OrganizationStructureViewComplexV1 extends VerticalLayout implements View
{

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    private OrganizationStructureServiceComplexV1 orgStructureService;


    @PostConstruct
    public void init()
    {
        setMargin(true);
        setSpacing(true);
    }


    @Override
    public void enter(ViewChangeEvent event)
    {
        removeAllComponents();
        Page.getCurrent().setTitle("OrganizationStructureView");

        Result<OrganizationStructureComplexV1> orgStructureResult = orgStructureService.getCompleteStructure();
        Label timeInterval = new Label(String.format("getCompleteStructure() of %d datasets needs %d [ms]",
                                                     orgStructureResult.getList().size(),
                                                     orgStructureResult.getTimeInterval().getEnd() - orgStructureResult.getTimeInterval()
                                                                                                                       .getStart()),
                                       ContentMode.HTML);

        addComponents(new RichText().withMarkDownResource("/OrganizationStructureComplexV1.md"), timeInterval);

        createStructure(orgStructureResult.getList());
    }


    private void createStructure(Collection<OrganizationStructureComplexV1> list)
    {
        if (!list.isEmpty())
        {
            Label facility = new Label(list.stream()
                                           .filter(o -> o.getParent().getType() == OrganizationComplexV1.Type.FACILITY)
                                           .findFirst()
                                           .get()
                                           .getParent()
                                           .toString());
            addComponent(facility);
        }
    }

}
