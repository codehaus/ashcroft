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
package org.codehaus.guantanamo.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.codehaus.guantanamo.Guantanamo;

import java.io.File;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GuantanamoTask extends Task {
    private File destDir;

    private File cloverxml;

    public void setDest(File destDir) {
        if (!destDir.isDirectory()) {
            throw new BuildException("dest must be a directory. " + destDir.getAbsolutePath());
        }
        this.destDir = destDir;
    }

    public void setCloverxml(File cloverxml) {
        if (!cloverxml.isFile()) {
            throw new BuildException("cloverxml must point to a Clover XML coverage file");
        }
        this.cloverxml = cloverxml;
    }

    public void execute() throws BuildException {
        verifyProperties();
        try {
            Guantanamo.runWithClover(cloverxml, destDir);
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    private void verifyProperties() {
        if (destDir == null) {
            throw new BuildException("dest must be specified.");
        }
        if (cloverxml == null) {
            throw new BuildException("cloverxml must be specified.");
        }
    }
}
