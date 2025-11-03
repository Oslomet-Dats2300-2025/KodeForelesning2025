import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Objects;

// Renamet siden den krasjer med lenket liste laget tidligere.
// Ville jo ikke vært et problem på eksamen.
class LenketListe2024E<T> {
    LenketListe2024E<T> neste;
    T verdi;
    int antall;
    public LenketListe2024E() {
        // Lager tom lenket liste
        verdi = null; neste = null; antall = 0;
    }
    public LenketListe2024E(T verdi) {
        // Lager lenket liste med en verdi
        this.verdi = verdi; neste = null; antall = 1;
    }
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke lov med null-verdier i lista.");
        if (this.verdi == null) // Om lista er tom, legg inn verdien i første node
            this.verdi = verdi;
        else if (neste == null) // Om neste plass er ledig, legg inn verdien der
            neste = new LenketListe2024E<>(verdi);
        else // G˚a videre i lista
            neste.leggInn(verdi);
        antall++;
        return true;
    }
    public boolean inneholder(T verdi) {
        // Dette er svaret på oppgaven.
        if (this.verdi.equals(verdi))
            return true;
        if (neste == null)
            return false;
        return neste.inneholder(verdi);
    }
}

class TurneringsTre<T> {
    private Comparator<? super T> cmp;
    private int antallNoder;
    private Node<T> rot;

    private static final class Node<T> {
        Node<T> venstre, høyre;
        T verdi;

        public Node(T verdi) {
            this.verdi = verdi;
            venstre = høyre = null;
        }
    }


    public TurneringsTre(Comparator<? super T> sammenlikner) {
        cmp = sammenlikner;
        rot = null;
        antallNoder = 0;
    }

    private Deque<Node<T>> veiTilBunn() {
        Deque<Node<T>> stabel = new ArrayDeque<>();
        if (antallNoder == 0) return stabel;
        String binRep = Integer.toBinaryString(antallNoder + 1);
        Node<T> denne = rot;
        stabel.push(denne); // ekvivalent med addFirst
        for (int i = 1; i < binRep.length() - 1; i++) {
            if (binRep.charAt(i) == '0')
                denne = denne.venstre;
            else
                denne = denne.høyre;
            stabel.push(denne);
        }
        return stabel;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke legg inn null'a");
        if (rot == null) {
            rot = new Node(verdi);
            antallNoder++;
            return true;
        }
        Deque<Node<T>> stabel = veiTilBunn();
        Node<T> denne = stabel.pop();
        denne.venstre = new Node(verdi);
        denne.høyre = new Node(denne.verdi);
        stabel.push(denne);
        while (!stabel.isEmpty()) {
            denne = stabel.pop();
            // Kunne avslutta tidligere ved å se når verdien vi oppdaterer ikke blir oppdatert lenger.
            if (cmp.compare(denne.venstre.verdi, denne.høyre.verdi) < 0)
                denne.verdi = denne.høyre.verdi;
            else
                denne.verdi = denne.venstre.verdi;
        }
        antallNoder += 2;
        return true;
    }
}