package com.thoughtworks.ashcroft.runtime;

import java.io.PrintWriter;
import java.io.StringWriter;
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

    public void checkAccess(Thread t) {
        if (isJUnitCall(getStackTrace())) {
            if (t.getName().startsWith("AWT-")) {
                throw new CantDoThat(CANT_USE_AWT);
            } else {
                throw new CantDoThat(CANT_START_THREADS);
            }
        }
    }

    public void checkConnect(String host, int port) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace)) {
            if (isUrlEquals(stackTrace)) {
                throw new CantDoThat(CANT_COMPARE_URLS);
            } else {
                throw new CantDoThat(CANT_OPEN_SOCKETS);
            }
        }
    }

    private boolean isUrlEquals(String stackTrace) {
        return stackTrace.indexOf("java.net.URL") != -1;
    }

    public void checkListen(int port) {
        if (isJUnitCall(getStackTrace())) {
            throw new CantDoThat(CANT_LISTEN_ON_SOCKETS);
        }
    }

    public void checkExit(int status) {
        if (isJUnitCall(getStackTrace())) {
            throw new CantDoThat(CANT_EXIT_JVM);
        }
    }

    public void checkWrite(String file) {
        if (isJUnitCall(getStackTrace())) {
            throw new CantDoThat(CANT_WRITE_FILES);
        }
    }

    public void checkRead(String file) {
        String stackTrace = getStackTrace();
        if (isJUnitCall(stackTrace) && !isClassLoaderRead(stackTrace)) {
            if (file.indexOf("font.properties") != -1) {
                throw new CantDoThat(CANT_USE_AWT);
            } else {
                if(false) {
                } else if(isJavaIoFileMkdirMethod(stackTrace)) {
                    throw new CantDoThat(CANT_CREATE_DIRECTORIES + file);
                } else if(isJavaIoFileExistsMethod(stackTrace)) {
                    throw new CantDoThat(CANT_CHECK_FILE_PRESENCE + file);
                } else if(!isJavaIoFileIsDirectoryMethod(stackTrace)) {
                    throw new CantDoThat(CANT_READ_FILES + file);
                }
            }
        }
    }

    public void checkPermission(Permission perm) {
        if ("createSecurityManager".equals(perm.getName())) {
            throw new CantDoThat(CANT_INSTALL_SECURITYMANAGER);
        } else if(perm instanceof PropertyPermission) {
            if ("write".equals(perm.getActions()) && !isTimeZoneGetDefault(getStackTrace())) {
                throw new CantDoThat(CANT_CHANGE_SYSTEM_PROPERTIES);
            }
        }
    }

    private boolean isTimeZoneGetDefault(String stackTrace) {
        return stackTrace.indexOf("java.util.TimeZone") != -1;
    }

    private boolean isJUnitCall(String stackTrace) {
        return stackTrace.indexOf("junit.framework.TestCase") != -1;
    }

    private boolean isClassLoaderRead(String stackTrace) {
        return stackTrace.indexOf("java.lang.ClassLoader") != -1;
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
        StringWriter sw = new StringWriter();
        new Throwable().printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
