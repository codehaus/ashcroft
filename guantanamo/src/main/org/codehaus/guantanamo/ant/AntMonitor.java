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

import org.apache.tools.ant.Project;
import org.codehaus.guantanamo.Monitor;

import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class AntMonitor implements Monitor {
    private final Project project;

    public AntMonitor(Project project) {
        this.project = project;
    }

    public void source(URL source) {
        project.log("GUANTANAMO Source: " + source, Project.MSG_VERBOSE);
    }

    public void destination(URL destination) {
        project.log("GUANTANAMO Destination: " + destination, Project.MSG_VERBOSE);
    }

    public void line(int lineNumber, String line) {
        project.log("GUANTANAMO Line: " + line, Project.MSG_VERBOSE);
    }

    public void openBlock(int lineNumber) {
        project.log("GUANTANAMO Open block: " + lineNumber, Project.MSG_VERBOSE);
    }

    public void closeBlock(int lineNumber) {
        project.log("GUANTANAMO Close block: " + lineNumber, Project.MSG_VERBOSE);
    }
}
