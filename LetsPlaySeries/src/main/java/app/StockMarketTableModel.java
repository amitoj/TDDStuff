package app;

import tdd.*;

import javax.swing.table.AbstractTableModel;

public class StockMarketTableModel extends AbstractTableModel {

    String titles[] = {"Year", "Starting Balance", "Starting Principal", "Withdrawals", "Appreciation", "Ending Balance"};

    private StockMarket market;

    public StockMarketTableModel(StockMarket market) {
        this.market = market;
    }

    @Override
    public int getRowCount() {
        return market.numberOfYears();
    }

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public String getColumnName(int index) {
        return titles[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StockMarketYear currentYear = market.getYear(rowIndex);
        switch (columnIndex) {
            case 0: return market.startingYear() + rowIndex;
            case 1: return currentYear.startingBalance();
            case 2: return currentYear.startingPrincipal();
            case 3: return currentYear.totalWithdrawn();
            case 4: return currentYear.appreciation();
            case 5: return currentYear.endingBalance();
            default: return null;
        }
    }
}
