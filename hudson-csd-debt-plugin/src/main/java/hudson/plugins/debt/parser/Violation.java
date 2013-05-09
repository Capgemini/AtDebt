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
package hudson.plugins.debt.parser;

/**
 * Java Bean class for a Debt Record.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class Violation {

    /** Type of warning. */
    private String rule;
    /** Category of warning. */
    private String ruleset;
    /** Category of warning. */
    private String externalInfoUrl;
    /** Package of warning. */
    private String javaPackage;
    /** Priority of warning. */
    private int priority;
    /** Message of warning. */
    private String message;
    /** The first line of the warning range. */
    private int beginline;
    /** The last line of the warning range. */
    private int endline;

    /**
     * Returns the rule.
     *
     * @return the rule
     */
    public String getRule() {
        return rule;
    }
    /**
     * Sets the rule to the specified value.
     *
     * @param rule the value to set
     */
    public void setRule(final String rule) {
        this.rule = rule;
    }
    /**
     * Returns the ruleset.
     *
     * @return the ruleset
     */
    public String getRuleset() {
        return ruleset;
    }
    /**
     * Sets the ruleset to the specified value.
     *
     * @param ruleset the value to set
     */
    public void setRuleset(final String ruleset) {
        this.ruleset = ruleset;
    }
    /**
     * Returns the externalInfoUrl.
     *
     * @return the externalInfoUrl
     */
    public String getExternalInfoUrl() {
        return externalInfoUrl;
    }
    /**
     * Sets the externalInfoUrl to the specified value.
     *
     * @param externalInfoUrl the value to set
     */
    public void setExternalInfoUrl(final String externalInfoUrl) {
        this.externalInfoUrl = externalInfoUrl;
    }
    /**
     * Returns the javaPackage.
     *
     * @return the javaPackage
     */
    public String getPackage() {
        return javaPackage;
    }
    /**
     * Sets the javaPackage to the specified value.
     *
     * @param packageName the value to set
     */
    public void setPackage(final String packageName) {
        javaPackage = packageName;
    }
    /**
     * Returns the priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }
    /**
     * Sets the priority to the specified value.
     *
     * @param priority the value to set
     */
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    /**
     * Returns the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * Sets the message to the specified value.
     *
     * @param message the value to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }
    /**
     * Returns the beginline.
     *
     * @return the beginline
     */
    public int getBeginline() {
        return beginline;
    }
    /**
     * Sets the beginline to the specified value.
     *
     * @param beginline the value to set
     */
    public void setBeginline(final int beginline) {
        this.beginline = beginline;
    }
    /**
     * Returns the endline.
     *
     * @return the endline
     */
    public int getEndline() {
        return endline;
    }
    /**
     * Sets the endline to the specified value.
     *
     * @param endline the value to set
     */
    public void setEndline(final int endline) {
        this.endline = endline;
    }
}

