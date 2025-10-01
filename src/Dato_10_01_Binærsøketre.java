import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class Dato_10_01_Binærsøketre {
    public static void main(String[] args) {
        Binærsøketre<Integer> bst = new Binærsøketre<>(Comparator.naturalOrder());
        int[] a = {7, 5, 9, 4, 6, 8, 11, 5, 7, 8, 10};
        for (int i : a) bst.leggInn(i);
        System.out.println(bst.antall());
        System.out.println(bst);

        Binærsøketre<Integer> rbst = new Binærsøketre<>(Comparator.reverseOrder());
        for (int i : a) rbst.leggInn(i);
        System.out.println(rbst);

        Binærsøketre<Integer> parodde = new Binærsøketre<>( (x, y) -> {
           if (x % 2 == 0 && y % 2 != 0) return -1;
           if (x % 2 != 0 && y % 2 == 0) return 1;
           return x.compareTo(y);
        });
        for (int i : a) parodde.leggInn(i);
        System.out.println(parodde);
    }
}

class Binærsøketre<T> implements Beholder<T> {

    private static final class Node<T> {
        Node<T> venstre, høyre;
        T verdi;

        public Node(T verdi, Node<T> venstre, Node<T> høyre) {
            this.verdi = verdi;
            this.venstre = venstre;
            this.høyre = høyre;
        }

        public Node(T verdi) {
            this(verdi, null, null);
        }
    }

    Node<T> rot;
    Comparator<? super T> cmp;

    public Binærsøketre(Comparator<? super T> cmp) {
        this.cmp = cmp;
        this.rot = null;
    }

    private boolean leggInnIterativ(T t) {
        Objects.requireNonNull(t, "Ikke lov å legge inn null-verdier");
        Node<T> current = rot;
        Node<T> previous = null;
        while (current != null) {
            previous = current;
            if (cmp.compare(t, current.verdi) < 0)
                current = current.venstre;
            else
                current = current.høyre;
        }
        current = new Node<>(t);
        if (previous == null)
            rot = current;
        else if (cmp.compare(t, previous.verdi) < 0)
            previous.venstre = current;
        else
            previous.høyre = current;
        return true;
    }

    private Node<T> leggInnRekursiv(Node<T> current, T t) {
        if (current == null) {
            return new Node<>(t);
        }
        if (cmp.compare(t, current.verdi) < 0)
            current.venstre = leggInnRekursiv(current.venstre, t);
        else
            current.høyre = leggInnRekursiv(current.høyre, t);
        return current;
    }

    private boolean leggInnRekursiv(T t) {
        Objects.requireNonNull(t, "Ulovlig med null-verdier.");
        rot = leggInnRekursiv(rot, t);
        return true;
    }

    @Override
    public boolean leggInn(T t) {
        return leggInnRekursiv(t);
    }

    @Override
    public boolean fjern(T t) {
        return false;
    }

    private int antall(Node<T> current) {
        if (current == null)
            return 0;
        return 1 + antall(current.venstre) + antall(current.høyre);
    }

    @Override
    public int antall() {
        return antall(rot);
    }

    @Override
    public boolean tom() {
        return (rot == null);
    }

    @Override
    public boolean inneholder(T t) {
        return false;
    }

    @Override
    public void nullstill() {

    }

    // Jeg lagde to metoder som går gjennom treet i inorden og printer
    // det som en liste. Vi kan nå se at treet oppfører seg som ønsket.
    private void inordenSJ(Node<T> current, StringJoiner sj) {
        if (current == null)
            return;
        inordenSJ(current.venstre, sj);
        sj.add(current.verdi.toString());
        inordenSJ(current.høyre, sj);
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        inordenSJ(rot, sj);
        return sj.toString();
    }
}