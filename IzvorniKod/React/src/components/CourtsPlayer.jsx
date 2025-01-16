import { useState, useEffect } from "react";
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
		setEvents([
			{
				id: "1",
				title: "Event 1",
				start: "2024-12-16 13:00",
				end: "2024-12-16 15:00",
			},
			{
				id: "2",
				title: "Event 2",
				start: "2024-12-18 03:00",
				end: "2024-12-18 05:00",
			},
		]);
	}, []);

	function openCal() {
		setIsOpenCal(true);
	}

	function closeCal() {
		setIsOpenCal(false);
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
					Prikazani su moguci termini
					<CalendarApp eventsProp={events}></CalendarApp>
					Vlasnik terena dopusta rezervacije u intervalu xx:xx-xx:xx
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
								<p> <img
										src={`data:image/jpeg;base64,${court.slikaTeren.photoData}`}
										alt={court.naziv}
										style={{ width: "300px", height: "200px", objectFit: "cover" }}
										/>
								</p>
								<p>Tip terena: {court.tipTeren}</p>
								<p>Vlasnik: {court.vlasnikTeren.email}</p>
							</CardContent>
							<CardFooter className="flex justify-between">
								<Button
									variant="outline"
									className="text-white"
									id={court.idteren}
									onClick={() => openCal()}
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
