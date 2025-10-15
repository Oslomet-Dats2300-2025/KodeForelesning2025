import java.util.PriorityQueue;

public class Dato_10_15_Huffmanntre {
    public static void main(String[] args) {
        char[] tegn = {'A', 'B', 'C', 'D', 'E'}; // Må være sortert alfabetisk
        int[] frekvens = {17, 9, 5, 2, 12};
        HuffmannTre ht = new HuffmannTre(tegn, frekvens);
        String[] kode = ht.finnKanoniskeKoder();
        int[] lengder = ht.finnLengder();
        for (int i = 0; i < tegn.length; i++) {
            System.out.println("Kode for tegn " + tegn[i] + " er " + kode[i] + ", med lengde " + lengder[i]);
        }
        ht.gjørKanonisk();
        System.out.println(ht.kod("BADCA"));
        System.out.println(ht.dekod("0011000100001"));
    }
}

class HuffmannTre {
    private class Node implements Comparable<Node> {
        Node venstre, høyre;
        int prioritet;
        char verdi;

        private Node() {
            venstre = null; høyre = null;
            prioritet = 0;
            verdi = 0;
        }

        public Node(char verdi, int prioritet) {
            this.verdi = verdi;
            this.prioritet = prioritet;
            venstre = null; høyre = null;
        }

        public Node(Node venstre, Node høyre) {
            this.venstre = venstre; this.høyre = høyre;
            this.prioritet = venstre.prioritet + høyre.prioritet;
        }

        @Override
        public int compareTo(Node o) {
            return this.prioritet - o.prioritet;
        }
    }

    /*
    private class BladNode extends Node {
        char verdi;
    }
    Kunne lagd dette i stedet
     */

    Node rot;
    char[] tegn;

    public HuffmannTre(char[] tegn, int[] frekvenser) {
        if (tegn.length != frekvenser.length)
            throw new IllegalArgumentException("Antall tegn og antall frekvenser må være like.");
        if (tegn.length < 2)
            throw new IllegalArgumentException("Skal ha minst to tegn i et Huffmanntre");

        this.tegn = tegn;

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < tegn.length; i++) {
            pq.add(new Node(tegn[i], frekvenser[i]));
        }

        while (pq.size() > 1) {
            Node p = pq.remove();
            Node q = pq.remove();
            pq.add(new Node(p, q));
        }
        rot = pq.remove();
    }

    public void printKoder() {
        printKoder(rot, "");
    }

    private void printKoder(Node denne, String kodeTilNå) {
        if (denne.verdi != 0) {
            System.out.println("Symbol " + denne.verdi + " har kode " + kodeTilNå);
            return;
        }
        printKoder(denne.venstre, kodeTilNå + "0");
        printKoder(denne.høyre, kodeTilNå + "1");
    }

    public String[] finnKoder() {
        String[] koder = new String[tegn.length];
        finnKoder(rot, "", koder);
        return koder;
    }

    private int finn(char c) {
        for (int i = 0; i < tegn.length; i++) {
            if (tegn[i] == c)
                return i;
        }
        throw new IllegalStateException("Noe har gått galt.");
    }

    private void finnKoder(Node denne, String kodeTilNå, String[] koder) {
        if (denne.verdi != 0) {
            int pos = finn(denne.verdi);
            koder[pos] = kodeTilNå;
            return;
        }
        finnKoder(denne.venstre, kodeTilNå + "0", koder);
        finnKoder(denne.høyre, kodeTilNå + "1", koder);
    }

    public int[] finnLengder() {
        int[] lengder = new int[tegn.length];
        finnLengder(rot, 0, lengder);
        return lengder;
    }

    private void finnLengder(Node denne, int lengde, int[] lengder) {
        if (denne.verdi != 0) {
            int pos = finn(denne.verdi);
            lengder[pos] = lengde;
            return;
        }
        finnLengder(denne.venstre, lengde + 1, lengder);
        finnLengder(denne.høyre, lengde + 1, lengder);
    }

    private int maks(int[] lengder) {
        int maksVerdi = lengder[0];
        for (int i = 1; i < lengder.length; i++) {
            if (lengder[i] > maksVerdi)
                maksVerdi = lengder[i];
        }
        return maksVerdi;
    }

    public String[] finnKanoniskeKoder() {
        int[] lengder = finnLengder();
        int n = maks(lengder);
        String[] posisjoner = new String[tegn.length];
        int posisjon = 1 << n;
        for (int i = n; i >= 0; i--) {
            for (int j = 0; j < tegn.length; j++) {
                if (lengder[j] == i) {
                    posisjoner[j] = Integer.toBinaryString(posisjon++).substring(1);
                }
            }
            posisjon /= 2;
        }
        return posisjoner;
    }

    public String kod(String input) {
        StringBuilder sb = new StringBuilder();
        char[] inputKarakterer = input.toCharArray();
        String[] koder = finnKoder();
        for (char c : inputKarakterer) {
            int pos = finn(c);
            sb.append(koder[pos]);
        }
        return sb.toString();
    }

    public String kodKanonisk(String input) {
        StringBuilder sb = new StringBuilder();
        char[] inputKarakterer = input.toCharArray();
        String[] koder = finnKanoniskeKoder();
        for (char c : inputKarakterer) {
            int pos = finn(c);
            sb.append(koder[pos]);
        }
        return sb.toString();
    }

    public String dekod(String input) {
        StringBuilder sb = new StringBuilder();
        char[] inputTabell = input.toCharArray();
        Node denne = rot;
        for (char c : inputTabell) {
            if (c == '0')
                denne = denne.venstre;
            else
                denne = denne.høyre;
            if (denne.verdi != 0) {
                sb.append(denne.verdi);
                denne = rot;
            }
        }
        if (denne != rot) {
            throw new IllegalArgumentException("Ikke en lovlig tekst å dekode");
        }
        return sb.toString();
    }

    public void gjørKanonisk() {
        String[] kanoniskeKoder = finnKanoniskeKoder();
        Node nyRot = new Node();
        for (int i = 0; i < kanoniskeKoder.length; i++) {
            char[] kodeTabell = kanoniskeKoder[i].toCharArray();
            Node denne = nyRot;
            for (char c : kodeTabell) {
                if (c == '0') {
                    if (denne.venstre == null)
                        denne.venstre = new Node();
                    denne = denne.venstre;
                } else {
                    if (denne.høyre == null)
                        denne.høyre = new Node();
                    denne = denne.høyre;
                }
            }
            denne.verdi = tegn[i];
        }
        rot = nyRot;
    }
}