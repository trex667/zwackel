package org.schreibvehler.complexV1;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.schreibvehler.boundary.Result;
import org.schreibvehler.complexV1.OrganizationComplexV1.Type;
import org.vaadin.viritin.label.RichText;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

@CDIView("OrganizationStructureView")
public class OrganizationStructureViewComplexV1 extends VerticalLayout implements View {

    private static final long serialVersionUID = 7277988489611347314L;

    @Inject
    private OrganizationStructureServiceComplexV1 orgStructureService;

    @PostConstruct
    public void init() {
        setMargin(true);
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        removeAllComponents();
        Page.getCurrent().setTitle("OrganizationStructureView");

        Result<OrganizationStructureComplexV1> orgStructureResult = orgStructureService.getCompleteStructure();
        Label timeInterval = new Label(String.format("getCompleteStructure() of %d datasets needs %d [ms]",
                orgStructureResult.getList().size(),
                orgStructureResult.getTimeInterval().getEnd() - orgStructureResult.getTimeInterval().getStart()),
                ContentMode.HTML);

        addComponents(new RichText().withMarkDownResource("/OrganizationStructureComplexV1.md"), timeInterval);

        TextField numberOfDepartments = new TextField("Number of departments to create", "200");
        Button createDataButton = new Button("create new departments (can take some time...)");
        createDataButton.addClickListener(e -> {
            orgStructureService.createTestData(Integer.parseInt(numberOfDepartments.getValue()));
        });
        addComponents(numberOfDepartments, createDataButton);
        createStructure(orgStructureResult.getList());
    }

    private void createStructure(Collection<OrganizationStructureComplexV1> list) {
        if (!list.isEmpty()) {

            Set<OrganizationComplexV1> facilities = new HashSet<>();
            Iterator<OrganizationStructureComplexV1> iterator = list.stream()
                    .filter(o -> o.getParent().getType() == OrganizationComplexV1.Type.FACILITY).iterator();
            while (iterator.hasNext()) {
                facilities.add(iterator.next().getParent());
            }

            TreeTable treeTable = new TreeTable("Organization Structure");
            treeTable.addContainerProperty("Name", String.class, null);
            treeTable.addContainerProperty("Type", OrganizationComplexV1.Type.class, null);
            treeTable.addContainerProperty("Begin", Date.class, null);
            treeTable.addContainerProperty("End", Date.class, null);
            for (OrganizationComplexV1 element : facilities) {
                treeTable.addItem(
                        new Object[] { element.getName(), element.getType(), element.getBegin(), element.getEnd() },
                        element.getId());
                addChildLabels(element, list, treeTable);
            }
            addComponent(treeTable);
        }
    }

    private void addChildLabels(OrganizationComplexV1 parent, Collection<OrganizationStructureComplexV1> list,
            TreeTable treeTable) {
        for (OrganizationStructureComplexV1 element : list) {
            if (parent.equals(element.getParent())) {
                OrganizationComplexV1 child = element.getChild();
                treeTable.addItem(new Object[] { child.getName(), child.getType(), child.getBegin(), child.getEnd() },
                        child.getId());
                treeTable.setParent(child.getId(), parent.getId());
                if (child.getType() != Type.DEPARTMENT) {
                    addChildLabels(child, list, treeTable);
                }
            }
        }
    }

}
