
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public static final int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(KAPASITEETTI,OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti,OLETUSKASVATUS);
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
      if (alkioidenLkm < ljono.length) {
        return false;
      }
        int[] uusijono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(ljono,uusijono);
        ljono = uusijono;
        return true;
    }

    public boolean lisaa(int luku) {
      if (kuuluu(luku)) {
        return false;
      }
      kasvataKokoaTarvittaessa();
      ljono[alkioidenLkm] = luku;
      alkioidenLkm++;
      return true;
    }

    public boolean kuuluu(int luku) {
       return kuuluuIndeksi(luku) != -1;
    }

    public int kuuluuIndeksi(int luku) {
      for (int i = 0; i < alkioidenLkm; i++) {
          if (luku == ljono[i]) {
              return i;
          }
      }
      return -1;
    }

    public static boolean kuuluu(int[] jono, int luku) {
      for (int i = 0; i < jono.length; i++) {
        if (luku == jono[i]) {
          return true;
        }
      }
      return false;
    }



    public boolean poista(int luku) {
      if (kuuluuIndeksi(luku) != -1) {
        for (int i = kuuluuIndeksi(luku) ; i < alkioidenLkm - 1; i++) {
             ljono[i] = ljono[i + 1];
        }
        alkioidenLkm--;
        return true;
      }
      return false;
    }

    public void kopioiTaulukko(int[] vanha, int[] uusi) {
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

          for (int j = 0; j < b.mahtavuus(); j++) {
              if (kuuluu(a.toIntArray(), b.toIntArray()[j])) {
                  y.lisaa(b.toIntArray()[j]);
              }
          }

      return y;
    }

    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            z.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            z.poista(b.toIntArray()[i]);
        }
        return z;
    }

}
