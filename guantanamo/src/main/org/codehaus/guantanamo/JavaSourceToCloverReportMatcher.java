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

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class JavaSourceToCloverReportMatcher {
    private final String cloverRootDir;

    public JavaSourceToCloverReportMatcher(String cloverRootDir) {
        this.cloverRootDir = cloverRootDir;
    }

    public String getCoveragePath(String relativeSourcePath) {
        int index = relativeSourcePath.indexOf(".java");
        return cloverRootDir + "/" + relativeSourcePath.substring(0, index) + ".html";
    }
}
