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

import org.codehaus.guantanamo.CountModifier;
import org.codehaus.guantanamo.LineModifier;
import org.codehaus.guantanamo.LineModifierProvider;
import org.codehaus.guantanamo.PlainLineModifier;
import org.codehaus.guantanamo.SourceFinder;
import org.codehaus.guantanamo.SourceRootFinder;
import org.codehaus.guantanamo.SourceVisitor;
import org.codehaus.guantanamo.URLLine;
import org.codehaus.guantanamo.GuantanamoException;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JCoverageXmlParser implements SourceFinder, LineModifierProvider, SourceRootFinder {
    private final LineModifier notModifyingLineModifier = new PlainLineModifier();
    private final MXParser parser = new MXParser();

    private final List sourceURLs = new ArrayList();
    private final Map lineModifiers = new HashMap();

    private URL sourceRoot;
    private URL sourceURL;

    public JCoverageXmlParser(Reader coverageXml) throws IOException {
        try {
            parser.setInput(coverageXml);
            parseDocument();
        } catch (XmlPullParserException e) {
            throw new GuantanamoException(e.getMessage());
        }
    }

    private void parseDocument() throws IOException, XmlPullParserException {
        while (true) {
            final int event = parser.nextTag();
            final String tagName = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG:
                    if ("coverage".equals(parser.getName())) {
                        sourceRoot = new File(parser.getAttributeValue(null, "src")).toURL();
                    } else if ("file".equals(parser.getName())) {
                        String fileName = parser.getAttributeValue(null, "name");
                        sourceURL = new File(sourceRoot.getFile(), fileName).toURL();
                        sourceURLs.add(sourceURL);
                    } else if ("line".equals(tagName)) {
                        final String ln = parser.getAttributeValue(null, "number");
                        if (ln != null) {
                            int lineNumber = Integer.parseInt(ln);
                            final int hits = Integer.parseInt(parser.getAttributeValue(null, "hits"));
                            LineModifier lineModifier = new CountModifier(hits);
                            URLLine URLLine = new URLLine(sourceURL, lineNumber);
                            lineModifiers.put(URLLine, lineModifier);
                        }
                    }
                    continue;
                case XmlPullParser.END_TAG:
                    if ("coverage".equals(tagName)) {
                        return;
                    }
            }
        }
    }

    public void accept(SourceVisitor sourceVisitor) throws IOException {
        for (Iterator iterator = sourceURLs.iterator(); iterator.hasNext();) {
            URL sourceURL = (URL) iterator.next();
            sourceVisitor.visitSource(sourceURL);
        }
    }

    public LineModifier getLineModifier(URL source, int lineNumber) {
        URLLine urlLine = new URLLine(source, lineNumber);

        LineModifier lineModifier = (LineModifier) lineModifiers.get(urlLine);
        if (lineModifier == null) {
            return notModifyingLineModifier;
        }
        return lineModifier;
    }

    public URL getSourceRootURL() {
        return sourceRoot;
    }
}
