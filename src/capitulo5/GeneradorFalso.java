package capitulo5;

public class GeneradorFalso {

    private int i = 0;
    private int j = 0;

    private float[] tiempoLlegadas = {
            4.549548f,
            6.371961f,
            6.494414f,
            7.640840f,
            8.381031f,
            8.597755f,
            12.388444f
    };
    private float[] tiempoServicios = {
            4.796673f,
            6.453289f,
            7.127680f,
            8.910286f,
            9.028479f
    };

    public GeneradorFalso(float mediaLlegada, float mediaServicio) {
    }

    public float generarSiguienteLlegada(float inicio) {
        float tiempo = tiempoLlegadas[i];
        i += 1;
        return tiempo;
    }

    public float generarTiempoServicio(float inicio) {
        float tiempo = tiempoServicios[j];
        j += 1;
        return tiempo;
    }
}
