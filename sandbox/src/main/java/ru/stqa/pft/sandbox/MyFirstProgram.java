package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
//    hello("world");
//    hello("user");
//
//    Square s = new Square(5);
//    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());
//
//    Rectangle r = new Rectangle(4, 6);
//    System.out.println("Площадь прямоугольники со сторонами " + r.a +" и " + r.b + " = " + r.area() +'\n');

    Point p1 = new Point(0, 0);
    Point p2 = new Point(1, 1);
    System.out.println("Расстояние между точкой p1(" + p1.x + ", " + p1.y + ")" + " и " + "p2(" + p2.x + ", " + p2.y + ")"
    + " = " + distance(p1,p2) +'\n');


    System.out.println("Расстояние между точкой p1(" + p1.x + ", " + p1.y + ")" + " и " + "p2(" + p2.x + ", " + p2.y + ")"
            + " = " + p1.distance(p2));

  }

//  public static void hello(String somebody) {
//    System.out.println("Hello, " + somebody + "!");
//  }

  public static double distance(Point p1, Point p2) {
    double distance = Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));

    return distance;
  }

}