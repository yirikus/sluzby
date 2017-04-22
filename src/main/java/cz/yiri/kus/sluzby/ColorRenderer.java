package cz.yiri.kus.sluzby;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

public class ColorRenderer extends javax.swing.table.DefaultTableCellRenderer {
	private List<Person> persons;
	private Team team;

	private static class Colors {
		private static final int[] colors =
		  {16777215 , 9868950 , 16771209 , 16758153 , 16746889 , 12713865 , 9043913 , 9210367 , 14453247 , 14601224 ,
			2874888 , 14575624 , 575454 , 12060894 , 9400399 , 11628118 , 6074710 , 11109583 , 5553033 , 16754688 ,
			16730112 , 16711680 , 16711866 , 34047 , 64767 , 65340};


		public static Color getColor(int index) {
			if (index >= 0) {
				return new Color(colors[(index % colors.length)]);
			}
			return Color.WHITE;
		}
	}


	public ColorRenderer(List<Person> persons, Team team) {
		this.persons = persons;
		this.team = team;
	}


	public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected,
	                                               boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


		if (this.persons == null) {
			throw new NullPointerException("List<Person> persons must not be null, call setPersons method");
		}
		if ((value instanceof String)) {
			Person mockPerson = new Person((String) value, this.team);
			if (mockPerson != null) {
				System.out.println("colorRenderer:" + this.persons.size());
				Color color = Colors.getColor(this.persons.indexOf(mockPerson));
				cell.setBackground(color);
			}
		}

		return cell;
	}
}