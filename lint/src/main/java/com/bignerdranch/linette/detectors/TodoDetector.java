package com.bignerdranch.linette.detectors;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.TextFormat;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.Node;

/**
 * Lint check for the usage of to-do statements
 */
public class TodoDetector extends Detector implements Detector.JavaPsiScanner {

    private static final String TODO_MATCHER_STRING = "TODO";

    private static final Class<? extends Detector> DETECTOR_CLASS = TodoDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "Todo";
    private static final String ISSUE_DESCRIPTION = "Todo Comments Detected";
    private static final String ISSUE_EXPLANATION = "Using the comment `// TODO` can be helpful during development, but should not be left in shipped code. Consider making this a Github Issue if you need to keep track of your todo's";
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
     * Constructs a new {@link TodoDetector} check
     */
    public TodoDetector() {
    }

    @Override
    public boolean appliesTo(ResourceFolderType folderType) {
        return true;
        //return super.appliesTo(folderType);
    }

    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return super.getApplicablePsiTypes();
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        String source = context.getContents();

        // Check validity of source
        if (source == null) {
            return null;
        }

        // Check for uses of to-dos
        int index = source.indexOf(TODO_MATCHER_STRING);
        for (int i = index; i >= 0; i = source.indexOf(TODO_MATCHER_STRING, i + 1)) {
            Location location = Location.create(context.file, source, i, i + TODO_MATCHER_STRING.length());
            context.report(ISSUE, location, ISSUE.getBriefDescription(TextFormat.TEXT));
        }
        return null;
        //return super.createPsiVisitor(context);
    }
}
