package Organizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
        MyCalendar calendar = new MyCalendar("test", 2018, 7,
                10, 2018, 9, 22, 8, 10, 20);


        calendar.addDayToCalendar();
        calendar.showDailySchedule(LocalDate.of(2018, 9, 24));


        //TODO Delete Calendar by Name
        //TODO Load Calendar by Name


       }
}
