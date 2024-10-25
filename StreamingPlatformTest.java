/*
 * Да се дефинира класа StreamingPlatform за репрезентација на една платформа за прикажување и гледање на филмови и серии. За класата StreamingPlatform да се имплементираат следните методи:

    Конструктор

    void addItem (String data) - метод за додавање на ставка во платформата. Во платформата може да има два типа на ставки: филмови и серии. Форматот на data може да биде: 

        NAME;genre1,genre2,…,genreN;rating1 rating2 rating3 .. ratingN за филмови (името на филмот, жанровите во кои припаѓа филмот, рејтинзите на гледачите за филмот)

        NAME;genre1,genre2,…,genreN;episode1 rating1 rating2 rating3 .. ratingN; episode1 rating1 rating2 rating3 .. ratingN;…;episodeN rating1 rating2 rating3 ... ratingN за серии (името на серијата, жанровите во кои припаѓа серијата, рејтинзите на гледачите за секоја епизодита)

    void listAllItems (OutputStream os) - метод за печатење на излезен поток на сите ставки во платформата. Ставките треба да бидат сортирани во опаѓачки редослед според рејтингот. Рејтингот се пресметува на следниот начин:

        Рејтингот на филмовите е просекот на рејтинзите на гледачите помножен со min(број_на_рејтнизи/20.0,1.0)

        Рејтингот на серијата се пресметува како просек од рејтинзите на трите епизоди со најдобар рејтинг. Рејтингот на една епизода се пресметува исто како рејтинг на филм.

    void listFromGenre (String genre, OutputStream os) - метод за печатење на излезен поток на сите ставки од жанрот genrge (во ист формат како претходниот метод).


Напомена: Во сите тест примери, сериите ќе имаат најмалку 3 епизоди!

--

Define a class `StreamingPlatform` to represent a platform for displaying and watching movies and series. Implement the following methods for the `StreamingPlatform` class:

    Constructor
    void addItem(String data) - a method for adding an item to the platform. The platform can have two types of items: movies and series. The format of the data can be:
        For movies: NAME;genre1,genre2,...,genreN;rating1 rating2 rating3 .. ratingN (name of the movie, genres it belongs to, viewer ratings for the movie)
        For series: NAME;genre1,genre2,...,genreN;episode1 rating1 rating2 rating3 .. ratingN; episode1 rating1 rating2 rating3 .. ratingN;...;episodeN rating1 rating2 rating3 ... ratingN (name of the series, genres it belongs to, viewer ratings for each episode)
    void listAllItems(OutputStream os) - a method for printing to an output stream all items on the platform. Items should be sorted in descending order based on their ratings. Ratings are calculated as follows:
        For movies, the rating is the average of viewer ratings multiplied by min(number_of_ratings/20.0, 1.0).
        For a series, the rating is calculated as the average of the ratings of the three episodes with the highest ratings. The rating for each episode is calculated the same way as the rating for a movie.
    void listFromGenre(String genre, OutputStream os) - a method for printing to an output stream all items from the specified genre (in the same format as the previous method).

Note: In all test cases, series will have at least 3 episodes!

 */

 import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class StreamingPlatform {
    String name;
    ArrayList<Movie> movies;
    ArrayList<TVShow> tvShows;

    public StreamingPlatform(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
        this.tvShows = new ArrayList<>();
    }

    public StreamingPlatform() {
        this.name = "";
        this.movies = new ArrayList<>();
        this.tvShows = new ArrayList<>();
    }

    // Method to add a movie or TV show
    public void addItem(String data) {
        String[] parts = data.split(";");

        // Extract title
        String title = parts[0].trim();

        // Extract genres
        String[] genreArray = parts[1].split(",");
        ArrayList<String> genres = new ArrayList<>(Arrays.asList(genreArray));

        // Check if input contains episodes
        if (parts[2].trim().startsWith("S")) {
            // Extract episodes
            ArrayList<Episode> episodes = new ArrayList<>();
            for (int i = 2; i < parts.length; i++) {
                String episodeData = parts[i].trim();
                String[] episodeParts = episodeData.split(" ");
                String episodeIdentifier = episodeParts[0].trim();
                ArrayList<Double> episodeRatings = new ArrayList<>();

                for (int j = 1; j < episodeParts.length; j++) {
                    episodeRatings.add(Double.parseDouble(episodeParts[j].trim()));
                }

                // Create Episode object and add to episodes list
                Episode episode = new Episode(episodeIdentifier, episodeRatings);
                episodes.add(episode);
            }

            // Create TVShow object with episodes and add to tvShows list
            TVShow tvShow = new TVShow(title, genres, episodes);
            tvShows.add(tvShow);
        } else {
            // If no episodes, treat as a regular movie
            String[] ratingsArray = parts[2].split(" ");
            ArrayList<Double> ratings = new ArrayList<>();
            for (String ratingStr : ratingsArray) {
                ratings.add(Double.parseDouble(ratingStr.trim()));
            }
            Movie movie = new Movie(title, genres, ratings);
            movies.add(movie);
        }
    }

    public void listAllItems(PrintStream out) {
        for (Movie movie : movies) {
            out.println(movie);
        }
        for (TVShow tvShow : tvShows) {
            out.println(tvShow);
        }
    }

    public void listFromGenre(String data, PrintStream out) {
        String genreToSearch = data.trim().toLowerCase();
        for (Movie movie : movies) {
            if (movie.genre.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(genreToSearch)) {
                out.println(movie);
            }
        }
        for (TVShow tvShow : tvShows) {
            if (tvShow.genre.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(genreToSearch)) {
                out.println(tvShow);
            }
        }
    }

    static class Movie {
        String title;
        ArrayList<String> genre;
        ArrayList<Double> ratings;

        public Movie(String title, ArrayList<String> genre, ArrayList<Double> ratings) {
            this.title = title;
            this.genre = genre;
            this.ratings = ratings;
        }

        public double ratingAvg() {
            double sum = 0;
            for (int i = 0; i < ratings.size(); i++) {
                sum += ratings.get(i);
            }
            double avg = sum / ratings.size();
            return avg * Math.min(ratings.size() / 20.0, 1.0);
        }

        @Override
        public String toString() {
            return "Movie " + title + " " + String.format("%.4f", ratingAvg());
        }
    }

    static class TVShow {
        String title;
        ArrayList<String> genre;
        ArrayList<Episode> episodes;

        public TVShow(String title, ArrayList<String> genre, ArrayList<Episode> episodes) {
            this.title = title;
            this.genre = genre;
            this.episodes = episodes;
        }

        public double ratingAvg() {
            List<Double> episodeRatings = episodes.stream()
                    .map(Episode::ratingAvg)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            double sum = 0;
            int count = Math.min(3, episodeRatings.size());
            for (int i = 0; i < count; i++) {
                sum += episodeRatings.get(i);
            }
            return sum / count;
        }

        @Override
        public String toString() {
            return "TV Show " + title + " " + String.format("%.4f", ratingAvg()) + " (" + episodes.size() + " episodes)";
        }
    }

    static class Episode {
        String title;
        ArrayList<Double> ratings;

        public Episode(String title, ArrayList<Double> ratings) {
            this.title = title;
            this.ratings = ratings;
        }

        public double ratingAvg() {
            double sum = 0;
            for (int i = 0; i < ratings.size(); i++) {
                sum += ratings.get(i);
            }
            double avg = sum / ratings.size();
            return avg * Math.min(ratings.size() / 20.0, 1.0);
        }
    }
}

public class StreamingPlatformTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StreamingPlatform sp = new StreamingPlatform();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            String method = parts[0];
            String data = Arrays.stream(parts).skip(1).collect(Collectors.joining(" "));
            if (method.equals("addItem")) {
                sp.addItem(data);
            } else if (method.equals("listAllItems")) {
                sp.listAllItems(System.out);
            } else if (method.equals("listFromGenre")) {
                sp.listFromGenre(data, System.out);
            }
        }
    }
}
