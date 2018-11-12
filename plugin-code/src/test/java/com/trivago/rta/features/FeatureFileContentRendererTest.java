package com.trivago.rta.features;

import com.trivago.rta.vo.DataTable;
import com.trivago.rta.vo.SingleScenario;
import com.trivago.rta.vo.Step;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeatureFileContentRendererTest {

    private FeatureFileContentRenderer featureFileContentRenderer;

    @Before
    public void setup() {
        featureFileContentRenderer = new FeatureFileContentRenderer();
    }

    @Test
    public void getRenderedFeatureFileContentTest() {
        String expectedOutput = "@featureTag1\n" +
                "@featureTag2\n" +
                "Feature: featureName\n" +
                "featureDescription\n" +
                "\n" +
                "@scenarioTag1\n" +
                "@scenarioTag2\n" +
                "Scenario: scenarioName\n" +
                "scenarioDescription\n" +
                "Step 1\n" +
                "Step 2\n" +
                "\n# Source feature: TESTPATH\n" +
                "# Generated by Cucable\n";

        String featureName = "Feature: featureName";
        String featureDescription = "featureDescription";
        String featureLanguage = "";
        List<String> featureTags = Arrays.asList("@featureTag1", "@featureTag2");
        String scenarioName = "Scenario: scenarioName";
        String scenarioDescription = "scenarioDescription";
        List<Step> backgroundSteps = Arrays.asList(
                new Step("Step 1", null, null),
                new Step("Step 2", null, null)
        );
        List<String> scenarioTags = Arrays.asList("@scenarioTag1", "@scenarioTag2");

        String featureFilePath = "TESTPATH";
        SingleScenario singleScenario = new SingleScenario(featureName, featureFilePath, featureLanguage, featureDescription, scenarioName, scenarioDescription, featureTags, backgroundSteps);
        singleScenario.setScenarioTags(scenarioTags);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }

    @Test
    public void getRenderedFeatureFileContentNonEnglishTest() {
        String expectedOutput = "# language: de\n\n" +
                "@featureTag1\n" +
                "@featureTag2\n" +
                "Feature: featureName\n" +
                "featureDescription\n" +
                "\n" +
                "@scenarioTag1\n" +
                "@scenarioTag2\n" +
                "Scenario: scenarioName\n" +
                "scenarioDescription\n" +
                "Step 1\n" +
                "Step 2\n" +
                "\n# Source feature: TESTPATH\n" +
                "# Generated by Cucable\n";

        String featureName = "Feature: featureName";
        String featureDescription = "featureDescription";
        String featureLanguage = "de";
        List<String> featureTags = Arrays.asList("@featureTag1", "@featureTag2");
        String scenarioName = "Scenario: scenarioName";
        String scenarioDescription = "scenarioDescription";
        List<Step> backgroundSteps = Arrays.asList(
                new Step("Step 1", null, null),
                new Step("Step 2", null, null)
        );
        List<String> scenarioTags = Arrays.asList("@scenarioTag1", "@scenarioTag2");

        String featureFilePath = "TESTPATH";
        SingleScenario singleScenario = new SingleScenario(featureName, featureFilePath, featureLanguage, featureDescription, scenarioName, scenarioDescription, featureTags, backgroundSteps);
        singleScenario.setScenarioTags(scenarioTags);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }

    @Test
    public void formatDataTableStringTest() {
        String expectedOutput = "Feature: featureName\n" +
                "featureDescription\n" +
                "\n" +
                "Scenario: scenarioName\n" +
                "scenarioDescription\n" +
                "Step 1\n" +
                "|cell11|cell12|cell13|\n" +
                "|cell21|cell22|cell23|\n" +
                "\n# Source feature: TESTPATH\n" +
                "# Generated by Cucable\n";

        String featureName = "Feature: featureName";
        String featureLanguage = "";
        String featureDescription = "featureDescription";
        String scenarioName = "Scenario: scenarioName";
        String scenarioDescription = "scenarioDescription";

        DataTable dataTable = new DataTable();
        dataTable.addRow(Arrays.asList("cell11", "cell12", "cell13"));
        dataTable.addRow(Arrays.asList("cell21", "cell22", "cell23"));

        List<Step> steps = Collections.singletonList(new Step("Step 1", dataTable, null));

        String featureFilePath = "TESTPATH";
        SingleScenario singleScenario = new SingleScenario(featureName, featureFilePath, featureLanguage, featureDescription, scenarioName, scenarioDescription, new ArrayList<>(), new ArrayList<>());
        singleScenario.setSteps(steps);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }

    @Test
    public void formatDocStringTest() {
        String expectedOutput = "Feature: featureName\n" +
                "\n" +
                "Scenario: scenarioName\n" +
                "Step 1\n" +
                "\"\"\"\n" +
                "DOCSTRING LINE 1\n" +
                "DOCSTRING LINE 2\n" +
                "\"\"\"\n" +
                "\n# Source feature: TESTPATH\n" +
                "# Generated by Cucable\n";

        String featureName = "Feature: featureName";
        String scenarioName = "Scenario: scenarioName";

        List<Step> steps = Collections.singletonList(new Step("Step 1", null, "DOCSTRING LINE 1\nDOCSTRING LINE 2"));

        String featureFilePath = "TESTPATH";
        SingleScenario singleScenario = new SingleScenario(featureName, featureFilePath, null, null, scenarioName, null, new ArrayList<>(), new ArrayList<>());
        singleScenario.setSteps(steps);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }

    @Test
    public void getRenderedFeatureFileContentReplaceBackslashesInCommentsTest() {
        String expectedOutput = "# language: de\n\n" +
                "@featureTag1\n" +
                "@featureTag2\n" +
                "Feature: featureName\n" +
                "featureDescription\n" +
                "\n" +
                "@scenarioTag1\n" +
                "@scenarioTag2\n" +
                "Scenario: scenarioName\n" +
                "scenarioDescription\n" +
                "Step 1\n" +
                "Step 2\n" +
                "\n# Source feature: c:/unknown/path\n" +
                "# Generated by Cucable\n";

        String featureName = "Feature: featureName";
        String featureDescription = "featureDescription";
        String featureLanguage = "de";
        List<String> featureTags = Arrays.asList("@featureTag1", "@featureTag2");
        String scenarioName = "Scenario: scenarioName";
        String scenarioDescription = "scenarioDescription";
        List<Step> backgroundSteps = Arrays.asList(
                new Step("Step 1", null, null),
                new Step("Step 2", null, null)
        );
        List<String> scenarioTags = Arrays.asList("@scenarioTag1", "@scenarioTag2");

        String featureFilePath = "c:\\unknown\\path";
        SingleScenario singleScenario = new SingleScenario(featureName, featureFilePath, featureLanguage, featureDescription, scenarioName, scenarioDescription, featureTags, backgroundSteps);
        singleScenario.setScenarioTags(scenarioTags);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }
}