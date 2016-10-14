package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.ddmlib.Log;
import com.android.resources.ResourceFolderType;
import com.android.resources.ResourceType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;


import org.w3c.dom.Document;

import java.io.File;
import java.util.EnumSet;

/**
 * Lint check for menu prefixes.
 */

public class DrawablePrefixDetector extends Detector implements Detector.OtherFileScanner {

    private static final String ACTION_BAR = "ab";
    private static final String BUTTON = "btn";
    private static final String DIALOG = "dialog";
    private static final String DIVIDER = "divider";
    private static final String ICON = "ic";
    private static final String MENU = "menu";
    private static final String NOTIFICATION = "notification";
    private static final String TABS = "tab";

    private static final Class<? extends Detector> DETECTOR_CLASS = DrawablePrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "DrawablePrefix";
    private static final String ISSUE_DESCRIPTION = "Incorrect or missing naming of drawable resource prefix";
    private static final String ISSUE_EXPLANATION = "Drawable should be prefixed with ab for ACTION_BAR, btn for BUTTON, dialog for DIALOG, divider for DIVIDER, ic for ICON, menu for MENU, notification for NOTIFICATION, tab for TABS\";\n";
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
     * Constructs a new {@link MenuNameDetector} check
     */
    public DrawablePrefixDetector() {
    }

    @Override
    public EnumSet<Scope> getApplicableFiles() {
        return Scope.ALL;
    }


    @Override
    public void run(@NonNull Context context) {
        // super.run(context);

        File file = context.file;

        context.report(ISSUE,
                Location.create(file),
                ISSUE_DESCRIPTION);


        if (LintUtils.endsWith(file.getPath(), "png") || LintUtils.endsWith(file.getPath(), "jpg")) {

            String fileName = file.getName();

            if (!(fileName.startsWith(ACTION_BAR) || fileName.startsWith(BUTTON)
                    || fileName.startsWith(DIALOG)
                    || fileName.startsWith(DIVIDER)
                    || fileName.startsWith(ICON)
                    || fileName.startsWith(MENU)
                    || fileName.startsWith(NOTIFICATION)
                    || fileName.startsWith(TABS))) {

            }


        }


    }



}
