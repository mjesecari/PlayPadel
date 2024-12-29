import "../Open.css";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Layout from "@/components/Layout";
import NavBar from "@/components/Navbar";

export default function Home({ userInfo }) {
	if (!userInfo)
		return (
			<>
				<Card className="pt-3">
					<CardHeader>
						<CardTitle>PlayPadel</CardTitle>
					</CardHeader>

					<CardContent className="font-mono">
						<Link to="/login">
							<Button className="m-5 text-zinc-50">Prijava</Button>
						</Link>

						<Link to="/signup">
							<Button className="m-5 text-zinc-50">Registracija</Button>
						</Link>
					</CardContent>
				</Card>
			</>
		);
	else {
		return (
			<>
				<NavBar></NavBar>

				<Card className="mt-24">
					<CardHeader>
						<CardTitle>PlayPadel</CardTitle>
					</CardHeader>

					<CardContent className=" text-left">
						<hr></hr>

						<CardTitle className="mt-10">Što je PlayPadel?</CardTitle>
						<div className="font-serif text-lg mt-10 mb-10">
							Padel je najbrže rastući sport u svijetu, te je dostupno
							sve više dvorana u gradu za igranje. Kako bi igrači lakše
							pronašli slobodan termin i lokaciju za igru, nudimo
							platformu koja omogućava rezervaciju i plaćanje terena za
							igranje padela.
						</div>
						{!userInfo.owner && (
							<>
								<hr></hr>

								<CardTitle className="mt-10">
									Želim igrati padel!
								</CardTitle>
								<div className="font-serif text-lg mt-10 mb-10">
									Bez obzira jesi li amater ili profesionalac u padelu,
									nudimo ti mogućnost rezervacija terena od svih
									vlasnika terena registriranih u našoj aplikaciji.
									Klikni na
									<b> Tereni i termini</b> gore lijevo za prikaz svih
									dostupnih terena.
								</div>

								<div className="font-serif text-lg mt-10 mb-10">
									Također možeš vidjeti listu svih aktivnih i budućih
									turnira klikom na <b>Turniri</b>.
								</div>
							</>
						)}

						{userInfo.owner && (
							<>
								<hr></hr>

								<CardTitle className="mt-10">
									Vlasnik sam terena!
								</CardTitle>
								<div className="font-serif text-lg mt-10 mb-10">
									Dodaj svoje terene u našu aplikaciju klikom na
									<b> Moji tereni</b> kako bi omogučio igračima lakšu
									rezervaciju istih.
								</div>

								<div className="font-serif text-lg mt-10 mb-10">
									Također možeš stvoriti turnir klikom na{" "}
									<b>Moji turniri</b> na kojeg će se igrači moći
									prijaviti putem aplikacije i dijeliti slike i
									komentare.
								</div>
							</>
						)}
					</CardContent>
				</Card>
			</>
		);
	}
}
