/*****************************************************************************
 * Copyright (C) SourceVisitor Organization. All rights reserved.               *
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
import org.jmock.core.Constraint;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CloverXmlParserTest extends MockObjectTestCase {
    public void testShouldIdentifyUntestedLinesAsPoliticallyIncorrect() throws IOException, XmlPullParserException {
        Reader cloverXml = new FileReader("target/clover.xml");
        SourceFinder finder = new CloverXmlParser(cloverXml);
        Mock sourceVisitor = mock(SourceVisitor.class);
        expectVisitSourceTimes(sourceVisitor, 21);

        finder.accept((SourceVisitor) sourceVisitor.proxy());
    }

    private void expectVisitSourceTimes(Mock sourceVisitor, int numberOfSources) throws MalformedURLException {
        for (int i = 0; i < numberOfSources; i++) {
            sourceVisitor.expects(once()).method("visitSource").with(new Constraint() {
                public boolean eval(Object o) {
                    URL url = (URL) o;
                    return url.getFile().indexOf("src/main/org/codehaus/guantanamo") != -1;
                }

                public StringBuffer describeTo(StringBuffer stringBuffer) {
                    return stringBuffer.append("The URL should have contained src/main/org/codehaus/guantanamo");
                }
            }).isVoid();
        }
    }
}
