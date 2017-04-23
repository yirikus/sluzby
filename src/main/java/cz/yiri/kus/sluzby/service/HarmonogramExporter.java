package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author terrmith
 */
public class HarmonogramExporter {

	private List<Day> days;
	private String filename;

	public HarmonogramExporter(List<Day> days, String filename) {
		this.filename = filename;
		this.days = days;
	}

	public void exportAsODT() {
		if (days == null) {
			throw new IllegalStateException("List of days is null");
		} else if (days.size() < 1) {
			throw new IllegalArgumentException("The size of days list must not be lower than 1");
		}
		OdtWriter.writeDocument(days, filename);

	}

	public void exportAsHTML() {
		if (days == null) {
			throw new IllegalStateException("List of days is null");
		} else if (days.size() < 1) {
			throw new IllegalArgumentException("The size of days list must not be lower than 1");
		}
		HtmlWriter.writeDocument(days, filename);
	}
}
