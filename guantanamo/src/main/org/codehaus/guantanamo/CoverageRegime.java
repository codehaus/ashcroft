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
public class CoverageRegime implements Regime {
    private final CoverageInvestigator coverageInvestigator;

    public CoverageRegime(CoverageInvestigator coverageInvestigator) {
        this.coverageInvestigator = coverageInvestigator;
    }

    public String regimify(String line, int lineNumber) {
        if(coverageInvestigator.isCovered(line, lineNumber)) {
            return line;
        } else {
            return "/// NOT COVERED: " + line;
        }
    }
}
