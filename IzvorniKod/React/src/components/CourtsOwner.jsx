import { useEffect, useState } from "react";
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

export default function CourtsOwner({ userInfo }) {
	const [courts, setCourts] = useState([]);

	const [openRes, setOpenRes] = useState(false);
	const [openTerenInp, setOpenTerenInp] = useState(false);
	const [openConfirmation, setOpenConfirmation] = useState(false);
	const [deleteId, setDeleteId] = useState();

	const [form, setForm] = useState({
		id: undefined,
		naziv: "",
		tip: "",
		vlasnikTerenaId: userInfo.id,
	});

	function fetchCourts() {
		axios
			.get("/api/tereni/my")
			.then((res) => {
				console.log(res);
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

	const handleFileChange = (event) => {
		setForm((oldForm) => ({ ...oldForm, slika: event.target.files[0] }));
	};

	const onSubmit = () => {
		console.log("submit", form);
		if (form.tip == "") {
			alert("Odaberite vrstu terena.");
			return;
		}
		if (form.naziv == "") {
			alert("Upišite naziv terena.");
			return;
		}
		if (form.lokacija == "") {
			alert("Upišite lokaciju terena.");
			return;
		}
		if (form.slika == null){
			alert("Dodajte sliku terena.");
			return;
		}
		//const data = JSON.stringify(form);
		const data = new FormData();
		data.append("naziv", form.naziv);
		data.append("tip", form.tip);
		data.append("lokacija", form.lokacija);
		data.append("vlasnikTerenaId", form.vlasnikTerenaId);
		const terenDTO = {
			naziv: form.naziv,
			tip: form.tip,
			lokacija: form.lokacija,
			vlasnikTerenaId: form.vlasnikTerenaId,
		};
		data.append("teren",new Blob([JSON.stringify(form)], { type: 'application/json' }));
		if (form.slika) {
		data.append("slika", form.slika); // Append file to FormData if it exists
		}
		// add new
		if (!form.id) {
			axios({
				url: "/api/tereni/",
				method: "POST",
				headers: {
					//"Content-Type": "application/json",
					"Content-Type": "multipart/form-data",
				},
				data: data,
			})
				.then((res) => {
					setOpenRes(true);
					setOpenTerenInp(false);
					setForm({
						id: undefined,
						naziv: "",
						tip: "",
						vlasnikTerenaId: userInfo.id,
						lokacija: "",
						slika: null
					});
				})
				.catch((err) => {});
			return;
		}

		// update existing
		axios({
			url: "/api/tereni/" + form.id,
			method: "PUT",
			headers: {
				//"Content-Type": "application/json",
				"Content-Type": "multipart/form-data",
			},
			data: data,
		})
			.then((res) => {
				console.log("put");
				setOpenRes(true);
				setOpenTerenInp(false);
				setForm({
					id: undefined,
					naziv: "",
					tip: "",
					vlasnikTerenaId: userInfo.id,
					lokacija: "",
					slika: null
				});
			})
			.catch((err) => {});
	};

	function deleteCourt(e) {
		setOpenConfirmation(true);
		setDeleteId(e.target.id);
	}

	function deleteConfirmed() {
		console.log(deleteId);
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

	function editCourt(e) {
		setOpenTerenInp(true);

		let editable = courts.filter((o) => o.idteren == e.target.id)[0];

		setForm({
			id: editable.idteren,
			naziv: editable.nazivTeren,
			tip: "", // editable.tipTeren,
			vlasnikTerenaId: userInfo.id,
			lokacija: editable.lokacijaTeren,
			slika: editable.slikaTeren
		});
		console.log("now", form);
	}

	if (!userInfo) return <p>Loading...</p>;

	// checked in local storage
	return (
		<>
			<div className="top-0 m-auto mt-10 text-left">
				<div className="flex">
					<h1 className="text-left m-10">Moji tereni</h1>
				</div>

				<Dialog
					id="input"
					open={openTerenInp}
					onOpenChange={setOpenTerenInp}
				>
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

								<div className="grid grid-cols-4 items-center gap-4">
									<Label htmlFor="lokacija" className="text-right">
										Lokacija
									</Label>
									<Input
										id="lokacija"
										name="lokacija"
										value={form.lokacija}
										onChange={onChange}
										className="col-span-3"
									/>
								</div>
								{/* File input */}
								<div className="grid grid-cols-4 items-center gap-4">
									<Label htmlFor="slika" className="text-right">
										Dodaj sliku
									</Label>
									<Input
										type="file"
										id="slika"
										name="slika"
										onChange={handleFileChange}
										className="col-span-3"
									/>
								</div>
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

				{/* display courts */}
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
									onClick={(e) => editCourt(e)}
								>
									Uredi
								</Button>
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
