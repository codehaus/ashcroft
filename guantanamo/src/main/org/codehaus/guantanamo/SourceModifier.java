/*****************************************************************************
 * Copyright (C) ModifyingSourceVisitor Organization. All rights reserved.               *
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
import java.net.URL;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public interface SourceModifier {
    void modifySource(URL source, URL out) throws IOException;
}
