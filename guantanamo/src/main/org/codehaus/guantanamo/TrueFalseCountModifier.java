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

import java.io.IOException;
import java.io.Writer;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class TrueFalseCountModifier implements LineModifier {
    public TrueFalseCountModifier(int trueCount, int falseCount) {
    }

    public void write(String line, Writer out, boolean forceRemove) throws IOException {
    }

    public boolean willRemove(String line) {
        return true;
    }
}
