package cz.yiri.kus.sluzby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class Main {
	private static JButton processButton;
	private static JButton saveButton;
	private static JButton loadButton;
	private static JButton addButton;
	private static JButton createButton;
	private static JButton writeButton;
	private static JTextField textField;
	private static JFrame frame;
	private static String VERSION = "2.2";

	private static List<Person> loadedYoung;

	private static List<Person> loadedOld;

	private static List<Day> loadedDays;
	private static JTabbedPane tabbedPane;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}


	private static void createAndShowGUI() {
		try {
			Calendar today = Calendar.getInstance();
			String datum = today.get(2) + 1 + "/" + today.get(1);
			frame = new JFrame("Sluzby" + VERSION + ": " + datum);
			frame.setPreferredSize(new Dimension(800, 640));
			frame.setLayout(new BorderLayout());


			JTable oldTable = new JTable();
			PersonTableModel oldModel = new PersonTableModel();
			oldTable.setModel(oldModel);
			oldTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
			oldTable.setFillsViewportHeight(true);
			TableColumn column = oldTable.getColumnModel().getColumn(0);
			column.setMaxWidth(120);
			column = oldTable.getColumnModel().getColumn(1);
			column.setMaxWidth(80);


			JTable countTable = new JTable();
			CountTableModel countModel = new CountTableModel();
			countTable.setModel(countModel);
			countTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
			countTable.setFillsViewportHeight(true);


			JTable youngTable = new JTable();
			PersonTableModel youngModel = new PersonTableModel();
			youngTable.setModel(youngModel);

			column = youngTable.getColumnModel().getColumn(0);
			column.setMaxWidth(120);
			column = youngTable.getColumnModel().getColumn(1);
			column.setMaxWidth(80);

			youngTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
			youngTable.setFillsViewportHeight(true);


			JTable table = new JTable();
			MainTableModel tableModel = new MainTableModel(oldModel, youngModel);
			table.setModel(tableModel);
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			table.setFillsViewportHeight(true);


			setCellRendererAsComboBox(2, table, oldModel.getPersons());
			setCellRendererAsComboBox(3, table, youngModel.getPersons());
			List<Person> youngAndOld = new ArrayList();
			youngAndOld.addAll(oldModel.getPersons());
			youngAndOld.addAll(youngModel.getPersons());
			setCellRendererAsComboBox(4, table, youngAndOld);


			JScrollPane scrollPane = new JScrollPane(table);
			JScrollPane oldScrollPane = new JScrollPane(oldTable);
			JScrollPane youngScrollPane = new JScrollPane(youngTable);
			JScrollPane countScrollPane = new JScrollPane(countTable);


			JToolBar toolBar = new JToolBar("Tools");
			addComponents(toolBar);


			tabbedPane = new JTabbedPane();
			tabbedPane.add("tabulka", scrollPane);
			tabbedPane.add("počet služeb", countScrollPane);
			tabbedPane.add("starší", oldScrollPane);
			tabbedPane.add("mladší", youngScrollPane);
			TabbedPaneListener listener =
			  new TabbedPaneListener(toolBar, processButton, saveButton, loadButton, addButton, textField, oldModel,
			                         youngModel, countModel, table);
			tabbedPane.addChangeListener(listener);


			ToolBarListener toolbarListener =
			  new ToolBarListener(tabbedPane, textField, youngModel, oldModel, tableModel, table);
			addButton.addActionListener(toolbarListener);
			loadButton.addActionListener(toolbarListener);
			saveButton.addActionListener(toolbarListener);
			writeButton.addActionListener(toolbarListener);
			processButton.addActionListener(toolbarListener);
			createButton.addActionListener(toolbarListener);
			textField.addActionListener(toolbarListener);

			frame.add(toolBar, "First");
			frame.add(tabbedPane, "Center");


			loadedYoung = new ArrayList();
			loadedOld = new ArrayList();
			loadedDays = new ArrayList();
			Storage.load(loadedYoung, loadedOld, loadedDays);
			System.out.println("###main: " + loadedDays.size());

			youngModel.updateTable(loadedYoung);
			oldModel.updateTable(loadedOld);
			tableModel.updateTable(loadedDays);

			setCellRendererAsComboBox(2, table, oldModel.getPersons());
			setCellRendererAsComboBox(3, table, youngModel.getPersons());

			List<Person> youngAndOld2 = new ArrayList();
			youngAndOld.addAll(oldModel.getPersons());
			youngAndOld.addAll(youngModel.getPersons());
			setCellRendererAsComboBox(4, table, youngAndOld2);


			TableColumn oldColumn = table.getColumnModel().getColumn(2);
			TableColumn youngColumn = table.getColumnModel().getColumn(3);
			TableCellRenderer oldRenderer = new ColorRenderer((List) oldModel.getPersons(), Team.OLD);
			TableCellRenderer youngRenderer = new ColorRenderer((List) youngModel.getPersons(), Team.YOUNG);
			oldColumn.setCellRenderer(oldRenderer);
			youngColumn.setCellRenderer(youngRenderer);


			frame.setDefaultCloseOperation(3);


			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Chyba!", 0);
		}
	}


	private static void addComponents(JToolBar toolBar) {
		createButton = new JButton("Nový");
		createButton.setActionCommand("CREATE");
		toolBar.add(createButton);

		processButton = new JButton("Spočítat");
		processButton.setActionCommand("PROCESS");
		toolBar.add(processButton);

		writeButton = new JButton("Dokument k odeslání");
		writeButton.setActionCommand("WRITE");
		toolBar.add(writeButton);

		saveButton = new JButton("Uložit");
		saveButton.setActionCommand("SAVE");
		toolBar.add(saveButton);

		loadButton = new JButton("Nahrát");
		loadButton.setActionCommand("LOAD");
		toolBar.add(loadButton);

		JSeparator sep = new JSeparator(1);
		toolBar.add(sep);

		addButton = new JButton("Přidej doktora");
		addButton.setActionCommand("ADD");


		textField = new JTextField();
		textField.setPreferredSize(new Dimension(50, 10));
		textField.setActionCommand("ADD");
	}

	public static void setCellRendererAsComboBox(int i, JTable table, Collection<Person> persons) {
		TableColumn column = table.getColumnModel().getColumn(i);
		JComboBox comboBox = new JComboBox();
		for (Person p : persons) {
			comboBox.addItem(p.getName());
		}
		column.setCellEditor(new DefaultCellEditor(comboBox));
	}
}