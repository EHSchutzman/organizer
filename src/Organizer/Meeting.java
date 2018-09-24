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

    public String printMeeting(){
        String output = "\n";
        if(!this.isTaken()){
            output+= "\nMeeting is open";
        }else{
            output+= "\nMeeting is closed";
            output+= "\nAttendee: " + this.attendee;
        }
        if(!this.location.equalsIgnoreCase("")){
            output+= "\nLocation: " + this.location;
        }
//        System.out.println("Year : "+ this.meetingTime.get(Calendar.YEAR));
//        System.out.println("MONTH : "+ this.meetingTime.get(Calendar.MONTH));
//        System.out.println("DAY : "+ this.meetingTime.get(Calendar.DAY_OF_MONTH));
        output+= "\nHour : "+ this.meetingTime.get(Calendar.HOUR_OF_DAY);
        output+= "\nMinutes : "+ this.meetingTime.get(Calendar.MINUTE);

        return output;
    }

    public void setLocation(String location){
        this.location = location;
    }
}

