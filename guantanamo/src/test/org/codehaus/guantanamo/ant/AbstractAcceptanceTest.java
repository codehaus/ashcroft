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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;
import org.codehaus.guantanamo.io.FileComparator;

import java.io.File;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractAcceptanceTest extends TestCase {
    private GuantanamoTask task;
    private File dest;

    protected void setUp() throws Exception {
        super.setUp();
        task = new GuantanamoTask();
        task.setProject(new Project());
        dest = new File("target/" + getToolName() + "/testdata/guantanamo");
        dest.mkdirs();
    }

    public void testShouldBeAbleToGuantanamiseSeveralSources() throws IOException, TokenStreamException, RecognitionException {
        setProperties(task);
        task.setDest(dest);
        task.execute();
        try {
            // We expect this to fail because source should be take away.
            new FileComparator().compare(dest, new File("src/testdata"));
            fail();
        } catch (RuntimeException expected) {
        }
    }

    public void testShouldFailWhenCloverDoesntExist() {
        task.setClover(new File("bogus"));
        task.setDest(dest);
        try {
            task.execute();
            fail();
        } catch (BuildException expected) {
        }
    }

    protected abstract String getToolName();

    protected abstract void setProperties(GuantanamoTask task);
}
