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

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class ModifyingSourceVisitorTest extends MockObjectTestCase {
    public void testShouldNotAllowOverwritingSources() throws IOException {
        URL sourceRootURL = new File("src/main").toURL();
        Mock sourceModifier = mock(SourceModifier.class);
        Mock sourceRootFinder = mock(SourceRootFinder.class);
        sourceRootFinder.expects(once()).method("getSourceRootURL").will(returnValue(new File("src/main").toURL()));
        SourceVisitor sourceVisitor = new ModifyingSourceVisitor(
                (SourceModifier)sourceModifier.proxy(),
                (SourceRootFinder)sourceRootFinder.proxy(),
                sourceRootURL);
        try {
            sourceVisitor.visitSource(new File("src/main/org/codehaus/guantanamo/Guantanamo.java").toURL());
            fail();
        } catch (OwnTerritoryException expected) {
        }
    }
}
