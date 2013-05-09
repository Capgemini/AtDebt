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

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Launcher;
import hudson.matrix.MatrixAggregator;
import hudson.matrix.MatrixBuild;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.FilesParser;
import hudson.plugins.analysis.core.HealthAwarePublisher;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;

import hudson.plugins.debt.parser.DebtParser;

/**
 * Publishes the results of the Debt analysis  (freestyle project type).
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class DebtPublisher extends HealthAwarePublisher {

    /** Unique ID of this class. */
    private static final long serialVersionUID = 5365438765329874L;
    /** Default Debt Report pattern. */
    private static final String DEFAULT_PATTERN = "**/debt.xml";
    /** Ant file-set pattern of files to work with. */
    private final String pattern;

    /**
     * Creates a new instance of <code>DebtPublisher</code>.
     *
     * @param healthy Report health as 100% when the number of warnings is less
     * than this value
     * @param unHealthy Report health as 0% when the number of warnings is
     * greater than this value
     * @param thresholdLimit determines which warning priorities should be
     * considered when evaluating the build stability and health
     * @param defaultEncoding the default encoding to be used when reading and
     * parsing files
     * @param useDeltaValues determines whether the absolute annotations delta 
     * or the actual annotations set difference should be used to evaluate the
     * build stability
     * @param unstableTotalAll annotation threshold
     * @param unstableTotalHigh annotation threshold
     * @param unstableTotalNormal annotation threshold
     * @param unstableTotalLow annotation threshold
     * @param unstableNewAll annotation threshold
     * @param unstableNewHigh annotation threshold
     * @param unstableNewNormal annotation threshold
     * @param unstableNewLow annotation threshold
     * @param failedTotalAll annotation threshold
     * @param failedTotalHigh annotation threshold
     * @param failedTotalNormal annotation threshold
     * @param failedTotalLow annotation threshold
     * @param failedNewAll annotation threshold
     * @param failedNewHigh annotation threshold
     * @param failedNewNormal annotation threshold
     * @param failedNewLow annotation threshold
     * @param canRunOnFailed determines whether the plug-in can run for failed
     * builds, too
     * @param pattern Ant file-set pattern to scan for Debt Report files
     */
    @DataBoundConstructor
    public DebtPublisher(final String healthy, final String unHealthy, final String thresholdLimit,
            final String defaultEncoding, final boolean useDeltaValues,
            final String unstableTotalAll, final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
            final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
            final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal, final String failedTotalLow,
            final String failedNewAll, final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
            final boolean canRunOnFailed,
            final String pattern) {
        super(healthy, unHealthy, "HIGH", defaultEncoding, useDeltaValues,
                unstableTotalAll, unstableTotalHigh, unstableTotalNormal, unstableTotalLow,
                unstableNewAll, unstableNewHigh, unstableNewNormal, unstableNewLow,
                failedTotalAll, failedTotalHigh, failedTotalNormal, failedTotalLow,
                failedNewAll, failedNewHigh, failedNewNormal, failedNewLow,
                canRunOnFailed, "Debt");
        if (pattern == null) {
            this.pattern = DEFAULT_PATTERN;
        }
        else {
            this.pattern = pattern;
        }
    }

    /**
     * Returns the Ant file-set pattern of files to work with.
     *
     * @return Ant file-set pattern of files to work with
     */
    public String getPattern() {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Request for the Ant File Pattern: " + pattern);

        return pattern;
    }

    /** {@inheritDoc} */
    @Override
    public Action getProjectAction(final AbstractProject<?, ?> project) {
        return new DebtProjectAction(project);
    }

    /** {@inheritDoc} */
    @Override
    public BuildResult perform(final AbstractBuild<?, ?> build, final PluginLogger logger) throws InterruptedException, IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Collecting Debt analysis files...");
        logger.log("Collecting Debt analysis files...");

        FilesParser debtCollector = new FilesParser(logger, StringUtils.defaultIfEmpty(getPattern(), DEFAULT_PATTERN), new DebtParser(getDefaultEncoding()),
                isMavenBuild(build), isAntBuild(build));

        ParserResult project = build.getWorkspace().act(debtCollector);
        DebtResult result = new DebtResult(build, getDefaultEncoding(), project);
        build.getActions().add(new DebtResultAction(build, this, result));

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public DebtPluginDescriptor getDescriptor() {
        return (DebtPluginDescriptor) super.getDescriptor();
    }

    /** {@inheritDoc} */
    public MatrixAggregator createAggregator(final MatrixBuild build, final Launcher launcher,
            final BuildListener listener) {
        return new DebtAnnotationsAggregator(build, launcher, listener, this, getDefaultEncoding());
    }
}
