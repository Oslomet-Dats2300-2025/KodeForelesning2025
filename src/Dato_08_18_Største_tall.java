import java.util.Arrays;

public class Dato_08_18_Største_tall {
    public static void main(String[] args) {
        int[] tabell = {1, 2, 27, 1, 3, 7, 13, -5, 7, 2, 9};
        int maks = maks_løkke(tabell);
        System.out.println(maks);
    }

    public static int maks_sorterer(int[] tabell) {
        Arrays.sort(tabell);
        return tabell[tabell.length - 1];
    }

    public static int maks_løkke(int[] tabell) {
        int maksFunnet = tabell[0];                 // to operasjoner: Hent tabell[0], lagre i maksFunnet
        for (int i = 1; i < tabell.length; i++) { // en operasjon, (n-1) operasjoner, (n-2) operasjoner
            if (tabell[i] > maksFunnet) // tre operasjoner, (n-1) ganger,
                maksFunnet = tabell[i]; // to operasjoner, ?? ganger
        }
        return maksFunnet; // en operasjon
    } // til sammen: 4 + (n-1) + (n-2) + 3*(n-1) + 2*k = 5n -2 + 2k
}
