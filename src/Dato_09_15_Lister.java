interface Beholder<T> {
    boolean leggInn(T t);
    boolean fjern(T t);
    int antall();
    boolean tom();
    boolean inneholder(T t);
    void nullstill();
}

interface Liste<T> extends Beholder<T> {
    boolean leggInn(int indeks, T t);
    T hent(int indeks);
    T oppdater(int indeks, T t);
    boolean fjern(int indeks);
    int indeksTil(T t);
}

class TabellListe<T> implements Liste<T> {
    private T[] tabell;
    private int antall, kapasitet;

    public TabellListe() {
        this(10);
    }

    public TabellListe(int kapasitet) {
        this.kapasitet = kapasitet;
        tabell = (T[]) new Object[kapasitet];
        antall = 0;
    }

    private void utvidTabell() {
        kapasitet = 2*kapasitet;
        T[] tmp = (T[]) new Object[kapasitet];
        System.arraycopy(tabell, 0, tmp, 0, antall);
        tabell = tmp;
    }

    private void sjekkIndeks(int indeks, boolean leggInn) {
        // Sjekk om indeks er lovlig. Kast feilmelding om ikke.
        if (leggInn && indeks >= 0 && indeks <= antall)
            return;
        if (indeks >= 0 && indeks < antall)
            return;
        throw new IndexOutOfBoundsException("Ulovlig indeks.");
    }

    @Override
    public boolean leggInn(T t) {
        if (antall == kapasitet) {
            utvidTabell();
        }
        tabell[antall++] = t;
        return true;
    }

    @Override
    public boolean fjern(T t) {
        return false;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return (antall == 0);
    }

    @Override
    public boolean inneholder(T t) {
        for (int i = 0; i < antall; i++) {
            if (tabell[i].equals(t))
                return true;
        }
        return false;
    }

    @Override
    public void nullstill() {
        kapasitet = 10;
        antall = 0;
        tabell = (T[]) new Object[kapasitet];
    }

    @Override
    public boolean leggInn(int indeks, T t) {
        sjekkIndeks(indeks, true);
        if (antall == kapasitet) {
            utvidTabell();
        }
        for (int i = antall-1; i >= indeks; i--) {
            tabell[i+1] = tabell[i];
        }
        tabell[indeks] = t;
        antall++;
        return true;
    }

    @Override
    public T hent(int indeks) {
        sjekkIndeks(indeks, false);
        return tabell[indeks];
    }

    @Override
    public T oppdater(int indeks, T t) {
        T tmp = tabell[indeks];
        tabell[indeks] = t;
        return tmp;
    }

    @Override
    public boolean fjern(int indeks) {
        return false;
    }

    @Override
    public int indeksTil(T t) {
        return 0;
    }
}

class LenketListe<T> implements Liste<T> {
    private class Node<T> {
        T verdi;
        Node<T> neste;

        public Node(T verdi) {
            this(verdi, null);
        }

        public Node(T verdi, Node<T> neste) {
            this.verdi = verdi;
            this.neste = neste;
        }

        public boolean inneholder(T verdi) {
            if (this.verdi.equals(verdi)) {
                return true;
            }
            if (neste == null) {
                return false;
            }
            return neste.inneholder(verdi);
        }
    }

    Node<T> hode;

    public LenketListe() {
        hode = null;
    }

    @Override
    public boolean leggInn(T t) {
        // Legger inn i *starten*
        hode = new Node<>(t, hode);
        return true;
    }

    @Override
    public boolean fjern(T t) {
        Node<T> current = hode;
        Node<T> prev = null;
        while (current != null) {
            if (current.verdi.equals(t)) {
                if (current == hode)
                    hode = current.neste;
                else
                    prev.neste = current.neste;
                current.neste = null;
                return true;
            }
            prev = current;
            current = current.neste;
        }
        return false;
    }

    @Override
    public int antall() {
        int ant = 0;
        Node<T> current = hode;
        while (current != null) {
            ant++;
            current = current.neste;
        }
        return ant;
    }

    @Override
    public boolean tom() {
        return (hode == null);
    }

    @Override
    public boolean inneholder(T t) {
        if (tom()) {
            return false;
        }
        return hode.inneholder(t);
    }

    @Override
    public void nullstill() {
        // rask måte, teknisk sett litt dårlig:
        hode = null;
    }

    @Override
    public boolean leggInn(int indeks, T t) {
        return false;
    }

    @Override
    public T hent(int indeks) {
        // sjekkIndeks(indeks, false);
        Node<T> current = hode;
        for (int i = 0; i < indeks; i++) {
            current = current.neste;
        }
        return current.verdi;
    }

    @Override
    public T oppdater(int indeks, T t) {
        return null;
    }

    @Override
    public boolean fjern(int indeks) {
        return false;
    }

    @Override
    public int indeksTil(T t) {
        return 0;
    }
}

public class Dato_09_15_Lister {
}
