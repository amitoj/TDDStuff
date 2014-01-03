package tdd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InterestRateTest {

    @Test
    public void nothing() {
        InterestRate rate = new InterestRate(0);
        assertEquals(0, rate.interestOn(1000));
    }

    @Test
    public void interest() {
        InterestRate rate = new InterestRate(10);
        assertEquals(100, rate.interestOn(1000));
    }

    @Test
    public void valueObject() {
        InterestRate rate1a = new InterestRate(10);
        InterestRate rate1b = new InterestRate(10);
        InterestRate rate2 = new InterestRate(20);

        assertEquals("10.0%", rate1a.toString());
        assertTrue("same rates should be equal", rate1a.equals(rate1b));
        assertFalse("different rates should not be equal", rate1a.equals(rate2));
        assertTrue("same rates have same hash code", rate1a.hashCode() == rate1b.hashCode());
    }
}
