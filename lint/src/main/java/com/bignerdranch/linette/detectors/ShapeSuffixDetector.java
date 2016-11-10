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

import static com.android.SdkConstants.DOT_JPEG;
import static com.android.SdkConstants.DOT_JPG;
import static com.android.SdkConstants.DRAWABLE_FOLDER;
import static com.android.SdkConstants.DOT_PNG;
import static com.android.tools.lint.detector.api.LintUtils.endsWith;

/**
 * Lint check for shape suffixes.
 */

public class ShapeSuffixDetector extends ResourceXmlDetector implements Detector.JavaScanner {

    private static final String SHAPE = "shape_";

    private static final String NORMAL = "_normal.xml";
    private static final String PRESSED = "_pressed.xml";
    private static final String FOCUSED = "_focused.xml";
    private static final String DISABLED = "_disabled.xml";
    private static final String SELECTED = "_selected.xml";
    private static final String DESELECTED = "_deselected.xml";

    private static final String CIRCLE = "circle.xml";
    private static final String RECTANGLE = "rectangle.xml";
    private static final String CORNERS = "corners.xml";

    private static final String GRADIENT = "gradient.xml";


    private static final Class<? extends Detector> DETECTOR_CLASS = ShapeSuffixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.ALL_RESOURCES_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "ShapeSuffix";
    private static final String ISSUE_DESCRIPTION = "Incorrect or missing naming of shape resource suffix";
    private static final String ISSUE_EXPLANATION = "Shapes should be suffixed with there shape: circle, rectangle, corners, or their state: normal, pressed, focused, disabled, selected";
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
     * Constructs a new {@link ShapeSuffixDetector} check
     */
    public ShapeSuffixDetector() {
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
                    if (folderName.startsWith(DRAWABLE_FOLDER)) {
                        File[] files = folder.listFiles();
                        if (files != null) {
                            for (File f : files) {
                                String name = f.getName();
                                if (isShapeFile(name)) {
                                    if (!hasState(name)) {
                                        //  if (name.endsWith("pressed.xml")) {
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
    }

    private boolean isShapeFile(String name) {
        return name.startsWith(SHAPE);
    }

    private boolean hasState(String name) {
        return name.endsWith(NORMAL)
                || name.endsWith(PRESSED)
                || name.endsWith(FOCUSED)
                || name.endsWith(DISABLED)
                || name.endsWith(SELECTED)
                || name.endsWith(DESELECTED)
                || name.endsWith(CIRCLE)
                || name.endsWith(RECTANGLE)
                || name.endsWith(CORNERS)
                || name.endsWith(GRADIENT);
    }

}











