package com.thoughtworks.ashcroft.runtime;

import junit.framework.TestCase;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.TimeZone;

/**
 * @author Obie Fernandez obie@thoughtworks.com
 * @author Aslak Helles&oslash;y
 */
public class JohnAshcroftTest extends TestCase {

    public void XtestShouldAllowCreationOfNewFileObject() {
        new File("church");
    }

    public void XtestShouldNotAllowCreationOfTempFile() throws IOException {
        try {
            File.createTempFile("yahoo", "john");
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't write to files during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowCreationOfTempFileInARootFolder() throws IOException {
        try {
            File.createTempFile("yahoo", "john", new File(System.getProperty("user.dir")));
            fail("Shouldn't be able to do it!");

        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't write to files during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowReadingOfFiles() throws IOException {
        String fileName = "C:\\Windows\\explorer.exe";
        try {
            new FileInputStream(new File(fileName)).read();
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't read from files during unit tests: " + fileName, expected.getMessage());
        }
    }

    public void XtestShouldNotAllowToCheckForFilePresence() {
        String fileName = "C:\\foo\\bar";
        try {
            new File(fileName).exists();
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't check for file presence during unit tests: " + fileName, expected.getMessage());
        }
    }

    public void XtestShouldNotAllowToCreateDirectories() {
        String fileName = "C:\\foo\\bar";
        try {
            new File(fileName).mkdirs();
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't create directories during unit tests: " + fileName, expected.getMessage());
        }
    }

    public void XtestShouldAllowCreationOfNewURLObject() throws MalformedURLException {
        new File("church").toURL();
    }

    public void XtestShouldNotAllowOpeningSockets() throws IOException {
        try {
            new Socket("localhost", 9999);
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't open sockets during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowServerSockets() throws IOException {
        try {
            new ServerSocket(9999).accept();
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't listen for incoming sockets during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowCheckingURLsForEqualityBecauseStupidURLImplementationWantsToGoOnline() throws IOException {
        try {
            assertEquals(new URL("http://www.google.com/"), new URL("http://www.google.com/"));
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't compare URLs during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowCreationOfNewThreadObject() {
        try {
            new Thread("gay");
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't start threads during unit tests", expected.getMessage());
        }
    }

    public void testShouldNotAllowDisplayOfFrames() {
        try {
            new Frame();
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(), "You can't use AWT during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowExitingVM() {
        try {
            System.exit(666);
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't exit the JVM during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowSettingAnotherSecurityManager() {
        try {
            System.setSecurityManager(new SecurityManager());
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"Thou shalt have no other security managers before me", expected.getMessage());
        }
    }

    public void TODOtestShouldNotAllowAccessingAndModifyingSystemProperties() {
        try {
            System.getProperties().setProperty("no", "way");
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't call System.getProperties() during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldNotAllowSettingSystemProperty() {
        try {
            System.setProperty("no", "way");
            fail("Shouldn't be able to do it!");
        } catch (CantDoThat expected) {
            assertEquals("Called from:" + expected.getStackTraceAsString(),"You can't set system properties during unit tests", expected.getMessage());
        }
    }

    public void XtestShouldBeAbleToGetDefaultTimeZone() {
        TimeZone.getDefault();
    }
}