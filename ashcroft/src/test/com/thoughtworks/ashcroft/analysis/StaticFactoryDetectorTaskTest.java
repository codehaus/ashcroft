package com.thoughtworks.ashcroft.analysis;

import junit.framework.TestCase;
import com.thoughtworks.qdox.model.JavaClass;
import java.io.IOException;
import org.apache.tools.ant.BuildException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StaticFactoryDetectorTaskTest extends TestCase {
    public void testShouldDetectStaticFactoryClassesInFileSet() throws IOException {
        StaticFactoryDetectorTask task = new StaticFactoryDetectorTask();
        JavaClass fruit = StaticFactoryDetectorTest.getFruitClass();
        JavaClass[] classes = new JavaClass[]{fruit};
        try {
            task.processClasses(classes);
            fail();
        } catch (BuildException expected) {
            String expectedMessage = "" +
                    "Ashcroft sees static factory methods! Can't do that!:\n" +
                    "    at com.thoughtworks.ashcroft.analysis.StaticFactoryDetectorTest$Fruit.factory(StaticFactoryDetectorTest.java:21)\n" +
                    "    at com.thoughtworks.ashcroft.analysis.StaticFactoryDetectorTest$Fruit.anotherFactory(StaticFactoryDetectorTest.java:29)\n";
            assertEquals(expectedMessage, expected.getMessage());
        }
    }
}
