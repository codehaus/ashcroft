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
        this.destDir = destDir;
    }

    public void setCloverxml(File cloverxml) {
        this.cloverxml = cloverxml;
    }

    public void execute() throws BuildException {
        verifyProperties();
        try {
            Guantanamo.runWithClover(cloverxml, destDir);
        } catch (IOException e) {
        }
    }

    private void verifyProperties() {
    }
}
