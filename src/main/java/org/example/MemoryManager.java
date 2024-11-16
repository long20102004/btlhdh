package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class MemoryManager {
    // Kích thước trang (Page Size)
    private static final int PAGE_SIZE = 4096; // 4 KB

    // Bảng trang (Page Table)
    private static final HashMap<Integer, Integer> pageTable = new HashMap<>();

    static {
        // Giả định bảng trang: Page -> Frame
        pageTable.put(0, 5); // Page 0 ánh xạ tới Frame 5
        pageTable.put(1, 2); // Page 1 ánh xạ tới Frame 2
        pageTable.put(2, 8); // Page 2 ánh xạ tới Frame 8
    }

    public static int logicalToPhysical(int logicalAddress) throws IllegalArgumentException {
        // Tính toán số trang (Page Number) và bù đắp (Offset)
        int pageNumber = logicalAddress / PAGE_SIZE;
        int offset = logicalAddress % PAGE_SIZE;

        // Kiểm tra page number có tồn tại trong bảng trang
        if (pageTable.containsKey(pageNumber)) {
            int frameNumber = pageTable.get(pageNumber);
            // Tính toán địa chỉ vật lý
            return frameNumber * PAGE_SIZE + offset;
        } else {
            throw new IllegalArgumentException("Page number không tồn tại trong bảng trang.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Nhập địa chỉ logic từ người dùng
            System.out.print("Nhập địa chỉ logic (logical address): ");
            int logicalAddress = scanner.nextInt();

            // Chuyển đổi sang địa chỉ vật lý
            int physicalAddress = logicalToPhysical(logicalAddress);
            System.out.println("Địa chỉ vật lý (Physical Address): " + physicalAddress);
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi: Đầu vào không hợp lệ.");
        } finally {
            scanner.close();
        }
    }
}
