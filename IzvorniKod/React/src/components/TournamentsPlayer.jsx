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
import Slider from "@mui/material/Slider";

export default function TournamentsPlayer({ userInfo }) {
    const [Playedtournaments, setPlayedTournaments] = useState([]);
    const [Registertournaments, setRegisterTournaments] = useState([]);
    const [Newtournaments, setNewTournaments] = useState([]);
    const [status, setStatus] = useState("prihvacen");
    const [filter, setFilter] = useState({
		cijenaKotizacije: [0, 100], // Range for Kotizacije
		iznosNagrade: [0, 1000], // Range for Nagrade
	});
    useEffect(() => {
        //fetchPlayedTournaments();
        //fetchRegisterTournaments();
        fetchNewTournaments();
    }, [userInfo.id]); //?[id]
    function fetchPlayedTournaments(){
        axios
        .get(`/api/prijavaTurnir/AllPlayed/${userInfo.id}`) // endpoint /api/tournaments/my/userInfo.id with status closed needs to return list of tournaments
        .then((res) => {
            setPlayedTournaments(res.data);
        })
        .catch((err) => console.error("Error fetching tournament details:", err));
    }
    function fetchRegisterTournaments(newStatus){
        console.log(newStatus);
        console.log(userInfo.id);
        axios
        .get(`/api/prijavaTurnir/AllWithStatus/${userInfo.id}/${newStatus}`) // endpoint /api/tournaments/my/userInfo.id/status with status opened list of tournaments and status 
        .then((res) => {
            setRegisterTournaments(res.data);
            console.log(res);
            
        })
        .catch((err) => console.error("Error fetching tournament details:", err));
    }
    function fetchNewTournaments(){
        axios
        .get(`/api/prijavaTurnir/AllForApplying/${userInfo.id}`/**,{
            params: {
                cijenaKotizacijeMin: filter.cijenaKotizacije[0],
                cijenaKotizacijeMax: filter.cijenaKotizacije[1],
                iznosNagradeMin: filter.iznosNagrade[0],
                iznosNagradeMax: filter.iznosNagrade[1],
            },
    }**/) // endpoint /api/tournaments/filter
        .then((res) => {
            setNewTournaments(res.data);
            console.log(res.data);
        })
        .catch((err) => console.error("Error fetching tournament details:", err));
    }
    function TournamentDetails(e){
        const idTurnir= e.target.id; // Get the tournament ID from the button
        const navigate = useNavigate();
        axios({
			url: "/api/#" + idTurnir, //endpoint for getting status of tournament with idTurnir
			method: "GET",
			
		})
			.then((res) => {
                    // Navigate to the TournamentDetails component
                    navigate(`/tournament/${idTurnir}/details`);
			})
			.catch((err) => {});
    }
    const handleSliderChange = (name) => (event, newValue) => {
		setFilter((prevFilter) => ({
			...prevFilter,
			[name]: newValue,
		}));
	};

	// Handle status filter change
	function handleStatusChange(e) {
        const newStatus = e.target.value
		setStatus(e.target.value);
        console.log(e.target.value);
		fetchRegisterTournaments(newStatus);
	}

    function HandlePrijava(e){
        axios
        axios({
            url: "/api/prijavaTurnir/", //endpoint for adding new turnir
            method: "POST",
            data:{
                 IDTurnir:e.target.id,
                 IDKorisnik: userInfo.id
            },
            headers: {
                "Content-Type": "application/json", // Ensure the content type is JSON
            },
            })
            .then((res) => {
                console.log(res);
            })
            .catch((err) => {});
    }
    return(
        <>
            <div className="p-8">
				<div className="p-8">
					<h1 className="text-left m-10">Turniri</h1>
                    {/* Filter Section */}
                    <div className="bg-gray-100 p-3 rounded-md shadow-md mb-10">
                        <h2 className="text-xl font-semibold mb-4">Filtriraj Turnire</h2>

                        <div className="mb-6">
                            <label className="block text-sm font-medium mb-2">
                                Cijena Kotizacije (€): {filter.cijenaKotizacije[0]} -{" "}
                                {filter.cijenaKotizacije[1]}
                            </label>
                            <Slider
                                value={filter.cijenaKotizacije}
                                onChange={handleSliderChange("cijenaKotizacije")}
                                valueLabelDisplay="auto"
                                min={0}
                                max={500}
                            />
                        </div>

                        <div className="mb-6">
                            <label className="block text-sm font-medium mb-2">
                                Iznos Nagrade (€): {filter.iznosNagrade[0]} - {filter.iznosNagrade[1]}
                            </label>
                            <Slider
                                value={filter.iznosNagrade}
                                onChange={handleSliderChange("iznosNagrade")}
                                valueLabelDisplay="auto"
                                min={0}
                                max={5000}
                            />
                        </div>

                        <button
                            onClick={fetchNewTournaments}
                            className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-md"
                        >
                            Pretraži
                        </button>
                    </div>

                    {/* New Tournaments Section */}
                    <div className="mb-10">
                        <h2 className="text-xl font-semibold mb-4">Ponuđeni turniri za oabrane filtere:</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {Newtournaments.map((tournament) => (
                                <div
                                    key={tournament.id}
                                    className="p-4 bg-white shadow-md rounded-md"
                                >
                                    <h3 className="font-bold text-lg">{tournament.nazivTurnir}</h3>
                                    <Button
                                        variant="outline"
                                        className="text-white"
                                        id={tournament.idturnir}
                                        onClick={(e) => HandlePrijava(e)}
                                    >
                                        Prijavi se
                                    </Button>
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Registered Tournaments Section */}
                    <div className="mb-10">
                        <h2 className="text-xl font-semibold mb-4">Turniri na koje ste se prijavili:</h2>
                        <div className="mb-4">
                            <label className="block text-sm font-medium mb-2">Status Prijave</label>
                            <select
                                value={status}
                                onChange={handleStatusChange}
                                className="p-2 border rounded-md w-full bg-gray-100"
                            >
                                <option value="prihvacen">Prihvaćen</option>
                                <option value="odbijen">Odbijen</option>
                                <option value="naCekanju">Na čekanju</option>
                            </select>
                        </div>
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {Registertournaments.map((tournament) => (
                                <div
                                    key={tournament.id}
                                    className="p-4 bg-white shadow-md rounded-md"
                                >
                                    <h3 className="font-bold text-lg">{tournament.nazivTurnir}</h3>
                                    <p>Status: {status}</p>
                                    
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Played Tournaments Section */}
                    <div>
                        <h2 className="text-xl font-semibold mb-4">Odigrani turniri:</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {Playedtournaments.map((tournament) => (
                                <div
                                    key={tournament.id}
                                    className="p-4 bg-white shadow-md rounded-md"
                                >
                                    <h3 className="font-bold text-lg">{tournament.naziv}</h3>
                                    <p>Cijena Kotizacije: {tournament.cijenaKotizacije} €</p>
                                    <p>Iznos Nagrade: {tournament.iznosNagrade} €</p>
                                    <p>Opis: {tournament.opis}</p>
                                    <Button
                                        variant="outline"
                                        className="text-white"
                                        id={tournament.idturnir}
                                        onClick={(e) => TournamentDetails(e)}
                                    >
                                        Prikaži detalje
                                    </Button>
                                </div>
                            ))}
                        </div>
                    </div>
				</div>

            </div>
        </>

    );
   
}