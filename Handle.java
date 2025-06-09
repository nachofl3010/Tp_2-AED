package aed;

public class Handle<T extends Comparable<T>> {
    private int posicion;
    private T elemento;

    public Handle(T elem, int pos) {
        this.elemento = elem;
        this.posicion = pos;
    }

    public int getPosicion(){
        return this.posicion;
    }
    public T getElemento(){
        return this.elemento;
    }

    public void setPosicion(int pos) {
        this.posicion = pos;
    }

    public void setElemento(T elem) {
        this.elemento = elem;
    }
}