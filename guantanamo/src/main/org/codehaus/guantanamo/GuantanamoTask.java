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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class GuantanamoTask extends MatchingTask {
    private JavaSourceToCloverReportMatcher matcher;
    private File dest;
    private GuantanamoFactory guantanamoFactory;

    public void setDir(File dir) {
        fileset.setDir(dir);
    }

    public void setDest(File dir) {
        dest = dir;
    }

    public void execute() throws BuildException {
        Project project = getProject();
        DirectoryScanner directoryScanner = fileset.getDirectoryScanner(project);
        String[] srcFiles = directoryScanner.getIncludedFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            File dir = fileset.getDir(getProject());
            String srcFile = srcFiles[i];
            fixFile(dir, srcFile);
        }
    }

    private void fixFile(File dir, String srcFile) throws BuildException {
        File src = new File(dir, srcFile);
        System.out.println("Fixing " + src.getAbsolutePath());
        File dest = new File(getDest(), srcFile + ".guantanamo");
        FileWriter tempOutput = null;
        try {
            FileReader originalInput = new FileReader(src);
            String coveragePath = matcher.getCoveragePath(srcFile);
            File coverageFile = new File(coveragePath);
            if(!coverageFile.exists()) {
                System.err.println("WARNING: Coverage file " + coverageFile.getAbsolutePath() + " not found.");
                return;
            }
            Reader coverageReader = new FileReader(coverageFile);
            Guantanamo guantanamo = getGuantanamoFactory().createGuantanamo(coverageReader);
            dest.getParentFile().mkdirs();
            tempOutput = new FileWriter(dest);
            guantanamo.scrutinise(originalInput, tempOutput);
        } catch (IOException e) {
            throw new BuildException(e);
        } finally{
            if(tempOutput != null) {
                try {
                    tempOutput.flush();
                    tempOutput.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private GuantanamoFactory getGuantanamoFactory() {
        if(guantanamoFactory == null) {
            guantanamoFactory = new DefaultGuantanamoFactory();
        }
        return guantanamoFactory;
    }

    private File getDest() {
        return dest != null ? dest : fileset.getDir(getProject());
    }

    public void setClover(File file) {
        matcher = new JavaSourceToCloverReportMatcher(file.getAbsolutePath());
    }

    public void setGuantanamoFactory(GuantanamoFactory guantanamoFactory) {
        this.guantanamoFactory = guantanamoFactory;
    }
}
