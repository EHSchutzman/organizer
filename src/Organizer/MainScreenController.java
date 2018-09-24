package Organizer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.time.LocalDate;


public class MainScreenController {


    private MyCalendar calendar = null;
    //new Calendar Area
    @FXML
    public Button saveButton;
    public Button deleteButton;
    public TextArea outputArea;
    public Text loadedCalendar;
    public Button newCalCreateButton;
    public Button printCalendarButton;
    public TextField newCalName;
    public TextField newCalStartYear;
    public TextField newCalStartMonth;
    public TextField newCalStartDate;
    public TextField newCalEarlyHour;
    public TextField newCalEndYear;
    public TextField newCalEndMonth;
    public TextField newCalEndDate;
    public TextField newCalMeetDur;
    public TextField newCalEndHour;


    @FXML
    public Button scheduleMeetingButton;
    public Button cancelMeetingButton;
    public TextField scheduleMeetingYear;
    public TextField scheduleMeetingMonth;
    public TextField scheduleMeetingDay;
    public TextField scheduleMeetingHour;
    public TextField scheduleMeetingMinutes;
    public TextField scheduleMeetingName;
    public TextField scheduleMeetingLocation;


    @FXML
    public Button loadCalButton;
    public Button delCalButton;
    public TextField delCalName;
    public TextField loadCalName;

    @FXML
    public Button monthlyScheduleButton;
    public TextField monthlyScheduleMonth;


    @FXML
    public Button dailyScheduleButton;
    public TextField dailyScheduleYear;
    public TextField dailyScheduleMonth;
    public TextField dailyScheduleDay;

    @FXML
    public Button blockDayOfWeekButton;
    public Button blockTimeOfDayButton;
    public Button blockDayButton;
    public Button blockTimeslotButton;
    public TextField blockWeekday;
    public TextField blockHour;
    public TextField blockMinutes;
    public TextField blockMonth;
    public TextField blockDay;
    public TextField blockYear;

    @FXML
    public Button addDayButton;
    public TextField addDayYear;
    public TextField addDayDay;
    public TextField addDayMonth;


    @FXML
    public void onNewCalendarClick() {

        if (calendar != null) {
            Main.saveCalendar(calendar);
        }

        String name = newCalName.getText();
        int startingYear = Integer.parseInt(newCalStartYear.getText());
        int startingMonth = Integer.parseInt(newCalStartMonth.getText());
        int startingDate = Integer.parseInt(newCalStartDate.getText());
        int meetingDuration = Integer.parseInt(newCalMeetDur.getText());
        int earlyHour = Integer.parseInt(newCalEarlyHour.getText());
        int endingHour = Integer.parseInt(newCalEndHour.getText());

        int endYear = Integer.parseInt(newCalEndYear.getText());
        int endMonth = Integer.parseInt(newCalEndMonth.getText());
        int endDate = Integer.parseInt(newCalEndDate.getText());
        calendar = new MyCalendar(name, startingYear, startingMonth, startingDate, endYear, endMonth, endDate, earlyHour, endingHour, meetingDuration);
        loadedCalendar.setText(name);
        Main.saveCalendar(calendar);

        newCalName.clear();
        newCalStartMonth.clear();
        newCalStartDate.clear();
        newCalStartYear.clear();
        newCalEndDate.clear();
        newCalEndYear.clear();
        newCalEndMonth.clear();
        newCalMeetDur.clear();
        newCalEarlyHour.clear();
        newCalEndHour.clear();
        calendar.printCalendar();


    }

    @FXML
    public void loadCalendar() {
        if (calendar != null) {
            Main.saveCalendar(calendar);
        }
        calendar = Main.readJSON(loadCalName.getText() + ".calendar");
        loadedCalendar.setText(calendar.name);
        System.out.println(calendar.earlyHour);
        loadCalName.clear();

    }


    @FXML
    public void scheduleMeeting() {
        System.out.println("Scheduling meeting");
        String name = scheduleMeetingName.getText();
        int year = Integer.parseInt(scheduleMeetingYear.getText());
        int month = Integer.parseInt(scheduleMeetingMonth.getText());
        int date = Integer.parseInt(scheduleMeetingDay.getText());
        int hour = Integer.parseInt(scheduleMeetingHour.getText());
        int minutes = Integer.parseInt(scheduleMeetingMinutes.getText());
        String location = scheduleMeetingLocation.getText();
        System.out.println(location);

        if (!location.equalsIgnoreCase("")) {
            System.out.println("Location not null");
            //schedulemeeting with location
            calendar.scheduleMeetingWithLocation(LocalDate.of(year, month, date), hour, minutes, name, location);
        } else {
            System.out.println("Location null");
            System.out.println(hour);

            System.out.println(minutes);

            calendar.scheduleSingleMeeting(LocalDate.of(year, month, date), hour, minutes, name);
        }

        Main.saveCalendar(calendar);
        scheduleMeetingDay.clear();
        scheduleMeetingHour.clear();
        scheduleMeetingMinutes.clear();
        scheduleMeetingYear.clear();
        scheduleMeetingName.clear();
        scheduleMeetingMonth.clear();
        scheduleMeetingLocation.clear();


    }

    @FXML
    public void showMonthlySchedule() {

        String output = calendar.showMonthlySchedule(Integer.parseInt(monthlyScheduleMonth.getText()));

        outputArea.setText(output);
        monthlyScheduleMonth.clear();
    }

    public void showDailySchedule() {
        int day = Integer.parseInt(dailyScheduleDay.getText());
        int month = Integer.parseInt(dailyScheduleMonth.getText());
        int year = Integer.parseInt(dailyScheduleYear.getText());

        String output = calendar.showDailySchedule(LocalDate.of(year, month, day));
        outputArea.setText(output);

        dailyScheduleDay.clear();
        dailyScheduleMonth.clear();
        dailyScheduleYear.clear();
    }

    public void blockWeekday() {
        String weekday = blockWeekday.getText();
        int hour = Integer.parseInt(blockHour.getText());
        int minutes = Integer.parseInt(blockMinutes.getText());
        calendar.scheduleMeetingByTimeofWeek(weekday, hour, minutes);
        Main.saveCalendar(calendar);
        blockWeekday.clear();
        blockHour.clear();
        blockMinutes.clear();

    }

    public void blockWholeDay() {
        int year = Integer.parseInt(blockYear.getText());
        int month = Integer.parseInt(blockMonth.getText());
        int day = Integer.parseInt(blockDay.getText());

        calendar.blockDay(LocalDate.of(year, month, day));
        Main.saveCalendar(calendar);
        blockYear.clear();
        blockMonth.clear();
        blockDay.clear();
    }


    public void blockTimeslot(){
        int hour = Integer.parseInt(blockHour.getText());
        int minutes = Integer.parseInt(blockMinutes.getText());

        calendar.scheduleMeetingByTimes(hour, minutes);
        Main.saveCalendar(calendar);
        blockHour.clear();
        blockMinutes.clear();
    }

    public void addDayAction(){
        int year = Integer.parseInt(addDayYear.getText());
        int month = Integer.parseInt(addDayMonth.getText());
        int day = Integer.parseInt(addDayDay.getText());

        calendar.addDayToCalendar(LocalDate.of(year, month, day));

        addDayDay.clear();
        addDayMonth.clear();
        addDayYear.clear();
    }
    @FXML
    public void cancelMeeting(){
        String name = scheduleMeetingName.getText();
        int year = Integer.parseInt(scheduleMeetingYear.getText());
        int month = Integer.parseInt(scheduleMeetingMonth.getText());
        int date = Integer.parseInt(scheduleMeetingDay.getText());
        int hour = Integer.parseInt(scheduleMeetingHour.getText());
        int minutes = Integer.parseInt(scheduleMeetingMinutes.getText());
        String location = scheduleMeetingLocation.getText();

        calendar.cancelMeeting(LocalDate.of(year, month, date), hour, minutes);
        Main.saveCalendar(calendar);
        scheduleMeetingDay.clear();
        scheduleMeetingHour.clear();
        scheduleMeetingMinutes.clear();
        scheduleMeetingYear.clear();
        scheduleMeetingName.clear();
        scheduleMeetingMonth.clear();
        scheduleMeetingLocation.clear();
    }
    public void deleteCalendar(){
        calendar = null;

        String name = delCalName.getText();

        File f = new File(name + ".calendar");

        if(f.delete()){
            delCalName.clear();
        }

        delCalName.clear();
        unloadCalendar();

    }
    public void unloadCalendar(){
        System.out.println("unloading");
        if(calendar != null){
            Main.saveCalendar(calendar);
        }
        calendar = null;
        loadedCalendar.setText("");
    }

    @FXML
    public void printWholeCal(){
        if(calendar == null){
            outputArea.clear();
            return;
        }
        String output = calendar.printCalendar();
        outputArea.clear();
        outputArea.setText(output);
    }

    @FXML
    public void deleteDay(){
        int year = Integer.parseInt(addDayYear.getText());
        int month = Integer.parseInt(addDayMonth.getText());
        int day = Integer.parseInt(addDayDay.getText());

        calendar.deleteDate(LocalDate.of(year, month, day));
        String output = calendar.showMonthlySchedule(month);

        outputArea.clear();
        outputArea.setText(output);


        Main.saveCalendar(calendar);
        addDayDay.clear();
        addDayMonth.clear();
        addDayYear.clear();
    }

}
