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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.generama.astunit.ASTTestCase;

import java.io.File;
import java.io.IOException;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class DogFoodTest extends ASTTestCase {
    public void testShouldHaveIdenticalSourcesToOurOwnGuantanamoedSources() throws IOException, TokenStreamException, RecognitionException {
        assertSourceTreeEquals(new File("target/main/guantanamoed-src"), new File("src/main"));
    }
}
