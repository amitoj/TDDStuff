package app;

import tdd.Dollars;
import tdd.InterestRate;
import tdd.StockMarketYear;
import tdd.TaxRate;

import javax.swing.table.AbstractTableModel;

public class StockMarketTableModel extends AbstractTableModel {

    String titles[] = {"Year", "Starting Balance", "Starting Principal", "Withdrawals", "Appreciation", "Ending Balance"};

    private int startingYear;
    private int endingYear;
    private StockMarketYear[] years;


    public StockMarketTableModel(int startingYear, int endingYear, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.startingYear = startingYear;
        this.endingYear = endingYear;
        populateYears(startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
    }

    private void populateYears(Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        int count = endingYear - startingYear + 1;
        years = new StockMarketYear[count];
        years[0] = new StockMarketYear(startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
        for (int i = 1; i < count; i++) {
            years[i] = years[i - 1].nextYear();
        }
    }

    @Override
    public int getRowCount() {
        return years.length;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int index) {
        return titles[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StockMarketYear currentYear = years[rowIndex];
        switch (columnIndex) {
            case 0: return startingYear + rowIndex;
            case 1: return currentYear.startingBalance();
            case 2: return currentYear.startingPrincipal();
            case 3: return currentYear.totalWithdrawn();
            case 4: return currentYear.appreciation();
            case 5: return currentYear.endingBalance();
            default: return null;
        }
    }
}
