/*
 * За потребите на Скопскиот маратон, да се имплементира класа TeamRace со еден статички метод void findBestTeam (InputStream is, OutputStream os). 
Методот од влезен поток ги чита информациите за сите учесници од една ИТ компанија на трка на 5 километри. За секој учесник од тимот информациите се зададени во нов ред во следниот формат:

ID START_TIME END_TIME (пр. 1234 08:00:05 08:31:26)


Методот треба да формира еден тим од најдобрите 4 учесници од компанијата и истиот да го испечати на излезен поток заедно со вкупното време на трчање на членовите на најдобриот тим. Четирите најдобри учесници треба да се сортирани во растечки редослед според времето на трчање. 

Напомена: Во сите тест примери ќе има најмалку 4 учесници од компанијата.
--

For the needs of the Skopje Marathon, implement a class TeamRace with a static method void findBestTeam(InputStream is, OutputStream os).

The method reads information from the input stream for all participants from an IT company in a 5-kilometer race. For each team participant, the information is provided in a new line in the following format:

ID START_TIME END_TIME (ex. 1234 08:00:05 08:31:26)

The method should form a team of the best 4 participants from the company and print it to the output stream along with the total running time of the members of the best team. The four best participants should be sorted in ascending order according to their running time.

Note: In all test cases, there will be at least 4 participants from the company.

 */

 import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

class Racer{
    String ID;
    LocalTime startTime;
    LocalTime endTime;

    public Racer(String ID, LocalTime startTime, LocalTime endTime) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getTimeDiference(){
        return endTime.toSecondOfDay() - startTime.toSecondOfDay(); //tosecond pravi vremeto da bide presmetano vo sekundi
    }

    public Duration getTotal(){
        return Duration.between(startTime,endTime);
    }

    @Override
    public String toString() {
        Duration totalTime = getTotal();
        long hours = totalTime.toHours();
        long minutes = totalTime.toMinutesPart();
        long seconds = totalTime.toSecondsPart();
        return String.format("%s %02d:%02d:%02d",ID, hours, minutes, seconds);
    }
}

class TeamRace{
    ArrayList<Racer> racers = new ArrayList<Racer>();

    public TeamRace(ArrayList<Racer> racers) {
        this.racers = racers;
    }

    public TeamRace() {
        this.racers = new ArrayList<>();
    }

    void addRacers(String input) {
        String[] lines = input.split("\\n"); //ovde e \\n za da gi zeme line po line kako shto se inputted a dole e space for space, zatoa ima 2 splits

        for (String line : lines) {
            String[] fields = line.split("\\s+");

            if (fields.length != 3) {
                throw new RuntimeException("Invalid racer data: " + line);
            }

            try {
                String ID = fields[0].trim();
                LocalTime startTime = LocalTime.parse(fields[1].trim()); //trim brishe whitespace (VAZHNO za nekoi zadachi), dobra praktika
                LocalTime endTime = LocalTime.parse(fields[2].trim());
                racers.add(new Racer(ID, startTime, endTime));
            } catch (Exception e) {
                throw new RuntimeException("Error parsing racer data: " + line, e);
            }
        }
    }

    List<Racer> getBestRacers(){
        Collections.sort(racers, Comparator.comparingLong(Racer::getTimeDiference));

        return racers.subList(0, 4);
    }

    static void findBestTeam(InputStream is, OutputStream os) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is)); //za vlez
             PrintWriter writer = new PrintWriter(os)) { //za izlez
            StringBuilder inputBuilder = new StringBuilder(); //za da se konkatenira
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) { //dodeka line ima red, i ne e prazen red, dodaj go line na inputbuilderot
                inputBuilder.append(line).append("\n");
            }
            String input = inputBuilder.toString();

            TeamRace teamRace = new TeamRace();
            teamRace.addRacers(input);

            List<Racer> bestRacers = teamRace.getBestRacers();

            for (Racer racer : bestRacers) {
                writer.println(racer);
            }

            Duration totalDuration = Duration.ZERO;
            for (Racer racer : bestRacers) {
                totalDuration = totalDuration.plus(racer.getTotal());
            }
            long totalHours = totalDuration.toHours();
            long totalMinutes = totalDuration.toMinutesPart();
            long totalSeconds = totalDuration.toSecondsPart();
            writer.printf("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("IO Error", e);
        }
    }
}

public class RaceTest {
    public static void main(String[] args) {
        try {
            TeamRace.findBestTeam(System.in, System.out);
        } catch (Exception e) {
            throw new RuntimeException("IO Error" + e);
        }
    }
}
