/*     */ package sluzby2;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
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
/*     */ public class HarmonogramExporter
/*     */ {
/*     */   private List<Day> days;
/*     */   private String filename;
/*     */   
/*     */   public HarmonogramExporter(List<Day> days, String filename)
/*     */   {
/*  45 */     this.filename = filename;
/*  46 */     this.days = days;
/*     */   }
/*     */   
/*     */   public void exportAsODT() {
/*  50 */     if (this.days == null)
/*  51 */       throw new NullPointerException("List of days is null");
/*  52 */     if (this.days.size() < 1) {
/*  53 */       throw new IllegalArgumentException("The size of days list must not be lower than 1");
/*     */     }
/*  55 */     SimpleOdt.run(this.days, this.filename);
/*     */   }
/*     */   
/*     */   public void exportAsHTML() {
/*  59 */     if (this.days == null)
/*  60 */       throw new NullPointerException("List of days is null");
/*  61 */     if (this.days.size() < 1) {
/*  62 */       throw new IllegalArgumentException("The size of days list must not be lower than 1");
/*     */     }
/*     */     try
/*     */     {
/*  66 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filename + ".html")));
/*     */       
/*  68 */       writer.write("<html><head><title>Sluzby</title><style>");writer.newLine();
/*  69 */       writer.write("table{border:2px solid black;border-collapse:collapse}");writer.newLine();
/*  70 */       writer.write("td{width:150;height:20;border:1px solid black;border-collapse:collapse}");writer.newLine();
/*  71 */       writer.write("</style></head><body><div style=\"width:610;height:80;text-align:center;border:2px solid black;font-size:16px\">");writer.newLine();
/*  72 */       writer.write("<br>Sluzby primářů a lékařů na interním,infekčním a nefrologickém oddělení Nemocnice Písek</div><table>");
/*  73 */       writer.write("<tr><td><b>DATUM</b><td><b>INTERNA</b><td><b>INTERNA</b><td><b>DIALYZA</b></tr>");
/*  74 */       writer.newLine();
/*  75 */       for (Day d : this.days) {
/*  76 */         String colour = "";
/*  77 */         writer.write("<tr>");
/*  78 */         if (d.isHoliday()) {
/*  79 */           colour = " style=\"background-color:#CCCCCC\"";
/*     */         }
/*  81 */         writer.write("<td" + colour + ">" + d.print());
/*     */         
/*  83 */         Person p = d.getOld();
/*  84 */         String name = p == null ? " " : p.getName();
/*  85 */         writer.write("<td" + colour + ">" + name);
/*     */         
/*  87 */         p = d.getYoung();
/*  88 */         name = p == null ? " " : p.getName();
/*  89 */         writer.write("<td" + colour + ">" + name);
/*     */         
/*  91 */         p = d.getThird();
/*  92 */         name = p == null ? " " : p.getName();
/*  93 */         writer.write("<td" + colour + ">" + name);
/*  94 */         writer.write("</tr>");
/*  95 */         writer.newLine();
/*     */       }
/*  97 */       writer.write("</table></body></html>");
/*  98 */       writer.close();
/*     */     }
/*     */     catch (IOException ioe) {
/* 101 */       System.err.println("IOE");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\HarmonogramExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */