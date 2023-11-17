package com.mycompany.mavenproject1;

import javax.swing.*;

// The Window interface provides a template for window-related operations
public interface Window {

    // Abstract method to create buttons in the window
    void createButtons();

    // Abstract method to create labels in the window
    void createLabels();

    // Default method to show or hide a window
    default void showWindow(boolean isVisible, JFrame window) {
        window.setVisible(isVisible);
    }
}