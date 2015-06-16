package capitulo5;

import java.util.Random;

public class Generador {

    private float mediaLlegada;
    private float mediaServicio;

    public Generador(float mediaLlegada, float mediaServicio) {
        this.mediaLlegada = mediaLlegada;
        this.mediaServicio = mediaServicio;
    }

    public float generarSiguienteLlegada(float inicio) {
        return inicio + aleatorioExponecial(mediaLlegada);
    }

    public float generarTiempoServicio(float inicio) {
        return inicio + aleatorioExponecial(mediaServicio);
    }

    public float aleatorioExponecial(float m) {
        Random rand = new Random();
        float x;
        x = rand.nextInt(Integer.MAX_VALUE);
        x /= Integer.MAX_VALUE;
        return (float)  (Math.log(1-x))/(-m);
    }
}
