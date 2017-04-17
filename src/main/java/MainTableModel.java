/*     */ package sluzby2;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private List<Day> days;
/*     */   private PersonTableModel oldModel;
/*     */   private PersonTableModel youngModel;
/*     */   
/*     */   public MainTableModel(PersonTableModel oldModel, PersonTableModel youngModel)
/*     */   {
/*  24 */     this.days = new ArrayList();
/*  25 */     this.oldModel = oldModel;
/*  26 */     this.youngModel = youngModel;
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/*  31 */     return this.days.size();
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/*  35 */     return 5;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int rowIndex, int columnIndex) {
/*  39 */     Day day = (Day)this.days.get(rowIndex);
/*  40 */     switch (columnIndex) {
/*     */     case 0: 
/*  42 */       return day.print();
/*     */     case 1: 
/*  44 */       return Boolean.valueOf(day.isHoliday());
/*     */     case 2: 
/*  46 */       if (day.getOld() == null) {
/*  47 */         return "-";
/*     */       }
/*  49 */       return day.getOld().getName();
/*     */     
/*     */     case 3: 
/*  52 */       if (day.getYoung() == null) {
/*  53 */         return "-";
/*     */       }
/*  55 */       return day.getYoung().getName();
/*     */     
/*     */     case 4: 
/*  58 */       if (day.getThird() == null) {
/*  59 */         return "-";
/*     */       }
/*  61 */       return day.getThird().getName();
/*     */     }
/*     */     
/*  64 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getColumnName(int columnIndex)
/*     */   {
/*  71 */     switch (columnIndex) {
/*     */     case 0: 
/*  73 */       return "Datum";
/*     */     case 1: 
/*  75 */       return "Svátek";
/*     */     case 2: 
/*  77 */       return "Interna - starší";
/*     */     case 3: 
/*  79 */       return "Interna - mladší";
/*     */     case 4: 
/*  81 */       return "Dialýza";
/*     */     }
/*  83 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> getColumnClass(int columnIndex)
/*     */   {
/*  89 */     switch (columnIndex) {
/*     */     case 1: 
/*  91 */       return Boolean.class;
/*     */     case 0: 
/*     */     case 2: 
/*     */     case 3: 
/*     */     case 4: 
/*  96 */       return String.class;
/*     */     }
/*     */     
/*  99 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValueAt(Object aValue, int rowIndex, int columnIndex)
/*     */   {
/* 105 */     Day day = (Day)this.days.get(rowIndex);
/*     */     Person p;
/*     */     Person o;
/* 108 */     switch (columnIndex) {
/*     */     case 1: 
/* 110 */       day.setHoliday(((Boolean)aValue).booleanValue());
/* 111 */       break;
/*     */     
/*     */     case 2: 
/* 114 */       if ((this.oldModel.getPersons() == null) || (this.oldModel.getPersons().isEmpty())) {
/* 115 */         System.out.println("mainTab leModel.column2.null or empty");
/* 116 */         return;
/*     */       }
/*     */       
/* 119 */       p = new Person((String)aValue, Team.OLD);
/*     */       
/* 121 */       List<Person> ps = new ArrayList();
/* 122 */       ps.addAll(this.oldModel.getPersons());
/*     */       
/*     */ 
/*     */ 
/* 126 */       o = day.getOld();
/* 127 */       if (o != null) {
/* 128 */         o.removeUsed(day);
/*     */       }
/*     */       
/* 131 */       if (ps.contains(p)) {
/* 132 */         p = (Person)ps.get(ps.indexOf(p));
/*     */       } else {
/* 134 */         throw new IllegalArgumentException();
/*     */       }
/* 136 */       day.setOld(p);
/* 137 */       break;
/*     */     
/*     */     case 3: 
/* 140 */       if ((this.youngModel.getPersons() == null) || (this.youngModel.getPersons().isEmpty())) {
/* 141 */         System.out.println("mainTableModel.column3.null or empty");
/* 142 */         return;
/*     */       }
/* 144 */       p = new Person((String)aValue, Team.YOUNG);
/*     */       
/* 146 */       List<Person> ps2 = new ArrayList();
/* 147 */       ps2.addAll(this.youngModel.getPersons());
/*     */       
/* 149 */       o = day.getYoung();
/* 150 */       if (o != null) {
/* 151 */         o.removeUsed(day);
/*     */       }
/*     */       
/* 154 */       if (ps2.contains(p)) {
/* 155 */         p = (Person)ps2.get(ps2.indexOf(p));
/*     */       } else {
/* 157 */         throw new IllegalArgumentException();
/*     */       }
/* 159 */       day.setYoung(p);
/* 160 */       break;
/*     */     
/*     */     case 4: 
/* 163 */       if ((this.oldModel.getPersons() == null) || (this.oldModel.getPersons().isEmpty())) {
/* 164 */         System.out.println("mainTableModel.column4.null or empty");
/* 165 */         return;
/*     */       }
/* 167 */       if ((this.youngModel.getPersons() == null) || (this.youngModel.getPersons().isEmpty())) {
/* 168 */         System.out.println("mainTableModel.column4.null or empty");
/* 169 */         return;
/*     */       }
/* 171 */       Person pyj = new Person((String)aValue, Team.YOUNG);
/*     */       
/* 173 */       List<Person> pyjs = new ArrayList();
/* 174 */       pyjs.addAll(this.youngModel.getPersons());
/* 175 */       List<Person> pojs = new ArrayList();
/* 176 */       pojs.addAll(this.oldModel.getPersons());
/*     */       
/* 178 */       o = day.getThird();
/*     */       
/* 180 */       if (pyjs.contains(pyj)) {
/* 181 */         p = (Person)pyjs.get(pyjs.indexOf(pyj)); } else { Person p;
/* 182 */         if (pojs.contains(pyj)) {
/* 183 */           p = (Person)pojs.get(pojs.indexOf(pyj));
/*     */         } else
/* 185 */           throw new IllegalArgumentException();
/*     */       }
/*     */       Person p;
/* 188 */       day.setThird(p);
/* 189 */       break;
/*     */     
/*     */     default: 
/* 192 */       throw new IllegalArgumentException("columnIndex");
/*     */     }
/* 194 */     fireTableCellUpdated(rowIndex, columnIndex);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCellEditable(int rowIndex, int columnIndex)
/*     */   {
/* 200 */     if (columnIndex == 0) {
/* 201 */       return false;
/*     */     }
/* 203 */     return true;
/*     */   }
/*     */   
/*     */   public void updateTable(List<Day> days) {
/* 207 */     this.days = days;
/* 208 */     System.out.println("###updateTable: " + this.days.size());
/* 209 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public List<Day> getDays() {
/* 213 */     System.out.println("###getDays: " + this.days.size());
/* 214 */     return this.days;
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\MainTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */