/*     */ package sluzby2;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.odftoolkit.odfdom.OdfFileDom;
/*     */ import org.odftoolkit.odfdom.doc.OdfTextDocument;
/*     */ import org.odftoolkit.odfdom.doc.office.OdfOfficeAutomaticStyles;
/*     */ import org.odftoolkit.odfdom.doc.office.OdfOfficeStyles;
/*     */ import org.odftoolkit.odfdom.doc.office.OdfOfficeText;
/*     */ import org.odftoolkit.odfdom.doc.style.OdfDefaultStyle;
/*     */ import org.odftoolkit.odfdom.doc.style.OdfStyle;
/*     */ import org.odftoolkit.odfdom.doc.style.OdfStyleParagraphProperties;
/*     */ import org.odftoolkit.odfdom.doc.style.OdfStyleTableColumnProperties;
/*     */ import org.odftoolkit.odfdom.doc.style.OdfStyleTextProperties;
/*     */ import org.odftoolkit.odfdom.doc.table.OdfTable;
/*     */ import org.odftoolkit.odfdom.doc.table.OdfTableCell;
/*     */ import org.odftoolkit.odfdom.doc.table.OdfTableRow;
/*     */ import org.odftoolkit.odfdom.doc.text.OdfTextHeading;
/*     */ import org.odftoolkit.odfdom.doc.text.OdfTextParagraph;
/*     */ import org.odftoolkit.odfdom.dom.element.OdfStyleBase;
/*     */ import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleOdt
/*     */ {
/*     */   private static String outputFileName;
/*     */   private static OdfTextDocument outputDocument;
/*     */   private static OdfFileDom contentDom;
/*     */   private static OdfFileDom stylesDom;
/*     */   private static OdfOfficeAutomaticStyles contentAutoStyles;
/*     */   private static OdfOfficeStyles stylesOfficeStyles;
/*     */   private static OdfOfficeText officeText;
/*     */   
/*     */   public static void run(List<Day> days, String fileName)
/*     */   {
/* 109 */     outputFileName = fileName + ".odt";
/*     */     
/* 111 */     setupOutputDocument();
/*     */     
/* 113 */     if (outputDocument != null)
/*     */     {
/* 115 */       cleanOutDocument();
/*     */       
/* 117 */       addOfficeStyles();
/*     */       
/* 119 */       processTitle(days);
/*     */       
/* 121 */       saveOutputDocument();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void setupOutputDocument()
/*     */   {
/* 128 */     System.out.println("setup");
/*     */     try
/*     */     {
/* 131 */       outputDocument = OdfTextDocument.newTextDocument();
/* 132 */       System.out.println("content");
/* 133 */       contentDom = outputDocument.getContentDom();
/* 134 */       System.out.println("styles");
/* 135 */       stylesDom = outputDocument.getStylesDom();
/* 136 */       System.out.println("autostyles");
/* 137 */       contentAutoStyles = contentDom.getOrCreateAutomaticStyles();
/* 138 */       System.out.println("officeStyles");
/* 139 */       stylesOfficeStyles = outputDocument.getOrCreateDocumentStyles();
/* 140 */       System.out.println("root");
/* 141 */       officeText = outputDocument.getContentRoot();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 145 */       System.err.println("Unable to create output file.");
/* 146 */       System.err.println(e.getMessage());
/* 147 */       outputDocument = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setFontWeight(OdfStyleBase style, String value)
/*     */   {
/* 153 */     System.out.println("setFontWeight");
/* 154 */     style.setProperty(OdfStyleTextProperties.FontWeight, value);
/* 155 */     style.setProperty(OdfStyleTextProperties.FontWeightAsian, value);
/* 156 */     style.setProperty(OdfStyleTextProperties.FontWeightComplex, value);
/*     */   }
/*     */   
/*     */   public static void setFontStyle(OdfStyleBase style, String value)
/*     */   {
/* 161 */     System.out.println("setFontStyle");
/* 162 */     style.setProperty(OdfStyleTextProperties.FontStyle, value);
/* 163 */     style.setProperty(OdfStyleTextProperties.FontStyleAsian, value);
/* 164 */     style.setProperty(OdfStyleTextProperties.FontStyleComplex, value);
/*     */   }
/*     */   
/*     */   public static void setFontSize(OdfStyleBase style, String value)
/*     */   {
/* 169 */     System.out.println("setFontSize");
/* 170 */     style.setProperty(OdfStyleTextProperties.FontSize, value);
/* 171 */     style.setProperty(OdfStyleTextProperties.FontSizeAsian, value);
/* 172 */     style.setProperty(OdfStyleTextProperties.FontSizeComplex, value);
/*     */   }
/*     */   
/*     */   public static void addOfficeStyles()
/*     */   {
/* 177 */     System.out.println("addOfficeStyles");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 182 */     OdfDefaultStyle defaultStyle = stylesOfficeStyles.getDefaultStyle(OdfStyleFamily.Paragraph);
/*     */     
/* 184 */     setFontSize(defaultStyle, "10pt");
/*     */     
/*     */ 
/* 187 */     OdfStyle style = stylesOfficeStyles.newStyle("SluzbyHeading1", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 190 */     style.setStyleDisplayNameAttribute("SluzbyHeading1");
/* 191 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.25cm");
/* 192 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.25cm");
/* 193 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/*     */     
/* 195 */     setFontSize(style, "24pt");
/*     */     
/*     */ 
/* 198 */     style = stylesOfficeStyles.newStyle("SluzbyTG", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 201 */     style.setStyleDisplayNameAttribute("SluzbyTG");
/* 202 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 203 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 204 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 205 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 206 */     style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
/* 207 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 208 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 209 */     style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
/* 210 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 213 */     style = stylesOfficeStyles.newStyle("SluzbyT", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 216 */     style.setStyleDisplayNameAttribute("SluzbyT");
/* 217 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 218 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 219 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 220 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 221 */     style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
/* 222 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 223 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 224 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 227 */     style = stylesOfficeStyles.newStyle("SluzbyLG", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 230 */     style.setStyleDisplayNameAttribute("SluzbyLG");
/* 231 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 232 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 233 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 234 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 235 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 236 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 237 */     style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
/* 238 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 241 */     style = stylesOfficeStyles.newStyle("SluzbyL", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 244 */     style.setStyleDisplayNameAttribute("SluzbyL");
/* 245 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 246 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 247 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 248 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 249 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 250 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 251 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 254 */     style = stylesOfficeStyles.newStyle("SluzbyR", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 257 */     style.setStyleDisplayNameAttribute("SluzbyR");
/* 258 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 259 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 260 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 261 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 262 */     style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
/* 263 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 264 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 267 */     style = stylesOfficeStyles.newStyle("SluzbyRG", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 270 */     style.setStyleDisplayNameAttribute("SluzbyRG");
/* 271 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 272 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 273 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 274 */     style.setProperty(OdfStyleParagraphProperties.BorderLeft, "0.018cm solid #000000");
/* 275 */     style.setProperty(OdfStyleParagraphProperties.BorderTop, "0.018cm solid #000000");
/* 276 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 277 */     style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
/* 278 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 281 */     style = stylesOfficeStyles.newStyle("SluzbyB", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 284 */     style.setStyleDisplayNameAttribute("SluzbyB");
/* 285 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 286 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 287 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 288 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 289 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 290 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 293 */     style = stylesOfficeStyles.newStyle("SluzbyBG", OdfStyleFamily.Paragraph);
/*     */     
/*     */ 
/* 296 */     style.setStyleDisplayNameAttribute("SluzbyBG");
/* 297 */     style.setProperty(OdfStyleParagraphProperties.MarginTop, "0.00cm");
/* 298 */     style.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.00cm");
/* 299 */     style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
/* 300 */     style.setProperty(OdfStyleParagraphProperties.BorderRight, "0.018cm solid #000000");
/* 301 */     style.setProperty(OdfStyleParagraphProperties.BorderBottom, "0.018cm solid #000000");
/* 302 */     style.setProperty(OdfStyleParagraphProperties.BackgroundColor, "#cccccc");
/* 303 */     setFontSize(style, "15pt");
/*     */     
/*     */ 
/* 306 */     style = stylesOfficeStyles.newStyle("SluzbyColumn1", OdfStyleFamily.TableColumn);
/*     */     
/*     */ 
/* 309 */     style.setStyleDisplayNameAttribute("SluzbyColumn1");
/* 310 */     style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "2.485cm");
/*     */     
/*     */ 
/* 313 */     style = stylesOfficeStyles.newStyle("SluzbyColumn2", OdfStyleFamily.TableColumn);
/*     */     
/*     */ 
/* 316 */     style.setStyleDisplayNameAttribute("SluzbyColumn2");
/* 317 */     style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "4.500cm");
/*     */     
/*     */ 
/* 320 */     style = stylesOfficeStyles.newStyle("SluzbyColumn3", OdfStyleFamily.TableColumn);
/*     */     
/*     */ 
/* 323 */     style.setStyleDisplayNameAttribute("SluzbyColumn3");
/* 324 */     style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "5.000cm");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 374 */     style = stylesOfficeStyles.newStyle("SluzbyRow1", OdfStyleFamily.TableRow);
/*     */     
/*     */ 
/* 377 */     style.setStyleDisplayNameAttribute("SluzbyRow1");
/* 378 */     style.setAttribute("style:keep-together", "true");
/* 379 */     style.setAttribute("fo:keep-together", "auto");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void cleanOutDocument()
/*     */   {
/* 388 */     System.out.println("cleanup");
/*     */     
/*     */ 
/* 391 */     Node childNode = officeText.getFirstChild();
/* 392 */     while (childNode != null)
/*     */     {
/* 394 */       officeText.removeChild(childNode);
/* 395 */       childNode = officeText.getFirstChild();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void processTitle(List<Day> days)
/*     */   {
/* 424 */     System.out.println("process");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 430 */     OdfTextHeading heading = new OdfTextHeading(contentDom);
/* 431 */     heading.addStyledContent("SluzbyHeading1", ((Day)days.get(1)).monthAndYearToString());
/*     */     
/* 433 */     officeText.appendChild(heading);
/*     */     
/* 435 */     OdfTable table = new OdfTable(contentDom);
/*     */     
/* 437 */     table.addStyledTableColumn("SluzbyColumnStyle1");
/* 438 */     table.addStyledTableColumn("SluzbyColumnStyle2");
/* 439 */     table.addStyledTableColumn("SluzbyColumnStyle3");
/* 440 */     table.addStyledTableColumn("SluzbyColumnStyle3");
/*     */     
/* 442 */     OdfTableRow headRow = new OdfTableRow(contentDom);
/*     */     
/* 444 */     OdfTableCell datum = new OdfTableCell(contentDom);
/* 445 */     datum.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "DATUM"));
/* 446 */     headRow.appendCell(datum);
/*     */     
/* 448 */     OdfTableCell starsi = new OdfTableCell(contentDom);
/* 449 */     starsi.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "INTERNA"));
/* 450 */     headRow.appendCell(starsi);
/*     */     
/* 452 */     OdfTableCell mladsi = new OdfTableCell(contentDom);
/* 453 */     mladsi.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "INTERNA"));
/* 454 */     headRow.appendCell(mladsi);
/*     */     
/* 456 */     OdfTableCell dialyza = new OdfTableCell(contentDom);
/* 457 */     dialyza.appendChild(new OdfTextParagraph(contentDom, "SluzbyT", "DIALÝZA"));
/* 458 */     headRow.appendCell(dialyza);
/*     */     
/* 460 */     table.appendRow(headRow);
/* 461 */     for (Day d : days) {
/* 462 */       OdfTableRow row = new OdfTableRow(contentDom);
/*     */       
/* 464 */       String bgColor = d.isHoliday() ? "G" : "";
/*     */       
/* 466 */       for (int j = 0; j < 4; j++)
/*     */       {
/* 468 */         String text = "";
/* 469 */         String cellType; String cellType; switch (j) {case 0:  String cellType;
/* 470 */           if (d.getDay().get(5) == 1) {
/* 471 */             cellType = "T";
/*     */           } else {
/* 473 */             cellType = "L";
/*     */           }
/* 475 */           break;
/*     */         default: 
/* 477 */           if (d.getDay().get(5) == 1) {
/* 478 */             cellType = "R";
/*     */           } else {
/* 480 */             cellType = "B";
/*     */           }
/*     */           break;
/*     */         }
/*     */         
/* 485 */         OdfTableCell cell = new OdfTableCell(contentDom);
/*     */         
/* 487 */         cell.appendChild(new OdfTextParagraph(contentDom, "Sluzby" + cellType + bgColor, d.get(j)));
/* 488 */         row.appendCell(cell);
/*     */       }
/* 490 */       table.appendRow(row);
/*     */     }
/*     */     
/* 493 */     officeText.appendChild(table);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void saveOutputDocument()
/*     */   {
/* 551 */     System.out.println("save");
/*     */     try
/*     */     {
/* 554 */       outputDocument.save(outputFileName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 558 */       System.err.println("Unable to save document.");
/* 559 */       System.err.println(e.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\SimpleOdt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */