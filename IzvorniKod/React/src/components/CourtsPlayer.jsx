import { useState, useEffect } from "react";
import moment from "moment";

import {
	Card,
	CardHeader,
	CardTitle,
	CardDescription,
	CardContent,
	CardFooter,
} from "../components/ui/card";
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "@/components/ui/dialog";

import CalendarApp from "./CalendarApp";
import { Button } from "@headlessui/react";
import axios from "axios";

export default function CourtsPlayer({ userInfo }) {
	const [courts, setCourts] = useState([]);
	const [events, setEvents] = useState();
	const [isOpenCal, setIsOpenCal] = useState(false);
	const [terenId, setTerenId] = useState();

	function fetchCourts() {
		axios
			.get("/api/tereni/")
			.then((res) => {
				setCourts(res.data);
			})
			.catch((error) => console.log(error));
	}

	useEffect(() => {
		fetchCourts();
		let busy = "zauzeto";
	}, []);

	function openCal(idteren) {
		axios
			.get("/api/rezervacije?terenId=" + idteren)
			.then((res) => {
				console.log("data", res.data);
				setEvents(res.data);
			})
			.then(console.log(events));
		setTerenId(idteren);
		setIsOpenCal(true);
	}

	function closeCal() {
		setIsOpenCal(false);
	}

	function sendReservation() {
		// axios
		// 	.post("/api/tereni/s")
		// 	.then((res) => {
		// 		setCourts(res.data);
		// 	})
		// 	.catch((error) => console.log(error));
	}

	return (
		<>
			<Dialog
				className="w-screen max-w-fit"
				open={isOpenCal}
				onOpenChange={setIsOpenCal}
			>
				<DialogContent className="w-screen !max-w-fit	">
					<DialogTitle>Termini</DialogTitle>
					<p>
						Prikazani su zauzeti termini. Kliknite i povucite za biranje
						željenog termina.
					</p>
					<p>Svi termini su u trajanju od jednog sata!</p>

					<CalendarApp
						//eventsProp={events}
						userInfo={userInfo}
						id={terenId}
					></CalendarApp>
				</DialogContent>
			</Dialog>

			<div className="top-0 m-auto mt-20 text-left">
				<div>
					{courts.length == 0 && (
						<h2 className="mt-24 ">Nema dostupnih terena</h2>
					)}
				</div>
				{courts.length != 0 && (
					<div>
						<h1 className="text-left m-10 ">Dostupni tereni</h1>
					</div>
				)}

				<div className="flex h-fit flex-wrap">
					{courts.map((court) => (
						<Card key={court.idteren} className="w-[350px] m-8">
							<CardHeader>
								<CardTitle>{court.nazivTeren}</CardTitle>
								<CardDescription>{court.tip}</CardDescription>
							</CardHeader>
							<CardContent>
								<p>Tip terena: {court.tipTeren}</p>
								<p>Vlasnik: {court.vlasnikTeren.email}</p>
							</CardContent>
							<CardFooter className="flex justify-between">
								<Button
									variant="outline"
									className="text-white"
									id={court.idteren}
									onClick={() => {
										openCal(court.idteren);
										console.log(court.idteren);
									}}
								>
									Rezerviraj
								</Button>
							</CardFooter>
						</Card>
					))}
				</div>
			</div>
		</>
	);
}
