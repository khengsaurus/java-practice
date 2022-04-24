package ClassQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//    1396.Design Underground System
class UndergroundSystem {
    private Map<String, Map<String, List<Integer>>> fromTo;
    private Map<Integer, Integer> timeIn;
    private Map<Integer, String> stationIn;

    public UndergroundSystem() {
        fromTo = new HashMap<>();
        timeIn = new HashMap<>();
        stationIn = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        timeIn.put(id, t);
        stationIn.put(id, stationName);
    }

    public void checkOut(int id, String stationName, int t) {
        int timeTaken = t - timeIn.get(id);
        String stationFrom = stationIn.get(id);
        fromTo.computeIfAbsent(stationFrom, k -> new HashMap<>())
                .computeIfAbsent(stationName, k -> new ArrayList<>())
                .add(timeTaken);
    }

    public double getAverageTime(String startStation, String endStation) {
        int sum = 0;
        List<Integer> times = fromTo.get(startStation).get(endStation);
        for (int time : times) sum += time;
        return (double) sum / times.size();
    }
}