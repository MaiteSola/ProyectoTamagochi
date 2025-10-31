package entrega.psp.entrega.tamagochis.maite;

public class Tamagochi implements Runnable {


	private int ritmoComida; // Controlo el tiempo que tarda en comer
	private int suciedad = 0;
	private boolean estaVivo = true;
	private long tiempoInicio;
	private boolean ocupado = false;
	
	//getters
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public int getRitmoComida() {
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

	

	public Tamagochi(String nombre, int ritmoComida) {
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
				Thread.sleep(2000);
				suciedad += 1;
				if (suciedad == 5) {
					System.out.println("Estoy empezando a estar muy sucio...");
				}

				if (suciedad >= 10) {

					morir("ha muerto por sucio.");
				}

				if (System.currentTimeMillis() - tiempoInicio > 300000) {
					// 30000 son 5 minutos en milisegundos
					morir("ha muerto de viejo");
				}

			} catch (InterruptedException e) {
				// No necesito sacar mensaje, aquí entra si el cuidador le interrumpe.

			}

		}

	}

	
	
	public synchronized void morir(String motivo) {

		if (!estaVivo)
			return; // Si ya no está no se puede matar
		estaVivo = false;
		System.out.println(nombre + " " + motivo);

	}

}
