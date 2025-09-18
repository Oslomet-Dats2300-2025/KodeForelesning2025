import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Dato_09_17_Iterasjon {
    public static void main(String[] args) {
        TabellListeMedIterator<Integer> tlmi = new TabellListeMedIterator<>();
        tlmi.leggInn(7);
        tlmi.leggInn(8);
        tlmi.leggInn(14);
        tlmi.leggInn(83);
        tlmi.leggInn(57);

        System.out.println("En enhanced for-loop:");
        for (Integer i : tlmi) {
            System.out.println(i);
        }

        // Er ekvivalent med
        System.out.println("Gjøre det manuelt:");
        Iterator<Integer> it = tlmi.iterator();
        while (it.hasNext()) {
            Integer i = it.next();
            System.out.println(i);
        }

        System.out.println("Start på posisjon 2");
        it = tlmi.iterator(2);
        while (it.hasNext()) {
            Integer i = it.next();
            System.out.println(i);
        }

        // En uendelig for-løkke, i teorien
        System.out.println("Alle positive heltall");
        AllePositiveHeltall aph = new AllePositiveHeltall();
        for (int i : aph) {
            System.out.println(i);
            if (i > 5000) {
                break;
            }
        }

        // Hva gjør metoden forEach?
        // Her: Printer hvert element, også
        System.out.println("Bruker forEach:");
        tlmi.forEach(i -> System.out.println(i));
        // alternativ skrivemåte:
        System.out.println("Alternativ skrivemåte:");
        tlmi.forEach(System.out::println);

        System.out.println("Lenket Liste Iterator:");
        LenketListeMedIterator<String> llmi = new LenketListeMedIterator<>();
        llmi.leggInn("Mars");
        llmi.leggInn("Goodbye");
        llmi.leggInn("World");
        llmi.leggInn("Hello");
        for (String s : llmi) {
            System.out.println(s);
        }

        // Til sammenlikning med Javas lenkede liste:
        System.out.println("Javas innebygde lenkede liste gir:");
        LinkedList<String> ll = new LinkedList<>();

        ll.add("Hello");
        ll.add("World");
        ll.add("Goodbye");
        ll.add("Mars");

        for (String s : ll) {
            System.out.println(s);
        }
    }
}
class TabellListeMedIterator<T> implements Liste<T>, Iterable<T> {
    private T[] tabell;
    private int antall, kapasitet;

    public TabellListeMedIterator() {
        this(10);
    }

    public TabellListeMedIterator(int kapasitet) {
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

class LenketListeMedIterator<T> implements Liste<T>, Iterable<T> {
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

    public LenketListeMedIterator() {
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
class AllePositiveHeltall implements Iterable<Integer> {

    @Override
    public Iterator<Integer> iterator() {
        return new AllePositiveHeltallIterator();
    }

    private class AllePositiveHeltallIterator implements Iterator<Integer> {
        int i = 1;
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return i++;
        }
    }
}

class Itererbar<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    // Denne metoden trenger man typisk ikke implementere
    // Kan implementere om man har en bedre løsning.
    @Override
    public void forEach(Consumer<? super T> action) {
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            T t = it.next();
            action.accept(t);
        }
    }

    class EnIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        // Det finnes en "remove"-metode
        // Ikke implementert som default.
        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
}