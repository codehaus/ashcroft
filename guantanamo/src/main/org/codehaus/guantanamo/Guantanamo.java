package org.codehaus.guantanamo;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface Guantanamo {
    void scrutinise(Reader original, Writer output) throws IOException;
}
