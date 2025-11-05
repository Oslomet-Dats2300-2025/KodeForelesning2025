import java.util.NoSuchElementException;

public class Dato_11_05_Eksamen2023 {
    public static void main(String[] args) {
        LenketListe2023<Integer> ll = new LenketListe2023<>();
        int[] a = {3, 7, 1, 8, 4, 8, 2, 9};
        for (int i : a)
            ll.leggInn(i);
        LenketListe2023<Integer> filtrert = ll.filtrer(
                i -> (i % 2 == 0)
        );
        System.out.println("Filtrert:");
        while (!filtrert.tom())
            System.out.println(filtrert.taUt());
        LenketListe2023<Integer> filtrertR = ll.filtrerRekursivt(
                i -> (i % 2 == 0)
        );
        System.out.println("Filtrert Rekursiv:");
        while (!filtrertR.tom())
            System.out.println(filtrertR.taUt());
        System.out.println("Hele:");
        while (!ll.tom())
            System.out.println(ll.taUt());

    }
}
@FunctionalInterface
interface Test2023<T> {
    boolean test(T verdi);
}
class LenketListe2023<T> {
    private Node hode;
    private final class Node {
        Node neste; T verdi;
        private Node(T verdi, Node neste) {
            this.verdi = verdi; this.neste = neste;
        }
        private Node(T verdi) {
            this(verdi, null);
        }
    }
    public LenketListe2023() {
        hode = null;
    }
    public boolean tom() {
        return hode == null;
    }
    public void leggInn(T verdi) {
        if (hode == null) hode = new Node(verdi);
        else hode = new Node(verdi, hode);
    }
    public T taUt() {
        if (hode == null)
            throw new NoSuchElementException("Lista er tom");
        T verdi = hode.verdi; hode = hode.neste;
        return verdi;
    }
    public LenketListe2023<T> filtrer(Test2023<T> p) {
        /*
        Dette er koden dere skal skrive
        */
        LenketListe2023<T> nyLL = new LenketListe2023();
        Node denne = hode;
        while (denne != null) {
            if (p.test(denne.verdi))
                nyLL.leggInn(denne.verdi);
            denne = denne.neste;
        }
        LenketListe2023<T> riktigVei = new LenketListe2023();
        while (!nyLL.tom()) {
            riktigVei.leggInn(nyLL.taUt());
        }
        return riktigVei;
    }

    public LenketListe2023<T> filtrerRekursivt(Test2023<T> p) {
        LenketListe2023<T> nyLL = new LenketListe2023<>();
        filtrerRekursivt(p, hode, nyLL);
        return nyLL;
    }

    private void filtrerRekursivt(Test2023<T> p, Node denne, LenketListe2023<T> ll) {
        if (denne == null)
            return;
        filtrerRekursivt(p, denne.neste, ll);
        if (p.test(denne.verdi))
            ll.leggInn(denne.verdi);
    }
}