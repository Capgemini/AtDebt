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

import hudson.model.AbstractProject;
import hudson.plugins.Messages;
import hudson.plugins.analysis.core.AbstractProjectAction;

/**
 * Entry point to visualize the DEbt trend graph in the project screen.
 * Drawing of the graph is delegated to the associated
 * {@link DebtResultAction}.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class DebtProjectAction extends AbstractProjectAction<DebtResultAction> {

    /**
     * Instantiates a new Debt project action.
     *
     * @param project
     *            the project that owns this action
     */
    public DebtProjectAction(final AbstractProject<?, ?> project) {
        super(project, DebtResultAction.class, new DebtPluginDescriptor());

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>In the DebtProjectAction constructor");
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Debt_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getTrendName() {
        return Messages.Debt_Trend_Name();
    }
}