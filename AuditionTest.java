/*
 * Да се имплементира класа за аудиција Audition со следните методи:

    void addParticpant(String city, String code, String name, int age) додава нов кандидат со код code, име и возраст за аудиција во даден град city. Во ист град не се дозволува додавање на кандидат со ист код како некој претходно додаден кандидат (додавањето се игнорира, а комплексноста на овој метод треба да биде $O(1)$)
    void listByCity(String city) ги печати сите кандидати од даден град подредени според името, а ако е исто според возраста (комплексноста на овој метод не треба да надминува $O(n*log_2(n))$, каде $n$ е бројот на кандидати во дадениот град).

 */

 import java.util.*;

class Candidate{
    String city;
    String code;
    String name;
    int age;

    public Candidate(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return code + " " + name + " " + age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCode() {
        return code;
    }
}
class Audition{
    ArrayList<Candidate> candidates = new ArrayList<>();
    HashSet<String> candidateCodes = new HashSet<>(); //mora so hashset za da e O(1)
    void addParticpant(String city, String code, String name, int age){
        String compositeKey =city + "|" + code;
        if (!candidateCodes.contains(compositeKey)){
            Candidate newCandidate = new Candidate(city,code,name,age);
            this.candidates.add(newCandidate);
            candidateCodes.add(compositeKey);
        }
    }

    void listByCity(String city){
        candidates.stream()
                .filter(candidate -> candidate.city.equals(city))
                .sorted(Comparator
                        .comparing(Candidate::getName)
                        .thenComparingInt(Candidate::getAge)
                        .thenComparing(Candidate::getCode))
                .forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}