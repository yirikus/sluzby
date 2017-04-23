package cz.yiri.kus.sluzby.test;

import cz.yiri.kus.sluzby.service.PublicHolidays;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author jiri.kus
 */
public class PublicHolidaysTest {

	@Test
	public void testPublicHolidays() {
		Assert.assertTrue(PublicHolidays.isPublicHoliday(date(1,1,2017)));
		Assert.assertTrue(PublicHolidays.isPublicHoliday(date(17,4,2017)));
		Assert.assertTrue(PublicHolidays.isPublicHoliday(date(14,4,2017)));
	}

	private Calendar date(int day, int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar;
	}
}
