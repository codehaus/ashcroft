package com.thoughtworks.ashcroft.runtime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Permission;
import java.util.PropertyPermission;

/**
 * Enable Ashcroft by setting the -Djava.security.manager=com.thoughtworks.ashcroft.runtime.JohnAshcroft
 * JVM parameter.
 *
 * @author Obie Fernandez obie@thoughtworks.com
 * @author Aslak Helles&oslash;y
 */
public class JohnAshcroft extends SecurityManager {
    private static final String CANT_USE_AWT = "You can't use AWT during unit tests";
    private static final String CANT_START_THREADS = "You can't start threads during unit tests";
    private static final String CANT_OPEN_SOCKETS = "You can't open sockets during unit tests";
    private static final String CANT_LISTEN_ON_SOCKETS = "You can't listen for incoming sockets during unit tests";
    private static final String CANT_EXIT_JVM = "You can't exit the JVM during unit tests";
    private static final String CANT_WRITE_FILES = "You can't write to files during unit tests";
    private static final String CANT_READ_FILES = "You can't read from files during unit tests: ";
    private static final String CANT_CREATE_DIRECTORIES = "You can't create directories during unit tests: ";
    private static final String CANT_CHECK_FILE_PRESENCE = "You can't check for file presence during unit tests: ";
    private static final String CANT_INSTALL_SECURITYMANAGER = "Thou shalt have no other security managers before me";
    private static final String CANT_COMPARE_URLS = "You can't compare URLs during unit tests";
    private static final String CANT_CHANGE_SYSTEM_PROPERTIES = "You can't set system properties during unit tests";

    private static final String LOGGING = System.getProperty("com.thoughtworks.ashcroft.runtime.logging");
    private static PrintWriter LOG_WRITER = null;
    private static final boolean FORGIVING = "true".equals(System.getProperty("com.thoughtworks.ashcroft.runtime.forgiving"));

    private boolean inGetStackTrace = false;
    private boolean hypocricy = false;

    static {
        if (LOGGING != null) {
            initializeLogWriter();
            initializeLogFlusher();
        }
    }

    private static void initializeLogWriter() {
        if (LOGGING.equals("stdout")) {
            LOG_WRITER = new PrintWriter(System.out);
        } else if (LOGGING.equals("stderr")) {
            LOG_WRITER = new PrintWriter(System.err);
        } else {
            try {
                LOG_WRITER = new PrintWriter(new FileWriter(LOGGING));
            } catch (IOException e) {
                System.err.println("Ashcroft couldn't write log to " + LOGGING);
                e.printStackTrace(System.err);
                System.exit(-1);
            }
        }
    }

    private static void initializeLogFlusher() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG_WRITER.flush();
                LOG_WRITER.close();
            }
        });
    }

    public void checkAccess(Thread t) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace)) {
            if (t.getName().startsWith("AWT-")) {
                cantDoThat(CANT_USE_AWT, stackTrace);
            } else {
                cantDoThat(CANT_START_THREADS, stackTrace);
            }
        }
    }

    public void checkConnect(String host, int port) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace)) {
            if (isUrlEquals(stackTrace)) {
                cantDoThat(CANT_COMPARE_URLS, stackTrace);
            } else {
                cantDoThat(CANT_OPEN_SOCKETS, stackTrace);
            }
        }
    }

    private boolean isUrlEquals(String stackTrace) {
        return stackTrace.indexOf("java.net.URL") != -1;
    }

    public void checkListen(int port) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace)) {
            cantDoThat(CANT_LISTEN_ON_SOCKETS, stackTrace);
        }
    }

    public void checkExit(int status) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace)) {
            cantDoThat(CANT_EXIT_JVM, stackTrace);
        }
    }

    public void checkWrite(String file) {
        if(!hypocricy) {
            String stackTrace = getStackTrace();
            if (isJUnitCall(stackTrace)) {
                cantDoThat(CANT_WRITE_FILES, stackTrace);
            }
        }
    }

    public void checkRead(String file) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace) && !isClassLoaderRead(stackTrace)) {
            if (isJavaAwtCall(stackTrace)) {
                cantDoThat(CANT_USE_AWT, stackTrace);
            } else {
                if (isTimeZoneGetDefault(stackTrace)) {
                    return;
                } else if (isJavaxXmlCall(stackTrace)) {
                    return;
                } else if (isJavaIoFileMkdirMethod(stackTrace)) {
                    cantDoThat(CANT_CREATE_DIRECTORIES + file, stackTrace);
                } else if (isJavaIoFileExistsMethod(stackTrace)) {
                    cantDoThat(CANT_CHECK_FILE_PRESENCE + file, stackTrace);
                } else if (!isJavaIoFileIsDirectoryMethod(stackTrace)) {
                    cantDoThat(CANT_READ_FILES + file, stackTrace);
                }
            }
        }
    }

    public void checkPermission(Permission perm) {
        if (!inGetStackTrace) {
            String stackTrace = getStackTrace();
            if ("createSecurityManager".equals(perm.getName())) {
                cantDoThat(CANT_INSTALL_SECURITYMANAGER, stackTrace);
            } else if (perm instanceof PropertyPermission) {
                if (!"line.separator".equals(perm.getName())) {
                    if ("write".equals(perm.getActions()) && !isTimeZoneGetDefault(stackTrace) && !isCompilerRun(stackTrace)) {
                        cantDoThat(CANT_CHANGE_SYSTEM_PROPERTIES, stackTrace);
                    }
                }
            }
        }
    }

    private boolean isCompilerRun(String stackTrace) {
        return stackTrace.indexOf("java.lang.Compiler$1.run") != -1;
    }

    private boolean isTimeZoneGetDefault(String stackTrace) {
        return stackTrace.indexOf("java.util.TimeZone") != -1;
    }

    private boolean isJUnitCall(String stackTrace) {
        return stackTrace.indexOf("junit.framework.TestCase") != -1;
    }

    private boolean isJavaAwtCall(String stackTrace) {
        return stackTrace.indexOf("java.awt") != -1;
    }

    private boolean isClassLoaderRead(String stackTrace) {
        return stackTrace.indexOf("java.lang.ClassLoader") != -1;
    }

    private boolean isJavaxXmlCall(String stackTrace) {
        return false ||
                // crimson/jdk 1.4
                stackTrace.indexOf("javax.xml") != -1 ||
                // xerces/jdk 1.3
                stackTrace.indexOf("org.apache.xerces") != -1;
    }

    private boolean isJavaIoFileIsDirectoryMethod(String stackTrace) {
        return stackTrace.indexOf("java.io.File") != -1 && stackTrace.indexOf("isDirectory") != -1;
    }

    private boolean isJavaIoFileExistsMethod(String stackTrace) {
        return stackTrace.indexOf("java.io.File") != -1 && stackTrace.indexOf("exists") != -1;
    }

    private boolean isJavaIoFileMkdirMethod(String stackTrace) {
        return stackTrace.indexOf("java.io.File") != -1 && stackTrace.indexOf("mkdir") != -1;
    }

    private String getStackTrace() {
        inGetStackTrace = true;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        new Throwable().printStackTrace(pw);
        String stackTrace = sw.toString();
        inGetStackTrace = false;
        return stackTrace;
    }

    private void cantDoThat(String whatYouCantDo, String stackTrace) {
        if (LOG_WRITER != null) {
            hypocricy = true;
            LOG_WRITER.println(whatYouCantDo);
            LOG_WRITER.println(stackTrace);
            LOG_WRITER.println("--------------------------------------------------------------------------------");
            hypocricy = false;
        }
        if(!FORGIVING) {
            throw new CantDoThat(whatYouCantDo, stackTrace);
        }
    }

}
