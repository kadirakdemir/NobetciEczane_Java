package Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtility {
	 public String getDate(){
	     Calendar calendar=Calendar.getInstance();
	     int year=calendar.get(Calendar.YEAR);
	     int mount=calendar.get(Calendar.MONTH);
	     int day=calendar.get(Calendar.DAY_OF_MONTH);
	     String date=year+"-"+(mount+1)+"-"+day;
	     return date;
	 }

    public String getDateAll(){
	    Calendar calendar=Calendar.getInstance();
        int year1=calendar.get(Calendar.YEAR);
        int mount1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);

        Date dt=new Date();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE,1);
        int year2=calendar.get(Calendar.YEAR);
        int mount2=calendar.get(Calendar.MONTH);
        int day2=calendar.get(Calendar.DAY_OF_MONTH);
        String dateAll=day1+"-"+(mount1+1)+"-"+year1+"   "+day2+"-"+(mount2+1)+"-"+year2+"  "+"08:00";
        return  dateAll;
    }


}
