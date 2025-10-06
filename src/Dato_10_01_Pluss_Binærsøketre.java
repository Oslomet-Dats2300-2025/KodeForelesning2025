import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class Dato_10_01_Pluss_Binærsøketre {
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

    private static final class NodePar<T> {
        Node<T> current, forelder;
        private NodePar(Node<T> current, Node<T> forelder) {
            this.current = current;
            this.forelder = forelder;
        }
    } // Kunne brukt bare Node<T>[] med 2 verdier.

    private static final class Node<T> {
        Node<T> venstre, høyre;
        T verdi;

        private Node(T verdi, Node<T> venstre, Node<T> høyre) {
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
        Node<T> forelder = null;
        while (current != null) {
            forelder = current;
            if (cmp.compare(t, current.verdi) < 0)
                current = current.venstre;
            else
                current = current.høyre;
        }
        current = new Node<>(t);
        if (forelder == null)
            rot = current;
        else if (cmp.compare(t, forelder.verdi) < 0)
            forelder.venstre = current;
        else
            forelder.høyre = current;
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

    private void fjernNode(Node<T> current, Node<T> forelder) {
        if (current.venstre == null && current.høyre == null) {
            // Ingen barn
            if (forelder == null)
                rot = null;
            else if (forelder.venstre == current)
                forelder.venstre = null;
            else
                forelder.høyre = null;
        } else if (current.høyre == null) {
            // Har venstrebarn, men ikke høyrebarn.
            if (forelder == null)
                rot = current.venstre;
            else if (forelder.venstre == current)
                forelder.venstre = current.venstre;
            else
                forelder.høyre = current.venstre;
        } else if (current.venstre == null) {
            // Har høyrebarn men ikke venstrebarn
            if (forelder == null)
                rot = current.høyre;
            else if (forelder.venstre == current)
                forelder.venstre = current.høyre;
            else
                forelder.høyre = current.høyre;
        } else {
            // Har to barn.
            Node<T> p = current.høyre;
            Node<T> q = current;
            while (p.venstre != null) {
                q = p;
                p = p.venstre;
            }
            current.verdi = p.verdi;
            fjernNode(p, q);
            // Kunne sletta direkte, ville vært å sette
            // forelders peker til p.høyre;
            // if (q == current)
            //    q.høyre = p.høyre;
            // else
            //    q.venstre = p.høyre;
        }
    }

    @Override
    public boolean fjern(T t) {
        NodePar<T> par = finnNode(t);
        if (par == null)
            return false;
        fjernNode(par.current, par.forelder);
        return true;
    }

    public int fjernAlle(T t) {
        // Skal fjerne alle kopier av en verdi.
        // Skal gi ut hvor mange kopier det var.
        Stabel<NodePar<T>> stabel = finnAlle(t);
        int teller = 0;
        while (!stabel.tom()) {
            NodePar<T> par = stabel.pop();
            fjernNode(par.current, par.forelder);
            teller++;
        }
        return teller;
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

    private NodePar<T> finnNodeIterativ(T t) {
        Node<T> forelder = null;
        Node<T> current = rot;
        while (current != null) {
            int cmpv = cmp.compare(t, current.verdi);
            if (cmpv == 0)
                return new NodePar<T>(current, forelder);
            else if (cmpv < 0) {
                forelder = current;
                current = current.venstre;
            } else {
                forelder = current;
                current = current.høyre;
            }
        }
        return null;
    }

    private Stabel<NodePar<T>> finnAlle(T t) {
        Stabel<NodePar<T>> stabel = new LenketListeSomStabel<>();
        Node<T> current = rot;
        Node<T> forelder = null;
        while (true) {
            NodePar<T> par = finnNodeRekursiv(current, forelder, t);
            if (par == null)
                break;
            current = par.current.høyre;
            forelder = par.current;
            stabel.push(par);
        }
        return stabel;
    }

    private NodePar<T> finnNodeRekursiv(Node<T> current, Node<T> forelder, T t) {
        if (current == null)
            return null;
        int cmpv = cmp.compare(t, current.verdi);
        if (cmpv < 0)
            return finnNodeRekursiv(current.venstre, current, t);
        else if (cmpv > 0)
            return finnNodeRekursiv(current.høyre, current, t);
        else
            return new NodePar<>(current, forelder);
    }

    private NodePar<T> finnNodeRekursiv(T t) {
        return finnNodeRekursiv(rot, null, t);
    }

    private NodePar<T> finnNode(T t) {
        return finnNodeIterativ(t);
    }

    @Override
    public boolean inneholder(T t) {
        return (finnNode(t) != null);
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