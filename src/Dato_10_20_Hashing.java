import java.util.Objects;

public class Dato_10_20_Hashing {
    public static void main(String[] args) {
        HashMap<String, String> hm = new HashMap<>(10);
        hm.leggInn("Hallo", "Hei");
        hm.leggInn("Hallo", "God dag");
    }
}

interface Map<N, V> {
    public V leggInn(N nøkkel, V verdi);
    public V fjern(N nøkkel);
    public V hent(N nøkkel);
}

interface Set<V> {
    public boolean leggInn(V verdi);
    public boolean fjern(V verdi);
    public boolean inneholder(V verdi);
}

class HashSet<V> implements Set<V> {
    HashMap<V, Object> hm;
    Object obj = new Object();

    public HashSet() {
        hm = new HashMap<>(500);
    }

    @Override
    public boolean leggInn(V verdi) {
        if (hm.hent(verdi) != null)
            return false;
        hm.leggInn(verdi, obj);
        return true;
    }

    @Override
    public boolean fjern(V verdi) {
        return hm.fjern(verdi) != null;
    }

    @Override
    public boolean inneholder(V verdi) {
        return hm.hent(verdi) != null;
    }
}

class HashMap<N, V> implements Map<N, V> {

    private class Node {
        N nøkkel;
        V verdi;
        Node neste;
        public Node(N nøkkel, V verdi, Node neste) {
            this.nøkkel = nøkkel;
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    Node[] liste;
    int størrelse;

    public HashMap(int størrelse) {
        this.størrelse = størrelse;
        liste = (Node[]) new Object[størrelse];
    }

    private int beregnPosisjon(int hashVerdi) {
        int pos = hashVerdi % størrelse;
        if (pos < 0) {
            pos = størrelse + pos;
        }
        return pos;
    }

    @Override
    public V leggInn(N nøkkel, V verdi) {
        Objects.requireNonNull(nøkkel);
        int pos = beregnPosisjon(nøkkel.hashCode());
        // Vi må gjennom lista og se om verdien allerede er der, og i så fall overskrive
        Node denne = liste[pos];
        while (denne != null) {
            if (denne.nøkkel.equals(nøkkel)) {
                V tmp = denne.verdi;
                denne.verdi = verdi;
                return tmp;
            }
            denne = denne.neste;
        }
        liste[pos] = new Node(nøkkel, verdi, liste[pos]);
        return null;
    }
    @Override
    public V fjern(N nøkkel) {
        int pos = beregnPosisjon(nøkkel.hashCode());
        Node denne = liste[pos];
        Node forrige = null;
        while (denne != null) {
            if (denne.nøkkel.equals(nøkkel)) {
                if (forrige == null) {
                    liste[pos] = denne.neste;
                } else {
                    forrige.neste = denne.neste;
                }
                denne.neste = null;
                return denne.verdi;
            }
            forrige = denne;
            denne = denne.neste;
        }
        return null;
    }
    @Override
    public V hent(N nøkkel) {
        int pos = beregnPosisjon(nøkkel.hashCode());
        Node denne = liste[pos];
        while (denne != null) {
            if (denne.nøkkel.equals(nøkkel)) {
                return denne.verdi;
            }
            denne = denne.neste;
        }
        return null;
    }
}

class Test {
    int alder;
    String navn;

    public Test(int alder, String navn) {
        this.alder = alder; this.navn = navn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return alder == test.alder && Objects.equals(navn, test.navn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alder, navn);
    }
}
