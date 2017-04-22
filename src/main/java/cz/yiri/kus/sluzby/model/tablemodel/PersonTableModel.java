package cz.yiri.kus.sluzby.model.tablemodel;

import cz.yiri.kus.sluzby.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author terrmith
 */
public class PersonTableModel extends AbstractTableModel{

    private List<Person> persons = new ArrayList<Person>();

    public int getRowCount() {
        return persons.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = persons.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return person.getName();
            case 1:
                return person.isWantsOnly();
            case 2:
                return person.wantedDaysToString();
            case 3:
                return person.unwantedDaysToString();
            default:
                throw new IllegalArgumentException("columnIndex");
        }

    }
    
    @Override
public String getColumnName(int columnIndex) {
    switch (columnIndex) {
        case 0:
            return "Jmeno";
        case 1:
            return "Chce pouze?";
        case 2:
            return "Chce";
        case 3:
            return "Nechce";
        default:
            throw new IllegalArgumentException("columnIndex");
    }
}

    @Override
public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
        case 0:
        case 2:
        case 3:
            return String.class;
        case 1:
            return Boolean.class;

        default:
            throw new IllegalArgumentException("columnIndex");
    }
}

@Override
public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Person person = persons.get(rowIndex);
    switch (columnIndex) {
        case 0:
            person.setName((String) aValue);
            break;
        case 1:
            person.setWantsOnly((Boolean) aValue);
            break;
        case 2:
            person.setWantedDays((String) aValue);
            break;
        case 3:
            person.setUnwantedDays((String) aValue);
            break;


        default:
            throw new IllegalArgumentException("columnIndex");
    }
    fireTableCellUpdated(rowIndex, columnIndex);
    if(person.getName().equals("")){
        persons.remove(person);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
}
    public void addPerson(Person person){
        persons.add(person);
        int lastRow = persons.size() - 1;
        fireTableRowsInserted(lastRow, lastRow);

    }

    public void updateTable(List<Person> newPpl){
        persons.clear();
        persons.addAll(newPpl);
        fireTableDataChanged();
    }


    public Collection<Person> getPersons(){
        return persons;
    }

}
