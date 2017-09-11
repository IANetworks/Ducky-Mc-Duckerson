package bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class HTMLParse
{
    private Document doc;
    String[] months = new String[12];

    public static Integer tryParse(String str)
    {
        Integer retVal;
        try
        {
            retVal = Integer.parseInt(str);
        }
        catch (NumberFormatException nfe)
        {
            retVal = 0; // or null if that is your preference
        }
        return retVal;
    }

    public String GetTodayCalendarData()
    {
        months[0] = "January";
        months[1] = "February";
        months[2] = "March";
        months[3] = "April";
        months[4] = "May";
        months[5] = "June";
        months[6] = "July";
        months[7] = "August";
        months[8] = "September";
        months[9] = "October";
        months[10] = "November";
        months[11] = "December";
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            Date date = new Date();

            String dataData = dateFormat.format(date);
            String[] monthFind = dataData.split("\\.");

            int indexOfMonth = Integer.parseInt(monthFind[1]);
            int nextMonthIndex = 0;

            if (indexOfMonth < 11)
            {
                nextMonthIndex = indexOfMonth++;
            }
            else
            {
                nextMonthIndex = 0;
            }

            int day = Integer.parseInt(monthFind[2].substring(0, monthFind[2].indexOf(' ')));

            doc = Jsoup.connect("https://gunsoficarus.com/community/forum/index.php?action=calendar").get(); doc = Jsoup.connect("https://gunsoficarus.com/community/forum/index.php?action=calendar;year=2017;month=8").get();

            String calendar = doc.getElementById("calendar").text();
            String[] parsedCalendar = calendar.split("\\s+");

            int monthIndex = 0;
            int monthAfterIndex = 0;

            for (int i = 0; i < parsedCalendar.length; i++)
            {
                if (parsedCalendar[i] == months[indexOfMonth])
                {
                    monthIndex = i;
                }
                else if (parsedCalendar[i] == months[nextMonthIndex])
                {
                    monthAfterIndex = i;
                }
            }

            String[] thisMonthsData = Arrays.copyOfRange(parsedCalendar, monthIndex, monthAfterIndex);

            int dayIndex = 0;
            int dayAfterIndex = 0;

            for (int i = 0; i < thisMonthsData.length; i++)
            {
                if (HTMLParse.tryParse(thisMonthsData[i]) == day)
                {
                    dayIndex = i;
                    dayAfterIndex = i++;
                }
            }

            String[] thisDaysData = Arrays.copyOfRange(thisMonthsData, dayIndex, dayAfterIndex);
            Boolean todayHasEvent = false;

            for (int i = 0; i < thisDaysData.length; i++)
            {
                if (thisDaysData[i].contains("Events"))
                {
                    todayHasEvent = true;
                }
            }

            if (todayHasEvent)
            {
                String todaysEvents = "";
                for (int i = 0; i < thisDaysData.length; i++)
                {
                    todaysEvents += thisDaysData[i] + " ";
                }
                System.out.println(todaysEvents);
                return todaysEvents;
            }
            else
            {

                System.out.println("No Event Today");
                return "No Event Today";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void main(String[] args)
    {
        HTMLParse parser = new HTMLParse();
        parser.GetTodayCalendarData();
    }
}
