package Utilities;


import java.util.Calendar;
import java.util.GregorianCalendar;



public class ClockUtility implements Runnable {
    clock saat;
    public ClockUtility(clock saat) {
        this.saat=saat;
    }

    @Override
    public void run() {
        for (;;){
            try {
                synchronized (saat){
                    Calendar calendar=new GregorianCalendar();
                    int hour=calendar.get(Calendar.HOUR_OF_DAY);
                    int minute=calendar.get(Calendar.SECOND);
                    saat.saat=minute;
                    System.out.println(saat.saat);
                    Thread.sleep(1000);
                }
            }
            catch (Exception ex){

            }

        }

    }
}
