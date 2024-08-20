package ra.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class InputMethods {
    private static final String ERROR_ALERT = "Định dạng không hợp lệ! Vui lòng thử lại";
    private static final String EMPTY_ALERT = "Trường nhập vào không thể để trống, Vui lòng thử lại";
    /*========================================Input Method Start========================================*/
    public static String getString() {
        while (true) {
            String result = getInput();
            if (result.equals("")) {
                System.err.println("Không được để trống");
                continue;
            }
            return result;
        }
    }
    public static char getChar() {
        return getString().charAt(0);
    }
    public static boolean getBoolean() {
        String result = getString();
        return result.equalsIgnoreCase("true");
    }
    public static byte getByte() {
        while (true) {
            try {
                return Byte.parseByte(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    public static short getShort() {
        while (true) {
            try {
                return Short.parseShort(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    public static int getInteger() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    public static float getFloat() {
        while (true) {
            try {
                return Float.parseFloat(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    // Nhập ngày tháng
    public static LocalDate getDate(){
        while (true) {
            try {
                return LocalDate.parse(getInput(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException errException) {
                System.err.println("Ngày tháng không đúng định dạng");
            }
        }
    }

    /*========================================Input Method End========================================*/

    private static String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static void pressAnyKey() {
        getInput();
    }
    /*========================================Other Method========================================*/
}
