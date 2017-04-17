/*     */ package sluzby2;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class HarmonogramFactory
/*     */ {
/*     */   private static String dateToString(Calendar c)
/*     */   {
/*  29 */     return c.get(5) + "." + c.get(2) + "." + c.get(1) + "=" + c.get(5) + "." + c.getDisplayName(2, 2, Locale.ENGLISH) + "(" + c.getDisplayName(7, 2, Locale.ENGLISH) + ")";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Collection<Day> newHarmonogram(Collection<Person> young, Collection<Person> old)
/*     */   {
/*  39 */     List<Day> days = new ArrayList();
/*     */     
/*  41 */     for (Person p : old) {
/*  42 */       p.clearWorkingDays();
/*     */     }
/*  44 */     for (Person p : young) {
/*  45 */       p.clearWorkingDays();
/*     */     }
/*     */     
/*     */ 
/*  49 */     Calendar today = Calendar.getInstance();
/*  50 */     today.set(5, 1);
/*  51 */     today.set(1, today.get(1) + today.get(2) / 11);
/*  52 */     today.set(2, (today.get(2) + 1) % 12);
/*  53 */     System.out.println(dateToString(today));
/*     */     
/*  55 */     int lastDay = today.getActualMaximum(5);
/*     */     
/*  57 */     for (int i = 1; i <= lastDay; i++) {
/*  58 */       Calendar day = Calendar.getInstance();
/*  59 */       day.set(5, i);
/*  60 */       day.set(2, today.get(2));
/*  61 */       day.set(1, today.get(1));
/*     */       
/*     */ 
/*  64 */       if ((day.get(7) == 7) || (day.get(7) == 1))
/*     */       {
/*     */ 
/*  67 */         Day newDay = new Day(day, true);
/*  68 */         days.add(newDay);
/*     */       }
/*     */       else {
/*  71 */         Day newDay = new Day(day, false);
/*  72 */         days.add(newDay);
/*     */       }
/*     */     }
/*     */     
/*  76 */     for (Iterator i$ = old.iterator(); i$.hasNext();) { p = (Person)i$.next();
/*  77 */       for (Integer i : p.getWantedDays()) {
/*  78 */         if ((i.intValue() <= days.size()) && (i.intValue() > 0)) {
/*  79 */           Day d = (Day)days.get(i.intValue() - 1);
/*  80 */           if ((d.getOld() == null) && (!p.worksOn(i))) {
/*  81 */             d.setOld(p);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     Person p;
/*  87 */     for (Iterator i$ = young.iterator(); i$.hasNext();) { p = (Person)i$.next();
/*  88 */       for (Integer i : p.getWantedDays()) {
/*  89 */         if ((i.intValue() <= days.size()) && (i.intValue() > 0)) {
/*  90 */           Day d = (Day)days.get(i.intValue() - 1);
/*  91 */           if ((d.getYoung() == null) && (!p.worksOn(i))) {
/*  92 */             d.setYoung(p);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     Person p;
/*  99 */     return days;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Collection<Day> fillHarmonogram(List<Day> days, Collection<Person> young, Collection<Person> old)
/*     */   {
/* 108 */     fillUnwanted(days, young, Team.YOUNG);
/* 109 */     fillUnwanted(days, old, Team.OLD);
/*     */     
/* 111 */     fillTheRest(days, young, Team.YOUNG);
/* 112 */     fillTheRest(days, old, Team.OLD);
/*     */     
/* 114 */     return days;
/*     */   }
/*     */   
/*     */   private static void fillUnwanted(List<Day> days, Collection<Person> persons, Team team) {
/* 118 */     List<Integer> badDays = new ArrayList();
/*     */     
/* 120 */     for (Person p : persons) {
/* 121 */       if (!p.isWantsOnly()) {
/* 122 */         for (Integer i : p.getUnwantedDays()) {
/* 123 */           if ((!badDays.contains(i)) && (i.intValue() - 1 < days.size()) && (i.intValue() - 1 >= 0)) {
/* 124 */             badDays.add(i);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 131 */     for (Integer i : badDays) {
/* 132 */       System.out.println("harmonogram.fill.day = " + i);
/*     */       
/* 134 */       Day d = (Day)days.get(i.intValue() - 1);
/*     */       Person person;
/*     */       Person person;
/* 137 */       if (team == Team.OLD) {
/* 138 */         person = d.getOld();
/*     */       } else {
/* 140 */         person = d.getYoung();
/*     */       }
/* 142 */       if (person == null) {
/* 143 */         boolean assigned = false;
/* 144 */         for (int k = 5; (k >= 0) && (!assigned); k--) {
/* 145 */           for (int j = 0; (j < 32) && (!assigned); j++) {
/* 146 */             for (Person p : persons) {
/* 147 */               if ((!p.isWantsOnly()) && 
/* 148 */                 (p.sizeOfWorkingDays() == j) && (p.canWorkOn(i)) && ((!d.isHoliday()) || (!p.hasHoliday())) && (!p.worksAround(i, k)))
/*     */               {
/*     */ 
/* 151 */                 if (team == Team.OLD) {
/* 152 */                   d.setOld(p);
/*     */                 } else {
/* 154 */                   d.setYoung(p);
/*     */                 }
/* 156 */                 System.out.println("assigned to " + p.getName());
/* 157 */                 assigned = true;
/* 158 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
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
/*     */   private static void fillTheRest(List<Day> days, Collection<Person> persons, Team team)
/*     */   {
/* 178 */     for (Day day : days) {
/*     */       Person person;
/*     */       Person person;
/* 181 */       if (team == Team.OLD) {
/* 182 */         person = day.getOld();
/*     */       } else {
/* 184 */         person = day.getYoung();
/*     */       }
/* 186 */       if (person == null) {
/* 187 */         boolean assigned = false;
/* 188 */         for (int k = 5; (k >= 0) && (!assigned); k--) {
/* 189 */           for (int j = 0; (j < 32) && (!assigned); j++)
/*     */           {
/* 191 */             for (Person p : persons) {
/* 192 */               if ((!p.isWantsOnly()) && 
/* 193 */                 (p.sizeOfWorkingDays() == j) && (p.canWorkOn(day.getInteger())) && ((!day.isHoliday()) || (!p.hasHoliday())) && (!p.worksAround(day.getInteger(), k)))
/*     */               {
/* 195 */                 System.out.println("assigned to " + p.getName());
/* 196 */                 if (team == Team.OLD) {
/* 197 */                   day.setOld(p);
/*     */                 } else {
/* 199 */                   day.setYoung(p);
/*     */                 }
/*     */                 
/* 202 */                 assigned = true;
/* 203 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\HarmonogramFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */