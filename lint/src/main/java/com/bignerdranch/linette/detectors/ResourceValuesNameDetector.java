package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Project;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;


import java.io.File;
import java.util.EnumSet;
import java.util.List;

/**
 * Lint check for naming convetion in values folder.
 */

public class ResourceValuesNameDetector extends ResourceXmlDetector implements Detector.JavaScanner {

    private static final String STRINGS = "strings.";
    private static final String STYLES = "styles.";
    private static final String COLORS = "colors.";
    private static final String DIMENS = "dimens.";
    private static final String ATTRS = "attrs.";
    private static final String CRASHLYTICS = "com_crashlytics_build_id.xml";

    private static final Class<? extends Detector> DETECTOR_CLASS = ResourceValuesNameDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.ALL_RESOURCES_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "ResourceValuesName";
    private static final String ISSUE_DESCRIPTION = "Resource files in the values folder should be plural";
    private static final String ISSUE_EXPLANATION = "string.xml should be strings.xml, style.xml should be styles.xml, color.xml should be colors.xml, dime.xml should be dimens.xml, attr.xml should be attrs.xml";
    private static final Category ISSUE_CATEGORY = Category.TYPOGRAPHY;
    private static final int ISSUE_PRIORITY = 8;
    private static final Severity ISSUE_SEVERITY = Severity.ERROR;

    public static final Issue ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_DESCRIPTION,
            ISSUE_EXPLANATION,
            ISSUE_CATEGORY,
            ISSUE_PRIORITY,
            ISSUE_SEVERITY,
            IMPLEMENTATION
    );

    /**
     * Constructs a new {@link ResourceValuesNameDetector} check
     */
    public ResourceValuesNameDetector() {
    }

    @Override
    public void afterCheckLibraryProject(@NonNull Context context) {
        if (!context.getProject().getReportIssues()) {
            // If this is a library project not being analyzed, ignore it
            return;
        }
        checkResourceFolder(context, context.getProject());
    }

    @Override
    public void afterCheckProject(@NonNull Context context) {
        checkResourceFolder(context, context.getProject());
    }

    private void checkResourceFolder(Context context, @NonNull Project project) {
        List<File> resourceFolders = project.getResourceFolders();
        for (File res : resourceFolders) {
            File[] folders = res.listFiles();
            if (folders != null) {

                for (File folder : folders) {
                    String folderName = folder.getName();
                    if (folderName.startsWith("values")) {
                        File[] files = folder.listFiles();
                        if (files != null) {
                            for (File f : files) {
                                String name = f.getName();
                                if (!isNameCorrect(name)) {

                                    context.report(ISSUE,
                                            Location.create(f),
                                            ISSUE_DESCRIPTION);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isNameCorrect(String name) {

        return name.startsWith(STRINGS) || name.startsWith(STYLES)
                || name.startsWith(COLORS) || name.startsWith(DIMENS)
                || name.startsWith(ATTRS) || name.startsWith(CRASHLYTICS);
    }

}











