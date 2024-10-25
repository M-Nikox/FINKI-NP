/*
 * Да се дефинира класа Risk со единствен метод int processAttacksData (InputStream is).

Методот од влезен поток ги чита информациите за извршените напади на еден играч врз другите играчи во стратешката игра Ризик. За секој поединечен напад информациите ќе се дадени во посебен ред и ќе бидат во следниот формат:

X1 X2 X3;Y1 Y2 Y3, каде што X1, X2 и X3 се броеви добиени со фрлање на 3 коцки (број од 1-6) на напаѓачот, а Y1, Y2 и Y3 се броеви добиени со фрлање на 3 коцки (број од 1-6) за одбрана. Како резултат методот треба да го врати бројот на целосно успешнo извршени напади. 

Еден напад се смета дека е целосно успешен доколку сите коцки фрлени од напаѓачот имаат број поголем од бројот на фрлените коцки на нападнатиот (најголемиот број на фрлените коцки на напаѓачот е поголем од најголемиот број на фрлените коцки на нападнатиот и соодветно за сите останати обиди (редоследот на фрлените коцки не игра улога). 

Пример влезот: 5 3 4; 2 4 1 се смета за целосно успешен напад бидејќи најголемата вредност на напаѓачот (5) е поголема од најголемата вредност на нападнатиот (4), втората најголема вредност на напаѓачот (4) е поголема од втората најголема вредност на нападнатиот (2), како и третата најголема вредност (3) на напаѓачот е поголема од третата најголемата вредност на нападнатиот (1).
 */

 import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}

class Round {
    List<Integer> attackData;
    List<Integer> defendData;

    Round(){
        this.attackData=new ArrayList<>();
        this.defendData=new ArrayList<>();
    }


    public Round(List<Integer> attackData, List<Integer> defendData) {
        this.attackData = attackData;
        this.defendData = defendData;
    }

    public void addRound(String s){
        String [] attackdefend = s.split(";");
        String [] attack = attackdefend[0].split("\\s+");
        String [] defend = attackdefend[1].split("\\s+");

        for (String atk : attack){
            attackData.add(Integer.valueOf(atk));
        }

        for (String def : defend){
            defendData.add(Integer.valueOf(def));
        }

        defendData.sort(Collections.reverseOrder());
        attackData.sort(Collections.reverseOrder());
    }

}

class Risk {
    List<Round> rounds;
    static int points=0;

    public static int getPoints() {
        return points;
    }

    Risk() {
        rounds = new ArrayList<>();
    }

    void calcPoints(){
        for (Round round : rounds){
            boolean win = true;
            for (int j=0; j<round.attackData.size(); j++){
                if (round.attackData.get(j) <= round.defendData.get(j)){
                    win=false;
                    break;
                }
            }
            if (win){
                points++;
            }
        }
    }

    public int processAttacksData(InputStream in) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            reader.lines().forEach(line -> {
                Round round = new Round();
                round.addRound(line);
                rounds.add(round);
            });
            calcPoints();
        return getPoints();
    }
}