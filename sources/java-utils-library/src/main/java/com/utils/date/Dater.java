package com.utils.date;

import com.utils.workWithStr.Stringer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

final public class Dater
{
    public String getFullDate() {
        return Calendar.getInstance().getTime()+"";
    }
    public String getDD_MM_YYYY() {
        return getCurrentDay()+"_"+(getCurrentMonth()+1)+"_"+getCurrentYear()+"";
    }

    public static String getDate(Date date){
        if(date==null)return "";
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(date);
        return new String( calendar.get( Calendar.DAY_OF_MONTH ) + "."
                + (calendar.get( Calendar.MONTH )+1) + "."
                + calendar.get( Calendar.YEAR)
                );

    }

    public static String getDate(Date date, String separator){
        if(date==null)return "";
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(date);
        return new String( calendar.get( Calendar.DAY_OF_MONTH ) + separator
                + (calendar.get( Calendar.MONTH )+1) + separator
                + calendar.get( Calendar.YEAR)
        );

    }

	public static String getDate()
	{

		Calendar calendar = GregorianCalendar.getInstance();
		
		return new String( calendar.get( Calendar.YEAR ) + "-" 
				+ (calendar.get( Calendar.MONTH ) + 1) + "-" 
				+ calendar.get( Calendar.DAY_OF_MONTH)
				+ " " + getTime() );
//				+ " " + pad(2, '0', calendar.get( Calendar.HOUR_OF_DAY ) + "" ) + ":" 
//				+ pad(2, '0', calendar.get( Calendar.MINUTE ) + "" ) + ":" 
//				+ pad(2, '0', calendar.get( Calendar.SECOND ) + "") );
	}
    public static int getHourFromTime(String time){//"hh:mm:ss"
       if(time.isEmpty())return -1;
       int ind=time.indexOf(":");
       try {
           if (ind == -1) return Integer.valueOf(time);
           return Integer.valueOf(time.substring(0, ind));
       }catch(Exception ex){
           return -1;
       }

    }
    public static int getMinutesFromTime(String time){//"hh:mm:ss"
        if(time.isEmpty())return -1;
        int ind=time.indexOf(":");
        try {
            if (ind == -1) return -1;
            String mmss=time.substring(ind+1);
            ind=mmss.indexOf(":");
            if (ind == -1){
                return  Integer.valueOf(mmss);
            }else {
                return  Integer.valueOf(mmss.substring(0,ind));
            }
        }catch(Exception ex){
            return -1;
        }

    }
    public static int getSecondsFromTime(String time){//"hh:mm:ss"
        if(time.isEmpty())return -1;
        int ind=time.indexOf(":");
        try {
            if (ind == -1) return -1;
            String mmss=time.substring(ind+1);
            ind=mmss.indexOf(":");
            if (ind == -1){
                return  0;
            }else {
                return  Integer.valueOf(mmss.substring(ind+1));
            }
        }catch(Exception ex){
            return -1;
        }

    }

/*
	public static String getDateForLog()
	{
		Calendar calendar = GregorianCalendar.getInstance();
		
		return new String( calendar.get( Calendar.YEAR ) + "-" 
				+ (calendar.get( Calendar.MONTH ) + 1) + "-" 
				+ calendar.get( Calendar.DAY_OF_MONTH) );
	}
*/
    public static String getDateForLog1()
    {
        Calendar calendar = GregorianCalendar.getInstance();

        return new String(calendar.get( Calendar.DAY_OF_MONTH)  + "_"
                + (calendar.get( Calendar.MONTH ) + 1) + "_"
                +  calendar.get( Calendar.YEAR ));
    }
    public static String getYesterday(java.util.Date date)
    {
        if(date==null)return "";
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return new String((calendar.get( Calendar.DAY_OF_MONTH)-1)  + "_"
                + (calendar.get( Calendar.MONTH ) + 1) + "_"
                +  calendar.get( Calendar.YEAR ));
    }
    public static java.util.Date getYesterdayDate(java.util.Date date)
    {
        if(date==null)return null;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), (calendar.get(Calendar.DAY_OF_MONTH) - 1));
        return
                calendar.getTime();
    }

	public static String getTime()
	{
		Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
		String hourString = pad(2, '0', calendar.get(Calendar.HOUR_OF_DAY) + "");
		String minuteString = pad(2, '0', calendar.get(Calendar.MINUTE) + "");
		String secondString = pad(2, '0', calendar.get( Calendar.SECOND ) + "" );
		
		return new String( hourString + ":" 
				+ minuteString + ":" 
				+ secondString );
	}
    public static String getTimeHM()
    {
        Calendar calendar = GregorianCalendar.getInstance();

        String hourString = pad(2, '0', calendar.get(Calendar.HOUR_OF_DAY) + "");
        String minuteString = pad(2, '0', calendar.get( Calendar.MINUTE ) + "" );

        return new String( hourString + ":"  + minuteString );
    }
    public static boolean greater(java.util.Date  date){
        if(date==null)return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        calendarOld.setTime(date);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        if(calendarOld.getTime().before(calendar.getTime())) return true;
     return false;
    }
    public static int getCurrentYear(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        return calendar.get(Calendar.YEAR);
    }
    public static int getCurrentMonth(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        return calendar.get(Calendar.MONTH);
    }
    public static int getCurrentDay(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean timeGreater(String  time){
        if(time==null || time.isEmpty())return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;
        calendarOld.set(getCurrentYear(),getCurrentMonth(),getCurrentDay(),
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]));
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        if(calendarOld.getTime().before(calendar.getTime())) return true;
        return false;
    }
    public static boolean timeGreater(String time1,String  time){//"  :  : "
        if(time==null || time.isEmpty())return false;
        if(time1==null || time1.isEmpty())return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;
        calendarOld.set(getCurrentYear(),getCurrentMonth(),getCurrentDay(),
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]), Integer.valueOf(timeArray[2].trim()));
        Calendar calendar = GregorianCalendar.getInstance();
        String[] time1Array=time1.split(":");
        calendar.set(getCurrentYear(), getCurrentMonth(), getCurrentDay(),
                Integer.valueOf(time1Array[0]), Integer.valueOf(time1Array[1]), Integer.valueOf(time1Array[2].trim()));

        if(calendarOld.getTime().before(calendar.getTime())) return true;
        return false;
    }

    public static boolean timeEqualsOrGreater(String  time){
        if(time==null || time.isEmpty())return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;

            calendarOld.set(getCurrentYear(), getCurrentMonth(), getCurrentDay(),
                    Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]));
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(new java.util.Date());
            if (calendarOld.getTime().after(calendar.getTime())) return false;
        return true;
    }

    public static boolean timeLess(String  time){
        if(time==null || time.isEmpty())return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;
        calendarOld.set(getCurrentYear(),getCurrentMonth(),getCurrentDay(),
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]));
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        if(calendarOld.getTime().after(calendar.getTime())) return true;
        return false;
    }
    public static boolean timeLessOrEquals(String  time){
        if(time==null || time.isEmpty())return false;
        Calendar calendarOld = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;
        calendarOld.set(getCurrentYear(),getCurrentMonth(),getCurrentDay(),
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]));
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new java.util.Date());
        if(calendarOld.getTime().equals(calendar.getTime())) return true;
        if(calendarOld.getTime().after(calendar.getTime())) return true;
        return false;
    }

    public static boolean timeLessNextDay(String  time){
        if(time==null || time.isEmpty())return false;
        Calendar currentCalendar= GregorianCalendar.getInstance();
        Calendar calendarNext = GregorianCalendar.getInstance();
        String[] timeArray=time.split(":");
        if(timeArray==null || timeArray.length<=1)return false;
        calendarNext.set(getCurrentYear(),getCurrentMonth(),getCurrentDay()+1,
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]));

        if(calendarNext.getTime().after(currentCalendar.getTime())) return true;
        return false;
    }

    public static boolean timeBetween(String  time1, String time2){
        if(time1==null || time1.isEmpty())return false;
        if(time2==null || time2.isEmpty())return false;
        String[] time11=time1.split(":");
        String[] time22=time2.split(":");
        String s1=Stringer.trunkLeaderZero(time11[0]);
        String s2=Stringer.trunkLeaderZero(time22[0]);
        if(Integer.valueOf(s1.isEmpty()?"0":s1)<Integer.valueOf(s2.isEmpty()?"0":s2)) {
            return  timeEqualsOrGreater(time1)&&
                    timeLessOrEquals(time2);
        }else {
            if(Integer.valueOf(s1.isEmpty()?"0":s1)>Integer.valueOf(s2.isEmpty()?"0":s2)) {
                boolean a = timeEqualsOrGreater(time1);
                if (!a && timeLess(time2)) return true;
                if (a && timeEqualsOrGreater(time2) && timeLessNextDay(time2))
                    return true;
            }
            if(Integer.valueOf(s1.isEmpty()?"0":s1)==Integer.valueOf(s2.isEmpty()?"0":s2)) {
                String s3=Stringer.trunkLeaderZero(time11[1]);
                String s4=Stringer.trunkLeaderZero(time22[1]);

                if(Integer.valueOf(s3.isEmpty()?"0":s3)>Integer.valueOf(s4.isEmpty()?"0":s4)) {
                    boolean a = timeEqualsOrGreater(time1);
                    if (!a && timeLess(time2)) return true;
                    if (a && timeEqualsOrGreater(time2) && timeLessNextDay(time2))
                        return true;

                }else{
                    return  timeEqualsOrGreater(time1)&&
                            timeLessOrEquals(time2);

                }
            }
        }
      return false;
    }
	private static String pad(int fieldWidth, char padChar, String s)
	{
        if(s==null || s.isEmpty())return "";
		StringBuilder sb = new StringBuilder();
		for (int i = s.length(); i < fieldWidth; i++) 
		{
			sb.append(padChar);
		}
		sb.append(s);
		 
		return sb.toString();
	}
    public static String plus(String time1,String time2){
        if(time1==null || time1.isEmpty())return "";
        if(time2==null || time2.isEmpty())return "";
        int hour1 = getHourFromTime(time1);
        int min1 = Dater.getMinutesFromTime(time1);
        int hour2 = Dater.getHourFromTime(time2);
        int min2 = Dater.getMinutesFromTime(time2);
        int sum = (hour2 * 60 + min2 ) + (hour1 * 60 + min1 );
        int hourS=sum/60;
        int minS=sum%60;
        String timeS= Stringer.setLeaderZero(String.valueOf(hourS),2)+":"+Stringer.setLeaderZero(String.valueOf(minS),2);
        System.out.println(timeS);
        return timeS;

    }
    public static String transform(int sec){

        int min=sec/60;
        int hour=sec/3600;
        int sec1=sec-hour*3600-min*60;
        String time=Stringer.setLeaderZero(String.valueOf(hour), 2)+":"+
                    Stringer.setLeaderZero(String.valueOf(min), 2);
        System.out.println(time);
        return time;
    }
    public static Date getDate(int year, int month, int dayOfMonth, int hh, int mm, int ss){
        Calendar calendar=GregorianCalendar.getInstance();
        calendar.set(year, month, dayOfMonth, hh, mm, ss);
        return calendar.getTime();
    }
    public static String format(String pattern, int year, int month, int dayOfMonth, int hh, int min, int sec){
        if(pattern==null || pattern.isEmpty())return getDate(year,month,dayOfMonth,hh,min,sec).toString();
      StringBuffer result =new StringBuffer("");
     String[] ar1=pattern.split(":");
        String[] ar2=pattern.split("-");
       if(ar1!=null && ar1.length!=1){
           int i=1;
           int count=ar1.length;
           for (String s:ar1){


               result.append(s.equals("dd")?pad(2, '0', String.valueOf(dayOfMonth)):
                               (s.equals("yy")?pad(2, '0', String.valueOf(year)):
                                       (s.equals("yyyy")?pad(4, '0', String.valueOf(year)):
                                       (s.equals("mm")?pad(2, '0', String.valueOf(month)):(
                                               s.equals("hh")?pad(2, '0', String.valueOf(hh)):(
                                              s.equals("min")?pad(2, '0', String.valueOf(min)):(
                                               s.equals("mm")?pad(2, '0', String.valueOf(min)):(
                                                       s.equals("sec")?pad(2, '0', String.valueOf(sec)):
                                                               s.equals("ss")?pad(2, '0', String.valueOf(sec)):""
                                               )
                                               )))
                               )
                             )));
               if(i<(count))result.append(":");
               i++;
           }
           return result.toString();
       } else {

           if (ar2 != null && ar2.length!=1) {
               int i=1;
               int count=ar2.length;
               for (String s : ar2) {

                   result.append(s.equals("dd") ? pad(2, '0', String.valueOf(dayOfMonth)) :
                           (s.equals("yy") ? pad(2, '0', String.valueOf(year)) :
                                   (s.equals("yyyy") ? pad(4, '0', String.valueOf(year)) :
                                   (s.equals("MM") ? pad(2, '0', String.valueOf(month)) : (
                                           s.equals("hh") ? pad(2, '0', String.valueOf(hh)) : (
                                                   s.equals("min") ? pad(2, '0', String.valueOf(min)) : (
                                                   s.equals("mm") ? pad(2, '0', String.valueOf(min)) : (
                                                           s.equals("sec") ? pad(2, '0', String.valueOf(sec)) :
                                                                   s.equals("ss") ? pad(2, '0', String.valueOf(sec)):""
                                                   )
                                           ))
                                   )
                           ))));
                   if(i<(count))result.append("-");
                   i++;

               }

           }
        return result.toString();
       }

    }
    private static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    public static String getCurrentTimeStamp(String pattern) {
       if(pattern!=null && !pattern.isEmpty())
           dateFormat=new SimpleDateFormat(pattern);
        java.util.Date today = new java.util.Date();

        return dateFormat.format(today.getTime());

    }
    public static Date parse(String str){
        if(str==null || str.isEmpty())return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date=new Date();
        try {

            date = formatter.parse(str);
            Calendar c=new GregorianCalendar();
            c.setTime(date);
            c.set(Calendar.MONTH,c.get(Calendar.MONTH)+1);
            System.out.println(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
return date;
    }
    public static void main(String[]args){
/*
      System.out.println(timeBetween("00:00","14:20"));
        System.out.println(getYesterday(new java.util.Date())) ;
        System.out.println(getYesterdayDate(new java.util.Date())) ;
*/
      //  plus("03:15", transform(300));
/*
        if(Dater.timeEqualsOrGreater("10:10") && Dater.timeLessOrEquals(Dater.plus("10:10", Dater.transform(360)))){
            System.out.println(true);
        }else System.out.println(false);
*/

/*
        Calendar c = new GregorianCalendar(1963,9, 28, 0, 0, 0);

        Date date=c.getTime();
*/


        //System.out.println(Dater.format("dd:mm:yyyy:hh:min:ss",1963, 9, 28, 0, 0, 0));;
        //System.out.println(Dater.getCurrentTimeStamp("dd MM yyyy hh:mm:ss"));;
        //System.out.println(Dater.parse("05-08-2015").toString());
/*
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());

        System.out.println(//Dater.getDate(new Date()));
                Dater.format("dd-MM-yyyy", c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH),
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        c.get(Calendar.SECOND)));
*/
        Calendar c = new GregorianCalendar();
        c.setTime(Dater.parse("28-09-1963"));
        String s=Dater.getDate(c.getTime());
        System.out.println(s);;

    }

}
