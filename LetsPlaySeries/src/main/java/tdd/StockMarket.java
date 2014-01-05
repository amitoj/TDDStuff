package tdd;

public class StockMarket {
    private int startingYear;
    private int endingYear;

    private StockMarketYear[] years;

    public StockMarket(int startingYear, int endingYear, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.startingYear = startingYear;
        this.endingYear = endingYear;

        populateYears(startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
    }

    private void populateYears(Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        years = new StockMarketYear[numberOfYears()];
        years[0] = new StockMarketYear(startingYear, startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
        for (int i = 1; i < numberOfYears(); i++) {
            years[i] = years[i - 1].nextYear();
        }
    }

    public StockMarketYear getYear(int offset) {
        return years[offset];
    }

    public int numberOfYears() {
        return endingYear - startingYear + 1;
    }

    public int startingYear() {
        return startingYear;
    }
}
