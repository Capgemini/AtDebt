package hudson.plugins.debt.parser;

import hudson.plugins.analysis.util.model.AbstractAnnotation;
import hudson.plugins.analysis.util.model.Priority;

import org.apache.commons.lang.StringUtils;

/**
 * A serializable Java Bean class representing a warning.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class DebtRecord extends AbstractAnnotation {

    /** Unique identifier of this class. */
    private static final long serialVersionUID = 5171661552905752234L;
    /** Origin of the annotation. */
    public static final String ORIGIN = "debt";

    /**
     * Creates a new instance of <code>DebtRecord</code>.
     *
     * @param priority the priority
     * @param message the message of the warning
     * @param category the warning category
     * @param type the identifier of the warning type
     * @param start the first line of the line range
     * @param end the last line of the line range
     */
    public DebtRecord(final Priority priority, final String message, final String category, final String type,
            final int start, final int end) {
        super(priority, message, start, end, category, type);

        setOrigin(ORIGIN);
    }

    /**
     * Creates a new instance of <code>DebtRecord</code>.
     *
     * @param priority the priority
     * @param message the message of the warning
     * @param category the warning category
     * @param type the identifier of the warning type
     * @param lineNumber the line number of the warning in the corresponding
     * file
     */
    public DebtRecord(final Priority priority, final String message, final String category, final String type, final int lineNumber) {
        this(priority, message, category, type, lineNumber, lineNumber);
    }

    /** {@inheritDoc} */
    public String getToolTip() {
        return DebtMessages.getInstance().getMessage(getCategory(), getType());
    }

    /** The tooltip. */
    @SuppressWarnings("unused")
    @edu.umd.cs.findbugs.annotations.SuppressWarnings("SS")
    private final String tooltip = StringUtils.EMPTY; // backward compatibility
}