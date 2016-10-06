package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Attr;

import java.util.Collection;
import java.util.EnumSet;

/**
 * Created by kyle.roe on 06/10/16.
 */

public class MenuIdPrefixDetector extends LayoutDetector {

    private static final String MENU = "menu";

    private static final Class<? extends LayoutDetector> DETECTOR_CLASS = MenuIdPrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "MenuIdPrefix";
    private static final String ISSUE_DESCRIPTION = "Missing menu id prefix";
    private static final String ISSUE_EXPLANATION = "Menu item ids should be prefixed with menu";
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
     * Constructs a new {@link LayoutIdPrefixDetector} check
     */
    public MenuIdPrefixDetector() {
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return ResourceFolderType.MENU == folderType;
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        return ALL;
    }

    @Override
    public void visitAttribute(@NonNull XmlContext context, @NonNull Attr attribute) {

        if (attribute != null) {

            String value = attribute.getValue();
            value = value.replace("@+id/", "");

            String prefix = attribute.getOwnerElement().getTagName();

            if (attribute.getName().contains("android:id")) {

                if (!value.startsWith(MENU)) {
                    context.report(ISSUE,
                            attribute,
                            context.getLocation(attribute),
                            ISSUE_DESCRIPTION);
                }
            }
        }
    }
}
