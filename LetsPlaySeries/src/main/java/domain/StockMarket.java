package domain;

public class StockMarket {
    private Year startingYear;
    private Year endingYear;

    private StockMarketYear[] years;

    public StockMarket(Year startingYear, Year endingYear, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
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

    public StockMarketYear getYearOffset(int offset) {
        return years[offset];
    }

    public int numberOfYears() {
        return endingYear.year() - startingYear.year() + 1;
    }

}
