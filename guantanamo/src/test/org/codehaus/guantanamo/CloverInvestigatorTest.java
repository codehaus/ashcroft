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

import org.jmock.MockObjectTestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CloverInvestigatorTest extends MockObjectTestCase {
    public void testShouldIdentifyUntestedLinesAsPoliticallyIncorrect() throws IOException {
        InputStream qdoxJavaClassCoverage = getClass().getResourceAsStream("JavaClass.html");
        CoverageInvestigator investigator = new CloverInvestigator(new BufferedReader(new InputStreamReader(qdoxJavaClassCoverage)));
        assertTrue(investigator.isCovered("     public JavaClass() {\n", 37));
        assertFalse(investigator.isCovered("    public JavaClass(String name) {\n", 40));
    }
}
