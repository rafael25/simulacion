package capitulo5;

import java.util.Random;

public class GeneradorInventario {

    private float media;

    public GeneradorInventario(float media) {
        this.media = media;
    }

    public float generarSiguientePedido(float inicio) {
        return inicio + aleatorioExponecial(media);
    }

    public int generarCantidadPedido() {
        float rand = aleatorioUniforme();
        int cantidad = 0;

        if (rand < 1/6f) {
            cantidad = 1;
        } else if (rand >= 1/6f && rand < 3/6f) {
            cantidad = 2;
        } else if (rand >= 3/6f && rand < 5/6f) {
            cantidad = 3;
        } else if (rand >= 5/6) {
            cantidad = 4;
        }

        return cantidad;
    }

    public float generarTiempoServicioProveedor(float inicio) {
        float x = aleatorioUniforme();
        float llegada = 1/2f + ((1 - 1/2f) * x);
        return inicio + llegada;
    }

    public float aleatorioExponecial(float m) {
        Random rand = new Random();
        float x;
        x = rand.nextInt(Integer.MAX_VALUE);
        x /= Integer.MAX_VALUE;
        return (float)  (Math.log(0.2)) * -m;
    }

    public float aleatorioUniforme() {
        Random rand = new Random();
        float x;
        x = rand.nextInt(Integer.MAX_VALUE);
        x /= Integer.MAX_VALUE;
        return x;
    }

}
