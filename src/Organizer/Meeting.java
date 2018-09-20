package Organizer;

import java.time.LocalDate;
import java.util.Calendar;

public class Meeting {

    protected Calendar meetingTime;
    protected int meetingDuration;
    protected String attendee;
    protected boolean taken;
    protected String location;

    public Meeting(Calendar meetingTime, int meetingDuration) {
        this.meetingTime = meetingTime;
        this.meetingDuration = meetingDuration;
        this.taken = false;
        this.attendee = "";
        this.location = "";
    }

    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isTaken() {
        return taken;
    }

    public void printMeeting(){
        System.out.println("\n\n");
        if(!this.isTaken()){
            System.out.println("Meeting is open");
        }else{
            System.out.println("Meeting is closed");
            System.out.println("Attendee: " + this.attendee);
        }
//        System.out.println("Year : "+ this.meetingTime.get(Calendar.YEAR));
//        System.out.println("MONTH : "+ this.meetingTime.get(Calendar.MONTH));
//        System.out.println("DAY : "+ this.meetingTime.get(Calendar.DAY_OF_MONTH));
        System.out.println("Hour : "+ this.meetingTime.get(Calendar.HOUR_OF_DAY));
        System.out.println("Minutes : "+ this.meetingTime.get(Calendar.MINUTE));


    }

    public void setLocation(String location){
        this.location = location;
    }
}

