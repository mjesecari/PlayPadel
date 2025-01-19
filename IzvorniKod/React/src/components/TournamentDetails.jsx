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

export default function TournamentDetails({userInfo}) {
    const { id } = useParams(); 
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
    }, [id]);

    function fetchTournamentDetails(){
        axios
            .get(`/api/tournaments/${id}/details`) // Replace with your actual API endpoint
            .then((res) => {
                setDetails(res.data);
            })
            .catch((err) => console.error("Error fetching tournament details:", err));
    }
    function fetchTournamentPhotos(){
        axios
			.get("/api/#") //endpoint for turnir/id/slike
			.then((res) => {
				console.log(res);
				setTournamentPhotos(res.data);
			})
			.catch((error) => console.log(error));
    }
    function fetchTournamentComments(){
        axios
			.get("/api/#") //endpoint for turniri/id/komentari
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

		axios
			.post(`/api/#s`, formData) // Replace with your API endpoint
			.then(() => {
				setPhotoDialogOpen(false);
				fetchTournamentPhotos(); // Refresh photos
			})
			.catch((err) => console.error("Error uploading photo:", err));
	}

	// Handle Comment Submission
	function handleCommentSubmit() {
		axios
			.post(`/api/#`, { comment: newComment, userId: userInfo.id }) // Replace with your API endpoint
			.then(() => {
				setCommentDialogOpen(false);
				fetchTournamentComments(); // Refresh comments
			})
			.catch((err) => console.error("Error submitting comment:", err));
	}

    
    if (!details) {
        return <p>Loading...</p>;
    }

    const sliderSettings = {
		dots: true,
		infinite: true,
		speed: 500,
		slidesToShow: 1,
		slidesToScroll: 1,
		arrows: true,
	};

    return (
		<div className="p-8">
			{/* Tournament Header */}
			<h1 className="text-4xl font-bold text-center mb-6">{details.naziv}</h1>

			{/* Photo Slideshow */}
			<div className="relative group">
				<Slider {...sliderSettings}>
					{tournamentPhotos.map((photo, index) => (
						<div key={index} className="w-full">
							<img
                                src={`data:image/jpeg;base64,${photo.photoData}`}
								alt={`Tournament Photo ${index + 1}`}
								className="w-full h-64 object-cover"
							/>
                            <div opacity-0 group-hover:opacity-100 bg-opacity-50 transition-opacity>
                                Sliku postavio: {photo.Korisnik.email}
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
				<h2 className="text-2xl font-semibold">Details</h2>
				<p className="mt-2">Nagrade: {details.nagrade}</p>
				<p className="mt-2">Opis: {details.opis}</p>
                <p className="mt-2">Lokacija: {details.lokacija}</p>
                <p className="mt-2">Datum: {details.datum}</p>
			</div>

			{/* Comments Slideshow */}
			<div className="mt-6 relative group">
				<h2 className="text-2xl font-semibold mb-4">Comments</h2>
				<Slider {...sliderSettings}>
					{tournamentComments.map((comment, index) => (
						<div key={index} className="p-4 border rounded">
							<p>{comment.text}</p>
							<p className="text-sm text-gray-500">— {comment.Korisnik.email}</p>
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