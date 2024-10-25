/*
 * Да се имплементира класа Canvas на која ќе чуваат различни форми. За секоја форма се чува:

    id:String
    color:Color (enum дадена)

Притоа сите форми треба да имплментираат два интерфејси:

    Scalable - дефиниран со еден метод void scale(float scaleFactor) за соодветно зголемување/намалување на формата за дадениот фактор
    Stackable - дефиниран со еден метод float weight() кој враќа тежината на формата (се пресметува како плоштина на соодветната форма)

Во класата Canvas да се имплементираат следните методи:

    void add(String id, Color color, float radius) за додавање круг
    void add(String id, Color color, float width, float height) за додавање правоаголник

При додавањето на нова форма, во листата со форми таа треба да се смести на соодветното место според нејзината тежина. Елементите постојано се подредени според тежината во опаѓачки редослед.

    void scale(String id, float scaleFactor) - метод кој ја скалира формата со даденото id за соодветниот scaleFactor. Притоа ако има потреба, треба да се изврши преместување на соодветните форми, за да се задржи подреденоста на елементите.

Не смее да се користи сортирање на листата.

    toString() - враќа стринг составен од сите фигури во нов ред. За секоја фигура се додава:

    C: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е круг
    R: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е правоаголник

 */

 import java.util.*;

enum Color {
    RED, GREEN, BLUE
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGINAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }
        }
    }
}

class Shape implements Scalable, Stackable {
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public float weight() {
        return width * height;
    }

    public void scale(float scaleFactor) {
        width = width * scaleFactor;
        height = height * scaleFactor;
    }

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("R: %-5s %10s %10.2f", id, color, weight());
    }
}

class Circle extends Shape {
    float radius;

    public void scale(float scaleFactor) {
        radius = radius * scaleFactor;
    }

    public float weight() {
        return radius * radius * 3.14f;
    }

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("C: %-5s %1s %10.2f", id, color, weight());
    }
}

interface Scalable {
    default void scale(float scaleFactor) {

    }
}

interface Stackable {
    default float weight() {
        return -1.0f;
    }
}

class Canvas {
    List<Shape> shapeList;

    Canvas() {
        shapeList = new ArrayList<>();
    }

    void add(String id, Color color, float radius) {
        Circle circle = new Circle(id, color, radius);
        insertSorted(circle);
    }

    void add(String id, Color color, float width, float height) {
        Rectangle rectangle = new Rectangle(id, color, width, height);
        insertSorted(rectangle);
    }

    private void insertSorted(Shape shape) {
        int index = Collections.binarySearch(shapeList, shape, Comparator.comparingDouble(Shape::weight).reversed());

        if (index < 0) {
            index = -(index + 1);
        }

        shapeList.add(index, shape);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shape shape : shapeList) {
            sb.append(shape.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void scale(String id, float scaleFactor) {
        for (Shape shape : shapeList) {
            if (shape.getId().equals(id)) {
                if (shape instanceof Circle) {
                    ((Circle) shape).radius *= scaleFactor;
                } else if (shape instanceof Rectangle) {
                    ((Rectangle) shape).width *= scaleFactor;
                    ((Rectangle) shape).height *= scaleFactor;
                }
            }
        }
    }
}
