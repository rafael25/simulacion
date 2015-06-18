package capitulo5;

import java.util.LinkedList;

public class Cola<T> {
    private final int ILIMITADA = -1;

    private int longitudMaxima;
    private int capacidad;
    private int balking;
    private LinkedList<T> cola;

    public Cola() {
        this.capacidad = ILIMITADA;
        this.cola = new LinkedList<T>();
    }

    public Cola(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new LinkedList<T>();
    }

    public T extraerSiguiente() {
        return cola.poll();
    }

    public void agregar(T elemento) {
        if (capacidad == ILIMITADA || longitud() < capacidad) {
            cola.add(elemento);
            longitudMaxima = (longitud() > longitudMaxima) ? longitud() : longitudMaxima;
        } else {
            balking += 1;
        }
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int longitud() {
        return cola.size();
    }

    public int longitudMax() {
        return longitudMaxima;
    }

    public int getBalking() {
        return balking;
    }

    @Override
    public String toString() {
        return cola.toString();
    }
}
