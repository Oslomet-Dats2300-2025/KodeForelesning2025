import java.util.Arrays;

public class Dato_09_10_Rekursjon {
    static int trekk = 1;
    public static void main(String[] args) {
        //hanoisTårn(3, 'C', 'A', 'B');

        int[] tab = {2, 3, 5, 10, 1, 7, 8, 9, 8, 4};
        kvikkSorter(tab);

        int[] tiTabell = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] svar = tiTabell.clone();
        do {
            int[] c = tiTabell.clone();
            kvikkSorter(c);
            if (!Arrays.equals(c, svar)) {
                System.out.println("Fikk gal sortering på tabell " + Arrays.toString(tiTabell) + ". Fikk " + Arrays.toString(c));
            }

        } while (EkstraFunksjoner.nestePermutasjon(tiTabell));
    }
    public static void hanoisTårn(int antall, char fra, char til, char hjelp) {
        if (antall <= 0) return;
        hanoisTårn(antall-1, fra, hjelp, til);
        System.out.println("Trekk: " + trekk + ": Flytter disk fra " + fra + " til " + til + ".");
        trekk++;
        hanoisTårn(antall-1, hjelp, til, fra);
    }

    public static void bytt(int[] tabell, int i, int j) {
        if (i == j) return;
        tabell[i] = tabell[i] ^ tabell[j];
        tabell[j] = tabell[i] ^ tabell[j];
        tabell[i] = tabell[i] ^ tabell[j];
    }

    public static int parterPåVerdi(int[] tabell, int fra, int til) {
        int venstre = fra;
        int høyre = til-1;
        int pivot = tabell[til];
        while (true) {
            while (venstre <= høyre && tabell[venstre] < pivot)
                venstre++;
            while (venstre <= høyre && tabell[høyre] >= pivot)
                høyre--;
            if (venstre > høyre) break;
            bytt(tabell, venstre++, høyre--);
        }
        bytt(tabell, venstre, til);
        return venstre;
    }

    public static void kvikkSorterLittRekursiv(int[] tabell, int fra, int til) {
        while (til - fra > 0) {
            int k = parterPåVerdi(tabell, fra, til);
            kvikkSorterLittRekursiv(tabell, fra, k - 1);
            fra = k + 1;
        }
    }

    public static void kvikkSorter(int[] tabell) {
        int fra, til;
        // Vi lager vår egen "funksjonsstabel"
        // Den må lagre to verdier i hver "løkke", en fra og en til.
        // Den kan derfor være maksimalt omtrent 2n lang, om vi er uheldige
        int[] stabel = new int[2*tabell.length];
        int stabelPeker = 0;
        // Vi starter med å gå fra 0 til tabell.length - 1 (inklusive)
        stabel[stabelPeker++] = 0;
        stabel[stabelPeker++] = tabell.length - 1;
        while (stabelPeker > 0) {
            // Hent ut hva fra og til skal være
            til = stabel[--stabelPeker];
            fra = stabel[--stabelPeker];
            while (til - fra > 0) {
                // Kunne kopiert inn parterPåVerdi i stedet for å kalle på funksjonen
                int k = parterPåVerdi(tabell, fra, til);
                // lagre fra-og-til-verdier vi senere må quicksorte
                stabel[stabelPeker++] = fra;
                stabel[stabelPeker++] = k-1;
                fra = k + 1;
            }
        }
    }

    public static void kvikkSorterRekursiv(int[] tabell) {
        kvikkSorterLittRekursiv(tabell, 0, tabell.length-1);
    }
}
