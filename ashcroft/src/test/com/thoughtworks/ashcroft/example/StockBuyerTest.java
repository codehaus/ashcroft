package com.thoughtworks.ashcroft.example;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * Try to run this test with -Djava.security.manager=com.thoughtworks.ashcroft.runtime.JohnAshcroft
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StockBuyerTest extends MockObjectTestCase {

    public void testShouldBuyStocksIfTheyAreCheaperThan80() {
        StockBuyer stockBuyer = new StockBuyer();

        Mock stockTickerMock = mock(StockTicker.class);
        stockTickerMock.expects(once()).method("getPrice").with(eq("MSFT")).will(returnValue(79));
        StockTicker mockTicker = (StockTicker) stockTickerMock.proxy();

        stockBuyer.setStockTicker(mockTicker);
        boolean decision = stockBuyer.shouldBuy("MSFT");
        assertTrue(decision);
    }
}
