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

import org.apache.commons.lang.StringUtils;
import org.jvnet.localizer.Localizable;

import java.util.Iterator;

/**
 * Provides access to rule descriptions and examples.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
// TODO: Remove this class alltogether?
public final class DebtMessages {

    /** Singleton instance. */
    private static final DebtMessages INSTANCE = new DebtMessages();

//    /** Available rule sets. */
//    private final Map<String, RuleSet> rules = new HashMap<String, RuleSet>();

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static DebtMessages getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of <code>DebtMessages</code>.
     */
    private DebtMessages() {
        // prevents instantiation
    }

    /**
     * Initializes the rules.
     */
    public void initialize() {
        System.out.println("<<<<<<<<<<<<<<< In DebtMessages.initialize()");
    }

    public String getMessage(final String ruleSetName, final String ruleName) {
//        if (rules.containsKey(ruleSetName)) {
//            RuleSet ruleSet = rules.get(ruleSetName);
//            Rule rule = ruleSet.getRuleByName(ruleName);
//            if (rule != null) {
//                return createMessage(rule);
//            }
//        }
//        return "this is the message";
        return StringUtils.EMPTY;
    }
    
//    private String createMessage(final Rule rule) {
//        List<String> examples = rule.getExamples();
//        if (!examples.isEmpty()) {
//            return rule.getDescription() + "<pre>" + examples.get(0) + "</pre>";
//        }
//        return rule.getDescription();
//    }

    public static Localizable _Debt_ResultAction_HealthReportNoItem() {
        return null;
    }
}


