package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author terrmith
 */
public class HarmonogramFactory {

	//private static Collection<Person> young;
	//private static Collection<Person> old;
	//private List<Day> days;

	private HarmonogramFactory() {
		//this.young = young;
		//this.old = old;
	}

	private static String dateToString(Calendar c) {
		return c.get(Calendar.DATE) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR) + "=" +
		  c.get(Calendar.DAY_OF_MONTH) + "." + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) +
		  "(" + c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ")";

	}

	/**
	 * Create new blank harmonogram that will be filled later
	 */
	public static Collection<Day> newHarmonogram(Collection<Person> young, Collection<Person> old) {
		List<Day> days = new ArrayList<Day>();
		//clear working days
		for (Person p : old) {
			p.clearWorkingDays();
		}
		for (Person p : young) {
			p.clearWorkingDays();
		}

		//set date as the next month after the current one
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DATE, 1);
		today.set(Calendar.YEAR, today.get(Calendar.YEAR) + (today.get(Calendar.MONTH)) / 11);
		today.set(Calendar.MONTH, (today.get(Calendar.MONTH) + 1) % 12);
		System.out.println(dateToString(today));

		int lastDay = today.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= lastDay; i++) {
			Calendar day = Calendar.getInstance();
			day.set(Calendar.DAY_OF_MONTH, i);
			day.set(Calendar.MONTH, (today.get(Calendar.MONTH)));
			day.set(Calendar.YEAR, today.get(Calendar.YEAR));

//ok
			if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
			  day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || PublicHolidays.isPublicHoliday(day)) {

				Day newDay = new Day(day, true);
				days.add(newDay);
			} else {

				Day newDay = new Day(day, false);
				days.add(newDay);
			}
		}
		//prirad dny, ktere nekdo chce
		for (Person p : old) {
			for (Integer i : p.getWantedDays()) {
				if (i <= days.size() && i > 0) {
					Day d = days.get(i.intValue() - 1);
					if (d.getOld() == null && !p.worksOn(i)) {
						d.setOld(p);
					}
				}
			}
		}

		for (Person p : young) {
			for (Integer i : p.getWantedDays()) {
				if (i <= days.size() && i > 0) {
					Day d = days.get(i.intValue() - 1);
					if (d.getYoung() == null && !p.worksOn(i)) {
						d.setYoung(p);


					}
				}
			}
		}
		return days;
	}

	/**
	 * Fills as many days with doctors
	 *
	 * @param days
	 * @return
	 */
	public static Collection<Day> fillHarmonogram(List<Day> days, Collection<Person> young, Collection<Person> old) {
		//copy days
		List<Day> newDays = new ArrayList<>();
		newDays.addAll(days);

		fillUnwanted(newDays, young, Team.YOUNG);
		fillUnwanted(newDays, old, Team.OLD);

		fillTheRest(newDays, young, Team.YOUNG);
		fillTheRest(newDays, old, Team.OLD);

		return newDays;
	}

	private static void fillUnwanted(List<Day> days, Collection<Person> persons, Team team) {
		List<Integer> badDays = new ArrayList<Integer>();

		for (Person p : persons) {
			if (!p.isWantsOnly()) {
				for (Integer i : p.getUnwantedDays()) {
					if (!badDays.contains(i) && ((i - 1) < days.size()) && (i - 1) >= 0) {
						badDays.add(i);
					}
				}
			}
		}

		//depending on number of jobs, any unwanted days are assigned
		for (Integer i : badDays) {
			System.out.println("harmonogram.fill.day = " + i);

			Day d = days.get(i - 1);

			Person person;
			if (team == Team.OLD) {
				person = d.getOld();
			} else {
				person = d.getYoung();
			}
			if (person == null) { //nobody assigned to the day
				boolean assigned = false; //is day assigned yet?
				for (int k = 5; k >= 0 && !assigned; k--) {
					for (int j = 0; j < 32 && !assigned; j++) { //choose person with least jobs
						for (Person p : persons) {
							if (!p.isWantsOnly()) {
								if (p.sizeOfWorkingDays() == j && p.canWorkOn(i) && !(d.isHoliday() && p.hasHoliday())
								  && !p.worksAround(i, k)) {


									if (team == Team.OLD) {
										d.setOld(p);
									} else {
										d.setYoung(p);
									}
									System.out.println("assigned to " + p.getName());
									assigned = true;
									break;

								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Assign people to all days excluding holidays (holidays are to be filled manually)
	 *
	 * @param days
	 * @param persons
	 * @param team
	 */
	private static void fillTheRest(List<Day> days, Collection<Person> persons, Team team) {

		//depending on number of jobs, any unwanted days are assigned
		for (Day day : days) {

			Person person;
			if (team == Team.OLD) {
				person = day.getOld();
			} else {
				person = day.getYoung();
			}
			if (person == null) { //nobody assigned to the day
				boolean assigned = false; //is day assigned yet?
				for (int k = 5; k >= 0 && !assigned; k--) {//choose person with least jobs around this date
					for (int j = 0; j < 32 && !assigned; j++) { //choose person with least jobs

						for (Person p : persons) {
							if (!p.isWantsOnly()) {
								if (p.sizeOfWorkingDays() == j && p.canWorkOn(day.getInteger()) && !(day.isHoliday()
								  && p.hasHoliday()) && !p.worksAround(day.getInteger(), k)) {

									System.out.println("assigned to " + p.getName());
									if (team == Team.OLD) {
										day.setOld(p);
									} else {
										day.setYoung(p);
									}

									assigned = true;
									break;

								}
							}
						}
					}
				}
			}
		}
	}
}
