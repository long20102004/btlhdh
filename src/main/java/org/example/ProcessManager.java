package org.example;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private List<String> processes;

    public ProcessManager() {
        processes = new ArrayList<>();
    }

    public void addProcess(String task) {
        processes.add(task);
    }

    public void killProcess(String task) {
        kill(task);
        processes.remove(task);
    }
    public void kill(String processName) {
        try {
            ProcessBuilder builder = new ProcessBuilder("taskkill", "/F", "/IM", processName);
            Process process = builder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printSystemUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad() * 100; // percentage
        long totalMemory = osBean.getTotalMemorySize() / (1024 * 1024); // in MB
        long freeMemory = osBean.getFreeMemorySize() / (1024 * 1024); // in MB

        System.out.println("System CPU Load: " + String.format("%.2f", cpuLoad) + "%");
        System.out.println("Total Memory: " + totalMemory + " MB");
        System.out.println("Free Memory: " + freeMemory + " MB");
    }
}