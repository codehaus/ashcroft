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

import junit.framework.TestCase;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.PrintWriter;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class AcceptanceTest extends TestCase {
    public void testShould() throws IOException {
        InputStream qdoxJavaClassCoverage = getClass().getResourceAsStream("JavaClass.html");
        InputStream qdoxJavaClass = getClass().getResourceAsStream("JavaClass.javax");
        Guantanamo guantanamo = new DefaultGuantanamo(new CoverageRegime(new CloverInvestigator(new InputStreamReader(qdoxJavaClassCoverage))));
        Writer out = new PrintWriter(System.out);
        guantanamo.scrutinise(new InputStreamReader(qdoxJavaClass), out);
        out.flush();
    }
}
