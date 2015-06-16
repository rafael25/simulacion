package capitulo5;

public class Figura5_2 {

    public static final int EMPLEADO_LIBRE = 0;
    public static final int EMPLEADO_OCUPADO = 1;

    private int numeroClientes;
    private float llegada;
    private float marcha;
    private float R;
    private int n;
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
    public Figura5_2(float mediaLlegada, float mediaServicio, int numeroClientes, int estadoEmpleado) {
        this.numeroClientes = numeroClientes;
        this.estadoEmpleado = estadoEmpleado;

        R = 0;
        n = 0;
        reloj = 0;
        ultimoEvento = 0;
        marcha = 0;

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
        Figura5_2 fig = new Figura5_2(1, 0.5f, 5, EMPLEADO_LIBRE);
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
        llegada = generador.generarSiguienteLlegada(reloj);

        if (estadoEmpleado == EMPLEADO_LIBRE) {
            estadoEmpleado = EMPLEADO_OCUPADO;
            n += 1;
            marcha = generador.generarTiempoServicio(reloj);
            ultimoEvento = reloj;
        } else if (estadoEmpleado == EMPLEADO_OCUPADO) {
            R = R + cola.longitud() * (reloj - ultimoEvento);
            cola.agregar(reloj);
            ultimoEvento = reloj;
        }
    }

    public void eventoMarcha() {
        if (cola.estaVacia()) {
            estadoEmpleado = EMPLEADO_LIBRE;
            marcha = 0;
            ultimoEvento = reloj;
        } else {
            n += 1;
            R = R + cola.longitud() * (reloj - ultimoEvento);
            Float e = cola.extraerSiguiente();
            marcha = generador.generarTiempoServicio(reloj);
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
        System.out.println("Lista de eventos: \t\tLlegada: " + llegada + "\t\tMarcha: " + marcha);
        System.out.println("Contadores estadisticos:");
        System.out.println("\t\tNumero de clientes que han empezado a ser atendidos: " + n);
        System.out.println("\t\tIntegral num. clientes en la cola respecto al tiempo: " + R);
        System.out.println();
    }

    public void informeFinal() {
        System.out.println("Fin de la simulacion...");
        System.out.println();
        System.out.println("I N F O R M E");
        System.out.println("----------------");
        System.out.println("Reloj: " + reloj);
        System.out.println("Numero medio de clientes en la cola: " + R/reloj);
    }

    public void siguienteEvento() {
        if (marcha != 0 && marcha < llegada) {
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
