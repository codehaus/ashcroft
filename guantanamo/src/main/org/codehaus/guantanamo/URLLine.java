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

import java.net.URL;

/**
 * Key used to locate {@link LineModifier}s in Maps.
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class URLLine extends Object {
    private String string;

    public URLLine(URL url, int lineNumber) {
        string = url.getFile() + ":" + lineNumber;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        return toString().equals(o.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return string;
    }
}
