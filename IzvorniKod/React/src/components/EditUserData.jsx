import React, { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "@/components/ui/dialog";
import {
	Card,
	CardHeader,
	CardContent,
	CardFooter,
	CardTitle,
	CardDescription,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import axios from "axios";

export default function EditUserData({ userInfo }) {
	const [userDetails, setUserDetails] = useState(userInfo);
	const [isEditing, setIsEditing] = useState(false); // Tracks editing state
	const [form, setForm] = useState({});
	const [dialogOpen, setDialogOpen] = useState();

	function fetchNewData() {
		axios.get("api/user").then((res) => {
			setUserDetails(res.data);
			sessionStorage.setItem("userInfo", JSON.stringify(res.data));
		});
	}

	function closeDialog() {
		setDialogOpen(false);
		setIsEditing(false);
	}

	function openDialog() {
		setDialogOpen(true);
	}

	const handleEditToggle = () => {
		setIsEditing(!isEditing); // Toggle editing state
	};

	const handleInputChange = (e) => {
		const { id, value } = e.target;
		setForm((prevForm) => ({
			...prevForm,
			[id]: value,
		}));
	};

	const handleSaveChanges = () => {
		const payload = {
			...userDetails, // Keep original data if not edited
			...form, // Include edited data
		};

		let url = "";
		if (userDetails.tip === "igrač") {
			url = "api/igrac/" + userInfo.id;
			if (
				payload.imeIgrac.trim() == "" ||
				payload.prezimeIgrac.trim() == "" ||
				payload.brojTel.trim() == ""
			) {
				alert("Neispravni podaci");
				return;
			}
		} else if (userDetails.tip === "vlasnik") {
			url = "api/vlasnik/" + userInfo.id;
			if (
				payload.nazivVlasnik.trim() == "" ||
				payload.lokacija.trim() == "" ||
				payload.brojTel.trim() == ""
			) {
				alert("Neispravni podaci");
				return;
			}
		}

		const options = {
			method: "PUT", // Use PUT for updating existing data
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(payload),
		};

		fetch(url, options)
			.then((res) => {
				if (res.ok) {
					alert("Podaci su uspješno spremljeni.");
					setIsEditing(false); // Toggle back to view mode
					setDialogOpen(false);
					fetchNewData();
				} else {
					return res.json().then((error) => {
						alert(`Error: ${error.message || "Failed to save changes"}`);
					});
				}
			})
			.catch((err) => {
				alert("Došlo je do greške. Pokušajte ponovo.");
			});
	};

	useEffect(() => {
		fetchNewData();
	}, []);

	useEffect(() => {
		if (userDetails) {
			// Pre-fill the form with existing data if available
			setForm({
				imeIgrac: userDetails.imeIgrac,
				prezimeIgrac: userDetails.prezimeIgrac,
				brojTel: userDetails.brojTel,
				nazivVlasnik: userDetails.nazivVlasnik,
				lokacija: userDetails.lokacija,
			});
		}
	}, [userDetails]);

	return (
		<>
			<Dialog open={dialogOpen}>
				<DialogTrigger asChild>
					<Button
						onClick={openDialog}
						className="bg-gray-800 text-gray-300 hover:bg-gray-700 outline-none hover:outline-none hover:text-white mx-2"
					>
						Osobni podaci
					</Button>
				</DialogTrigger>
				<DialogContent className="sm:max-w-[425px]">
					<DialogHeader>
						<DialogTitle>Osobni podaci</DialogTitle>
					</DialogHeader>

					<Card>
						{!isEditing ? (
							<>
								{userDetails.tip == "igrač" && (
									<>
										<CardHeader>
											<CardDescription>Igrač</CardDescription>
										</CardHeader>
										<CardContent>
											<br></br>
											<p>Ime: {userDetails.imeIgrac} </p>
											<br></br>
											<p>Prezime: {userDetails.prezimeIgrac} </p>
											<br></br>
											<p>Email: {userDetails.email} </p>
											<br />
											<p>Broj telefona: {userDetails.brojTel} </p>
										</CardContent>
									</>
								)}
								{userDetails.tip == "vlasnik" && (
									<>
										<CardHeader>
											<CardDescription>
												Vlasnik terena
											</CardDescription>
										</CardHeader>
										<CardContent>
											<br></br>
											<p>Naziv kluba: {userDetails.nazivVlasnik} </p>
											<br></br>
											<p>Email: {userDetails.email} </p>
											<br></br>
											<p>Broj telefona: {userDetails.brojTel} </p>
											<br></br>
											<p>Lokacija: {userDetails.lokacija} </p>
										</CardContent>
									</>
								)}
							</>
						) : (
							<>
								{userDetails.tip == "igrač" && (
									<>
										<CardHeader>
											<CardDescription>Igrač</CardDescription>
										</CardHeader>
										<CardContent>
											<p>Ime </p>
											<Input
												id="imeIgrac"
												defaultValue={userDetails.imeIgrac}
												onChange={handleInputChange}
											/>
											<br></br>
											<p>Prezime </p>
											<Input
												id="prezimeIgrac"
												defaultValue={userDetails.prezimeIgrac}
												onChange={handleInputChange}
											/>
											<br></br>
											<p>Broj telefona </p>
											<Input
												id="brojTel"
												defaultValue={userDetails.brojTel}
												onChange={handleInputChange}
											/>
										</CardContent>
									</>
								)}
								{userDetails.tip == "vlasnik" && (
									<>
										<CardHeader>
											<CardDescription>
												Vlasnik terena
											</CardDescription>
										</CardHeader>
										<CardContent>
											<p>Naziv kluba </p>
											<Input
												id="nazivVlasnik"
												defaultValue={userDetails.nazivVlasnik}
												onChange={handleInputChange}
											/>
											<br></br>
											<p>Broj telefona </p>
											<Input
												id="brojTel"
												defaultValue={userDetails.brojTel}
												onChange={handleInputChange}
											/>
											<br></br>
											<p>Lokacija </p>
											<Input
												id="lokacija"
												defaultValue={userDetails.lokacija}
												onChange={handleInputChange}
											/>
										</CardContent>
									</>
								)}
								<CardFooter className="flex justify-center	">
									<Button
										className="w-full "
										onClick={handleSaveChanges}
									>
										Spremi promjene
									</Button>
								</CardFooter>
							</>
						)}

						<CardFooter className="flex-row justify-between">
							<Button
								className="text-white w-1/3 m-2"
								onClick={handleEditToggle}
							>
								Uredi
							</Button>
							<Button
								className="text-black bg-transparent border-2 border-black w-1/3 m-2"
								onClick={closeDialog}
							>
								Zatvori
							</Button>
						</CardFooter>
					</Card>
				</DialogContent>
			</Dialog>
		</>
	);
}
