package com.thoughtworks.ashcroft.analysis;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision: 0.0 $
 */
public class StaticFactoryDetectorTest extends TestCase {
    private StaticFactoryDetector staticFactoryDetector = new StaticFactoryDetector();

    public static class Fruit {
        // analysis cuz return type == hosting class and static
        public static Fruit factory() {
            return null;
        }

        public Fruit yo() {
            return null;
        }

        public static Fruit anotherFactory() {
            return null;
        }
    }

    public void testShouldRecogniseStaticMethodsWithReturnValueAsFactoryMethod() throws IOException {
        JavaClass fruit = getFruitClass();
        JavaMethod whatever = fruit.getMethodBySignature("factory", null);
        StringWriter report = new StringWriter();
        staticFactoryDetector.writeReport(whatever, report);
        String expected = "    at com.thoughtworks.ashcroft.analysis.StaticFactoryDetectorTest$Fruit.factory(StaticFactoryDetectorTest.java:21)\n";
        assertEquals(expected, report.toString());
    }

    public void testShouldNotRecogniseNonStaticMethodsWithReturnValueAsFactoryMethod() throws IOException {
        JavaClass fruit = getFruitClass();
        JavaMethod whatever = fruit.getMethodBySignature("yo", null);
        StringWriter report = new StringWriter();
        staticFactoryDetector.writeReport(whatever, report);
        String expected = "";
        assertEquals(expected, report.toString());
    }

    public void testShouldRecogniseClassWithFactoryMethodAsNaughtyClass() throws IOException {
        JavaClass fruit = getFruitClass();
        StringWriter report = new StringWriter();
        staticFactoryDetector.writeReport(fruit, report);
        String expected = "" +
                "    at com.thoughtworks.ashcroft.analysis.StaticFactoryDetectorTest$Fruit.factory(StaticFactoryDetectorTest.java:21)\n" +
                "    at com.thoughtworks.ashcroft.analysis.StaticFactoryDetectorTest$Fruit.anotherFactory(StaticFactoryDetectorTest.java:29)\n";
        assertEquals(expected, report.toString());
    }

    static JavaClass getFruitClass() throws IOException {
        JavaDocBuilder javaDocBuilder = new JavaDocBuilder();
        String thisSourcePath = "/" + StaticFactoryDetectorTest.class.getName().replace('.', '/') + ".java";
        JavaSource thisSource = javaDocBuilder.addSource(StaticFactoryDetectorTaskTest.class.getResource(thisSourcePath));
        JavaClass thisTestClass = thisSource.getClasses()[0].getInnerClasses()[0];
        return thisTestClass;
    }
}
