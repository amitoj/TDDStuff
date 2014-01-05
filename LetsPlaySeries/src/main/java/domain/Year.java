package domain;

public class Year {
    private int year;

    public Year(int year) {
        this.year = year;
    }

    public Year nextYear() {
        return new Year(year + 1);
    }

    @Override
    public String toString() {
        return String.valueOf(year);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Year year1 = (Year) o;

        if (year != year1.year) return false;

        return true;
    }

    public int year() {
        return year;
    }
}