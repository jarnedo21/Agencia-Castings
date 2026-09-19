package co.edu.udec.agenciacastings.infrastructure.ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Utilidades para leer datos del teclado. Cada lectura se repite hasta que el valor sea valido.
 */
public class Consola {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Scanner entrada;
    private final PrintStream salida;

    public Consola(Scanner entrada, PrintStream salida) {
        this.entrada = entrada;
        this.salida = salida;
    }

    public void mostrar(String mensaje) {
        salida.println(mensaje);
    }

    public String leerTexto(String etiqueta) {
        while (true) {
            String valor = leerLinea(etiqueta + ": ");
            if (!valor.isBlank()) return valor;
            mostrar("  ! Este campo es obligatorio");
        }
    }

    public String leerTextoOpcional(String etiqueta) {
        String valor = leerLinea(etiqueta + " (Enter para omitir): ");
        return valor.isBlank() ? null : valor;
    }

    public int leerEntero(String etiqueta) {
        while (true) {
            String valor = leerLinea(etiqueta + ": ");
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                mostrar("  ! Ingrese un numero entero");
            }
        }
    }

    public double leerDecimal(String etiqueta) {
        while (true) {
            String valor = leerLinea(etiqueta + ": ").replace(',', '.');
            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                mostrar("  ! Ingrese un numero (ej: 1.75)");
            }
        }
    }

    public LocalDate leerFecha(String etiqueta) {
        while (true) {
            String valor = leerLinea(etiqueta + " (dd/MM/aaaa): ");
            try {
                return LocalDate.parse(valor, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                mostrar("  ! Fecha invalida, use el formato dd/MM/aaaa");
            }
        }
    }

    public <E extends Enum<E>> E leerEnum(String etiqueta, Class<E> tipo) {
        E[] valores = tipo.getEnumConstants();
        mostrar(etiqueta + ":");
        for (int i = 0; i < valores.length; i++) {
            mostrar("  " + (i + 1) + ". " + valores[i]);
        }
        return valores[leerOpcion(valores.length) - 1];
    }

    /**
     * Muestra una lista numerada y devuelve el elemento escogido, o null si la lista esta vacia.
     */
    public <T> T seleccionar(String etiqueta, List<T> elementos, Function<T, String> descripcion) {
        if (elementos.isEmpty()) {
            mostrar("  ! No hay registros de " + etiqueta + ". Registre uno primero.");
            return null;
        }
        mostrar("Seleccione " + etiqueta + ":");
        for (int i = 0; i < elementos.size(); i++) {
            mostrar("  " + (i + 1) + ". " + descripcion.apply(elementos.get(i)));
        }
        return elementos.get(leerOpcion(elementos.size()) - 1);
    }

    public boolean confirmar(String pregunta) {
        String valor = leerLinea(pregunta + " (s/n): ");
        return valor.equalsIgnoreCase("s") || valor.equalsIgnoreCase("si");
    }

    private int leerOpcion(int maximo) {
        while (true) {
            int opcion = leerEntero("Opcion");
            if (opcion >= 1 && opcion <= maximo) return opcion;
            mostrar("  ! Elija un numero entre 1 y " + maximo);
        }
    }

    private String leerLinea(String prompt) {
        salida.print(prompt);
        if (!entrada.hasNextLine()) {
            throw new FinDeEntradaException();
        }
        return entrada.nextLine().trim();
    }

    /** Se lanza cuando ya no hay mas datos en la entrada (por ejemplo, Ctrl+D o fin de archivo). */
    public static class FinDeEntradaException extends RuntimeException {
        public FinDeEntradaException() {
            super("Fin de la entrada");
        }
    }
}
