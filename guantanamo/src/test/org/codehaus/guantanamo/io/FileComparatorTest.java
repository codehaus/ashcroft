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
package org.codehaus.guantanamo.io;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FileComparatorTest extends TestCase {
    public void testShouldNotFailForIdenticalFiles() throws IOException {
        File buildXml = new File("build.xml");
        new FileComparator().compare(buildXml, buildXml);
    }

    public void testShouldFailForDifferentFiles() throws IOException {
        File buildXml = new File("build.xml");
        File licence = new File("LICENSE.txt");
        try {
            new FileComparator().compare(buildXml, licence);
            fail();
        } catch (RuntimeException expected) {
        }
    }

    public void testShouldNotFailForIdenticalDirectories() throws IOException {
        File main = new File("src/main");
        new FileComparator().compare(main, main);
    }

    public void testShouldFailForNonMatchingDirectories() throws IOException {
        File main = new File("src/main");
        File test = new File("src/test");
        try {
            new FileComparator().compare(main, test);
            fail();
        } catch (FileNotFoundException expected) {
        }
    }

    public void testShouldFailForNonMatchingDirectoriesWithDifferentContent() throws IOException {
        File exp = new File("src/expected/clover");
        File actual = new File("src/testdata");
        try {
            new FileComparator().compare(exp, actual);
            fail();
        } catch (RuntimeException expected) {
            expected.printStackTrace();
        }
    }

}
