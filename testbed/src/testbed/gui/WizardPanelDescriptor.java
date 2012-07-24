package testbed.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import testbed.interfaces.IParametersPanel;
import testbed.interfaces.IScenario;
import testbed.interfaces.ITrustModel;

/**
 * A base descriptor class used to reference a Component panel for the Wizard,
 * as well as provide general rules as to how the panel should behave.
 */
public class WizardPanelDescriptor implements Observer {

    private static final String DEFAULT_IDENTIFIER = "DefaultIdentifier";
    private static final String TITLE = "<html><h1>%s</h1></html>";

    /**
     * Identifier returned by getNextPanelDescriptor() to indicate that this is
     * the last panel and the text of the 'Next' button should change to
     * 'Finish'.
     */
    public static final FinishIdentifier FINISH = new FinishIdentifier();

    private Wizard wizard;
    private Object identifier;

    private Object nextPanelDescriptor = FINISH;
    private Object backPanelDescriptor = null;

    private Component panel;
    private IParametersPanel paramsPanel;
    private String name = String.format(TITLE, "Unnamed");

    /**
     * Default constructor. The id and the Component panel must be set
     * separately.
     */
    public WizardPanelDescriptor() {
	identifier = DEFAULT_IDENTIFIER;
	panel = createPanel(null);
	name = String.format(TITLE, "Unnamed");
    }

    public WizardPanelDescriptor(Object id) {
	identifier = id;
	panel = createPanel(null);
	name = String.format(TITLE, "Unnamed");
    }

    /**
     * Constructor which accepts both the Object-based identifier and a
     * reference to the Component class which makes up the panel.
     * 
     * @param id
     *            Object-based identifier
     * @param panel
     *            A class which extends java.awt.Component that will be inserted
     *            as a panel into the wizard dialog.
     */
    public WizardPanelDescriptor(Object id, IParametersPanel params,
	    String title) {
	identifier = id;
	paramsPanel = params;
	name = String.format(TITLE, title);
	panel = createPanel((params == null ? null : params.getComponent()));
    }

    /**
     * Returns to java.awt.Component that serves as the actual panel.
     * 
     * @return A reference to the java.awt.Component that serves as the panel
     */
    public final Component getPanelComponent() {
	return panel;
    }

    public final IParametersPanel getIParametersPanel() {
	return paramsPanel;
    }

    public final void setIParamsPanel(IParametersPanel params, String title) {
	paramsPanel = params;
	name = String.format(TITLE, title);
	panel = createPanel((params == null ? null : params.getComponent()));
    }

    /**
     * Returns the unique Object-based identifier for this panel descriptor.
     * 
     * @return The Object-based identifier
     */
    public final Object getID() {
	return identifier;
    }

    /**
     * Sets the Object-based identifier for this panel. The identifier must be
     * unique from all the other identifiers in the panel.
     * 
     * @param id
     *            Object-based identifier for this panel.
     */
    public final void setID(Object id) {
	identifier = id;
    }

    final void setWizard(Wizard w) {
	wizard = w;
    }

    /**
     * Returns a reference to the Wizard component.
     * 
     * @return The Wizard class hosting this descriptor.
     */
    public final Wizard getWizard() {
	return wizard;
    }

    /**
     * Returns a reference to the current WizardModel for this Wizard component.
     * 
     * @return The current WizardModel for this Wizard component.
     */
    public WizardModel getWizardModel() {
	return wizard.getModel();
    }

    // Override this method to provide an Object-based identifier
    // for the next panel.

    /**
     * Override this class to provide the Object-based identifier of the panel
     * that the user should traverse to when the Next button is pressed. Note
     * that this method is only called when the button is actually pressed, so
     * that the panel can change the next panel's identifier dynamically at
     * runtime if necessary. Return null if the button should be disabled.
     * Return FinishIdentfier if the button text should change to 'Finish' and
     * the dialog should end.
     * 
     * @return Object-based identifier.
     */
    public Object getNext() {
	return this.nextPanelDescriptor;
    }

    public void setNext(Object nextPanelDescriptor) {
	this.nextPanelDescriptor = nextPanelDescriptor;
    }

    // Override this method to provide an Object-based identifier
    // for the previous panel.

