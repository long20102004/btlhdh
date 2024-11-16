package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ProcessLister {
    public Set<ProcessInfo> listProcessInfo() {
        List<ProcessInfo> processes = new ArrayList<>();
        try {
            ProcessBuilder builder = new ProcessBuilder("powershell",
                    "Get-Process | Select-Object Name, MainWindowTitle, SessionId");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // Bo qua dong -------
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Chia theo dau cach
                String[] parts = line.trim().split("\\s+");
                String name = parts[0];

                // Neu co 3 phan tro len => MainWindowTitle khong trong => application
                if (parts.length >= 3) {
                    processes.add(new ProcessInfo(name, "APPLICATION"));
                }
                else processes.add(new ProcessInfo(name, "BACKGROUND"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        processes.sort(Comparator.comparing(ProcessInfo::getType));
        return new LinkedHashSet<>(processes);
    }
}
