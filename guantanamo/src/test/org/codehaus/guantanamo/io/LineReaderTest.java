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

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class LineReaderTest extends TestCase {
    public void testShouldPreserveLineEndingsForEachReadLine() throws IOException {
        LineReader variousLineEndings = new LineReader(new StringReader("" +
                "unix\n" +
                "mac\r" +
                "pc\r\n" +
                "\n" +
                "\n" +
                "\r" +
                "\r" +
                "\r\n" +
                "\r\n" +
                "nolineend"));

        assertEquals("unix\n", variousLineEndings.readLine());
        assertEquals("mac\r", variousLineEndings.readLine());
        assertEquals("pc\r\n", variousLineEndings.readLine());
        assertEquals("\n", variousLineEndings.readLine());
        assertEquals("\n", variousLineEndings.readLine());
        assertEquals("\r", variousLineEndings.readLine());
        assertEquals("\r", variousLineEndings.readLine());
        assertEquals("\r\n", variousLineEndings.readLine());
        assertEquals("\r\n", variousLineEndings.readLine());
        assertEquals("nolineend", variousLineEndings.readLine());
    }
}
