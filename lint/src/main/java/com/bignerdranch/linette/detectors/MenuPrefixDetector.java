package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LintUtils;
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

public class MenuPrefixDetector extends ResourceXmlDetector {

    private static final Class<? extends ResourceXmlDetector> DETECTOR_CLASS = MenuPrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "MenuPrefix";
    private static final String ISSUE_DESCRIPTION = "Missing menu resource prefix";
    private static final String ISSUE_EXPLANATION = "Menu resource should be prefixed with activity or fragment, based on where the menu is called";
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
    public MenuPrefixDetector() {
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return ResourceFolderType.MENU == folderType;
    }

    @Override
    public boolean appliesTo(@NonNull Context context, @NonNull File file) {
        return LintUtils.isXmlFile(file);
    }

    @Override
    public void visitDocument(@NonNull XmlContext context, @NonNull Document document) {

        File file = context.file;
        String fileName = file.getName();

        if (document != null) {

            if (!(fileName.startsWith("activity") || fileName.startsWith("fragment"))) {
                context.report(ISSUE,
                        document,
                        context.getLocation(document),
                        ISSUE_DESCRIPTION);
            }

        }

    }

}
