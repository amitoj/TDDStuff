package tdd;

public class TaxRate {

    private double rate;

    public TaxRate(double rateAsPercentage) {
        this.rate = rateAsPercentage / 100.0;
    }

    public Dollars simpleTaxFor(Dollars amount) {
        return new Dollars((int)(rate * amount.toInt()));
    }

    public Dollars compoundTaxFor(Dollars amount) {
        return new Dollars((int)((amount.toInt() / (1 - rate)) - amount.toInt()));
    }

    @Override
    public String toString() {
        return (rate * 100) + "%";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(rate);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRate taxRate = (TaxRate) o;

        if (Double.compare(taxRate.rate, rate) != 0) return false;

        return true;
    }

}
