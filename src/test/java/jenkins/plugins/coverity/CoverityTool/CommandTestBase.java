/*******************************************************************************
 * Copyright (c) 2017 Synopsys, Inc
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Synopsys, Inc - initial implementation and documentation
 *******************************************************************************/
package jenkins.plugins.coverity.CoverityTool;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import jenkins.plugins.coverity.CoverityTempDir;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;

public abstract class CommandTestBase {

    protected AbstractBuild build;
    protected Launcher launcher;
    protected BuildListener buildListener;
    protected IMocksControl mocker;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() throws IOException, InterruptedException {
        mocker = EasyMock.createNiceControl();
        build = mocker.createMock(AbstractBuild.class);
        launcher = mocker.createMock(Launcher.class);
        buildListener = mocker.createMock(BuildListener.class);

        File temp = new File("TestDir");
        FilePath filePath = new FilePath(temp);
        CoverityTempDir tempDir = new CoverityTempDir(filePath, false);

        EnvVars envVars = new EnvVars();
        envVars.put("COV_IDIR", "TestDir");

//        expect(build.getAction(CoverityTempDir.class)).andReturn(tempDir);
        expect(build.getEnvironment(buildListener)).andReturn(envVars);
    }

    @After
    public void teardown() {
        expectedException = ExpectedException.none();
        mocker.reset();
    }

    public static void checkCommandLineArg(List<String> argList, String arg){
        assertTrue(argList.contains(arg));
        argList.remove(arg);
    }
}
