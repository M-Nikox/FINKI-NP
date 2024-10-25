/*
 * Да се имплементира класа F1Race која ќе чита од влезен тек (стандарден влез, датотека, ...) податоци за времињата од последните 3 круга на неколку пилоти на Ф1 трка. Податоците се во следниот формат:

Driver_name lap1 lap2 lap3, притоа lap е во формат mm:ss:nnn каде mm се минути ss се секунди nnn се милисекунди (илјадити делови од секундата). Пример:

Vetel 1:55:523 1:54:987 1:56:134.

Ваша задача е да ги имплементирате методите:

    F1Race() - default конструктор
    void readResults(InputStream inputStream) - метод за читање на податоците
    void printSorted(OutputStream outputStream) - метод кој ги печати сите пилоти сортирани според нивното најдобро време (најкраткото време од нивните 3 последни круга) во формат Driver_name best_lap со 10 места за името на возачот (порамнето од лево) и 10 места за времето на најдобриот круг порамнето од десно. Притоа времето е во истиот формат со времињата кои се читаат.

 */

 import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Driver{
    String name;
    List<String> lapTimes;
    Driver(String name){
        this.name=name;
        this.lapTimes=new ArrayList<>();
    }

    Driver(){
        this.name="";
        this.lapTimes=new ArrayList<>();
    }

    //gettersetter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLapTimes() {
        return lapTimes;
    }

    public void setLapTimes(List<String> lapTimes) {
        this.lapTimes = lapTimes;
    }

    //createadriver


    @Override
    public String toString() {
        return name + " " + String.join(" ", lapTimes);
    }

    public void create(String input){
        String parts[] = input.split("\\s+");
        setName(parts[0]);
        Arrays.stream(parts, 1, parts.length).forEach(lapTime -> lapTimes.add(lapTime));
    }

    public String getBestLapTime() {
        String bestLap = null;

        for (String currtime : lapTimes) {
            if (currtime != null && (bestLap == null || currtime.compareTo(bestLap) < 0)) {
                bestLap = currtime;
            }
        }

        return bestLap;
    }

}

class F1Race {
    List<Driver> drivers;

    F1Race(){
        this.drivers = new ArrayList<>();
    }
    public void readResults(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        reader.lines().forEach(line -> {
            Driver driver = new Driver();
            driver.create(line);
            drivers.add(driver);
        });
    }
    public void printSorted(PrintStream out) {
        List<Driver> sortedDrivers = drivers.stream()
                .sorted(Comparator.comparing(Driver::getBestLapTime))
                .collect(Collectors.toList());

        for (int i = 0; i<sortedDrivers.size(); i++) {
            out.println(i+1 + ". " + String.format("%-10s %9s", sortedDrivers.get(i).getName(), sortedDrivers.get(i).getBestLapTime()));
        }
    }

}