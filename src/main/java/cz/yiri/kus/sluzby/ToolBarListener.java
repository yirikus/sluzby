package cz.yiri.kus.sluzby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class ToolBarListener
  implements ActionListener {
	private JTabbedPane tabbedPane;
	private JTextField textField;
	private PersonTableModel youngModel;
	private PersonTableModel oldModel;
	private MainTableModel mainModel;
	private boolean tableCreated;
	private JTable mainTable;
	private HarmonogramExporter exporter;

	public ToolBarListener(JTabbedPane tabbedPane, JTextField textField, PersonTableModel youngModel,
	                       PersonTableModel oldModel, MainTableModel mainModel, JTable mainTable) {
		this.tabbedPane = tabbedPane;
		this.textField = textField;
		this.oldModel = oldModel;
		this.youngModel = youngModel;
		this.mainModel = mainModel;
		this.mainTable = mainTable;
	}


	public void actionPerformed(ActionEvent e) {
		if ("CREATE".equals(e.getActionCommand())) {
			this.mainModel.updateTable(
			  (List) HarmonogramFactory.newHarmonogram(this.youngModel.getPersons(), this.oldModel.getPersons()));
			this.tableCreated = false;
			System.out.println(this.tableCreated);


		} else if ("PROCESS".equals(e.getActionCommand())) {
			if (this.tableCreated) {
				System.out.println("option 1 - " + this.tableCreated);
				this.mainModel.updateTable(
				  (List) HarmonogramFactory.newHarmonogram(this.youngModel.getPersons(), this.oldModel.getPersons()));
				this.mainModel.updateTable((List) HarmonogramFactory
				  .fillHarmonogram(this.mainModel.getDays(), this.youngModel.getPersons(), this.oldModel.getPersons()));
			} else {
				System.out.println("option 2 - " + this.tableCreated);
				this.mainModel.updateTable((List) HarmonogramFactory
				  .fillHarmonogram(this.mainModel.getDays(), this.youngModel.getPersons(), this.oldModel.getPersons()));
				this.tableCreated = true;
			}
		} else if ("WRITE".equals(e.getActionCommand())) {
			this.exporter = new HarmonogramExporter(this.mainModel.getDays(), "tabulka");
			this.exporter.exportAsHTML();
			this.exporter.exportAsODT();


		} else if ("SAVE".equals(e.getActionCommand())) {
			Storage.save(this.youngModel.getPersons(), this.oldModel.getPersons(), this.mainModel.getDays());
		} else if ("LOAD".equals(e.getActionCommand())) {
			List<Person> loadedYoung = new ArrayList();
			List<Person> loadedOld = new ArrayList();
			List<Day> loadedDays = new ArrayList();
			Storage.load(loadedYoung, loadedOld, loadedDays);

			this.youngModel.updateTable(loadedYoung);
			this.oldModel.updateTable(loadedOld);
			this.mainModel.updateTable(loadedDays);

			Main.setCellRendererAsComboBox(2, this.mainTable, this.oldModel.getPersons());
			Main.setCellRendererAsComboBox(3, this.mainTable, this.youngModel.getPersons());
			List<Person> youngAndOld = new ArrayList();
			youngAndOld.addAll(this.oldModel.getPersons());
			youngAndOld.addAll(this.youngModel.getPersons());
			Main.setCellRendererAsComboBox(4, this.mainTable, youngAndOld);
		} else if ("ADD".equals(e.getActionCommand())) {
			System.out.println("toolBarListener.ADD");
			int index = this.tabbedPane.getSelectedIndex();
			if (index == 2) {
				this.oldModel.addPerson(new Person(this.textField.getText(), Team.OLD));
			} else if (index == 3) {
				this.youngModel.addPerson(new Person(this.textField.getText(), Team.YOUNG));
			}
		}
	}
}
