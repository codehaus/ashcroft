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
package org.codehaus.guantanamo.jcoverage;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.generama.astunit.ASTTestCase;
import org.xmlpull.v1.XmlPullParserException;
import org.codehaus.guantanamo.Guantanamo;
import org.codehaus.guantanamo.Monitor;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.thoughtworks.proxy.toys.nullobject.Null;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JCoverageAcceptanceTest extends ASTTestCase {
    public void testShouldRemoveLinesAndLeaveSourceInComplingState() throws IOException, XmlPullParserException, TokenStreamException, RecognitionException {
        File coverageXml = new File("target/jcoverage/testdata/coverage-report/coverage.xml");
        Guantanamo.runWithJCoverage(coverageXml, new File("target/jcoverage/testdata/guantanamo"), (Monitor) Null.object(Monitor.class));
        final URL expected = new File("src/expected/jcoverage/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL();
        final URL guantanamoed = new File("target/jcoverage/testdata/guantanamo/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL();
        assertAstEquals(expected, guantanamoed);
    }
}
