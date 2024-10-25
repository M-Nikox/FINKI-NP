/*
 * Треба да се напише класа SuperString. Класата во позадина претставува листа на стрингови LinkedList<String> и ги нуди следните методи:

    SuperString() - креира празен стринг
    append(String s) - го додава стрингот на крајот во листата
    insert(String s) - го додава стрингот на почеток на листата
    contains(String s):boolean - враќа true доколку стрингот s се наоѓа во супер-стрингот. Стрингот s може да е разделен во повеќе подстрингови во листата. Пр: list = [ "st" , "arz" , "andrej" ] , contains("tarzan") –> true
    reverse() - го превртува стрингот на следниов начин. Ги превртува сите елементи во листата, а потоа и секој подстринг како елемент посебно го превртува. list = [ "st" , "arz" , "andrej: ]; reverse(); list = [ "jerdna", "zra", "ts"]
    toString():String - ги враќа конкатенирани сите елементи во листата list = [ "st" , "arz" , "andrej"]; toString() -> "starzandrej"
    removeLast(int k) – ги отстранува последнo додадените k подстрингови

 */

 import java.util.Scanner;

class SuperString {
    StringBuilder strBuilder;
    int[] counter = new int[100];
    int k=0;

    SuperString() {
        this.strBuilder = new StringBuilder();
    }

    public void append(String s) {
        strBuilder.append(s);
        counter[k++]+=s.length();
    }

    public void insert(String s) {
        StringBuilder newStr = new StringBuilder(s);
        newStr.append(strBuilder);
        strBuilder=newStr;
    }

    public boolean contains(String s){
        return strBuilder.indexOf(s) != -1;
    }

    public void reverse(){
        strBuilder= new StringBuilder(strBuilder).reverse();
    }

    @Override
    public String toString() {
        return strBuilder.toString();
    }

    public void removeLast(int k) {
        if (k <= 0 || k > counter.length) {
            return;  // Nothing to remove if k is non-positive or greater than the number of substrings
        }

        // Calculate the total length of the last k substrings
        int totalLengthToRemove = 0;
        for (int i = counter.length - k; i < counter.length; i++) {
            totalLengthToRemove += counter[i];
        }

        // Remove the last k substrings from the strBuilder
        strBuilder.delete(strBuilder.length() - totalLengthToRemove, strBuilder.length());

        // Update the counter array
        k -= Math.min(k, counter.length);  // Ensure k is within the valid range
        for (int i = counter.length - k; i < counter.length; i++) {
            counter[i] = 0;
        }
    }

}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}
