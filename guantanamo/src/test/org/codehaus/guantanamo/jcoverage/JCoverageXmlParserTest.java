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

import org.codehaus.guantanamo.LineModifierProvider;
import org.codehaus.guantanamo.SourceFinder;
import org.codehaus.guantanamo.SourceRootFinder;
import org.codehaus.guantanamo.SourceVisitor;
import org.codehaus.guantanamo.GuantanamoException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JCoverageXmlParserTest extends MockObjectTestCase {
    public void testShouldVisitEachSource() throws IOException, XmlPullParserException {
        Reader coverageXml = new FileReader(new File("target/jcoverage/testdata/coverage-report/coverage.xml"));
        SourceFinder finder = new JCoverageXmlParser(coverageXml);
        Mock sourceVisitor = mock(SourceVisitor.class);
        sourceVisitor.expects(once()).method("visitSource").with(eq(new File("src/testdata/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL())).isVoid();
        sourceVisitor.expects(once()).method("visitSource").with(eq(new File("src/testdata/org/codehaus/guantanamo/testdata/ProperlyTested.java").toURL())).isVoid();

        finder.accept((SourceVisitor) sourceVisitor.proxy());
    }

    public void testShouldFindLineModifiers() throws IOException {
        Reader coverageXml = new FileReader(new File("target/jcoverage/testdata/coverage-report/coverage.xml"));
        LineModifierProvider lineModifierProvider = new JCoverageXmlParser(coverageXml);
        final URL poorlyTested = new File("src/testdata/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL();
        assertFalse(lineModifierProvider.getLineModifier(poorlyTested, 7).willRemove(""));
        assertTrue(lineModifierProvider.getLineModifier(poorlyTested, 9).willRemove(""));
    }

    public void testShouldFindSourceRoot() throws IOException {
        Reader coverageXml = new FileReader(new File("target/jcoverage/testdata/coverage-report/coverage.xml"));
        SourceRootFinder sourceRootFinder = new JCoverageXmlParser(coverageXml);
        assertEquals(new File("src/testdata").toURL(), sourceRootFinder.getSourceRootURL());
    }

    public void testShouldThrowGuantanamoExceptionOnBadXml() throws IOException {
        try {
            new JCoverageXmlParser(new FileReader("LICENSE.txt"));
            fail();
        } catch (GuantanamoException expected) {
        }
    }
}
