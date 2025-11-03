package entrega.psp.entrega.tamagochis.maite;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cuidador {

	private static final int PARTICIPANTES = 5;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// Programa principal. Se encarga de crear y gestionar los Tamagochis
		ArrayList<Tamagochi> listaTamagochis = new ArrayList<>();
		// 1 - Crear Tamagochis
		listaTamagochis = crearTamagochis();

		// 2- los lanzamos en su propio hilo
		for (Tamagochi t : listaTamagochis) {
			Thread hilo = new Thread(t);
			hilo.start();
			System.out.println("Los tamagochis han empezado a vivir");

		}

		// 2 -Ordenes del tamagochi
		menu(scan, listaTamagochis);

		// 3- Comunicación entre Tamagochis
	}

	// Creamos los tamagochis
	public static ArrayList<Tamagochi> crearTamagochis() {
		Random rnd = new Random();
		ArrayList<Tamagochi> lista = new ArrayList<>();
		Tamagochi t;
		for (int i = 0; i < PARTICIPANTES; i++) {

			String nombre = "t" + i;
			long ritmoComida = rnd.nextLong(10000, 80000);

			t = new Tamagochi(nombre, ritmoComida);
			lista.add(t);
		}
		return lista;

	}

	public static void menu(Scanner scan, ArrayList<Tamagochi> listaTamagochis) {

		String opcion = "";
		do {

			System.out.println("MENÚ TAMAGOCHI");
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

			case "1":
				alimentarse(scan, listaTamagochis);
				break;
			case "2":
				jugar(scan, listaTamagochis);
				break;
			case "3":
				limpiar(scan, listaTamagochis);
				break;
			case "4":
				consultarEstado(scan, listaTamagochis);
				break;
			case "5":
			    matar(scan, listaTamagochis);
			    break;
			case "6":
				System.out.println("Salir");
				break;
			}

		} while (!opcion.equals("6"));
	}

	public static void alimentarse(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {

		Tamagochi t = seleccionar(scan, listaTamagochi);

		if (t != null) {

			t.alimentar();
		}

	}

	public static void jugar(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {

		Tamagochi t = seleccionar(scan, listaTamagochi);

		if (t != null) {

			t.jugar();
		}

	}

	public static void limpiar(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {

		Tamagochi t = seleccionar(scan, listaTamagochi);

		if (t != null) {

			t.limpiar();
		}
	}

	public static void matar(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {

		String motivo = "El cuidador lo ha matado";
		Tamagochi t = seleccionar(scan, listaTamagochi);

		if (t != null) {

			t.morir(motivo);
		}
	}

	public static void consultarEstado(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {
		
		System.out.println("\n===== ESTADO DE TAMAGOTCHIS =====");
	    for (Tamagochi t : listaTamagochi) {
	        System.out.printf(
	            "%s → Vivo: %b | Ocupado: %b | Suciedad: %d%n",
	            t.getNombre(), t.isEstaVivo(), t.isOcupado(), t.getSuciedad()
	        );
	    }
	    System.out.println("=================================\n");

	}

	public static Tamagochi seleccionar(Scanner scan, ArrayList<Tamagochi> listaTamagochi) {

		System.out.println("Elige un tamagochi");
		for (int i = 0; i < listaTamagochi.size(); i++) {
			Tamagochi t = listaTamagochi.get(i);
			System.out.printf("%d. %s (vivo: %b, ocupado: %b)%n", i + 1, t.getNombre(), t.isEstaVivo(), t.isOcupado());
		}
		int indice = Integer.parseInt(scan.nextLine()) - 1;
		if (indice >= 0 && indice < listaTamagochi.size()) {
			return listaTamagochi.get(indice);
		}
		return null;

	}

}
