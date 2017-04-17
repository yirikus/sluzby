/*     */ package sluzby2;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ public class PersonTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*  19 */   private List<Person> persons = new ArrayList();
/*     */   
/*     */   public int getRowCount() {
/*  22 */     return this.persons.size();
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/*  26 */     return 4;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int rowIndex, int columnIndex) {
/*  30 */     Person person = (Person)this.persons.get(rowIndex);
/*  31 */     switch (columnIndex) {
/*     */     case 0: 
/*  33 */       return person.getName();
/*     */     case 1: 
/*  35 */       return Boolean.valueOf(person.isWantsOnly());
/*     */     case 2: 
/*  37 */       return person.wantedDaysToString();
/*     */     case 3: 
/*  39 */       return person.unwantedDaysToString();
/*     */     }
/*  41 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getColumnName(int columnIndex)
/*     */   {
/*  48 */     switch (columnIndex) {
/*     */     case 0: 
/*  50 */       return "Jmeno";
/*     */     case 1: 
/*  52 */       return "Chce pouze?";
/*     */     case 2: 
/*  54 */       return "Chce";
/*     */     case 3: 
/*  56 */       return "Nechce";
/*     */     }
/*  58 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> getColumnClass(int columnIndex)
/*     */   {
/*  64 */     switch (columnIndex) {
/*     */     case 0: 
/*     */     case 2: 
/*     */     case 3: 
/*  68 */       return String.class;
/*     */     case 1: 
/*  70 */       return Boolean.class;
/*     */     }
/*     */     
/*  73 */     throw new IllegalArgumentException("columnIndex");
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValueAt(Object aValue, int rowIndex, int columnIndex)
/*     */   {
/*  79 */     Person person = (Person)this.persons.get(rowIndex);
/*  80 */     switch (columnIndex) {
/*     */     case 0: 
/*  82 */       person.setName((String)aValue);
/*  83 */       break;
/*     */     case 1: 
/*  85 */       person.setWantsOnly(((Boolean)aValue).booleanValue());
/*  86 */       break;
/*     */     case 2: 
/*  88 */       person.setWantedDays((String)aValue);
/*  89 */       break;
/*     */     case 3: 
/*  91 */       person.setUnwantedDays((String)aValue);
/*  92 */       break;
/*     */     
/*     */ 
/*     */     default: 
/*  96 */       throw new IllegalArgumentException("columnIndex");
/*     */     }
/*  98 */     fireTableCellUpdated(rowIndex, columnIndex);
/*  99 */     if (person.getName().equals("")) {
/* 100 */       this.persons.remove(person);
/* 101 */       fireTableRowsDeleted(rowIndex, rowIndex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 107 */   public boolean isCellEditable(int rowIndex, int columnIndex) { return true; }
/*     */   
/*     */   public void addPerson(Person person) {
/* 110 */     this.persons.add(person);
/* 111 */     int lastRow = this.persons.size() - 1;
/* 112 */     fireTableRowsInserted(lastRow, lastRow);
/*     */   }
/*     */   
/*     */   public void updateTable(List<Person> newPpl)
/*     */   {
/* 117 */     this.persons.clear();
/* 118 */     this.persons.addAll(newPpl);
/* 119 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public Collection<Person> getPersons()
/*     */   {
/* 124 */     return this.persons;
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\PersonTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */