package ra.run.view.admin;


import ra.entity.User;
import ra.util.IOFile;
import ra.util.InputMethods;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void showUser() {
        while (true) {
            System.out.println("1. Hiển thị người dùng");
            System.out.println("2. Tìm kiếm người dùng");
            System.out.println("3. Khoá/mở người dùng");
            System.out.println("4. Cấp quyền người dùng");
            System.out.println("5. Quay Lại");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    lockUser();
                    break;
                case 4:
                    assignRole();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại");
            }
        }
    }

    public static void displayUser() {
        try {
            List<User> users = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
            if (users == null || users.isEmpty()) {
                System.out.println("Không có người dùng nào để hiển thị.");
            } else {
                for (User user : users) {
                    System.out.println(user.toString());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc dữ liệu người dùng: " + e.getMessage());
        }
    }

    public static void searchUser() {
        try {
            List<User> users = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
            if (users.isEmpty()) {
                System.out.println("Danh sách người dùng trống.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập email người dùng cần tìm: ");
            String email = scanner.nextLine().trim();

            boolean found = false;
            for (User user : users) {
                if (user.getEmail().contains(email)) {
                    System.out.println("Tìm thấy người dùng:");
                    System.out.println(user);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy người dùng với email: " + email);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tìm kiếm người dùng: " + e.getMessage());
        }
    }


    public static void lockUser() {
        try {
            List<User> users = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
            if (users.isEmpty()) {
                System.out.println("Danh sách người dùng trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập email người dùng cần khoá/mở: ");
            String email = scanner.nextLine().trim();

            boolean userFound = false;
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    System.out.print("Nhập 'lock' để khoá hoặc 'unlock' để mở khoá người dùng: ");
                    String action = scanner.nextLine().trim();

                    if ("lock".equalsIgnoreCase(action)) {
                        user.setActive(false);
                        System.out.println("Người dùng đã bị khoá.");
                    } else if ("unlock".equalsIgnoreCase(action)) {
                        user.setActive(true);
                        System.out.println("Người dùng đã được mở khoá.");
                    } else {
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    IOFile.writeToFile(IOFile.CUSTOMER_PATH, users);
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                System.out.println("Không tìm thấy người dùng với email: " + email);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi khoá/mở khoá người dùng: " + e.getMessage());
        }
    }
    public static void assignRole() {
        try {
            List<User> users = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
            if (users.isEmpty()) {
                System.out.println("Danh sách người dùng trống.");
                return;
            }
            System.out.print("Nhập email người dùng cần cấp quyền: ");
            String email = InputMethods.getString().trim();

            boolean userFound = false;
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    System.out.print("Nhập 'admin' để cấp quyền Admin hoặc 'user' để cấp quyền User: ");
                    String role = InputMethods.getString().trim();
                    if ("admin".equalsIgnoreCase(role)) {
                        user.setRole("ADMIN");
                        System.out.println("Người dùng đã được cấp quyền Admin.");
                    } else if ("user".equalsIgnoreCase(role)) {
                        user.setRole("USER");
                        System.out.println("Người dùng đã được cấp quyền User.");
                    } else {
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    IOFile.writeToFile(IOFile.CUSTOMER_PATH, users);
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                System.out.println("Không tìm thấy người dùng với email: " + email);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi cấp quyền người dùng: " + e.getMessage());
        }
    }

}
