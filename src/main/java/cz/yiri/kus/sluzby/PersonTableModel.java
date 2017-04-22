package cz.yiri.kus.sluzby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class PersonTableModel
  extends AbstractTableModel {
	private List<Person> persons = new ArrayList();

	public int getRowCount() {
		return this.persons.size();
	}

	public int getColumnCount() {
		return 4;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = (Person) this.persons.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return person.getName();
			case 1:
				return Boolean.valueOf(person.isWantsOnly());
			case 2:
				return person.wantedDaysToString();
			case 3:
				return person.unwantedDaysToString();
		}
		throw new IllegalArgumentException("columnIndex");
	}


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
		}
		throw new IllegalArgumentException("columnIndex");
	}


	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
			case 2:
			case 3:
				return String.class;
			case 1:
				return Boolean.class;
		}

		throw new IllegalArgumentException("columnIndex");
	}


	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Person person = (Person) this.persons.get(rowIndex);
		switch (columnIndex) {
			case 0:
				person.setName((String) aValue);
				break;
			case 1:
				person.setWantsOnly(((Boolean) aValue).booleanValue());
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
		if (person.getName().equals("")) {
			this.persons.remove(person);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
	}


	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void addPerson(Person person) {
		this.persons.add(person);
		int lastRow = this.persons.size() - 1;
		fireTableRowsInserted(lastRow, lastRow);
	}

	public void updateTable(List<Person> newPpl) {
		this.persons.clear();
		this.persons.addAll(newPpl);
		fireTableDataChanged();
	}

	public Collection<Person> getPersons() {
		return this.persons;
	}
}


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\PersonTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */