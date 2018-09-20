package Organizer;

import java.time.LocalDate;
import java.util.ArrayList;

public class MyDate {

    protected LocalDate date;
    protected ArrayList<Meeting> meetings;

    public MyDate(LocalDate date) {
        this.date = date;
        this.meetings = new ArrayList<>();
    }


    public void addMeeting(Meeting m){
        this.meetings.add(m);
    }


    public void printMyDate(){

        System.out.println("\n\n$$$$$ NEW DATE $$$$$");
        System.out.println(this.date.toString());
        for (Meeting m:this.meetings
             ) {
            m.printMeeting();
        }
        System.out.println("\n\n");

    }
}
