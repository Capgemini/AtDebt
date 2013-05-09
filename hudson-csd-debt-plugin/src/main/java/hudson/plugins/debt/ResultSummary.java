/*
 * @Debt Copyright (C) 2010 Andrew Harmel-Law
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * To contact the development team, please refer to the project site at:
 *
 *      http://kenai.com/projects/csdutilities
 */
package hudson.plugins.debt;

import hudson.plugins.Messages;

/**
 * Represents the result summary of the Debt parser. This summary will be
 * shown in the summary.jelly script of the Debt result action.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public final class ResultSummary {

    /**
     * Returns the message to show as the result summary.
     *
     * @param result the result
     * @return the message
     */
    public static String createSummary(final DebtResult result) {
        StringBuilder summary = new StringBuilder();
        int violations = result.getNumberOfAnnotations();

        summary.append("Debt: ");
        if (violations > 0) {
            summary.append("<a href=\"debtResult\">");
        }
        if (violations == 1) {
            summary.append(Messages.Debt_ResultAction_OneWarning());
        }
        else {
            summary.append(Messages.Debt_ResultAction_MultipleWarnings(violations));
        }
        if (violations > 0) {
            summary.append("</a>");
        }
        summary.append(" ");
        if (result.getNumberOfModules() == 1) {
            summary.append(Messages.Debt_ResultAction_OneFile());
        }
        else {
            summary.append(Messages.Debt_ResultAction_MultipleFiles(result.getNumberOfModules()));
        }
        return summary.toString();
    }

    /**
     * Returns the message to show as the result summary.
     *
     * @param result
     *            the result
     * @return the message
     */
    public static String createDeltaMessage(final DebtResult result) {
        StringBuilder summary = new StringBuilder();
        if (result.getNumberOfNewWarnings() > 0) {
            summary.append("<li><a href=\"debtResult/new\">");
            if (result.getNumberOfNewWarnings() == 1) {
                summary.append(Messages.Debt_ResultAction_OneNewWarning());
            }
            else {
                summary.append(Messages.Debt_ResultAction_MultipleNewWarnings(result.getNumberOfNewWarnings()));
            }
            summary.append("</a></li>");
        }
        if (result.getNumberOfFixedWarnings() > 0) {
            summary.append("<li><a href=\"debtResult/fixed\">");
            if (result.getNumberOfFixedWarnings() == 1) {
                summary.append(Messages.Debt_ResultAction_OneFixedWarning());
            }
            else {
                summary.append(Messages.Debt_ResultAction_MultipleFixedWarnings(result.getNumberOfFixedWarnings()));
            }
            summary.append("</a></li>");
        }

        return summary.toString();
    }

    /**
     * Instantiates a new result summary.
     */
    private ResultSummary() {
        // prevents instantiation
    }
}