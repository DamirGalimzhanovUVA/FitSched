import java.util.*;

public class CommonCalendarTimes {

    public static Map<String, List<int[]>> parseCalendar(String calendarStr) {
        Map<String, List<int[]>> calendar = new HashMap<>();
        String[] days = calendarStr.split(",");
        for (String day : days) {
            if (!day.contains(":"))
                continue;
            String[] parts = day.split(":");
            String dayName = parts[0].trim();
            String intervalsStr = parts[1].trim().replace("{", "").replace("}", "");
            String[] intervals = intervalsStr.split(",");
            List<int[]> intervalList = new ArrayList<>();
            for (String interval : intervals) {
                String[] times = interval.split("-");
                int start = Integer.parseInt(times[0].trim());
                int end = Integer.parseInt(times[1].trim());
                intervalList.add(new int[] { start, end });
            }
            calendar.put(dayName, intervalList);
        }

        // Print the parsed calendar
        System.out.println("Parsed Calendar:");
        for (Map.Entry<String, List<int[]>> entry : calendar.entrySet()) {
            String day = entry.getKey();
            List<int[]> intervals = entry.getValue();
            System.out.print(day + ": ");
            for (int[] interval : intervals) {
                System.out.print("{" + interval[0] + "-" + interval[1] + "} ");
            }
            System.out.println();
        }

        return calendar;
    }

    public static int timeToMinutes(int time) {
        return (time / 100) * 60 + (time % 100);
    }

    public static int minutesToTime(int minutes) {
        return (minutes / 60) * 100 + (minutes % 60);
    }

    public static Map<String, List<int[]>> findCommonTimes(List<String> usersCalendars) {
        if (usersCalendars == null || usersCalendars.isEmpty()) {
            return new HashMap<>();
        }

        List<Map<String, List<int[]>>> parsedCalendars = new ArrayList<>();
        for (String calendarStr : usersCalendars) {
            parsedCalendars.add(parseCalendar(calendarStr));
        }

        Map<String, List<Set<Integer>>> availableTimes = new HashMap<>();
        for (Map<String, List<int[]>> userCalendar : parsedCalendars) {
            for (Map.Entry<String, List<int[]>> entry : userCalendar.entrySet()) {
                String day = entry.getKey();
                List<int[]> intervals = entry.getValue();
                if (!availableTimes.containsKey(day)) {
                    availableTimes.put(day, new ArrayList<>());
                }
                for (int[] interval : intervals) {
                    int start = timeToMinutes(interval[0]);
                    int end = timeToMinutes(interval[1]);
                    Set<Integer> minuteSet = new HashSet<>();
                    for (int i = start; i < end; i++) {
                        minuteSet.add(i);
                    }
                    availableTimes.get(day).add(minuteSet);
                }
            }
        }

        Map<String, List<int[]>> commonTimes = new HashMap<>();
        for (Map.Entry<String, List<Set<Integer>>> entry : availableTimes.entrySet()) {
            String day = entry.getKey();
            List<Set<Integer>> intervals = entry.getValue();
            if (intervals.isEmpty())
                continue;

            // Find intersection of all intervals for the day
            Set<Integer> common = new HashSet<>(intervals.get(0));
            for (Set<Integer> interval : intervals) {
                common.retainAll(interval);
            }
            if (common.isEmpty())
                continue;

            // Reconstruct intervals from intersecting minutes
            List<int[]> commonIntervals = new ArrayList<>();
            List<Integer> sortedMinutes = new ArrayList<>(common);
            Collections.sort(sortedMinutes);
            int start = -1;
            int prev = -1;
            for (int minute : sortedMinutes) {
                if (start == -1) {
                    start = minute;
                } else if (minute != prev + 1) {
                    commonIntervals.add(new int[] { minutesToTime(start), minutesToTime(prev + 1) });
                    start = minute;
                }
                prev = minute;
            }
            if (start != -1) {
                commonIntervals.add(new int[] { minutesToTime(start), minutesToTime(prev + 1) });
            }
            commonTimes.put(day, commonIntervals);
        }

        return commonTimes;
    }

    public static void main(String[] args) {
        List<String> usersCalendars = Arrays.asList(
                "M:{1000-1200,1400-1600},T:{900-1100,1300-1500},W:{1000-1200}",
                "M:{1100-1300,1500-1700},T:{1000-1200,1400-1600},W:{1100-1300}",
                "M:{1000-1300,1400-1700},T:{900-1200,1300-1600},W:{1000-1300}");
        Map<String, List<int[]>> commonTimes = findCommonTimes(usersCalendars);
        System.out.println();

        for (Map.Entry<String, List<int[]>> entry : commonTimes.entrySet()) {
            String day = entry.getKey();
            List<int[]> intervals = entry.getValue();
            System.out.print(day + ": ");
            for (int[] interval : intervals) {
                System.out.print("{" + interval[0] + "-" + interval[1] + "} ");
            }
            System.out.println();
        }
    }
}