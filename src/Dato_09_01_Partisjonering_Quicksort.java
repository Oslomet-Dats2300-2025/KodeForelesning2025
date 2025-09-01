import java.util.Arrays;

public class Dato_09_01_Partisjonering_Quicksort {
    public static void main(String[] args) {
        char[] tabell = {'a', 'c', 'd', 'e', 'h', 'i', 'k', 'o', 'i', 'r', 'u', 'z'};
        parterVokal(tabell);
        System.out.println(Arrays.toString(tabell));
        char[] kunVokaler = {'a', 'o', 'y', 'e'};
        parterVokal(kunVokaler);
        System.out.println(Arrays.toString(kunVokaler));
        char[] kunKonsonanter = {'k', 't', 's', 'l'};
        parterVokal(kunKonsonanter);
        System.out.println(Arrays.toString(kunKonsonanter));

        int[] tallTabell = {1, 5, 4, 3, 6};
        parterPåVerdi(tallTabell, 0, tallTabell.length - 1);
        System.out.println(Arrays.toString(tallTabell));
        int[] tilfeldigListe = EkstraFunksjoner.randPerm(10);
        kvikkSort(tilfeldigListe);
        System.out.println(Arrays.toString(tilfeldigListe));
    }

    public static boolean erVokal(char a) {
        char[] vokaler = {'a', 'e', 'i', 'o', 'u', 'y', 'æ', 'ø', 'å'};
        for (int i = 0; i < vokaler.length; i++) {
            if (a == vokaler[i])
                return true;
        }
        return false;
    }

    public static void parterVokal(char[] tabell) {
        int v = 0;
        int h = tabell.length - 1;
        while (v < h) {
            while (v < h && erVokal(tabell[v]))
                v++;
            while (v < h && !erVokal(tabell[h]))
                h--;
            bytt(tabell, v++, h--);
        }
    }

    public static void bytt(char[] tabell, int i, int j) {
        char tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static void bytt(int[] tabell, int i, int j) {
        int tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static int parterPåVerdi(int[] tabell, int fra, int til) {
        int v = fra;
        int h = til-1;
        int pivot = tabell[til];
        while (v < h) {
            while (v <= h && tabell[v] < pivot)
                v++;
            while (v <= h && tabell[h] >= pivot)
                h--;
            if (v > h) break;
            bytt(tabell, v++, h--);
        }
        bytt(tabell, v, til);
        return v;
    }

    public static void kvikkSort(int[] tabell, int fra, int til) {
        if (til - fra <= 0)
            return;
        int k = parterPåVerdi(tabell, fra, til);
        kvikkSort(tabell, fra, k-1);
        kvikkSort(tabell, k+1, til);
    }

    public static void kvikkSort(int[] tabell) {
        kvikkSort(tabell, 0, tabell.length-1);
    }
}
