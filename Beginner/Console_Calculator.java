
import java.util.Scanner;

public class Console_Calculator {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Console Calculator in Java");
        System.out.println("Select the type of operation you want to perform:");
        System.out.println("1. Airthmatic Operations");
        System.out.println("2. Scientific Operations");
        System.out.println("3. Unit Conversion");
        System.out.println("4. Perccentage Calculation");

        int type_of_operation = scanner.nextInt();
        Boolean another_operation ;

        switch (type_of_operation) {
            case 1:
                Airthmatic_Operations();
                System.out.println("Do you want to perform another operation? (true/false)");
                another_operation = scanner.nextBoolean();
                if (another_operation) {
                    main(null);
                }
                break;
            case 2:
                Scientific_Operations();
                System.out.println("Do you want to perform another operation? (true/false)");
                another_operation = scanner.nextBoolean();
                if (another_operation) {
                    main(null);
                }
                break;
            case 3:
                Unit_Conversion();
                System.out.println("Do you want to perform another operation? (true/false)");
                another_operation = scanner.nextBoolean();
                if (another_operation) {
                    main(null);
                }
                break;
            case 4:
                Percentage_Calculation();
                System.out.println("Do you want to perform another operation? (true/false)");
                another_operation = scanner.nextBoolean();
                if (another_operation) {
                    main(null);
                }
                break;
            default:
                throw new AssertionError();
        }
        System.out.println("Thank you for using the calculator");
    }

    public static void Airthmatic_Operations() {
        System.out.println("Select the type of operation you want to perform:");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Modulus");
        System.out.println("6. Back to Main Menu");
        System.out.println("7. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        System.out.println("Enter the First number ");
        int num1 = scanner.nextInt();
        System.out.println("Enter the Second number ");
        int num2 = scanner.nextInt();

        if (choice == 7) {
            return;
        }

        switch(choice) {
            case 1:
                System.out.println("the sum of the Two numbers is: " + (num1 + num2));
                break;
            case 2:
                System.out.println("the sum of the Two numbers is: " + ((num1) - (num2)));
                break;
            case 3:
                System.out.println("the sum of the Two numbers is: " + (num1 * num2));
                break;
            case 4:
                System.out.println("the sum of the Two numbers is: " + (num1 / num2));
                break;
            default:
                throw new AssertionError();
        }
    }

    private static void Unit_Conversion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void Scientific_Operations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void Percentage_Calculation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
