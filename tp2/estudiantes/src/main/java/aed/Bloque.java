package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> transacciones;
    private HeapMax<Transaccion> heap;
    private int sumaMontos;


    public Bloque(int capacidad) {
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new HeapMax<Transaccion>(capacidad);
        this.sumaMontos = 0;
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.agregarAtras(t); // O(1)
        heap.agregarElemento(t); // O(log n_b)
        if (t.id_vendedor() != 0) { // no hay que tener en cuenta las de creación
            sumaMontos += t.monto(); // O(1), solo si no es creación
        }
        System.out.println("Estado del heap después de agregar: " + heap);
    }

    public Transaccion obtenerMaximaTransaccion() { // no sé si se usa
        return heap.obtenerMaximo();
    }

    public Transaccion extraerMaximaTransaccion() { // para sacar la transacción en hackearTx
        return heap.sacarMaximo(); // O(log n_b)
    }

    public void restarMonto(Transaccion t) { // para restar cuando se saca la transacción con hackearTx
    if (t.id_vendedor() != 0) { // no hay que tener en cuenta las de creación
        sumaMontos -= t.monto();
    }
    }

    public int getSumaMontos() {
        return sumaMontos;
    }

    public int promedioMontos() {
        int n = 0;
        for (int i = 0; i < transacciones.longitud(); i++) {
            if (transacciones.obtener(i).id_comprador() != 0) {
                n++;
            }
        }
        if (n > 0) {
            return sumaMontos / n;
        } else {
            return 0;
        }
    }

    public int numeroTransacciones() {
        return transacciones.longitud();
    }

    public ListaEnlazada<Transaccion> getTransacciones() {
        return transacciones;
    }
}