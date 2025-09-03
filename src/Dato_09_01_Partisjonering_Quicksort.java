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
        System.out.println(Arrays.toString(tilfeldigListe));
        kvikkSorter(tilfeldigListe);
        System.out.println(Arrays.toString(tilfeldigListe));

        // Tester at vi faktisk sorterer, ved å teste *alle* tabeller på 10 element.
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

    public static boolean erVokal(char a) {
        char[] vokaler = {'a', 'e', 'i', 'o', 'u', 'y', 'æ', 'ø', 'å'};
        for (int i = 0; i < vokaler.length; i++) {
            if (a == vokaler[i])
                return true;
        }
        return false;
    }

    public static void parterVokal(char[] tabell) {
        int venstre = 0;
        int høyre = tabell.length - 1;
        while (venstre < høyre) {
            while (venstre < høyre && erVokal(tabell[venstre]))
                venstre++;
            while (venstre < høyre && !erVokal(tabell[høyre]))
                høyre--;
            bytt(tabell, venstre++, høyre--);
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

    /*
    Grunnen til <= i denne varianten og if-test som avbryter er at vi
    må passe på at metoden *alltid* avslutter med at "venstre" peker
    på første verdi som er større enn eller lik pivot.

    Om vi hadde gjort som forrige ville vi noen ganger pekt på siste
    verdi som er mindre enn pivot, og måtte sjekka for dette i
    kvikkSorter-metoden.
     */
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

    public static void kvikkSorter(int[] tabell, int fra, int til) {
        if (til - fra <= 0)
            return;
        int k = parterPåVerdi(tabell, fra, til);
        kvikkSorter(tabell, fra, k-1);
        kvikkSorter(tabell, k+1, til);
    }

    public static void kvikkSorter(int[] tabell) {
        kvikkSorter(tabell, 0, tabell.length-1);
    }
}
