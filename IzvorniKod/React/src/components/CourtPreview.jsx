import { useState } from "react";
import NavBar from "@/components/Navbar";
import {
	Card,
	CardHeader,
	CardTitle,
	CardDescription,
	CardContent,
	CardFooter,
} from "./ui/card";
import { Button } from "@headlessui/react";
import { HeartIcon } from "lucide-react";

export default function CourtPreview() {
	// const userInfo = JSON.parse(sessionStorage.getItem("userInfo"));

	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});

	console.log(userInfo);
	const courts = [
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
		{
			name: " naziv terena ",
			type: "vanjski ",
			location: "adress",
			contactinfo: "email vlasnika terena",
		},
	];

	if (!userInfo) return <p>Loading...</p>;

	// checked in local storage
	return (
		<>
			<NavBar></NavBar>
			<div className="top-0 m-auto mt-10 text-left">
				<div className="flex">
					<h1 className="text-left m-10">Moji tereni</h1>
				</div>
				<Button className="h-fit text-white ml-10">Dodaj novi</Button>

				<div className="flex h-fit flex-wrap">
					{courts.map((court) => (
						<Card className="w-[350px] m-8">
							<CardHeader>
								<CardTitle>{court.name}</CardTitle>
								<CardDescription>{court.type}</CardDescription>
								<CardDescription>{court.location}</CardDescription>
							</CardHeader>
							<CardContent>
								<image></image>
							</CardContent>
							<CardFooter className="flex justify-between">
								{/* on click open court details */}
								<Button variant="outline" className="text-white">
									Više
								</Button>
							</CardFooter>
						</Card>
					))}
				</div>
			</div>
		</>
	);
}
