package com.thoughtworks.ashcroft.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StockBuyer {
    public boolean shouldBuy(String symbol) throws IOException {
        // system stuff
        Properties properties = new Properties();
        properties.load(new FileInputStream("test_data"));
        String val = properties.getProperty(symbol);
        int value = Integer.parseInt(val);

        // bus logic
        return value < 80;
    }
}
