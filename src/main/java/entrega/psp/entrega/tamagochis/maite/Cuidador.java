package entrega.psp.entrega.tamagochis.maite;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cuidador {

    private static final int PARTICIPANTES = 5;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Tamagochi> listaTamagochis = crearTamagochis();

        // Lanzar hilos
        for (Tamagochi t : listaTamagochis) {
            Thread hilo = new Thread(t);
            hilo.start();
        }
        System.out.println("Los tamagochis han empezado a vivir.");

        // Menú principal
        menu(scan, listaTamagochis);
        scan.close();
    }

    public static ArrayList<Tamagochi> crearTamagochis() {
        Random rnd = new Random();
        ArrayList<Tamagochi> lista = new ArrayList<>();
        for (int i = 0; i < PARTICIPANTES; i++) {
            String nombre = "T" + (i + 1);
            long ritmoComida = rnd.nextLong(1000, 8000);
            lista.add(new Tamagochi(nombre, ritmoComida));
        }
        return lista;
    }

    public static void menu(Scanner scan, ArrayList<Tamagochi> listaTamagochis) {
        String opcion;
        do {
            System.out.println("\n===== MENÚ TAMAGOCHI =====");
            System.out.println("1. Alimentar Tamagotchi");
            System.out.println("2. Jugar con Tamagotchi");
            System.out.println("3. Limpiar Tamagotchi");
            System.out.println("4. Ver estado de todos");
            System.out.println("5. Matar un Tamagotchi");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = scan.nextLine();

            switch (opcion) {
                case "1": alimentarse(scan, listaTamagochis);
                case "2": jugar(scan, listaTamagochis);
                case "3": limpiar(scan, listaTamagochis);
                case "4": consultarEstado(listaTamagochis);
                case "5": matar(scan, listaTamagochis);
                case "6": System.out.println("Saliendo del programa...");
                default : System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (!opcion.equals("6"));
    }

    public static void alimentarse(Scanner scan, ArrayList<Tamagochi> lista) {
        Tamagochi t = seleccionar(scan, lista);
        if (t != null) t.alimentar();
    }

    public static void jugar(Scanner scan, ArrayList<Tamagochi> lista) {
        Tamagochi t = seleccionar(scan, lista);
        if (t != null) t.jugar();
    }

    public static void limpiar(Scanner scan, ArrayList<Tamagochi> lista) {
        Tamagochi t = seleccionar(scan, lista);
        if (t != null) t.limpiar();
    }

    public static void matar(Scanner scan, ArrayList<Tamagochi> lista) {
        Tamagochi t = seleccionar(scan, lista);
        if (t != null) t.morir("El cuidador lo ha matado.");
    }

    public static void consultarEstado(ArrayList<Tamagochi> lista) {
        System.out.println("\n===== ESTADO DE TAMAGOCHIS =====");
        for (Tamagochi t : lista) {
            System.out.printf("%s → Vivo: %b | Ocupado: %b | Suciedad: %d%n",
                    t.getNombre(), t.isEstaVivo(), t.isOcupado(), t.getSuciedad());
        }
        System.out.println("=================================\n");
    }

    public static Tamagochi seleccionar(Scanner scan, ArrayList<Tamagochi> lista) {
        System.out.println("\nElige un Tamagochi:");
        for (int i = 0; i < lista.size(); i++) {
            Tamagochi t = lista.get(i);
            System.out.printf("%d. %s (Vivo: %b, Ocupado: %b)%n",
                    i + 1, t.getNombre(), t.isEstaVivo(), t.isOcupado());
        }

        try {
            int indice = Integer.parseInt(scan.nextLine()) - 1;
            if (indice >= 0 && indice < lista.size()) {
                return lista.get(indice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
        }
        System.out.println("Selección no válida.");
        return null;
    }
}