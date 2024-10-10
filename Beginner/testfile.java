import java.util.Scanner;

public class testfile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 0) {
            throw new AssertionError("no cannot be zero");
        }
        System.out.println("enter diff no.");
    }
}
