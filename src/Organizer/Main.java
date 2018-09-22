package Organizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static java.lang.Math.toIntExact;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        MyCalendar calendar = new MyCalendar("test", 2018, 7,
                10, 2018, 7, 12, 8, 10, 20);


        calendar.addDayToCalendar();
        calendar.scheduleSingleMeeting(LocalDate.of(2018, 7, 11), 8, 0, "Ethan");
        calendar.showDailySchedule(LocalDate.of(2018, 7, 11));

        saveCalendar(calendar);
        MyCalendar newCal = readJSON("test.calendar");

        newCal.showDailySchedule(LocalDate.of(2018, 7, 11));



        //TODO Delete Calendar by Name
        //TODO Load Calendar by Name


    }


    protected static void saveCalendar(MyCalendar c) {
        JSONObject obj = new JSONObject();

        obj.put("duration", c.meetingDuration);
        obj.put("earlyHour",c.earlyHour);
        obj.put("endingHour",c.endingHour);
        obj.put("meetingDuration", c.meetingDuration);
        obj.put("numberOfMeetings", c.numberOfMeetings);

        JSONArray startingDate = new JSONArray();
        startingDate.add(c.startingDate.get(Calendar.YEAR));
        startingDate.add(c.startingDate.get(Calendar.MONTH));
        startingDate.add(c.startingDate.get(Calendar.DATE));
        startingDate.add(c.startingDate.get(Calendar.HOUR));
        startingDate.add(c.startingDate.get(Calendar.MINUTE));


        JSONArray endingDate = new JSONArray();
        endingDate.add(c.endingDate.get(Calendar.YEAR));
        endingDate.add(c.endingDate.get(Calendar.MONTH));
        endingDate.add(c.endingDate.get(Calendar.DATE));
        endingDate.add(c.endingDate.get(Calendar.HOUR));
        endingDate.add(c.endingDate.get(Calendar.MINUTE));

        obj.put("startingDate", startingDate);
        obj.put("endingDate", endingDate);
        for (MyDate date : c.meetings) {
            JSONObject object = new JSONObject();
            //add date.date as key
            for (Meeting m : date.meetings) {
                JSONArray calendar = new JSONArray();
                String time = "";
                calendar.add(m.meetingTime.get(Calendar.YEAR));
                calendar.add(m.meetingTime.get(Calendar.MONTH));
                calendar.add(m.meetingTime.get(Calendar.DATE));
                calendar.add(m.meetingTime.get(Calendar.HOUR));
                calendar.add(m.meetingTime.get(Calendar.MINUTE));
                calendar.add(m.meetingDuration);
                calendar.add(m.taken);
                calendar.add(m.attendee);
                calendar.add(m.location);
                time+= m.meetingTime.get(Calendar.HOUR) + "-" + m.meetingTime.get(Calendar.MINUTE);

                object.put(time, calendar);
            }
            obj.put(date.date.toString(), object);
        }

        obj.put("name", c.name);

        try (FileWriter f = new FileWriter(obj.get("name").toString() + ".calendar")) {
            f.write(obj.toJSONString());
            f.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static MyCalendar readJSON(String filename){
        JSONParser parser = new JSONParser();
        ArrayList<LocalDate> ldates = new ArrayList<>();
        
        MyCalendar calendar = new MyCalendar();

        try {

            Object obj = parser.parse(new FileReader(filename));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            int earlyHour =  toIntExact((long)jsonObject.get("earlyHour"));
            int endingHour =  toIntExact((long)jsonObject.get("endingHour"));


            
            JSONArray startingInfo = (JSONArray) jsonObject.get("startingDate");
            int startingYear = toIntExact((long) startingInfo.get(0));
            int startingMonth = toIntExact((long) startingInfo.get(0));
            int startingDate = toIntExact((long) startingInfo.get(0));

            JSONArray endingInfo = (JSONArray) jsonObject.get("endingDate");
            int endingYear = toIntExact((long) endingInfo.get(0));
            int endingMonth = toIntExact((long) endingInfo.get(0));
            int endingDate = toIntExact((long) endingInfo.get(0));
            
            Calendar starting = Calendar.getInstance();
            Calendar ending = Calendar.getInstance();
            
            starting.set(Calendar.YEAR, startingYear);
            starting.set(Calendar.MONTH, startingMonth);
            starting.set(Calendar.DATE, startingDate);

            ending.set(Calendar.YEAR, endingYear);
            ending.set(Calendar.MONTH, endingMonth);
            ending.set(Calendar.DATE, endingDate);



            calendar.setName(name);
            calendar.setStartingDate(starting);
            calendar.setEndingDate(ending);
            calendar.setEarlyHour(earlyHour);
            calendar.setEndingHour(endingHour);
            calendar.setMeetingDuration(toIntExact((long) jsonObject.get("meetingDuration")));
            calendar.setNumberOfMeetings(toIntExact((long) jsonObject.get("numberOfMeetings")));
            // loop array

            for (Object key: jsonObject.keySet()){

                try{
                   LocalDate date = LocalDate.parse(key.toString());
                   ldates.add(date);


                }catch (Exception e){

                }
            }


            for (LocalDate date: ldates){

                MyDate myDate = new MyDate(date);

                JSONObject object = (JSONObject) jsonObject.get(date.toString());
                for (Object key: object.keySet()){
                    JSONArray meetingInfo = (JSONArray) object.get(key);

                    long year = (long) meetingInfo.get(0);
                    long month = (long) meetingInfo.get(1);
                    long day = (long) meetingInfo.get(2);
                    long hour  = (long) meetingInfo.get(3);
                    long minutes = (long) meetingInfo.get(4);
                    long duration = (long) meetingInfo.get(5);
                    boolean taken = (boolean) meetingInfo.get(6);
                    String attendee = (String) meetingInfo.get(7);
                    String location = (String) meetingInfo.get(8);

                    Calendar c =  Calendar.getInstance();
                    c.set(Calendar.YEAR, toIntExact(year));
                    c.set(Calendar.MONTH, toIntExact(month));
                    c.set(Calendar.DATE, toIntExact(day));
                    c.set(Calendar.HOUR, toIntExact(hour));
                    c.set(Calendar.MINUTE, toIntExact(minutes));
                    Meeting m = new Meeting(c, toIntExact(duration));
                    m.setTaken(taken);
                    m.setAttendee(attendee);
                    m.setLocation(location);
                    myDate.addMeeting(m);

                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


}
