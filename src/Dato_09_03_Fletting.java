import java.util.Arrays;

public class Dato_09_03_Fletting {
    public static void main(String[] args) {
        int[] a = {2, 4, 5, 10, 23, 49};
        int[] b = {1, 7, 13, 14, 15, 35, 53, 56};
        int[] resultat = sortertFlett(a, b);
        System.out.println(Arrays.toString(resultat));

        int[] usortertTabell = {2, 4, 1, 7, 13, 3, 5, 10};
        int[] sortertTabell = fletteSorter(usortertTabell);
        System.out.println(Arrays.toString(sortertTabell)); // Sortert variant
        System.out.println(Arrays.toString(usortertTabell)); // Ikke endret.
    }

    public static int[] sortertFlett(int[] a, int[] b) {
        int n = a.length + b.length;
        int[] resultat = new int[n];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                resultat[k++] = a[i++];
            else
                resultat[k++] = b[j++];
            // resultat[k++] = (a[i] < b[j]) ? a[i++] : b[j++]; // gjør det samme
        }

        while (i < a.length) resultat[k++] = a[i++];
        while (j < b.length) resultat[k++] = b[j++];

        return resultat;
    }

    public static int[] fletteSorter(int[] tabell) {
        if (tabell.length <= 1)
            return tabell.clone();
        int halve = tabell.length / 2;
        int[] førsteHalvdel = new int[halve];
        int[] andreHalvdel = new int[tabell.length-halve];
        for (int i = 0; i < halve; i++) {
            førsteHalvdel[i] = tabell[i];
        }
        for (int i = 0; i < tabell.length - halve; i++) {
            andreHalvdel[i] = tabell[i+halve];
        }
        førsteHalvdel = fletteSorter(førsteHalvdel);
        andreHalvdel = fletteSorter(andreHalvdel);
        return sortertFlett(førsteHalvdel, andreHalvdel);
    }
}
