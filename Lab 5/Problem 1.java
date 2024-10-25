/*
 * Да се напише класа ResizableArray која ќе претставува поле (низа) со променлива должина. Класата може да чува елементи од било кој тип (треба да биде генеричка со еден параметар T) и треба да ги има дефинирано следните методи:

    ResizableArray() - креира ново празно поле
    addElement(T element) - додава нов елемент во полето (доколку нема доволно место го зголемува капацитетот на полето).
    removeElement(T element):boolean - aко постои таков елемент истиот го брише и враќа true, во спротивно враќа false, доколку има повеќе инстанци од дадениот елемент се брише само една од нив (ако има многу празно место во полето го намалува неговиот капацитет)
    contains(T element):boolean - враќа true доколку во полето постои дадениот елемент
    toArray():Object[] - ги враќа сите елементи во полето како обична низа
    isEmpty() - враќа true доколку во полето нема ниеден елемент
    count():int - го браќа бројот на елементи во полето
    elementAt(int idx):T - го враќа елементот на соодветната позиција, доколку нема таков фрла исклучок ArrayIndexOutOfBoundsException (елементите во полето се наоѓаат на позиции [0, count()])

Забелешка: за чување на елементите мора да се користи обична низа Т[] elements, не смее да се користи ArrayList<T> и истата мора да биде декларирана како private.

Дополнително, класата ResizableArray треба да има еден статички метод:

<T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src)

Овој метод треба да изврши копирање на сите елементи од src во dest (src останува непроменета, dest ги содржи сите елементи кои ги имал од порано и дополнително сите елементи кои ги има во src).

Следно треба да се напише класа IntegerArray која наследува од класата ResizableArray IntegerArray extends ResizableArray<Integer> и служи за чување на цели броеви. Оваа класа ги нуди следниве методи:

    sum():double - ја враќа сумата на сите елементи во полето
    mean():double - го дава просекот на сите елементи во полето
    countNonZero():int - го дава бројот на елементи во полето кои имаат вредност различна од нула
    distinct():IntegerArray - враќа нов објект кој во себе ги содржи истите елементи кои ги содржи this, но нема дупликат елементи
    increment(int offset):IntegerArray - враќа нов објект кој во себе ги содржи сите елемeнти кои ги содржи this, но на нив додавајќи offset

 */

 import java.util.Scanner;
import java.util.LinkedList;

public class ResizableArrayTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int test = jin.nextInt();
		if ( test == 0 ) { //test ResizableArray on ints
			ResizableArray<Integer> a = new ResizableArray<Integer>();
			System.out.println(a.count());
			int first = jin.nextInt();
			a.addElement(first);
			System.out.println(a.count());
			int last = first;
			while ( jin.hasNextInt() ) {
				last = jin.nextInt();
				a.addElement(last);
			}
			System.out.println(a.count());
			System.out.println(a.contains(first));
			System.out.println(a.contains(last));
			System.out.println(a.removeElement(first));
			System.out.println(a.contains(first));
			System.out.println(a.count());
		}
		if ( test == 1 ) { //test ResizableArray on strings
			ResizableArray<String> a = new ResizableArray<String>();
			System.out.println(a.count());
			String first = jin.next();
			a.addElement(first);
			System.out.println(a.count());
			String last = first;
			for ( int i = 0 ; i < 4 ; ++i ) {
				last = jin.next();
				a.addElement(last);
			}
			System.out.println(a.count());
			System.out.println(a.contains(first));
			System.out.println(a.contains(last));
			System.out.println(a.removeElement(first));
			System.out.println(a.contains(first));
			System.out.println(a.count());
			ResizableArray<String> b = new ResizableArray<String>();
			ResizableArray.copyAll(b, a);
			System.out.println(b.count());
			System.out.println(a.count());
			System.out.println(a.contains(first));
			System.out.println(a.contains(last));
			System.out.println(b.contains(first));
			System.out.println(b.contains(last));
			ResizableArray.copyAll(b, a);
			System.out.println(b.count());
			System.out.println(a.count());
			System.out.println(a.contains(first));
			System.out.println(a.contains(last));
			System.out.println(b.contains(first));
			System.out.println(b.contains(last));
			System.out.println(b.removeElement(first));
			System.out.println(b.contains(first));
			System.out.println(b.removeElement(first));
			System.out.println(b.contains(first));

			System.out.println(a.removeElement(first));
			ResizableArray.copyAll(b, a);
			System.out.println(b.count());
			System.out.println(a.count());
			System.out.println(a.contains(first));
			System.out.println(a.contains(last));
			System.out.println(b.contains(first));
			System.out.println(b.contains(last));
		}
		if ( test == 2 ) { //test IntegerArray
			IntegerArray a = new IntegerArray();
			System.out.println(a.isEmpty());
			while ( jin.hasNextInt() ) {
				a.addElement(jin.nextInt());
			}
			jin.next();
			System.out.println(a.sum());
			System.out.println(a.mean());
			System.out.println(a.countNonZero());
			System.out.println(a.count());
			IntegerArray b = a.distinct();
			System.out.println(b.sum());
			IntegerArray c = a.increment(5);
			System.out.println(c.sum());
			if ( a.sum() > 100 )
				ResizableArray.copyAll(a, a);
			else
				ResizableArray.copyAll(a, b);
			System.out.println(a.sum());
			System.out.println(a.removeElement(jin.nextInt()));
			System.out.println(a.sum());
			System.out.println(a.removeElement(jin.nextInt()));
			System.out.println(a.sum());
			System.out.println(a.removeElement(jin.nextInt()));
			System.out.println(a.sum());
			System.out.println(a.contains(jin.nextInt()));
			System.out.println(a.contains(jin.nextInt()));
		}
		if ( test == 3 ) { //test insanely large arrays
			LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
			for ( int w = 0 ; w < 500 ; ++w ) {
				ResizableArray<Integer> a = new ResizableArray<Integer>();
				int k =  2000;
				int t =  1000;
				for ( int i = 0 ; i < k ; ++i ) {
					a.addElement(i);
				}
				
				a.removeElement(0);
				for ( int i = 0 ; i < t ; ++i ) {
					a.removeElement(k-i-1);
				}
				resizable_arrays.add(a);
			}
			System.out.println("You implementation finished in less then 3 seconds, well done!");
		}
	}
	
}
