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
import java.util.Collections;
import java.util.List;

import org.apache.maven.project.MavenProject;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.maven.MavenBuildProxy;
import hudson.maven.MojoInfo;
import hudson.maven.MavenBuild;
import hudson.maven.MavenModule;
import hudson.model.Action;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.FilesParser;
import hudson.plugins.analysis.core.HealthAwareMavenReporter;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;

import hudson.plugins.debt.parser.DebtParser;

/**
 * Publishes the results of the Debt analysis  (maven 2 project type).
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class DebtReporter extends HealthAwareMavenReporter {

    /** Unique identifier of this class. */
    private static final long serialVersionUID = 2272875032054063321L;

    /** Default Debt pattern. */
    private static final String DEBT_XML_FILE = "target/site/debt/debt.xml";

    /** Ant file-set pattern of files to work with. */
    @SuppressWarnings("unused")
    private String pattern; // obsolete since release 2.5

    /**
     * Creates a new instance of <code>DebtReporter</code>.
     *
     * @param threshold Annotation threshold to be reached if a build should be
     * considered as unstable.
     * @param newThreshold New annotations threshold to be reached if a build
     * should be considered unstable.
     * @param failureThreshold Annotation threshold to be reached if a build 
     * should be considered a failure.
     * @param newFailureThreshold New annotations threshold to be reached if a
     * build should be considered a failure.
     * @param healthy Report health as 100% when the number of warnings is less
     * than this value
     * @param unHealthy Report health as 0% when the number of warnings is
     * greater than this value
     * @param thresholdLimit determines which warning priorities should be
     * considered when evaluating the build stability and health
     * @param canRunOnFailed determines whether the plug-in can run for failed
     * builds, too
     */
    @DataBoundConstructor
    public DebtReporter(final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy, final String thresholdLimit, final boolean canRunOnFailed) {
        super(threshold, newThreshold, failureThreshold, newFailureThreshold,
                healthy, unHealthy, thresholdLimit, canRunOnFailed, "debt");
    }

    /** {@inheritDoc} */
    @Override
    protected boolean acceptGoal(final String goal) {
        // TODO: the "site" element of this may not work at the moment...
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>In DebtReporter.acceptGoal() - returning " + ("debt".equals(goal) || "site".equals(goal)));
        return "debt".equals(goal) || "site".equals(goal);
    }

    /** {@inheritDoc} */
    @Override
    public ParserResult perform(final MavenBuildProxy build, final MavenProject pom,
            final MojoInfo mojo, final PluginLogger logger) throws InterruptedException, IOException {
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>About to create a FilesParser");
        
        FilesParser debtCollector = new FilesParser(logger, DEBT_XML_FILE,
                new DebtParser(getDefaultEncoding()), getModuleName(pom));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Created a FilesParser");
        
        return getTargetPath(pom).act(debtCollector);
    }

    /** {@inheritDoc} */
    @Override
    protected BuildResult persistResult(final ParserResult project, final MavenBuild build) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Entered DebtReporter.persistResult()");

        DebtResult result = new DebtResult(build, getDefaultEncoding(), project);
        build.getActions().add(new MavenDebtResultAction(build, this, getDefaultEncoding(), result));
        build.registerAsProjectAction(DebtReporter.this);

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public List<DebtProjectAction> getProjectActions(final MavenModule module) {
        return Collections.singletonList(new DebtProjectAction(module));
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends Action> getResultActionClass() {
        return MavenDebtResultAction.class;
    }
}

