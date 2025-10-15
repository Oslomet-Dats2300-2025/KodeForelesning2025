import java.util.Comparator;

public class Dato_10_08_Minimumshaug {
    public static void main(String[] args) {
        int[] tabell = {2, 7, 1, 8, 12, 4, 2, 4, 15, 6, 9};
        Minimumshaug<Integer> minHeap = new Minimumshaug<>(Comparator.naturalOrder());
        for (int i : tabell)
            minHeap.leggInn(i);
        System.out.println(minHeap);
        minHeap.leggInn(0);
        System.out.println(minHeap);
    }
}

class Minimumshaug<T> implements Beholder<T> {
    TabellListe<T> treListe;
    Comparator<? super T> cmp;

    public Minimumshaug(Comparator<? super T> cmp) {
        this.cmp = cmp;
        treListe = new TabellListe<>(8);
        // Vi teller fra plass 1 i binærtrær, så legger inn ingenting på plass 0.
        treListe.leggInn(0, null);
    }

    @Override
    public boolean leggInn(T t) {
        treListe.leggInn(t);
        int k = antall();
        int forelder = k/2;
        if (forelder == 0) return true;
        T forelderVerdi = treListe.hent(forelder);
        while (cmp.compare(t, forelderVerdi) < 0) {
            treListe.oppdater(k, forelderVerdi);
            treListe.oppdater(forelder, t);
            k = forelder;
            forelder = k/2;
            if (forelder == 0) return true;
            forelderVerdi = treListe.hent(forelder);
        }
        return true;
    }

    @Override
    public boolean fjern(T t) {
        // Lag selv
        // Må sikkert lage `fjern` i TabellListe først, da dette ikke er
        // implementert.
        return false;
    }

    public T fjern() {
        // Denne burde gi ut minste verdi. Burde bruke denne, og ikke `fjern(t)`.
        return null;
    }

    @Override
    public int antall() {
        return treListe.antall() - 1;
    }

    @Override
    public boolean tom() {
        return antall() == 0;
    }

    @Override
    public boolean inneholder(T t) {
        return treListe.inneholder(t);
    }

    @Override
    public void nullstill() {

    }

    public String toString() {
        return treListe.toString();
    }
}
