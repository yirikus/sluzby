package cz.yiri.kus.sluzby.view;

import cz.yiri.kus.sluzby.model.FormModel;
import cz.yiri.kus.sluzby.model.tablemodel.CountTableModel;
import cz.yiri.kus.sluzby.model.tablemodel.MainTableModel;
import cz.yiri.kus.sluzby.model.tablemodel.PersonTableModel;
import cz.yiri.kus.sluzby.service.HarmonogramExporter;
import cz.yiri.kus.sluzby.service.HarmonogramFactory;
import cz.yiri.kus.sluzby.Main;
import cz.yiri.kus.sluzby.service.Storage;
import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author terrmith
 */
public class ToolBarListener implements ActionListener {

	private final FormModel model;
	private final ComponentTree components;
	private boolean tableCreated;
	private HarmonogramExporter exporter;


	public ToolBarListener(ComponentTree components, FormModel model) {
		this.model = model;
		this.components = components;
	}

	/**
	 * Action handler for buttons on toolbar
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			//vytvor tabulku pro novy mesic
			MainTableModel mainModel = (MainTableModel)components.getMainTable().getModel();
			if (Constants.CREATE.equals(e.getActionCommand())) {
				mainModel.updateTable((List<Day>) HarmonogramFactory
				  .newHarmonogram(model.getYoung(), model.getOld()));
				tableCreated = false;
				System.out.println(tableCreated);

				//vypocitej harmonogram pro aktualni mesic
			} else if (Constants.PROCESS.equals(e.getActionCommand())) {
				if (tableCreated) {
					System.out.println("option 1 - " + tableCreated);
					mainModel.updateTable(
					  (List<Day>) HarmonogramFactory.newHarmonogram(model.getYoung(), model.getOld()));
					mainModel.updateTable((List<Day>) HarmonogramFactory
					  .fillHarmonogram(model.getDays(), model.getYoung(), model.getOld()));

				} else {
					System.out.println("option 2 - " + tableCreated);
					mainModel.updateTable((List<Day>) HarmonogramFactory
					  .fillHarmonogram(mainModel.getDays(), model.getYoung(), model.getOld()));
					tableCreated = true;
				}
			} else if (Constants.WRITE.equals(e.getActionCommand())) {
				exporter = new HarmonogramExporter(mainModel.getDays(), "tabulka");
				exporter.exportAsHTML();
				exporter.exportAsODT();
				//uloz
			} else if (Constants.SAVE.equals(e.getActionCommand())) {
				Storage.save(model);
			} else if (Constants.PREV_CALENDAR.equals(e.getActionCommand())) {
				Storage.save(model);
				model.getDate().add(Calendar.MONTH, -1);
				components.getDateField().updateText(model);
				load();
				//update counts in case we are on counts tab
				((CountTableModel)components.getCountTable().getModel()).updateTable(model);
			} else if (Constants.NEXT_CALENDAR.equals(e.getActionCommand())) {
				Storage.save(model);
				model.getDate().add(Calendar.MONTH, 1);
				components.getDateField().updateText(model);
				load();
				//update counts in case we are on counts tab
				((CountTableModel)components.getCountTable().getModel()).updateTable(model);
			} else if (Constants.LOAD.equals(e.getActionCommand())) {
				load();
			} else if (Constants.ADD.equals(e.getActionCommand())) {
				PersonTableModel youngModel = (PersonTableModel)components.getYoungTable().getModel();
				PersonTableModel oldModel = (PersonTableModel)components.getOldTable().getModel();
				System.out.println("toolBarListener.ADD");
				int index = components.getTabbedPane().getSelectedIndex();
				String name = components.getDoctorNameField().getText();
				if (index == 2) {
					oldModel.addPerson(new Person(name, Team.OLD));
				} else if (index == 3) {
					youngModel.addPerson(new Person(name, Team.YOUNG));
				}

			}
		} catch (RuntimeException ex) {
			// write to file
			ex.printStackTrace();
			JOptionPane.showMessageDialog(components.getFrame(),
			                              ex.getMessage(),
			                              "Chyba!",
			                              JOptionPane.ERROR_MESSAGE);

		}
	}

	private void load() {
		MainTableModel mainModel = (MainTableModel)components.getMainTable().getModel();
		FormModel loadedModel = Storage.load(model.getDate());
		PersonTableModel youngModel = (PersonTableModel)components.getYoungTable().getModel();
		PersonTableModel oldModel = (PersonTableModel)components.getOldTable().getModel();
		if (loadedModel.getYoung() != null && !loadedModel.getYoung().isEmpty()) {
			youngModel.updateTable(loadedModel.getYoung());
		}
		if (loadedModel.getOld() != null && !loadedModel.getOld().isEmpty()) {
			oldModel.updateTable(loadedModel.getOld());
		}
		mainModel.updateTable(loadedModel.getDays());

		Main.setCellRendererAsComboBox(2, components.getMainTable(), model.getOld());
		Main.setCellRendererAsComboBox(3, components.getMainTable(), model.getYoung());
		List<Person> youngAndOld = new ArrayList<Person>();
		youngAndOld.addAll(model.getOld());
		youngAndOld.addAll(model.getYoung());
		Main.setCellRendererAsComboBox(4, components.getMainTable(), youngAndOld);
	}

}
