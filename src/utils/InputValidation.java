package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidation {

    public static int validateIntGE0(Scanner sc, String message) {

        while(true) {

            try {
                System.out.println(message);
                int value = sc.nextInt();
                sc.nextLine();

                if (value >= 0) {
                    return value;

                } else {
                    System.out.println("Por favor, introduza um número inteiro maior ou igual a zero.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro maior ou igual a zero.");
                sc.nextLine();
            }
        }
    }

    public static int validateIntBetween(Scanner sc, String message, int min, int max) {

        System.out.print(message);

        while(true) {

            try {
                int value = sc.nextInt();
                sc.nextLine();

                if (value >= min && value <= max) {
                    return value;

                } else {
                    System.out.println("Por favor, introduza um número inteiro entre " + min + " e " + max);
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro entre " + min + " e " + max);
                sc.nextLine();
            }
        }
    }

    public static int validateIntGT0(Scanner sc, String text) {

        while (true) {
            try {
                System.out.print(text);
                int value = sc.nextInt();
                sc.nextLine();

                if (value <= 0) {
                    System.out.println("Por favor, introduza um número inteiro maior do que 0.");

                } else {
                    return value;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }
    }

    public static int validateIntGTN(Scanner sc, String text, int n) {

        while (true) {
            try {
                System.out.print(text);
                int value = sc.nextInt();
                sc.nextLine();

                if (value <= n) {
                    System.out.println("Por favor, introduza um número inteiro maior do que " + n + ".");

                } else {
                    return value;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro maior do que 0 " + n + ".");
                sc.nextLine();
            }
        }
    }

    public static int validateInt(Scanner sc, String text) {

        while (true) {
            try {
                System.out.print(text);
                int value = sc.nextInt();
                sc.nextLine();

                return value;

            } catch (InputMismatchException e) {
                System.out.println("Por favor introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }
    }

    public static double validateDoubleGT0(Scanner sc, String text) {

        while (true) {
            try {
                System.out.print(text);
                double value = sc.nextDouble();
                sc.nextLine();

                if (value < 0) {
                    System.out.println("Por favor introduza um double maior do que 0.");

                } else {
                    return value;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor introduza um double maior do que 0");
                sc.nextLine();
            }
        }
    }
}
