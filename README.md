# PlayPadel

Padel je najbrže rastući sport u svijetu te je dostupno sve više dvorana u gradu za igranje. 
PlayPadel je platforma koju igrači mogu koristiti kako bi lakše pronašli slobodni teren i termin za igru, a vlasnici terena mogu lakše organizirati turnire. 
Platforma također služi za promociju padela kao sporta, te se igrači mogu prijaviti na turnire, pratiti rezultate prošlih turnira te objavljivati slike i komentare vezane za turnir.

# Opis projekta
Ovaj projekt je reuzltat timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. 

Cilj ovog projekta je izgraditi aplikaciju koja povezuje igrače i vlasnike terena padela. Na taj način ćemo olakšati komunikaciju i organizaciju svima zainteresiranima za padel.

Platforma također služi za promociju padela kao sporta. Mogućnost pratićenja prošlih, aktivnih i budućih turnira pozitivno utječe na izgradnju zajednice.

Cilj tima koji izrađuje ovaj projekt je savladati osnove razvoja softvera i razviti dobre navike . Kroz projekt ćemo naučiti koristiti neke od popularnih razvojnih okruženja, Spring i React, te naučiti konvencije git-a i GitHuba. 


# Funkcijski zahtjevi
## Aplikacija
 - aplikacija mora omogućiti korisniku prijavljivanje kao igrač, vlasnik terena ili administrator
 - mogućnost plaćanja unutar aplikacije (koristeći PayPal ili kreditnom karticom) ili gotovinom isključivo pri korištenju terena
## Administrator
 - administrator upravlja cijenom članarine te može mijenjati podatke od ostalih korisnika
## Vlasnici terena
 - vlasnik terena mora moći dodati podatke o dvorani poput naziva, adrese, kontakt telefona, popis terena i turnira
 - vlasnik terena mora moći organizirati turnir
 - vlasnik terena mora odobriti svakog igrača na svom turniru
## Igrači
 - igrač mora moći rezervirati termine, pregledavati termine, odabrati način plaćanja termina, otkazati termin i pregledavati turnire
 - za pregled i rezervaciju termina koristi se Google kalendar
 - igrač mora moći otkazati termin najkasnije 24h prije samoga termina
 - termin se mora moći pregledati temeljem kriterija cijene kotizacije, razine igrača i iznosu nagrade
 - igrač se mora moći prijaviti na otvoren turnir
 - ako je igrač sudjelovao na turniru može komentirati turnire i stavljati slike s tog turnira
 - igrač se može pretplatiti na sadržaj o turnirima i time primati obavijesti kada je novi turnir objavljen
## Tereni 
 - svaki teren mora imati lokaciju, sliku, tip terena i termine
## Termini
 - termini moraju imati datum, vrijeme i cijenu
## Turniri
 - turnir mora imati podatke poput naziva, lokacije, datuma, cijenu kotizacije, nagrade i opis, te ako je završen rezultate turnira i fotografije
 - podatke o turniru unosi organizator, tj. vlasnik terena
 

## Nefunkcijski zahtjevi
 - podatci o korisnicima su zaštićeni



# Tehnologije

#Instalcija
# Članovi tima 
> Lorena Ivanišević, Paula Jagić, Dino Plečko, Boris Šeremet, Klara Vrbanac, Šimun Vrsalović, Borna Zelić

# Kontribucije
>Pravila ovise o organizaciji tima i su često izdvojena u CONTRIBUTING.md



# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.
>### Poboljšajte funkcioniranje tima:
>* definirajte načina na koji će rad biti podijeljen među članovima grupe
>* dogovorite kako će grupa međusobno komunicirati.
>* ne gubite vrijeme na dogovore na koji će grupa rješavati sporove primjenite standarde!
>* implicitno podrazmijevamo da će svi članovi grupe slijediti kodeks ponašanja.
 
>###  Prijava problema
>Najgore što se može dogoditi je da netko šuti kad postoje problemi. Postoji nekoliko stvari koje možete učiniti kako biste najbolje riješili sukobe i probleme:
>* Obratite mi se izravno [e-pošta](mailto:vlado.sruk@fer.hr) i  učinit ćemo sve što je u našoj moći da u punom povjerenju saznamo koje korake trebamo poduzeti kako bismo riješili problem.
>* Razgovarajte s vašim asistentom jer ima najbolji uvid u dinamiku tima. Zajedno ćete saznati kako riješiti sukob i kako izbjeći daljnje utjecanje u vašem radu.
>* Ako se osjećate ugodno neposredno razgovarajte o problemu. Manje incidente trebalo bi rješavati izravno. Odvojite vrijeme i privatno razgovarajte s pogođenim članom tima te vjerujte u iskrenost.

# 📝 Licenca
Važeča (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija
