package com.thoughtworks.ashcroft.analysis;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StaticFactoryDetector {
    /**
     * Prints the same way as a stack trace. Most IDEs will turn it into a hyperlink.
     *
     * @param method
     * @param report
     * @throws IOException
     */
    public void writeReport(JavaMethod method, Writer report) throws IOException {
        if(method.isStatic() && method.isPublic()) {
            // TODO: move this to QDox: asStacktraceLine()
            // add visitor support to QDox? a la ASM?

            report.write("    at ");
            report.write(method.getParent().getFullyQualifiedName());
            report.write(".");
            report.write(method.getName());
            report.write("(");
            report.write(method.getParent().getParentSource().getFile().getName());
            report.write(":");
            report.write("" + method.getLineNumber());
            report.write(")\n");
        }
    }

    public void writeReport(JavaClass clazz, Writer report) throws IOException {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            writeReport(methods[i], report);
        }
    }

}
