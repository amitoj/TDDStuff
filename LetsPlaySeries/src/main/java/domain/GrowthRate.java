package domain;

import util.Require;

public class GrowthRate {

    private double rateAsPercentage;

    public GrowthRate(double rateAsPercentage) {
        Require.that(rateAsPercentage > 0, "growth rate must be positive (and not zero); was " + rateAsPercentage);
        this.rateAsPercentage = rateAsPercentage;
    }

    public Dollars growthFor(Dollars amount) {
        return amount.percentage(rateAsPercentage);
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

        GrowthRate rate1 = (GrowthRate) o;

        if (Double.compare(rate1.rateAsPercentage, rateAsPercentage) != 0) return false;

        return true;
    }

}
