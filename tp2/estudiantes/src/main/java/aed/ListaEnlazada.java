package aed;


public class ListaEnlazada<T> { // esto sería como declarar los obs de un TAD
    private int size;
    private Nodo primero;
    private Nodo ultimo; 

    private class Nodo { // el Nodo se compotaría como un struct, donde es una tupla con renombe de tipos. guardo el valor actual, el nodo siguiente y el anterior
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v) {valor = v;}
    }

    public ListaEnlazada() { // creación
        this.size = 0;
        this.primero = null;
        this.ultimo = null;
    }

    public int longitud() {
        return this.size;
    }

    public void agregarAdelante(T elem) { // agrego a la izquierda
        Nodo nuevoNodo = new Nodo(elem);
        if (this.size == 0){ // les doy el mismo valor a ambos porque aún no existía. el primero y el último es lo mismo, hay un solo elem.
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
        }
        else{
            this.primero.ant = nuevoNodo; // enlazo el nuevo valor antes del "primero" que estaba antes
            nuevoNodo.sig = this.primero; // muevo el "primero" que estaba antes a la pos que le sigue
            this.primero = nuevoNodo; // actualizo el valor del primero
        }

        this.size = size + 1;
    }

    public void agregarAtras(T elem) { // agreo a la derecha
        Nodo nuevoNodo = new Nodo(elem);
        if (this.size == 0){ // les doy el mismo valor a ambos porque aún no existía. el primero y el último es lo mismo, hay un solo elem.
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
        }
        else{
            this.ultimo.sig = nuevoNodo; // agrego un nuevo nodo al final
            nuevoNodo.ant = this.ultimo; // el anterior toma el valor que tenía en el último
            nuevoNodo.sig = null; // le asigno valor nulo al nodo siguiente al último
            this.ultimo = nuevoNodo; // actualizo el valor del úlimo
        }

        this.size = size + 1;
    }

// los siguientes ejercicios recorrí el índice de esta forma por temas de complejidad. al recorrer en mitades, en el peor de los casos  el costo es O(n), en el mejor, O(n/2)

    public T obtener(int i) {
        Nodo nodoActual;
        if (i < size / 2) { // si i < la mitad del size, recorrés desde el principio hacia adelante
            nodoActual = primero;
            for (int n = 0; n < i; n++) {
                nodoActual = nodoActual.sig;
            }
        } else { // si i > la mitad del size, recorro desde el final para atrás
            nodoActual = ultimo;
            for (int n = size - 1; n > i; n--) {
                nodoActual = nodoActual.ant;
            }
        }
    
        return nodoActual.valor;
    }

    public void eliminar(int i) {
        Nodo nodoActual; // recorro igual que antes
        if (i < size / 2) {
        nodoActual = primero;
        for (int n = 0; n < i; n++) {
            nodoActual = nodoActual.sig;
        }
        } else {
        nodoActual = ultimo;
        for (int n = size - 1; n > i; n--) {
            nodoActual = nodoActual.ant;
        }
    }

         if (nodoActual.ant != null) { // si no es el primero, su anterior (ant) debe apuntar al siguiente (sig)
        nodoActual.ant.sig = nodoActual.sig;
        } else { // si es el primero, actualiza las referencias primero
            primero = nodoActual.sig;
    }

        if (nodoActual.sig != null) { // si no es el último, su siguiente (sig) debe apuntar al anterior (ant)
        nodoActual.sig.ant = nodoActual.ant;
        } else { // si es el último, actualiza las referencias último
            ultimo = nodoActual.ant;
    }

    size --;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo nodoActual; // recorro igual que antes
        if (indice < size / 2) {
        nodoActual = primero;
        for (int n = 0; n < indice; n++) {
            nodoActual = nodoActual.sig;
        }
        } else {
        nodoActual = ultimo;
        for (int n = size - 1; n > indice; n--) {
            nodoActual = nodoActual.ant;
        }
    }

    nodoActual.valor = elem; // exactamente igual al obtener pero solo modifico el valor del nodo actual por elem
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        this.size = 0;
        this.primero = null;
        this.ultimo = null;

        Nodo nodoActualCopia = lista.primero; // referencia a los nodos de la lista original, empezando por el primero
        while (nodoActualCopia != null) { // me aseguro que termina en el último
            this.agregarAtras(nodoActualCopia.valor); // no copio el nodo sino el valor del nodo en la nueva copia
            nodoActualCopia = nodoActualCopia.sig;  // le asigno un nuevo valor a nodoActualCopia así copio el valor del siguiente nodo
        }
    }
    
    @Override
    public String toString() {
        String lista = "[";
        for (int n = 0; n < this.size - 1; n++){
            lista = lista + this.obtener(n) + ", ";
        };
        return lista + this.ultimo.valor + "]";
    }
}