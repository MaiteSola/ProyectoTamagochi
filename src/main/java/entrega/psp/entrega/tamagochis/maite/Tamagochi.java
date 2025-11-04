package entrega.psp.entrega.tamagochis.maite;

import java.util.Random;
import java.util.Scanner;

public class Tamagochi implements Runnable {

    private final Scanner scan = new Scanner(System.in);
    private final long ritmoComida; 
    private int suciedad = 0;
    private boolean estaVivo = true;
    private final long tiempoInicio;
    private boolean ocupado = false;
    private final String nombre;

    // ======== Getters ========
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

    // ======== Constructor ========
    public Tamagochi(String nombre, long ritmoComida) {
        this.nombre = nombre;
        this.ritmoComida = ritmoComida;
        this.tiempoInicio = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (estaVivo) {
            try {
                Thread.sleep(20000);
                suciedad++;
                if (suciedad == 5) {
                    System.out.println(nombre + ": Estoy empezando a estar muy sucio...");
                }
                if (suciedad >= 10) {
                    morir("ha muerto por suciedad.");
                }
                if (System.currentTimeMillis() - tiempoInicio > 300000) { // 5 minutos
                    morir("ha muerto de viejo.");
                }
            } catch (InterruptedException e) {
            	// Si quiero que se pueda interrumpir lo hago aquí.
            }
        }
    }

    public void morir(String motivo) {
        if (!estaVivo) return;
        estaVivo = false;
        ocupado = false;
        System.out.println(nombre + " " + motivo);
    }

    public void alimentar() {
        if (ocupado || !estaVivo) return;
        ocupado = true;
        try {
            System.out.println(nombre + ": Empiezo a comer...");
            try {
                Thread.sleep(ritmoComida);
            } catch (InterruptedException ignored) {
            	// Si quiero que se pueda interrumpir lo hago aquí.
            }
            System.out.println(nombre + ": He terminado de comer.");
        } finally {
            ocupado = false;
        }
    }

    public void jugar() {
        if (ocupado || !estaVivo) return;
        ocupado = true;
        try {
            Random rnd = new Random();
            int num1 = rnd.nextInt(10);
            int num2 = rnd.nextInt(10);
            int resultado = num1 + num2;
            int respuesta = -1;

            System.out.println(nombre + ": ¡VAMOS A JUGAR A SUMAS!");
            do {
                System.out.print(num1 + " + " + num2 + " = ");
                try {
                    respuesta = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Introduce un número válido.");
                }
            } while (resultado != respuesta);

            System.out.println(nombre + ": ¡Acertaste!");
        } finally {
            ocupado = false;
        }
    }

    public void limpiar() {
        if (ocupado || !estaVivo) return;
        ocupado = true;
        try {
            System.out.println(nombre + ": Empiezo a limpiarme...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
                // Si quiero que se pueda interrumpir lo hago aquí.
            }
            suciedad = 0;
            System.out.println(nombre + ": ¡Me he limpiado!");
        } finally {
            ocupado = false;
        }
    }
}
