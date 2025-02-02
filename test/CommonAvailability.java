import java.util.*;

public class CommonAvailability {
    public static void main(String[] args) {
        List<String> users = Arrays.asList(
                "M:{1200-1600,1800-2000};T:{1200-1500}",
                "M:{1300-1700,1900-2100};T:{1400-1600}");

        for (String user : users) {
            System.out.println("Parsed Calendar for user: " + user);
            Map<String, List<int[]>> parsedSchedule = parseSchedule(user);
            parsedSchedule.forEach((day, slots) -> {
                System.out.print(day + ": ");
                for (int[] slot : slots) {
                    System.out.print("{" + slot[0] + "-" + slot[1] + "} ");
                }
                System.out.println();
            });
            System.out.println();
        }

        Map<String, List<int[]>> commonAvailability = findCommonAvailability(users);
        System.out.println("Common Availability:");
        commonAvailability.forEach((day, slots) -> {
            System.out.print(day + ": ");
            for (int[] slot : slots) {
                System.out.print("{" + slot[0] + "-" + slot[1] + "} ");
            }
            System.out.println();
        });
    }

    public static Map<String, List<int[]>> findCommonAvailability(List<String> users) {
        Map<String, List<int[]>> commonSchedule = new HashMap<>();

        for (String user : users) {
            Map<String, List<int[]>> userSchedule = parseSchedule(user);
            if (commonSchedule.isEmpty()) {
                commonSchedule.putAll(userSchedule);
            } else {
                for (String day : new HashSet<>(commonSchedule.keySet())) {
                    if (userSchedule.containsKey(day)) {
                        commonSchedule.put(day, intersectSlots(commonSchedule.get(day), userSchedule.get(day)));
                    } else {
                        commonSchedule.put(day, new ArrayList<>());
                    }
                }
            }
        }
        return commonSchedule;
    }

    private static Map<String, List<int[]>> parseSchedule(String user) {
        Map<String, List<int[]>> schedule = new HashMap<>();
        String[] dayEntries = user.split(";"); // Change delimiter to ;
        for (String dayEntry : dayEntries) {
            int colonIndex = dayEntry.indexOf(":");
            if (colonIndex == -1)
                continue;
            String day = dayEntry.substring(0, colonIndex).trim();
            String times = dayEntry.substring(colonIndex + 1).replaceAll("[{}]", "").trim();
            schedule.putIfAbsent(day, new ArrayList<>());

            for (String time : times.split(",")) {
                if (!time.isEmpty() && time.contains("-")) {
                    String[] range = time.split("-");
                    schedule.get(day).add(new int[] { Integer.parseInt(range[0]), Integer.parseInt(range[1]) });
                }
            }
        }
        return schedule;
    }

    private static List<int[]> intersectSlots(List<int[]> list1, List<int[]> list2) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            int start = Math.max(list1.get(i)[0], list2.get(j)[0]);
            int end = Math.min(list1.get(i)[1], list2.get(j)[1]);
            if (start < end) {
                result.add(new int[] { start, end });
            }
            if (list1.get(i)[1] < list2.get(j)[1])
                i++;
            else
                j++;
        }
        return result;
    }
}

// import java.util.*;

// public class CommonAvailability {
// public static void main(String[] args) {
// List<String> users = Arrays.asList(
// "M:{1200-1300,1500-1800};T:{1200-1500}",
// "M:{1100-1400,1600-1700};T:{1300-1400}",
// "M:{1200-1300,1400-1700};T:{1200-1400}");

// for (String user : users) {
// System.out.println("Parsed Calendar for user: " + user);
// Map<String, List<int[]>> parsedSchedule = parseSchedule(user);
// parsedSchedule.forEach((day, slots) -> {
// System.out.print(day + ": ");
// for (int[] slot : slots) {
// System.out.print("{" + slot[0] + "-" + slot[1] + "} ");
// }
// System.out.println();
// });
// System.out.println();
// }

// Map<String, List<int[]>> commonAvailability = findCommonAvailability(users);
// System.out.println("Common Availability:");
// commonAvailability.forEach((day, slots) -> {
// System.out.print(day + ": ");
// for (int[] slot : slots) {
// System.out.print("{" + slot[0] + "-" + slot[1] + "} ");
// }
// System.out.println();
// });
// }

// public static Map<String, List<int[]>> findCommonAvailability(List<String>
// users) {
// Map<String, List<int[]>> commonSchedule = new HashMap<>();

// for (String user : users) {
// Map<String, List<int[]>> userSchedule = parseSchedule(user);
// if (commonSchedule.isEmpty()) {
// commonSchedule.putAll(userSchedule);
// } else {
// for (String day : new HashSet<>(commonSchedule.keySet())) {
// if (userSchedule.containsKey(day)) {
// commonSchedule.put(day, intersectSlots(commonSchedule.get(day),
// userSchedule.get(day)));
// } else {
// commonSchedule.put(day, new ArrayList<>());
// }
// }
// }
// }
// return commonSchedule;
// }

// private static Map<String, List<int[]>> parseSchedule(String user) {
// Map<String, List<int[]>> schedule = new HashMap<>();
// String[] dayEntries = user.split(";");
// // for (String dayEntry : dayEntries) {
// // System.out.println(dayEntry);
// // }
// for (String dayEntry : dayEntries) {
// int colonIndex = dayEntry.indexOf(":");
// if (colonIndex == -1)
// continue;
// String day = dayEntry.substring(0, colonIndex).trim();
// String times = dayEntry.substring(colonIndex + 1).replaceAll("[{}]",
// "").trim();
// schedule.putIfAbsent(day, new ArrayList<>());

// for (String time : times.split(",")) {
// if (!time.isEmpty() && time.contains("-")) {
// String[] range = time.split("-");
// schedule.get(day).add(new int[] { Integer.parseInt(range[0]),
// Integer.parseInt(range[1]) });
// }
// }
// }
// return schedule;
// }

// private static List<int[]> intersectSlots(List<int[]> list1, List<int[]>
// list2) {
// List<int[]> result = new ArrayList<>();
// int i = 0, j = 0;
// while (i < list1.size() && j < list2.size()) {
// int start = Math.max(list1.get(i)[0], list2.get(j)[0]);
// int end = Math.min(list1.get(i)[1], list2.get(j)[1]);
// if (start < end) {
// result.add(new int[] { start, end });
// }
// if (list1.get(i)[1] < list2.get(j)[1])
// i++;
// else
// j++;
// }
// return result;
// }
// }
