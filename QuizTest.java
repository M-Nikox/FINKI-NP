/*
 * Да се дефинира класа Quiz за репрезентација на еден квиз во кој може да се наоѓаат повеќе прашања од 2 типа (true/false прашање или прашање со избор на еден точен одговор од 5 понудени одговори A/B/C/D/E). За класата Quiz да се имплементираат следните методи:

    конструктор
    void addQuestion(String questionData) - метод за додавање на прашање во квизот. Податоците за прашањето се дадени во текстуална форма и може да бидат во следните два формати согласно типот на прашањето:
        TF;text;points;answer (answer може да биде true или false)
        MC;text;points;answer (answer е каракатер кој може да ја има вредноста A/B/C/D/E)
            Со помош на исклучок од тип InvalidOperationException да се спречи додавање на прашање со повеќе понудени одговори во кое како точен одговор се наоѓа некоја друга опција освен опциите A/B/C/D/E.
    void printQuiz(OutputStream os) - метод за печатење на квизот на излезен поток. Потребно е да се испечатат сите прашања од квизот подредени според бројот на поени на прашањата во опаѓачки редослед.
    void answerQuiz (List<String> answers, OutputStream os) - метод којшто ќе ги одговори сите прашања од квизот (во редоследот во којшто се внесени) со помош на одговорите од листата answers. Методот треба да испечати извештај по колку поени се освоени од секое прашање и колку вкупно поени се освоени од квизот (види тест пример). Да се фрли исклучок од тип InvalidOperationException доколку бројот на одговорите во `answers е различен од број на прашања во квизот.
        За точен одговор на true/false прашање се добиваат сите предвидени поени, а за неточен одговор се добиваат 0 поени
        За точен одговор на прашање со повеќе понудени одговори се добиваат сите предвидени поени, а за неточен одговор се добиваат негативни поени (20% од предвидените поени).

 */

 import java.io.PrintStream;
import java.util.*;

class InvalidOperationException extends Exception {
    InvalidOperationException(String s){
        super(s + " is not allowed option for this question");
    }
}

class Question {
    String type;
    String text;
    String answer;
    float points;

    public Question(String type, String text, float points, String answer) {
        this.type = type;
        this.text = text;
        this.answer = answer;
        this.points = points;
    }

    public float getPoints() {
        return points;
    }

    public String getAnswer() {
        return answer;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type.equals("MC")){
            return
                    "Multiple Choice Question: " +
                            text + " " +
                            "Points " + (int) points +
                            " Answer: " + answer;
        } else
        if (type.equals("TF")){
            return
                    "True/False Question: " +
                            text + " " +
                            "Points: " + (int) points +
                            " Answer: " + answer;
        }
        return "Sum ting wong";
    }
}

class Quiz {
    List<Question> questions;
    List<Question> answers;
    float points;

    Quiz(){
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        points=0;
    }
    //type;text;points;answer
    public void addQuestion(String s) throws InvalidOperationException {
        String[] parts = s.split(";");
        if (parts[0].startsWith("TF") || parts[0].startsWith("MC")){
            if (validate(s)){
                Question question = new Question(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                questions.add(question);
            } else throw new InvalidOperationException(parts[3]);
        }
    }

    public void printQuiz(PrintStream out) {
        questions.sort(Comparator.comparingDouble(Question::getPoints).reversed());

        for (Question question : questions){
            out.println(question);
        }
    }

    public void answerQuiz(List<String> answers, PrintStream out){
        if (answers.size() > questions.size()){
            System.out.println("Answers and questions must be of same length!");
            return;
        } else {
            for (int i=0; i<questions.size(); i++){
                if (questions.get(i).getAnswer().equals(answers.get(i))){
                    points+=questions.get(i).getPoints();
                    out.println(i+1 + ". " + String.format("%.2f",questions.get(i).getPoints()));
                } else {
                    if (questions.get(i).getType().equals("TF")){
                        out.println(i+1 + ". 0.00");
                    } else {
                        points-=questions.get(i).getPoints()*0.20;
                        out.println(i+1 + ". " + String.format("-%.2f",questions.get(i).getPoints()*0.20));
                    }
                }
            }
        }
        out.println("Total points: " + String.format("%.2f",points));
    }

    public boolean validate(String s){
        String[] parts = s.split(";");
        String str2beval=parts[3];
        return str2beval.contains("A") ||
                str2beval.contains("B") ||
                str2beval.contains("C") ||
                str2beval.contains("D") ||
                str2beval.contains("E") ||
                str2beval.contains("true") ||
                str2beval.contains("false");
    }
}

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            quiz.answerQuiz(answers, System.out);
        } else {
            System.out.println("Invalid test case");
        }
    }
}
