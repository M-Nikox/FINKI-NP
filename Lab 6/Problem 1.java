/*
 * Со користење на ArrayList или LinkedList сакаме да развиеме класа за работа со листи од цели броеви IntegerList. Листата ги има следните вообичаени методи:

    IntegerList() - конструктор кој креира празна листа.
    IntegerList(Integer… numbers) - конструктор коj креира листа што ги содржи елементите numbers во истиот редослед во кој тие се појавуваат во низата.
    add(int el, int idx) - го додава елементот на соодветниот индекс. Доколку има други елементи после таа позиција истите се поместуваат на десно за едно место (нивните индекси им се зголемуваат за 1). Доколку idx е поголемо од сегашната големина на листата ја зголемуваме листата и сите нови елементи ги иницијалираме на нула (освен тој на позиција idx кој го поставуваме на el).
    remove(int idx):int - го отстранува елементот на дадена позиција од листата и истиот го враќа. Доколку после таа позиција има други елементи истите се поместуваат во лево (нивните индекси се намалуваат за 1).
    set(int el, int idx) - го поставува елементот на соодветната позиција.
    get(int idx):int - го враќа елементот на соодветната позиција.
    size():int - го враќа бројот на елементи во листата.

Освен овие методи IntegerList треба да нуди и неколку методи згодни за работа со цели броеви:

    count(int el):int - го враќа бројот на појавувања на соодветниот елемент во листата.
    removeDuplicates() - врши отстранување на дупликат елементите од листата. Доколку некој елемент се сретнува повеќе пати во листата ја оставаме само последната копија од него. Пр: 1,2,4,3,4,5. -> removeDuplicates() -> 1,2,3,4,5
    sumFirst(int k):int - ја дава сумата на првите k елементи.
    sumLast(int k):int - ја дава сумата на последните k елементи.
    shiftRight(int idx, int k) - го поместува елементот на позиција idx за k места во десно. При поместувањето листата ја третираме како да е кружна. Пр: list = [1,2,3,4]; list.shiftLeft(1,2); list = [1,3,4,2] - (листата е нула индексирана така да индексот 1 всушност се однесува на елементот 2 кој го поместуваме две места во десно) list = [1,2,3,4]; list.shiftLeft(2, 3); list = [1,3,2,4] - елементот 3 го поместуваме 3 места во десно. По две поместувања стигнуваме до крајот на листата и потоа продолжуваме да итерираме од почетокот на листата уште едно место и овде го сместуваме.
    shiftLeft(int idx , int k) - аналогно на shiftRight.
    addValue(int value):IntegerList - враќа нова листа каде елементите се добиваат од оригиналната листа со додавање на value на секој елемент. Пр: list = [1,4,3]; addValue(5) -> [6,9,8]

Забелешка која важи за сите методи освен add: Ако индексот е негативен или поголем од тековната големина на листата фрламе исклучок ArrayIndexOutOfBoundsException.
 */

 import java.util.*;

class IntegerList{
    ArrayList<Integer> list = new ArrayList<>();

    public IntegerList() {
        this.list=new ArrayList<>();
    }

    public IntegerList(Integer... numbers) {
        this.list = new ArrayList<>(Arrays.asList(numbers));
    }

    public void add(int el, int idx){
        if(idx > list.size()){
            list.add(el);
            return;
        }
        list.add(idx, el);
    }


    public int remove(int idx){
        ArrayList<Integer> newList = new ArrayList<>();
        int element=0;
        for (Integer integer : list) {
            if (integer.equals(idx)) {
                element = integer;
                continue;
            } else {
                newList.add(integer);
            }
        }
        list=newList;
        return element;
    }

    public void set(int el, int idx){
        list.set(idx,el);
    }

    public int get(int idx){
        return list.get(idx);
    }

    public int size(){
        int ct=0;
        for (Integer integer : list){
            ct++;
        }
        return ct;
    }

    public int count(int el){
        int ct=0;
        for (Integer integer : list){
            if (Objects.equals(integer, el)){
                ct++;
            }
        }
        return ct;
    }

    public void removeDuplicates() {
        ArrayList<Integer> newList = new ArrayList<>(list.size());
        boolean passedOnce = false;

        for (int i = list.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (list.get(j).equals(list.get(i))) {
                    if (!passedOnce) {
                        passedOnce = true;
                        continue;
                    }
                    list.remove(j);
                }
            }
            passedOnce = false;
        }
    }

    public int sumFirst(int k){
        int sum=0;
        for (int i=0; i<k; i++){
            sum+=list.get(i);
        }
        return sum;
    }

    public int sumLast(int k){
        int sum=0;
        for (int i=list.size()-1; i>=list.size()-k; i--){
            sum+=list.get(i);
        }
        return sum;
    }

    public void shiftRight(int idx, int k){
        int shift = k % list.size();

        ArrayList<Integer> sublist = (ArrayList<Integer>) list.subList(idx, idx-1);

        Collections.rotate(sublist,shift);
    }

    public void shiftLeft(int idx, int k){
        int shift = k % list.size();

        ArrayList<Integer> sublist = (ArrayList<Integer>) list.subList(idx, idx+1);

        Collections.rotate(sublist,-shift);
    }

    public IntegerList addValue (int value){
        IntegerList newList = new IntegerList();
        for (Integer integer : list){
            newList.add(integer + value, list.size());
        }
        return newList;
    }

}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}