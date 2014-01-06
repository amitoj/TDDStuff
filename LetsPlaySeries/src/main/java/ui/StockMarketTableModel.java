package ui;

import domain.*;
import util.UnreachableCodeException;

import javax.swing.table.AbstractTableModel;

public class StockMarketTableModel extends AbstractTableModel {

    String titles[] = {"Year", "Starting Balance", "Cost Basis", "Sales", "Growth", "Ending Balance"};

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
        StockMarketYear currentYear = market.getYearOffset(rowIndex);
        switch (columnIndex) {
            case 0: return currentYear.year();
            case 1: return currentYear.startingBalance();
            case 2: return currentYear.startingCostBasis();
            case 3: return currentYear.totalSold();
            case 4: return currentYear.growth();
            case 5: return currentYear.endingBalance();
            default: throw new UnreachableCodeException();
        }
    }
}
