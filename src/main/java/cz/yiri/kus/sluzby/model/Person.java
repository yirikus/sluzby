package cz.yiri.kus.sluzby.model;

import cz.yiri.kus.sluzby.model.Team;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class Person implements Comparable{
    
    private boolean hasHoliday = false;
    private boolean wantsOnly = false;
    private String name;
    private Team team;


    //Wanted and unwanted days
    private Set<Integer> unwantedDays = new HashSet<Integer>();
    private Set<Integer> wantedDays = new HashSet<Integer>();

    private Set<Integer> usedDays = new HashSet<Integer>();
    
    /**
     * konstruktor tridy Person, dny, kdy osoba nemuze slouzit se pridaji pozdeji
     * @param name Jmeno osoby
     */
    public Person(String name, Team team) {
        this(name,new HashSet<Integer>(),new HashSet<Integer>(), team);
    }


    /**
     * konstruktor tridy Person
     * @param name jmeno osoby
     *        bad mnozina dnu, kdy osoba nemuze slouzit
     *        bad mnozina dnu, kdy osoba chce slouzit
     */
    public Person(String name, Set<Integer> bad,Set<Integer> good, Team team) {
        this.name = name;
        this.unwantedDays = bad;
        this.team=team;
    }
    
    /**
     * Vraci true pokud osoba chce slouzit pouze specifikovane dny
     * @return 
     */
    public boolean isWantsOnly() {
        return wantsOnly;
    }

    /**
     * Nastavi jestli osoba chce pouze specifikovane dny
     * @param wantsOnly 
     */
    public void setWantsOnly(boolean wantsOnly) {
        this.wantsOnly = wantsOnly;
    }
    
    /**
     * Returns team of person, there are two teams young and old
     * @return team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets
     * @param hasWeekend 
     */
    public void setHasHoliday(boolean hasWeekend) {
        this.hasHoliday = hasWeekend;
    }

    /**
     * Returns true if person is assigned to the holiday, one person can have perfectly only one holiday
     * @return
     */
    public boolean hasHoliday(){
        return hasHoliday;
    }

    /**
     * Returns set of days on which Person works
     * @return
     */
    public Set<Integer> getWorkDays() {
        return usedDays;
    }
    
    /**
     * Returns false when given days is in unwanted days list, otherwise true
     * @param i den
     * @return true pokud osoba dany den nemuze slouzit
     */
    public boolean canWorkOn(Integer i){
        if(wantsOnly){
           return wantedDays.contains(i); 
        }else{
            return !unwantedDays.contains(i);
        }
    }
    /**
     * Vraci true pokud osoba dany den slouzi
     * @param i den
     * @return true pokud osoba dany slouzi
     */
    public boolean worksOn(Integer i){
        return usedDays.contains(i);
    }
    
    /**
     * Prida den, kdy osoba nemuze slouzit
     * @param i den
     */
    public void addUnwanted(Integer i){
        unwantedDays.add(i);
    }
    /**
     * Sets unwanted days
     * @param unwanted
     */
    public void setUnwantedDays(String unwanted){
        unwantedDays.clear();
        unwantedDays = parseDaysFromString(unwanted);
    }
    /**
     * Sets wanted days
     * @param wanted
     */
    public void setWantedDays(String wanted){
        wantedDays.clear();
        wantedDays = parseDaysFromString(wanted);
    }

    /**
     * Sets work days (it does not set anything else, like hasHoliday, etc.)
     * @param work
     */
    public void setWorkDays(String work){
        usedDays.clear();
        usedDays = parseDaysFromString(work);
    }

    /**
     * Parses String to set of integers
     * @param str string to be parsed
     */
  private Set<Integer> parseDaysFromString(String str){

        Set<Integer> result = new HashSet<Integer>();
        int i;
        String rest = str;
        rest = rest.trim();
        StringBuffer number= new StringBuffer("");

        for (i = 0; i < rest.length(); i++) {
           if(Character.isDigit(rest.charAt(i))){
              number.append(rest.charAt(i));
           }else{
               if(!number.toString().equals("")){
                   result.add(Integer.parseInt(number.toString()));
                   number.delete(0, number.length());
               }
           }
        }
         if(!number.toString().equals("")){
                   result.add(Integer.parseInt(number.toString()));
                   number.delete(0, number.length());
               }

      return result;
  }

  /**
   * Vynuluje sluzby
   */
  public void clearWorkingDays(){
    usedDays.clear();
    setHasHoliday(false);
  }
    /**
     * Prida den, ve ktery osoba bude slouzit
     * @param d den
     */
    public void addUsed(Day d){
        usedDays.add(d.getInteger());
        if(d.isHoliday()){
            setHasHoliday(true);
        }
    }

    public void removeUsed(Day d){
        usedDays.remove(d.getInteger());
        if(d.isHoliday()){
            setHasHoliday(false);
        }
    }

    /**
     * Prida den, ve ktery osoba chce slouzit
     * @param i den
     */
    public void addWanted(Integer i){
        wantedDays.add(i);
    }
    /**
     * Returns true if given day is near to any other used days using given radius
     * @param day day to be tested
     * @param radius radius
     * @return
     */
    public boolean worksAround(Integer day,int radius){
        for(Integer i:usedDays){
            if(i>=(day-radius) && i<=(day+radius)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns set of unwanted days as a string
     */
    public String unwantedDaysToString(){
        StringBuilder sb = new StringBuilder("");
        for(Integer i:unwantedDays){
            sb.append(i);
            sb.append(",");
        }
        return sb.toString();
    }

    /**
     * Returns set of wanted days as a string
     */
    public String wantedDaysToString(){
        StringBuilder sb = new StringBuilder("");
        for(Integer i:wantedDays){
            sb.append(i);
            sb.append(",");
        }
        return sb.toString();
    }
    
    /**
     * Hash code
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }
    
    /**
     * Two persons equals when they have the same name
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Person){
            Person p2=(Person)o;
            return name.equals(p2.getName());
        }else{
            return false;
        }
    }
    /**
     * @return jmeno osoby
     */
    public String getName() {
        return name;
    }
    /**
     * Porovna na zaklade poctu dnu, kdy osoba nemuze slouzit a jmena
     */
    public int compareTo(Object o) {
        Person p =(Person)o;
        if((sizeOfUnwantedDays() - p.sizeOfUnwantedDays())!=0){
            return -(sizeOfUnwantedDays() - p.sizeOfUnwantedDays());
        }else{
            return -name.compareTo(p.getName());
        }
    }
    
    /**
     * @return pocet dnu, kdy osoba nemuze slouzit
     */
    public int sizeOfUnwantedDays(){
        return unwantedDays.size();
    }

    /**
     * @return pocet dnu, kdy osoba chce slouzit
     */
    public int sizeOfWantedDays(){
        return wantedDays.size();
    }
    
    /**
     * @return pocet dnu, kdy osoba slouzi
     */
    public int sizeOfWorkingDays(){
        return usedDays.size();
    }        
    
    /**
     * TODO: předělat
     * K NICEMU!
     * to string (jmeno den den den ...)
     */
    @Override
    public String toString(){
        String str = name+" ";
        for(Integer i:unwantedDays){
            str += i+" ";
        }
        return str;
    }

   public void setName(String string) {
        this.name=string;
    }

    public Set<Integer> getUnwantedDays() {
        return unwantedDays;
    }

    public Set<Integer> getWantedDays() {
        return wantedDays;
    }
    
}
