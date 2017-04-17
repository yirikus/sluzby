/*    */ package sluzby2;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JTabbedPane;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.JToolBar;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ import javax.swing.event.ChangeListener;
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
/*    */ public class TabbedPaneListener
/*    */   implements ChangeListener
/*    */ {
/*    */   private JToolBar toolBar;
/*    */   private JButton processButton;
/*    */   private JButton saveButton;
/*    */   private JButton loadButton;
/*    */   private JButton addButton;
/*    */   private JTextField textField;
/*    */   private PersonTableModel old;
/*    */   private PersonTableModel young;
/*    */   private CountTableModel counts;
/*    */   private JTable mainTable;
/*    */   
/*    */   public TabbedPaneListener(JToolBar toolBar, JButton processButton, JButton saveButton, JButton loadButton, JButton addButton, JTextField textField, PersonTableModel old, PersonTableModel young, CountTableModel counts, JTable mainTable)
/*    */   {
/* 45 */     this.addButton = addButton;
/* 46 */     this.loadButton = loadButton;
/* 47 */     this.processButton = processButton;
/* 48 */     this.saveButton = saveButton;
/* 49 */     this.textField = textField;
/* 50 */     this.toolBar = toolBar;
/* 51 */     this.counts = counts;
/* 52 */     this.old = old;
/* 53 */     this.young = young;
/* 54 */     this.mainTable = mainTable;
/*    */   }
/*    */   
/*    */   public void stateChanged(ChangeEvent e) {
/* 58 */     JTabbedPane source = (JTabbedPane)e.getSource();
/* 59 */     int index = source.getSelectedIndex();
/*    */     
/* 61 */     switch (index) {
/*    */     case 0: 
/* 63 */       this.toolBar.remove(this.textField);
/* 64 */       this.toolBar.remove(this.addButton);
/* 65 */       Main.setCellRendererAsComboBox(2, this.mainTable, this.old.getPersons());
/* 66 */       Main.setCellRendererAsComboBox(3, this.mainTable, this.young.getPersons());
/* 67 */       List<Person> youngAndOld = new ArrayList();
/* 68 */       youngAndOld.addAll(this.old.getPersons());
/* 69 */       youngAndOld.addAll(this.young.getPersons());
/* 70 */       Main.setCellRendererAsComboBox(4, this.mainTable, youngAndOld);
/* 71 */       break;
/*    */     case 1: 
/* 73 */       this.toolBar.remove(this.textField);
/* 74 */       this.toolBar.remove(this.addButton);
/*    */       
/* 76 */       this.counts.updateTable(this.old.getPersons(), this.young.getPersons());
/* 77 */       break;
/*    */     case 2: 
/*    */     case 3: 
/* 80 */       this.toolBar.remove(this.textField);
/* 81 */       this.toolBar.remove(this.addButton);
/* 82 */       this.toolBar.add(this.addButton);
/* 83 */       this.toolBar.add(this.textField);
/*    */     }
/*    */     
/* 86 */     this.toolBar.revalidate();
/* 87 */     this.toolBar.repaint();
/*    */   }
/*    */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\TabbedPaneListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */