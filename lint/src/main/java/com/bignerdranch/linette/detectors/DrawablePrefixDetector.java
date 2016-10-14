package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
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
import static com.android.SdkConstants.DOT_XML;
import static com.android.SdkConstants.DRAWABLE_FOLDER;
import static com.android.SdkConstants.DOT_PNG;
import static com.android.tools.lint.detector.api.LintUtils.endsWith;

/**
 * Lint check for drawable prefixes.
 */

public class DrawablePrefixDetector extends ResourceXmlDetector implements Detector.JavaScanner {

    private static final String ACTION_BAR = "ab";
    private static final String BUTTON = "btn";
    private static final String DIALOG = "dialog";
    private static final String DIVIDER = "divider";
    private static final String ICON = "ic";
    private static final String MENU = "menu";
    private static final String NOTIFICATION = "notification";
    private static final String TABS = "tab";

    private static final Class<? extends ResourceXmlDetector> DETECTOR_CLASS = DrawablePrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.OTHER_SCOPE;

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
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return folderType == ResourceFolderType.MENU;
    }

    @Override
    public void afterCheckProject(@NonNull Context context) {
        // super.afterCheckProject(context);

        checkResourceFolder(context, context.getProject());
    }


    private void checkResourceFolder(Context context, @NonNull Project project) {

        File[] folders = new File[]{};
        File[] files = new File[]{};

        List<File> resourceFolders = project.getResourceFolders();
        for (File res : resourceFolders) {
            folders = res.listFiles();
        }
        for (File folder : folders) {
            String folderName = folder.getName();
            if (folderName.startsWith(DRAWABLE_FOLDER)) {
                files = folder.listFiles();
            }
            checkDrawableDir(context, files);

        }

    }

    private void checkDrawableDir(Context context, File[] files) {

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                //noinspection StatementWithEmptyBody
                if (name.endsWith(DOT_XML)) {
                    // pass - most common case, avoids checking other extensions
                } else if (endsWith(name, DOT_PNG)
                        || endsWith(name, DOT_JPG)
                        || endsWith(name, DOT_JPEG)) {

                    if (!(name.startsWith(ACTION_BAR)) || !(name.startsWith(BUTTON))
                            || !(name.startsWith(DIALOG))
                            || !(name.startsWith(DIVIDER))
                            || !(name.startsWith(ICON))
                            || !(name.startsWith(MENU))
                            || !(name.startsWith(NOTIFICATION))
                            || !(name.startsWith(TABS))) {

                        context.report(ISSUE,
                                Location.create(file),
                                ISSUE_DESCRIPTION);

                    }
                }
            }
        }
    }
}




