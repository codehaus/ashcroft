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

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.apache.tools.ant.Project;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GuantanamoTaskTest extends MockObjectTestCase {
    public void testShouldBeAbleToGuantanamiseSeveralSources() {
        Mock guantanamoFactory = mock(GuantanamoFactory.class);
//        Mock guantanamo = mock(Guantanamo.class);
//        guantanamoFactory.expects(once()).method("createGuantanamo").with(isA(FileReader.class)).will(returnValue(guantanamo.proxy()));
//        guantanamo.expects(once()).method("scrutinise").with(isA(FileReader.class),isA(FileWriter.class));

        GuantanamoTask task = new GuantanamoTask();
        task.setProject(new Project());
        task.setGuantanamoFactory((GuantanamoFactory)guantanamoFactory.proxy());
        task.setClover(new File("target/clover"));

        task.setDir(new File("src/main"));
        File dest = new File("target/guantanamo");
        dest.mkdirs();
        task.setDest(dest);
        task.createInclude().setName("org/codehaus/guantanamo/DefaultGuantanamo.java");
        task.execute();
    }
}
