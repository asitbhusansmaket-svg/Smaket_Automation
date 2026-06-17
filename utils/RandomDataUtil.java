package utils;

import java.util.Random;

public class RandomDataUtil {

    private static final Random random = new Random();

    // Random numeric cart number (e.g. 10001)
    public static String randomCartNumber() {
        return String.valueOf(10000 + random.nextInt(90000));
    }

    // Random machine number (e.g. MACH-342)
    public static String randomMachineNumber() {
        return "MACH-" + (100 + random.nextInt(900));
    }

    // Fixed or random prefix
    public static String cartPrefix() {
        return "CRT";
    }
}
