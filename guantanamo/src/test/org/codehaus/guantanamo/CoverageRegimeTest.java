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

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CoverageRegimeTest extends MockObjectTestCase {
    public void testShouldDeleteUntestedLines() {
        Mock lineInvestigator = mock(CoverageInvestigator.class);
        Regime regime = new CoverageRegime((CoverageInvestigator)lineInvestigator.proxy());

        lineInvestigator.expects(once()).method("isCovered").with(eq("ping"), eq(10)).will(returnValue(false));
        lineInvestigator.expects(once()).method("isCovered").with(eq("pong"), eq(11)).will(returnValue(true));

        assertEquals("/// NOT COVERED: ping", regime.regimify("ping", 10));
        assertEquals("pong", regime.regimify("pong", 11));
    }
}
