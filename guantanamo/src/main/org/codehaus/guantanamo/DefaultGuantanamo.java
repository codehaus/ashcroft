/*****************************************************************************
 * Copyright (C) Guantanamo Organization. All rights reserved.               *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Aslak Hellesoy                                           *
 * Idea by Chris Stevenson                                                   *
 *****************************************************************************/
package org.codehaus.guantanamo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class DefaultGuantanamo implements Guantanamo {
    private final Regime regime;

    public DefaultGuantanamo(Regime regime) {
        this.regime = regime;
    }

    public void scrutinise(Reader original, Writer output) throws IOException {
        BufferedReader originalCode = new BufferedReader(original);

        String line = null;
        int lineNumber = 1;
        while((line = readLineWithLineSeparator(originalCode)) != null) {
            String politicallyCorrectLine = regime.regimify(line, lineNumber);
            output.write(politicallyCorrectLine);
            lineNumber++;
        }
    }

    private String readLineWithLineSeparator(Reader reader) throws IOException {
        StringBuffer result = new StringBuffer();
        while(true) {
            int c = reader.read();
            result.append((char) c);
            if ((c == '\n')) {
                return result.toString();
            }
            if (c == -1) {
                return null;
            }
        }
    }
}
