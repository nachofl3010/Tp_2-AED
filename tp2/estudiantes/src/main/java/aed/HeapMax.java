package aed;


public class HeapMax<T extends Comparable<T>> {
    private Handle<T>[] heap;
    private int size;


    public HeapMax(int capacidad) { // para crear un heap vacío con capacidad fija (hay que usarlo con agregarElemento) - O(n*log n) creo
        heap = (Handle<T>[]) new Handle[capacidad];
        size = 0;
    }

    public HeapMax(Handle<T>[] elementos, int cantidad) { // para crear un heap directamente con un array, es literalmente un heapify - O(n)
        this.heap = elementos;
        this.size = cantidad;
        for (int i = (size - 1) / 2; i >= 0; i--) { // reordeno el heap desde los nodos del medio para arriba
            siftDown(i);
        }
    }

    public Handle<T> agregarElemento(T elem) { // encolar - O(log n)
        Handle<T> h = new Handle<>(elem, size);
        this.heap[this.size] = h;
        siftUp(size);
        this.size++;
        return h;
    }

    public T obtenerMaximo() { // consultarMax - O(1)
        if (this.size == 0) {
            return null;
        }
        return this.heap[0].getElemento();
    }

    public Handle<T> obtenerElemento(int pos) { // O(1)
        return this.heap[pos];
    }

    public T sacarMaximo() { // desencolarMax - O(log n)
        if (this.size == 0) {
            return null;
        }
        T maximo = heap[0].getElemento();
        swap(0, size - 1); // el máximo se va al final
        this.heap[size - 1] = null; // borrar el máximo
        this.size--;
        siftDown(0); // baja la raíz
        return maximo;
    }

    public void actualizar(Handle<T> h) { // O(log n)
        siftUp(h.getPosicion());
        siftDown(h.getPosicion());
    }

    private void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2; // p(v) = 2p(u)+1 con v hijo de u
            if (heap[i].getElemento().compareTo(heap[padre].getElemento()) > 0) {
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i){
        boolean continuar = true;

        while (2*i+1<this.size && continuar) { // se pide que exista al menos el hijo izquierdo
            int izq = 2*i+1;
            int der = 2*i+2;
            int mayor = i;

            if (heap[izq].getElemento().compareTo(heap[mayor].getElemento()) > 0) {
                mayor = izq; // si el elemento es mayor, se guarda
            }

            if (der < this.size && // se fija si existe el derecho
                    heap[der].getElemento().compareTo(heap[mayor].getElemento()) > 0) {
                mayor = der;
            }

            if (mayor != i) {
                swap(i, mayor);
                i = mayor;
            }
            else {
                continuar = false;
            }
        }
    }

    private void swap(int i, int j) {
        Handle<T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        heap[i].setPosicion(i);
        heap[j].setPosicion(j);
    }
}


