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
}
