/*****************************************************************************
 * Copyright (C) Guantanamo Organization. All rights reserved.               *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Aslak Hellesoy                                           *
 * Idea by Chris Stevenson                                                   *
 *****************************************************************************/
package org.codehaus.guantanamo;

import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface Monitor {
    void source(URL source);

    void destination(URL destination);

    void line(int lineNumber, String line);

    void openBlock(int lineNumber);

    void closeBlock(int lineNumber);
}
