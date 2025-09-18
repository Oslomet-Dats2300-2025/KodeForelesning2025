import java.util.function.Consumer;

public class Dato_09_17_Funksjonelle_Interfaces {
    public static void main(String[] args) {
        // Vil gange et tall med seg selv, og så printe det.
        GangMedSelv gms = new GangMedSelv();
        printFunksjonellInterface(gms, 7);

        // En bedre skrivemåte:
        printFunksjonellInterface(i -> i*i*i ,7);
    }


    public static void printFunksjonellInterface(FunksjonellInterface f, int i) {
        System.out.println(f.funksjon(i));
    }
}

class GangMedSelv implements FunksjonellInterface {
    public int funksjon(int i) {
        return i * i;
    }
}

@FunctionalInterface // Sjekker at jeg har nøyaktig én metode.
interface FunksjonellInterface {
    int funksjon(int i);
}

interface NyFunksjonellInterface {
    String strengFunksjon(int i);
}

class Printer implements Consumer<Integer> {
    @Override
    public void accept(Integer integer) {
        System.out.println(integer);
    }
}