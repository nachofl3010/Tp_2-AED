package aed;
import java.util.ArrayList;

// p: cantidad de usuarios
// n_b: cantidad de transacciones por bloques 

public class Berretacoin {

    private ListaEnlazada<Bloque> bloques;
    private ArrayList<Handle<Usuario>> handles; // handles
    private HeapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios){ // O(p)

        this.usuarios = new HeapMax<Usuario>(n_usuarios);
        this.handles = new ArrayList<Handle<Usuario>>(n_usuarios);
        for (int i = 0; i < n_usuarios; i++){
            Usuario usuario = new Usuario (i,0);
            Handle<Usuario> handle = usuarios.agregarElemento(usuario);
            handles.add(handle);
        }
        this.bloques = new ListaEnlazada<Bloque>();

        // O(p) Porque estamos creando un arreglo de handles de tamaño P
        // O(p) y tambien inicializamos el heapify a partir del arreglo saldos
        // O(1) ademas la lista dob enlazada de bloques 
    }

    public void agregarBloque(Transaccion[] transacciones){ // O(n_b * log(P))

        for (Transaccion t : transacciones){
            Handle<Usuario> handleComprador = handles.get(t.id_comprador());
            Usuario comprador = handleComprador.getElemento();
            comprador.saldo -= t.monto();
            usuarios.actualizar(handleComprador);

            Handle<Usuario> handleVendedor = handles.get(t.id_vendedor());
            Usuario vendedor = handleVendedor.getElemento();
            vendedor.saldo += t.monto();
            usuarios.actualizar(handleVendedor);
        } // O(n_b * log(P)) : para cada transaccion hacemos dos actualizaciones, cada una en O(log P)

        Bloque nuevoBloque = new Bloque (transacciones.length);
        for (Transaccion t : transacciones) {
            nuevoBloque.agregarTransaccion(t);
        }
        bloques.agregarAtras(nuevoBloque);
        
        // O(n_b * log(n_b)) : para cada transaccion agregamos a un heap de tamaño a lo sumo n_b. El resto son O(1).

    }
    public Transaccion txMayorValorUltimoBloque() {
        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        return ult.obtenerMaximaTransaccion();
    }

    public Transaccion[] txUltimoBloque() {
        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        int n = ult.numeroTransacciones();
        Transaccion[] arr = new Transaccion[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ult.getTransacciones().obtener(i);
        }
        return arr;
    }

    /** ID del usuario con saldo máximo (menor ID en caso de empate) */
    public int maximoTenedor() {
        Usuario top = usuarios.obtenerMaximo();
        return top.id;
    }

    public int montoMedioUltimoBloque() {
        Bloque ultimo = bloques.obtener(bloques.longitud() - 1);
        int suma = ultimo.getSumaMontos();
        int cant = ultimo.numeroTransacciones();
        return suma/cant;
    }

    public void hackearTx(){ // O(log n_b + log(p))
        // O(log n_b) obtenerMaximo de heapMax
        // O(log p) buscar id_vendedor y id_comprador y actualizar saldo y reordenar heapMax
        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        Transaccion t = ult.obtenerMaximaTransaccion();

        // Revertir pago
        Handle<Usuario> hV = handles.get(t.id_vendedor());
        Usuario v = hV.getElemento();
        v.saldo -= t.monto();
        usuarios.actualizar(hV);

        Handle<Usuario> hC = handles.get(t.id_comprador());
        Usuario c = hC.getElemento();
        c.saldo += t.monto();
        usuarios.actualizar(hC);
    }
}







