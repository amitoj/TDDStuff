package app;

import tdd.Dollars;
import tdd.InterestRate;
import tdd.TaxRate;

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
        StockMarketTableModel model = new StockMarketTableModel(2010, 2050, new Dollars(10000), new Dollars(7000), new InterestRate(10), new TaxRate(25));

        JTable tableView = new JTable(model);
        return new JScrollPane(tableView);
    }

    public static void main(String[] args) {
        new Application().setVisible(true);
    }
}
