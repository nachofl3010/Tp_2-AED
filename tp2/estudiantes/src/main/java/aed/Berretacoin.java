package aed;
import java.util.ArrayList;

// p: cantidad de usuarios
// n_b: cantidad de transacciones por bloques 

public class Berretacoin {

    private ListaEnlazada<Bloque> bloques;
    private ArrayList<Handle<Usuario>> handles; // handles
    private HeapMax<Usuario> usuarios;

    public Berretacoin(int n_usuarios) { // O(p)

        this.usuarios = new HeapMax<Usuario>(n_usuarios);
        this.handles = new ArrayList<Handle<Usuario>>(n_usuarios);
        for (int i = 1; i <= n_usuarios; i++) {
            Usuario usuario = new Usuario(i, 0);
            Handle<Usuario> handle = usuarios.agregarElemento(usuario);
            handles.add(handle);
        }
        this.bloques = new ListaEnlazada<Bloque>();

        // O(p) Porque estamos creando un arreglo de handles de tamaño P
        // O(p) y tambien inicializamos el heapify a partir del arreglo saldos
        // O(1) ademas la lista dob enlazada de bloques 
    }

    public void agregarBloque(Transaccion[] transacciones) { // O(n_b * log(P))

        for (Transaccion t : transacciones) {
            System.out.println("Transaccion: " + t);

            if (t.id_comprador() != 0) {

                Handle<Usuario> handleComprador = handles.get(t.id_comprador() - 1);
                Usuario comprador = handleComprador.getElemento();
                comprador.saldo -= t.monto();
                usuarios.actualizar(handleComprador);
            }

            Handle<Usuario> handleVendedor = handles.get(t.id_vendedor() - 1);
            Usuario vendedor = handleVendedor.getElemento();
            vendedor.saldo += t.monto();
            usuarios.actualizar(handleVendedor);
        } // O(n_b * log(P)) : para cada transaccion hacemos dos actualizaciones, cada una en O(log P)

        Bloque nuevoBloque = new Bloque(transacciones.length);
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

    public int maximoTenedor() {
        Usuario top = usuarios.obtenerMaximo();
        return top.id;
    }

    public int montoMedioUltimoBloque() {
        Bloque ultimo = bloques.obtener(bloques.longitud() - 1);
        System.out.println("Monto medio del último bloque: " + ultimo.promedioMontos());
        return ultimo.promedioMontos();
    }

    public void hackearTx() {
        if (bloques.longitud() == 0) return;

        Bloque ult = bloques.obtener(bloques.longitud() - 1);
        if (ult.numeroTransacciones() == 0) return;

        Transaccion t = ult.extraerMaximaTransaccion(); // saco del heap

        ult.restarMonto(t); // resto del total de montos si no es creación

        if (t.id_comprador() != 0) { // revierto los saldos
            Handle<Usuario> hV = handles.get(t.id_vendedor() - 1);
            Usuario v = hV.getElemento();
            v.saldo -= t.monto();
            usuarios.actualizar(hV);

            Handle<Usuario> hC = handles.get(t.id_comprador() - 1);
            Usuario c = hC.getElemento();
            c.saldo += t.monto();
            usuarios.actualizar(hC);
        } else {
            Handle<Usuario> hV = handles.get(t.id_vendedor() - 1);
            Usuario v = hV.getElemento();
            v.saldo -= t.monto();
            usuarios.actualizar(hV);
        }

        ListaEnlazada<Transaccion> lista = ult.getTransacciones(); // elimino siempre (cuando también es de creación) de ListaEnlazada
        for (int i = 0; i < lista.longitud(); i++) {
            if (lista.obtener(i).id() == t.id()) {
                lista.eliminar(i);
                break;
            }
        }
    }
}







