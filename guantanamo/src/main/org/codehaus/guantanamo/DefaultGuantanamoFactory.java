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

import java.io.Reader;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class DefaultGuantanamoFactory implements GuantanamoFactory {
    public Guantanamo createGuantanamo(Reader coverageReader) {
        try {
            Guantanamo guantanamo = new DefaultGuantanamo(new CoverageRegime(new CloverInvestigator(coverageReader)));
            return guantanamo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
