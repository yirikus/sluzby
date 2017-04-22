/*     */ package cz.yiri.kus.sluzby;
/*     */ 
/*     */ import java.util.Calendar;
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
/*     */ public class Day
/*     */ {
/*     */   private Calendar day;
/*     */   private Person old;
/*     */   private Person young;
/*     */   private Person third;
/*     */   private boolean holiday;
/*     */   
/*     */   public Day(Calendar day, boolean holiday)
/*     */   {
/*  24 */     this.day = day;
/*  25 */     this.holiday = holiday;
/*     */   }
/*     */   
/*     */   public void setHoliday(boolean holiday) {
/*  29 */     this.holiday = holiday;
/*     */   }
/*     */   
/*     */   public String monthAndYearToString() {
/*  33 */     String value = "";
/*  34 */     switch (this.day.get(2)) {
/*  35 */     case 0:  value = "LEDEN";
/*  36 */       break;
/*  37 */     case 1:  value = "ÚNOR";
/*  38 */       break;
/*  39 */     case 2:  value = "BŘEZEN";
/*  40 */       break;
/*  41 */     case 3:  value = "DUBEN";
/*  42 */       break;
/*  43 */     case 4:  value = "KVĚTEN";
/*  44 */       break;
/*  45 */     case 5:  value = "ČERVEN";
/*  46 */       break;
/*  47 */     case 6:  value = "ČERVENEC";
/*  48 */       break;
/*  49 */     case 7:  value = "SRPEN";
/*  50 */       break;
/*  51 */     case 8:  value = "ZÁŘÍ";
/*  52 */       break;
/*  53 */     case 9:  value = "ŘÍJEN";
/*  54 */       break;
/*  55 */     case 10:  value = "LISTOPAD";
/*  56 */       break;
/*  57 */     case 11:  value = "PROSINEC";
/*     */     }
/*     */     
/*     */     
/*  61 */     return value + " " + this.day.get(1);
/*     */   }
/*     */   
/*     */   public void setOld(Person old)
/*     */   {
/*  66 */     this.old = old;
/*  67 */     old.addUsed(this);
/*     */   }
/*     */   
/*     */   public void setYoung(Person young) {
/*  71 */     this.young = young;
/*  72 */     young.addUsed(this);
/*     */   }
/*     */   
/*     */   public Person getOld() {
/*  76 */     return this.old;
/*     */   }
/*     */   
/*     */   public Person getYoung() {
/*  80 */     return this.young;
/*     */   }
/*     */   
/*     */   public Calendar getDay() {
/*  84 */     return this.day;
/*     */   }
/*     */   
/*     */   public Person getThird() {
/*  88 */     return this.third;
/*     */   }
/*     */   
/*     */   public void setThird(Person third) {
/*  92 */     this.third = third;
/*     */   }
/*     */   
/*     */   public boolean isHoliday() {
/*  96 */     return this.holiday;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String get(int field)
/*     */   {
/* 108 */     String value = "?";
/* 109 */     switch (field) {
/* 110 */     case 0:  value = print();
/* 111 */       break;
/* 112 */     case 1:  value = getOld() == null ? "-" : getOld().getName();
/* 113 */       break;
/* 114 */     case 2:  value = getYoung() == null ? "-" : getYoung().getName();
/* 115 */       break;
/* 116 */     case 3:  value = getThird() == null ? "" : getThird().getName();
/*     */     }
/*     */     
/*     */     
/* 120 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String print()
/*     */   {
/* 128 */     String dayOfWeek = "";
/* 129 */     switch (getDay().get(7)) {
/* 130 */     case 1:  dayOfWeek = "Ne ";
/* 131 */       break;
/* 132 */     case 2:  dayOfWeek = "Po ";
/* 133 */       break;
/* 134 */     case 3:  dayOfWeek = "Út ";
/* 135 */       break;
/* 136 */     case 4:  dayOfWeek = "St ";
/* 137 */       break;
/* 138 */     case 5:  dayOfWeek = "Čt ";
/* 139 */       break;
/* 140 */     case 6:  dayOfWeek = "Pá ";
/* 141 */       break;
/* 142 */     case 7:  dayOfWeek = "So ";
/* 143 */       break;
/* 144 */     default:  throw new IllegalArgumentException("Wrong use of Calendar.DAY_OF_WEEK");
/*     */     }
/*     */     
/* 147 */     return dayOfWeek + getDay().get(5) + "." + (getDay().get(2) + 1) + ".";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 154 */     return getDay().get(5) + "." + (getDay().get(2) + 1) + "." + getDay().get(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Integer getInteger()
/*     */   {
/* 164 */     return Integer.valueOf(getDay().get(5));
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\Day.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */