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

import hudson.Extension;
import hudson.plugins.Messages;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Descriptor for the class {@link DebtPublisher}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
@Extension(ordinal = 100)
public final class DebtPluginDescriptor extends PluginDescriptor {

    /** Plug-in name. */
    private static final String PLUGIN_NAME = "debt";

    /** Icon to use for the result and project action. */
    private static final String ACTION_ICON = "/plugin/debt/icons/debt-24x24.png";

    /**
     * Instantiates a new Debt Descriptor.
     */
    public DebtPluginDescriptor() {
        super(DebtPublisher.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>In the DebtPluginDescriptor constructor.");
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return Messages.Debt_Publisher_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getPluginName() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>In DebtPluginDescriptor.getPluginName()");
        return PLUGIN_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getIconUrl() {
        return ACTION_ICON;
    }
}
