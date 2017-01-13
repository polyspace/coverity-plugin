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

import jenkins.plugins.coverity.CoverityPublisher;
import jenkins.plugins.coverity.InvocationAssistance;
import jenkins.plugins.coverity.TaOptionBlock;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CovAnalyzeCommandTest extends CommandTestBase {

    @Test
    public void CovAnalyzeCommand_MisraConfigurationTest() throws IOException, InterruptedException {
        File misraConfigFile = new File("misraConfigFile");
        try{
            misraConfigFile.createNewFile();

            InvocationAssistance invocationAssistance = new InvocationAssistance(
                    false, StringUtils.EMPTY, false, StringUtils.EMPTY, false, false,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    true, misraConfigFile.getPath(), StringUtils.EMPTY, null, false, false,
                    StringUtils.EMPTY, StringUtils.EMPTY, null, false
            );
            CoverityPublisher publisher = new CoverityPublisher(
                    null, invocationAssistance, false, false, false, false, false,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    null, null, null
            );

            CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
            setExpectedArguments(new String[] {"cov-analyze", "--dir", "TestDir", "--misra-config", misraConfigFile.getPath()});
            covAnalyzeCommand.runCommand();
        }finally {
            misraConfigFile.delete();
        }
    }

    @Test
    public void CovAnalyzeCommand_MisraConfigurationTest_WithEmptyMisraConfigFilePath() throws IOException, InterruptedException {
        InvocationAssistance invocationAssistance = new InvocationAssistance(
                false, StringUtils.EMPTY, false, StringUtils.EMPTY, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                true, StringUtils.EMPTY, StringUtils.EMPTY, null, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, null, false
        );
        CoverityPublisher publisher = new CoverityPublisher(
                null, invocationAssistance, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, null, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        try{
            covAnalyzeCommand.runCommand();
            Assert.fail("RuntimeException should have been thrown");
        }catch(RuntimeException e) {
            assertEquals("Misra configuration file is required to run Misra analysis.", e.getMessage());
        }
    }

    @Test
    public void CovAnalyzeCommand_MisraConfigurationTest_WithInvalidMisraConfigFilePath() throws IOException, InterruptedException {
        File misraConfigFile = new File("misraConfigFile");

        InvocationAssistance invocationAssistance = new InvocationAssistance(
                false, StringUtils.EMPTY, false, StringUtils.EMPTY, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                true, misraConfigFile.getPath(), StringUtils.EMPTY, null, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, null, false
        );
        CoverityPublisher publisher = new CoverityPublisher(
                null, invocationAssistance, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, null, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        try{
            covAnalyzeCommand.runCommand();
            Assert.fail("RuntimeException should have been thrown");
        }catch (RuntimeException e) {
            assertEquals("Could not find MISRA configuration file at \"" + misraConfigFile.getAbsolutePath() + "\"", e.getMessage());
        }
    }

    @Test
    public void CovAnalyzeCommand_AdditionalArgumentsTest() throws IOException, InterruptedException {
        InvocationAssistance invocationAssistance = new InvocationAssistance(
                false, StringUtils.EMPTY, false, StringUtils.EMPTY, false, false,
                StringUtils.EMPTY, "additionalArgs", StringUtils.EMPTY, StringUtils.EMPTY,
                false, StringUtils.EMPTY, StringUtils.EMPTY, null, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, null, false
        );
        CoverityPublisher publisher = new CoverityPublisher(
                null, invocationAssistance, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, null, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        setExpectedArguments(new String[] {"cov-analyze", "--dir", "TestDir", "additionalArgs"});
        covAnalyzeCommand.runCommand();
    }

    @Test
    public void CovAnalyzeCommand_AdditionalArgumentsTest_WithParseException() throws IOException, InterruptedException {
        InvocationAssistance invocationAssistance = new InvocationAssistance(
                false, StringUtils.EMPTY, false, StringUtils.EMPTY, false, false,
                StringUtils.EMPTY, "\'", StringUtils.EMPTY, StringUtils.EMPTY,
                false, StringUtils.EMPTY, StringUtils.EMPTY, null, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, null, false
        );
        CoverityPublisher publisher = new CoverityPublisher(
                null, invocationAssistance, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, null, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        try{
            covAnalyzeCommand.runCommand();
            Assert.fail("RuntimeException should have been thrown");
        }catch(RuntimeException e) {
            assertEquals("ParseException occurred during tokenizing the cov analyze additional arguments.", e.getMessage());
        }
    }

    @Test
    public void CovAnalyzeCommand_TestAdvisorConfigurationTest_WithEmptyStripPath() throws IOException, InterruptedException {
        File taPolicyFile = new File("taPolicyFile");
        try{
            taPolicyFile.createNewFile();

            TaOptionBlock taOptionBlock = new TaOptionBlock(
                    StringUtils.EMPTY, false, false, false,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    false, false, false,
                    taPolicyFile.getPath(), "Path2Strip",
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, false
            );

            CoverityPublisher publisher = new CoverityPublisher(
                    null, null, false, false, false, false, false,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    null, taOptionBlock, null
            );

            CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
            setExpectedArguments(new String[] {"cov-analyze", "--dir", "TestDir", "--test-advisor", "--test-advisor-policy", taPolicyFile.getPath(), "--strip-path", "Path2Strip"});
            covAnalyzeCommand.runCommand();
        }finally {
            taPolicyFile.delete();
        }
    }

    @Test
    public void CovAnalyzeCommand_TestAdvisorConfigurationTest_WithEmptyTaPolicyFilePath() throws IOException, InterruptedException {
        TaOptionBlock taOptionBlock = new TaOptionBlock(
                StringUtils.EMPTY, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, false
        );

        CoverityPublisher publisher = new CoverityPublisher(
                null, null, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, taOptionBlock, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        try{
            covAnalyzeCommand.runCommand();
            Assert.fail("RuntimeException should have been thrown");
        }catch(RuntimeException e) {
            assertEquals("Test Advisor Policy File is required to run the Test Advisor.", e.getMessage());
        }
    }

    @Test
    public void CovAnalyzeCommand_TestAdvisorConfigurationTest_WithInvalidTaPolicyFilePath() throws IOException, InterruptedException {
        File taPolicyFile = new File("taPolicyFile");

        TaOptionBlock taOptionBlock = new TaOptionBlock(
                StringUtils.EMPTY, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                false, false, false,
                taPolicyFile.getPath(), "Path2Strip",
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, false
        );

        CoverityPublisher publisher = new CoverityPublisher(
                null, null, false, false, false, false, false,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                null, taOptionBlock, null
        );

        CovCommand covAnalyzeCommand = new CovAnalyzeCommand(build, launcher, listener, publisher, StringUtils.EMPTY, envVars);
        try{
            covAnalyzeCommand.runCommand();
            Assert.fail("RuntimeException should have been thrown");
        }catch(RuntimeException e) {
            assertEquals("Could not find test policy file at \"" + taPolicyFile.getAbsolutePath() + "\"", e.getMessage());
        }
    }
}
