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

export default function UserData({ userInfo }) {
	const [userDetails, setUserDetails] = useState(userInfo);
	const [isEditing, setIsEditing] = useState(false); // Tracks editing state
	const [form, setForm] = useState({});
	const [dialogOpen, setDialogOpen] = useState();

	function fetchNewData() {
		axios.get("api/user").then((res) => {
			setUserDetails(res.data);
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
		} else if (userDetails.tip === "vlasnik") {
			url = "api/korisnik/vlasnik";
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
					console.log("Changes saved successfully.");
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
				console.error("Error during saving changes:", err);
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
											<p>Email: {userDetails.email} </p>
											<p>Broj telefona: {userDetails.brojTel} </p>
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
											<p>Naziv </p>
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
											<p>Lokacijia </p>
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
// 	return (
// 		<>
// 			{/* <nav style={styles.navbar}>
// 				<ul style={styles.navList}>
// 					<li style={styles.navItem}> */}
// 			{userDetails.tip === "igrač" && (
// 				<Drawer>
// 					<DrawerTrigger className="text-black bg-transparent text-sm">
// 						Osobni podaci
// 					</DrawerTrigger>
// 					<DrawerContent>
// 						<DrawerHeader>
// 							<DrawerTitle>Osobni podaci</DrawerTitle>
// 						</DrawerHeader>
// 						<DrawerFooter>
// 							{!isEditing ? (
// 								<Card>
// 									<CardHeader>
// 										<CardDescription>Igrač</CardDescription>
// 									</CardHeader>
// 									<CardContent>
// 										<br></br>
// 										<p>Ime: {userDetails.imeIgrac} </p>
// 										<br></br>
// 										<p>Prezime: {userDetails.prezimeIgrac} </p>
// 										<br></br>
// 										<p>Email: {userDetails.email} </p>
// 									</CardContent>
// 									<CardFooter>
// 										<p>Broj telefona: {userDetails.brojTel} </p>
// 									</CardFooter>
// 								</Card>
// 							) : (
// 								<Card>
// 									<CardHeader>
// 										<CardDescription>Igrač</CardDescription>
// 									</CardHeader>
// 									<CardContent>
// 										<p>Ime </p>
// 										<Input
// 											id="imeIgrac"
// 											defaultValue={userDetails.imeIgrac}
// 											onChange={handleInputChange}
// 										/>
// 										<br></br>
// 										<p>Prezime </p>
// 										<Input
// 											id="prezimeIgrac"
// 											defaultValue={userDetails.prezimeIgrac}
// 											onChange={handleInputChange}
// 										/>
// 										<br></br>
// 										<p>Broj telefona </p>
// 										<Input
// 											id="brojTel"
// 											defaultValue={userDetails.brojTel}
// 											onChange={handleInputChange}
// 										/>
// 									</CardContent>
// 									<CardFooter>
// 										<Button
// 											style={{ fontSize: "1.1rem" }}
// 											onClick={handleSaveChanges}
// 										>
// 											Spremi promjene
// 										</Button>
// 									</CardFooter>
// 								</Card>
// 							)}
// 							<br />
// 							<br />
// 							<Button
// 								style={{ fontSize: "1.1rem" }}
// 								onClick={handleEditToggle}
// 							>
// 								Uredi
// 							</Button>
// 							<DrawerClose className="text-white">Zatvori</DrawerClose>
// 						</DrawerFooter>
// 					</DrawerContent>
// 				</Drawer>
// 			)}

// 			{userDetails.tip === "vlasnik" && (
// 				<Drawer>
// 					<DrawerTrigger className="text-black bg-transparent text-sm">
// 						Osobni podaci
// 					</DrawerTrigger>
// 					<DrawerContent>
// 						<DrawerHeader>
// 							<DrawerTitle>Osobni podaci</DrawerTitle>
// 						</DrawerHeader>
// 						<DrawerFooter>
// 							{!isEditing ? (
// 								<Card>
// 									<CardHeader>
// 										<CardTitle>{userDetails.nazivVlasnik}</CardTitle>
// 										<CardDescription>Vlasnik</CardDescription>
// 									</CardHeader>
// 									<CardContent>
// 										<p>Email: {userDetails.email}</p>
// 									</CardContent>
// 									<CardFooter>
// 										<p>Broj telefona: {userDetails.brojTel}</p>
// 										<br></br>

// 										<p>Lokacija: {userDetails.lokacija}</p>
// 									</CardFooter>
// 								</Card>
// 							) : (
// 								<Card>
// 									<CardHeader>
// 										<CardDescription>Igrač</CardDescription>
// 									</CardHeader>
// 									<CardContent>
// 										<p>Naziv </p>
// 										<Input
// 											id="nazivVlasnik"
// 											defaultValue={userDetails.nazivVlasnik}
// 											onChange={handleInputChange}
// 										/>
// 										<br></br>
// 										<p>Broj telefona </p>
// 										<Input
// 											id="brojTel"
// 											defaultValue={userDetails.brojTel}
// 											onChange={handleInputChange}
// 										/>
// 										<br></br>
// 										<p>Lokacijia </p>
// 										<Input
// 											id="lokacija"
// 											defaultValue={userDetails.lokacija}
// 											onChange={handleInputChange}
// 										/>
// 									</CardContent>
// 									<CardFooter>
// 										<Button
// 											style={{ fontSize: "1.1rem" }}
// 											onClick={handleSaveChanges}
// 										>
// 											Spremi promjene
// 										</Button>
// 									</CardFooter>
// 								</Card>
// 							)}
// 							<br />
// 							<br />
// 							<Button
// 								style={{ fontSize: "1.1rem" }}
// 								onClick={handleEditToggle}
// 							>
// 								Uredi
// 							</Button>
// 							<DrawerClose className="text-white">Zatvori</DrawerClose>
// 						</DrawerFooter>
// 					</DrawerContent>
// 				</Drawer>
// 			)}
// 			{/* </li>
// 					<li style={styles.navItem}></li>
// 					<li style={styles.navItem}></li>
// 					<li style={styles.navItem}></li>
// 				</ul>
// 			</nav> */}
// 		</>
// 	);
// }

// const styles = {
// 	navbar: {
// 		backgroundColor: "#333",
// 		padding: "10px 0",
// 		position: "fixed", // Fixed at the top
// 		top: "0",
// 		left: "0",
// 		width: "100%",
// 		zIndex: "1000", // Ensures it stays on top
// 	},
// 	navList: {
// 		display: "flex",
// 		justifyContent: "flex-start", // Aligns items to the left
// 		listStyleType: "none",
// 		margin: 0,
// 		padding: 0,
// 	},
// 	navItem: {
// 		margin: "0 20px",
// 	},
// 	link: {
// 		color: "white",
// 		textDecoration: "none",
// 		fontSize: "18px",
// 		transition: "color 0.3s",
// 	},
// };
