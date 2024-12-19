import { useState, useEffect } from "react";
import axios from "axios";

export default function CourtsPlayer({ userInfo }) {
	const [courts, setCourts] = useState([]);

	function fetchCourts() {
		axios
			.get("/api/tereni")
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
	);
}
