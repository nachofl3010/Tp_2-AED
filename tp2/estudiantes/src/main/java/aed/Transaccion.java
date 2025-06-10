package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto != otro.monto) {
            return Integer.compare(this.monto, otro.monto); // mayor monto primero
        } else {
            return Integer.compare(this.id, otro.id); // menor id primero
        }
    }


    @Override
    public String toString() {
        return "Transaccion[id=" + id + 
               ", comprador=" + id_comprador + 
               ", vendedor=" + id_vendedor + 
               ", monto=" + monto + "]";
    }




    @Override
    public boolean equals(Object otro){
        if (!(otro instanceof Transaccion)) return false;
        Transaccion t = (Transaccion) otro;
        return this.id == t.id &&
                this.id_comprador == t.id_comprador &&
                this.id_vendedor == t.id_vendedor &&
                this.monto == t.monto;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }

    public int id_vendedor() {
        return id_vendedor;
    }

    public int id() {
        return id;
    }
}