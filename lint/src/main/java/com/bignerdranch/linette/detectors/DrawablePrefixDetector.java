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
 * Lint check for drawable prefixes.
 */

public class DrawablePrefixDetector extends ResourceXmlDetector implements Detector.JavaScanner {

    private static final String ACTION_BAR = "ab_";
    private static final String BUTTON = "btn_";
    private static final String DIALOG = "dialog_";
    private static final String DIVIDER = "divider_";
    private static final String ICON = "ic_";
    private static final String MENU = "menu_";
    private static final String NOTIFICATION = "notification_";
    private static final String TABS = "tab_";

    private static final Class<? extends Detector> DETECTOR_CLASS = DrawablePrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.ALL_RESOURCES_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "DrawablePrefix";
    private static final String ISSUE_DESCRIPTION = "Incorrect or missing naming of drawable resource prefix";
    private static final String ISSUE_EXPLANATION = "Drawable should be prefixed with ab for ACTION_BAR, btn for BUTTON, dialog for DIALOG, divider for DIVIDER, ic for ICON, menu for MENU, notification for NOTIFICATION and tab for TABS\";\n";
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
     * Constructs a new {@link DrawablePrefixDetector} check
     */
    public DrawablePrefixDetector() {
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
                                if (isDrawableFile(name)) {
                                    if (!isCorrectName(name)) {

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

    private boolean isDrawableFile(String name) {
        return endsWith(name, DOT_PNG) || endsWith(name, DOT_JPG)
                || endsWith(name, DOT_JPEG);
    }

    private boolean isCorrectName(String name) {

        return name.startsWith(ACTION_BAR) || name.startsWith(BUTTON)
                || name.startsWith(DIALOG) || name.startsWith(DIVIDER)
                || name.startsWith(ICON) || name.startsWith(MENU)
                || name.startsWith(NOTIFICATION) || name.startsWith(TABS);
    }

}











