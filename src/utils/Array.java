package utils;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Array {

    public static int[] newRandomArray(Scanner sc) {

        int min = 0;
        int max = 0;
        int n = 0;

        boolean correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor mínimo a colocar no array: ");
                min = sc.nextInt();
                sc.nextLine();
                correctInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor máximo a colocar no array: ");
                max = sc.nextInt();
                sc.nextLine();

                if (max < min) {
                    System.out.println("O número máximo tem de ser superior ao número mínimo.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o número de elementos a colocar no array: ");
                n = sc.nextInt();
                sc.nextLine();

                if (n <= 0) {
                    System.out.println("O array tem de ter elementos.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        int[] randomArray = new int[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            randomArray[i] = rand.nextInt (min, max + 1);
        }

        return randomArray;
    }

    public static double[] newDoubleRandomArray(Scanner sc) {

        double min = 0;
        double max = 0;
        int n = 0;

        boolean correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor mínimo a colocar no array: ");
                min = sc.nextDouble();
                sc.nextLine();
                correctInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número.");
                sc.nextLine();
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor máximo a colocar no array: ");
                max = sc.nextDouble();
                sc.nextLine();

                if (max < min) {
                    System.out.println("O número máximo tem de ser superior ao número mínimo.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o número de elementos a colocar no array: ");
                n = sc.nextInt();
                sc.nextLine();

                if (n <= 0) {
                    System.out.println("O array tem de ter elementos.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }

        double[] randomArray = new double[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            randomArray[i] = rand.nextDouble(min, max + 1);
        }

        return randomArray;
    }
	
	public static int[] deleteFromArray(int[] array, int value) {

        int count = 0;
        for (int k : array) {

            if (k == value) {
                count++;
            }
        }

        int[] newArray = new int[array.length - count];

        int j = 0;
        for (int k : array) {

            if (k != value) {
                newArray[j++] = k;
            }
        }
        return newArray;
    }

    public static int[] invertArray(int[] array) {

        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {

            newArray[array.length -1 - i] = array[i];
        }
        return newArray;
    }
	
	public static void invertArrayVoid(int[] array) {

        int temp;
        for (int i = 0; i < array.length / 2; i++) {

            temp = array[array.length -1 -i];
            array[array.length -1 -i] = array[i];
            array[i] = temp;
        }
    }

    public static int[][] newRandom2DArray(Scanner sc) {

        int min = 0;
        int max = 0;
        int nRows = 0;
        int nColumns = 0;

        boolean correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor mínimo a colocar no array bidimensional: ");
                min = sc.nextInt();
                sc.nextLine();
                correctInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o valor máximo a colocar no array bidimensional:");
                max = sc.nextInt();
                sc.nextLine();

                if (max < min) {
                    System.out.println("O número máximo tem de ser superior ao número mínimo.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor introduza um número inteiro.");
                sc.nextLine(); // para gastar o enter senão fica em ciclo infinito
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o número de linhas para criar no array bidimensional: ");
                nRows = sc.nextInt();
                sc.nextLine();

                if (nRows < 2) {
                    System.out.println("O array tem de ter pelo menos 2 linhas.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine(); // para gastar o enter senão fica em ciclo infinito
            }
        }

        correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Introduza o número de colunas para criar no array bidimensional: ");
                nColumns = sc.nextInt();
                sc.nextLine();

                if (nColumns < 2) {
                    System.out.println("O array tem de ter pelo menos 2 colunas.");

                } else {
                    correctInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduza um número inteiro.");
                sc.nextLine();
            }
        }

        int[][] random2DArray = new int[nRows][nColumns];
        Random rand = new Random();

        for (int l = 0; l < nRows; l++) {

            for (int c = 0; c < nColumns; c++) {
                random2DArray[l][c] = rand.nextInt(min, max + 1);
            }
        }

        return random2DArray;
    }

    public static void print2DArray(int[][] array2D) {

        int nRows = array2D.length;
        int nColumns = array2D[0].length;

        for (int[] ints : array2D) {
            System.out.println();

            for (int c = 0; c < nColumns; c++) {
                System.out.print("\t" + ints[c]);
            }
        }
        System.out.println();
    }
}
