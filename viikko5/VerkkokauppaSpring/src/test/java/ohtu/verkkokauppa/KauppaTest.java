
package ohtu.verkkokauppa;

import ohtu.verkkokauppa.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

  @Test
  public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
      // luodaan ensin mock-oliot
      Pankki pankki = mock(Pankki.class);

      Viitegeneraattori viite = mock(Viitegeneraattori.class);
      // määritellään että viitegeneraattori palauttaa viitten 42
      when(viite.uusi()).thenReturn(42);

      Varasto varasto = mock(Varasto.class);
      // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
      when(varasto.saldo(1)).thenReturn(10);
      when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

      // sitten testattava kauppa
      Kauppa k = new Kauppa(varasto, pankki, viite);

      // tehdään ostokset
      k.aloitaAsiointi();
      k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
      k.tilimaksu("pekka", "12345");

      // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
      verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
      // toistaiseksi välitetään kutsussa käytetyistä parametreista
  }

  @Test
  public void tilisiirtoToimiiKahdellaEriTuotteella() {
    Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    when(varasto.saldo(1)).thenReturn(10);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    when(varasto.saldo(2)).thenReturn(20);
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 3));

    Kauppa kauppa = new Kauppa(varasto, pankki, viite);
    kauppa.aloitaAsiointi();
    kauppa.lisaaKoriin(1);
    kauppa.lisaaKoriin(2);
    kauppa.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 8);
  }

  @Test
  public void tilisiirtoToimiiKahdellaSamallaTuotteella() {
    Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(5);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "kalja", 9));

    Kauppa kauppa = new Kauppa(varasto, pankki, viite);
    kauppa.aloitaAsiointi();
    kauppa.lisaaKoriin(1);
    kauppa.lisaaKoriin(1);
    kauppa.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 18);

  }

  @Test
  public void ostetaanTuoteJotaOnVarastossaJaJotaEiOleVarastossa() {
    Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    when(varasto.saldo(1)).thenReturn(3);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "vappupallo", 10));

    when(varasto.saldo(2)).thenReturn(0);
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "joulukuusi", 7));

    Kauppa kauppa = new Kauppa(varasto, pankki, viite);
    kauppa.aloitaAsiointi();
    kauppa.lisaaKoriin(1);
    kauppa.lisaaKoriin(2);
    kauppa.tilimaksu("mluukkai","12346");

    verify(pankki).tilisiirto("mluukkai", 42, "12346", "33333-44455", 10);
  }

  @Test
  public void aloitaAsiointiNollaaEdellisetOstokset() {

    Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(3);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "peruna", 1));

    Kauppa kauppa = new Kauppa(varasto, pankki, viite);
    kauppa.aloitaAsiointi();
    kauppa.lisaaKoriin(1);
    kauppa.aloitaAsiointi();
    kauppa.lisaaKoriin(1);
    kauppa.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 1);

  }

  @Test
  public void viitenumeroKasvaa() {
    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    when(viite.uusi()).
        thenReturn(1).
        thenReturn(2).
        thenReturn(3);


  Varasto varasto = mock(Varasto.class);

  when(varasto.saldo(1)).thenReturn(3);
  when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "peruna", 1));

  Kauppa kauppa = new Kauppa(varasto, pankki, viite);
  kauppa.aloitaAsiointi();
  kauppa.lisaaKoriin(1);
  kauppa.tilimaksu("pekka", "12345");
  verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 1);

  kauppa.aloitaAsiointi();
  kauppa.lisaaKoriin(1);
  kauppa.tilimaksu("pekka", "12345");
  verify(pankki).tilisiirto("pekka", 2, "12345", "33333-44455", 1);

  kauppa.aloitaAsiointi();
  kauppa.lisaaKoriin(1);
  kauppa.tilimaksu("pekka", "12345");
  verify(pankki).tilisiirto("pekka", 3, "12345", "33333-44455", 1);

  }

  @Test

  public void poistaKoristaPoistaaTuotteen() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    when(viite.uusi()).thenReturn(42);


  Varasto varasto = mock(Varasto.class);

  when(varasto.saldo(1)).thenReturn(3);
  when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "peruna", 1));

  Kauppa kauppa = new Kauppa(varasto, pankki, viite);
  kauppa.aloitaAsiointi();
  kauppa.lisaaKoriin(1);
  kauppa.lisaaKoriin(1);
  kauppa.poistaKorista(1);
  kauppa.tilimaksu("aleksi", "135");

  verify(pankki).tilisiirto("aleksi", 42, "135", "33333-44455", 1);

  }

}
