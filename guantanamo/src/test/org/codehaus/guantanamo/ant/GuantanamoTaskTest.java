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
import org.apache.tools.ant.Project;
import org.jmock.MockObjectTestCase;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GuantanamoTaskTest extends MockObjectTestCase {
    public void testShouldBeAbleToGuantanamiseSeveralSources() {
        GuantanamoTask task = new GuantanamoTask();
        task.setProject(new Project());
        File cloverXml = new File("target/testdata/clover-reports/clover.xml");
        assertTrue(cloverXml.isFile());
        task.setCloverxml(cloverXml);

        File dest = new File("target/guantanamo/guantanamoed-src");
        dest.mkdirs();
        task.setDest(dest);
        task.execute();

        final File coreDir = new File("target/guantanamo/guantanamoed-src/org/codehaus/guantanamo");
        assertTrue(coreDir.isDirectory());
        assertEquals(16, coreDir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        }).length);
    }
}
