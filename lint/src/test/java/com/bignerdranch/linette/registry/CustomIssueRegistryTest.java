//package com.bignerdranch.linette.registry;
//
//import com.android.tools.lint.detector.api.Issue;
//import com.bignerdranch.linette.detectors.DrawablePrefixDetector;
//import com.bignerdranch.linette.detectors.EnumDetector;
//import com.bignerdranch.linette.detectors.LayoutIdPrefixDetector;
//import com.bignerdranch.linette.detectors.LayoutPrefixDetector;
//import com.bignerdranch.linette.detectors.MenuIdPrefixDetector;
//import com.bignerdranch.linette.detectors.MenuNameDetector;
//import com.bignerdranch.linette.detectors.MenuPrefixDetector;
//import com.bignerdranch.linette.detectors.MinSdkDetector;
//import com.bignerdranch.linette.detectors.ResourceValuesNameDetector;
//import com.bignerdranch.linette.detectors.ShapeSuffixDetector;
//import com.bignerdranch.linette.detectors.TodoDetector;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * Test the {@link CustomIssueRegistry}
// */
//public class CustomIssueRegistryTest {
//
//    private CustomIssueRegistry mCustomIssueRegistry;
//
//    /**
//     * Setup for the other test methods
//     */
//    @Before
//    public void setUp() throws Exception {
//        mCustomIssueRegistry = new CustomIssueRegistry();
//    }
//
//    /**
//     * Test that the issue registry contains the correct number of issues
//     */
//    @Test
//    public void testNumberOfIssues() throws Exception {
//        int size = mCustomIssueRegistry.getIssues().size();
//        assertThat(size).isEqualTo(11);
//    }
//
//    /**
//     * Test that the issue registry contains the correct issues
//     */
//    @Test
//    public void testGetIssues() throws Exception {
//        List<Issue> actual = mCustomIssueRegistry.getIssues();
//        assertThat(actual).contains(EnumDetector.ISSUE);
//        assertThat(actual).contains(MinSdkDetector.ISSUE);
//        assertThat(actual).contains(TodoDetector.ISSUE);
//        assertThat(actual).contains(LayoutIdPrefixDetector.ISSUE);
//        assertThat(actual).contains(MenuNameDetector.ISSUE);
//        assertThat(actual).contains(MenuPrefixDetector.ISSUE);
//        assertThat(actual).contains(MenuIdPrefixDetector.ISSUE);
//        assertThat(actual).contains(DrawablePrefixDetector.ISSUE);
//        assertThat(actual).contains(LayoutPrefixDetector.ISSUE);
//        assertThat(actual).contains(ShapeSuffixDetector.ISSUE);
//        assertThat(actual).contains(ResourceValuesNameDetector.ISSUE);
//
//    }
//
//}
