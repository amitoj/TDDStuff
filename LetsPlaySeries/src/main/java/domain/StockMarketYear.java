package domain;

public class StockMarketYear {

    private Year year;
    private Dollars startingBalance;
    private InterestRate interestRate;
    private Dollars totalWithdrawals;
    private Dollars startingPrincipal;
    private TaxRate capitalGainsTaxRate;

    public StockMarketYear(Year year, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.year = year;
        this.startingBalance = startingBalance;
        this.interestRate = interestRate;
        this.startingPrincipal = startingPrincipal;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.totalWithdrawals = new Dollars(0);
    }

    public Dollars startingBalance() {
        return startingBalance;
    }

    public Dollars startingPrincipal() {
        return startingPrincipal;
    }

    public InterestRate interestRate() {
        return interestRate;
    }

    public TaxRate capitalGainsTaxRate() {
        return capitalGainsTaxRate;
    }

    public void withdraw(Dollars amount) {
        this.totalWithdrawals = totalWithdrawals.plus(amount);
    }

    private Dollars capitalGainsWithdrawn() {
        Dollars capitalGains = startingBalance().minus(startingPrincipal);
        return capitalGains.maxOfTwoValues(totalWithdrawals);
    }

    public Dollars capitalGainsTaxIncurred() {
        return capitalGainsTaxRate.compoundTaxFor(capitalGainsWithdrawn());
    }

    public Dollars totalWithdrawn() {
        return totalWithdrawals.plus(capitalGainsTaxIncurred());
    }

    public Dollars interestEarned() {
        return interestRate.interestOn(startingBalance.minus(totalWithdrawn()));
    }

    public Dollars endingBalance() {
        return startingBalance
                .minus(totalWithdrawn())
                .plus(interestEarned());
    }

    public Dollars endingPrincipal() {
        return startingPrincipal.subtractToZero(totalWithdrawals);
    }

    public StockMarketYear nextYear() {
        return new StockMarketYear(year.nextYear(), this.endingBalance(), this.endingPrincipal(), this.interestRate(), this.capitalGainsTaxRate() );
    }

    public Dollars appreciation() {
        return interestRate.interestOn(startingBalance.minus(totalWithdrawn()));
    }

    public Year year() {
        return year;
    }
}