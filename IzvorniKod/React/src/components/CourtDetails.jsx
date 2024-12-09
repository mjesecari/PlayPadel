import NavBar from "@/components/Navbar";
import { Card, CardHeader, CardTitle } from "./ui/card";
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
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

export default function CourtPreview() {
	const court = {
		name: " naziv terena ",
		type: "vanjski ",
		location: "adress",
		contactinfo: "email vlasnika terena",
		price: "5kn",
	};
	return (
		<>
			<Dialog>
				<DialogTrigger asChild>
					<Button variant="outline">Edit Profile</Button>
				</DialogTrigger>
				<DialogContent className="sm:max-w-[425px]">
					<DialogHeader>
						<DialogTitle>Rezerviraj teren</DialogTitle>
						<DialogDescription>
							Odaberi vrijeme rezervacije i način plaćanja
						</DialogDescription>
					</DialogHeader>
					<div className="grid gap-4 py-4">
						<div className="grid grid-cols-4 items-center gap-4">
							<Label htmlFor="name" className="text-right">
								datum
								{/*  treba bit drukciji input  */}
							</Label>
							<Input
								id="name"
								value="Pedro Duarte"
								className="col-span-3"
							/>
						</div>
						<div className="grid grid-cols-4 items-center gap-4">
							<Label htmlFor="username" className="text-right">
								Vrijeme
								{/*  treba bit drukciji input  */}
							</Label>
							<Input
								id="username"
								value="@peduarte"
								className="col-span-3"
							/>
						</div>
						<div className="grid grid-cols-4 items-center gap-4">
							<Label htmlFor="username" className="text-right">
								Cijena
								{/*  treba bit drukciji input  */}
							</Label>
							<Label className="text-left">{court.price} </Label>
						</div>
					</div>
					<DialogFooter>
						<Button type="submit">Save changes</Button>
					</DialogFooter>
				</DialogContent>
			</Dialog>

			{/* should be popup across whole screen */}
			{/*          
			<Card className="w-[350px]">
				<CardHeader>
					<CardTitle>{court.name}</CardTitle>
					<CardDescription>{court.type}</CardDescription>
					<CardDescription>{court.location}</CardDescription>
				</CardHeader>
				<CardContent>
					<CardTitle>Rezerviraj:</CardTitle>
					<form>
						<div className="grid w-full items-center gap-4">
							<div className="flex flex-col space-y-1.5">
								<Label htmlFor="name">Name</Label>
								<Input id="name" placeholder="Name of your project" />
							</div>
							<div className="flex flex-col space-y-1.5">
								<Label htmlFor="framework">Framework</Label>
								<Select>
									<SelectTrigger id="framework">
										<SelectValue placeholder="Select" />
									</SelectTrigger>
									<SelectContent position="popper">
										<SelectItem value="next">Next.js</SelectItem>
										<SelectItem value="sveltekit">
											SvelteKit
										</SelectItem>
										<SelectItem value="astro">Astro</SelectItem>
										<SelectItem value="nuxt">Nuxt.js</SelectItem>
									</SelectContent>
								</Select>
							</div>
						</div>
					</form>
				</CardContent>
				<CardFooter className="flex justify-between">
					<Button variant="outline">Natrag</Button>
					<Button>Potvrdi rezervaciju</Button>
				</CardFooter>
			</Card> */}
		</>
	);
}
