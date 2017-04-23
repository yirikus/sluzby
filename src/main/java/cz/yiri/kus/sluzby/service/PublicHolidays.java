package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.DayMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author jiri.kus
 */
public class PublicHolidays {

	private static final List<DayMonth> PUBLIC_HOLIDAYS;
	static {
		List<DayMonth> tempList = new ArrayList<DayMonth>();
		tempList.add(new DayMonth(1,1));
		tempList.add(new DayMonth(1,5));
		tempList.add(new DayMonth(8,5));
		tempList.add(new DayMonth(5,7));
		tempList.add(new DayMonth(6,7));
		tempList.add(new DayMonth(28,9));
		tempList.add(new DayMonth(28,10));
		tempList.add(new DayMonth(17,11));
		tempList.add(new DayMonth(24,12));
		tempList.add(new DayMonth(25,12));
		tempList.add(new DayMonth(26,12));
		PUBLIC_HOLIDAYS = Collections.unmodifiableList(tempList);
	}

	public static boolean isPublicHoliday(Calendar day) {
		DayMonth targetDate = DayMonth.create(day);
		for (DayMonth publicHoliday : PUBLIC_HOLIDAYS) {
			if (publicHoliday.equals(targetDate)) {
				return true;
			}
		}

		Calendar easterMonday = EasterCalculator.findHolyDay(day.get(Calendar.YEAR));
		Calendar easterFriday = EasterCalculator.findHolyDay(day.get(Calendar.YEAR));
		easterFriday.add(Calendar.DAY_OF_MONTH, -3);

		return targetDate.equals(DayMonth.create(easterFriday)) || targetDate.equals(DayMonth.create(easterMonday));
	}
}
