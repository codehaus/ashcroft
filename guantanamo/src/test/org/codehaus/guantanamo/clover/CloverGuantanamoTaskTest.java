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
package org.codehaus.guantanamo.clover;

import org.codehaus.guantanamo.ant.GuantanamoTask;
import org.codehaus.guantanamo.ant.AbstractAcceptanceTest;

import java.io.File;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CloverGuantanamoTaskTest extends AbstractAcceptanceTest {
    protected String getToolName() {
        return "clover";
    }

    protected void setProperties(GuantanamoTask task) {
        task.setClover(new File("target/clover/testdata/coverage-report/clover.xml"));
    }
}
