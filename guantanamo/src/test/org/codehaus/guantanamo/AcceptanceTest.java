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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.generama.astunit.ASTTestCase;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class AcceptanceTest extends ASTTestCase {
    public void testShouldRemoveLinesAndLeaveSourceInComplingState() throws IOException, XmlPullParserException, TokenStreamException, RecognitionException {
        File cloverXml = new File("target/testdata/clover-reports/clover.xml");
        Guantanamo.runWithClover(cloverXml, new File("target/testdata/guantanamoed-src"));
        final URL expected = new File("src/expected/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL();
        final URL guantanamoed = new File("target/testdata/guantanamoed-src/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL();
        assertAstEquals(expected, guantanamoed);
    }
}
