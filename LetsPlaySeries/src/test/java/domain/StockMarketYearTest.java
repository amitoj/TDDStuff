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
    public void totalSold() {
        StockMarketYear year = newYear();
        assertEquals("nothing sold", new Dollars(0), year.totalSold());

        year.sell(new Dollars(3000)); //TODO: rename to 'sell'
        assertEquals("one sell transaction", new Dollars(3000), year.totalSold());

        year.sell(new Dollars(750));
        year.sell(new Dollars(1350));
        assertEquals("multiple sales", new Dollars(5100), year.totalSold());
    }

    @Test
    public void capitalGainsTax() throws Exception {
        StockMarketYear marketYear = newYear();

        marketYear.sell(new Dollars(4000));
        assertEquals("capital gains tax includes tax on withdrawals to cover capital gains", new Dollars(1333), marketYear.capitalGainsTaxIncurred());
        assertEquals("total withdrawn includes capital gains tax", new Dollars(5333), marketYear.totalWithdrawn());
    }

    @Test
    public void treatAllWithdrawalsAsSubjectToCapitalGainsTaxUntilAllCapitalGainsHaveBeenSold() throws Exception {
        StockMarketYear marketYear = newYear();

        Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);

        marketYear.sell(new Dollars(500));
        assertEquals("pay tax on all withdrawals until all capital gains withdrawn", new Dollars(167), marketYear.capitalGainsTaxIncurred());

        marketYear.sell(capitalGains);
        assertEquals("to match spreadsheet(JS), pay compounding tax on capital gains even when compunded amount is not capital gains",
                new Dollars(2333), marketYear.capitalGainsTaxIncurred());

        marketYear.sell(new Dollars(1000));
        assertEquals("pay no more tax once all capital gains withdrawn", new Dollars(2333), marketYear.capitalGainsTaxIncurred());
    }

    @Test
    public void interestEarned() throws Exception {
        StockMarketYear marketYear = newYear();
        assertEquals("basic interest earned", new Dollars(1000), marketYear.appreciation());

        marketYear.sell(new Dollars(2000));
        assertEquals("withdrawals don't earn interst (which pays capital gains tax)", new Dollars(733), marketYear.appreciation());
    }

    @Test
    public void endingPrincipal() {
        StockMarketYear year = newYear();

        year.sell(new Dollars(500));
        assertEquals("withdrawals less than capital gains do not reduce principal", STARTING_PRINCIPAL, year.endingPrincipal());

        year.sell(new Dollars(6500));

        Dollars totalWithdrawn = new Dollars(9333);
        assertEquals("total withdrawn on withdrawals of 6500 including capital gain tax is 6500/0.75 = 9333", totalWithdrawn, year.totalWithdrawn());

        Dollars capitalGains = new Dollars(7000);

        //  9333 - Capital Gains (7000) = 2333 (Principal is reduced by 2333)
        //  3000 - 2333 = 667 is what's left
        Dollars principalReducedBy = totalWithdrawn.minus(capitalGains);
        Dollars expectedPrincipal = STARTING_PRINCIPAL.minus(principalReducedBy);
        assertEquals("principal should be reduced by difference between total withdrawals and capital gains", expectedPrincipal, year.endingPrincipal());

        year.sell(new Dollars(1000));
        assertEquals("principal goes negative when we're overdrawn", new Dollars(-333), year.endingPrincipal());
    }

    @Test
    public void endingBalance() {
        StockMarketYear marketYear = newYear();
        assertEquals("ending balance includes interest", new Dollars(11000), marketYear.endingBalance());

        marketYear.sell(new Dollars(1000));
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