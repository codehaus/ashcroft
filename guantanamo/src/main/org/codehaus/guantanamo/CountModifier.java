/*****************************************************************************
 * Copyright (C) SourceVisitor Organization. All rights reserved.               *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Aslak Hellesoy                                           *
 * Idea by Chris Stevenson                                                   *
 *****************************************************************************/
package org.codehaus.guantanamo;

import java.io.Writer;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CountModifier implements LineModifier {
    private final int count;

    public CountModifier(int count) {
        this.count = count;
    }

    public void write(String line, Writer out, boolean forceRemove) throws IOException {
        if(!willRemove(line) && !forceRemove) {
            out.write(line);
        }
    }

    public boolean willRemove(String line) {
        return count == 0;
    }
}
