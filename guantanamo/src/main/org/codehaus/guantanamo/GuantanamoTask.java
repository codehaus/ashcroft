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
    private File destDir;
    private GuantanamoFactory guantanamoFactory;
    private File cloverDir;

    public void setDir(File dir) {
        fileset.setDir(dir);
    }

    public void setDest(File dir) {
        destDir = dir;
    }

    public void execute() throws BuildException {
        verifyProperties();
        Project project = getProject();
        DirectoryScanner directoryScanner = fileset.getDirectoryScanner(project);
        String[] srcFiles = directoryScanner.getIncludedFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            File dir = getDir();
            String srcFile = srcFiles[i];
            fixFile(dir, srcFile);
        }
    }

    private File getDir() {
        return fileset.getDir(getProject());
    }

    private void verifyProperties() {
        if(getDir() == null) {
            throw new BuildException("dir must be specified.");
        }
        if(!getDir().isDirectory()) {
            throw new BuildException("dir must be a directory.");
        }
        if(destDir == null) {
            throw new BuildException("dest must be specified.");
        }
        if(!destDir.isDirectory()) {
            throw new BuildException("dest must be a directory.");
        }
        if(destDir.getAbsolutePath().equals(getDir().getAbsolutePath())) {
            throw new BuildException("dir and dest can't point to the same directory.");
        }
        if(cloverDir == null) {
            throw new BuildException("clover must be specified.");
        }
        if(!cloverDir.isDirectory()) {
            throw new BuildException("clover must be a directory.");
        }
    }

    private void fixFile(File dir, String srcFile) throws BuildException {
        File src = new File(dir, srcFile);
        System.out.println("Fixing " + src.getAbsolutePath());
        File destFile = new File(destDir, srcFile);
        FileWriter destWriter = null;
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
            destFile.getParentFile().mkdirs();
            destWriter = new FileWriter(destFile);
            guantanamo.removeBadness(originalInput, destWriter);
            destWriter.flush();
            destWriter.close();
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    private GuantanamoFactory getGuantanamoFactory() {
        if(guantanamoFactory == null) {
            guantanamoFactory = new DefaultGuantanamoFactory();
        }
        return guantanamoFactory;
    }

    public void setClover(File dir) {
        cloverDir = dir;
        matcher = new JavaSourceToCloverReportMatcher(dir.getAbsolutePath());
    }

    public void setGuantanamoFactory(GuantanamoFactory guantanamoFactory) {
        this.guantanamoFactory = guantanamoFactory;
    }
}
