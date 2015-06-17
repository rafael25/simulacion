package capitulo5;

import java.util.LinkedList;

public class Cola<T> {

    private LinkedList<T> cola;

    public Cola() {
        this.cola = new LinkedList<T>();
    }

    public T extraerSiguiente() {
        return cola.poll();
    }

    public void agregar(T elemento) {
        cola.add(elemento);
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int longitud() {
        return cola.size();
    }

    @Override
    public String toString() {
        return cola.toString();
    }
}
