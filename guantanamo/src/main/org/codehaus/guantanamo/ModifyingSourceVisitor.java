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

import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ModifyingSourceVisitor implements SourceVisitor {
    private final SourceModifier sourceModifier;
    private final SourceRootFinder sourceRootFinder;
    private final URL destinationRootURL;

    public ModifyingSourceVisitor(SourceModifier sourceModifier, SourceRootFinder sourceRootFinder, URL destinationRootURL) {
        this.sourceModifier = sourceModifier;
        this.sourceRootFinder = sourceRootFinder;
        this.destinationRootURL = destinationRootURL;
    }

    public void visitSource(URL source) throws IOException {
        URL out = getOut(source);
        sourceModifier.modifySource(source, out);
    }

    private URL getOut(URL source) throws MalformedURLException {
        String sourceRootPath = sourceRootFinder.getSourceRootURL().toExternalForm();
        String sourcePath = source.toExternalForm();
        String relativeSourcePath = sourcePath.substring(sourceRootPath.length());

        final File outRootDir = new File(destinationRootURL.getFile());
        File outFile = new File(outRootDir, relativeSourcePath);
        URL out = outFile.toURL();
        return out;
    }
}
