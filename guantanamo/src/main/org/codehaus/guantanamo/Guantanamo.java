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
    private Stack blocks = new Stack();

    public static void runWithClover(File cloverXml, File destinationFolder) throws IOException {
        CloverXmlParser cloverXmlParser = new CloverXmlParser(new FileReader(cloverXml));
        Guantanamo guantanamo = new Guantanamo(cloverXmlParser);
        cloverXmlParser.accept(new ModifyingSourceVisitor(guantanamo, cloverXmlParser, destinationFolder.toURL()));
    }

    public Guantanamo(LineModifierProvider lineModifierProvider) {
        this.lineModifierProvider = lineModifierProvider;
    }

    public void modifySource(URL source, URL destination) throws IOException {
        LineReader sourceReader = new LineReader(source);
        String line = null;
        int lineNumber = 1;
        File outFile = new File(destination.getFile());
        outFile.getParentFile().mkdirs();
        Writer out = new FileWriter(outFile);
        while ((line = sourceReader.readLine()) != null) {
            LineModifier lineModifier = lineModifierProvider.getLineModifier(source, lineNumber);

            boolean willBeRemoved = lineModifier.willRemove(line);
            SourceAnalysis sourceAnalysis = analyseLine(line, willBeRemoved);

            boolean forceRemove = false;
            if (sourceAnalysis.isOpenBlock()) {
                blocks.push(sourceAnalysis);
            }
            if (sourceAnalysis.isCloseBlock()) {
                SourceAnalysis openBlock = (SourceAnalysis) blocks.pop();
                forceRemove = openBlock.wasRemoved();
            }
            lineModifier.write(line, out, forceRemove);

            lineNumber++;
        }
        out.flush();
    }

    private SourceAnalysis analyseLine(String line, boolean removed) {
        if (line.trim().endsWith("{")) {
            return new OpenBlock(removed);
        } else if (line.trim().endsWith("}")) {
            return new CloseBlock(removed);
        } else {
            return new PlainCode(removed);
        }
    }
}
