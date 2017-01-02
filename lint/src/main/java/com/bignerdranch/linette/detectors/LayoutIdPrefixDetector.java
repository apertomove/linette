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
 * Lint check for layout id prefixes.
 */
public class LayoutIdPrefixDetector extends LayoutDetector {

    private static final String LAYOUT = "layout";
    private static final String TEXT = "text";
    private static final String EDIT = "edit";
    private static final String IMAGE = "image";
    private static final String BUTTON = "button";
    private static final String BTN = "btn";
    private static final String SEEK_BAR = "seek_bar";
    private static final String FLOATING_ACTION_BUTTON = "fab";
    private static final String PAGER = "pager";
    private static final String CHECK_BOX = "check";
    private static final String SPINNER = "spinner";

    private static final Class<? extends LayoutDetector> DETECTOR_CLASS = LayoutIdPrefixDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "LayoutIdPrefix";
    private static final String ISSUE_DESCRIPTION = "Missing layout id prefix";
    private static final String ISSUE_EXPLANATION = "Layout resource ids should be prefixed with";
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
    public LayoutIdPrefixDetector() {
    }

    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return ResourceFolderType.LAYOUT == folderType;
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        //return Collections.singletonList(ATTR_NAME);
        return ALL;
    }

    @Override
    public void visitAttribute(@NonNull XmlContext context, @NonNull Attr attribute) {

        if (attribute != null) {

            String value = attribute.getValue();
            value = value.replace("@+id/", "");

            String prefix = attribute.getOwnerElement().getTagName();
            String specification = "";

            if (attribute.getName().contains("android:id") && !prefix.equals("include")) {

                if (prefix.contains("Layout") && !value.startsWith(LAYOUT)) {
                    specification = LAYOUT;
                } else if (prefix.startsWith("Text") && !value.startsWith(TEXT)) {
                    specification = TEXT;
                } else if (prefix.startsWith("Edit") && !value.startsWith(EDIT)) {
                    specification = EDIT;
                } else if (prefix.startsWith("Image") && !value.startsWith(IMAGE)) {
                    specification = IMAGE;
                //} else if ((prefix.startsWith("Button") && !value.startsWith(BUTTON)) || (prefix.startsWith("Button") && !value.startsWith(BTN))) {
                } else if (prefix.startsWith("Button") && !value.startsWith(BUTTON)) {
                    specification = BUTTON + " / " + BTN;
                } else if (prefix.startsWith("SeekBar") && !value.startsWith(SEEK_BAR)) {
                    specification = SEEK_BAR;
                } else if (prefix.contains("Pager") && !value.startsWith(PAGER)) {
                    specification = PAGER;
                } else if (prefix.contains("FloatingActionButton") && !value.startsWith(FLOATING_ACTION_BUTTON)) {
                    specification = FLOATING_ACTION_BUTTON;
                } else if (prefix.contains("CheckBox") && !value.startsWith(CHECK_BOX)) {
                    specification = CHECK_BOX;
                } else if (prefix.contains("Spinner") && !value.startsWith(SPINNER)) {
                    specification = SPINNER;
                } else {
                    return;
                }

                context.report(ISSUE,
                        attribute,
                        context.getLocation(attribute),
                        ISSUE_DESCRIPTION + " " + specification);
            }
        }
    }
}


