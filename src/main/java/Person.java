/*     */ package sluzby2;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class Person
/*     */   implements Comparable
/*     */ {
/*  21 */   private boolean hasHoliday = false;
/*  22 */   private boolean wantsOnly = false;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private Team team;
/*     */   
/*  28 */   private Set<Integer> unwantedDays = new HashSet();
/*  29 */   private Set<Integer> wantedDays = new HashSet();
/*     */   
/*  31 */   private Set<Integer> usedDays = new HashSet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Person(String name, Team team)
/*     */   {
/*  38 */     this(name, new HashSet(), new HashSet(), team);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Person(String name, Set<Integer> bad, Set<Integer> good, Team team)
/*     */   {
/*  49 */     this.name = name;
/*  50 */     this.unwantedDays = bad;
/*  51 */     this.team = team;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWantsOnly()
/*     */   {
/*  59 */     return this.wantsOnly;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWantsOnly(boolean wantsOnly)
/*     */   {
/*  67 */     this.wantsOnly = wantsOnly;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/*  75 */     return this.team;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHasHoliday(boolean hasWeekend)
/*     */   {
/*  83 */     this.hasHoliday = hasWeekend;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasHoliday()
/*     */   {
/*  91 */     return this.hasHoliday;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<Integer> getWorkDays()
/*     */   {
/*  99 */     return this.usedDays;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canWorkOn(Integer i)
/*     */   {
/* 108 */     if (this.wantsOnly) {
/* 109 */       return this.wantedDays.contains(i);
/*     */     }
/* 111 */     return !this.unwantedDays.contains(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean worksOn(Integer i)
/*     */   {
/* 120 */     return this.usedDays.contains(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addUnwanted(Integer i)
/*     */   {
/* 128 */     this.unwantedDays.add(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUnwantedDays(String unwanted)
/*     */   {
/* 135 */     this.unwantedDays.clear();
/* 136 */     this.unwantedDays = parseDaysFromString(unwanted);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWantedDays(String wanted)
/*     */   {
/* 143 */     this.wantedDays.clear();
/* 144 */     this.wantedDays = parseDaysFromString(wanted);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWorkDays(String work)
/*     */   {
/* 152 */     this.usedDays.clear();
/* 153 */     this.usedDays = parseDaysFromString(work);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Set<Integer> parseDaysFromString(String str)
/*     */   {
/* 162 */     Set<Integer> result = new HashSet();
/*     */     
/* 164 */     String rest = str;
/* 165 */     rest = rest.trim();
/* 166 */     StringBuffer number = new StringBuffer("");
/*     */     
/* 168 */     for (int i = 0; i < rest.length(); i++) {
/* 169 */       if (Character.isDigit(rest.charAt(i))) {
/* 170 */         number.append(rest.charAt(i));
/*     */       }
/* 172 */       else if (!number.toString().equals("")) {
/* 173 */         result.add(Integer.valueOf(Integer.parseInt(number.toString())));
/* 174 */         number.delete(0, number.length());
/*     */       }
/*     */     }
/*     */     
/* 178 */     if (!number.toString().equals("")) {
/* 179 */       result.add(Integer.valueOf(Integer.parseInt(number.toString())));
/* 180 */       number.delete(0, number.length());
/*     */     }
/*     */     
/* 183 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearWorkingDays()
/*     */   {
/* 190 */     this.usedDays.clear();
/* 191 */     setHasHoliday(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addUsed(Day d)
/*     */   {
/* 198 */     this.usedDays.add(d.getInteger());
/* 199 */     if (d.isHoliday()) {
/* 200 */       setHasHoliday(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeUsed(Day d) {
/* 205 */     this.usedDays.remove(d.getInteger());
/* 206 */     if (d.isHoliday()) {
/* 207 */       setHasHoliday(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWanted(Integer i)
/*     */   {
/* 216 */     this.wantedDays.add(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean worksAround(Integer day, int radius)
/*     */   {
/* 225 */     for (Integer i : this.usedDays) {
/* 226 */       if ((i.intValue() >= day.intValue() - radius) && (i.intValue() <= day.intValue() + radius)) {
/* 227 */         return true;
/*     */       }
/*     */     }
/* 230 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String unwantedDaysToString()
/*     */   {
/* 237 */     StringBuilder sb = new StringBuilder("");
/* 238 */     for (Integer i : this.unwantedDays) {
/* 239 */       sb.append(i);
/* 240 */       sb.append(",");
/*     */     }
/* 242 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String wantedDaysToString()
/*     */   {
/* 249 */     StringBuilder sb = new StringBuilder("");
/* 250 */     for (Integer i : this.wantedDays) {
/* 251 */       sb.append(i);
/* 252 */       sb.append(",");
/*     */     }
/* 254 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 262 */     return this.name.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 270 */     if ((o instanceof Person)) {
/* 271 */       Person p2 = (Person)o;
/* 272 */       return this.name.equals(p2.getName());
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 281 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */   public int compareTo(Object o)
/*     */   {
/* 287 */     Person p = (Person)o;
/* 288 */     if (sizeOfUnwantedDays() - p.sizeOfUnwantedDays() != 0) {
/* 289 */       return -(sizeOfUnwantedDays() - p.sizeOfUnwantedDays());
/*     */     }
/* 291 */     return -this.name.compareTo(p.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int sizeOfUnwantedDays()
/*     */   {
/* 299 */     return this.unwantedDays.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int sizeOfWantedDays()
/*     */   {
/* 306 */     return this.wantedDays.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int sizeOfWorkingDays()
/*     */   {
/* 313 */     return this.usedDays.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 323 */     String str = this.name + " ";
/* 324 */     for (Integer i : this.unwantedDays) {
/* 325 */       str = str + i + " ";
/*     */     }
/* 327 */     return str;
/*     */   }
/*     */   
/*     */   public void setName(String string) {
/* 331 */     this.name = string;
/*     */   }
/*     */   
/*     */   public Set<Integer> getUnwantedDays() {
/* 335 */     return this.unwantedDays;
/*     */   }
/*     */   
/*     */   public Set<Integer> getWantedDays() {
/* 339 */     return this.wantedDays;
/*     */   }
/*     */ }


/* Location:              C:\Users\Dell\Desktop\sluzby\interna\sluzby2.jar!\sluzby2\Person.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */