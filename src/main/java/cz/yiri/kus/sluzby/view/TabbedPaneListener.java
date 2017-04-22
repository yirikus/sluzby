package cz.yiri.kus.sluzby.view;

import cz.yiri.kus.sluzby.Main;
import cz.yiri.kus.sluzby.model.tablemodel.CountTableModel;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.tablemodel.PersonTableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author terrmith
 */
public class TabbedPaneListener implements ChangeListener {

    private JToolBar toolBar;
    private JButton processButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;
    private JTextField textField;
    private PersonTableModel old;
    private PersonTableModel young;
    private CountTableModel counts;
    private JTable mainTable;

    public TabbedPaneListener(JToolBar toolBar,
            JButton processButton,
            JButton saveButton,
            JButton loadButton,
            JButton addButton,
            JTextField textField,
            PersonTableModel old,
            PersonTableModel young,
            CountTableModel counts,
            JTable mainTable) {

        this.addButton = addButton;
        this.loadButton = loadButton;
        this.processButton = processButton;
        this.saveButton = saveButton;
        this.textField = textField;
        this.toolBar = toolBar;
        this.counts = counts;
        this.old = old;
        this.young = young;
        this.mainTable = mainTable;
    }

    public void stateChanged(ChangeEvent e) {
        JTabbedPane source = (JTabbedPane) e.getSource();
        int index = source.getSelectedIndex();

        switch (index) {
            case 0:
                toolBar.remove(textField);
                toolBar.remove(addButton);
                Main.setCellRendererAsComboBox(2, mainTable, old.getPersons());
                Main.setCellRendererAsComboBox(3, mainTable, young.getPersons());
                List<Person> youngAndOld = new ArrayList<Person>();
                youngAndOld.addAll(old.getPersons());
                youngAndOld.addAll(young.getPersons());
                Main.setCellRendererAsComboBox(4, mainTable, youngAndOld);
                break;
            case 1:
                toolBar.remove(textField);
                toolBar.remove(addButton);

                counts.updateTable(old.getPersons(), young.getPersons());
                break;
            case 2:
            case 3:
                toolBar.remove(textField);
                toolBar.remove(addButton);
                toolBar.add(addButton);
                toolBar.add(textField);
                break;
        }
        toolBar.revalidate();
        toolBar.repaint();

    }
}
