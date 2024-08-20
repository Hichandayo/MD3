package ra.run;

import ra.run.view.admin.AdminMenu;
import ra.run.view.users.UserInfo;
import ra.run.view.users.UserMenu;
import ra.entity.User;
import ra.util.IOFile;
import ra.util.InputMethods;
import ra.util.validate.UserValidation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ra.run.view.users.UserInfo.currentUser;

public class Main {
    static UserMenu userMenu = new UserMenu();
    static AdminMenu adminMenu = new AdminMenu();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private static final String LOGIN_STATUS_PATH = "src/ra/data/userLogin.txt";
    private static final String USER_DATA_PATH = IOFile.CUSTOMER_PATH;

    public static void main(String[] args){
        BeginMenu();
    }

    public static void BeginMenu()  {

        while (true) {
            autoLogin();
            System.out.println(ANSI_PURPLE + "╔═════════════════════════ ══╗" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║ Welcome to Hi-chan Shop    ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╠══════════════════════════ ═╣" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    1. Đăng nhập " + ANSI_RESET + ANSI_PURPLE + "           ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    2. Đăng kí" + ANSI_RESET + ANSI_PURPLE + "              ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    3. Đăng nhập Admin" + ANSI_RESET + ANSI_PURPLE + "      ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "║" + ANSI_RESET + ANSI_CYAN + "    4. Thoát" + ANSI_RESET + ANSI_PURPLE + "                ║" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "╚ " + ANSI_RESET + ANSI_CYAN + "  Nhập lựa chọn ⭣ ⭣ ⭣" + ANSI_RESET + ANSI_PURPLE + "     ═╝" + ANSI_RESET);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    // đăng nhập
                    break;
                case 2:
                    register();
                    // đăng kí
                    break;
                case 3:
                    adminLogin();
                    // đăng nhập admin
                    break;
                case 4:
                    // thoát
                    System.out.println("Tạm biệt");
                    break;
                default:
                    System.err.println("ko đúng lựa chọn");
            }
            if (choice == 3) {
                break;
            }
        }
    }
    // tự động đăng nhập
    public static void autoLogin() {
        File file = new File(LOGIN_STATUS_PATH);
        if (file.exists() && !file.isDirectory()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String email = reader.readLine();
                if (email != null && !email.isEmpty()) {
                    IOFile<User> userIO = new IOFile<>();
                    List<User> users = userIO.readFromFile(USER_DATA_PATH);
                    Optional<User> userOptional = users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
                    if (userOptional.isPresent()) {
                        UserInfo.setUserLoggedIn(userOptional.get());
                        if (userOptional.get().getRole().equalsIgnoreCase("ADMIN")) {
                            System.out.println("Chào mừng Admin!");
                            adminMenu.adminMenu();
                        } else {
                            System.out.println("Chào mừng Quý Khách");
                            userMenu.userMenu();
                        }
                    }else{
                        System.err.println("Email not found!!");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Lỗi khi đọc trạng thái đăng nhập. Chi tiết: " + e.getMessage());
            }
        }
    }
    public static void clearUserLoggedIn() throws IOException {
        File file = new File(LOGIN_STATUS_PATH);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_STATUS_PATH));
            writer.write("");
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //----------Đăng Ký-------------
    public static void register() {
        IOFile<User> userIO = new IOFile<>();
        List<User> users;
        System.out.println("************ Đăng Ký ************");

        try {
            users = userIO.readFromFile(USER_DATA_PATH);
            if (users == null) {
                users = new ArrayList<>();
            }
            String name;
            String email;
            String password;
            String phone;
            String address;

            while (true) {
                //Nhập và kiển tra tên người dùng
                while (true) {
                    System.out.println(ANSI_PURPLE + "Nhập Tên ngươi dùng" + ANSI_RESET);
                    name = InputMethods.getString().trim();
                    if (UserValidation.isValidName(name)) {
                        break;
                    }
                    if (!name.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Tên đăng nhập không hợp lệ. Vui lòng nhập lại.");
                    }
                }
                //Nhập và kiểm tra email
                while (true) {
                    System.out.println(ANSI_PURPLE + "Nhập email đăng nhập" + ANSI_RESET);
                    email = InputMethods.getString().trim();
                    if (UserValidation.isValidEmail(email)) {
                        break;
                    }
                    if (email.isEmpty() || !UserValidation.isValidEmail(email)) {
                        System.out.println("Email không hợp lệ hoặc để trống. Vui lòng nhập lại.");
                    } else {
                        System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
                    }
                    String finalEmail = email;
                    if (users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(finalEmail))) {
                        System.out.println("Email đã tồn tại. Vui lòng chọn email khác.");
                    }
                }
                //Nhập và kiểm tra mật khẩu
                while (true) {
                    System.out.println(ANSI_PURPLE + "Nhập mật khẩu" + ANSI_RESET);
                    password = InputMethods.getString().trim();
                    if (UserValidation.isValidPassword(password)) {
                        break;
                    }if (password.isEmpty()) {
                        System.out.println("Mật khẩu không được để trống.");
                        continue;
                    } else {
                        System.out.println("Mật khẩu phải có 1 chữ viết hoa, 1 chữ viết thường, 1 số và có 8 kí tự trở lên. Vui lòng nhập lại.");
                    }
                }
                //Nhập và kiểm tra số điện thoại
                while (true) {
                    System.out.println(ANSI_PURPLE + "Nhập số điện thoại" + ANSI_RESET);
                    phone = InputMethods.getString().trim();
                    if (UserValidation.isValidPhone(phone)) {
                        break;
                    }
                    if (phone.isEmpty()) {
                        System.out.println("Số điện thoại không được để trống.");
                    } else {
                        System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                    }
                }
                //Nhập và kiểm tra địa chỉ
                while (true) {
                    System.out.println(ANSI_PURPLE + "Nhập địa chỉ : " + ANSI_RESET);
                    address = InputMethods.getString().trim();
                    if (UserValidation.isValidAddress(address)) {
                        break;
                    }
                    if (address.isEmpty()) {
                        System.out.println("Địa chỉ không được để trống.");
                        continue;
                    } else {
                        System.out.println("Địa chỉ hợp lệ. Vui lòng nhập lại.");
                    }
                }
                break;
            }
            User newUser = new User(name, email, password, phone, address, true);
            users.add(newUser);

            userIO.writeToFile(USER_DATA_PATH, users);
            System.out.println("Đăng ký thành công!");

        } catch (IOException e) {
            System.err.println("Không thể ghi người dùng vào tập tin. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi lớp không tìm thấy. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
        login();
    }

    public static void setUserLoggedIn(User user) {
        currentUser = user;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_STATUS_PATH))) {
            writer.write(user.getEmail());  // Lưu email người dùng vào tệp login_status.txt
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu trạng thái đăng nhập. Chi tiết: " + e.getMessage());
        }
    }

    public static void login() {
        try {
            System.out.println("---------- Login ----------");
            System.out.print("Email tài khoản: ");
            String email = InputMethods.getString();
            System.out.print("Mật khẩu: ");
            String password = InputMethods.getString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                System.err.println("Tên tài khoản hoặc mật khẩu không được để trống.");
                return;
            }
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(USER_DATA_PATH);

            if (users == null || users.isEmpty()) {
                System.err.println("Danh sách người dùng không thể tải từ tập tin hoặc không có người dùng nào được lưu.");
                return;
            }
            boolean loggedIn = false;
            for (User user : users) {
                if (user != null && user.getEmail() != null && user.getPassword() != null) {
                    if (user.getEmail().trim().equalsIgnoreCase(email) && user.getPassword().trim().equals(password)) {
                        if (user.isActive()) {
                            loggedIn = true;
                            System.out.println("Đăng nhập thành công!");
                            setUserLoggedIn(user);
                            UserInfo.setUserLoggedIn(user);
                            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                                System.out.println("Chào mừng Admin!");
                                adminMenu.adminMenu();
                            } else {
                                System.out.println("Chào mừng Quý Khách");
                                userMenu.userMenu();
                            }
                            break;
                        } else {
                            System.err.println("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ với quản trị viên.");
                            return;
                        }
                    }
                }
            }
            if (!loggedIn) {
                System.err.println("Email hoặc mật khẩu không hợp lệ. Vui lòng thử lại.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Đã xảy ra lỗi trong quá trình đăng nhập. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void adminLogin() {
        System.out.println("----------Welcome Admin----------");
        System.out.print("Username: ");
        String adminEmail = InputMethods.getString();
        System.out.print("Password: ");
        String adminPassword = InputMethods.getString();

        IOFile<User> userIO = new IOFile<>();
        List<User> users;

        try {
            if (adminEmail.equals("admin@gmail.com") && adminPassword.equals("admin123")) {
                System.out.println("Login successful!");
                adminMenu.adminMenu();
                return; // Exit the method since admin login is successful
            }

            // Check against users list from file
            users = userIO.readFromFile(USER_DATA_PATH);
            if (users != null) {
                boolean isAdmin = users.stream()
                        .anyMatch(user -> user.getEmail().equalsIgnoreCase(adminEmail)
                                && user.getPassword().equals(adminPassword)
                                && user.getRole().equalsIgnoreCase("ADMIN"));

                if (isAdmin) {
                    System.out.println("Login successful!");
                    adminMenu.adminMenu();
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            } else {
                System.out.println("No user data found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
