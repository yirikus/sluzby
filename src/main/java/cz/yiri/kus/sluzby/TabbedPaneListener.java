package cz.yiri.kus.sluzby;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TabbedPaneListener
  implements ChangeListener {
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

	public TabbedPaneListener(JToolBar toolBar, JButton processButton, JButton saveButton, JButton loadButton,
	                          JButton addButton, JTextField textField, PersonTableModel old, PersonTableModel young,
	                          CountTableModel counts, JTable mainTable) {
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
				this.toolBar.remove(this.textField);
				this.toolBar.remove(this.addButton);
				Main.setCellRendererAsComboBox(2, this.mainTable, this.old.getPersons());
				Main.setCellRendererAsComboBox(3, this.mainTable, this.young.getPersons());
				List<Person> youngAndOld = new ArrayList();
				youngAndOld.addAll(this.old.getPersons());
				youngAndOld.addAll(this.young.getPersons());
				Main.setCellRendererAsComboBox(4, this.mainTable, youngAndOld);
				break;
			case 1:
				this.toolBar.remove(this.textField);
				this.toolBar.remove(this.addButton);

				this.counts.updateTable(this.old.getPersons(), this.young.getPersons());
				break;
			case 2:
			case 3:
				this.toolBar.remove(this.textField);
				this.toolBar.remove(this.addButton);
				this.toolBar.add(this.addButton);
				this.toolBar.add(this.textField);
		}

		this.toolBar.revalidate();
		this.toolBar.repaint();
	}
}