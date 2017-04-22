package cz.yiri.kus.sluzby;

import cz.yiri.kus.sluzby.model.tablemodel.CountTableModel;
import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.tablemodel.PersonTableModel;
import cz.yiri.kus.sluzby.model.Team;
import cz.yiri.kus.sluzby.service.Storage;
import cz.yiri.kus.sluzby.view.ColorRenderer;
import cz.yiri.kus.sluzby.view.Constants;
import cz.yiri.kus.sluzby.view.TabbedPaneListener;
import cz.yiri.kus.sluzby.view.ToolBarListener;
import cz.yiri.kus.sluzby.model.tablemodel.MainTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author terrmith
 */
public class Main{

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

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

     /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        try{
        Calendar today = Calendar.getInstance();
        String datum = (today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.YEAR);
        frame = new JFrame("Sluzby"+VERSION+": "+datum);
        frame.setPreferredSize(new Dimension(800,640));
        frame.setLayout(new BorderLayout());
       
        //////old tab
        JTable oldTable = new JTable();
        PersonTableModel oldModel = new PersonTableModel();
        oldTable.setModel(oldModel);
        oldTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        oldTable.setFillsViewportHeight(true);
        TableColumn column = oldTable.getColumnModel().getColumn(0);
        column.setMaxWidth(120);
        column = oldTable.getColumnModel().getColumn(1);
        column.setMaxWidth(80);

        //// count tab
        JTable countTable = new JTable();
        CountTableModel countModel = new CountTableModel();
        countTable.setModel(countModel);
        countTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        countTable.setFillsViewportHeight(true);

        //////young tab        
        JTable youngTable = new JTable();
        PersonTableModel youngModel = new PersonTableModel();
        youngTable.setModel(youngModel);

        column = youngTable.getColumnModel().getColumn(0);
        column.setMaxWidth(120);
        column = youngTable.getColumnModel().getColumn(1);
        column.setMaxWidth(80);
        
        youngTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        youngTable.setFillsViewportHeight(true);

         //// main table tab
        JTable table = new JTable();
        MainTableModel tableModel = new MainTableModel(oldModel, youngModel);
        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);


        setCellRendererAsComboBox(2,table,oldModel.getPersons());
        setCellRendererAsComboBox(3,table,youngModel.getPersons());
        List<Person> youngAndOld = new ArrayList<Person>();
        youngAndOld.addAll(oldModel.getPersons());
        youngAndOld.addAll(youngModel.getPersons());
        setCellRendererAsComboBox(4,table,youngAndOld);

        //////scrollers
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane oldScrollPane = new JScrollPane(oldTable);
        JScrollPane youngScrollPane = new JScrollPane(youngTable);
        JScrollPane countScrollPane = new JScrollPane(countTable);

        //////toolbar
        JToolBar toolBar = new JToolBar("Tools");
        addComponents(toolBar);

        //////tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.add("tabulka",scrollPane);
        tabbedPane.add("počet služeb",countScrollPane);
        tabbedPane.add("starší",oldScrollPane);
        tabbedPane.add("mladší",youngScrollPane);
        TabbedPaneListener listener = new TabbedPaneListener(toolBar, processButton, saveButton, loadButton, addButton, textField,oldModel,youngModel,countModel,table);
        tabbedPane.addChangeListener(listener);

        //listeners
        ToolBarListener toolbarListener = new ToolBarListener(tabbedPane,textField,youngModel, oldModel,tableModel,table);
        addButton.addActionListener(toolbarListener);
        loadButton.addActionListener(toolbarListener);
        saveButton.addActionListener(toolbarListener);
        writeButton.addActionListener(toolbarListener);
        processButton.addActionListener(toolbarListener);
        createButton.addActionListener(toolbarListener);
        textField.addActionListener(toolbarListener);
        //////frame
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(tabbedPane, BorderLayout.CENTER);

        //načíst
        loadedYoung = new ArrayList<Person>();
        loadedOld= new ArrayList<Person>();
        loadedDays= new ArrayList<Day>();
        Storage.load(loadedYoung, loadedOld, loadedDays);
        System.out.println("###main: "+loadedDays.size());

        youngModel.updateTable(loadedYoung);
        oldModel.updateTable(loadedOld);
        tableModel.updateTable(loadedDays);

        Main.setCellRendererAsComboBox(2, table, oldModel.getPersons());
        Main.setCellRendererAsComboBox(3, table, youngModel.getPersons());
           
        List<Person> youngAndOld2 = new ArrayList<Person>();
        youngAndOld.addAll(oldModel.getPersons());
        youngAndOld.addAll(youngModel.getPersons());
        Main.setCellRendererAsComboBox(4, table, youngAndOld2);

        //setRenderers
        TableColumn oldColumn = table.getColumnModel().getColumn(2);
        TableColumn youngColumn = table.getColumnModel().getColumn(3);
        TableCellRenderer oldRenderer = new ColorRenderer((List<Person>)oldModel.getPersons(), Team.OLD);
        TableCellRenderer youngRenderer = new ColorRenderer((List<Person>)youngModel.getPersons(), Team.YOUNG);
        oldColumn.setCellRenderer(oldRenderer);
        youngColumn.setCellRenderer(youngRenderer);

        //
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,
            e.getMessage(),
             "Chyba!",
                JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Adds various components to the toolbar
     * @param toolBar
     */
private static void addComponents(JToolBar toolBar){
        createButton = new JButton("Nový");
        createButton.setActionCommand(Constants.CREATE);
        toolBar.add(createButton);
    
        processButton = new JButton("Spočítat");
        processButton.setActionCommand(Constants.PROCESS);
        toolBar.add(processButton);

        writeButton = new JButton("Dokument k odeslání");
        writeButton.setActionCommand(Constants.WRITE);
        toolBar.add(writeButton);        

        saveButton = new JButton("Uložit");
        saveButton.setActionCommand(Constants.SAVE);
        toolBar.add(saveButton);

        loadButton = new JButton("Nahrát");
        loadButton.setActionCommand(Constants.LOAD);
        toolBar.add(loadButton);

        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        toolBar.add(sep);

        addButton = new JButton("Přidej doktora");
        addButton.setActionCommand(Constants.ADD);
        //toolBar.add(addButton);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 10));
        textField.setActionCommand(Constants.ADD);
        //toolBar.add(textField);
}

    public static void setCellRendererAsComboBox(int i,JTable table, Collection<Person> persons) {
        TableColumn column = table.getColumnModel().getColumn(i);
        JComboBox comboBox = new JComboBox();
        for(Person p:persons){
            comboBox.addItem(p.getName());
        }
        column.setCellEditor(new DefaultCellEditor(comboBox));
    }


}
