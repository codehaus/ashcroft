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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class FileComparator {

    public void compare(File expected, File actual) throws IOException {
        if (expected.isFile()) {
            assertEquals(expected, actual);
        } else {
            iterate(expected, actual, "");
        }
    }

    private void iterate(File expectedRoot, File currentActualDir, String relativePath) throws IOException {
        File[] children = currentActualDir.listFiles();
        for (int i = 0; i < children.length; i++) {
            File child = children[i];
            if (child.isFile() && child.getName().endsWith(".java")) {
                File expectedDir = new File(expectedRoot, relativePath);
                File expectedSource = new File(expectedDir, child.getName());
                assertEquals(expectedSource, child);
            } else if (child.isDirectory()) {
                iterate(expectedRoot, child, relativePath + File.separator + child.getName());
            }
        }
    }

    private void assertEquals(File expectedFile, File actualFile) throws IOException {
        LineReader expected = new LineReader(new FileReader(expectedFile));
        LineReader actual = new LineReader(new FileReader(actualFile));
        String expectedLine = null;
        int lineNumber = 1;
        while ((expectedLine = expected.readLine()) != null) {
            assertEquals(lineNumber, expectedLine, actual.readLine(), expectedFile, actualFile);
            lineNumber++;
        }
    }

    private void assertEquals(int lineNumber, String expectedLine, String actualLine, File expectedFile, File actualFile) {
        if (!expectedLine.equals(actualLine)) {
            String message =
                    "Expected:\n" +
                    expectedFile + ":" + lineNumber + "\n" +
                    expectedLine +
                    "Actual:\n" + "\n" +
                    actualFile + ":" + lineNumber + "\n" +
                    actualLine;
            throw new RuntimeException(message);
        }
    }
}
