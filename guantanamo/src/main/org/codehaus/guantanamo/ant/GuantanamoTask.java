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
    private File jcoverage;

    public void setDest(File destDir) {
        this.destDir = destDir;
    }

    public void setClover(File cloverxml) {
        this.cloverxml = cloverxml;
    }

    public void setJcoverage(File jcoverage) {
        this.jcoverage = jcoverage;
    }

    public void execute() throws BuildException {
        verifyProperties();
        try {
            AntMonitor monitor = new AntMonitor(getProject());
            if(cloverxml != null) {
                Guantanamo.runWithClover(cloverxml, destDir, monitor);
            }
            if(jcoverage != null) {
                Guantanamo.runWithJCoverage(jcoverage, destDir, monitor);
            }
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    private void verifyProperties() {
    }
}
