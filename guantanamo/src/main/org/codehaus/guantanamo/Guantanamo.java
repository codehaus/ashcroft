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

import org.codehaus.guantanamo.clover.CloverXmlParser;
import org.codehaus.guantanamo.io.LineReader;
import org.codehaus.guantanamo.jcoverage.JCoverageXmlParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Stack;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class Guantanamo implements SourceModifier {
    private final LineModifierProvider lineModifierProvider;
    private final Monitor monitor;
    private Stack blocks = new Stack();

    public static void runWithClover(File cloverXml, File destDir, Monitor monitor) throws IOException {
        CloverXmlParser cloverXmlParser = new CloverXmlParser(new FileReader(cloverXml));
        Guantanamo guantanamo = new Guantanamo(cloverXmlParser, monitor);
        cloverXmlParser.accept(new ModifyingSourceVisitor(guantanamo, cloverXmlParser, destDir.toURL()));
    }

    public static void runWithJCoverage(File jcoverage, File destDir, Monitor monitor) throws IOException {
        JCoverageXmlParser JCoverageXmlParser = new JCoverageXmlParser(new FileReader(jcoverage));
        Guantanamo guantanamo = new Guantanamo(JCoverageXmlParser, monitor);
        JCoverageXmlParser.accept(new ModifyingSourceVisitor(guantanamo, JCoverageXmlParser, destDir.toURL()));
    }

    public Guantanamo(LineModifierProvider lineModifierProvider, Monitor monitor) {
        this.lineModifierProvider = lineModifierProvider;
        this.monitor = monitor;
    }

    public void modifySource(URL source, URL destination) throws IOException {
        monitor.source(source);
        monitor.destination(destination);
        LineReader sourceReader = new LineReader(source);
        String line = null;
        int lineNumber = 1;
        File outFile = new File(destination.getFile());
        outFile.getParentFile().mkdirs();
        Writer out = new FileWriter(outFile);
        while ((line = sourceReader.readLine()) != null) {
            monitor.line(lineNumber, line);
            LineModifier lineModifier = lineModifierProvider.getLineModifier(source, lineNumber);

            boolean willBeRemoved = lineModifier.willRemove(line);
            SourceAnalysis sourceAnalysis = analyseLine(line, willBeRemoved);

            boolean forceRemove = false;
            if (sourceAnalysis.isOpenBlock()) {
                monitor.openBlock(lineNumber);
                blocks.push(sourceAnalysis);
            }
            if (sourceAnalysis.isCloseBlock()) {
                monitor.closeBlock(lineNumber);
                SourceAnalysis openBlock = (SourceAnalysis) blocks.pop();
                forceRemove = openBlock.wasRemoved();
            }
            lineModifier.write(line, out, forceRemove);

            lineNumber++;
        }
        out.flush();
    }

    private SourceAnalysis analyseLine(String line, boolean removed) {
        final String trimmedLine = line.trim();
        if (trimmedLine.endsWith("{")) {
            return new OpenBlock(removed);
        } else if (trimmedLine.endsWith("}") && !trimmedLine.startsWith("*")) {
            return new CloseBlock(removed);
        } else {
            return new PlainCode(removed);
        }
    }
}
