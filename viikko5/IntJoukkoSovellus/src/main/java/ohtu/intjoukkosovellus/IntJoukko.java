
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti > 0) {
          alusta(kapasiteetti,OLETUSKASVATUS);
        }
    }


    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti > 0 && kasvatuskoko > 0) {
        alusta(kapasiteetti, kasvatuskoko);
      }
    }

    public void alusta(int kapasiteetti,int kasvatuskoko) {
      this.ljono = new int[kapasiteetti];
      this.alkioidenLkm = 0;
      this.kasvatuskoko = kasvatuskoko;
    }

    public boolean kasvataKokoaTarvittaessa() {
      if (alkioidenLkm >= ljono.length) {
        int[] uusijono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(ljono,uusijono);
        ljono = uusijono;
        return true;
      }
        return false;
    }

    public boolean lisaa(int luku) {
      if (!kuuluu(luku)) {
        kasvataKokoaTarvittaessa();
        ljono[alkioidenLkm] = luku;
        alkioidenLkm++;
        return true;
      }
      return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int apu = 0;
        for (int i = 0; i < alkioidenLkm; i++) {
          ljono[i - apu] = ljono[i];
          if (luku == ljono[i]) {
            apu++;
          }
        }
        alkioidenLkm -= apu;
        return apu > 0;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
            if (alkioidenLkm == 0) {
              return "{}";
            }
            return "{" + listaaAlkiot() + "}";
    }

    public String listaaAlkiot() {
      String tuotos = "";
      for (int i = 0; i < alkioidenLkm - 1; i++) {
          tuotos += ljono[i];
          tuotos += ", ";
      }
      tuotos += ljono[alkioidenLkm - 1];
      return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }


    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            x.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            x.lisaa(b.toIntArray()[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (a.toIntArray()[i] == b.toIntArray()[j]) {
                    y.lisaa(b.toIntArray()[j]);
                }
            }
        }
        return y;
    }

    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(bTaulu[i]);
        }

        return z;
    }

}
