/*
 * Да се имплементира класа Parking за евидентирање на паркираните возила на еден паркинг. За класата да се имплементираат следните методи:

    Конструктор Parking(int capacity) - за креирање на објект од класата Parking со максимален број на возила capacity

    void update (String registration, String spot, LocalDateTime timestamp, boolean entry) - метод за ажурирање на информации за едно возило во паркингот. Овој метод секогаш ќе се повикува точно 2 пати, прв пат за влез на возилото во паркингот и втор пат за излез од паркингот. Доколку entry e true, тоа означува возилото со регистрација registration се паркира на местото sport во времето timestamp. Доколку entry е false, тоа означува дека возилото излегува од паркингот во времето timestamp.

    void currentState() - метод кој печати колку % е исполнет капацитетот на паркингот во моментот и потоа ги печати информациите за моментално паркираните возила сортирани според времето на влез во паркингот во опаѓачки редослед.

    void history() - метод кој печати информации за паркирани возила кои веќе го напуштиле паркингот, сортирани според времетраењето на паркирањето во опаѓачки редослед.

    Map<String, Integer> carStatistics() - метод кој враќа мапа во која клуч е регистрација на возило, а вредност е бројот на паркирања на тоа возило на паркингот. Паровите да се сортирани според клучот.
        За паркирање се смета и активно паркирање т.е. доколку возилото моментално e во паркингот.

    Map<String,Double> spotOccupancy (LocalDateTime start, LocalDateTime end) - метод кој враќа мапа во која клуч е ID на паркинг местото, а вредноста е процентот од времето изминато од start до end во кое паркинг местото било зафатено.

Во почетниот код ви е даден и статичкиот методот durationBetween од класата DateUtil кој пресметува изминати минути помеѓу два објекти од класата LocalDateTime. 
 */

 import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class DateUtil {
    public static long durationBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes();
    }
}

class Vehicle{
    String registration;
    String spot;
    LocalDateTime timestamp;
    boolean entry;

    public Vehicle(String registration, String spot, LocalDateTime timestamp, boolean entry) {
        this.registration = registration;
        this.spot = spot;
        this.timestamp = timestamp;
        this.entry = entry;
    }

    @Override
    public String toString() {
        return String.format("Registration number: %s, Spot: %s, Start timestamp: %s", registration, spot, timestamp);
    }
}

class Parking {
    int capacity;
    ArrayList<Vehicle> vehicles = new ArrayList<>();
    ArrayList<Vehicle> vehicleHistory = new ArrayList<>();

    Parking(int capacity){
        this.capacity=capacity;
    }

    void update (String registration, String spot, LocalDateTime timestamp, boolean entry){
        Vehicle vehicle = new Vehicle(registration, spot, timestamp, entry);
        if (entry){
            vehicles.add(vehicle);
        } else {
            vehicles.remove(vehicle);
        }
        vehicleHistory.add(vehicle);
    }


    public void currentState() {
        double fcap = (double) vehicles.size() / capacity * 100.00;
        System.out.println("Capacity filled: " + fcap);
        System.out.println(vehicles);
    }

    public void history() {
    }

    //public <V> Map<Object,V> carStatistics() {
    //}

    //public <V> Map<Object,V> spotOccupancy(LocalDateTime start, LocalDateTime end) {
    //}
}

public class ParkingTesting {

    public static <K, V extends Comparable<V>> void printMapSortedByValue(Map<K, V> map) {
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s -> %s", entry.getKey().toString(), entry.getValue().toString())));

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int capacity = Integer.parseInt(sc.nextLine());

        Parking parking = new Parking(capacity);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equals("update")) {
                String registration = parts[1];
                String spot = parts[2];
                LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
                boolean entrance = Boolean.parseBoolean(parts[4]);
                parking.update(registration, spot, timestamp, entrance);
            } else if (parts[0].equals("currentState")) {
                System.out.println("PARKING CURRENT STATE");
                parking.currentState();
            } else if (parts[0].equals("history")) {
                System.out.println("PARKING HISTORY");
                parking.history();
            } else if (parts[0].equals("carStatistics")) {
                System.out.println("CAR STATISTICS");
                //printMapSortedByValue(parking.carStatistics());
            } else if (parts[0].equals("spotOccupancy")) {
                LocalDateTime start = LocalDateTime.parse(parts[1]);
                LocalDateTime end = LocalDateTime.parse(parts[2]);
                //printMapSortedByValue(parking.spotOccupancy(start, end));
            }
        }
    }
}
