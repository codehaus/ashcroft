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

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface SourceFinder {
    void accept(SourceVisitor sourceVisitor) throws IOException;
}
