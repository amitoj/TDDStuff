package domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaxRateTest {

    @Test
    public void nothing() {
        TaxRate taxRate = new TaxRate(0);
        assertEquals(new Dollars(0), taxRate.simpleTaxFor(new Dollars(1000) ));
        assertEquals(new Dollars(0), taxRate.compoundTaxFor(new Dollars(1000) ));
    }

    @Test
    public void compoundTaxIsTheAmountOfTaxThatIsIncurredIfYouAlsoPayTaxOnTheTax() {
        TaxRate taxRate = new TaxRate(25);
        assertEquals(new Dollars(333), taxRate.compoundTaxFor(new Dollars(1000) ));
    }

    @Test
    public void valueObject() {
        TaxRate taxRate1a = new TaxRate(33);
        TaxRate taxRate1b = new TaxRate(33);
        TaxRate taxRate2 = new TaxRate(40);

        assertEquals("33.0%", taxRate1a.toString());
        assertTrue("same values should be equal", taxRate1a.equals(taxRate1b));
        assertFalse("different value should not be equal", taxRate1a.equals(taxRate2));
        assertTrue("same values have same hash code", taxRate1a.hashCode() == taxRate1b.hashCode());
    }

}
