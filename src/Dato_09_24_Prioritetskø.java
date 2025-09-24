import java.util.NoSuchElementException;

public class Dato_09_24_Prioritetskø {
}

// Denne klassen er langt fra ferdig. Er et lite prosjekt å fullføre.
class PrioritetsKø<T> {
    class PrioritetsNode {
        T verdi;
        int prioritet;
        PrioritetsNode venstre, høyre, forelder;

        public PrioritetsNode(T verdi, int prioritet, PrioritetsNode forelder) {
            this.verdi = verdi;
            this.forelder = forelder;
            this.prioritet = prioritet;
            venstre = null; høyre = null;
        }
    }

    PrioritetsNode rot = null;
    int antall = 0;

    private PrioritetsNode finnSisteNode() {
        // Skal finne den "siste" noden i det komplette treet.
        // Dere kommer senere til å lære hvordan vite posisjon basert på nummer til node, som her kan brukes til å vite
        // om man burde gå høyre/venstre. Man må se på binærtallrepresentasjonen av antall elementer i treet.
        throw new UnsupportedOperationException("Denne har jeg ikke laget.");
    }

    private PrioritetsNode finnNesteLedigeForelder() {
        // Skal finne forelderen til neste ledige posisjon i treet.
        // Må bruke liknende logikk som `finnSisteNode`, men hvor du vil finne posisjon "n+1" heller.
        // Må stoppe "tidlig" siden du skal finne forelderen, slipper gå siste steget.
        throw new UnsupportedOperationException("Ikke laget.");
    }

    // Lagde denne etter forelesning, så det skulle være lettere å forstå
    // hva som skjer.
    private void byttNoder(PrioritetsNode a, PrioritetsNode b) {
        // Bytter verdi og prioritet mellom to noder.
        // Verdi:
        T tmp = a.verdi;
        a.verdi = b.verdi;
        b.verdi = tmp;
        // Prioritet:
        int tmpPrio = a.prioritet;
        a.prioritet = b.prioritet;
        b.prioritet = tmpPrio;
    }

    public void leggInn(T verdi, int prioritet) {
        if (rot == null) {
            rot = new PrioritetsNode(verdi, prioritet, null);
            antall++;
            return;
        } // Denne skrev jeg ikke i forelesning, men var ganske rett frem, så la til nå.
        PrioritetsNode nlf = finnNesteLedigeForelder();
        PrioritetsNode siste = new PrioritetsNode(verdi, prioritet, nlf);
        if (nlf.venstre == null)
            nlf.venstre = siste;
        else
            nlf.høyre = siste;
        while (siste != rot) {
            if (siste.prioritet < siste.forelder.prioritet) {
                byttNoder(siste, siste.forelder); // Bytt verdi/prio opp et steg.
                siste = siste.forelder; // Gå opp et steg.
            } else
                break;
        }
        antall++;
    }

    public T taUt() {
        if (tom())
            throw new NoSuchElementException("Tom kø.");
        PrioritetsNode siste = finnSisteNode();
        // Vi må nå:
        // - Bytt verdi mellom første og siste node.
        // - Slett siste node
        // - La øverste verdi "boble nedover" ved å bytte med dens minste barn.
        if (true) // kun her sånn at java ikke klager over at det under er unreachable.
            throw new UnsupportedOperationException("Ikke laget resten ennå");
        antall--;
        return null;
    }

    public T kikk() {
        if (tom())
            throw new NoSuchElementException("Tom kø.");
        return rot.verdi;
    }

    public boolean tom() {
        return (rot == null);
    }
}