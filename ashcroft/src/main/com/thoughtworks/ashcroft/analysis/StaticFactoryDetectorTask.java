package com.thoughtworks.ashcroft.analysis;

import com.thoughtworks.qdox.ant.AbstractQdoxTask;
import com.thoughtworks.qdox.model.JavaClass;
import org.apache.tools.ant.BuildException;

import java.io.StringWriter;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StaticFactoryDetectorTask extends AbstractQdoxTask {
    private static final String MESSAGE = "Ashcroft sees static factory methods! Can't do that!:\n";
    private StaticFactoryDetector staticFactoryDetector = new StaticFactoryDetector();

    protected void processClasses(JavaClass[] javaClasses) {
        StringWriter reportWriter = new StringWriter();
        try {
            for (int i = 0; i < javaClasses.length; i++) {
                staticFactoryDetector.writeReport(javaClasses[i], reportWriter);
            }
            String report = reportWriter.getBuffer().toString();
            if(!report.equals("")) {
                throw new BuildException(MESSAGE + report);
            }
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }
}
