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
package org.codehaus.guantanamo.clover;

import org.codehaus.guantanamo.SourceFinder;
import org.codehaus.guantanamo.SourceVisitor;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CloverXmlParserTest extends MockObjectTestCase {
    public void testShouldIdentifyUntestedLinesAsPoliticallyIncorrect() throws IOException, XmlPullParserException {
        Reader cloverXml = new FileReader(new File("target/testdata/clover-reports/clover.xml"));
        SourceFinder finder = new CloverXmlParser(cloverXml);
        Mock sourceVisitor = mock(SourceVisitor.class);
        sourceVisitor.expects(once()).method("visitSource").with(eq(new File("src/testdata/org/codehaus/guantanamo/testdata/PoorlyTested.java").toURL())).isVoid();
        sourceVisitor.expects(once()).method("visitSource").with(eq(new File("src/testdata/org/codehaus/guantanamo/testdata/ProperlyTested.java").toURL())).isVoid();

        finder.accept((SourceVisitor) sourceVisitor.proxy());
    }
}
