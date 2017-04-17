/*     */ package sluzby2;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
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
/*     */ public class Storage
/*     */ {
/*     */   private static final String DATA_FILE = "data.txt";
/*     */   private static final String COLUMN = ":";
/*     */   private static final String SEPARATOR = "#";
/*     */   private static final String END = "&";
/*     */   
/*     */   public static void save(Collection<Person> young, Collection<Person> old, Collection<Day> days)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt")));
/*     */       
/*  44 */       savePersons(writer, young);
/*  45 */       savePersons(writer, old);
/*     */       
/*  47 */       saveDays(writer, days);
/*     */       
/*  49 */       writer.close();
/*     */     } catch (FileNotFoundException fnf) {
/*  51 */       System.err.println("Dany soubor neexistuje");
/*     */     }
/*     */     catch (IOException ioe) {
/*  54 */       System.err.println("IOE");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void load(List<Person> young, List<Person> old, List<Day> days)
/*     */   {
/*     */     try
/*     */     {
/*  64 */       BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt")));
/*     */       
/*  66 */       loadPersons(reader, young, Team.YOUNG);
/*  67 */       loadPersons(reader, old, Team.OLD);
/*     */       
/*  69 */       loadDays(reader, young, old, days);
/*     */       
/*  71 */       reader.close();
/*     */     } catch (FileNotFoundException fnf) {
/*  73 */       System.err.println("Dany soubor neexistuje");
/*     */     }
/*     */     catch (IOException ioe) {
/*  76 */       System.err.println("IOE");
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
/*     */   private static void savePersons(BufferedWriter writer, Collection<Person> persons)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/*  90 */     for (Person p : persons) {
/*  91 */       writer.write(p.getName() + ":" + p.isWantsOnly() + ":" + p.hasHoliday() + ":");
/*     */       
/*     */ 
/*     */ 
/*  95 */       writer.write("#");
/*  96 */       for (Integer i : p.getUnwantedDays()) {
/*  97 */         writer.write(i + "#");
/*     */       }
/*  99 */       writer.write(":");
/* 100 */       writer.write("#");
/* 101 */       for (Integer i : p.getWantedDays()) {
/* 102 */         writer.write(i + "#");
/*     */       }
/* 104 */       writer.write(":");
/* 105 */       writer.write("#");
/* 106 */       for (Integer i : p.getWorkDays()) {
/* 107 */         writer.write(i + "#");
/*     */       }
/* 109 */       writer.write(":");
/* 110 */       writer.newLine();
/*     */     }
/* 112 */     writer.write("&");
/* 113 */     writer.newLine();
/*     */   }
/*     */   
/*     */   private static void loadPersons(BufferedReader reader, Collection<Person> persons, Team team) throws IOException
/*     */   {
/* 118 */     String string = reader.readLine();
/* 119 */     while ((string != null) && (!string.equals("&"))) {
/* 120 */       System.out.println("loadPersons.reading: " + string);
/* 121 */       String[] splitted = string.split(":");
/* 122 */       for (int i = 0; i < splitted.length; i++) {
/* 123 */         System.out.println(i + ": " + splitted[i]);
/*     */       }
/* 125 */       String name = splitted[0];
/* 126 */       boolean wantsOnly = splitted[1].equals("true");
/* 127 */       boolean hasHoliday = splitted[2].equals("true");
/*     */       
/* 129 */       Person person = new Person(name, team);
/* 130 */       person.setWantsOnly(wantsOnly);
/* 131 */       person.setHasHoliday(hasHoliday);
/* 132 */       person.setUnwantedDays(splitted[3]);
/* 133 */       person.setWantedDays(splitted[4]);
/* 134 */       person.setWorkDays(splitted[5]);
/*     */       
/* 136 */       persons.add(person);
/*     */       
/* 138 */       string = reader.readLine();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void saveDays(BufferedWriter writer, Collection<Day> days) throws FileNotFoundException, IOException {
/* 143 */     for (Day d : days) {
/* 144 */       writer.write(d.toString() + ":" + (d.getOld() != null ? d.getOld().getName() : "#") + ":" + (d.getYoung() != null ? d.getYoung().getName() : "#") + ":" + (d.getThird() != null ? d.getThird().getName() : "#") + ":" + d.isHoliday() + ":");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 149 */       writer.newLine();
/*     */     }
/*     */     
/* 152 */     writer.write("&");
/* 153 */     writer.newLine();
/*     */   }
/*     */   
/*     */   private static void loadDays(BufferedReader reader, List<Person> young, List<Person> old, List<Day> days) throws IOException {
/* 157 */     String string = reader.readLine();
/* 158 */     while ((string != null) && (!string.equals("&"))) {
/* 159 */       System.out.println(string);
/*     */       
/* 161 */       String[] splitted = string.split(":");
/* 162 */       String[] date = splitted[0].split("\\.");
/* 163 */       for (int i = 0; i < splitted.length; i++) {
/* 164 */         System.out.println(i + ": " + splitted[i]);
/*     */       }
/* 166 */       System.out.println("--");
/* 167 */       for (int i = 0; i < date.length; i++) {
/* 168 */         System.out.println(i + ": " + date[i]);
/*     */       }
/* 170 */       Integer dayOfMonth = new Integer(date[0]);
/* 171 */       Integer month = Integer.valueOf(new Integer(date[1]).intValue() - 1);
/* 172 */       Integer year = new Integer(date[2]);
/* 173 */       String oldName = splitted[1];
/* 174 */       String youngName = splitted[2];
/* 175 */       String thirdName = splitted[3];
/* 176 */       boolean holiday = splitted[4].equals("true");
/*     */       
/* 178 */       Calendar cal = Calendar.getInstance();
/* 179 */       cal.set(1, year.intValue());
/* 180 */       cal.set(2, month.intValue());
/* 181 */       cal.set(5, dayOfMonth.intValue());
/*     */       
/* 183 */       Day day = new Day(cal, holiday);
/* 184 */       if (!youngName.equals("#")) {
/* 185 */         Person p = new Person(youngName, Team.YOUNG);
/* 186 */         if (young.contains(p)) {
/* 187 */           day.setYoung((Person)young.get(young.indexOf(p)));
/*     */         } else {
/* 189 */           throw new IllegalArgumentException("young does not contain given name");
/*     */         }
/*     */       }
/*     */       
/* 193 */       if (!oldName.equals("#")) {
/* 194 */         Person p = new Person(oldName, Team.YOUNG);
/* 195 */         if (old.contains(p)) {
/* 196 */           day.setOld((Person)old.get(old.indexOf(p)));
/*     */         } else {
/* 198 */           throw new IllegalArgumentException("old does not contain given name");
/*     */         }
/*     */       }
/*     */       
/* 202 */       if (!thirdName.equals("#")) {
/* 203 */         Person p = new Person(thirdName, Team.YOUNG);
/* 204 */         if (young.contains(p)) {
/* 205 */           day.setThird((Person)young.get(young.indexOf(p)));
/* 206 */         } else if (old.contains(p)) {
/* 207 */           day.setThird((Person)old.get(old.indexOf(p)));
/*     */         } else {
/* 209 */           throw new IllegalArgumentException("young nor old does not contain given name");
/*     */         }
/*     */       }
/*     */       
/* 213 */       days.add(day);
/*     */       
/* 215 */       string = reader.readLine();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\Storage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */