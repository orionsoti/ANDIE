package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions {

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        actions.add(new UndoAction(LanguageSettings.getTranslated("undo"), new ImageIcon("src/images/undo_small.png"),
                LanguageSettings.getTranslated("undo"), Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction(LanguageSettings.getTranslated("redo"), new ImageIcon("src/images/redo_small.png"),
                LanguageSettings.getTranslated("redo"), Integer.valueOf(KeyEvent.VK_Y)));
    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(LanguageSettings.getTranslated("edit"));

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Creates and adds edit action buttons to the toolBar.
     * </p>
     * 
     * @param toolBar Target JMenuBar that the buttons are added to.
     */
    public void createToolMenu(JMenuBar toolBar) {
        // Creates the buttons
        JButton undo = new JButton(new ImageIcon("src/images/undo1.png"));
        JButton redo = new JButton(new ImageIcon("src/images/redo1.png"));

        // Adds action listeners
        undo.addActionListener(actions.get(0));
        redo.addActionListener(actions.get(1));

        // Sets the button size
        undo.setPreferredSize(Andie.buttonSize);
        redo.setPreferredSize(Andie.buttonSize);

        // Sets the tooltips
        undo.setToolTipText(LanguageSettings.getTranslated("undo"));
        redo.setToolTipText(LanguageSettings.getTranslated("redo"));

        undo.setFocusPainted(false);
        redo.setFocusPainted(false);

        // Create a separator
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension separatorDimension = new Dimension(10,
                toolBar.getPreferredSize().height);
        separator.setMaximumSize(separatorDimension);

        // Add the separator to the toolBar
        toolBar.add(separator);

        // Adds to toolBar
        toolBar.add(undo);
        toolBar.add(redo);
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * Sets 'ctrl + z' as the hotkey for undo
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // Sets the hotkey as 'ctrl + z' to trigger an undo action.
            KeyStroke z = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(),
                    enabled);
            putValue(Action.ACCELERATOR_KEY, z);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(z, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);

        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (MacroActions.isRecording() == true) {
                MacroActions.macroRunningMsg();
                return;
            }
            try {
                target.getImage().undo();
                target.repaint();
                target.getParent().revalidate();
            } catch (EmptyStackException exception) {
                // JOptionPane.showMessageDialog(null,
                // LanguageSettings.getTranslated("noOperation"));
                Object[] options = { LanguageSettings.getTranslated("ok") };
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noOperation"),
                        LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * Sets 'ctrl + y' as the redo hotkey.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            // Sets the hotkey as 'ctrl + y' to trigger a redo action.
            KeyStroke y = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(),
                    enabled);
            putValue(Action.ACCELERATOR_KEY, y);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(y, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (MacroActions.isRecording() == true) {
                MacroActions.macroRunningMsg();
                return;
            }
            try {
                target.getImage().redo();
                target.repaint();
                target.getParent().revalidate();

            } catch (EmptyStackException exception) {
                Object[] options = { LanguageSettings.getTranslated("ok") };
                JOptionPane.showOptionDialog(null, LanguageSettings.getTranslated("noOperation"),
                        LanguageSettings.getTranslated("alert"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
        }
    }

}
