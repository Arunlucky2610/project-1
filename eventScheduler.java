import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class event {
    String name;
    LocalTime startTime;
    LocalTime endTime;

    public event(String name, String startTime, String endTime) {
        this.name = name;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    @Override
    public String toString() {
        return String.format("\"%s\", Start: \"%s\", End: \"%s\"", name, startTime, endTime);
    }
}

public class eventScheduler {
    public static List<List<event>> detectConflicts(List<event> events) {
        List<List<event>> conflicts = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).startTime.isBefore(events.get(j).endTime) &&
                        events.get(i).endTime.isAfter(events.get(j).startTime)) {
                    List<event> conflictPair = new ArrayList<>();
                    conflictPair.add(events.get(i));
                    conflictPair.add(events.get(j));
                    conflicts.add(conflictPair);
                }
            }
        }
        return conflicts;
    }

    public static List<String> suggestResolution(List<List<event>> conflicts) {
        List<String> suggestions = new ArrayList<>();
        for (List<event> conflict : conflicts) {
            event event = conflict.get(1);
            if (event.name.equals("Workshop B")) {
                LocalTime newStartTime = LocalTime.of(13, 0);
                LocalTime newEndTime = newStartTime.plusMinutes(90);
                suggestions.add(String.format("Reschedule \"%s\" to Start: \"%s\", End: \"%s\"",
                        event.name, newStartTime, newEndTime));
            }
        }
        return suggestions;
    }

    public static void main(String[] args) {
        List<event> events = new ArrayList<>();
        events.add(new event("Meeting A", "09:00", "10:30"));
        events.add(new event("Workshop B", "10:00", "11:30"));
        events.add(new event("Lunch Break", "12:00", "13:00"));
        events.add(new event("Presentation C", "10:30", "12:00"));

        events.sort(Comparator.comparing(e -> e.startTime));
        System.out.println("Sorted Schedule:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i));
        }

        List<List<event>> conflicts = detectConflicts(events);
        System.out.println("\nConflicting Events:");
        for (int i = 0; i < conflicts.size(); i++) {
            System.out.printf("%d. \"%s\" and \"%s\"%n", i + 1, conflicts.get(i).get(0).name, conflicts.get(i).get(1).name);
        }

        List<String> suggestions = suggestResolution(conflicts);
        System.out.println("\nSuggested Resolutions:");
        for (String suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }
}