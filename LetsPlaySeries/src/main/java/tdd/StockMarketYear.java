package tdd;

public class StockMarketYear {

    private Dollars startingBalance;
    private InterestRate interestRate;
    private int totalWithdrawals;
    private Dollars startingPrincipal;
    private TaxRate capitalGainsTaxRate;

    public StockMarketYear(Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.startingBalance = startingBalance;
        this.interestRate = interestRate;
        this.startingPrincipal = startingPrincipal;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.totalWithdrawals = 0;
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

    public void withdraw(int amount) {
        this.totalWithdrawals += amount;
    }

    private int capitalGainsWithdrawn() {
        int result = -1 * (startingPrincipal().amount() - totalWithdrawals);
        return Math.max(0, result);
    }

    public int capitalGainsTaxIncurred() {
        return  capitalGainsTaxRate.compoundTaxFor(capitalGainsWithdrawn());
    }

    public int totalWithdrawn() {
        return totalWithdrawals + capitalGainsTaxIncurred();
    }

    public int interestEarned() {
        return interestRate.interestOn(startingBalance.amount() - totalWithdrawn());
    }

    public int endingBalance() {
        return startingBalance.amount() - totalWithdrawn() + interestEarned();
    }

    public int endingPrincipal() {
        return startingPrincipal.subtractToZero(new Dollars(totalWithdrawals)).amount();
    }

    public StockMarketYear nextYear() {
        return new StockMarketYear(new Dollars(this.endingBalance()), new Dollars(this.endingPrincipal()), this.interestRate(), this.capitalGainsTaxRate() );
    }
}