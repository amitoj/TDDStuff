package tdd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockMarketYearTest {

    private static final InterestRate INTEREST_RATE = new InterestRate(10);
    private static final Dollars STARTING_BALANCE = new Dollars(10000);
    private static final Dollars STARTING_PRINCIPAL = new Dollars(3000);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);


    @Test
    public void startingValues() throws Exception {
        StockMarketYear year = newYear();
        assertEquals("", STARTING_BALANCE, year.startingBalance());
        assertEquals("", STARTING_PRINCIPAL, year.startingPrincipal());
        assertEquals("", INTEREST_RATE, year.interestRate());
        assertEquals("", CAPITAL_GAINS_TAX_RATE, year.capitalGainsTaxRate());
        assertEquals("", 0, year.totalWithdrawn());
    }

    @Test
    public void capitalGainsTax() throws Exception {
        StockMarketYear year = newYear();
        year.withdraw(4000);
        assertEquals("", 333, year.capitalGainsTaxIncurred());
        assertEquals("", 4333, year.totalWithdrawn());
    }

    @Test
    public void interestEarned() throws Exception {
        StockMarketYear year = newYear();
        assertEquals("basic interest earned", 1000, year.interestEarned());
        year.withdraw(2000);
        assertEquals("withdrawals don't earn interst", 800, year.interestEarned());
        year.withdraw(2000);
        assertEquals("capital gains tax withdrawals don't earn interest", 566, year.interestEarned());
    }

    @Test
    public void endingPrincipal() {
        StockMarketYear year = newYear();
        year.withdraw(1000);
        assertEquals("ending principal considers withdrawals", 2000, year.endingPrincipal());
        year.withdraw(500);
        assertEquals("ending principal considers total multiple withdrawals", 1500, year.endingPrincipal());
        year.withdraw(3000);
        assertEquals("ending principal never goes below zero", 0, year.endingPrincipal());
    }

    @Test
    public void endingBalance() {
        StockMarketYear year = newYear();
        assertEquals("ending balance includes interest", 11000, year.endingBalance());
        year.withdraw(1000);
        assertEquals("ending balance includes withdrawals", 9900, year.endingBalance());
        year.withdraw(3000);
        assertEquals("ending balance includes capital gains tax withdrawals", 6233, year.endingBalance());
    }

    @Test
    public void nextYearStartingValueMatchesThisYearEndingValues() {
        StockMarketYear thisYear = newYear();
        StockMarketYear nextYear = thisYear.nextYear();
        assertEquals("starting balance", thisYear.endingBalance(), nextYear.startingBalance().amount());
        assertEquals("starting principal", thisYear.endingPrincipal(), nextYear.startingPrincipal().amount());
        assertEquals("interest", thisYear.interestRate(), nextYear.interestRate());
        assertEquals("capital gains tax rate", thisYear.capitalGainsTaxRate(), nextYear.capitalGainsTaxRate());
    }

    private StockMarketYear newYear() {
        return new StockMarketYear(STARTING_BALANCE, STARTING_PRINCIPAL, INTEREST_RATE, CAPITAL_GAINS_TAX_RATE);
    }
}