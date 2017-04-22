package cz.yiri.kus.sluzby;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;

import org.odftoolkit.odfdom.OdfFileDom;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.doc.office.OdfOfficeText;
import org.odftoolkit.odfdom.doc.style.OdfDefaultStyle;
import org.odftoolkit.odfdom.doc.style.OdfStyle;
import org.odftoolkit.odfdom.doc.style.OdfStyleParagraphProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTableColumnProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTextProperties;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;
import org.odftoolkit.odfdom.doc.text.OdfTextHeading;
import org.odftoolkit.odfdom.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.dom.element.OdfStyleBase;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.w3c.dom.Node;


public class SimpleOdt {
	private static String outputFileName;
	private static OdfTextDocument outputDocument;
	private static OdfFileDom contentDom;
	private static OdfFileDom stylesDom;
	private static OdfOfficeAutomaticStyles contentAutoStyles;
	private static OdfOfficeStyles stylesOfficeStyles;
	private static OdfOfficeText officeText;

	public static void run(List<Day> days, String fileName) {
		outputFileName = fileName + ".odt";

		setupOutputDocument();

		if (outputDocument != null) {
			cleanOutDocument();

			addOfficeStyles();

			processTitle(days);

			saveOutputDocument();
		}
	}


	public static void setupOutputDocument() {
		System.out.println("setup");
		try {
			outputDocument = OdfTextDocument.newTextDocument();
			System.out.println("content");
			contentDom = outputDocument.getContentDom();
			System.out.println("styles");
			stylesDom = outputDocument.getStylesDom();
			System.out.println("autostyles");
			contentAutoStyles = contentDom.getOrCreateAutomaticStyles();
			System.out.println("officeStyles");
			stylesOfficeStyles = outputDocument.getOrCreateDocumentStyles();
			System.out.println("root");
			officeText = outputDocument.getContentRoot();
		} catch (Exception e) {
			System.err.println("Unable to create output file.");
			System.err.println(e.getMessage());
			outputDocument = null;
		}
	}

	public static void setFontWeight(OdfStyleBase style, String value) {
		System.out.println("setFontWeight");
		style.setProperty(OdfStyleTextProperties.FontWeight, value);
		style.setProperty(OdfStyleTextProperties.FontWeightAsian, value);
		style.setProperty(OdfStyleTextProperties.FontWeightComplex, value);
	}

	public static void setFontStyle(OdfStyleBase style, String value) {
		System.out.println("setFontStyle");
		style.setProperty(OdfStyleTextProperties.FontStyle, value);
		style.setProperty(OdfStyleTextProperties.FontStyleAsian, value);
		style.setProperty(OdfStyleTextProperties.FontStyleComplex, value);
	}

	public static void setFontSize(OdfStyleBase style, String value) {
		System.out.println("setFontSize");
		style.setProperty(OdfStyleTextProperties.FontSize, value);
		style.setProperty(OdfStyleTextProperties.FontSizeAsian, value);
		style.setProperty(OdfStyleTextProperties.FontSizeComplex, value);
	}

	public static void addOfficeStyles() {
		System.out.println("addOfficeStyles");


		OdfDefaultStyle defaultStyle = stylesOfficeStyles.getDefaultStyle(OdfStyleFamily.Paragraph);

		setFontSize(defaultStyle, "10pt");


		OdfStyle style = stylesOfficeStyles.newStyle("SluzbyHeading1", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyHeading1");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.25cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.25cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

		setFontSize(style, "24pt");


		style = stylesOfficeStyles.newStyle("SluzbyTG", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyTG");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyT", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyT");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyLG", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyLG");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyL", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyL");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyR", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyR");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyRG", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyRG");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyB", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyB");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyBG", OdfStyleFamily.Paragraph);


		style.setStyleDisplayNameAttribute("SluzbyBG");
		style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
		style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
		setFontSize(style, "15pt");


		style = stylesOfficeStyles.newStyle("SluzbyColumn1", OdfStyleFamily.TableColumn);


		style.setStyleDisplayNameAttribute("SluzbyColumn1");
		style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "2.485cm");


		style = stylesOfficeStyles.newStyle("SluzbyColumn2", OdfStyleFamily.TableColumn);


		style.setStyleDisplayNameAttribute("SluzbyColumn2");
		style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "4.500cm");


		style = stylesOfficeStyles.newStyle("SluzbyColumn3", OdfStyleFamily.TableColumn);


		style.setStyleDisplayNameAttribute("SluzbyColumn3");
		style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "5.000cm");


		style = stylesOfficeStyles.newStyle("SluzbyRow1", OdfStyleFamily.TableRow);


		style.setStyleDisplayNameAttribute("SluzbyRow1");
		style.setAttribute("style:keep-together", "true");
		style.setAttribute("fo:keep-together", "auto");
	}


	public static void cleanOutDocument() {
		System.out.println("cleanup");


		Node childNode = officeText.getFirstChild();
		while (childNode != null) {
			officeText.removeChild(childNode);
			childNode = officeText.getFirstChild();
		}
	}


	public static void processTitle(List<Day> days) {
		System.out.println("process");


		OdfTextHeading heading = new OdfTextHeading(contentDom);
		heading.addStyledContent("SluzbyHeading1", ((Day) days.get(1)).monthAndYearToString());

		officeText.appendChild(heading);

		OdfTable table = new OdfTable(contentDom);

		table.addStyledTableColumn("SluzbyColumnStyle1");
		table.addStyledTableColumn("SluzbyColumnStyle2");
		table.addStyledTableColumn("SluzbyColumnStyle3");
		table.addStyledTableColumn("SluzbyColumnStyle3");

		OdfTableRow headRow = new OdfTableRow(contentDom);

		OdfTableCell datum = new OdfTableCell(contentDom);
		datum.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "DATUM"));
		headRow.appendCell(datum);

		OdfTableCell starsi = new OdfTableCell(contentDom);
		starsi.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "INTERNA"));
		headRow.appendCell(starsi);

		OdfTableCell mladsi = new OdfTableCell(contentDom);
		mladsi.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "INTERNA"));
		headRow.appendCell(mladsi);

		OdfTableCell dialyza = new OdfTableCell(contentDom);
		dialyza.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "DIALÝZA"));
		headRow.appendCell(dialyza);

		table.appendRow(headRow);
		for (Day d : days) {
			OdfTableRow row = new OdfTableRow(contentDom);

			String bgColor = d.isHoliday() ? "G" : "";

			for (int j = 0; j < 4; j++) {
				String text = "";
				String cellType;
				String cellType;
				switch (j) {
					case 0:
						String cellType;
						if (d.getDay().get(5) == 1) {
							cellType = "T";
						} else {
							cellType = "L";
						}
						break;
					default:
						if (d.getDay().get(5) == 1) {
							cellType = "R";
						} else {
							cellType = "B";
						}
						break;
				}

				OdfTableCell cell = new OdfTableCell(contentDom);

				cell.appendChild(new OdfTextParagraph(contentDom, "Sluzby" + cellType + bgColor, d.get(j)));
				row.appendCell(cell);
			}
			table.appendRow(row);
		}

		officeText.appendChild(table);
	}


	public static void saveOutputDocument() {
		System.out.println("save");
		try {
			outputDocument.save(outputFileName);
		} catch (Exception e) {
			System.err.println("Unable to save document.");
			System.err.println(e.getMessage());
		}
	}
}
