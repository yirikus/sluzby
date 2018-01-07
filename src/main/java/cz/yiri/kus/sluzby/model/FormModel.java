package cz.yiri.kus.sluzby.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author jiri.kus
 */
public class FormModel {

	private Calendar date;
	private List<Person> old = new ArrayList<Person>();
	private List<Person> young = new ArrayList<Person>();
	private List<Day> days = new ArrayList<Day>();

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public List<Person> getOld() {
		return old;
	}

	public void setOld(List<Person> old) {
		this.old = old;
	}

	public List<Person> getYoung() {
		return young;
	}

	public void setYoung(List<Person> young) {
		this.young = young;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}
}
