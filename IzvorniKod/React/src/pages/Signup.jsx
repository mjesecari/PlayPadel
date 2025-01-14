import * as React from "react";
import { Link, useNavigate, redirect } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
	Card,
	CardContent,
	CardDescription,
	CardFooter,
	CardHeader,
	CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from "@/components/ui/select";
import { useState, useEffect } from "react";

export default function Signup() {
	const [errorMsg, setErrorMsg] = useState(false);
	const [selectedRole, setSelectedRole] = useState("");
	const [form, setForm] = useState({
		email: "",
		role: "",
		ime: "",
		prezime: "",
		brojTel: "",
		nazivVlasnik: "",
		lokacija: "",
	});

	useEffect(() => {
		setErrorMsg(false);
	}, []);

	const navigate = useNavigate();

	const emailRegex = /^[\w\-\.]+@(gmail+\.)+[\w-]{2,}$/gm;
	// from https://regex101.com/r/lHs2R3/1

	const onChange = (event) => {
		const { name, value } = event.target;
		setForm((oldForm) => ({ ...oldForm, [name]: value }));
	};

	const handleSelectChange = (value) => {
		setForm((oldForm) => ({ ...oldForm, role: value }));
		console.log("-- Selected Role:", value);
	};

	const onSubmit = () => {
		console.log(form);
		if (!emailRegex.test(form.email)) {
			// TODO change error display
			alert("Unesite ispravnu gmail adresu.");
			return;
		}
		if (form.role == "") {
			alert("Odaberite vrstu računa.");
			return;
		}

		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
		};

		// REGISTER PLAYER
		if (form.role == "igrač") {
			if (form.ime == "" || form.prezime == "" || form.brojTel == "") {
				alert("Nedostaju podaci");
				return;
			}

			let data = JSON.stringify({
				email: form.email,
				role: form.role,
				imeIgrac: form.ime,
				prezimeIgrac: form.prezime,
				brojTel: form.brojTel,
			});
			options.body = data;
			console.log(data);

			fetch("api/register/igrac", options)
				.then((res) => {
					console.log(res);
					navigate("/Login");
					//return;
				})
				.catch((err) => {
					setErrorMsg(true);
				});
		}

		// REGISTER COURT OWNER
		else if (form.role == "vlasnik") {
			if (
				form.nazivVlasnik == "" ||
				form.lokacija == "" ||
				form.brojTel == ""
			) {
				alert("Nedostaju podaci");
				return;
			}
			let data = JSON.stringify({
				email: form.email,
				role: form.role,
				nazivVlasnik: form.nazivVlasnik,
				lokacija: form.lokacija,
				brojTel: form.brojTel,
			});
			console.log(data);
			options.body = data;

			fetch("api/register/vlasnik", options)
				.then((res) => {
					console.log(res);
					navigate("/Login");
				})
				.catch((err) => {
					setErrorMsg(true);
				});
		}
	};

	return (
		<Card className="w-[350px] m-auto pt-4">
			<CardHeader>
				<CardTitle>Registriraj se</CardTitle>
				<CardDescription>Napravi svoj korisnički račun</CardDescription>
				{errorMsg && (
					<CardDescription className="text-red-500 text-md ">
						Došlo je do pogreške, postoji profil s tom gmail adresom
					</CardDescription>
				)}
			</CardHeader>

			{/* consider changing to =onSubmit() */}
			<form onSubmit={(e) => e.preventDefault()}>
				<CardContent>
					<div className="grid w-full items-center gap-4">
						<div className="flex flex-col space-y-1.5">
							<Label htmlFor="name">Gmail</Label>
							<Input
								id="name"
								placeholder="Gmail"
								name="email"
								onChange={onChange}
								value={form.email}
							/>
						</div>

						<Select onValueChange={handleSelectChange}>
							<SelectTrigger id="framework">
								<SelectValue placeholder="Izaberi vrstu računa" />
							</SelectTrigger>
							<SelectContent position="popper">
								<SelectItem value="igrač">Igrač</SelectItem>
								<SelectItem value="vlasnik">Vlasnik terena</SelectItem>
							</SelectContent>
						</Select>
					</div>

					{/* samo za igrača: */}

					{form.role == "igrač" && (
						<div name="container">
							<hr className="m-8" />
							<CardDescription>
								Molimo upišite dodatne podatke
							</CardDescription>
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Ime</Label>
								<Input
									id="ime"
									placeholder="Ime"
									name="ime"
									onChange={onChange}
									value={form.ime}
								/>
							</div>
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Prezime</Label>
								<Input
									id="prezime"
									placeholder="Prezime"
									name="prezime"
									onChange={onChange}
									value={form.prezime}
								/>
							</div>
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Broj mobitela</Label>
								<Input
									id="brojTel"
									placeholder="Broj mobitela"
									name="brojTel"
									onChange={onChange}
									value={form.brojTel}
								/>
							</div>
						</div>
					)}

					{/* samo za vlasnika terena: */}

					{form.role == "vlasnik" && (
						<div name="container">
							<hr className="m-8" />
							<CardDescription>
								Molimo upišite dodatne podatke
							</CardDescription>{" "}
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Ime kluba</Label>
								<Input
									id="nazivVlasnik"
									placeholder="Upisite ime kluba"
									name="nazivVlasnik"
									onChange={onChange}
									value={form.nazivVlasnik}
								/>
							</div>
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Lokacija</Label>
								<Input
									id="lokacija"
									placeholder="Lokacija vaših terena"
									name="lokacija"
									onChange={onChange}
									value={form.lokacija}
								/>
							</div>
							<div className="flex flex-col space-y-1.5 mt-4">
								<Label>Broj mobitela/telefona</Label>
								<Input
									id="brojTel"
									placeholder="Broj mobitela"
									name="brojTel"
									onChange={onChange}
									value={form.brojTel}
								/>
							</div>
						</div>
					)}
				</CardContent>
				<CardFooter className="flex justify-between">
					<Link to="/Home">
						<Button variant="outline">Natrag</Button>
					</Link>
					{/* consider changing to type = "submit" */}
					<Button type="button" onClick={() => onSubmit()}>
						Registriraj se
					</Button>
				</CardFooter>
			</form>
		</Card>
	);
}
