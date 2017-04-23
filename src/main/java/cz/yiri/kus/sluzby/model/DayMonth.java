package cz.yiri.kus.sluzby.model;

import java.util.Calendar;

/**
 * @author jiri.kus
 */
public class DayMonth {
	private int day;
	private int month;

	public DayMonth(int day, int month) {
		this.day = day;
		this.month = month;
	}

	private int getDay() {
		return day;
	}

	private int getMonth() {
		return month;
	}

	public static DayMonth create(Calendar day) {
		int dayOfMonth = day.get(Calendar.DAY_OF_MONTH);
		int month = day.get(Calendar.MONTH);

		return new DayMonth(dayOfMonth, month + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DayMonth)) {
			return false;
		}

		DayMonth dayMonth = (DayMonth) o;

		if (day != dayMonth.day) {
			return false;
		}
		if (month != dayMonth.month) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = day;
		result = 31 * result + month;
		return result;
	}
}
