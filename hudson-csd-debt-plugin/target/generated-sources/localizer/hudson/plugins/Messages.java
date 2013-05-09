// CHECKSTYLE:OFF

package hudson.plugins;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;

@SuppressWarnings({
    "",
    "PMD"
})
public class Messages {

    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     *  1 new warning
     * 
     */
    public static String Debt_ResultAction_OneNewWarning() {
        return holder.format("Debt.ResultAction.OneNewWarning");
    }

    /**
     *  1 new warning
     * 
     */
    public static Localizable _Debt_ResultAction_OneNewWarning() {
        return new Localizable(holder, "Debt.ResultAction.OneNewWarning");
    }

    /**
     * Debt warnings trend graph (new vs. fixed)
     * 
     */
    public static String Portlet_WarningsNewVsFixedGraph() {
        return holder.format("Portlet.WarningsNewVsFixedGraph");
    }

    /**
     * Debt warnings trend graph (new vs. fixed)
     * 
     */
    public static Localizable _Portlet_WarningsNewVsFixedGraph() {
        return new Localizable(holder, "Portlet.WarningsNewVsFixedGraph");
    }

    /**
     * Debt warnings per project
     * 
     */
    public static String Portlet_WarningsTable() {
        return holder.format("Portlet.WarningsTable");
    }

    /**
     * Debt warnings per project
     * 
     */
    public static Localizable _Portlet_WarningsTable() {
        return new Localizable(holder, "Portlet.WarningsTable");
    }

    /**
     * Fixed Debt Warnings
     * 
     */
    public static String Debt_FixedWarnings_Detail_header() {
        return holder.format("Debt.FixedWarnings.Detail.header");
    }

    /**
     * Fixed Debt Warnings
     * 
     */
    public static Localizable _Debt_FixedWarnings_Detail_header() {
        return new Localizable(holder, "Debt.FixedWarnings.Detail.header");
    }

    /**
     * Debt Trend
     * 
     */
    public static String Debt_Trend_Name() {
        return holder.format("Debt.Trend.Name");
    }

    /**
     * Debt Trend
     * 
     */
    public static Localizable _Debt_Trend_Name() {
        return new Localizable(holder, "Debt.Trend.Name");
    }

    /**
     * from {0} Debt files.
     * 
     */
    public static String Debt_ResultAction_MultipleFiles(Object arg1) {
        return holder.format("Debt.ResultAction.MultipleFiles", arg1);
    }

    /**
     * from {0} Debt files.
     * 
     */
    public static Localizable _Debt_ResultAction_MultipleFiles(Object arg1) {
        return new Localizable(holder, "Debt.ResultAction.MultipleFiles", arg1);
    }

    /**
     * New Debt Warnings
     * 
     */
    public static String Debt_NewWarnings_Detail_header() {
        return holder.format("Debt.NewWarnings.Detail.header");
    }

    /**
     * New Debt Warnings
     * 
     */
    public static Localizable _Debt_NewWarnings_Detail_header() {
        return new Localizable(holder, "Debt.NewWarnings.Detail.header");
    }

    /**
     * Debt: {0} warnings found.
     * 
     */
    public static String Debt_ResultAction_HealthReportMultipleItem(Object arg1) {
        return holder.format("Debt.ResultAction.HealthReportMultipleItem", arg1);
    }

    /**
     * Debt: {0} warnings found.
     * 
     */
    public static Localizable _Debt_ResultAction_HealthReportMultipleItem(Object arg1) {
        return new Localizable(holder, "Debt.ResultAction.HealthReportMultipleItem", arg1);
    }

    /**
     * Debt warnings trend graph (priority distribution)
     * 
     */
    public static String Portlet_WarningsPriorityGraph() {
        return holder.format("Portlet.WarningsPriorityGraph");
    }

    /**
     * Debt warnings trend graph (priority distribution)
     * 
     */
    public static Localizable _Portlet_WarningsPriorityGraph() {
        return new Localizable(holder, "Portlet.WarningsPriorityGraph");
    }

    /**
     *  1 fixed warning
     * 
     */
    public static String Debt_ResultAction_OneFixedWarning() {
        return holder.format("Debt.ResultAction.OneFixedWarning");
    }

