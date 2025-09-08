import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Dato_09_08_Generisk_Kode {
    public static void main(String[] args) {
        // Finnes mange Arrays.toString-metoder
        // Integer i = new Integer(7); // lov men ikke anbefalt
        Integer j = 7; // Automatisk oversetting
        int[] tabell = {1, 2, 3, 4};
        Integer[] tabellInteger = new Integer[tabell.length];
        for (int k = 0; k < tabell.length; k++) {
            tabellInteger[k] = tabell[k];
        }

        String hallo = "Hallo!";
        //Integer hallo = 7; // Vil medføre feil om vi kjører men ikke kompileringsfeil, for BoksObjekt
        String passord = "1234";
        BoksObjekt hemmeligKode = new BoksObjekt(hallo, passord);

        String kode = (String) hemmeligKode.hentUt("1234");

        // Holder styr på at vi skal ha en String, så Integer hallo vil gi kompileringsfeil
        BoksGenerisk<String> nyHemmeligKode = new BoksGenerisk<>(hallo, passord);

        kode = nyHemmeligKode.hentUt("1234");

        // Eksempler på ting som implementerer Comparable<T>:
        System.out.println("Hallo".compareTo("Hei")); // <0 - "Hallo" kommer først i alfabetet
        System.out.println("Hallo".compareTo("Hallo")); // =0 - De er like.
        System.out.println("Hei".compareTo("Hallo")); // >0 - "Hei" kommer sist i alfabetet

        Integer a = 5;
        Integer b = 8;
        System.out.println(a.compareTo(b)); // < 0 - 5 < 8.
        System.out.println(a.compareTo(a)); // 0 - 5 = 5
        System.out.println(b.compareTo(a)); // >0 - 8 > 5
        // Idé: Om vi vet at typen vi ser på implementerer Comparable<T> så kan
        // vi bruke compareTo.

        System.out.println(maks(tabellInteger));

        Person[] tabellPersoner = {new Person("Nikolai", "Hansen"), new Person("Peder", "Hansen")};
        System.out.println(maks(tabellPersoner));

        A[] tabellA = {new A()};
        B[] tabellB = {new B()};
        C[] tabellC = {new C()};

        maks(tabellA);
        maks(tabellB);
        maks(tabellC); // Kun funker med besvergelsen <? super T>

        // Bruk av Comparator
        Integer[] tabellInteger2 = {7, 4, 1, 8, 2, 5};
        // Innebygde comparators for comparables.
        Comparator<Integer> cmp = Comparator.naturalOrder();
        System.out.println(maks(tabellInteger2, cmp));
        cmp = Comparator.reverseOrder();
        System.out.println(maks(tabellInteger2, cmp));

        Comparator<Person> cmpPers = Comparator.naturalOrder();
        System.out.println(maks(tabellPersoner, cmpPers));

        cmpPers = new IslandskSammenlikner();
        System.out.println(maks(tabellPersoner, cmpPers));
    }


    public <T> void bytt(T[] tabell, int i, int j) {
        T tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static <T extends Comparable<? super T>> int maks(T[] tabell) {
        // Hvorfor Comparable<? super T> i stedet for Comparable<T>? Se notat.
        if (tabell.length == 0)
            throw new NoSuchElementException("Tom tabell");
        T maksVerdi = tabell[0];
        int maksPosisjon = 0;
        for (int i = 1; i < tabell.length; i++) {
            if (0 > maksVerdi.compareTo(tabell[i])) {
                maksVerdi = tabell[i];
                maksPosisjon = i;
            }
        }
        return maksPosisjon;
    }


    public static <T> int maks(T[] tabell, Comparator<? super T> cmp) {
        // Hvorfor Comparable<? super T> i stedet for Comparable<T>? Se notat.
        if (tabell.length == 0)
            throw new NoSuchElementException("Tom tabell");
        T maksVerdi = tabell[0];
        int maksPosisjon = 0;
        for (int i = 1; i < tabell.length; i++) {
            if (0 > cmp.compare(maksVerdi, tabell[i])) {
                maksVerdi = tabell[i];
                maksPosisjon = i;
            }
        }
        return maksPosisjon;
    }
}

class IslandskSammenlikner implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        int s = o1.fornavn.compareTo(o2.fornavn);
        if (s != 0)
            return s;
        return o1.etternavn.compareTo(o2.etternavn);
    }
}
class BoksObjekt {
    private Object verdi;
    private String passord;

    public BoksObjekt(Object verdi, String passord) {
        this.verdi = verdi;
        this.passord = passord;
    }

    public Object hentUt(String passord) {
        if (!passord.equals(this.passord))
            throw new IllegalStateException("Feil passord!");
        return verdi;
    }
}

class BoksGenerisk<T> {
    private T verdi;
    private String passord;

    public BoksGenerisk(T verdi, String passord) {
        this.verdi = verdi;
        this.passord = passord;
    }

    public T hentUt(String passord) {
        if (!passord.equals(this.passord))
            throw new IllegalStateException("Feil passord!");
        return verdi;
    }
}

class Person implements Comparable<Person> {
    String fornavn;
    String etternavn;

    public Person(String fornavn, String etternavn) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public String greeting() {
        return "Hallo! Jeg heter " + this.fornavn + " " + this.etternavn + ".";
    }

    @Override
    public int compareTo(Person other) {
        int s = this.etternavn.compareTo(other.etternavn);
        if (s != 0)
            return s;
        return this.fornavn.compareTo(other.fornavn);
    }
}

class A implements Comparable<A> {
    @Override
    public int compareTo(A other) {
        return 0;
    }
}

class B extends A {}

class C implements Comparable<Object> {
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}