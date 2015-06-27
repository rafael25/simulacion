package capitulo5;

public class Figura5_7 {

    private final int NO_DEFINIDO =  -1;

    private float precioUnitario;
    private float tramitePedido;
    private float tramitePedidoUrgente;
    private int Inventario;
    private float Imas;
    private float Imenos;
    private float costoPedidos;
    private float reloj;
    private float ultimoEvento;
    private float pedidoCliente;
    private float llegadaProducto;
    private float evaluarInventario;
    private int finSimulacion;
    private int Z;
    private int S;
    private int s;

    private GeneradorInventario generador;

    /*******************************
     *                             *
     *   Rutina de inicialización  *
     *                             *
     *******************************/
    public Figura5_7(int Inventario, float precioUnitario, float tramitePedido, float tramitePedidoUrgente, int finSimulacion, int S, int s) {
        this.Inventario = Inventario;
        this.precioUnitario = precioUnitario;
        this.tramitePedido = tramitePedido;
        this.tramitePedidoUrgente = tramitePedidoUrgente;
        this.finSimulacion = finSimulacion;
        this.S = S;
        this.s = s;

        this.generador = new GeneradorInventario(0.1f);

        this.pedidoCliente = generador.generarSiguientePedido(0);
        this.llegadaProducto = NO_DEFINIDO;
    }

    /*******************************
     *                             *
     *     Rutina de ejecución     *
     *                             *
     *******************************/
    public static void main(String... args) {
        int inventarioInicial = 60;
        float precioUnitario = 300;
        float precioTramite = 3200;
        float precioTramiteUrgente = 7000;
        int finSimulacion = 120;
        int S = 40;
        int s = 20;

        Figura5_7 fig = new Figura5_7(inventarioInicial, precioUnitario, precioTramite, precioTramiteUrgente, finSimulacion, S, s);

        do {
            fig.siguienteEvento();
            fig.informe();
        } while (! fig.esFinalDeSimulacion());

        fig.finSimulacion();
        fig.informe();
    }

    /*******************************
     *                             *
     *     Rutinas de eventos      *
     *                             *
     *******************************/
    public void eventoPedidoCliente() {
        if (Inventario > 0) {
            Imas += Inventario * (reloj - ultimoEvento);
        } else {
            Imenos -= Inventario * (reloj - ultimoEvento);
        }

        ultimoEvento = reloj;
        Inventario -= generador.generarCantidadPedido();
        pedidoCliente = generador.generarSiguientePedido(reloj);
    }

    public void eventoLlegadaProducto() {
        if (Inventario > 0) {
            Imas += Inventario * (reloj - ultimoEvento);
        } else {
            Imenos -= Inventario * (reloj - ultimoEvento);
        }

        ultimoEvento = reloj;
        Inventario += Z;
        llegadaProducto = NO_DEFINIDO;
    }

    public void eventoEvaluarInventario() {
        if (Inventario < s) {
            Z = S - Inventario;

            if (Inventario < 0) {
                costoPedidos += tramitePedidoUrgente + (precioUnitario * Z);
                llegadaProducto = generador.generarTiempoServicioProveedorUrgente(reloj);
            } else {
                costoPedidos += tramitePedido + (precioUnitario * Z);
                llegadaProducto = generador.generarTiempoServicioProveedor(reloj);
            }
        }

        evaluarInventario += 1;
    }

    private void finSimulacion() {
        if (Inventario > 0) {
            Imas += Inventario * (reloj - ultimoEvento);
        } else {
            Imenos -= Inventario * (reloj - ultimoEvento);
        }

        ultimoEvento = reloj;
    }

    private void informe() {
        System.out.println("Reloj: " + reloj);
        System.out.println("Inventario: " + Inventario);
        System.out.println("Imas: " + Imas);
        System.out.println("Imenos: " + Imenos);
        System.out.println("Costo total: " + costoPedidos);
        System.out.println();
    }

    private void siguienteEvento() {
        if (llegadaProducto != NO_DEFINIDO) {
            if (llegadaProducto <= pedidoCliente && llegadaProducto <= evaluarInventario) {
                reloj = llegadaProducto;
                eventoLlegadaProducto();
            } else if (pedidoCliente < evaluarInventario) {
                reloj = pedidoCliente;
                eventoPedidoCliente();
            } else {
                reloj = evaluarInventario;
                eventoEvaluarInventario();
            }
        } else if (pedidoCliente < evaluarInventario) {
            reloj = pedidoCliente;
            eventoPedidoCliente();
        } else {
            reloj = evaluarInventario;
            eventoEvaluarInventario();
        }
    }

    public boolean esFinalDeSimulacion() {
        return reloj >= finSimulacion;
    }
}
