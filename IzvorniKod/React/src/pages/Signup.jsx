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
import { useState } from "react";

export default function Signup() {
	const [selectedRole, setSelectedRole] = useState("");
	const [form, setForm] = useState({ email: "", role: "" });
	const navigate = useNavigate();

	const emailRegex = /^[\w\-\.]+@(gmail+\.)+[\w-]{2,}$/gm;
	// from https://regex101.com/r/lHs2R3/1

	const onChange = (event) => {
		const { name, value } = event.target;
		setForm((oldForm) => ({ ...oldForm, [name]: value }));
	};

	const handleSelectChange = (value) => {
		setForm((oldForm) => ({ ...oldForm, role: value }));
		console.log("-- ignored Selected Role: ", value);
	};

	const onSubmit = () => {
		if (!emailRegex.test(form.email)) {
			// TODO change error display
			alert("Unesite ispravnu gmail adresu.");
			return;
		}
		if (form.role == "") {
			alert("Odaberite vrstu računa.");
			return;
		}

		const data = JSON.stringify(form);
		console.log(data);

		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: data,
		};
		return fetch("/api/register", options).then((res) => {
			console.log(res);
			// TODO if fetch returns error display error msg
			navigate("/Login");
		});
	};

	return (
		<Card className="w-[350px] m-auto pt-4">
			<CardHeader>
				<CardTitle>Registriraj se</CardTitle>
				<CardDescription>Napravi svoj korisnički račun</CardDescription>
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
