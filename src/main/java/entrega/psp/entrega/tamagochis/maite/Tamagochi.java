package entrega.psp.entrega.tamagochis.maite;

import java.util.Random;
import java.util.Scanner;

public class Tamagochi implements Runnable {

    private final Scanner scan = new Scanner(System.in);
    private final long ritmoComida;
    private int suciedad = 0;
    private boolean estaVivo = true;
    private final long tiempoInicio;
    private final String nombre;
    private Estados estado;

    public String getNombre() { return nombre; }
    public long getRitmoComida() { return ritmoComida; }
    public int getSuciedad() { return suciedad; }
    public boolean isEstaVivo() { return estaVivo; }
    public long getTiempoInicio() { return tiempoInicio; }
    public Estados getEstado() { return estado; }

    public Tamagochi(String nombre, long ritmoComida) {
        this.nombre = nombre;
        this.ritmoComida = ritmoComida;
        this.tiempoInicio = System.currentTimeMillis();
        this.estado = Estados.OCIOSO;
    }

    @Override
    public void run() {
        while (estaVivo) {
            try {
            	
                // Si está alimentándose o limpiándose, no avanza su vida normal
                if (estado == Estados.ALIMENTANDOSE) {
                    alimentarseProceso();
                    continue; // vuelve al inicio del bucle
                }

                if (estado == Estados.LIMPIANDOSE) {
                    limpiarProceso();
                    continue;
                }

                if (estado == Estados.MUERTO) {
                    morir(nombre + ": El cuidador lo ha matado.");
                    break;
                }

                // Solo si está ocioso sigue "viviendo"
                if (estado == Estados.OCIOSO) {
                	Thread.sleep(20000);
                    suciedad++;

                    if (suciedad == 5) {
                        System.out.println("\n" + nombre + ": Estoy empezando a estar sucio...");
                    }

                    if (suciedad >= 10) {
                        morir("\nHa muerto por suciedad.");
                    }

                    if (System.currentTimeMillis() - tiempoInicio > 300000) {
                        morir("\nHa muerto de viejo.");
                    }
                }

            } catch (InterruptedException ignored) {}
        }
    }

    private void alimentarseProceso() {
        if (!estaVivo) return;
        System.out.println("\n"+nombre + ": Empiezo a comer...");
        try {
            Thread.sleep(ritmoComida);
        } catch (InterruptedException ignored) {}
        System.out.println("\n"+nombre + ": He terminado de comer.");
        estado = Estados.OCIOSO;
    }

    private void limpiarProceso() {
        if (!estaVivo) return;
        System.out.println("\n"+nombre + ": Empiezo a limpiarme...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
        suciedad = 0;
        System.out.println("\n"+nombre + ": ¡Ya estoy limpio!");
        estado = Estados.OCIOSO;
    }

    public void alimentar() {
        if (!estaVivo || estado != Estados.OCIOSO) return;
        estado = Estados.ALIMENTANDOSE;
    }

    public void limpiar() {
        if (!estaVivo || estado != Estados.OCIOSO) return;
        estado = Estados.LIMPIANDOSE;
    }

    public void jugar() {
        if (!estaVivo || estado != Estados.OCIOSO) return;
        estado = Estados.JUGANDO;
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

            System.out.println("\n"+nombre + ": ¡Acertaste!");
        } finally {
            estado = Estados.OCIOSO;
        }
    }

    public void morir(String motivo) {
        if (!estaVivo) return;
        estaVivo = false;
        estado = Estados.MUERTO;
        System.out.println("\n"+nombre + " " + motivo);
    }
}
