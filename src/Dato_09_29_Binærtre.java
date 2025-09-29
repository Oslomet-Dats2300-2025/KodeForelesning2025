import java.util.ArrayDeque;
import java.util.Queue;

public class Dato_09_29_Binærtre {
    public static void main(String[] args) {
        Binærtre<String> btre = new Binærtre<>();
        btre.leggInn(0, "A");
        btre.leggInn(3, "B");
        btre.leggInn(6, "C");
        btre.leggInn(7, "D");
        btre.leggInn(14, "E");
        btre.leggInn(15, "F");
        btre.leggInn(31, "G");

        //btre.leggInn(5, "Gir feilmelding, kan ikke komme til");
        //btre.leggInn(6, "Gir feilmelding, allerede lagt verdi der");

        Binærtre<Character> littStørre = new Binærtre<>();
        littStørre.leggInn(1, 'A');
        littStørre.leggInn(2, 'B');
        littStørre.leggInn(3, 'C');
        littStørre.leggInn(4, 'D');
        littStørre.leggInn(6, 'E');
        littStørre.leggInn(7, 'F');
        littStørre.leggInn(14, 'G');
        littStørre.leggInn(15, 'H');
        System.out.println("Print preorden:");
        littStørre.preorden();
        System.out.println("Print inorden:");
        littStørre.inorden();
        System.out.println("Print postorden:");
        littStørre.postorden();
        System.out.println("Print nivåorden:");
        littStørre.nivåorden();
    }
}

class Binærtre<T> {
    private class Node {
        Node venstre, høyre;
        T verdi;

        public Node(T verdi) {
            this(verdi, null, null);
        }

        public Node(T verdi, Node venstre, Node høyre) {
            this.verdi = verdi;
            this.venstre = venstre;
            this.høyre = høyre;
        }
    }

    Node rot = null;

    public void leggInn(int posisjon, T verdi) {
        char[] binærtall = Integer.toBinaryString(posisjon).toCharArray();
        Node p = rot; Node q = null;
        for (int i = 1; i < binærtall.length; i++) {
            q = p;
            if (p == null)
                throw new IllegalArgumentException("Ikke lovlig indeks.");
            if (binærtall[i] == '0')
                p = p.venstre;
            else
                p = p.høyre;
        }
        if (p != null)
            throw new IllegalArgumentException("Allerede noe på denne indeksen");
        p = new Node(verdi);
        if (q == null) {
            rot = p;
        } else if (binærtall[binærtall.length - 1] == '0') {
            q.venstre = p;
        } else {
            q.høyre = p;
        }
    }

    // De to neste metodene er for spesielt interesserte
    // Innlegging men rekursivt i stedet for iterativt.
    public void leggInnRekursiv(int indeks, T verdi) {
        char[] binærtall = Integer.toBinaryString(indeks).toCharArray();
        rot = leggInnRekursiv(rot, verdi, binærtall, 1);
    }
    private Node leggInnRekursiv(Node p, T verdi, char[] binærtall, int i) {
        // Vil ikke gi rett feilmelding om du prøver sende inn gale posisjoner.
        // Oppgave å fikse dette.
        if (p == null && i >= binærtall.length)
            return new Node(verdi);
        if (binærtall[i] == '0') {
            p.venstre = leggInnRekursiv(p.venstre, verdi, binærtall, i+1);
        } else {
            p.høyre = leggInnRekursiv(p.høyre, verdi, binærtall, i+1);
        }
        return p;
    }

    public void preorden() {
        preorden(rot);
    }

    private void preorden(Node p) {
        if (p == null)
            return;
        System.out.println(p.verdi);
        preorden(p.venstre);
        preorden(p.høyre);
    }
    public void inorden() {
        inorden(rot);
    }

    private void inorden(Node p) {
        if (p == null)
            return;
        inorden(p.venstre);
        System.out.println(p.verdi);
        inorden(p.høyre);
    }
    public void postorden() {
        postorden(rot);
    }

    private void postorden(Node p) {
        if (p == null)
            return;
        postorden(p.venstre);
        postorden(p.høyre);
        System.out.println(p.verdi);
    }

    public void nivåorden() {
        Queue<Node> kø = new ArrayDeque<>();
        if (rot != null)
            kø.add(rot);
        while (!kø.isEmpty()) {
            Node p = kø.remove();
            if (p.venstre != null)
                kø.add(p.venstre);
            if (p.høyre != null)
                kø.add(p.høyre);
            System.out.println(p.verdi);
        }
    }
}