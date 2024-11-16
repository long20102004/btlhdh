package org.example;

import javax.swing.*;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        TaskManagerApp app = new TaskManagerApp();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.run();
            }
        });
    }
}
