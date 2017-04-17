/*    */ package sluzby2;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ColorRenderer extends javax.swing.table.DefaultTableCellRenderer
/*    */ {
/*    */   private List<Person> persons;
/*    */   private Team team;
/*    */   
/*    */   private static class Colors
/*    */   {
/* 14 */     private static final int[] colors = { 16777215, 9868950, 16771209, 16758153, 16746889, 12713865, 9043913, 9210367, 14453247, 14601224, 2874888, 14575624, 575454, 12060894, 9400399, 11628118, 6074710, 11109583, 5553033, 16754688, 16730112, 16711680, 16711866, 34047, 64767, 65340 };
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     public static Color getColor(int index)
/*    */     {
/* 53 */       if (index >= 0) {
/* 54 */         return new Color(colors[(index % colors.length)]);
/*    */       }
/* 56 */       return Color.WHITE;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ColorRenderer(List<Person> persons, Team team)
/*    */   {
/* 67 */     this.persons = persons;
/* 68 */     this.team = team;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*    */   {
/* 76 */     Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/*    */     
/*    */ 
/* 79 */     if (this.persons == null) {
/* 80 */       throw new NullPointerException("List<Person> persons must not be null, call setPersons method");
/*    */     }
/* 82 */     if ((value instanceof String))
/*    */     {
/* 84 */       Person mockPerson = new Person((String)value, this.team);
/* 85 */       if (mockPerson != null) {
/* 86 */         System.out.println("colorRenderer:" + this.persons.size());
/* 87 */         Color color = Colors.getColor(this.persons.indexOf(mockPerson));
/* 88 */         cell.setBackground(color);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 93 */     return cell;
/*    */   }
/*    */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\ColorRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */