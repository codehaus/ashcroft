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
import org.codehaus.guantanamo.io.FileComparator;

import java.io.File;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FileComparatorTask extends Task {

    private File expected;
    private File actual;

    public void setExpected(File expected) {
        this.expected = expected;
    }

    public void setActual(File actual) {
        this.actual = actual;
    }

    public void execute() throws BuildException {
        try {
            new FileComparator().compare(expected, actual);
        } catch (RuntimeException e) {
            throw new BuildException(e);
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }
}
