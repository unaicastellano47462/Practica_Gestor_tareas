package Utilidades;

import java.util.Scanner;

public class EntradaDatos {

    public static int leerEntero(Scanner sc, String mensaje) {
        int num = 0;
        boolean error = true;
        try {
            num = 0;
            do
            {
                System.out.print(mensaje);
                try {
                    num = Integer.parseInt(sc.nextLine());
                    // También se puede con nextInt pero así me ahorro el limpiar el buffer después.
                    error = false;
                } catch (NumberFormatException e) {
                    System.out.println("Error: introduce un número entero válido.");
                }
            }
            while (error);
            return num;
        }
        catch (Exception e) {
            System.out.println("Error leyendo el número: " + e.getMessage());
            return -1;
        }
    }

    public static String leerTexto(Scanner sc, String mensaje,String expresionRegular) {
        try
        {
            String texto;
            do
            {
                System.out.print(mensaje);
                texto = sc.nextLine();
                if (!texto.matches(expresionRegular))
                {
                    System.out.println("Error: formato no válido. Inténtalo de nuevo.");
                }
            } while (!texto.matches(expresionRegular));
            return texto;
        }
        catch (Exception e)
        {
            System.out.println("Error leyendo el texto: " + e.getMessage());
            return "";
        }
    }

    public static boolean leerBooleano(Scanner sc, String mensaje) {
        boolean valor = false;
        boolean error = true;
        try {
            do {
                System.out.print(mensaje);
                String input = sc.nextLine().trim().toLowerCase();
                if (input.equals("true") || input.equals("false")) {
                    valor = Boolean.parseBoolean(input);
                    error = false;
                } else {
                    System.out.println("Error: introduce 'true' o 'false'.");
                }
            } while (error);
            return valor;
        } catch (Exception e) {
            System.out.println("Error leyendo el valor booleano: " + e.getMessage());
            return false;
        }
    }
}

