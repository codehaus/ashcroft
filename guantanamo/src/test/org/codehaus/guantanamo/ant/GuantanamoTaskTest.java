/*****************************************************************************
 * Copyright (C) SourceVisitor Organization. All rights reserved.               *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Aslak Hellesoy                                           *
 * Idea by Chris Stevenson                                                   *
 *****************************************************************************/
package org.codehaus.guantanamo.ant;

import org.apache.tools.ant.Project;
import org.jmock.MockObjectTestCase;
import org.codehaus.guantanamo.ant.GuantanamoTask;

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
        File clover = new File("target/clover.xml");
        clover.mkdirs();
        task.setCloverxml(clover);

        File dest = new File("target/guantanamo");
        dest.mkdirs();
        task.setDest(dest);
        task.execute();
        assertEquals(17, new File("target/guantanamo/org/codehaus/guantanamo").list(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        }).length);
    }


}
