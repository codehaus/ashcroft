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

import org.codehaus.guantanamo.CountModifier;
import org.codehaus.guantanamo.LineModifier;
import org.codehaus.guantanamo.LineModifierProvider;
import org.codehaus.guantanamo.SourceFinder;
import org.codehaus.guantanamo.SourceRootFinder;
import org.codehaus.guantanamo.SourceVisitor;
import org.codehaus.guantanamo.TrueFalseCountModifier;
import org.codehaus.guantanamo.URLLine;
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
public class CloverXmlParser implements SourceFinder, LineModifierProvider, SourceRootFinder {
    private LineModifier notModifyingLineModifier = new PlainLineModifier();
    private final MXParser parser = new MXParser();
    private String fileName;
    private Map lineModifiers = new HashMap();
    private List sourceURLs = new ArrayList();
    private URL sourceRoot;
    private String packageName;

    public CloverXmlParser(Reader cloverXml) throws IOException {
        try {
            parser.setInput(cloverXml);
            parseDocument();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    private void parseDocument() throws IOException, XmlPullParserException {
        while (true) {
            final int event = parser.nextTag();
            final String tagName = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG:
                    if ("package".equals(parser.getName())) {
                        packageName = parser.getAttributeValue(null, "name");
                    }
                    if ("file".equals(parser.getName())) {
                        fileName = parser.getAttributeValue(null, "name");
                        sourceURLs.add(new File(fileName).toURL());

                        // determine the source root
                        if(sourceRoot == null) {
                            String packagePath = packageName.replace('.', File.separatorChar);
                            int sourceRootPathEnd = fileName.indexOf(packagePath);
                            String sourceRootPath = fileName.substring(0, sourceRootPathEnd);
                            sourceRoot = new File(sourceRootPath).toURL();
                        }
                    }
                    if ("line".equals(tagName)) {
                        int lineNumber = Integer.parseInt(parser.getAttributeValue(null, "num"));
                        String count = parser.getAttributeValue(null, "count");
                        String truecount = parser.getAttributeValue(null, "truecount");
                        String falsecount = parser.getAttributeValue(null, "falsecount");
                        LineModifier lineModifier = null;
                        if (count != null) {
                            lineModifier = new CountModifier(Integer.parseInt(count));
                        } else {
                            lineModifier = new TrueFalseCountModifier(Integer.parseInt(truecount), Integer.parseInt(falsecount));
                        }
                        final URLLine URLLine = new URLLine(new File(fileName).toURL(), lineNumber);
                        lineModifiers.put(URLLine, lineModifier);
                    }
                    continue;
                case XmlPullParser.END_TAG:
                    if ("coverage".equals(tagName)) {
                        return;
                    }
                    continue;
            }
        }
    }

    public void accept(SourceVisitor sourceVisitor) throws IOException {
        for (Iterator iterator = sourceURLs.iterator(); iterator.hasNext();) {
            final URL source = (URL) iterator.next();
            sourceVisitor.visitSource(source);
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
