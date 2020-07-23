package com.github.hirakida.mojo;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "hello", threadSafe = true, defaultPhase = LifecyclePhase.COMPILE)
public class HelloPlugin extends AbstractMojo {
    @Parameter(property = "hello.message", defaultValue = "Hello!")
    private String message;
    @Parameter(property = "basedir", required = true)
    private File basedir;
    @Parameter(property = "project.build.directory", required = true)
    private File buildDirectory;
    @Parameter(property = "project.build.outputDirectory", required = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        log.info("message: " + message);
        if (log.isDebugEnabled()) {
            log.debug("basedir: " + basedir.getAbsolutePath());
            log.debug("buildDirectory: " + buildDirectory.getAbsolutePath());
            log.debug("outputDirectory: " + outputDirectory.getAbsolutePath());
        }
    }
}
