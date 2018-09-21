package Organizer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MyCalendar {

    protected String name;
    protected Calendar startingDate;
    protected Calendar endingDate;
    protected int earlyHour;
    protected int endingHour;
    protected int meetingDuration;
    protected int numberOfMeetings;
    protected long numWorkingDays;
    protected List<LocalDate> workingDates;
    protected ArrayList<MyDate> meetings = new ArrayList<>();


//    protected LocalDate localDate = LocalDate.of(2018, 9, 16);

    public MyCalendar(String name, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int earlyHour, int endingHour, int meetingDuration) {
        this.name = name; //Name of calendar


        this.earlyHour = earlyHour; //starting hour for meetings
        this.endingHour = endingHour; // ending hour for meetings
        this.meetingDuration = meetingDuration; // length of each meeting

        //Initialize the startingDate
        this.startingDate = Calendar.getInstance();
        this.startingDate.set(Calendar.YEAR, startYear);
        this.startingDate.set(Calendar.MONTH, startMonth);
        this.startingDate.set(Calendar.DAY_OF_MONTH, startDay);
        this.startingDate.set(Calendar.HOUR_OF_DAY, this.earlyHour);
        this.startingDate.set(Calendar.MINUTE, 0);


        //Initialize the ending date
        this.endingDate = Calendar.getInstance();
        this.endingDate.set(Calendar.YEAR, endYear);
        this.endingDate.set(Calendar.MONTH, endMonth);
        this.endingDate.set(Calendar.DAY_OF_MONTH, endDay);

        //caluclate the list of working dates
        this.workingDates = getDatesBetween(LocalDate.of(this.startingDate.get(Calendar.YEAR), this.startingDate.get(Calendar.MONTH), this.startingDate.get(Calendar.DATE)),
                LocalDate.of(this.endingDate.get(Calendar.YEAR), this.endingDate.get(Calendar.MONTH), this.endingDate.get(Calendar.DATE)));
        this.getWorkingDates();


        //calculate number of meetings in a day
        this.numberOfMeetings = (60 / this.meetingDuration) * (this.endingHour - this.earlyHour);

        //create the ArrayList of MyDates of each meeting
        createMeetings();
        this.printCalendar();

    }

    /**
     * This function takes a list of dates and removes any that are not weekdays (MON-FRI)
     */
    private void getWorkingDates() {
        ArrayList<Integer> daysToRemove = new ArrayList<>();
        for (int i = 0; i < this.workingDates.size(); i++) {
            switch (this.workingDates.get(i).getDayOfWeek()) {
                case SUNDAY:

                    daysToRemove.add(i);
                    break;
                case SATURDAY:
                    daysToRemove.add(i);
                    break;
                default:
                    break;
            }

        }
        for (int j = daysToRemove.size() - 1; j >= 0; j--) {
            this.workingDates.remove(this.workingDates.get(daysToRemove.get(j)));
        }
    }

    protected void printCalendar() {
        for (MyDate d : this.meetings) {
            d.printMyDate();
        }

    }

    /**
     * @param startDate startingDate
     * @param endDate   endingDate
     * @return returns a list of dates between the two dates
     */
    public static List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {


        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * This function populates the ArrayList of MyDates,
     * which each contain the localDate and a list of meetings on that date
     */
    private void createMeetings() {
        for (LocalDate d : this.workingDates) {
            ArrayList<Calendar> meetings = new ArrayList<>();
            Calendar c = (Calendar) this.startingDate.clone(); // has the proper starting hour
            c.set(Calendar.MONTH, d.getMonthValue());
            c.set(Calendar.DAY_OF_MONTH, d.getDayOfMonth());
            c.set(Calendar.YEAR, d.getYear());
            MyDate myDate = new MyDate(d);
            for (int i = 0; i < this.numberOfMeetings; i++) {
                meetings.add((Calendar) c.clone());
                c.add(Calendar.MINUTE, this.meetingDuration);
            }
            for (Calendar cal : meetings) {
                Meeting m = new Meeting(cal, this.meetingDuration);
                myDate.addMeeting(m);
            }
            this.meetings.add(myDate);
        }
    }

    //specific day & time
    protected void scheduleSingleMeeting(LocalDate date, int hour, int minutes, String name) {
        for (MyDate meeting : this.meetings) {
            if (meeting.date.equals(date)) {

                for (Meeting m : meeting.meetings) {
                    if (m.meetingTime.get(Calendar.HOUR) == hour && m.meetingTime.get(Calendar.MINUTE) == minutes) {
                        if (!m.isTaken()) {
                            m.setAttendee(name);
                            m.setTaken(true);
                        }

                    }

                }
            }
        }

    }

    protected void closeSlotByDateAndTime(LocalDate date, int hour, int minutes) {
        scheduleSingleMeeting(date, hour, minutes, "BLOCKED");

    }

    //every day @ 2pm
    protected void scheduleMeetingByTimes(int hour, int minutes) {
        for (MyDate d : this.meetings) {
            scheduleForTime(hour, minutes, d);
//            d.printMyDate();
        }


    }

    //Block all of a day
    protected void blockDay(LocalDate day) {
        for (MyDate d : this.meetings) {
            if (d.date.equals(day)) {
                for (Meeting m : d.meetings) {
                    if (!m.isTaken()) {
                        m.setTaken(true);
                        m.setAttendee("BLOCKED");
                    }
                }
            }
        }


    }

    //Block Tuesdays @ 2pm
    protected void scheduleMeetingByTimeofWeek(String weekDay, int hour, int minutes) {
        for (MyDate date : this.meetings) {
            if (date.date.getDayOfWeek().name().equalsIgnoreCase(weekDay)) {
                scheduleForTime(hour, minutes, date);
            }

        }

    }

    protected void scheduleForTime(int hour, int minutes, MyDate date) {
        for (Meeting m : date.meetings) {
            if (m.meetingTime.get(Calendar.HOUR) == hour && m.meetingTime.get(Calendar.MINUTE) == minutes) {

                if (!m.isTaken()) {
                    m.setTaken(true);
                    m.setAttendee("BLOCKED");
                }

            }
        }
    }


    protected void printByWeekday(String weekDay) {
        for (MyDate date : this.meetings) {
            if (date.date.getDayOfWeek().name().equalsIgnoreCase(weekDay)) {
                date.printMyDate();
            }
        }

    }

    protected void deleteDate(LocalDate date) {
        for (int i = 0; i < this.meetings.size(); i++) {
            MyDate d = this.meetings.get(i);
            if (d.date.equals(date)) {
                this.meetings.remove(i);
                return;
            }
        }
    }


    protected void scheduleMeetingWithLocation(LocalDate date, int hour, int minutes, String name, String location) {
        for (MyDate meeting : this.meetings) {
            if (meeting.date.equals(date)) {

                for (Meeting m : meeting.meetings) {
                    if (m.meetingTime.get(Calendar.HOUR) == hour && m.meetingTime.get(Calendar.MINUTE) == minutes) {
                        if (!m.isTaken()) {
                            m.setLocation(location);
                            m.setAttendee(name);
                            m.setTaken(true);
                        }

                    }

                }
            }
        }
    }

    protected void cancelMeeting(LocalDate date, int hour, int minutes) {

        for (MyDate d : this.meetings) {
            if (d.date.equals(date)) {
                for (Meeting m : d.meetings) {
                    if (m.meetingTime.get(Calendar.HOUR) == hour && m.meetingTime.get(Calendar.MINUTE) == minutes) {
                        m.setTaken(false);

                    }
                }
            }
        }
    }

    protected void showDailySchedule(LocalDate d) {
        for (MyDate date : this.meetings) {
            if (date.date.equals(d)) {
                date.printMyDate();
            }
        }
    }

    protected void showMonthlySchedule(int month) {
        for (MyDate date : this.meetings) {
            if (date.date.getMonthValue() == month) {
                date.printMyDate();
            }
        }

    }

    protected void addDayToCalendar() {
        this.endingDate.add(Calendar.DATE, 1);
        LocalDate end = LocalDate.of(this.endingDate.get(Calendar.YEAR), this.endingDate.get(Calendar.MONTH), this.endingDate.get(Calendar.DATE));
        System.out.println("Last Day is " + end.toString());
        System.out.println("Day of Week is " + end.getDayOfWeek().name());
        end.plusDays(1);

        while (!checkDayIsWeekday(end)) {

            this.endingDate.add(Calendar.DATE, 1);
            end = LocalDate.of(this.endingDate.get(Calendar.YEAR), this.endingDate.get(Calendar.MONTH), this.endingDate.get(Calendar.DATE));

        }

        ArrayList<Calendar> meetings = new ArrayList<>();
        Calendar c = (Calendar) this.startingDate.clone(); // has the proper starting hour
        c.set(Calendar.MONTH, end.getMonthValue());
        c.set(Calendar.DAY_OF_MONTH, end.getDayOfMonth());
        c.set(Calendar.YEAR, end.getYear());
        MyDate myDate = new MyDate(end);
        for (int i = 0; i < this.numberOfMeetings; i++) {
            meetings.add((Calendar) c.clone());
            c.add(Calendar.MINUTE, this.meetingDuration);
        }
        for (Calendar cal : meetings) {
            Meeting m = new Meeting(cal, this.meetingDuration);
            myDate.addMeeting(m);
        }
        this.meetings.add(myDate);
    }

    protected boolean checkDayIsWeekday(LocalDate d) {

        switch (d.getDayOfWeek()) {

            case SATURDAY:
                return false;
            case SUNDAY:
                return false;
            default:
                return true;

        }
    }
}