    /**
     * Override this class to provide the Object-based identifier of the panel
     * that the user should traverse to when the Back button is pressed. Note
     * that this method is only called when the button is actually pressed, so
     * that the panel can change the previous panel's identifier dynamically at
     * runtime if necessary. Return null if the button should be disabled.
     * 
     * @return Object-based identifier
     */
    public Object getBack() {
	return this.backPanelDescriptor;
    }

    public void setBack(Object backPanelDescriptor) {
	this.backPanelDescriptor = backPanelDescriptor;
    }

    // Override this method in the subclass if you wish it to be called
    // just before the panel is displayed.

    /**
     * Override this method to provide functionality that will be performed just
     * before the panel is to be displayed.
     */
    public void aboutToDisplayPanel() {

    }

    // Override this method in the subclass if you wish to do something
    // while the panel is displaying.

    /**
     * Override this method to perform functionality when the panel itself is
     * displayed.
     */
    public void displayingPanel() {

    }

    // Override this method in the subclass if you wish it to be called
    // just before the panel is switched to another or finished.

    /**
     * Override this method to perform functionality just before the panel is to
     * be hidden.
     */
    public void aboutToHidePanel() {

    }

    static class FinishIdentifier {
	public static final String ID = "FINISH";
    }

    /**
     * Returns a Component with title panel at the top and elements panel in the
     * middle.
     * 
     * @param elements
     *            The component that contains elements to be shown on the menu.
     *            If elements is null, then the message "no parameters needed"
     *            is displayed.
     * @return
     */
    public Component createPanel(Component elements) {
	final JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());

	final JLabel title = new JLabel();
	title.setText(name);
	panel.add(title, BorderLayout.NORTH);

	if (null == elements) {
	    final JPanel e = new JPanel();
	    e.setLayout(new FlowLayout());
	    final JLabel label = new JLabel(
		    "<html><p align='center'>No parameters required.</p></html>");

	    e.add(label);
	    elements = e;
	}

	// to make it scrollable
	panel.add(new JScrollPane(elements), BorderLayout.CENTER);

	return panel;
    }

    @Override
    public void update(Observable o, Object arg) {
	if (arg instanceof Boolean) {
	    /*
	     * enable/disable next/finish button
	     */
	    wizard.setBackButtonEnabled((Boolean) arg);
	    wizard.setNextFinishButtonEnabled((Boolean) arg);
	} else if (arg instanceof IScenario) {
	    /*
	     * set scenario parameters panel
	     */
	    final IParametersPanel current = wizard.getModel()
		    .getPanelDescriptor(ParametersGUI.SCENARIO)
		    .getIParametersPanel();

	    final IScenario scenario = (IScenario) arg;
	    final IParametersPanel novel = scenario.getParametersPanel();

	    // do not change if the new value is the same as old value
	    if (null != current && null != novel
		    && current.getClass() == novel.getClass()) {
		return;
	    }

	    // get ParametersPanel
	    final IParametersPanel params = scenario.getParametersPanel();

	    // create new WPD
	    final WizardPanelDescriptor wpd = new WizardPanelDescriptor(
		    ParametersGUI.SCENARIO, params, scenario.getName());

	    // register WPD
	    wizard.registerWizardPanel(wpd);

	    // initialize WPD
	    if (null != params)
		params.initialize(wpd, wizard.getClassLoader());

	    // set next / previous buttons
	    wpd.setBack(ParametersGUI.MAIN);
	    wpd.setNext(ParametersGUI.MODELS);
	} else if (arg instanceof ITrustModel) {
	    /*
	     * set trust model parameters panel
	     */

	    final IParametersPanel current = wizard.getModel()
		    .getPanelDescriptor(ParametersGUI.MODELS)
		    .getIParametersPanel();

	    final ITrustModel model = (ITrustModel) arg;
	    final IParametersPanel novel = model.getParametersPanel();

	    // make change only if the value changed
	    if (null != current && null != novel
		    && current.getClass() == novel.getClass()) {
		return;
	    }

	    // get ParametersPanel
	    final IParametersPanel params = model.getParametersPanel();

	    // create, register and initialize new WPD
	    final WizardPanelDescriptor wpd = new WizardPanelDescriptor(
		    ParametersGUI.MODELS, params, model.getName());
	    wizard.registerWizardPanel(wpd);

	    if (null != params)
		params.initialize(wpd, wizard.getClassLoader());

	    wpd.setBack(ParametersGUI.SCENARIO);
	    // wpd.setNext(ParametersGUI.MODELS);
	}
    }
}
