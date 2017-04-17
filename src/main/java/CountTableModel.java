/*    */ package sluzby2;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.swing.table.AbstractTableModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CountTableModel
/*    */   extends AbstractTableModel
/*    */ {
/* 19 */   private List<Person> persons = new ArrayList();
/*    */   
/*    */   public int getRowCount() {
/* 22 */     return this.persons.size();
/*    */   }
/*    */   
/*    */   public int getColumnCount() {
/* 26 */     return 4;
/*    */   }
/*    */   
/*    */   public Object getValueAt(int rowIndex, int columnIndex) {
/* 30 */     Person person = (Person)this.persons.get(rowIndex);
/* 31 */     switch (columnIndex) {
/*    */     case 0: 
/* 33 */       return person.getName();
/*    */     case 1: 
/* 35 */       if (person.getTeam() == Team.OLD) {
/* 36 */         return "starší";
/*    */       }
/* 38 */       return "mladší";
/*    */     
/*    */     case 2: 
/* 41 */       return Integer.valueOf(person.sizeOfWorkingDays());
/*    */     case 3: 
/* 43 */       StringBuilder sb = new StringBuilder();
/* 44 */       for (Integer i : person.getWorkDays()) {
/* 45 */         if (!person.canWorkOn(i)) {
/* 46 */           sb.append("").append(i).append(",");
/*    */         }
/*    */       }
/* 49 */       return sb.toString();
/*    */     }
/* 51 */     throw new IllegalArgumentException("columnIndex");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getColumnName(int columnIndex)
/*    */   {
/* 58 */     switch (columnIndex) {
/*    */     case 0: 
/* 60 */       return "Jméno";
/*    */     case 1: 
/* 62 */       return "Tým";
/*    */     case 2: 
/* 64 */       return "Počet služeb";
/*    */     case 3: 
/* 66 */       return "Nechce";
/*    */     }
/* 68 */     throw new IllegalArgumentException("columnIndex");
/*    */   }
/*    */   
/*    */ 
/*    */   public Class<?> getColumnClass(int columnIndex)
/*    */   {
/* 74 */     switch (columnIndex) {
/*    */     case 0: 
/*    */     case 1: 
/*    */     case 3: 
/* 78 */       return String.class;
/*    */     case 2: 
/* 80 */       return Integer.class;
/*    */     }
/*    */     
/* 83 */     throw new IllegalArgumentException("columnIndex");
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isCellEditable(int rowIndex, int columnIndex)
/*    */   {
/* 89 */     return false;
/*    */   }
/*    */   
/*    */   public void updateTable(Collection<Person> old, Collection<Person> young) {
/* 93 */     this.persons.clear();
/* 94 */     this.persons.addAll(old);
/* 95 */     this.persons.addAll(young);
/* 96 */     fireTableDataChanged();
/*    */   }
/*    */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\CountTableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */