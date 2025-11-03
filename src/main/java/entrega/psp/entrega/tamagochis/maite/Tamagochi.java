package entrega.psp.entrega.tamagochis.maite;

import java.util.Random;
import java.util.Scanner;

public class Tamagochi implements Runnable {

	Scanner scan = new Scanner(System.in);

	private long ritmoComida; // Controlo el tiempo que tarda en comer
	private int suciedad = 0;
	private boolean estaVivo = true;
	private long tiempoInicio;
	private boolean ocupado = false;

	// getters
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public long getRitmoComida() {
		return ritmoComida;
	}

	public int getSuciedad() {
		return suciedad;
	}

	public boolean isEstaVivo() {
		return estaVivo;
	}

	public long getTiempoInicio() {
		return tiempoInicio;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public Tamagochi(String nombre, long ritmoComida) {
		super();
		this.nombre = nombre;
		this.ritmoComida = ritmoComida;
		this.tiempoInicio = System.currentTimeMillis(); // Para controlar el tiempo de vida.
	}

	@Override
	public void run() {

		while (estaVivo) {

			// Controlas la suciedad

			try {
			
				Thread.sleep(20000);
				suciedad += 1;
				if (suciedad == 5) {
					System.out.println("Estoy empezando a estar muy sucio...");
				}

				if (suciedad >= 10) {

					morir("ha muerto por sucio.");
				}

				if (System.currentTimeMillis() - tiempoInicio > 300000) {
					// 300.000 ms son 5 minutos
					morir("ha muerto de viejo");
				}

			} catch (InterruptedException e) {
				// No necesito sacar mensaje, aquí entra si el cuidador le interrumpe.

			}

		}

	}

	public void morir(String motivo) {

		if (!estaVivo)
			return; // Si ya no está no se puede matar
		estaVivo = false;
		ocupado = false;
		System.out.println(nombre + " " + motivo);
	}

	public void alimentar() {

		if (ocupado || !estaVivo)
			return;
		ocupado = true;

		try {
			System.out.println("Empiezo a comer");
			Thread.sleep(ritmoComida);
			System.out.println("He terminado de comer");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ocupado = false;
		}

	}

	public void jugar() {

		if (ocupado || !estaVivo)
			return;
		ocupado = true;
		Random rnd = new Random();
		int num1 = rnd.nextInt(0, 10);
		int num2 = rnd.nextInt(0, 10);
		int resultado = num1 + num2;
		int result = 0;
		System.out.println("VAMOS A JUGAR A SUMAS");
		do {
			System.out.println(num1 + " + " + num2 + "= ");
			String resultadoStr = scan.nextLine();
			result = Integer.parseInt(resultadoStr);

		} while (resultado != result);

		System.out.println("¡Acertaste!");

	}

	public void limpiar() {

		if (ocupado || !estaVivo)
			return;
		ocupado = true;

		try {
			System.out.println("Empiezo a limpiarme");
			Thread.sleep(5000);

			suciedad = 0;
			System.out.println("Me he limpiado");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ocupado = false;
		}
	}

}
