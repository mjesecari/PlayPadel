import { useEffect, useState } from "react";
import NavBar from "@/components/Navbar";
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
import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from "@/components/ui/select";

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@headlessui/react";
import axios from "axios";

export default function CourtsOwner() {
	const [courts, setCourts] = useState([]);

	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});

	const [openRes, setOpenRes] = useState(false);
	const [openTerenInp, setOpenTerenInp] = useState(false);
	const [openConfirmation, setOpenConfirmation] = useState(false);
	const [deleteId, setDeleteId] = useState();

	const [form, setForm] = useState({
		naziv: "",
		tip: "",
		vlasnikTerenaId: userInfo.id,
	});

	function fetchCourts() {
		axios
			.get("/api/tereni/my")
			.then((res) => {
				setCourts(res.data);
			})
			.catch((error) => console.log(error));
	}

	useEffect(() => {
		fetchCourts();
	}, [openRes]);

	const onChange = (event) => {
		const { name, value } = event.target;
		setForm((oldForm) => ({ ...oldForm, [name]: value }));
	};

	const handleSelectChange = (value) => {
		setForm((oldForm) => ({ ...oldForm, tip: value }));
	};

	const onSubmit = () => {
		if (form.tip == "") {
			alert("Odaberite vrstu terena.");
			return;
		}
		if (form.naziv == "") {
			alert("Odaberite vrstu terena.");
			return;
		}
		const data = JSON.stringify(form);

		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: data,
		};

		axios({
			url: "/api/tereni/",
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			data: data,
		})
			.then((res) => {
				setOpenRes(true);
				setOpenTerenInp(false);
			})
			.catch((err) => {});
	};

	function deleteCourt(e) {
		setOpenConfirmation(true);
		setDeleteId(e.target.id);
	}

	function deleteConfirmed() {
		axios({
			url: "/api/tereni/" + deleteId,
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((res) => {
				fetchCourts();
				setOpenConfirmation(false);
			})
			.catch((err) => {});
	}

	function deleteCanceled() {
		setDeleteId();
		setOpenConfirmation(false);
	}
	if (!userInfo) return <p>Loading...</p>;

	// checked in local storage
	return (
		<>
			<NavBar></NavBar>
			<div className="top-0 m-auto mt-10 text-left">
				<div className="flex">
					<h1 className="text-left m-10">Moji tereni</h1>
				</div>

				<Dialog open={openTerenInp} onOpenChange={setOpenTerenInp}>
					<DialogTrigger asChild>
						<Button className="h-fit text-white ml-10">
							Dodaj novi teren
						</Button>
					</DialogTrigger>
					<DialogContent className="sm:max-w-[425px]">
						<DialogHeader>
							<DialogTitle>Dodaj novi teren</DialogTitle>
							<DialogDescription>
								Upiši podatke o terenu kojeg želiš dodati.
							</DialogDescription>
						</DialogHeader>
						<form onSubmit={(e) => e.preventDefault()}>
							<div className="grid gap-4 py-4">
								<div className="grid grid-cols-4 items-center gap-4">
									<Label htmlFor="name" className="text-right">
										Naziv
									</Label>
									<Input
										id="naziv"
										name="naziv"
										value={form.naziv}
										onChange={onChange}
										className="col-span-3"
									/>
								</div>

								<Select onValueChange={handleSelectChange}>
									<SelectTrigger id="framework">
										<SelectValue placeholder="Tip terena" />
									</SelectTrigger>
									<SelectContent position="popper">
										<SelectItem value="vanjski">vanjski</SelectItem>
										<SelectItem value="unutarnji">
											unutarnji
										</SelectItem>
									</SelectContent>
								</Select>
							</div>
						</form>
						<DialogFooter>
							<Button
								type="submit"
								onClick={() => onSubmit()}
								className="h-fit text-white ml-10"
							>
								Dodaj
							</Button>
						</DialogFooter>
					</DialogContent>
				</Dialog>

				{/* response after adding new  */}
				<Dialog open={openRes} onOpenChange={setOpenRes}>
					<DialogContent className="sm:max-w-[425px]">
						<DialogHeader>
							<DialogTitle>Dodano!</DialogTitle>
						</DialogHeader>
					</DialogContent>
				</Dialog>

				<Dialog open={openConfirmation}>
					<DialogContent className="sm:max-w-[425px]">
						<DialogHeader>
							<DialogTitle>Jeste li sigurni?</DialogTitle>
						</DialogHeader>
						<DialogFooter>
							<Button className="text-white" onClick={deleteConfirmed}>
								Da
							</Button>
							<Button className="text-white" onClick={deleteCanceled}>
								{" "}
								Ne
							</Button>
						</DialogFooter>
					</DialogContent>
				</Dialog>

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
									onClick={(e) => deleteCourt(e)}
								>
									Obriši
								</Button>
							</CardFooter>
						</Card>
					))}
				</div>
			</div>
		</>
	);
}
