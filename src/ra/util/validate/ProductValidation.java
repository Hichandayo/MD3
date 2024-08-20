package ra.util.validate;
public class ProductValidation {

    public static boolean isValidCategoryName(String categoryName) {
        // Biểu thức chính quy kiểm tra tên chỉ chứa chữ cái hoặc cả chữ cái và số, không được chỉ có số
        return categoryName != null && categoryName.matches("^(?!\\d+$)[a-zA-Z0-9]+$");
    }

    public static boolean isValidProductPrice(String productPrice) {
        if (productPrice == null || productPrice.trim().isEmpty()) {
            return false; // Giá trị không được để trống
        }
        double price = Double.parseDouble(productPrice);
        return price > 1000; // Giá trị phải lớn hơn 1000
    }

    public static boolean isValidProductQuantity(String productQuantity) {
        String regex = "^[0-9]{1,10}$";
        return productQuantity.matches(regex);
    }
    public static boolean isValidProductDescription(String productDescription) {
        String regex = "^(?=.*\\S).{1,100}$";
        return productDescription.matches(regex);
    }
    public static boolean isValidProductStatus(String productStatus) {
        String regex = "^[0-1]{1}$";
        return productStatus.matches(regex);
    }
}
