package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {
    Vector a;
    Vector b;
    Vector c;

    @Before
    public void setup() {
        a = new Vector(0,0);
        b = new Vector(1,0);
        c = new Vector(3, 4);
    }

    @Test
    public void distanceSquared() {
        assertEquals(1, a.distanceSquared(b), Constants.EPSILON);
        assertEquals(25, a.distanceSquared(c), Constants.EPSILON);
        assertEquals(0, a.distanceSquared(a), Constants.EPSILON);
    }

    @Test
    public void distance() {
        assertEquals(1, a.distance(b), Constants.EPSILON);
        assertEquals(5, a.distance(c), Constants.EPSILON);
        assertEquals(0, a.distance(a), Constants.EPSILON);
    }

    @Test
    public void manhattanDistance() {
        assertEquals(1, a.manhattanDistance(b), Constants.EPSILON);
        assertEquals(7, a.manhattanDistance(c), Constants.EPSILON);
        assertEquals(0, a.manhattanDistance(a), Constants.EPSILON);
    }

    @Test
    public void lengthSquared() {
        assertEquals(0, a.lengthSquared(), Constants.EPSILON);
        assertEquals(1, b.lengthSquared(), Constants.EPSILON);
        assertEquals(25, c.lengthSquared(), Constants.EPSILON);
    }

    @Test
    public void length() {
        assertEquals(0, a.length(), Constants.EPSILON);
        assertEquals(1, b.length(), Constants.EPSILON);
        assertEquals(5, c.length(), Constants.EPSILON);
    }

    @Test
    public void add() {
        assertEquals(new Vector(0,0), a.add(Vector.ORIGIN));
        assertEquals(new Vector(2,0), b.add(b));
        assertEquals(new Vector(4,4), c.add(b));
        assertEquals(c.add(b), b.add(c));
        assertEquals(c, c.add(Vector.ORIGIN));
    }

    @Test
    public void invertSign() {
        assertEquals(a, a.invertSign());
        assertEquals(new Vector(-1, 0), b.invertSign());
        assertEquals(new Vector(-3, -4), c.invertSign());
        assertEquals(c.invertSign().invertSign(), c);
    }

    @Test
    public void sub() {
        assertEquals(new Vector(0,0), a.sub(Vector.ORIGIN));
        assertEquals(Vector.ORIGIN, b.sub(b));
        assertEquals(new Vector(2,4), c.sub(b));
        assertEquals(c, c.sub(Vector.ORIGIN));
    }

    @Test
    public void scale() {
        assertEquals(new Vector(0,0), a.scale(3));
        assertEquals(Vector.ORIGIN, b.scale(0));
        assertEquals(new Vector(6,8), c.scale(2));
        assertEquals(c.scale(-1), c.invertSign());
    }

    @Test
    public void testEquals() {
        assertEquals(a, new Vector(0));
        assertEquals(Vector.ORIGIN, a);
        assertEquals(b, new Vector(1,0));
        assertEquals(c, new Vector(c));
        assertEquals(Vector.UNDEFINED, Vector.UNDEFINED);
    }

    @Test
    public void testHashCode() {
        assertEquals(a.hashCode(), new Vector(0).hashCode());
        assertEquals(Vector.ORIGIN.hashCode(), a.hashCode());
        assertEquals(b.hashCode(), new Vector(1,0).hashCode());
        assertEquals(c.hashCode(), new Vector(c).hashCode());
    }

    @Test
    public void isValid() {
        assertTrue(Vector.ORIGIN.isValid());
        assertTrue(c.isValid());
        assertFalse(Vector.UNDEFINED.isValid());
        assertFalse(new Vector(Double.NaN, 0).isValid());
        assertFalse(new Vector(Double.POSITIVE_INFINITY, 0).isValid());
        assertFalse(new Vector(Double.NaN, Double.NaN).isValid());
        assertFalse(new Vector(0, Double.NaN).isValid());
    }
}