package com.bignerdranch.linette.detectors;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.bignerdranch.linette.AbstractDetectorTest;

import java.util.Arrays;
import java.util.List;


import static com.android.SdkConstants.DOT_JAVA;

public class EnumDetectorTest extends AbstractDetectorTest {

    @Override
    protected Detector getDetector() {
        return new EnumDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Arrays.asList(EnumDetector.ISSUE);
    }

    /**
     * Test that an empty Java file has no warnings.
     */
    public void testEmptyCase() throws Exception {
        String expected = "No warnings.";
        String result = lintProject(java(DOT_JAVA, ""));
        assertEquals(expected, result);
    }

    /**
     * Test that an enum Java file has a warning.
     */
    public void testEnumCase() throws Exception {
        String expected = "EnumDetectorTest_testEnumCase: Warning: Avoid Using Enums [EnumDetector]\n0 errors, 1 warnings\n";
        String result = lintProject(
                java(DOT_JAVA,
                     String.format("package com.example.lint; public enum Pet { CAT, DOG, TURTLE }")
                )
        );
        assertEquals(expected, result);
    }

}
