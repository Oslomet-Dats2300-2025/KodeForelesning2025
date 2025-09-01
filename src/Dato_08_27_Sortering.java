public class Dato_08_27_Sortering {
    public static void main(String[] args) {
        int n = 100000;
        long tic = System.currentTimeMillis();
        utvalgsSorter(EkstraFunksjoner.randPerm(n));
        long toc = System.currentTimeMillis();
        System.out.println("Dette tok " + (toc - tic) + " ms");
        tic = System.currentTimeMillis();
        utvalgsSorter(EkstraFunksjoner.randPerm(2 * n));
        toc = System.currentTimeMillis();
        System.out.println("Dette tok " + (toc - tic) + " ms");
    }

    public static void utvalgsSorter(int[] tabell) {
        int minste_posisjon;
        for (int i = 0; i < tabell.length - 1; i++) {
            minste_posisjon = min(tabell, i, tabell.length);
            bytt(tabell, i, minste_posisjon);
        }
    }

    public static void bytt(int[] tabell, int i, int j) {
        int tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static int min(int[] tabell) {
        return min(tabell, 0, tabell.length);
    }

    public static int min(int[] tabell, int fra, int til) {
        // Finner minste verdi mellom "fra" og "til", inkludert "fra"
        // Burde egentlig sjekke at fra < til, og fra >= 0,
        // og fra < tabell.length, og til <= tabell.length

        int laveste_posisjon = fra;
        for (int i = fra+1; i < til; i++) {
            if (tabell[i] < tabell[laveste_posisjon])
                laveste_posisjon = i;
        }
        return laveste_posisjon;
    }

    public static boolean erSortert(int[] tabell) {
        for (int i = 1; i < tabell.length; i++) {
            if (tabell[i-1] > tabell[i]) // fant en inversjon
                return false;
        }
        return true;
    }

    public static void bobleSorter(int[] tabell) {
        for (int i = tabell.length - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (tabell[j-1] > tabell[j]) { // fant en inversjon
                    bytt(tabell, j-1, j);
                }
            }
        }
    }
}
