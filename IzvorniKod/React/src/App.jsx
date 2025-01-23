import {BrowserRouter, Routes, Route ,Navigate} from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import MainPage from "./components/EditUserData";
import CourtsPage from "./pages/CourtsPage";
import Reservations from "./pages/Reservations";
import AdminPage from "./pages/AdminPage";
import TournamentsPage from "./pages/TournamentsPage";
import PaymentSuccess from "./pages/PaymentSuccess";
import Infouser from "./pages/Infouser";
import TournamentDetails from "./components/TournamentDetails"

import { useEffect, useState } from "react";

import MembershipPage from "./pages/MembershipPage";
import NoMembership from "./components/NoMembership";
import PaymentCancel from "./pages/PaymenCancel";
import PaymentError from "./pages/PaymentError";

const AdminRoute = ({ element }) => {
  const userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
  if (!userInfo || userInfo.tip !== "admin")return <Navigate to="/" />;
  return element;
};

function App() {
	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});

	const options = {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		},
	};

	useEffect(() => {
		fetch("/api/user", options)
			.then((res) => {
				if (res.status === 200) {
					return res.json();
				} else {
					throw new Error("User not authenticated");
				}
			})
			.then((data) => {
				setUserInfo(data);
				sessionStorage.setItem("userInfo", JSON.stringify(data));
			})
			.catch((error) => {
				console.error("Error fetching user data:", error);
			});
	}, []);
	return (
		<>
			{/* <Layout navigation={navigation} /> */}

			<BrowserRouter>
				<Routes>
					<Route index element={<Home userInfo={userInfo} />} />
					<Route path="Home" element={<Home userInfo={userInfo} />} />
					<Route
						path="CourtPreview"
						//element={<CourtPreview userInfo={userInfo} />}
					/>
					<Route
						path="CourtsPage"
						element={<CourtsPage userInfo={userInfo} />}
					/>
					<Route
						path="Reservations"
						element={<Reservations userInfo={userInfo} />}
					/>
					
					<Route path="/AdminPage" element={<AdminRoute element={<AdminPage userInfo={userInfo} />} />} />

					<Route path="/Login" element={<Login />} />
					<Route path="/Signup" element={<Signup />} />

					<Route
						path="/Infouser"
						element={<Infouser userInfo={userInfo} />}
					/>
					<Route path="/Membership" element={<MembershipPage />} />
					<Route path="/payment/success" element={<PaymentSuccess />} />
					<Route path="/payment/cancel" element={<PaymentCancel />} />
					<Route path="/payment/error" element={<PaymentError />} />
					<Route path="/NoMembership" element={<NoMembership />} />
					
					<Route path="TournamentsPage" element={<TournamentsPage userInfo={userInfo} />} />
					<Route path="/tournament/:idTurnir/details" element={<TournamentDetails userInfo={userInfo}/>} />
					
				</Routes>
			</BrowserRouter>
		</>
	);
}

export default App;
