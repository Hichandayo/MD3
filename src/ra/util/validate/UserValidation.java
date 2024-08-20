package ra.util.validate;

public class UserValidation {
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$";
        return email.matches(regex);
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(regex);
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^(\\+84|0)\\d{9,10}$";
        return phone.matches(regex);
    }
    public static boolean isValidBirthday(String birthday){
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$";
        return birthday.matches(regex);
    }
    public static boolean isValidName(String name) {
        // Biểu thức chính quy kiểm tra tên chỉ chứa chữ cái hoa và thường
        return name != null && name.matches("^[a-zA-Z]+$");
    }
    public static boolean isValidAddress(String address) {
        // kiểm tra địa chỉ chỉ chứa chữ cái hoa, thường, số và ký tự /
        return address != null && address.matches("^[a-zA-Z0-9/]+$");
    }
}