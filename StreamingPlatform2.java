/*
 * Да се имплементира класа StreamingPlatform во која ќе се чуваат информации за оценките кои корисниците ги даваат за филмовите кои ги гледале на платформата. За класата да се имплементираат следните методи:

    Конструктор StreamingPlatform()

    метод void addMovie (String id, String name)- за додавање на нов филм во платформата

    метод void addUser (String id, String username) - за додавање на нов корисник (гледач) во платформата

    метод addRating (String userId, String movieId, int rating) - за додавање на оценка rating на филмот со ID movieId од корисникот со ID userId. Рејтинзите се цели броеви во опсег [1,10]

    метод void topNMovies (int n) - за печатење на најдобрите n филмови на платформата според просечниот рејтинг

    метод favouriteMoviesForUsers(List<String> userIds) - за печатење на омилените филмови на корисниците чии ID се во листата userIds.
        За омилени филмови на еден корисник се сметаат филмовите на кои им има дадено најголема оценка

    метод void similarUsers(String userId) - за печатење на корисниците подредени во опаѓачки редослед според сличноста со корисникот со ID userId. За слични корисници се сметаат корисници кои имаат дадени блиски оценки на истите филмови. За да се пресмета сличност помеѓу двајца корисници потребно е да:

        Да се креираат мапи (за секој корисник) во која клуч ќе биде ID на филмот, а вредност оценката која корисникот ја дал на филмот. Ако на платформата има двајца корисници (u1,u2) и два филмови (m1,m2) и корисникот u1 дал оценка 7 за m1, a u2 дал оценка 8 за m2 и 7 за m1, мапата за u1 треба да биде {m1=7, m2=0}, a мапата за u2 треба да биде {m1=7, m2=8}.

        На креираните мапи да се пресмета косинусна сличност со помош на статичкиот метод cosineSimilarity во класата CosineSimilarityCalculator

 */

 import java.util.*;
import java.util.stream.Collectors;

class CosineSimilarityCalculator {

    public static double cosineSimilarity(Map<String, Integer> c1, Map<String, Integer> c2) {
        return cosineSimilarity(c1.values(), c2.values());
    }

    public static double cosineSimilarity(Collection<Integer> c1, Collection<Integer> c2) {
        int[] array1;
        int[] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1 = 0, down2 = 0;

        for (int i = 0; i < c1.size(); i++) {
            up += (array1[i] * array2[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down1 += (array1[i] * array1[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down2 += (array2[i] * array2[i]);
        }

        return up / (Math.sqrt(down1) * Math.sqrt(down2));
    }
}


public class StreamingPlatform2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StreamingPlatform sp = new StreamingPlatform();

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String [] parts = line.split("\\s+");

            if (parts[0].equals("addMovie")) {
                String id = parts[1];
                String name = Arrays.stream(parts).skip(2).collect(Collectors.joining(" "));
                sp.addMovie(id ,name);
            } else if (parts[0].equals("addUser")){
                String id = parts[1];
                String name = parts[2];
                sp.addUser(id ,name);
            } else if (parts[0].equals("addRating")){
                //String userId, String movieId, int rating
                String userId = parts[1];
                String movieId = parts[2];
                int rating = Integer.parseInt(parts[3]);
                sp.addRating(userId, movieId, rating);
            } else if (parts[0].equals("topNMovies")){
                int n = Integer.parseInt(parts[1]);
                System.out.println("TOP " + n + " MOVIES:");
                sp.topNMovies(n);
            } else if (parts[0].equals("favouriteMoviesForUsers")) {
                List<String> users = Arrays.stream(parts).skip(1).collect(Collectors.toList());
                System.out.println("FAVOURITE MOVIES FOR USERS WITH IDS: " + users.stream().collect(Collectors.joining(", ")));
                sp.favouriteMoviesForUsers(users);
            } else if (parts[0].equals("similarUsers")) {
                String userId = parts[1];
                System.out.println("SIMILAR USERS TO USER WITH ID: " + userId);
                sp.similarUsers(userId);
            }
        }
    }
}
