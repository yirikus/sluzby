package cz.yiri.kus.sluzby.model.tablemodel;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.FormModel;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author terrmith
 */
public class MainTableModel extends AbstractTableModel {

	private final List<Day> days;
	private final List<Person> old;
	private final List<Person> young;

	public MainTableModel(FormModel model) {
		super();
		this.days = model.getDays();
		this.old = model.getOld();
		this.young = model.getYoung();

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
				if (day.getOld() == null) {
					return "-";
				} else {
					return day.getOld().getName();
				}
			case 3:
				if (day.getYoung() == null) {
					return "-";
				} else {
					return day.getYoung().getName();
				}
			case 4:
				if (day.getThird() == null) {
					return "-";
				} else {
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Day day = days.get(rowIndex);
		switch (columnIndex) {
			case 1:
				day.setHoliday((Boolean) aValue);
				break;
			case 2:
				//note: if adding a person number of work days must be updated
				if (checkOld()) {
					return;
				}

				setOldCell((String) aValue, day);
				break;
			case 3:
				//note: if adding a person number of work days must be updated
				if (checkYoung()) {
					return;
				}
				setYoungCell((String) aValue, day);
				break;
			case 4:
				//note: dialyza does not change number of work days
				if (checkYoungAndOld()) {
					return;
				}
				setDialyzaCell((String) aValue, day);
				break;

			default:
				throw new IllegalArgumentException("columnIndex");
		}
		fireTableCellUpdated(rowIndex, columnIndex);

	}

	private boolean checkYoungAndOld() {
		if (old == null || old.isEmpty()) {
			System.out.println("mainTableModel.column4.null or empty");
			return true;
		}
		if (young == null || young.isEmpty()) {
			System.out.println("mainTableModel.column4.null or empty");
			return true;
		}
		return false;
	}

	private boolean checkYoung() {
		if (young == null || young.isEmpty()) {
			System.out.println("mainTableModel.column3.null or empty");
			return true;
		}
		return false;
	}

	private boolean checkOld() {
		if (old == null || old.isEmpty()) {
			System.out.println("mainTab leModel.column2.null or empty");
			return true;
		}
		return false;
	}

	private void setOldCell(String aValue, Day day) {
		Person p;
		Person o;
		p = new Person((String) aValue, Team.OLD);

		List<Person> ps = new ArrayList<Person>();
		ps.addAll(old);

		//there are no doctors in the list

		o = day.getOld();
		if (o != null) {
			o.removeUsed(day);
		}

		if (ps.contains(p)) {
			p = ps.get(ps.indexOf(p));
		} else {
			p = null;
		}
		day.setOld(p);
	}

	private void setYoungCell(String aValue, Day day) {
		Person p;
		Person o;
		p = new Person((String) aValue, Team.YOUNG);

		List<Person> ps2 = new ArrayList<Person>();
		ps2.addAll(young);

		o = day.getYoung();
		if (o != null) {
			o.removeUsed(day);
		}

		if (ps2.contains(p)) {
			p = ps2.get(ps2.indexOf(p));
		} else {
			p = null;
		}
		day.setYoung(p);
	}

	private void setDialyzaCell(String aValue, Day day) {
		Person o;
		Person p;
		Person pyj = new Person((String) aValue, Team.YOUNG);

		List<Person> pyjs = new ArrayList<Person>();
		pyjs.addAll(young);
		List<Person> pojs = new ArrayList<Person>();
		pojs.addAll(old);

		o = day.getThird();

		if (pyjs.contains(pyj)) {
			p = pyjs.get(pyjs.indexOf(pyj));
		} else if (pojs.contains(pyj)) {
			p = pojs.get(pojs.indexOf(pyj));
		} else {
			p = null;
		}

		day.setThird(p);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex != 0;
	}

	public void updateTable(List<Day> days) {
		this.days.clear();
		this.days.addAll(days);
		System.out.println("###updateTable: " + this.days.size());
		fireTableDataChanged();
	}

	public List<Day> getDays() {
		System.out.println("###getDays: " + this.days.size());
		return days;
	}

}
