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

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface Regime {
    /**
     * Modifies a line of code according to a certain regime.
     * @param line the line to modify
     * @return the modified line
     */
    String regimify(String line, int lineNumber);
}
