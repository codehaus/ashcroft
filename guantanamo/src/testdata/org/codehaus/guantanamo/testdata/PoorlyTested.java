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
package org.codehaus.guantanamo.testdata;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class PoorlyTested {
    public void methodWithUncoveredSimpleIfs() {
        boolean b = true;
        if(b) {
            int i = 1;
            if(i == 0) {
                new Object();
            }
        }
    }

    public void methodWithStatementInIf(String s) {
		if(s.trim().equals("a")) {
			new Object();
		}
	}
}