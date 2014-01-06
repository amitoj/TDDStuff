package domain;

public class StockMarketYear {

    private Year year;
    private Dollars startingBalance;
    private GrowthRate growthRate;
    private Dollars totalSellOrders;
    private Dollars costBasis;
    private TaxRate capitalGainsTaxRate;

    public StockMarketYear(Year year, Dollars startingBalance, Dollars costBasis, GrowthRate growthRate, TaxRate capitalGainsTaxRate) {
        this.year = year;
        this.startingBalance = startingBalance;
        this.growthRate = growthRate;
        this.costBasis = costBasis;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.totalSellOrders = new Dollars(0);
    }

    public Dollars startingBalance() {
        return startingBalance;
    }

    public Dollars startingCostBasis() {
        return costBasis;
    }

    private Dollars startingCapitalGains() {
        return startingBalance().minus(costBasis);
    }

    public GrowthRate growthRate() {
        return growthRate;
    }

    public TaxRate capitalGainsTaxRate() {
        return capitalGainsTaxRate;
    }

    public void sell(Dollars amount) {
        this.totalSellOrders = totalSellOrders.plus(amount);
    }

    private Dollars capitalGainsWithdrawn() {
        return Dollars.min(startingCapitalGains(), totalSellOrders());
    }

    public Dollars capitalGainsTaxIncurred() {
        return capitalGainsTaxRate.compoundTaxFor(capitalGainsWithdrawn());
    }

    public Dollars totalSellOrders() {
        return totalSellOrders;
    }

    public Dollars totalSold() {
        return totalSellOrders.plus(capitalGainsTaxIncurred());
    }

    public Dollars growth() {
        return growthRate.growthFor(startingBalance.minus(totalSold()));
    }

    public Dollars endingBalance() {
        return startingBalance
                .minus(totalSold())
                .plus(growth());
    }

    public Dollars endingCostBasis() {
        Dollars purchasesSold = totalSold().subtractToZero(startingCapitalGains());
        return startingCostBasis().minus(purchasesSold);
    }

    public StockMarketYear nextYear() {
        return new StockMarketYear(year.nextYear(), this.endingBalance(), this.endingCostBasis(), this.growthRate(), this.capitalGainsTaxRate());
    }

    public Year year() {
        return year;
    }
}