    /**
     *  1 fixed warning
     * 
     */
    public static Localizable _Debt_ResultAction_OneFixedWarning() {
        return new Localizable(holder, "Debt.ResultAction.OneFixedWarning");
    }

    /**
     * {0} fixed warnings
     * 
     */
    public static String Debt_ResultAction_MultipleFixedWarnings(Object arg1) {
        return holder.format("Debt.ResultAction.MultipleFixedWarnings", arg1);
    }

    /**
     * {0} fixed warnings
     * 
     */
    public static Localizable _Debt_ResultAction_MultipleFixedWarnings(Object arg1) {
        return new Localizable(holder, "Debt.ResultAction.MultipleFixedWarnings", arg1);
    }

    /**
     * Debt: one warning found.
     * 
     */
    public static String Debt_ResultAction_HealthReportSingleItem() {
        return holder.format("Debt.ResultAction.HealthReportSingleItem");
    }

    /**
     * Debt: one warning found.
     * 
     */
    public static Localizable _Debt_ResultAction_HealthReportSingleItem() {
        return new Localizable(holder, "Debt.ResultAction.HealthReportSingleItem");
    }

    /**
     * from one Debt file.
     * 
     */
    public static String Debt_ResultAction_OneFile() {
        return holder.format("Debt.ResultAction.OneFile");
    }

    /**
     * from one Debt file.
     * 
     */
    public static Localizable _Debt_ResultAction_OneFile() {
        return new Localizable(holder, "Debt.ResultAction.OneFile");
    }

    /**
     * Debt Warnings
     * 
     */
    public static String Debt_Detail_header() {
        return holder.format("Debt.Detail.header");
    }

    /**
     * Debt Warnings
     * 
     */
    public static Localizable _Debt_Detail_header() {
        return new Localizable(holder, "Debt.Detail.header");
    }

    /**
     * Debt: no warnings found.
     * 
     */
    public static String Debt_ResultAction_HealthReportNoItem() {
        return holder.format("Debt.ResultAction.HealthReportNoItem");
    }

    /**
     * Debt: no warnings found.
     * 
     */
    public static Localizable _Debt_ResultAction_HealthReportNoItem() {
        return new Localizable(holder, "Debt.ResultAction.HealthReportNoItem");
    }

    /**
     *  1 warning
     * 
     */
    public static String Debt_ResultAction_OneWarning() {
        return holder.format("Debt.ResultAction.OneWarning");
    }

    /**
     *  1 warning
     * 
     */
    public static Localizable _Debt_ResultAction_OneWarning() {
        return new Localizable(holder, "Debt.ResultAction.OneWarning");
    }

    /**
     * {0} new warnings
     * 
     */
    public static String Debt_ResultAction_MultipleNewWarnings(Object arg1) {
        return holder.format("Debt.ResultAction.MultipleNewWarnings", arg1);
    }

    /**
     * {0} new warnings
     * 
     */
    public static Localizable _Debt_ResultAction_MultipleNewWarnings(Object arg1) {
        return new Localizable(holder, "Debt.ResultAction.MultipleNewWarnings", arg1);
    }

    /**
     * Publish Debt analysis results
     * 
     */
    public static String Debt_Publisher_Name() {
        return holder.format("Debt.Publisher.Name");
    }

    /**
     * Publish Debt analysis results
     * 
     */
    public static Localizable _Debt_Publisher_Name() {
        return new Localizable(holder, "Debt.Publisher.Name");
    }

    /**
     * Debt Warnings
     * 
     */
    public static String Debt_ProjectAction_Name() {
        return holder.format("Debt.ProjectAction.Name");
    }

    /**
     * Debt Warnings
     * 
     */
    public static Localizable _Debt_ProjectAction_Name() {
        return new Localizable(holder, "Debt.ProjectAction.Name");
    }

    /**
     * {0} warnings
     * 
     */
    public static String Debt_ResultAction_MultipleWarnings(Object arg1) {
        return holder.format("Debt.ResultAction.MultipleWarnings", arg1);
    }

    /**
     * {0} warnings
     * 
     */
    public static Localizable _Debt_ResultAction_MultipleWarnings(Object arg1) {
        return new Localizable(holder, "Debt.ResultAction.MultipleWarnings", arg1);
    }

}
