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
package org.codehaus.guantanamo.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * A reader which preserves EOL when reading lines.
 *
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class LineReader extends FilterReader {
    private final StringBuffer sb = new StringBuffer();
    private char lastChar = (char) -1;
    private boolean backlog = false;

    public LineReader(Reader reader) {
        super(reader);
    }

    public LineReader(URL url) throws IOException {
        this(new InputStreamReader(url.openStream()));
    }

    /**
     * Reads a line, including the line separator at the end, i.e either
     * \n, \r or \r\n. If the last line doesn't have a line ending, it is returned as is.
     *
     * @return a line with line separator, or null if EOF.
     * @throws IOException
     */
    public String readLine() throws IOException {
        while (true) {
            char c = (char) in.read();
            if (c == (char) -1) {
                if (sb.length() > 0) {
                    final String result = sb.toString();
                    sb.delete(0, result.length());
                    return result;
                } else {
                    return null;
                }
            }

            if (c == '\n') {
                if (backlog) {
                    sb.append(lastChar);
                    backlog = false;
                }
                sb.append(c);
                final String result = sb.toString();
                sb.delete(0, result.length());
                lastChar = c;
                return result;
            } else if (lastChar == '\r') {
                if (backlog) {
                    sb.append(lastChar);
                }
                backlog = true;
                lastChar = c;
                final String result = sb.toString();
                sb.delete(0, result.length());
                return result;
            } else {
                if (backlog) {
                    sb.append(lastChar);
                    backlog = false;
                }
                sb.append(c);
                lastChar = c;
            }
        }
    }
}
