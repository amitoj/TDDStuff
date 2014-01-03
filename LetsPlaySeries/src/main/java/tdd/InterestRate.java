package tdd;

public class InterestRate {

    private double rate;

    public InterestRate(double rateAsPercentage) {
        rate = rateAsPercentage / 100.0;
    }

    public Dollars interestOn(Dollars amount) {
        return new Dollars((int)(amount.toInt() * rate));
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

        InterestRate rate1 = (InterestRate) o;

        if (Double.compare(rate1.rate, rate) != 0) return false;

        return true;
    }

}
