/*     */ package sluzby2;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   private static JButton processButton;
/*     */   private static JButton saveButton;
/*     */   private static JButton loadButton;
/*     */   private static JButton addButton;
/*     */   private static JButton createButton;
/*     */   private static JButton writeButton;
/*     */   private static JTextField textField;
/*     */   private static JFrame frame;
/*  43 */   private static String VERSION = "2.2";
/*     */   
/*     */   private static List<Person> loadedYoung;
/*     */   
/*     */   private static List<Person> loadedOld;
/*     */   
/*     */   private static List<Day> loadedDays;
/*     */   private static JTabbedPane tabbedPane;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*  54 */     SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {}
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void createAndShowGUI()
/*     */   {
/*     */     try
/*     */     {
/*  69 */       Calendar today = Calendar.getInstance();
/*  70 */       String datum = today.get(2) + 1 + "/" + today.get(1);
/*  71 */       frame = new JFrame("Sluzby" + VERSION + ": " + datum);
/*  72 */       frame.setPreferredSize(new Dimension(800, 640));
/*  73 */       frame.setLayout(new BorderLayout());
/*     */       
/*     */ 
/*  76 */       JTable oldTable = new JTable();
/*  77 */       PersonTableModel oldModel = new PersonTableModel();
/*  78 */       oldTable.setModel(oldModel);
/*  79 */       oldTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
/*  80 */       oldTable.setFillsViewportHeight(true);
/*  81 */       TableColumn column = oldTable.getColumnModel().getColumn(0);
/*  82 */       column.setMaxWidth(120);
/*  83 */       column = oldTable.getColumnModel().getColumn(1);
/*  84 */       column.setMaxWidth(80);
/*     */       
/*     */ 
/*  87 */       JTable countTable = new JTable();
/*  88 */       CountTableModel countModel = new CountTableModel();
/*  89 */       countTable.setModel(countModel);
/*  90 */       countTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
/*  91 */       countTable.setFillsViewportHeight(true);
/*     */       
/*     */ 
/*  94 */       JTable youngTable = new JTable();
/*  95 */       PersonTableModel youngModel = new PersonTableModel();
/*  96 */       youngTable.setModel(youngModel);
/*     */       
/*  98 */       column = youngTable.getColumnModel().getColumn(0);
/*  99 */       column.setMaxWidth(120);
/* 100 */       column = youngTable.getColumnModel().getColumn(1);
/* 101 */       column.setMaxWidth(80);
/*     */       
/* 103 */       youngTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
/* 104 */       youngTable.setFillsViewportHeight(true);
/*     */       
/*     */ 
/* 107 */       JTable table = new JTable();
/* 108 */       MainTableModel tableModel = new MainTableModel(oldModel, youngModel);
/* 109 */       table.setModel(tableModel);
/* 110 */       table.setPreferredScrollableViewportSize(new Dimension(500, 70));
/* 111 */       table.setFillsViewportHeight(true);
/*     */       
/*     */ 
/* 114 */       setCellRendererAsComboBox(2, table, oldModel.getPersons());
/* 115 */       setCellRendererAsComboBox(3, table, youngModel.getPersons());
/* 116 */       List<Person> youngAndOld = new ArrayList();
/* 117 */       youngAndOld.addAll(oldModel.getPersons());
/* 118 */       youngAndOld.addAll(youngModel.getPersons());
/* 119 */       setCellRendererAsComboBox(4, table, youngAndOld);
/*     */       
/*     */ 
/* 122 */       JScrollPane scrollPane = new JScrollPane(table);
/* 123 */       JScrollPane oldScrollPane = new JScrollPane(oldTable);
/* 124 */       JScrollPane youngScrollPane = new JScrollPane(youngTable);
/* 125 */       JScrollPane countScrollPane = new JScrollPane(countTable);
/*     */       
/*     */ 
/* 128 */       JToolBar toolBar = new JToolBar("Tools");
/* 129 */       addComponents(toolBar);
/*     */       
/*     */ 
/* 132 */       tabbedPane = new JTabbedPane();
/* 133 */       tabbedPane.add("tabulka", scrollPane);
/* 134 */       tabbedPane.add("počet služeb", countScrollPane);
/* 135 */       tabbedPane.add("starší", oldScrollPane);
/* 136 */       tabbedPane.add("mladší", youngScrollPane);
/* 137 */       TabbedPaneListener listener = new TabbedPaneListener(toolBar, processButton, saveButton, loadButton, addButton, textField, oldModel, youngModel, countModel, table);
/* 138 */       tabbedPane.addChangeListener(listener);
/*     */       
/*     */ 
/* 141 */       ToolBarListener toolbarListener = new ToolBarListener(tabbedPane, textField, youngModel, oldModel, tableModel, table);
/* 142 */       addButton.addActionListener(toolbarListener);
/* 143 */       loadButton.addActionListener(toolbarListener);
/* 144 */       saveButton.addActionListener(toolbarListener);
/* 145 */       writeButton.addActionListener(toolbarListener);
/* 146 */       processButton.addActionListener(toolbarListener);
/* 147 */       createButton.addActionListener(toolbarListener);
/* 148 */       textField.addActionListener(toolbarListener);
/*     */       
/* 150 */       frame.add(toolBar, "First");
/* 151 */       frame.add(tabbedPane, "Center");
/*     */       
/*     */ 
/* 154 */       loadedYoung = new ArrayList();
/* 155 */       loadedOld = new ArrayList();
/* 156 */       loadedDays = new ArrayList();
/* 157 */       Storage.load(loadedYoung, loadedOld, loadedDays);
/* 158 */       System.out.println("###main: " + loadedDays.size());
/*     */       
/* 160 */       youngModel.updateTable(loadedYoung);
/* 161 */       oldModel.updateTable(loadedOld);
/* 162 */       tableModel.updateTable(loadedDays);
/*     */       
/* 164 */       setCellRendererAsComboBox(2, table, oldModel.getPersons());
/* 165 */       setCellRendererAsComboBox(3, table, youngModel.getPersons());
/*     */       
/* 167 */       List<Person> youngAndOld2 = new ArrayList();
/* 168 */       youngAndOld.addAll(oldModel.getPersons());
/* 169 */       youngAndOld.addAll(youngModel.getPersons());
/* 170 */       setCellRendererAsComboBox(4, table, youngAndOld2);
/*     */       
/*     */ 
/* 173 */       TableColumn oldColumn = table.getColumnModel().getColumn(2);
/* 174 */       TableColumn youngColumn = table.getColumnModel().getColumn(3);
/* 175 */       TableCellRenderer oldRenderer = new ColorRenderer((List)oldModel.getPersons(), Team.OLD);
/* 176 */       TableCellRenderer youngRenderer = new ColorRenderer((List)youngModel.getPersons(), Team.YOUNG);
/* 177 */       oldColumn.setCellRenderer(oldRenderer);
/* 178 */       youngColumn.setCellRenderer(youngRenderer);
/*     */       
/*     */ 
/* 181 */       frame.setDefaultCloseOperation(3);
/*     */       
/*     */ 
/* 184 */       frame.pack();
/* 185 */       frame.setVisible(true);
/*     */     } catch (Exception e) {
/* 187 */       JOptionPane.showMessageDialog(frame, e.getMessage(), "Chyba!", 0);
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
/*     */   private static void addComponents(JToolBar toolBar)
/*     */   {
/* 200 */     createButton = new JButton("Nový");
/* 201 */     createButton.setActionCommand("CREATE");
/* 202 */     toolBar.add(createButton);
/*     */     
/* 204 */     processButton = new JButton("Spočítat");
/* 205 */     processButton.setActionCommand("PROCESS");
/* 206 */     toolBar.add(processButton);
/*     */     
/* 208 */     writeButton = new JButton("Dokument k odeslání");
/* 209 */     writeButton.setActionCommand("WRITE");
/* 210 */     toolBar.add(writeButton);
/*     */     
/* 212 */     saveButton = new JButton("Uložit");
/* 213 */     saveButton.setActionCommand("SAVE");
/* 214 */     toolBar.add(saveButton);
/*     */     
/* 216 */     loadButton = new JButton("Nahrát");
/* 217 */     loadButton.setActionCommand("LOAD");
/* 218 */     toolBar.add(loadButton);
/*     */     
/* 220 */     JSeparator sep = new JSeparator(1);
/* 221 */     toolBar.add(sep);
/*     */     
/* 223 */     addButton = new JButton("Přidej doktora");
/* 224 */     addButton.setActionCommand("ADD");
/*     */     
/*     */ 
/* 227 */     textField = new JTextField();
/* 228 */     textField.setPreferredSize(new Dimension(50, 10));
/* 229 */     textField.setActionCommand("ADD");
/*     */   }
/*     */   
/*     */   public static void setCellRendererAsComboBox(int i, JTable table, Collection<Person> persons)
/*     */   {
/* 234 */     TableColumn column = table.getColumnModel().getColumn(i);
/* 235 */     JComboBox comboBox = new JComboBox();
/* 236 */     for (Person p : persons) {
/* 237 */       comboBox.addItem(p.getName());
/*     */     }
/* 239 */     column.setCellEditor(new DefaultCellEditor(comboBox));
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\Main.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */