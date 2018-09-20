package Organizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        MyCalendar calendar = new MyCalendar("test", 2018, 8,
                10, 2018, 8, 25, 8, 10, 20);


        calendar.scheduleSingleMeeting(LocalDate.of(2018, 10, 14), 9, 40, "Ethan");
        calendar.scheduleSingleMeeting(LocalDate.of(2018, 10, 14), 9, 40, "Bill");

        calendar.scheduleMeetingByTimes(9,40);


        calendar.blockDay(LocalDate.of(2018,8,10));

        calendar.scheduleMeetingByTimeofWeek("wednesday", 8, 0);

        calendar.printDates();
        calendar.deleteDate(LocalDate.of(2018, 8, 16));
        System.out.println("\n\n\n");
        calendar.printDates();



        //TODO Delete Calendar by Name
        //TODO Load Calendar by Name
        //TODO Add a day to the calendar
        //TODO cancel a meeting
        //TODO show daily schedule
        //TODO show Monthly Schedule
        //TODO test scheduleMeetingWithLocation
    }
}
