package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;
import cz.yiri.kus.sluzby.model.Team;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author terrmith
 */
public class Storage {

	private static final String DATA_FILE = "data.txt";
	private static final String COLUMN = ":";
	private static final String SEPARATOR = "#";
	private static final String END = "&";

	/**
	 * static class
	 */
	private Storage() {
	}

	/**
	 * Saves current values
	 */
	public static void save(Collection<Person> young, Collection<Person> old, Collection<Day> days) {
		//nutno ulozit old, young,days
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATA_FILE)));

			savePersons(writer, young);
			savePersons(writer, old);

			saveDays(writer, days);

			writer.close();
		} catch (FileNotFoundException fnf) {
			System.err.println("Dany soubor neexistuje");

		} catch (IOException ioe) {
			System.err.println("IOE");

		}
	}

	/**
	 * Loads previous table
	 */
	public static void load(List<Person> young, List<Person> old, List<Day> days) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(DATA_FILE)));

			loadPersons(reader, young, Team.YOUNG);
			loadPersons(reader, old, Team.OLD);

			loadDays(reader, young, old, days);

			reader.close();
		} catch (FileNotFoundException fnf) {
			System.err.println("Dany soubor neexistuje");

		} catch (IOException ioe) {
			System.err.println("IOE");

		}
	}

	/**
	 * Saves persons separating columns by $ and values in column by #, end is indicated by &
	 *
	 * @param writer
	 * @param persons
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void savePersons(BufferedWriter writer, Collection<Person> persons)
	  throws FileNotFoundException, IOException {

		for (Person p : persons) {
			writer.write(p.getName() + COLUMN +
			               p.isWantsOnly() + COLUMN +
			               p.hasHoliday() + COLUMN);

			writer.write(SEPARATOR);
			for (Integer i : p.getUnwantedDays()) {
				writer.write(i + SEPARATOR);
			}
			writer.write(COLUMN);
			writer.write(SEPARATOR);
			for (Integer i : p.getWantedDays()) {
				writer.write(i + SEPARATOR);
			}
			writer.write(COLUMN);
			writer.write(SEPARATOR);
			for (Integer i : p.getWorkDays()) {
				writer.write(i + SEPARATOR);
			}
			writer.write(COLUMN);
			writer.newLine();
		}
		writer.write(END);
		writer.newLine();

	}

	private static void loadPersons(BufferedReader reader, Collection<Person> persons, Team team) throws IOException {
		String string = reader.readLine();
		int lineNumber = 1;
		while (string != null && !string.equals(END)) {
			String[] splitted = string.split(COLUMN);
			if (splitted.length != 6) {
				throw new IllegalStateException(
				  "Chyba na řádku #" + lineNumber + ": " + string + ". Očekávaný formát: jméno" + COLUMN + "chcePouze"
					+ COLUMN + "máSvátek" + COLUMN + "nechtěnéDny" + COLUMN + "chtěnéDny" + COLUMN + "přidělené dny");
			}
			String name = splitted[0];
			boolean wantsOnly = splitted[1].equals("true") ? true : false;
			boolean hasHoliday = splitted[2].equals("true") ? true : false;

			Person person = new Person(name, team);
			person.setWantsOnly(wantsOnly);
			person.setHasHoliday(hasHoliday);
			person.setUnwantedDays(splitted[3]);
			person.setWantedDays(splitted[4]);
			person.setWorkDays(splitted[5]);

			persons.add(person);

			string = reader.readLine();
			lineNumber++;
		}
	}

	private static void saveDays(BufferedWriter writer, Collection<Day> days)
	  throws FileNotFoundException, IOException {
		for (Day d : days) {
			writer.write(d.toString() + COLUMN +
			               (d.getOld() != null ? d.getOld().getName() : SEPARATOR) + COLUMN +
			               (d.getYoung() != null ? d.getYoung().getName() : SEPARATOR) + COLUMN +
			               (d.getThird() != null ? d.getThird().getName() : SEPARATOR) + COLUMN +
			               d.isHoliday() + COLUMN);
			writer.newLine();
		}

		writer.write(END);
		writer.newLine();
	}

	private static void loadDays(BufferedReader reader, List<Person> young, List<Person> old, List<Day> days)
	  throws IOException {
		String string = reader.readLine();
		while (string != null && !string.equals(END)) {
			System.out.println(string);

			String[] splitted = string.split(COLUMN);
			String[] date = splitted[0].split("\\.");
			for (int i = 0; i < splitted.length; i++) {
				System.out.println(i + ": " + splitted[i]);
			}
			System.out.println("--");
			for (int i = 0; i < date.length; i++) {
				System.out.println(i + ": " + date[i]);
			}
			Integer dayOfMonth = new Integer(date[0]);
			Integer month = new Integer(date[1]) - 1;
			Integer year = new Integer(date[2]);
			String oldName = splitted[1];
			String youngName = splitted[2];
			String thirdName = splitted[3];
			boolean holiday = splitted[4].equals("true") ? true : false;

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			Day day = new Day(cal, holiday);
			if (!youngName.equals(SEPARATOR)) {
				Person p = new Person(youngName, Team.YOUNG);
				if (young.contains(p)) {
					day.setYoung(young.get(young.indexOf(p)));
				} else {
					throw new IllegalArgumentException("young does not contain given name");
				}
			}

			if (!oldName.equals(SEPARATOR)) {
				Person p = new Person(oldName, Team.YOUNG);
				if (old.contains(p)) {
					day.setOld(old.get(old.indexOf(p)));
				} else {
					throw new IllegalArgumentException("old does not contain given name");
				}
			}

			if (!thirdName.equals(SEPARATOR)) {
				Person p = new Person(thirdName, Team.YOUNG);
				if (young.contains(p)) {
					day.setThird(young.get(young.indexOf(p)));
				} else if (old.contains(p)) {
					day.setThird(old.get(old.indexOf(p)));
				} else {
					throw new IllegalArgumentException("young nor old does not contain given name");
				}
			}

			days.add(day);

			string = reader.readLine();
		}
	}
}

