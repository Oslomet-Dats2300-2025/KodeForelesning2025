import java.util.Random;

public class EkstraFunksjoner {
    public static int[] randPerm(int n) {
        Random r = new Random();
        int[] tabell = new int[n];
        for (int i = 0; i < n; ++i) tabell[i] = i+1;
        for (int k = n-1; k > 0; --k) {
            int i = r.nextInt(k+1);
            bytt(tabell, i, k);
        }
        return tabell;
    }

    public static void bytt(int[] tabell, int i, int j) {
        int tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static boolean nestePermutasjon(int[] a) {
        int n = a.length;
        int i = n - 2;
        while (i >= 0 && a[i] > a[i+1]) i--;
        if (i < 0) return false;
        int verdi = a[i];
        int j = n - 1;
        while (verdi > a[j]) j--;
        bytt(a, i, j);
        i++; j = n-1;
        while (i < j) bytt(a, i++, j--);
        return true;
    }
}
