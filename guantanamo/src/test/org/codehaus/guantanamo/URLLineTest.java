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
package org.codehaus.guantanamo;

import junit.framework.TestCase;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class URLLineTest extends TestCase {
    public void testShouldBeEqual() throws MalformedURLException {
        URLLine a = new URLLine(new File("target/clover.xml").toURL(), 1);
        URLLine b = new URLLine(new File("target\\clover.xml").toURL(), 1);
        assertEquals(a, b);
        assertEquals(b, a);
    }
}
