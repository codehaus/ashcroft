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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class DefaultGuantanamoTest extends MockObjectTestCase{
    public void testShouldBeAbleToModifyLines() throws IOException {
        Reader original = new StringReader( "" +
                "one\n" +
                "two\n" +
                "three\n"
        );

        String expected = "" +
                "one\n" +
                "three\n";

        Writer output = new StringWriter();

        Mock regime = mock(Regime.class);
        regime.expects(once()).method("regimify").with(eq("one\n"), eq(1)).will(returnValue("one\n"));
        regime.expects(once()).method("regimify").with(eq("two\n"), eq(2)).will(returnValue(""));
        regime.expects(once()).method("regimify").with(eq("three\n"), eq(3)).will(returnValue("three\n"));

        Guantanamo guantanamo = new DefaultGuantanamo((Regime)regime.proxy());
        guantanamo.scrutinise(original, output);

        assertEquals(expected, output.toString());
    }
}
