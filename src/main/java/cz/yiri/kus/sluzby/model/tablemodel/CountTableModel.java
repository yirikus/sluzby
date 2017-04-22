package cz.yiri.kus.sluzby.model.tablemodel;

import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author terrmith
 */
public class CountTableModel extends AbstractTableModel{

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
                if(person.getTeam()== Team.OLD){
                    return "starší";
                }else{
                    return "mladší";
                }
            case 2:
                return person.sizeOfWorkingDays();
            case 3:
                StringBuilder sb = new StringBuilder();
                for(Integer i:person.getWorkDays()){
                    if(!person.canWorkOn(i)){
                        sb.append("").append(i).append(",");
                    }
                }
                return sb.toString();
            default:
                throw new IllegalArgumentException("columnIndex");
        }

    }

    @Override
public String getColumnName(int columnIndex) {
    switch (columnIndex) {
        case 0:
            return "Jméno";
        case 1:
            return "Tým";
        case 2:
            return "Počet služeb";
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
        case 1:
        case 3:
            return String.class;
        case 2:
            return Integer.class;

        default:
            throw new IllegalArgumentException("columnIndex");
    }
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
}

public void updateTable(Collection<Person> old, Collection<Person> young){
    persons.clear();
    persons.addAll(old);
    persons.addAll(young);
    fireTableDataChanged();
}

}
