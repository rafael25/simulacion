package capitulo5;

import javax.swing.*;

public class Figura5_4 {
    public static final int EMPLEADO_LIBRE = 0;
    public static final int EMPLEADO_OCUPADO = 1;
    public static final int NO_DEFINIDO =  -1;

    private int numeroClientes;
    private float llegada;
    private float marcha;
    private float D;
    private float Dmax;
    private float R;
    private float Tc;
    private float S;
    private float tiempoServicio;
    private int n;
    private int nOut;
    private int nMinuto;
    private float ti;
    private float reloj;
    private float ultimoEvento;
    private int estadoEmpleado;

    private Cola<Float> cola;
    private Generador generador;

    /*******************************
     *                             *
     *   Rutina de inicialización  *
     *                             *
     *******************************/
    public Figura5_4(float mediaLlegada, float mediaServicio, int numeroClientes, int estadoEmpleado) {
        this.numeroClientes = numeroClientes;
        this.estadoEmpleado = estadoEmpleado;

        this.marcha = NO_DEFINIDO;

        this.cola = new Cola<Float>();
        this.generador = new Generador(mediaLlegada, mediaServicio);

        llegada = generador.generarSiguienteLlegada(reloj);
    }

    /*******************************
     *                             *
     *     Rutina de ejecución     *
     *                             *
     *******************************/
    public static void main(String args[]) {

        /* Entrada de datos */
        float m1 = Float.parseFloat(JOptionPane.showInputDialog("Media del intervalo entre llegadas: "));
        float m2 = Float.parseFloat(JOptionPane.showInputDialog("Media del tiempo de servicio: "));
        int c = Integer.parseInt(JOptionPane.showInputDialog("Num. clientes cuyo tiempo de espera debe simularse: "));

        Figura5_4 fig = new Figura5_4(m1, m2, c, EMPLEADO_LIBRE);
        fig.informeEstadoSimulacion();

        do {
            fig.siguienteEvento();
            fig.informeEstadoSimulacion();
        } while (! fig.esFinalDeSimulacion());

        fig.informeFinal();
    }

    /*******************************
     *                             *
     *     Rutinas de eventos      *
     *                             *
     *******************************/
    public void eventoLlegada() {
        ti = reloj;
        llegada = generador.generarSiguienteLlegada(reloj);

        if (estadoEmpleado == EMPLEADO_LIBRE) {
            estadoEmpleado = EMPLEADO_OCUPADO;
            n += 1;
            marcha = generador.generarTiempoServicio(reloj);
            tiempoServicio = marcha - reloj;
            ultimoEvento = reloj;
        } else if (estadoEmpleado == EMPLEADO_OCUPADO) {
            R = R + cola.longitud() * (reloj - ultimoEvento);
            cola.agregar(reloj);
            ultimoEvento = reloj;
        }
    }

    public void eventoMarcha() {
        float tiempoEnCola;
        Tc = Tc + reloj - ti;
        nOut += 1;
        S += tiempoServicio;

        if (cola.estaVacia()) {
            estadoEmpleado = EMPLEADO_LIBRE;
            marcha = NO_DEFINIDO;
            ultimoEvento = reloj;
        } else {
            n += 1;
            R = R + cola.longitud() * (reloj - ultimoEvento);
            Float e = cola.extraerSiguiente();
            tiempoEnCola = reloj - e;
            D = D + tiempoEnCola;
            Dmax = (tiempoEnCola > Dmax)? tiempoEnCola : Dmax;
            nMinuto = (tiempoEnCola > 1)? nMinuto + 1 : nMinuto;
            marcha = generador.generarTiempoServicio(reloj);
            tiempoServicio = marcha - reloj;
            ultimoEvento = reloj;
        }
    }

    /*******************************
     *                             *
     *     Rutinas de informes     *
     *                             *
     *******************************/
    public void informeEstadoSimulacion() {
        System.out.println("-------------- Reloj: " + reloj);
        String empleado = (estadoEmpleado == EMPLEADO_LIBRE)? "LIBRE" : "OCUPADO";
        System.out.println("Empleado: " + empleado + "\t\tUltimo evento: " + ultimoEvento);
        String estadoCola = (cola.estaVacia()) ? "Cola vacia" : cola.toString();
        System.out.println("Cola: (" + cola.longitud() + ") "  + estadoCola);
        String marcha = (this.marcha == NO_DEFINIDO)? "--" : this.marcha +"";
        System.out.println("Lista de eventos: \t\tLlegada: " + llegada + "\t\tMarcha: " + marcha);
        System.out.println("Contadores estadisticos:");
        System.out.println("\t\tNumero de clientes que han empezado a ser atendidos: " + n);
        System.out.println("\t\tTiempo total de espera de los clientes en la cola: " + D);
        System.out.println("\t\tIntegral num. clientes en la cola respecto al tiempo: " + R);
        System.out.println("\t\tTiempo total de los clientes en el sistema: " + Tc);
        System.out.println();
    }

    public void informeFinal() {
        System.out.println("Fin de la simulacion...");
        System.out.println();
        System.out.println("I N F O R M E");
        System.out.println("----------------");
        System.out.println("Reloj: " + reloj);
        System.out.println("Tiempo medio de espera en la cola: " + D/n);
        System.out.println("Numero medio de clientes en la cola: " + R/reloj);
        System.out.println("Tiempo de cliclo medio: " + Tc/nOut);
        System.out.println("Tiempo maximo de espera en la cola: " + Dmax);
        System.out.println("Numero maximo de clientes en la cola: " + cola.longitudMax());
        System.out.println("Proporcion de clientes que han esperado mas de un minuto: " + (float) nMinuto/n);
        System.out.println("Ocupacion del empleado: " + S/reloj);
    }

    public void siguienteEvento() {
        if (marcha != NO_DEFINIDO && marcha < llegada) {
            reloj = marcha;
            eventoMarcha();
        } else {
            reloj = llegada;
            eventoLlegada();
        }
    }

    public boolean esFinalDeSimulacion() {
        return n >= numeroClientes;
    }
}
