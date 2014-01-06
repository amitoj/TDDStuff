package domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockMarketYearTest {

    private static final Year YEAR = new Year(2010);
    private static final InterestRate INTEREST_RATE = new InterestRate(10);
    private static final Dollars STARTING_BALANCE = new Dollars(10000);
    private static final Dollars STARTING_PRINCIPAL = new Dollars(3000);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);

    @Test
    public void startingValues() throws Exception {
        StockMarketYear year = newYear();

        assertEquals("year", YEAR, year.year());
        assertEquals("", STARTING_BALANCE, year.startingBalance());
        assertEquals("", STARTING_PRINCIPAL, year.startingPrincipal());
        assertEquals("", INTEREST_RATE, year.interestRate());
        assertEquals("", CAPITAL_GAINS_TAX_RATE, year.capitalGainsTaxRate());
        assertEquals("", new Dollars(0), year.totalWithdrawn());
    }

    @Test
    public void capitalGainsTax() throws Exception {
        StockMarketYear marketYear = newYear();

        marketYear.withdraw(new Dollars(4000));
        assertEquals("capital gains tax includes tax on withdrawals to cover capital gains", new Dollars(1333), marketYear.capitalGainsTaxIncurred());
        assertEquals("total withdrawn includes capital gains tax", new Dollars(5333), marketYear.totalWithdrawn());
    }

    @Test
    public void treatAllWithdrawalsAsSubjectToCapitalGainsTaxUntilAllCapitalGainsHaveBeenSold() throws Exception {
        StockMarketYear marketYear = newYear();

        Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);

        marketYear.withdraw(new Dollars(500));
        assertEquals("pay tax on all withdrawals until all capital gains withdrawn", new Dollars(167), marketYear.capitalGainsTaxIncurred());

        marketYear.withdraw(capitalGains);
        assertEquals("pay tax on all withdrawals until all capital gains withdrawn", new Dollars(2333), marketYear.capitalGainsTaxIncurred() );

        marketYear.withdraw(new Dollars(1000));
        assertEquals("pay no more tax once all capital gains withdrawn", new Dollars(2333), marketYear.capitalGainsTaxIncurred() );
    }

    @Test
    public void interestEarned() throws Exception {
        StockMarketYear marketYear = newYear();
        assertEquals("basic interest earned", new Dollars(1000), marketYear.appreciation());

        marketYear.withdraw(new Dollars(2000));
        assertEquals("withdrawals don't earn interst (which pays capital gains tax)", new Dollars(733), marketYear.appreciation());
    }

    @Test
    public void endingPrincipal() {
        StockMarketYear marketYear = newYear();

        marketYear.withdraw(new Dollars(1000));
        assertEquals("ending principal considers withdrawals", new Dollars(2000), marketYear.endingPrincipal());

        marketYear.withdraw(new Dollars(500));
        assertEquals("ending principal considers total multiple withdrawals", new Dollars(1500), marketYear.endingPrincipal());

        marketYear.withdraw(new Dollars(3000));
        assertEquals("ending principal never goes below zero", new Dollars(0), marketYear.endingPrincipal());
    }

    @Test
    public void endingBalance() {
        StockMarketYear marketYear = newYear();
        assertEquals("ending balance includes interest", new Dollars(11000), marketYear.endingBalance());

        marketYear.withdraw(new Dollars(1000));
        assertEquals("ending balance includes withdrawals (which pays capital gains tax)", new Dollars(9533), marketYear.endingBalance());
    }

    @Test
    public void nextYearStartingValueMatchesThisYearEndingValues() {
        StockMarketYear thisYear = newYear();
        StockMarketYear nextYear = thisYear.nextYear();
        assertEquals("year", new Year(2011), nextYear.year());
        assertEquals("starting balance", thisYear.endingBalance(), nextYear.startingBalance());
        assertEquals("starting principal", thisYear.endingPrincipal(), nextYear.startingPrincipal());
        assertEquals("interest", thisYear.interestRate(), nextYear.interestRate());
        assertEquals("capital gains tax rate", thisYear.capitalGainsTaxRate(), nextYear.capitalGainsTaxRate());
    }

    private StockMarketYear newYear() {
        return new StockMarketYear(YEAR, STARTING_BALANCE, STARTING_PRINCIPAL, INTEREST_RATE, CAPITAL_GAINS_TAX_RATE);
    }
}