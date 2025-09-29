public class Dato_08_25_Binærtre {
    public static void main(String[] args) {
        Binærtre_gammel bt = new Binærtre_gammel();
        bt.rot = new Node(25);
        bt.rot.venstre = new Node(7);
        bt.rot.høyre = new Node(41);
        bt.rot.venstre.venstre = new Node(3);
        bt.rot.venstre.høyre = new Node(13);
        bt.rot.venstre.venstre.venstre = new Node(1);
        bt.rot.venstre.venstre.høyre = new Node(4);
        bt.rot.venstre.høyre.venstre = new Node(10);
        bt.rot.venstre.høyre.høyre = new Node(21);

        System.out.println(bt.finn(22));
    }
}

class Node {
    public int verdi;
    public Node venstre;
    public Node høyre;
    public Node(int verdi) {
        this.verdi = verdi;
        this.venstre = null;
        this.høyre = null;
    }
}

class Binærtre_gammel {
    Node rot;

    public Binærtre_gammel() {
        this.rot = null;
    }

    public boolean finn(int i) {
        Node p = rot;
        while (p != null) {
            if (i == p.verdi) {
                return true;
            } else if (i < p.verdi) {
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }
        return false;
    }
}