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

import junit.framework.TestCase;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import java.io.File;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FileComparatorTaskTest extends TestCase {
    public void testShouldFailOnDifferentDirectories() {
        FileComparatorTask task = new FileComparatorTask();
        task.setProject(new Project());
        task.setExpected(new File("src/main"));
        task.setActual(new File("src/test"));
        try {
            task.execute();
            fail();
        } catch (BuildException expected) {
        }
    }

    public void testShouldFailOnDifferentContents() {
        FileComparatorTask task = new FileComparatorTask();
        task.setProject(new Project());
        task.setExpected(new File("src/expected/jcoverage"));
        task.setActual(new File("src/testdata"));
        try {
            task.execute();
            fail();
        } catch (BuildException expected) {
        }
    }
}
