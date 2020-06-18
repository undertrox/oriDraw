package de.undertrox.oridraw.util.math;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineTest {

    Line a;
    Line b;
    Line c;

    @Before
    public void setup() {
        a = new Line(new Vector(0,0), new Vector(6,8));
        b = new Line(Vector.ORIGIN, Vector.ORIGIN);
        c = new Line(new Vector(3,4), Vector.UNDEFINED);
    }

    @Test
    public void flipPoints() {
        Line d = new Line(a.getStart(), a.getEnd());
        d.flipPoints();

        assertEquals(a.getStart(), d.getEnd());
        assertEquals(a.getEnd(), d.getStart());
    }

    @Test
    public void translate() {
        assertEquals(a, a.translate(Vector.ORIGIN));
        assertEquals(new Line(new Vector(1,1), new Vector(7,9)), a.translate(new Vector(1,1)));
    }

    @Test
    public void testEquals() {
        Line d = new Line(a.getStart(), a.getEnd());
        d.flipPoints();
        assertEquals(a,d);
        assertEquals(new Line(new Vector(0,0), new Vector(6,8)), a);
        assertEquals(b,b);
        assertEquals(c,c);
    }

    @Test
    public void testHashCode() {
        Line d = new Line(a.getStart(), a.getEnd());
        d.flipPoints();
        assertEquals(new Line(new Vector(0,0), new Vector(6,8)).hashCode(), a.hashCode());
        assertEquals(b.hashCode(),b.hashCode());
    }

    @Test
    public void contains() {
        assertTrue(a.contains(a.getStart()));
        assertTrue(a.contains(a.getEnd()));
        assertTrue(a.contains(new Vector(3,4)));
        assertFalse(a.contains(Vector.UNDEFINED));
        assertFalse(c.contains(Vector.ORIGIN));
        assertFalse(b.contains(new Vector(1,1)));
    }

    @Test
    public void toVector() {
        assertEquals(new Vector(0,0), b.toVector());
        assertEquals(new Vector(-6,-8), a.toVector());
        assertEquals(Vector.UNDEFINED, c.toVector());
    }

    @Test
    public void getPointAt() {
        assertEquals(a.getStart(), a.getPointAt(0));
        assertEquals(a.getEnd(), a.getPointAt(1));
        assertEquals(new Vector(3,4), a.getPointAt(0.5));
        assertEquals(Vector.UNDEFINED, c.getPointAt(0));
    }
}