package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class TaskManagerApp {
    private ProcessManager processManager;
    private JTable processTable;
    private DefaultTableModel tableModel;

    public TaskManagerApp() {
        processManager = new ProcessManager();
    }

    public void run() {
        createAndShowGUI();
        Set<ProcessInfo> processInfos = new ProcessLister().listProcessInfo();
        for (ProcessInfo processInfo : processInfos) {
            processManager.addProcess(processInfo.getName());
            tableModel.addRow(new Object[]{processInfo.getName(), processInfo.getType()});
        }
        ProcessManager.printSystemUsage();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Process Name", "Type", "CPU"}, 0);
        processTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(processTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = getjButton();
        frame.add(deleteButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JButton getjButton() {
        JButton deleteButton = new JButton("Delete Process");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = processTable.getSelectedRow();
                if (selectedRow != -1) {
                    String processName = (String) tableModel.getValueAt(selectedRow, 0);
                    processManager.killProcess(processName + ".exe");
                    System.out.println("Killed: " + processName);
                    tableModel.removeRow(selectedRow);
                }
            }
        });
        return deleteButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerApp().run();
            }
        });
    }
}