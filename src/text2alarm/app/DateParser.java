package text2alarm.app;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.*;

public class DateParser {
	
	public Date parse(String text){
		ParsePosition pp = new ParsePosition(0);
		Date d = null;
		
		if(d == null){
			SimpleDateFormat f = new SimpleDateFormat("HH:mm");
			Pattern ptn = Pattern.compile(".*(\\d\\d:\\d\\d).*", Pattern.DOTALL);
			Matcher mc = ptn.matcher(text);
			if(mc.matches()){
				d = f.parse(mc.group(1), pp);
				setToday(d);
				if(d.before(Calendar.getInstance().getTime())){
					// ‰ß‹‚Ì“ú•t‚É‚È‚Á‚½ê‡A‚P“ú‘«‚·
					d.setTime(d.getTime() + 24*3600*1000);
				}
			}
		}
		return d;
	}
	
	static public void setToday(Date d){
		Calendar cal = Calendar.getInstance();
		d.setYear(cal.get(Calendar.YEAR) - 1900);
		d.setMonth(cal.get(Calendar.MONTH));
		d.setDate(cal.get(Calendar.DAY_OF_MONTH));
	}
	
}
