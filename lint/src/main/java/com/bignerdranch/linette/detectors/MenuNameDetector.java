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
 * Lint check for menu naming.
 */
public class MenuNameDetector extends ResourceXmlDetector {

    private static final Class<? extends ResourceXmlDetector> DETECTOR_CLASS = MenuNameDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "MenuNaming";
    private static final String ISSUE_DESCRIPTION = "Incorrect naming of menu resource";
    private static final String ISSUE_EXPLANATION = "Menu resources are already located in the menu directory and therefore do not need to contain the word menu.";
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    private static final int ISSUE_PRIORITY = 8;
    private static final Severity ISSUE_SEVERITY = Severity.WARNING;

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
    public MenuNameDetector() {
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

        if (document != null && fileName.contains("menu")) {

            context.report(ISSUE,
                    document,
                    context.getLocation(document),
                    ISSUE_DESCRIPTION);
        }
    }
}