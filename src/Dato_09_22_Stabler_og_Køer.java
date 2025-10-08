import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dato_09_22_Stabler_og_Køer {
    public static void main(String[] args) {
        //Stabel<Integer> stabel = new TabellListeSomStabel<>();
        Stabel<Integer> stabel = new LenketListeSomStabel<>();
        stabel.push(12);
        stabel.push(14);
        stabel.push(23);
        stabel.pop();
        System.out.println(stabel.pop());
    }
}

interface Stabel<T> {
    void push(T t);
    T pop();
    T peek();
    boolean tom();
}

interface Kø<T> {
    void enqueue(T t);
    T dequeue();
    T peek();
    boolean tom();
}


class TabellListeSomStabel<T> implements Liste<T>, Iterable<T>, Stabel<T> {
    private T[] tabell;
    private int antall, kapasitet;

    public TabellListeSomStabel() {
        this(10);
    }

    public TabellListeSomStabel(int kapasitet) {
        this.kapasitet = kapasitet;
        tabell = (T[]) new Object[kapasitet];
        antall = 0;
    }

    public void push(T t) {
        leggInn(t);
    }

    public T pop() {
        if (tom())
            throw new NoSuchElementException("Tom Stabel");
        T tmp = tabell[--antall];
        tabell[antall] = null;
        return tmp;
    }

    public T peek() {
        if (tom())
            throw new NoSuchElementException("Tom Stabel");
        return tabell[antall-1];
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

    @Override
    public Iterator<T> iterator() {
        return new TabellListeIterator(0);
    }

    public Iterator<T> iterator(int i) {
        return new TabellListeIterator(i);
    }

    // Ny indre iteratorklasse
    private class TabellListeIterator implements Iterator<T> {
        int i;

        public TabellListeIterator(int i) {
            this.i = i;
        }

        @Override
        public boolean hasNext() {
            return i < antall;
        }

        @Override
        public T next() {
            return tabell[i++];
        }
    }
}

class LenketListeSomStabel<T> implements Liste<T>, Iterable<T>, Stabel<T> {
    @Override
    public void push(T t) {
        leggInn(t);
    }

    @Override
    public T pop() {
        if (tom())
            throw new NoSuchElementException("Tom Stabel");
        T tmp = hode.verdi;
        hode = hode.neste;
        return tmp;
    }

    @Override
    public T peek() {
        if (tom())
            throw new NoSuchElementException("Tom Stabel");
        return hode.verdi;
    }

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

    public LenketListeSomStabel() {
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

    @Override
    public Iterator<T> iterator() {
        return new LenketListeIterator();
    }

    class LenketListeIterator implements Iterator<T> {
        Node<T> current = hode;
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public T next() {
            T tmp = current.verdi;
            current = current.neste;
            return tmp;
        }
    }
}

class LenketListeSomKø<T> implements Kø<T> {

    private class Node {
        T verdi;
        Node neste;

        public Node(T verdi) {
            this(verdi, null);
        }

        public Node(T verdi, Node neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    Node hode, hale;

    public LenketListeSomKø() {
        hode = null; hale = null;
    }

    @Override
    public void enqueue(T t) {
        Node nyNode = new Node(t);
        if (hode == null) {
            hode = nyNode;
            hale = nyNode;
        } else {
            hale.neste = nyNode;
            hale = nyNode;
        }
    }

    @Override
    public T dequeue() {
        if (hode == null)
            throw new NoSuchElementException("Tom kø");
        T tmp = hode.verdi;
        hode = hode.neste;
        if (hode == null)
            hale = null;
        return tmp;
    }

    @Override
    public T peek() {
        if (hode == null)
            throw new NoSuchElementException("Tom kø");
        return hode.verdi;
    }

    @Override
    public boolean tom() {
        return (hode == null);
    }
}

class TabellListeSomKø<T> implements Kø<T> {
    T[] tabell;
    int start, slutt;
    int kapasitet, antall;

    public TabellListeSomKø(int kapasitet) {
        this.kapasitet = kapasitet;
        tabell = (T[]) new Object[kapasitet];
    }

    public TabellListeSomKø() {
        this(10);
    }

    private void utvidTabell() {
        kapasitet = 2*kapasitet;
        T[] tmp = (T[]) new Object[kapasitet];
        for (int i = 0; i < antall; i++) {
            tmp[i] = tabell[(start+i) % kapasitet];
        }
        start = 0;
        slutt = antall;
        tabell = tmp;
    }

    @Override
    public void enqueue(T t) {
        if (antall == kapasitet)
            utvidTabell();
        tabell[slutt] = t;
        slutt = (slutt + 1) % kapasitet;
        antall++;
    }

    @Override
    public T dequeue() {
        if (antall == 0) {
            throw new NoSuchElementException("Tom kø.");
        }
        antall--;
        T tmp = tabell[start];
        start = (start + 1) % kapasitet;
        return tmp;
    }

    @Override
    public T peek() {
        return tabell[start];
    }

    @Override
    public boolean tom() {
        return (antall == 0);
    }
}