package cz.yiri.kus.sluzby;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class MainTableModel
  extends AbstractTableModel {
	private List<Day> days;
	private PersonTableModel oldModel;
	private PersonTableModel youngModel;

	public MainTableModel(PersonTableModel oldModel, PersonTableModel youngModel) {
		this.days = new ArrayList();
		this.oldModel = oldModel;
		this.youngModel = youngModel;
	}

	public int getRowCount() {
		return this.days.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Day day = (Day) this.days.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return day.print();
			case 1:
				return Boolean.valueOf(day.isHoliday());
			case 2:
				if (day.getOld() == null) {
					return "-";
				}
				return day.getOld().getName();

			case 3:
				if (day.getYoung() == null) {
					return "-";
				}
				return day.getYoung().getName();

			case 4:
				if (day.getThird() == null) {
					return "-";
				}
				return day.getThird().getName();
		}

		throw new IllegalArgumentException("columnIndex");
	}


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
		}
		throw new IllegalArgumentException("columnIndex");
	}


	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 1:
				return Boolean.class;
			case 0:
			case 2:
			case 3:
			case 4:
				return String.class;
		}

		throw new IllegalArgumentException("columnIndex");
	}


	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Day day = (Day) this.days.get(rowIndex);
		Person p;
		Person o;
		switch (columnIndex) {
			case 1:
				day.setHoliday(((Boolean) aValue).booleanValue());
				break;

			case 2:
				if ((this.oldModel.getPersons() == null) || (this.oldModel.getPersons().isEmpty())) {
					System.out.println("mainTab leModel.column2.null or empty");
					return;
				}

				p = new Person((String) aValue, Team.OLD);

				List<Person> ps = new ArrayList();
				ps.addAll(this.oldModel.getPersons());


				o = day.getOld();
				if (o != null) {
					o.removeUsed(day);
				}

				if (ps.contains(p)) {
					p = (Person) ps.get(ps.indexOf(p));
				} else {
					throw new IllegalArgumentException();
				}
				day.setOld(p);
				break;

			case 3:
				if ((this.youngModel.getPersons() == null) || (this.youngModel.getPersons().isEmpty())) {
					System.out.println("mainTableModel.column3.null or empty");
					return;
				}
				p = new Person((String) aValue, Team.YOUNG);

				List<Person> ps2 = new ArrayList();
				ps2.addAll(this.youngModel.getPersons());

				o = day.getYoung();
				if (o != null) {
					o.removeUsed(day);
				}

				if (ps2.contains(p)) {
					p = (Person) ps2.get(ps2.indexOf(p));
				} else {
					throw new IllegalArgumentException();
				}
				day.setYoung(p);
				break;

			case 4:
				if ((this.oldModel.getPersons() == null) || (this.oldModel.getPersons().isEmpty())) {
					System.out.println("mainTableModel.column4.null or empty");
					return;
				}
				if ((this.youngModel.getPersons() == null) || (this.youngModel.getPersons().isEmpty())) {
					System.out.println("mainTableModel.column4.null or empty");
					return;
				}
				Person pyj = new Person((String) aValue, Team.YOUNG);

				List<Person> pyjs = new ArrayList();
				pyjs.addAll(this.youngModel.getPersons());
				List<Person> pojs = new ArrayList();
				pojs.addAll(this.oldModel.getPersons());

				o = day.getThird();

				if (pyjs.contains(pyj)) {
					p = (Person) pyjs.get(pyjs.indexOf(pyj));
				} else {
					Person p;
					if (pojs.contains(pyj)) {
						p = (Person) pojs.get(pojs.indexOf(pyj));
					} else {
						throw new IllegalArgumentException();
					}
				}
				Person p;
				day.setThird(p);
				break;

			default:
				throw new IllegalArgumentException("columnIndex");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}


	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return true;
	}

	public void updateTable(List<Day> days) {
		this.days = days;
		System.out.println("###updateTable: " + this.days.size());
		fireTableDataChanged();
	}

	public List<Day> getDays() {
		System.out.println("###getDays: " + this.days.size());
		return this.days;
	}
}