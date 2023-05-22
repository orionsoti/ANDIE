package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the
 * application.
 * These actions do not affect the contents of the image itself, just the way it
 * is displayed.
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
public class ViewActions {

    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();

        actions.add(new ZoomInAction(LanguageSettings.getTranslated("zoomIn"),
                new ImageIcon("src/images/zoom-in_small.png"), LanguageSettings.getTranslated("zoomInDesc"),
                Integer.valueOf(KeyEvent.VK_EQUALS)));
        actions.add(new ZoomOutAction(LanguageSettings.getTranslated("zoomOut"),
                new ImageIcon("src/images/zoom-out_small.png"), LanguageSettings.getTranslated("zoomOutDesc"),
                Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction(LanguageSettings.getTranslated("zoomFull"),
                new ImageIcon("src/images/zoom-full_small.png"), LanguageSettings.getTranslated("zoomFullDesc"),
                Integer.valueOf(KeyEvent.VK_1)));

    }

    /**
     * <p>
     * Creates and adds viewAction buttons to the toolBar.
     * </p>
     * 
     * @param viewTools Target JMenuBar that the buttons are added to.
     */
    public void createToolMenu(JMenuBar viewTools) {
        // Creates JButton elements
        JButton zoomIn = new JButton(new ImageIcon("src/images/zoom-in.png"));
        JButton zoomOut = new JButton(new ImageIcon("src/images/zoom-out.png"));
        JButton fullscreen = new JButton(new ImageIcon("src/images/zoomfull.png"));

        // Adds corresponding ActionListener
        zoomOut.addActionListener(actions.get(1));
        zoomIn.addActionListener(actions.get(0));
        fullscreen.addActionListener(actions.get(2));

        // Sets button size
        zoomOut.setPreferredSize(Andie.buttonSize);
        zoomIn.setPreferredSize(Andie.buttonSize);
        fullscreen.setPreferredSize(Andie.buttonSize);

        // Sets the tooltips for the buttons
        zoomOut.setToolTipText(LanguageSettings.getTranslated("zoomOut"));
        zoomIn.setToolTipText(LanguageSettings.getTranslated("zoomIn"));
        fullscreen.setToolTipText(LanguageSettings.getTranslated("zoomFull"));

        zoomOut.setFocusPainted(false);
        zoomIn.setFocusPainted(false);
        fullscreen.setFocusPainted(false);

        // Create a separator
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension separatorDimension = new Dimension(10,
                viewTools.getPreferredSize().height);
        separator.setMaximumSize(separatorDimension);

        // Add the separator to the toolBar
        viewTools.add(separator);

        // Adds the buttons to the menu
        viewTools.add(zoomIn);
        viewTools.add(zoomOut);
        viewTools.add(fullscreen);
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The new menu containing the view actions
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu(LanguageSettings.getTranslated("view"));
        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }
        return viewMenu;
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     * actual contents.
     * </p>
     * 
     * @see ImageAction
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * Sets the hotkey as 'ctrl + '+'' for zoom-in.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // Sets the hotkey as 'ctrl + =' for the zoom-in action.
            KeyStroke plus = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, plus);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(plus, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom() + 10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     * actual contents.
     * </p>
     * 
     * @see ImageAction
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * Sets the hotkey for zoom-out as 'ctrl + -'.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // Sets 'ctrl + -' as the hotkey for the zoom-out action.
            KeyStroke minus = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), enabled);
            putValue(Action.ACCELERATOR_KEY, minus);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(minus, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom() - 10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     * actual contents.
     * </p>
     * 
     * @see ImageAction
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * Sets hotkey as ctrl + 1 for zoom-full.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // Sets the hotkey as 'ctrl + 1' for the zoom-full action.
            KeyStroke one = KeyStroke.getKeyStroke(KeyEvent.VK_1,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()| KeyEvent.SHIFT_DOWN_MASK , enabled);
            putValue(Action.ACCELERATOR_KEY, one);

            InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(one, getValue(Action.NAME));

            target.getActionMap().put(getValue(Action.NAME), this);

        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }
}