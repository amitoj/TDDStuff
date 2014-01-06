package ui;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application() {
        this.setSize(900, 400);
        this.setLocation(400, 300);

        Container content = this.getContentPane();
        content.add(getTableView());
    }

    private JScrollPane getTableView() {
        StockMarketTableModel model = new StockMarketTableModel(stockMarket());
        JTable tableView = new JTable(model);
        return new JScrollPane(tableView);
    }

    public StockMarket stockMarket() {
        Year startingYear = new Year(2010);
        Year endingYear = new Year(2050);
        Dollars startingBalance = new Dollars(10000);
        Dollars endingBalance = new Dollars(7000);
        InterestRate interestRate = new InterestRate(10);
        TaxRate capitalGainsTaxRate = new TaxRate(25);

        StockMarket market = new StockMarket(startingYear, endingYear, startingBalance, endingBalance, interestRate, capitalGainsTaxRate, new Dollars(10));
        return market;
    }


    public static void main(String[] args) {
        new Application().setVisible(true);
    }
}
