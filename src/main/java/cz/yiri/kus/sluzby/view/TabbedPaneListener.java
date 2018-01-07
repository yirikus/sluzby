package cz.yiri.kus.sluzby.view;

import cz.yiri.kus.sluzby.Main;
import cz.yiri.kus.sluzby.model.FormModel;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.tablemodel.CountTableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author terrmith
 */
public class TabbedPaneListener implements ChangeListener {

	private final FormModel model;
	private final ComponentTree components;

	public TabbedPaneListener(ComponentTree components,
                              FormModel model) {

	    this.model = model;
	    this.components = components;
    }

    public void stateChanged(ChangeEvent e) {
        JTabbedPane source = (JTabbedPane) e.getSource();
        int index = source.getSelectedIndex();
	    JToolBar toolBar = components.getToolBar();
        switch (index) {
            case 0:
                toolBar.remove(components.getDoctorNameField());
                toolBar.remove(components.getAddButton());
                Main.setCellRendererAsComboBox(2, components.getMainTable(), model.getOld());
                Main.setCellRendererAsComboBox(3, components.getMainTable(), model.getYoung());
                List<Person> youngAndOld = new ArrayList<Person>();
                youngAndOld.addAll(model.getOld());
                youngAndOld.addAll(model.getYoung());
                Main.setCellRendererAsComboBox(4, components.getMainTable(), youngAndOld);
                break;
            case 1:
                toolBar.remove(components.getDoctorNameField());
                toolBar.remove(components.getAddButton());
	            ((CountTableModel)components.getCountTable().getModel()).updateTable(model);
                break;
            case 2:
            case 3:
                toolBar.remove(components.getDoctorNameField());
                toolBar.remove(components.getAddButton());
                toolBar.add(components.getAddButton());
                toolBar.add(components.getDoctorNameField());
                break;
        }
        toolBar.revalidate();
        toolBar.repaint();

    }
}
