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

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@headlessui/react";
import axios from "axios";
import { useNavigate } from "react-router-dom"

export default function TournamentsOwner({ userInfo }) {
	const [tournaments, setTournaments] = useState([]);

	const [openRes, setOpenRes] = useState(false);
	const [openTurnirInp, setOpenTurnirInp] = useState(false);
	const [openConfirmation, setOpenConfirmation] = useState(false);
	const [deleteId, setDeleteId] = useState();
    const [openPlayerDialog, setOpenPlayerDialog] = useState(false);
    const [players, setPlayers] = useState([]);

	const [form, setForm] = useState({
		id: undefined,
        idVlasnik: userInfo.id,
		naziv: "",
		lokacija: "",
		datum: "",
        cijenaKotizacije:"",
        nagrade: [""],
        opis: ""
	});

	function fetchTournaments() {
		axios
			.get("/api/#") //endpoint for turniri/my
			.then((res) => {
				console.log(res);
				setTournaments(res.data);
			})
			.catch((error) => console.log(error));
	}
    function fetchPlayers(idTurnir) {
		axios
			.get("/api/#") //endpoint for getting all players that aplied for tournament with id = idTurnir
			.then((res) => {
				console.log(res);
				setPlayers(res.data);
			})
			.catch((error) => console.log(error));
	}

	useEffect(() => {
		fetchTournaments();
	}, [openRes]);

	const onChange = (event) => {
		const { name, value } = event.target;
		setForm((oldForm) => ({ ...oldForm, [name]: value }));
	};

	

	const handleNagradeChange = (index, value) => {
        setForm((prevForm) => {
          const updatedNagrade = [...prevForm.nagrade];
          updatedNagrade[index] = value;
      
          // Add a new empty field only if the last field is non-empty
          if (index === updatedNagrade.length - 1 && value.trim() !== "") {
            updatedNagrade.push("");
          }
      
          return { ...prevForm, nagrade: updatedNagrade };
        });
      };
      
      const handleRemoveInput = (index) => {
        setForm((prevForm) => {
          const updatedNagrade = prevForm.nagrade.filter((_, i) => i !== index);
          return { ...prevForm, nagrade: updatedNagrade };
        });
      };
	const onSubmit = () => {
		console.log("submit", form);
		if (form.naziv == "") {
			alert("Upišite naziv turnira.");
			return;
		}
		if (form.lokacija == "") {
			alert("Upišite lokaciju turnira.");
			return;
		}
        if (form.datum == "") {
			alert("Upišite datum turnira.");
			return;
		}
        if (form.cijenaKotizacije== "") {
			alert("Upišite cijenu kotizacije.");
			return;
		}
		if (form.nagrade == [""]) {
			alert("Upišite nagrade za turnir.");
			return;
		}
        if (form.opis == "") {
			alert("Upišite opis turnira.");
			return;
		}
		const data = new FormData();
        const formData ={
            idTurnir: form.id,
            idVlasnik: form.idVlasnik,
            naziv: form.naziv,
            lokacija: form.lokacija,
            datum: form.datum,
            cijenaKotizacije: form.cijenaKotizacije,
            nagrade: form.nagrade,
            opis: form.opis
        };
		data.append("formData",JSON.stringify(formData));
		// add new
		if (!form.id) {
			axios({
				url: "/api/#", //endpoint for adding new turnir
				method: "POST",
				headers: {
					"Content-Type": "application/json",
					//"Content-Type": "multipart/form-data",
				},
				data: data,
				
			})
				.then((res) => {
					setOpenRes(true);
					setOpenTurnirInp(false);
					setForm({
						//id: undefined,
                        idVlasnik: userInfo.id,
                        naziv: "",
                        lokacija: "",
                        datum: "",
                        cijenaKotizacije:"",
                        nagrade: [""],
                        opis: ""
					});
				})
				.catch((err) => {});
			return;
		}

		// update existing
		axios({
			url: "/api/#" + form.id, //endpoint for updating existing turnir
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
				//"Content-Type": "multipart/form-data",
			},
			data: data,
		})
			.then((res) => {
				console.log("put");
				setOpenRes(true);
				setOpenTurnirInp(false);
				setForm({
					id: undefined,
                    idVlasnik: userInfo.id,
                    naziv: "",
                    lokacija: "",
                    datum: "",
                    cijenaKotizacije:"",
                    nagrade: [""],
                    opis: ""
				});
			})
			.catch((err) => {});
	};

	function deleteTournament(e) {
		setOpenConfirmation(true);
		setDeleteId(e.target.id);
	}

	function deleteConfirmed() {
		console.log(deleteId);
		axios({
			url: "/api/#" + deleteId, //endpoint for deleting court
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((res) => {
				fetchTournaments();
				setOpenConfirmation(false);
			})
			.catch((err) => {});
	}

    function TournamentDetails(e){
        const idTurnir= e.target.id; // Get the tournament ID from the button
        const navigate = useNavigate();
        axios({
			url: "/api/#" + idTurnir, //endpoint for getting status of tournament with idTurnir
			method: "GET",
			
		})
			.then((res) => {
				const status = res.data.status;
                if(status === "open"){
                    fetchPlayers(idTurnir);
                    setOpenPlayerDialog(true);
                }else {
                    // Navigate to the TournamentDetails component
                    navigate(`/tournament/${idTurnir}/details`);
                }
			})
			.catch((err) => {});
    }
    function handlePlayerAction(playerId, action) {
        // Example: Send an API request to accept or reject the player
        axios
            .put(`/api/#`) // Replace with your API endpoint
            .then(() => {
                // Remove the player from the list after the action
                setPlayers((prevPlayers) =>
                    prevPlayers.filter((player) => player.id !== playerId)
                );
            })
            .catch((err) => console.error("Error handling player action:", err));
    }

	function deleteCanceled() {
		setDeleteId();
		setOpenConfirmation(false);
	}

	function editTournament(e) {
		setOpenTurnirInp(true);

		let editable = tournaments.filter((o) => o.idturnir == e.target.id)[0];

		setForm({
			id: editable.id,
            idVlasnik: userInfo.id,
            naziv: editable.naziv,
            lokacija: editable.lokacija,
            datum: "",//editable.datum,
            cijenaKotizacije: editable.cijenaKotizacije,
            nagrade: editable.nagrade,
            opis: editable.opis
		});
		console.log("now", form);
	}

	if (!userInfo) return <p>Loading...</p>;

	// checked in local storage
	return (
		<>
			<div className="top-0 m-auto mt-10 text-left">
				<div className="flex">
					<h1 className="text-left m-10">Moji turniri</h1>
				</div>

				<Dialog
					id="input"
					open={openTurnirInp}
					onOpenChange={setOpenTurnirInp}
				>
					<DialogTrigger asChild>
						<Button
							className="h-fit text-white ml-10"
						>
							Dodaj novi turnir
						</Button>
					</DialogTrigger>
					<DialogContent className="sm:max-w-[425px]">
						<DialogHeader>
							<DialogTitle>Dodaj novi turnir</DialogTitle>
							<DialogDescription>
								Upiši podatke o turniru kojeg želiš dodati.
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
								<div className="grid grid-cols-4 items-center gap-4">
                                    <Label htmlFor="datum" className="text-right">
										Datum
									</Label>
                                    <Input
                                        id="datum"
                                        name="datum"
                                        type="date"
                                        value={form.datum}
                                        onChange={onChange}
                                        className="col-span-3"
                                    />
                                    
                                </div>
                                <div className="grid grid-cols-4 items-center gap-4">
									<Label htmlFor="cijenaKotizacije" className="text-right">
										Cijena kotizacije
									</Label>
									<Input
                                        type="number"
										id="cijenaKotizacije"
										name="cijenaKotizacije"
										value={form.cijenaKotizacije}
										onChange={onChange}
										className="col-span-3"
									/>
								</div>
                                <div className="grid grid-cols-4 items-center gap-4">
									<Label htmlFor="opis" className="text-right">
										Opis
									</Label>
									<Input
                                        type="text"
										id="opis"
										name="opis"
										value={form.opis}
										onChange={onChange}
										className="col-span-3"
									/>
								</div>
                                <div className="items-center">
                                    <Label htmlFor="nagrade" className="text-right">
										Nagrade
									</Label>
                                    {form.nagrade.map((nagrada, index) => (
                                        <div key={index} className="grid grid-cols-4 items-center gap-4">
                                            <Input
                                                type="text"
                                                id="nagrade"
                                                name="nagrade"
                                                value={nagrada}
                                                onChange={(e) => handleNagradeChange(index, e.target.value)}
                                                placeholder={`Upiši nagradu za ${index + 1}. mjesto`}
                                                className="col-span-3 mt-5"
                                            />
                                            {index !== form.nagrade.length - 1 && (
                                                <button type="button" onClick={() => handleRemoveInput(index)} className="text-white mt-5">
                                                    Ukloni
                                                </button>
                                            )}
                                        </div>
                                    ))}
                                    
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

				{/* display tournaments */}
				<div className="flex h-fit flex-wrap">
					{tournaments.map((tournament) => (
						<Card key={tournament.idturnir} className="w-[350px] m-8">
							<CardHeader>
								<CardTitle>{tournament.nazivTurnir}</CardTitle>
								<CardDescription>{tournament.status}</CardDescription>
							</CardHeader>
							<CardContent>
								<p>Lokacija Turnira: {tournament.lokacijaTurnir}</p>
                                <p>Datum: {tournament.datum}</p>
                                <p>Cijena kotizacije: {tournament.cijenaKotizacije}</p>
								<p>Vlasnik: {tournament.vlasnikTeren.email}</p>
                                <p opacity-0 group-hover:opacity-100 bg-opacity-50 transition-opacity>Opis: {tournament.opis}</p>
							</CardContent>
							<CardFooter className="flex justify-between">
								<Button
									variant="outline"
									className="text-white"
									id={tournament.idturnir}
									onClick={(e) => editTournament(e)}
								>
									Uredi
								</Button>
								<Button
									variant="outline"
									className="text-white"
									id={tournament.idturnir}
									onClick={(e) => deleteTournament(e)}
								>
									Obriši
								</Button>
                                <Button
                                    variant="outline"
                                    className="text-white"
                                    id={tournament.idturnir}
                                    onClick={(e) => TournamentDetails(e)}
                                >
                                    Prikaži detalje
                                </Button>
							</CardFooter>
						</Card>
					))}
				</div>
                {/*dialog for displaying all players that applied for a tournament*/}
                <Dialog open={openPlayerDialog} onOpenChange={setOpenPlayerDialog}>
                    <DialogContent>
                        <DialogHeader>
                            <DialogTitle>Igrači turnira</DialogTitle>
                            <DialogDescription>
                                Lista igrača prijavljenih za turnir
                            </DialogDescription>
                        </DialogHeader>
                        <div className="space-y-4">
                            {players.length > 0 ? (
                                players.map((player) => (
                                    <div
                                        key={player.id}
                                        className="flex justify-between items-center border p-2 rounded"
                                    >
                                        <p>{player.email}</p>
                                        <div className="space-x-2">
                                            <Button
                                                className="bg-green-500 text-white px-4 py-2 rounded"
                                                onClick={() =>
                                                    handlePlayerAction(player.id, "accept")
                                                }
                                            >
                                                Prihvati
                                            </Button>
                                            <Button
                                                className="bg-red-500 text-white px-4 py-2 rounded"
                                                onClick={() =>
                                                    handlePlayerAction(player.id, "reject")
                                                }
                                            >
                                                Odbij
                                            </Button>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p>Nema prijavljenih igrača.</p>
                            )}
                        </div>
                        <DialogFooter>
                            <Button
                                className="bg-gray-500 text-white px-4 py-2 rounded"
                                onClick={() => setOpenPlayerDialog(false)}
                            >
                                Zatvori
                            </Button>
                        </DialogFooter>
                    </DialogContent>
                </Dialog>
			</div>
		</>
	);
}
