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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class CloverInvestigator implements CoverageInvestigator {
    private final Pattern coveragePattern = Pattern.compile("<TD align=\"right\" class=\"[a-zA-Z]*\">\\s*<A title=\"Line ([0-9]*):[^>]*>&nbsp;([0-9]*)</A", Pattern.DOTALL);
    private final Map lineCounts = new HashMap();
    private Stack blockCoverage = new Stack();

    public CloverInvestigator(Reader classCoverageReport) throws IOException {
        String report = readReport(classCoverageReport);
        scanReport(report);
    }

    private String readReport(Reader classCoverageReport) throws IOException {
        BufferedReader bufferedReport = new BufferedReader(classCoverageReport);

        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bufferedReport.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String report = sb.toString();
        return report;
    }

    private void scanReport(String report) {
        Matcher m = coveragePattern.matcher(report);
        while (m.find()) {
            String lineNumberString = m.group(1);
            int lineNumber = Integer.parseInt(lineNumberString);

            String countString = m.group(2);
            int count = Integer.parseInt(countString);

            lineCounts.put(new Integer(lineNumber), new Integer(count));
        }
    }

    public boolean isCovered(String line, int lineNumber) {
        if(!isComment(line) && line.trim().endsWith("{")) {
            blockCoverage.push(new Boolean(true));
        }
        boolean isCovered = true;
        Integer count = (Integer) lineCounts.get(new Integer(lineNumber));
        if(count != null) {
            isCovered = count.intValue() > 0;
            if(!isCovered) {
                blockCoverage.pop();
                blockCoverage.push(new Boolean(false));
            }
        } else {
            // No coverage information. Empty line or a non-statement line.
            // Delete it if it corresponds to previously deleted lines
            if(!isComment(line) && line.trim().endsWith("}")) {
                Boolean openingWasCovered = (Boolean) blockCoverage.pop();
                isCovered = openingWasCovered.booleanValue();
            }
        }
        return isCovered;
    }

    private boolean isComment(String line) {
        String trimmedLine = line.trim();
        return
                trimmedLine.startsWith("//") ||
                trimmedLine.startsWith("/*") ||
                trimmedLine.startsWith("*") ||
                trimmedLine.endsWith("*/");
    }
}
