package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.Day;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;

/**
 * @author jiri.kus
 */
public class OdtWriter {

	public static void writeDocument(java.util.List<Day> days, String fileName) {

		if (days == null || fileName == null) {
			throw new IllegalArgumentException("days and fileName must not be null!");
		}

		TextDocument outputOdt;
		try {
			outputOdt = TextDocument.newTextDocument();

			createHeader(days, outputOdt);

			// add table
			createTable(days, outputOdt);

			outputOdt.save(fileName + "_" + days.get(0).monthAndYearToString("_") + ".odt");
		} catch (Exception e) {
			throw new RuntimeException("ERROR: unable to create output file: " + e.getMessage(), e);
		}
	}

	private static void createHeader(java.util.List<Day> days, TextDocument outputOdt) {
		Paragraph paragraph = outputOdt.addParagraph(days.get(0).monthAndYearToString(null));
		Font font = new Font("Arial", StyleTypeDefinitions.FontStyle.REGULAR, 24, Color.BLACK);
		paragraph.setFont(font);
		paragraph.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);

	}

	private static void createTable(java.util.List<Day> days, TextDocument outputOdt) {
		Table table = outputOdt.addTable(days.size() + 1, 4);
		addHeader(table);
		for (int i = 0; i < days.size(); i++) {
			addRow(days.get(i), table, i + 1);
		}
	}

	private static void addHeader(Table table) {
		int index = 0;
		Cell cell = table.getCellByPosition(0, index);
		cell.setStringValue("DEN");

		cell = table.getCellByPosition(1, index);
		cell.setStringValue("INTERNA");

		cell = table.getCellByPosition(2, index);
		cell.setStringValue("INTERNA");

		cell = table.getCellByPosition(3, index);
		cell.setStringValue("DIALÝZA");

	}

	private static void addRow(Day day, Table table, int index) {
		Cell cell = table.getCellByPosition(0, index);
		cell.setStringValue(day.print());
		setCellColor(day, cell);

		cell = table.getCellByPosition(1, index);
		cell.setStringValue(day.getOld() != null ? day.getOld().getName() : "");
		setCellColor(day, cell);

		cell = table.getCellByPosition(2, index);
		cell.setStringValue(day.getYoung() != null ? day.getYoung().getName() : "");
		setCellColor(day, cell);

		cell = table.getCellByPosition(3, index);
		cell.setStringValue(day.getThird() != null ? day.getThird().getName() : "");
		setCellColor(day, cell);
	}

	private static void setCellColor(Day day, Cell cell) {
		if (day.isHoliday()) {
			cell.setCellBackgroundColor(new Color("#CCCCCC"));
		}
	}
}
