package ra.run.view.users;


import ra.entity.User;
import ra.services.UserServiceImpl;
import ra.util.InputMethods;

import java.io.IOException;

public class UserInfo {
    private static UserServiceImpl userService = new UserServiceImpl();
    public static User currentUser;

    public static void UserInfo() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("1. Đổi mật khẩu");
            System.out.println("2. Hiển thị thông tin");
            System.out.println("3. Thay đổi thông tin");
            System.out.println("4. Quay Lại");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    showInfo();
                    break;
                case 3:
                    editInfo();
                    break;
                case 4:
                    System.out.println("Trở về trang chủ.");
                    return;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại.");
            }
        }
    }

    public static void showInfo() {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.println("Thông tin người dùng:");
        System.out.println("Tên: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Số điện thoại: " + currentUser.getPhone());
        System.out.println("Địa chỉ: " + currentUser.getAddress());
        System.out.println("Trạng thái: " + (currentUser.isActive() ? "Hoạt động" : "Không hoạt động"));
    }

    public static void setUserLoggedIn(User user) {
        currentUser = user;
        System.out.println("đăng nhập với tài khoản : " + currentUser.getName());
    }


    private static void changePassword() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.print("Nhập mật khẩu cũ: ");
        String oldPassword = InputMethods.getString();

        System.out.print("Nhập mật khẩu mới: ");
        String newPassword = InputMethods.getString();

        if (userService.isValidPassword(currentUser.getEmail(), oldPassword)) {
            if (userService.updatePassword(currentUser.getEmail(), newPassword)) {
                System.out.println("Mật khẩu đã được thay đổi thành công.");
            } else {
                System.out.println("Đã xảy ra lỗi khi thay đổi mật khẩu.");
            }
        } else {
            System.out.println("Mật khẩu cũ không đúng.");
        }
    }

    private static void editInfo() {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.println("Nhập thông tin mới của bạn (nhấn Enter để giữ nguyên):");

        System.out.print("Tên: ");
        String newName = InputMethods.getString();
        if (!newName.trim().isEmpty()) {
            currentUser.setName(newName);
        }

        System.out.print("Số điện thoại: ");
        String newPhone = InputMethods.getString();
        if (!newPhone.trim().isEmpty()) {
            currentUser.setPhone(newPhone);
        }

        System.out.print("Địa chỉ: ");
        String newAddress = InputMethods.getString();
        if (!newAddress.trim().isEmpty()) {
            currentUser.setAddress(newAddress);
        }

        // Cập nhật thông tin người dùng
        userService.update(currentUser);
        System.out.println("Thông tin đã được cập nhật thành công.");
    }
}
