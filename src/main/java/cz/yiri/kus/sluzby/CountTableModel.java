package cz.yiri.kus.sluzby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class CountTableModel
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
				if (person.getTeam() == Team.OLD) {
					return "starší";
				}
				return "mladší";

			case 2:
				return Integer.valueOf(person.sizeOfWorkingDays());
			case 3:
				StringBuilder sb = new StringBuilder();
				for (Integer i : person.getWorkDays()) {
					if (!person.canWorkOn(i)) {
						sb.append("").append(i).append(",");
					}
				}
				return sb.toString();
		}
		throw new IllegalArgumentException("columnIndex");
	}


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
		}
		throw new IllegalArgumentException("columnIndex");
	}


	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
			case 1:
			case 3:
				return String.class;
			case 2:
				return Integer.class;
		}

		throw new IllegalArgumentException("columnIndex");
	}


	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void updateTable(Collection<Person> old, Collection<Person> young) {
		this.persons.clear();
		this.persons.addAll(old);
		this.persons.addAll(young);
		fireTableDataChanged();
	}
}