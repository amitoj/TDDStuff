package domain;

import util.Require;

public class TaxRate {

    private double rateAsPercentage;

    public TaxRate(double rateAsPercentage) {
        Require.that(rateAsPercentage > 0, "tax rate must be positive (and not zero); was " + rateAsPercentage);
        this.rateAsPercentage = rateAsPercentage;
    }

    public Dollars simpleTaxFor(Dollars amount) {
        return amount.percentage(rateAsPercentage);
    }

    public Dollars compoundTaxFor(Dollars amount) {
        double compoundRate = (100.0 / (100.0 - rateAsPercentage)) - 1;
        return amount.percentage(compoundRate * 100.0);
    }

    @Override
    public String toString() {
        return rateAsPercentage + "%";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(rateAsPercentage);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRate taxRate = (TaxRate) o;

        if (Double.compare(taxRate.rateAsPercentage, rateAsPercentage) != 0) return false;

        return true;
    }

}
