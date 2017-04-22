package cz.yiri.kus.sluzby;

import java.awt.Component;
import java.awt.Color;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ColorRenderer extends DefaultTableCellRenderer {

    private static class Colors {

        private static final int colors[]={
            0xFFFFFF, //bila
            0x969696, //sediva
            0xFFE889, //svetle zluta
            0xFFB589, //svetle oranzova
            0xFF8989, 
            0xC1FF89, 
            0x89FFC9,
            0x8C89FF, 
            0xDC89FF, 
            0xDECC08, //syte zluta
            0x2BDE08, //syte zelena
            0xDE6808, //syte oranzova
            0x08C7DE, //syte modra
            0xB808DE, //fialova
            0x8F704F, //svetle hneda
            0xB16E56,  //cihlova
            0x5CB156,  // nevyrazna zelena
            0xA984CF,  // fialkova
            0x54BB89,  // tyrkysova
            0xFFA800,  // ostra oranzova
            0xFF4800,  //cerveno oranzova
            0xFF0000,  // ostra cervena
            0xFF00BA,  // ostra fialova
            0x0084FF,  //ostra modra
            0x00FCFF,  // ostra svetlemodra
            0x00FF3C,  // matrix green
            
        };    
        
        private Colors() {
        }
        
        /**
         * Returns color according to index
         * @param index
         * @return 
         */
        public static Color getColor(int index){
            if(index>=0){
                return new Color(colors[index%colors.length]);
            }else{
                return Color.WHITE;
            }

        }
    }
    
    private List<Person> persons;
    private Team team;    
    
    public ColorRenderer(List<Person> persons,Team team){
        super();
        this.persons = persons;
        this.team = team;
    }
    
    @Override
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);

            if (persons==null){
                throw new NullPointerException("List<Person> persons must not be null, call setPersons method");
            }
            if( value instanceof String )
            {
                Person mockPerson = new Person((String) value,this.team);
                if(mockPerson!=null){
                    System.out.println("colorRenderer:"+persons.size());
                    Color color = Colors.getColor(persons.indexOf(mockPerson));            
                    cell.setBackground(color);
                }

            }

        return cell;
    }
}