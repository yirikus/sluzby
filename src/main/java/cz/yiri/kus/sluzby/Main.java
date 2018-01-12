package cz.yiri.kus.sluzby;

import cz.yiri.kus.sluzby.view.ComponentTree;
import cz.yiri.kus.sluzby.model.FormModel;
import cz.yiri.kus.sluzby.model.tablemodel.CountTableModel;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.tablemodel.PersonTableModel;
import cz.yiri.kus.sluzby.model.Team;
import cz.yiri.kus.sluzby.service.Storage;
import cz.yiri.kus.sluzby.view.ColorRenderer;
import cz.yiri.kus.sluzby.view.Constants;
import cz.yiri.kus.sluzby.view.DateComponent;
import cz.yiri.kus.sluzby.view.TabbedPaneListener;
import cz.yiri.kus.sluzby.view.ToolBarListener;
import cz.yiri.kus.sluzby.model.tablemodel.MainTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author terrmith
 */
public class Main {

	private static final ComponentTree components = new ComponentTree();
	private static final String VERSION = "2.4.0";
	//model
	private static FormModel model = new FormModel();

	public static void main(String[] args) {

		initModel();
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void initModel() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH,1);
		model.setDate(date);
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		try {
			createFrame();
			TabbedPaneListener tabbedPaneListener = new TabbedPaneListener(components, model);
			ToolBarListener toolbarListener = new ToolBarListener(components, model);

			//////old tab
			JTable oldTable = createOldTable();
			components.setOldTable(oldTable);

			//// count tab
			JTable countTable = createCountTable();
			components.setCountTable(countTable);

			//////young tab
			JTable youngTable = createYoungTable();
			components.setYoungTable(youngTable);

			//// main table tab
			JTable table = createMainTable();
			components.setMainTable(table);

			//layout
			createLayout(tabbedPaneListener, toolbarListener, oldTable, countTable, youngTable, table);

			//load
			load(oldTable, youngTable, table);

			setRenderers(table);

			//
			components.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//Display the window.
			components.getFrame().pack();
			components.getFrame().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(components.getFrame(),
			                              e.getMessage(),
			                              "Chyba!",
			                              JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void setRenderers(JTable table) {
		Main.setCellRendererAsComboBox(2, table, model.getOld());
		Main.setCellRendererAsComboBox(3, table, model.getYoung());

		List<Person> youngAndOld2 = new ArrayList<Person>();
		youngAndOld2.addAll(model.getOld());
		youngAndOld2.addAll(model.getYoung());
		Main.setCellRendererAsComboBox(4, table, youngAndOld2);

		//setRenderers
		TableColumn oldColumn = table.getColumnModel().getColumn(2);
		TableColumn youngColumn = table.getColumnModel().getColumn(3);
		TableCellRenderer oldRenderer = new ColorRenderer((List<Person>) model.getOld(), Team.OLD);
		TableCellRenderer youngRenderer = new ColorRenderer((List<Person>) model.getYoung(), Team.YOUNG);
		oldColumn.setCellRenderer(oldRenderer);
		youngColumn.setCellRenderer(youngRenderer);
	}

	private static void load(JTable oldTable, JTable youngTable, JTable table) {
		FormModel loadedmodel = Storage.load(model.getDate());
		System.out.println("###main: " + loadedmodel.getDays().size());

		((PersonTableModel)youngTable.getModel()).updateTable(loadedmodel.getYoung());
		((PersonTableModel)oldTable.getModel()).updateTable(loadedmodel.getOld());
		((MainTableModel)table.getModel()).updateTable(loadedmodel.getDays());
	}

	private static void createLayout(TabbedPaneListener tabbedPaneListener, ToolBarListener toolbarListener,
	                                 JTable oldTable, JTable countTable, JTable youngTable, JTable table) {
		//////scrollers
		JScrollPane scrollPane = new JScrollPane(table);
		JScrollPane oldScrollPane = new JScrollPane(oldTable);
		JScrollPane youngScrollPane = new JScrollPane(youngTable);
		JScrollPane countScrollPane = new JScrollPane(countTable);

		//////toolbar
		JToolBar toolBar = new JToolBar("Tools");
		addComponents(toolBar, toolbarListener);

		//////tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		components.setTabbedPane(tabbedPane);
		tabbedPane.add("tabulka", scrollPane);
		tabbedPane.add("počet služeb", countScrollPane);
		tabbedPane.add("starší", oldScrollPane);
		tabbedPane.add("mladší", youngScrollPane);

		tabbedPane.addChangeListener(tabbedPaneListener);

		//////frame
		components.getFrame().add(toolBar, BorderLayout.PAGE_START);
		components.getFrame().add(tabbedPane, BorderLayout.CENTER);
		components.setToolBar(toolBar);
	}

	private static JTable createMainTable() {
		JTable table = new JTable();
		MainTableModel tableModel = new MainTableModel(model);
		table.setModel(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);


		setCellRendererAsComboBox(2, table, model.getOld());
		setCellRendererAsComboBox(3, table, model.getYoung());
		List<Person> youngAndOld = new ArrayList<Person>();
		youngAndOld.addAll(model.getOld());
		youngAndOld.addAll(model.getYoung());
		setCellRendererAsComboBox(4, table, youngAndOld);
		return table;
	}

	private static JTable createYoungTable() {
		TableColumn column;
		JTable youngTable = new JTable();
		PersonTableModel youngModel = new PersonTableModel(model.getYoung());
		youngTable.setModel(youngModel);

		column = youngTable.getColumnModel().getColumn(0);
		column.setMaxWidth(120);
		column = youngTable.getColumnModel().getColumn(1);
		column.setMaxWidth(80);

		youngTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		youngTable.setFillsViewportHeight(true);
		return youngTable;
	}

	private static JTable createCountTable() {
		JTable countTable = new JTable();
		CountTableModel countModel = new CountTableModel(model);
		countTable.setModel(countModel);
		countTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		countTable.setFillsViewportHeight(true);
		return countTable;
	}

	private static JTable createOldTable() {
		JTable oldTable = new JTable();
		PersonTableModel oldModel = new PersonTableModel(model.getOld());
		oldTable.setModel(oldModel);
		oldTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		oldTable.setFillsViewportHeight(true);
		TableColumn column = oldTable.getColumnModel().getColumn(0);
		column.setMaxWidth(120);
		column = oldTable.getColumnModel().getColumn(1);
		column.setMaxWidth(80);
		return oldTable;
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Sluzby " + VERSION);
		frame.setPreferredSize(new Dimension(800, 640));
		frame.setLayout(new BorderLayout());
		components.setFrame(frame);
		return frame;
	}

	/**
	 * Adds various components to the toolbar
	 *
	 */
	private static void addComponents(JToolBar toolBar, ActionListener listener) {
		components.setCreateButton(addButtonToToolbar(toolBar, "Nový", Constants.CREATE, listener));
		components.setProcessButton(addButtonToToolbar(toolBar, "Spočítat", Constants.PROCESS, listener));
		components.setWriteButton(addButtonToToolbar(toolBar, "Dokument k odeslání", Constants.WRITE, listener));
		components.setSaveButton(addButtonToToolbar(toolBar, "Uložit", Constants.SAVE, listener));
		components.setLoadButton(addButtonToToolbar(toolBar, "Nahrát", Constants.SAVE, listener));
		components.setPrevCalendar(addButtonToToolbar(toolBar, "<", Constants.PREV_CALENDAR, listener));
		addDateToToolbar(toolBar);
		components.setNextCalendar(addButtonToToolbar(toolBar, ">", Constants.NEXT_CALENDAR, listener));


		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		toolBar.add(sep);

		JButton addButton = new JButton("Přidej doktora");
		addButton.addActionListener(listener);
		addButton.setActionCommand(Constants.ADD);
		components.setAddButton(addButton);

		JTextField doctorNameField = new JTextField();
		doctorNameField.setPreferredSize(new Dimension(50, 10));
		doctorNameField.setActionCommand(Constants.ADD);
		doctorNameField.addActionListener(listener);
		components.setDoctorNameField(doctorNameField);
	}

	private static JLabel addDateToToolbar(JToolBar toolBar) {
		DateComponent dateField = new DateComponent();
		components.setDateField(dateField);
		dateField.updateText(model);
		toolBar.add(dateField);
		return dateField;
	}

	private static JButton addButtonToToolbar(JToolBar toolBar, String label, String action, ActionListener listener) {
		JButton newButton = new JButton(label);
		newButton.setActionCommand(action);
		newButton.addActionListener(listener);
		toolBar.add(newButton);
		return newButton;
	}

	public static void setCellRendererAsComboBox(int i, JTable table, Collection<Person> persons) {
		TableColumn column = table.getColumnModel().getColumn(i);
		JComboBox<String> comboBox = new JComboBox<String>();
		// empty, so it is possible to remove a person
		comboBox.addItem("");
		for (Person p : persons) {
			comboBox.addItem(p.getName());
		}
		column.setCellEditor(new DefaultCellEditor(comboBox));
	}
}
