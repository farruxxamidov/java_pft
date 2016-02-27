package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Farrukh on 26-Feb-16.
 */
public class PointTests {

  @Test
  public void testDistance() {

    checkDistance(0, 1, 0, 5, 4);
    checkDistance(-1, 0, -1, 6, 6);
    checkDistance(0, -3, 0, 6, 9);

  }

  public void checkDistance(double x1, double y1, double x2, double y2, double result) {
    Point p1 = new Point(x1, y1);
    Point p2 = new Point(x2, y2);
    Assert.assertEquals(p1.distance(p2), result);
  }

}
