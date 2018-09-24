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


    public String printMyDate(boolean careAboutTaken){
        String output = "";
        output += "\n################";
        output += this.date.toString();
        output += "################\n";

        for (Meeting m:this.meetings) {
            if(careAboutTaken){
                if(m.isTaken()){
                    output +=  m.printMeeting();
                }
            }
            else{
                output +=  m.printMeeting();
            }
        }

        return output;
    }
}
