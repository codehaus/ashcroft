package com.thoughtworks.ashcroft.example;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StockBuyerTest extends TestCase {
    public void testShouldBuyStocksIfTheyAreCheaperThanEighty() throws IOException {
        StockBuyer stockBuyer = new StockBuyer();
        assertTrue(stockBuyer.shouldBuy("MSFT"));
    }

    public void testShouldNotBuyStocksIfTheyAreMoreExpensiveThanEighty() throws IOException {
        StockBuyer stockBuyer = new StockBuyer();
        assertFalse(stockBuyer.shouldBuy("ORA"));
    }
}
