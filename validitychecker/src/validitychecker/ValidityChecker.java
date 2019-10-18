package validitychecker;

import java.util.ArrayList;

public class ValidityChecker {
    public static void main(String[] args) {
        String a = null;
        validityChecker(new String[]{"validityCheckNotNull"}, a);
    }

    private static Boolean validityChecker(String[] checks, String data) { //Maybe redo with enums?
        Boolean valid = true;
        String validityCheck;
        for (String string : checks) {
            validityCheck = string;
            if (string == "validityCheckNotNull") {
                valid = validityCheckNotNull(data);
            } else if (string == "validityCheckSSN") {
                valid = validityCheckSSN(data);
            }
            if (!valid) {
                System.out.println(data + " did not pass " + validityCheck + ".");
                return false;
            }
        }
        System.out.println("All validity checks passed!");
        return true;
    }

    private static boolean validityCheckNotNull(String data) {
        if (data == null) {
            return false;
        }
        return true;
    }

    private static boolean validityCheckSSN(String data) {
        data = data.replaceAll("[^0-9]", "");
        if (!(data.length() == 12 || data.length() == 10)) {
            System.out.println("Length of SSN is weird, yo!");
            return false;
        }
        if (data.length() == 12) {
            data = data.substring(2);
        }       
        
        ArrayList<Integer> values = new ArrayList<Integer>();
        int num;
        for (int i = 0; i < data.length() - 1; i++) {
            num = Character.getNumericValue(data.charAt(i));
            if(i % 2 == 0) {
                values.add(handleEvenIndexValues(num));
            } else {
                values.add(num);
            }
        }
        int sum = 0;
        for (Integer integer : values) {
            sum += integer;
        }

        int resultNum = (10 - sum % 10) % 10;
        int controlNum = Integer.parseInt(data.substring(data.length() - 1));

        System.out.println("data: " + data);        
        System.out.println("values: " + values);
        System.out.println("resultNum: " + resultNum);
        System.out.println("controlNum: " + controlNum);

        if (resultNum == controlNum) {
            return true;
        }
        return false;
    }

    private static Integer handleEvenIndexValues(Integer num) {
        num*=2;
        if (num > 9) {
            int term1 = num % 10;
            int term2 = (num - num % 10) / 10;
            num = term1 + term2;
        }
        return num;
    }
}