import { useState, useEffect } from "react";
import {
	Card,
	CardHeader,
	CardTitle,
	CardDescription,
	CardContent,
	CardFooter,
} from "../components/ui/card";

import { Button } from "@headlessui/react";
import axios from "axios";

export default function CourtsPlayer({ userInfo }) {
	const [courts, setCourts] = useState([]);

	function fetchCourts() {
		axios
			.get("/api/tereni/")
			.then((res) => {
				setCourts(res.data);
				console.log(res.data);
			})
			.catch((error) => console.log(error));
	}

	useEffect(() => {
		fetchCourts();
	}, []);

	return (
		<>
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
