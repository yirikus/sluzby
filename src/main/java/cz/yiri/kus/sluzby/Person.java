package cz.yiri.kus.sluzby;

import java.util.HashSet;
import java.util.Set;


public class Person
  implements Comparable {
	private boolean hasHoliday = false;
	private boolean wantsOnly = false;

	private String name;

	private Team team;

	private Set<Integer> unwantedDays = new HashSet();
	private Set<Integer> wantedDays = new HashSet();

	private Set<Integer> usedDays = new HashSet();


	public Person(String name, Team team) {
		this(name, new HashSet(), new HashSet(), team);
	}


	public Person(String name, Set<Integer> bad, Set<Integer> good, Team team) {
		this.name = name;
		this.unwantedDays = bad;
		this.team = team;
	}


	public boolean isWantsOnly() {
		return this.wantsOnly;
	}


	public void setWantsOnly(boolean wantsOnly) {
		this.wantsOnly = wantsOnly;
	}


	public Team getTeam() {
		return this.team;
	}


	public void setHasHoliday(boolean hasWeekend) {
		this.hasHoliday = hasWeekend;
	}


	public boolean hasHoliday() {
		return this.hasHoliday;
	}


	public Set<Integer> getWorkDays() {
		return this.usedDays;
	}


	public boolean canWorkOn(Integer i) {
		if (this.wantsOnly) {
			return this.wantedDays.contains(i);
		}
		return !this.unwantedDays.contains(i);
	}


	public boolean worksOn(Integer i) {
		return this.usedDays.contains(i);
	}


	public void addUnwanted(Integer i) {
		this.unwantedDays.add(i);
	}


	public void setUnwantedDays(String unwanted) {
		this.unwantedDays.clear();
		this.unwantedDays = parseDaysFromString(unwanted);
	}


	public void setWantedDays(String wanted) {
		this.wantedDays.clear();
		this.wantedDays = parseDaysFromString(wanted);
	}


	public void setWorkDays(String work) {
		this.usedDays.clear();
		this.usedDays = parseDaysFromString(work);
	}


	private Set<Integer> parseDaysFromString(String str) {
		Set<Integer> result = new HashSet();

		String rest = str;
		rest = rest.trim();
		StringBuffer number = new StringBuffer("");

		for (int i = 0; i < rest.length(); i++) {
			if (Character.isDigit(rest.charAt(i))) {
				number.append(rest.charAt(i));
			} else if (!number.toString().equals("")) {
				result.add(Integer.valueOf(Integer.parseInt(number.toString())));
				number.delete(0, number.length());
			}
		}

		if (!number.toString().equals("")) {
			result.add(Integer.valueOf(Integer.parseInt(number.toString())));
			number.delete(0, number.length());
		}

		return result;
	}


	public void clearWorkingDays() {
		this.usedDays.clear();
		setHasHoliday(false);
	}


	public void addUsed(Day d) {
		this.usedDays.add(d.getInteger());
		if (d.isHoliday()) {
			setHasHoliday(true);
		}
	}

	public void removeUsed(Day d) {
		this.usedDays.remove(d.getInteger());
		if (d.isHoliday()) {
			setHasHoliday(false);
		}
	}


	public void addWanted(Integer i) {
		this.wantedDays.add(i);
	}


	public boolean worksAround(Integer day, int radius) {
		for (Integer i : this.usedDays) {
			if ((i.intValue() >= day.intValue() - radius) && (i.intValue() <= day.intValue() + radius)) {
				return true;
			}
		}
		return false;
	}


	public String unwantedDaysToString() {
		StringBuilder sb = new StringBuilder("");
		for (Integer i : this.unwantedDays) {
			sb.append(i);
			sb.append(",");
		}
		return sb.toString();
	}


	public String wantedDaysToString() {
		StringBuilder sb = new StringBuilder("");
		for (Integer i : this.wantedDays) {
			sb.append(i);
			sb.append(",");
		}
		return sb.toString();
	}


	public int hashCode() {
		return this.name.hashCode();
	}


	public boolean equals(Object o) {
		if ((o instanceof Person)) {
			Person p2 = (Person) o;
			return this.name.equals(p2.getName());
		}
		return false;
	}


	public String getName() {
		return this.name;
	}


	public int compareTo(Object o) {
		Person p = (Person) o;
		if (sizeOfUnwantedDays() - p.sizeOfUnwantedDays() != 0) {
			return -(sizeOfUnwantedDays() - p.sizeOfUnwantedDays());
		}
		return -this.name.compareTo(p.getName());
	}


	public int sizeOfUnwantedDays() {
		return this.unwantedDays.size();
	}


	public int sizeOfWantedDays() {
		return this.wantedDays.size();
	}


	public int sizeOfWorkingDays() {
		return this.usedDays.size();
	}


	public String toString() {
		String str = this.name + " ";
		for (Integer i : this.unwantedDays) {
			str = str + i + " ";
		}
		return str;
	}

	public void setName(String string) {
		this.name = string;
	}

	public Set<Integer> getUnwantedDays() {
		return this.unwantedDays;
	}

	public Set<Integer> getWantedDays() {
		return this.wantedDays;
	}
}


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\Person.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */