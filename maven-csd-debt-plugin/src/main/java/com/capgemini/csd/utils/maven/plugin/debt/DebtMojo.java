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
package com.capgemini.csd.utils.maven.plugin.debt;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.util.FileUtils;

import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoParameter;
import org.jfrog.maven.annomojo.annotations.MojoPhase;

/**
 * Goal which analyses the @Debt annotations in a codebase and creates a report
 * which is (currently) written to file.
 *
 * @author Andrew Harmel-Law
 * @email andrew@harmel-law.com
 */
@MojoGoal("debt")
@MojoPhase("post-compile")
public class DebtMojo extends AbstractMojo {

    private static final String DEBT_PROCESSOR = "com.capgemini.csd.debt.processor.DebtProcessor";
    @MojoParameter(required = true,
    expression = "${debt.scanPackage}",
    description = "Package to scan for @Debt annotations. (Includes sub-packages)")
    private String scanPackage;
    @MojoParameter(required = false,
    defaultValue = "-1",
    expression = "${debt.warningThreshold}",
    description = "Sets the threshold when a warning will be shown about debt levels..")
    private Integer warningThreshold;
    @MojoParameter(required = false,
    defaultValue = "-1",
    expression = "${debt.failThreshold}",
    description = "Sets the threshold when an error will be shown about debt levels and the build failed.")
    private Integer failThreshold;
    @MojoParameter(required = false,
    defaultValue = "${project.build.directory}/site/debt/",
    expression = "${debt.reportDirectory}",
    description = "Directory to output the report. Defaults to target/site/debt.")
    private String reportDirectory;
    @MojoParameter(required = false,
    defaultValue = "debt.xml",
    expression = "${debt.reportFileName}",
    description = "File to write the debt report to. Defaults to debt.xml.")
    private String reportFileName;
    @MojoParameter(required = false,
    defaultValue = "${project.build.sourceDirectory}",
    expression = "${debt.sourceDirectory}",
    description = "Directory containing the source to be analysed. Defaults to src")
    private File sourceDirectory;
    @MojoParameter(required = true,
    defaultValue = "true",
    expression = "${debt.failOnCompileError}",
    description = "Indicates whether the build will continue even if there are compilation errors. Defaults to true.")
    private Boolean failOnCompileError;
    @MojoParameter(required = false,
    readonly = true,
    defaultValue = "${project}")
    private MavenProject project;
    @MojoParameter(required = false,
    readonly = true,
    defaultValue = "${plugin.artifacts}")
    private java.util.List<Artifact> pluginArtifacts;

    /**
     * Runs the debt:debt goal
     */
    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Scanning source for @Debt annotations.");

        try {
            executeWithExceptionsHandled();
        } catch (Exception ex) {
            if (failOnCompileError) {
                throw new MojoExecutionException(ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void executeWithExceptionsHandled() throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        getLog().debug("Got the Java Compiler and Java File Manager.");

        List<File> files = null;
        if (getSourceDirectory().exists()) {
            files = FileUtils.getFiles(getSourceDirectory(), "**/*.java", null);
        } else {
            getLog().warn("No source files detected. @Debt Processor task will be skipped.");
            return;
        }

        getLog().debug("Got all the source files to analyse for @Debt annotations.");
        getLog().debug("Number of files: " + files.size());

        Iterable<? extends JavaFileObject> compilationUnits = null;

        if (files != null && !files.isEmpty()) {
            compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
        } else {
            getLog().warn("No source files detected. @Debt Processor task will be skipped.");
            return;
        }

        String compileClassPath = buildClassPath();

        getLog().debug("Compile classpath: " + compileClassPath);

        String processor = DEBT_PROCESSOR;

        List<String> options = new ArrayList<String>(10);

        options.add("-cp");
        options.add(compileClassPath);
        options.add("-proc:only");

        addCompilerArguments(options);

        if (processor != null) {
            options.add("-processor");
            options.add(processor);
        } else {
            getLog().debug("No @Debt processor found. Using default discovery mechanism.");
        }

        for (String option : options) {
            getLog().debug("javac option: " + option);
        }

        DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<JavaFileObject>();

        CompilationTask task = compiler.getTask(new PrintWriter(System.out), fileManager, dc, options, null, compilationUnits);

        boolean result = false;
        try {
            result = task.call();
        } catch (Exception ex) {
            throw new MojoExecutionException("There was an exception thrown while processing the @Debt annotations.", ex);
        }

        // Process the Diagnostics and fail the build if required
        for (Diagnostic<? extends JavaFileObject> diag : dc.getDiagnostics()) {
            getLog().info(diag.getMessage(null));

            if (!result && diag.getKind().equals(Diagnostic.Kind.ERROR)) {
                throw new MojoExecutionException("Processing the @Debt annotations failed. " + diag.getMessage(null));
            }
        }
    }

    private void addCompilerArguments(List<String> options) {
        options.add("-Acom.capgemini.csd.debt.scan=" + scanPackage);
        getLog().debug("@Debt package for analysis: " + scanPackage);
        options.add("-Acom.capgemini.csd.debt.failthreshold=" + failThreshold);
        getLog().debug("@Debt fail threshold: " + failThreshold);
        options.add("-Acom.capgemini.csd.debt.warnthreshold=" + warningThreshold);
        getLog().debug("@Debt warn threshold: " + warningThreshold);
        options.add("-Acom.capgemini.csd.debt.report.dir=" + reportDirectory);
        getLog().debug("@Debt Report Directory: " + reportDirectory);
        options.add("-Acom.capgemini.csd.debt.report.name=" + reportFileName);
        getLog().debug("@Debt Report File Name: " + reportFileName);
        options.add("-Acom.capgemini.csd.debt.report.enabled=true");
    }

    private String buildClassPath() {
        StringBuilder result = new StringBuilder();

        // Add the <dependencies> from the plugin's POM
        if (pluginArtifacts != null) {
            for (Artifact a : pluginArtifacts) {
                java.io.File f = a.getFile();

                if (f != null) {
                    getLog().debug("File: " + a.getFile().getAbsolutePath());
                    getLog().debug("Scope: " + a.getScope());
                    result.append(File.pathSeparator).append(a.getFile().getAbsolutePath());
                }
            }
        }
        getLog().debug("Plugin dependencies (plugin artifacts): " + result.toString());

        return result.toString();
    }

    private List<String> getProjectResources() {
        java.util.List<String> result = new java.util.ArrayList<String>();

        List<Resource> resources = project.getResources();

        if (resources != null) {
            getLog().debug("Project resources is not null.");

            for (Resource r : resources) {
                result.add(r.getDirectory());
                getLog().debug("Classpath Element: " + r.getDirectory());
            }
        }
        return Collections.unmodifiableList(result);
    }

    private List<Dependency> getPluginDependencies() {
        return project.getDependencies();
    }

    private File getSourceDirectory() {
        return sourceDirectory;
    }
}
