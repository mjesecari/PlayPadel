import { useEffect, useState } from "react";
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
import { useParams } from "react-router-dom";
import Slider from "react-slick"; // Add react-slick for carousel functionality
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import moment from 'moment';

export default function TournamentDetails({userInfo}) {
    const { idTurnir } = useParams(); 
    const [tournamentPhotos, setTournamentPhotos] = useState([]);
    const [tournamentComments, setTournamentComments] = useState([]);
    const [details, setDetails] = useState(null);
    const [photoDialogOpen, setPhotoDialogOpen] = useState(false);
	const [commentDialogOpen, setCommentDialogOpen] = useState(false);
	const [newPhoto, setNewPhoto] = useState(null);
	const [newComment, setNewComment] = useState("");
    useEffect(() => {
        fetchTournamentDetails();
        fetchTournamentPhotos();
		fetchTournamentComments();
    }, [idTurnir]);

    function fetchTournamentDetails(){
		console.log(idTurnir);
        axios
            .get(`/api/turnir/detalji/${idTurnir}`) // Replace with your actual API endpoint
            .then((res) => {
                setDetails(res.data);
            })
            .catch((err) => console.error("Error fetching tournament details:", err));
    }
    function fetchTournamentPhotos(){
        axios
			.get("/api/slikeTurnir/" + idTurnir) //endpoint for turnir/id/slike
			.then((res) => {
				console.log(res);
				setTournamentPhotos(res.data);
			})
			.catch((error) => console.log(error));
    }
    function fetchTournamentComments(){
        axios
			.get("/api/komentariTurnir/" + idTurnir) //endpoint for turniri/id/komentari
			.then((res) => {
				console.log(res);
				setTournamentComments(res.data);
			})
			.catch((error) => console.log(error));
    }

    function handlePhotoUpload() {
		const formData = new FormData();
		formData.append("photo", newPhoto);
        formData.append("userId", userInfo.id);
		axios({
			url: `/api/slikaTurnir/${idTurnir}`,
			method: "POST",
			headers: {
				//"Content-Type": "application/json",
				"Content-Type": "multipart/form-data",
			},
			data: formData
		}).then(() => {
				setPhotoDialogOpen(false);
				fetchTournamentPhotos(); // Refresh photos
		})
		.catch((err) => console.error("Error uploading photo:", err));
	}

	// Handle Comment Submission
	function handleCommentSubmit() {
		axios
			.post(`/api/komentarTurnir/${idTurnir}`, { tekst: newComment, userId: userInfo.id }) // Replace with your API endpoint
			.then(() => {
				setCommentDialogOpen(false);
				fetchTournamentComments(); // Refresh comments
			})
			.catch((err) => console.error("Error submitting comment:", err));
	}

    
    if (!details) {
        return <p>Loading...</p>;
    }

    const sliderSettingsPhotos = {
		dots: true,
		infinite: tournamentPhotos.length > 1,
		speed: 500,
		slidesToShow: 1,
		slidesToScroll: 1,
		arrows: true,         // Show previous/next arrows
		autoplay: true,       // Enable autoplay
		autoplaySpeed: 3000,  // Time between transitions (in milliseconds)
		pauseOnHover: true, 
	};
	const sliderSettingsComments = {
		dots: true,
		infinite: tournamentComments.length > 1,
		speed: 500,
		slidesToShow: 1,
		slidesToScroll: 1,
		arrows: true,         // Show previous/next arrows
		autoplay: true,       // Enable autoplay
		autoplaySpeed: 3000,  // Time between transitions (in milliseconds)
		pauseOnHover: true, 
	};

    return (
		<div className="p-8">
			{/* Tournament Header */}
			<h1 className="text-4xl font-bold text-center mb-6">{details.nazivTurnir}</h1>

			{/* Photo Slideshow */}
			<div className="relative group">
				<Slider {...sliderSettingsPhotos}>
					{tournamentPhotos.map((photo, index) => (
						<div
							key={index}
							className="relative w-full h-full flex items-center justify-center w-[90vw] h-[50vh] mx-auto overflow-hidden"
						>
							<img
								src={`data:image/jpeg;base64,${photo.photoData}`}
								alt={`Tournament Photo ${index + 1}`}
								className="w-full h-full object-cover rounded-lg shadow-lg"
							/>
							<div className="absolute bottom-2 left-2 bg-black bg-opacity-50 text-white text-sm px-2 py-1 rounded opacity-0 group-hover:opacity-100 transition-opacity">
								Sliku postavio: {photo.korisnik.email}
							</div>
						</div>
					))}
				</Slider>
				{/* Add Photo Button (Visible on Hover) */}
				<div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 bg-black bg-opacity-50 transition-opacity">
                    <Button
						className="bg-white text-black px-4 py-2 rounded"
						onClick={() => setPhotoDialogOpen(true)}
					>
						Dodaj Sliku
					</Button>
				</div>
			</div>

			{/* Tournament Details */}
			<div className="mt-6">
				<h2 className="text-2xl font-semibold">Detalji</h2>
				<p className="mt-2">Nagrade: {details.nagrade.map((nagrada, index) => (
									<span key={index}>
									za {index + 1}. mjesto: {nagrada} €
									<br />
									</span>
								))}</p>
				<p className="mt-2">Opis: {details.opis}</p>
                <p className="mt-2">Lokacija: {details.lokacijaTurnir}</p>
                <p className="mt-2">Datum: {moment(details.datumTurnir).format('DD-MM-YYYY')}</p>
				<p className="mt-2">Opis: {details.cijenaKotizacije}€</p>
			</div>

			{/* Comments Slideshow */}
			<div className="mt-6 relative group">
				<h2 className="text-2xl font-semibold mb-4">Komentari</h2>
				<Slider {...sliderSettingsComments}>
					{tournamentComments.map((comment, index) => (
						<div key={index} className="p-4 border rounded">
							<p>{comment.tekstKomentara}</p>
							<p className="text-sm text-gray-500">— {comment.korisnik.email}</p>
						</div>
					))}
				</Slider>
				{/* Add Comment Button (Visible on Hover) */}
				<div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 bg-black bg-opacity-50 transition-opacity">
					<Button
						className="bg-white text-black px-4 py-2 rounded"
						onClick={() => setCommentDialogOpen(true)}
					>
						Dodaj Komentar
					</Button>
				</div>
			</div>

			{/* Photo Upload Dialog */}
			<Dialog open={photoDialogOpen} onOpenChange={setPhotoDialogOpen}>
				<DialogContent>
					<DialogHeader>
						<DialogTitle>Odaberi sliku</DialogTitle>
					</DialogHeader>
					<Input type="file" onChange={(e) => setNewPhoto(e.target.files[0])} />
					<DialogFooter>
						<Button onClick={handlePhotoUpload}>Dodaj</Button>
					</DialogFooter>
				</DialogContent>
			</Dialog>

			{/* Comment Submission Dialog */}
			<Dialog open={commentDialogOpen} onOpenChange={setCommentDialogOpen}>
				<DialogContent>
					<DialogHeader>
						<DialogTitle>Dodaj Komentar</DialogTitle>
					</DialogHeader>
					<Input
						placeholder="Upiši svoj komentar..."
						onChange={(e) => setNewComment(e.target.value)}
					/>
					<DialogFooter>
						<Button onClick={handleCommentSubmit}>Postavi</Button>
					</DialogFooter>
				</DialogContent>
			</Dialog>
		</div>
	);

}