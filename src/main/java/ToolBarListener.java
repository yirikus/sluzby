/*     */ package sluzby2;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
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
/*     */ public class ToolBarListener
/*     */   implements ActionListener
/*     */ {
/*     */   private JTabbedPane tabbedPane;
/*     */   private JTextField textField;
/*     */   private PersonTableModel youngModel;
/*     */   private PersonTableModel oldModel;
/*     */   private MainTableModel mainModel;
/*     */   private boolean tableCreated;
/*     */   private JTable mainTable;
/*     */   private HarmonogramExporter exporter;
/*     */   
/*     */   public ToolBarListener(JTabbedPane tabbedPane, JTextField textField, PersonTableModel youngModel, PersonTableModel oldModel, MainTableModel mainModel, JTable mainTable)
/*     */   {
/*  36 */     this.tabbedPane = tabbedPane;
/*  37 */     this.textField = textField;
/*  38 */     this.oldModel = oldModel;
/*  39 */     this.youngModel = youngModel;
/*  40 */     this.mainModel = mainModel;
/*  41 */     this.mainTable = mainTable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  51 */     if ("CREATE".equals(e.getActionCommand())) {
/*  52 */       this.mainModel.updateTable((List)HarmonogramFactory.newHarmonogram(this.youngModel.getPersons(), this.oldModel.getPersons()));
/*  53 */       this.tableCreated = false;
/*  54 */       System.out.println(this.tableCreated);
/*     */ 
/*     */ 
/*     */     }
/*  58 */     else if ("PROCESS".equals(e.getActionCommand())) {
/*  59 */       if (this.tableCreated) {
/*  60 */         System.out.println("option 1 - " + this.tableCreated);
/*  61 */         this.mainModel.updateTable((List)HarmonogramFactory.newHarmonogram(this.youngModel.getPersons(), this.oldModel.getPersons()));
/*  62 */         this.mainModel.updateTable((List)HarmonogramFactory.fillHarmonogram(this.mainModel.getDays(), this.youngModel.getPersons(), this.oldModel.getPersons()));
/*     */       }
/*     */       else {
/*  65 */         System.out.println("option 2 - " + this.tableCreated);
/*  66 */         this.mainModel.updateTable((List)HarmonogramFactory.fillHarmonogram(this.mainModel.getDays(), this.youngModel.getPersons(), this.oldModel.getPersons()));
/*  67 */         this.tableCreated = true;
/*     */       }
/*  69 */     } else if ("WRITE".equals(e.getActionCommand())) {
/*  70 */       this.exporter = new HarmonogramExporter(this.mainModel.getDays(), "tabulka");
/*  71 */       this.exporter.exportAsHTML();
/*  72 */       this.exporter.exportAsODT();
/*     */ 
/*     */ 
/*     */     }
/*  76 */     else if ("SAVE".equals(e.getActionCommand())) {
/*  77 */       Storage.save(this.youngModel.getPersons(), this.oldModel.getPersons(), this.mainModel.getDays());
/*     */     }
/*  79 */     else if ("LOAD".equals(e.getActionCommand())) {
/*  80 */       List<Person> loadedYoung = new ArrayList();
/*  81 */       List<Person> loadedOld = new ArrayList();
/*  82 */       List<Day> loadedDays = new ArrayList();
/*  83 */       Storage.load(loadedYoung, loadedOld, loadedDays);
/*     */       
/*  85 */       this.youngModel.updateTable(loadedYoung);
/*  86 */       this.oldModel.updateTable(loadedOld);
/*  87 */       this.mainModel.updateTable(loadedDays);
/*     */       
/*  89 */       Main.setCellRendererAsComboBox(2, this.mainTable, this.oldModel.getPersons());
/*  90 */       Main.setCellRendererAsComboBox(3, this.mainTable, this.youngModel.getPersons());
/*  91 */       List<Person> youngAndOld = new ArrayList();
/*  92 */       youngAndOld.addAll(this.oldModel.getPersons());
/*  93 */       youngAndOld.addAll(this.youngModel.getPersons());
/*  94 */       Main.setCellRendererAsComboBox(4, this.mainTable, youngAndOld);
/*     */     }
/*  96 */     else if ("ADD".equals(e.getActionCommand())) {
/*  97 */       System.out.println("toolBarListener.ADD");
/*  98 */       int index = this.tabbedPane.getSelectedIndex();
/*  99 */       if (index == 2) {
/* 100 */         this.oldModel.addPerson(new Person(this.textField.getText(), Team.OLD));
/* 101 */       } else if (index == 3) {
/* 102 */         this.youngModel.addPerson(new Person(this.textField.getText(), Team.YOUNG));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\ToolBarListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */