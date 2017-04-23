package cz.yiri.kus.sluzby.model;

import java.util.Calendar;

/**
 * reprezentuje kalendarni den s pridanymi slouzicimi doktory
 *
 * @author terrmith
 */
public class Day {

	private Calendar day;
	private Person old;
	private Person young;
	private Person third; //dialyza
	private boolean holiday;

	public Day(Calendar day, boolean holiday) {
		this.day = day;
		this.holiday = holiday;
	}

	public void setHoliday(boolean holiday) {
		this.holiday = holiday;
	}

	public String monthAndYearToString() {
		String value = "";
		switch (day.get(Calendar.MONTH)) {
			case Calendar.JANUARY:
				value = "LEDEN";
				break;
			case Calendar.FEBRUARY:
				value = "ÚNOR";
				break;
			case Calendar.MARCH:
				value = "BŘEZEN";
				break;
			case Calendar.APRIL:
				value = "DUBEN";
				break;
			case Calendar.MAY:
				value = "KVĚTEN";
				break;
			case Calendar.JUNE:
				value = "ČERVEN";
				break;
			case Calendar.JULY:
				value = "ČERVENEC";
				break;
			case Calendar.AUGUST:
				value = "SRPEN";
				break;
			case Calendar.SEPTEMBER:
				value = "ZÁŘÍ";
				break;
			case Calendar.OCTOBER:
				value = "ŘÍJEN";
				break;
			case Calendar.NOVEMBER:
				value = "LISTOPAD";
				break;
			case Calendar.DECEMBER:
				value = "PROSINEC";
				break;

		}
		return value + " " + day.get(Calendar.YEAR);
	}


	public void setOld(Person old) {
		this.old = old;
		if (old != null) {
			old.addUsed(this);
		}
	}

	public void setYoung(Person young) {
		this.young = young;
		if (young != null) {
			young.addUsed(this);
		}
	}

	public Person getOld() {
		return old;
	}

	public Person getYoung() {
		return young;
	}

	public Calendar getDay() {
		return day;
	}

	public Person getThird() {
		return third;
	}

	public void setThird(Person third) {
		this.third = third;
	}

	public boolean isHoliday() {
		return holiday;
	}

	/**
	 * 0:datum
	 * 1:starsi
	 * 2:mladsi
	 * 3:dialyza
	 *
	 * @param field number of field to be returned
	 * @return value of given field
	 */
	public String get(int field) {
		String value = "?";
		switch (field) {
			case 0:
				value = print();
				break;
			case 1:
				value = (getOld() == null ? "-" : getOld().getName());
				break;
			case 2:
				value = (getYoung() == null ? "-" : getYoung().getName());
				break;
			case 3:
				value = (getThird() == null ? "" : getThird().getName());
				break;

		}
		return value;
	}

	/**
	 * Retruns day in this format: D.M.Y
	 */
	public String print() {
		final String dayOfWeek;
		switch (getDay().get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				dayOfWeek = "Ne ";
				break;
			case Calendar.MONDAY:
				dayOfWeek = "Po ";
				break;
			case Calendar.TUESDAY:
				dayOfWeek = "Út ";
				break;
			case Calendar.WEDNESDAY:
				dayOfWeek = "St ";
				break;
			case Calendar.THURSDAY:
				dayOfWeek = "Čt ";
				break;
			case Calendar.FRIDAY:
				dayOfWeek = "Pá ";
				break;
			case Calendar.SATURDAY:
				dayOfWeek = "So ";
				break;
			default:
				throw new IllegalArgumentException("Wrong use of Calendar.DAY_OF_WEEK");

		}
		return dayOfWeek + getDay().get(Calendar.DAY_OF_MONTH) + "." +
		  (getDay().get(Calendar.MONTH) + 1) + ".";
	}

	@Override
	public String toString() {
		return getDay().get(Calendar.DAY_OF_MONTH) + "." +
		  (getDay().get(Calendar.MONTH) + 1) + "."
		  + getDay().get(Calendar.YEAR);
	}

	/**
	 * Returns Calendar.DAY_OF_MONTH of this day
	 */
	public Integer getInteger() {
		return getDay().get(Calendar.DAY_OF_MONTH);
	}

}
