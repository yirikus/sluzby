package cz.yiri.kus.sluzby.model.tablemodel;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author terrmith
 */
public class MainTableModel extends AbstractTableModel{

    private List<Day> days;
    private PersonTableModel oldModel;
    private PersonTableModel youngModel;

    public MainTableModel(PersonTableModel oldModel,PersonTableModel youngModel) {
        super();
        this.days= new ArrayList<Day>();
        this.oldModel=oldModel;
        this.youngModel=youngModel;

    }

    public int getRowCount() {
        return days.size();
    }

    public int getColumnCount() {
        return 5;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Day day = days.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return day.print();
            case 1:
                return day.isHoliday();
            case 2:
                if(day.getOld()==null){
                    return "-";
                }else{
                    return day.getOld().getName();
                }
            case 3:
                if(day.getYoung()==null){
                    return "-";
                }else{
                    return day.getYoung().getName();
                }
            case 4:
                if(day.getThird()==null){
                    return "-";
                }else{
                    return day.getThird().getName();
                }
            default:
                throw new IllegalArgumentException("columnIndex");
        }

    }

    @Override
public String getColumnName(int columnIndex) {
    switch (columnIndex) {
        case 0:
            return "Datum";
        case 1:
            return "Svátek";
        case 2:
            return "Interna - starší";
        case 3:
            return "Interna - mladší";
        case 4:
            return "Dialýza";
        default:
            throw new IllegalArgumentException("columnIndex");
    }
}

    @Override
public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
        case 1:
            return Boolean.class;
        case 0:
        case 2:
        case 3:
        case 4:
            return String.class;

        default:
            throw new IllegalArgumentException("columnIndex");
    }
}

@Override
public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    Day day = days.get(rowIndex);
    Person p;
    Person o;
    switch (columnIndex) {
        case 1:
            day.setHoliday((Boolean)aValue);
            break;
        case 2:
            //note: if adding a person number of work days must be updated
            if(oldModel.getPersons()==null || oldModel.getPersons().isEmpty()){
                System.out.println("mainTab leModel.column2.null or empty");
                return;
            }

            p = new Person((String)aValue, Team.OLD);

            List<Person> ps = new ArrayList<Person>();
            ps.addAll(oldModel.getPersons());

            //there are no doctors in the list

            o=day.getOld();
            if(o!=null){
                o.removeUsed(day);
            }

            if(ps.contains(p)){
               p=ps.get(ps.indexOf(p));
            }else{
                throw new IllegalArgumentException();
            }
            day.setOld(p);
            break;
        case 3:
            //note: if adding a person number of work days must be updated
           if(youngModel.getPersons()==null || youngModel.getPersons().isEmpty()){
               System.out.println("mainTableModel.column3.null or empty");
                return;
            }
            p = new Person((String)aValue, Team.YOUNG);

            List<Person> ps2 = new ArrayList<Person>();
            ps2.addAll(youngModel.getPersons());

            o=day.getYoung();
            if(o!=null){
                o.removeUsed(day);
            }

            if(ps2.contains(p)){
               p=ps2.get(ps2.indexOf(p));
            }else{
                throw new IllegalArgumentException();
            }
            day.setYoung(p);
            break;
        case 4:
            //note: dialyza does not change number of work days
            if(oldModel.getPersons()==null || oldModel.getPersons().isEmpty()){
                System.out.println("mainTableModel.column4.null or empty");
                return;
            }
            if(youngModel.getPersons()==null || youngModel.getPersons().isEmpty()){
                System.out.println("mainTableModel.column4.null or empty");
                return;
            }
            Person pyj = new Person((String)aValue, Team.YOUNG);

            List<Person> pyjs = new ArrayList<Person>();
            pyjs.addAll(youngModel.getPersons());
            List<Person> pojs = new ArrayList<Person>();
            pojs.addAll(oldModel.getPersons());

            o=day.getThird();

            if(pyjs.contains(pyj)){
               p=pyjs.get(pyjs.indexOf(pyj));
            }else if(pojs.contains(pyj)){
               p=pojs.get(pojs.indexOf(pyj));
            }else{
                throw new IllegalArgumentException();
            }

            day.setThird(p);
            break;

        default:
            throw new IllegalArgumentException("columnIndex");
    }
    fireTableCellUpdated(rowIndex, columnIndex);
    
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
    if(columnIndex==0){
        return false;
    }else{
        return true;
    }
}
    public void updateTable(List<Day> days){        
        this.days=days;
        System.out.println("###updateTable: "+this.days.size());
        fireTableDataChanged();
    }

    public List<Day> getDays() {
        System.out.println("###getDays: "+this.days.size());
        return days;
    }

}
