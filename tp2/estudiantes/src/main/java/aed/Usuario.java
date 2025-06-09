package aed;

public class Usuario implements Comparable<Usuario> {
    public int id;
    public int saldo;

    public Usuario(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    @Override
    public int compareTo(Usuario otro) {
        if (this.saldo != otro.saldo) {
            return Integer.compare(this.saldo, otro.saldo);
        } else {
            return Integer.compare(otro.id, this.id); // menor id gana si hay empate
        }
    }
}