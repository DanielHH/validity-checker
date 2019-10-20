package validitychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ValidityChecker {
    public static void main(String[] args) {
        final String[] availableValidityChecks = {"NotNull", "SSN"};
        String data = null;
        String input;
        ArrayList<String> checksToPerform = new ArrayList<String>();

        Scanner in = new Scanner(System.in);

        System.out.println("Enter data: ");
        data = in.nextLine();

        System.out.println("\nThese are the validitychecks offered:");
        for (String check : availableValidityChecks) {
            System.out.println(check);
        }
        System.out.println("\nEnter desired validitychecks in order of operation. Separate with ENTER. When done, just press ENTER.: ");
        while (true) {
            input = in.nextLine();
            if (Arrays.asList(availableValidityChecks).contains(input)) {
                if (!checksToPerform.contains(input)) {
                    checksToPerform.add(input);
                    System.out.println(input + " added to validitychecks to perform. \n");
                } else {
                    System.out.println("You have already added this validitycheck");
                }
            } else if (input.isEmpty()) {
                if (checksToPerform.isEmpty()) {
                    System.out.println("Please enter a validitycheck:");
                } else {
                    break;
                }
            } else {
                System.out.println("Not a validitycheck..");
            }
        }
        System.out.println("Performing validitychecks... \n");
        validityChecker(checksToPerform, data);
    }

    private static Boolean validityChecker(ArrayList<String> checks, String data) { //Maybe redo with enums?
        Boolean valid = true;
        String validityCheck;
        for (String check : checks) {
            validityCheck = check;
            if (check.equals("NotNull")) {
                valid = validityCheckNotNull(data);
            } else if (check.equals("SSN")) {
                valid = validityCheckSSN(data);
            }
            if (!valid) {
                System.out.println(data + " did not pass " + validityCheck + ".");
                return false;
            }
        }
        System.out.println("All validitychecks passed!");
        return true;
    }

    private static boolean validityCheckNotNull(String data) {
        return data != null;
    }

    private static boolean validityCheckSSN(String data) {
        data = data.replaceAll("[^0-9]", "");
        if (!(data.length() == 12 || data.length() == 10)) { //length of SSN is weird
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
        return resultNum == controlNum;
    }

    private static Integer handleEvenIndexValues(Integer num) {
        num *= 2;
        if (num > 9) {
            int term1 = num % 10;
            int term2 = (num - num % 10) / 10;
            num = term1 + term2;
        }
        return num;
    }
}