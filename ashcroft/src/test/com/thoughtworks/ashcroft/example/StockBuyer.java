package com.thoughtworks.ashcroft.example;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StockBuyer {
    private StockTicker stockTicker;

    public boolean shouldBuy(String symbol) {
        int price = stockTicker.getPrice(symbol);
        return price < 80;
    }

    public void setStockTicker(StockTicker stockTicker) {
        this.stockTicker = stockTicker;
    }
}
