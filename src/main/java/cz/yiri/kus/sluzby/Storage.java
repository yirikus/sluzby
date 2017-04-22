package cz.yiri.kus.sluzby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;


public class Storage {
	private static final String DATA_FILE = "data.txt";
	private static final String COLUMN = ":";
	private static final String SEPARATOR = "#";
	private static final String END = "&";

	public static void save(Collection<Person> young, Collection<Person> old, Collection<Day> days) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt")));

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


	public static void load(List<Person> young, List<Person> old, List<Day> days) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt")));

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


	private static void savePersons(BufferedWriter writer, Collection<Person> persons)
	  throws FileNotFoundException, IOException {
		for (Person p : persons) {
			writer.write(p.getName() + ":" + p.isWantsOnly() + ":" + p.hasHoliday() + ":");


			writer.write("#");
			for (Integer i : p.getUnwantedDays()) {
				writer.write(i + "#");
			}
			writer.write(":");
			writer.write("#");
			for (Integer i : p.getWantedDays()) {
				writer.write(i + "#");
			}
			writer.write(":");
			writer.write("#");
			for (Integer i : p.getWorkDays()) {
				writer.write(i + "#");
			}
			writer.write(":");
			writer.newLine();
		}
		writer.write("&");
		writer.newLine();
	}

	private static void loadPersons(BufferedReader reader, Collection<Person> persons, Team team) throws IOException {
		String string = reader.readLine();
		while ((string != null) && (!string.equals("&"))) {
			System.out.println("loadPersons.reading: " + string);
			String[] splitted = string.split(":");
			for (int i = 0; i < splitted.length; i++) {
				System.out.println(i + ": " + splitted[i]);
			}
			String name = splitted[0];
			boolean wantsOnly = splitted[1].equals("true");
			boolean hasHoliday = splitted[2].equals("true");

			Person person = new Person(name, team);
			person.setWantsOnly(wantsOnly);
			person.setHasHoliday(hasHoliday);
			person.setUnwantedDays(splitted[3]);
			person.setWantedDays(splitted[4]);
			person.setWorkDays(splitted[5]);

			persons.add(person);

			string = reader.readLine();
		}
	}

	private static void saveDays(BufferedWriter writer, Collection<Day> days)
	  throws FileNotFoundException, IOException {
		for (Day d : days) {
			writer.write(
			  d.toString() + ":" + (d.getOld() != null ? d.getOld().getName() : "#") + ":" + (d.getYoung() != null ?
				d.getYoung().getName() : "#") + ":" + (d.getThird() != null ? d.getThird().getName() : "#") + ":" + d
				.isHoliday() + ":");


			writer.newLine();
		}

		writer.write("&");
		writer.newLine();
	}

	private static void loadDays(BufferedReader reader, List<Person> young, List<Person> old, List<Day> days)
	  throws IOException {
		String string = reader.readLine();
		while ((string != null) && (!string.equals("&"))) {
			System.out.println(string);

			String[] splitted = string.split(":");
			String[] date = splitted[0].split("\\.");
			for (int i = 0; i < splitted.length; i++) {
				System.out.println(i + ": " + splitted[i]);
			}
			System.out.println("--");
			for (int i = 0; i < date.length; i++) {
				System.out.println(i + ": " + date[i]);
			}
			Integer dayOfMonth = new Integer(date[0]);
			Integer month = Integer.valueOf(new Integer(date[1]).intValue() - 1);
			Integer year = new Integer(date[2]);
			String oldName = splitted[1];
			String youngName = splitted[2];
			String thirdName = splitted[3];
			boolean holiday = splitted[4].equals("true");

			Calendar cal = Calendar.getInstance();
			cal.set(1, year.intValue());
			cal.set(2, month.intValue());
			cal.set(5, dayOfMonth.intValue());

			Day day = new Day(cal, holiday);
			if (!youngName.equals("#")) {
				Person p = new Person(youngName, Team.YOUNG);
				if (young.contains(p)) {
					day.setYoung((Person) young.get(young.indexOf(p)));
				} else {
					throw new IllegalArgumentException("young does not contain given name");
				}
			}

			if (!oldName.equals("#")) {
				Person p = new Person(oldName, Team.YOUNG);
				if (old.contains(p)) {
					day.setOld((Person) old.get(old.indexOf(p)));
				} else {
					throw new IllegalArgumentException("old does not contain given name");
				}
			}

			if (!thirdName.equals("#")) {
				Person p = new Person(thirdName, Team.YOUNG);
				if (young.contains(p)) {
					day.setThird((Person) young.get(young.indexOf(p)));
				} else if (old.contains(p)) {
					day.setThird((Person) old.get(old.indexOf(p)));
				} else {
					throw new IllegalArgumentException("young nor old does not contain given name");
				}
			}

			days.add(day);

			string = reader.readLine();
		}
	}
}