package cz.yiri.kus.sluzby.view;

import cz.yiri.kus.sluzby.model.FormModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;

/**
 * @author jiri.kus
 */
public class DateComponent extends JLabel {

	public void updateText(FormModel model) {
		String datum = (model.getDate().get(Calendar.MONTH) + 1) + "/" + model.getDate().get(Calendar.YEAR);
		this.setText(datum);
		this.setFont(new Font("Arial", Font.BOLD, 20));
	}
}
