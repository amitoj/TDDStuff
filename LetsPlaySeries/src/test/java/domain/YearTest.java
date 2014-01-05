package domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YearTest {

    @Test
    public void nextYear() {
        Year thisYear = new Year(2010);
        assertEquals(new Year(2011), thisYear.nextYear());
    }

    @Test
    public void valueObject() {
        Year year1a = new Year(2010);
        Year year1b = new Year(2010);
        Year year2 = new Year(2012);

        assertEquals("2010", year1a.toString());
        assertTrue("years with same values should be equal", year1a.equals(year1b));
        assertFalse("years with different values should not be equal", year1a.equals(year2));
        assertTrue("years with same values should have same hash code", year1a.hashCode() == year1b.hashCode());
    }

}
