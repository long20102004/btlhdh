package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class PhysicalToLogical {
    private static int PAGE_SIZE;
    private static final HashMap<Integer, Integer> reversePageTable = new HashMap<>();

    public static int physicalToLogical(int physicalAddress) throws IllegalArgumentException {
        // Tính toán số khung và offset
        int frameNumber = physicalAddress / PAGE_SIZE;
        int offset = physicalAddress % PAGE_SIZE;

        // Kiểm tra số khung có tồn tại trong bảng phân trang ngược
        if (reversePageTable.containsKey(frameNumber)) {
            int pageNumber = reversePageTable.get(frameNumber);
            return pageNumber * PAGE_SIZE + offset;
        } else {
            throw new IllegalArgumentException("Số khung không tồn tại trong bảng phân trang ngược.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Nhập kích thước trang
            System.out.print("Nhập kích thước trang: ");
            PAGE_SIZE = scanner.nextInt();

            // Nhập số lượng anh xa
            System.out.print("Nhập số lượng ánh xạ trong bảng phân trang ngược: ");
            int mappings = scanner.nextInt();

            // Nhập khung -> trang
            System.out.println("Nhập ánh xạ khung -> trang: ");
            for (int i = 0; i < mappings; i++) {
                System.out.print("Khung: ");
                int frameNumber = scanner.nextInt();
                System.out.print("Trang: ");
                int pageNumber = scanner.nextInt();
                reversePageTable.put(frameNumber, pageNumber);
            }

            // Nhập địa chỉ vật lý để chuyển đổi
            System.out.print("Nhập địa chỉ vật lý: ");
            int physicalAddress = scanner.nextInt();

            // Chuyển đổi sang địa chỉ logic
            int logicalAddress = physicalToLogical(physicalAddress);
            System.out.println("Địa chỉ logic: " + logicalAddress);
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi: Đầu vào không hợp lệ.");
        } finally {
            scanner.close();
        }
    }
}
