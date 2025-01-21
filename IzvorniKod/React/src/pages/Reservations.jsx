import axios from "axios";
import { useState, useEffect } from "react";
import NavBar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";
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
import { Button } from "@headlessui/react";
import moment from "moment";

export default function Reservations() {
	const [reservations, setReservations] = useState([]);
	const [openConfirmation, setOpenConfirmation] = useState(false);
	const [deleteId, setDeleteId] = useState();

	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});
	const navigate = useNavigate();

	if (!userInfo) {
		navigate("/", { replace: true });
		return;
	}

	function fetchRes() {
		axios
			.get("/api/rezervacije")
			.then((res) => {
				console.log(res.data.filter((e) => e.korisnik.id == userInfo.id));
				setReservations(
					res.data.filter((e) => e.korisnik.id == userInfo.id)
				);
			})
			.then();
	}

	useState(() => {
		fetchRes();
	}, []);

	function deleteRes(id, vrijeme) {
		setOpenConfirmation(true);
		console.log(
			id,
			moment(vrijeme).subtract(1, "months").format("YYYY-MM-DDThh:mm:ss")
		);
		setDeleteId({
			id: id,
			time: moment(vrijeme)
				.subtract(1, "months")
				.format("YYYY-MM-DDTHH:mm:ss"),
		}); //  2024-12-25T12:00:00
	}
	function deleteConfirmed() {
		axios
			.delete(
				"/api/rezervacije?terenId=" +
					deleteId.id +
					"&startTime=" +
					deleteId.time
			)
			.then(() => {
				fetchRes();
				setOpenConfirmation(false);
			});
	}

	function deleteCanceled() {
		setOpenConfirmation(false);
	}

	return (
		<>
			<NavBar></NavBar>

			<Dialog open={openConfirmation}>
				<DialogContent className="sm:max-w-[425px]">
					<DialogHeader>
						<DialogTitle>
							Jeste li sigurni da želite obrisati rezervaciju?
						</DialogTitle>
					</DialogHeader>
					<DialogFooter>
						<Button className="text-white" onClick={deleteConfirmed}>
							Da
						</Button>
						<Button className="text-white" onClick={deleteCanceled}>
							Ne
						</Button>
					</DialogFooter>
				</DialogContent>
			</Dialog>

			<div className="">
				<h1 className="text-left mt-20">Moje rezervacije</h1>
				<div className="flex h-fit flex-wrap">
					{reservations.map((r) => (
						<Card className="w-[350px] m-8 text-left text-md">
							<CardHeader>
								<CardTitle>{r.zaTeren.nazivTeren}</CardTitle>
								<CardDescription>{r.zaTeren.tipTeren}</CardDescription>
							</CardHeader>
							<CardContent>
								<p>
									Datum:{" "}
									{moment(r.vrijeme)
										.subtract(1, "month")
										.format("DD.MM.YYYY.")
										.toString()}
								</p>
								<p>
									Trajanje: {/* TODO  military time */}
									<b>
										{moment(r.vrijeme)
											.subtract(1, "month")
											.format("HH:mm")
											.toString()}
									</b>
									{" - "}
									<b>
										{moment(r.vrijeme)
											.add(1, "hours")
											.format("HH:mm")}
									</b>
								</p>

								<p>Vlasnik terena: {r.zaTeren.vlasnikTeren.email}</p>
							</CardContent>
							<CardFooter>
								<Button
									className="text-white"
									onClick={() =>
										deleteRes(r.zaTeren.idteren, r.vrijeme)
									}
								>
									Otkaži
								</Button>
							</CardFooter>
						</Card>
					))}
				</div>
			</div>
		</>
	);
}
