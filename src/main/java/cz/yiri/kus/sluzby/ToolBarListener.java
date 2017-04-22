package cz.yiri.kus.sluzby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author terrmith
 */
public class ToolBarListener implements ActionListener{

    private JTabbedPane tabbedPane;
    private JTextField textField;
    private PersonTableModel youngModel;
    private PersonTableModel oldModel;
    private MainTableModel mainModel;
    //private HarmonogramFactory sluzby;
    private boolean tableCreated;
    private JTable mainTable;
    private HarmonogramExporter exporter;


    public ToolBarListener(JTabbedPane tabbedPane,JTextField textField,PersonTableModel youngModel,PersonTableModel oldModel,MainTableModel mainModel,JTable mainTable) {
        this.tabbedPane=tabbedPane;
        this.textField=textField;
        this.oldModel=oldModel;
        this.youngModel=youngModel;
        this.mainModel=mainModel;
        this.mainTable=mainTable;                
        //sluzby = new HarmonogramFactory(youngModel.getPersons(), oldModel.getPersons());
    }

/**
 * Action handler for buttons on toolbar
 * @param e
 */
    public void actionPerformed(ActionEvent e) {
        //vytvor tabulku pro novy mesic
        if (Constants.CREATE.equals(e.getActionCommand())) {
            mainModel.updateTable((List<Day>)HarmonogramFactory.newHarmonogram(youngModel.getPersons(), oldModel.getPersons()));
            tableCreated=false;
            System.out.println(tableCreated);


        //vypocitej harmonogram pro aktualni mesic
        } else if (Constants.PROCESS.equals(e.getActionCommand())) {
            if(tableCreated){
                System.out.println("option 1 - "+tableCreated);
                mainModel.updateTable((List<Day>)HarmonogramFactory.newHarmonogram(youngModel.getPersons(), oldModel.getPersons()));
                mainModel.updateTable((List<Day>)HarmonogramFactory.fillHarmonogram(mainModel.getDays(),youngModel.getPersons(), oldModel.getPersons()));
                
            }else{
                System.out.println("option 2 - "+tableCreated);
                mainModel.updateTable((List<Day>)HarmonogramFactory.fillHarmonogram(mainModel.getDays(),youngModel.getPersons(), oldModel.getPersons()));
                tableCreated=true;  
            }
        }else if(Constants.WRITE.equals(e.getActionCommand())){
            exporter = new HarmonogramExporter(mainModel.getDays(),"tabulka");
            exporter.exportAsHTML();
            exporter.exportAsODT();           
            

        //uloz
        } else if (Constants.SAVE.equals(e.getActionCommand())) {
            Storage.save(youngModel.getPersons(), oldModel.getPersons(),mainModel.getDays());

        } else if (Constants.LOAD.equals(e.getActionCommand())) {
            List<Person> loadedYoung = new ArrayList<Person>();
            List<Person> loadedOld= new ArrayList<Person>();
            List<Day> loadedDays= new ArrayList<Day>();
            Storage.load(loadedYoung,loadedOld,loadedDays);

            youngModel.updateTable(loadedYoung);
            oldModel.updateTable(loadedOld);
            mainModel.updateTable(loadedDays);

            Main.setCellRendererAsComboBox(2, mainTable, oldModel.getPersons());
            Main.setCellRendererAsComboBox(3, mainTable, youngModel.getPersons());
                List<Person> youngAndOld = new ArrayList<Person>();
                youngAndOld.addAll(oldModel.getPersons());
                youngAndOld.addAll(youngModel.getPersons());
                Main.setCellRendererAsComboBox(4, mainTable, youngAndOld);                         

        } else if (Constants.ADD.equals(e.getActionCommand())) {
            System.out.println("toolBarListener.ADD");
            int index = tabbedPane.getSelectedIndex();
            if(index==2){
                oldModel.addPerson(new Person(textField.getText(),Team.OLD));
            }else if(index==3){
                youngModel.addPerson(new Person(textField.getText(),Team.YOUNG));
            }

        }
    }

}
