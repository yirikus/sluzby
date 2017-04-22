package cz.yiri.kus.sluzby.service;

import cz.yiri.kus.sluzby.model.Day;
import cz.yiri.kus.sluzby.model.Person;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 *
 * @author terrmith
 */
public class HarmonogramExporter {

    private List<Day> days;
    private String filename;

    public HarmonogramExporter(List<Day> days,String filename){        
        this.filename = filename;
        this.days = days;
    }

    public void exportAsODT(){
        if(days==null){
            throw new NullPointerException("List of days is null");
        }else if(days.size()<1){
            throw new IllegalArgumentException("The size of days list must not be lower than 1");
        }
        throw new UnsupportedOperationException("export disabled due to library debility");

    }
    public void exportAsHTML(){
        if(days==null){
            throw new NullPointerException("List of days is null");
        }else if(days.size()<1){
            throw new IllegalArgumentException("The size of days list must not be lower than 1");
        }
        try{

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+".html")));

            writer.write("<html><head><title>Sluzby</title><style>");writer.newLine();
            writer.write("table{border:2px solid black;border-collapse:collapse}");writer.newLine();
            writer.write("td{width:150;height:20;border:1px solid black;border-collapse:collapse}");writer.newLine();
            writer.write("</style></head><body><div style=\"width:610;height:80;text-align:center;border:2px solid black;font-size:16px\">");writer.newLine();
            writer.write("<br>Sluzby primářů a lékařů na interním,infekčním a nefrologickém oddělení Nemocnice Písek</div><table>");
            writer.write("<tr><td><b>DATUM</b><td><b>INTERNA</b><td><b>INTERNA</b><td><b>DIALYZA</b></tr>");
            writer.newLine();
            for(Day d:days){
                String colour="";
                writer.write("<tr>");
                if(d.isHoliday()){
                    colour=" style=\"background-color:#CCCCCC\"";
                }
                writer.write("<td"+colour+">"+d.print());

                Person p = d.getOld();
                String name = p==null?" ":p.getName();
                writer.write("<td"+colour+">"+name);

                p = d.getYoung();
                name = p==null?" ":p.getName();
                writer.write("<td"+colour+">"+name);

                p = d.getThird();
                name = p==null?" ":p.getName();
                writer.write("<td"+colour+">"+name);
                writer.write("</tr>");
                writer.newLine();
            }
            writer.write("</table></body></html>");
            writer.close();

        }catch(IOException ioe){
            System.err.println("IOE");

        }
    }
}
