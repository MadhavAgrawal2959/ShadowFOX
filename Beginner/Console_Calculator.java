
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
        System.out.println("5.Exit");

        int type_of_operation = scanner.nextInt();
        Boolean another_operation;

        if (type_of_operation == 5) {
            System.out.println("Thank you for using the calculator");
        } else {
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
                    Logarithmic_Exponential_Operations();
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

        if (choice == 7) {
            return;
        } else if (choice == 6) {
            main(null);
        } else {
            System.out.println("Enter the First number ");
            float num1 = scanner.nextInt();
            System.out.println("Enter the Second number ");
            float num2 = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("the Addition of the Two numbers is: " + (num1 + num2));
                    break;
                case 2:
                    System.out.println("the  Substraction the Two numbers is: " + ((num1) - (num2)));
                    break;
                case 3:
                    System.out.println("the Multiplication of the Two numbers is: " + (num1 * num2));
                    break;
                case 4:
                    try {
                        int result = (int) num1 / (int) num2;
                        System.out.println("the Division of two no is" + result);
                    } catch (ArithmeticException e) {
                        System.out.println("Error: Division by zero is not allowed.");
                    }
                    System.out.println("want to rechange the number2 ?(true/false)");
                    boolean change = scanner.nextBoolean();
                    if (change) {
                        num2 = scanner.nextInt();
                        System.out.println("the sum of the Two numbers is: " + (num1 / num2));
                    }
                    break;
                case 5:
                    System.out.println("the sum of the Two numbers is: " + (num1 % num2));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private static void Unit_Conversion() {
        System.out.println("Select the type of operation you want to perform");
        System.out.println("1. Temperature Conversion");
        System.out.println("2. Currency Conversion");
        System.out.println("3. Length Conversion");
        System.out.println("4. Weight Conversion");
        System.out.println("5. Back to Main Menu");
        System.out.println("6. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 6) {
            return;
        } else if (true) {
            main(null);
        } else {
            switch (choice) {
                case 1:
                    Temperature_Conversion();
                    break;
                case 2:
                    Currency_Conversion();
                    break;
                case 3:
                    Length_Conversion();
                    break;
                case 4:
                    Weight_Conversion();
                    break;
                default:
                    throw new AssertionError();
            }
        }

    }

    private static void Temperature_Conversion() {
        System.out.println("Select the type of operation you want to perform");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Back to Main Menu");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4) {
            return;
        } else if (choice == 3) {
            main(null);
        } else {
            System.out.println("Enter the temperature: ");
            float temp = scanner.nextFloat();
            switch (choice) {
                case 1:
                    System.out.println("Temperature in Fahrenheit: " + (temp * 9 / 5 + 32));
                    break;
                case 2:
                    System.out.println("Temperature in Celsius: " + ((temp - 32) * 5 / 9));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private static void Currency_Conversion() {
        System.out.println("Select the type of operation you want to perform");
        System.out.println("1. USD to INR");
        System.out.println("2. INR to USD");
        System.out.println("3. Back to Main Menu");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4) {
            return;
        } else if (choice == 3) {
            main(null);
        } else {
            System.out.println("Enter the amount: ");
            float amount = scanner.nextFloat();
            switch (choice) {
                case 1:
                    System.out.println("Amount in INR: " + (amount * 74.85));
                    break;
                case 2:
                    System.out.println("Amount in USD: " + (amount / 74.85));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private static void Length_Conversion() {
        System.out.println("Select the type of operation you want to perform");
        System.out.println("1. Meter to Kilometer");
        System.out.println("2. Kilometer to Meter");
        System.out.println("3. Back to Main Menu");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4) {
            return;
        } else if (choice == 3) {
            main(null);
        } else {
            System.out.println("Enter the length: ");
            float length = scanner.nextFloat();
            switch (choice) {
                case 1:
                    System.out.println("Length in Kilometer: " + (length / 1000));
                    break;
                case 2:
                    System.out.println("Length in Meter: " + (length * 1000));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private static void Weight_Conversion() {
        System.out.println("Select the type of operation you want to perform");
        System.out.println("1. Gram to Kilogram");
        System.out.println("2. Kilogram to Gram");
        System.out.println("3. Back to Main Menu");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4) {
            return;
        } else if (choice == 3) {
            main(null);
        } else {
            System.out.println("Enter the weight: ");
            float weight = scanner.nextFloat();
            switch (choice) {
                case 1:
                    System.out.println("Weight in Kilogram: " + (weight / 1000));
                    break;
                case 2:
                    System.out.println("Weight in Gram: " + (weight * 1000));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private static void Logarithmic_Exponential_Operations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void Percentage_Calculation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
