import {BrowserRouter, Routes, Route ,Navigate} from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import MainPage from "./components/EditUserData";
import CourtsPage from "./pages/CourtsPage";
import Reservations from "./pages/Reservations";
import AdminPage from "./pages/AdminPage";

//import CourtPreview from "./components/CourtPreview";
import Infouser from "./pages/Infouser";

import { useEffect, useState } from "react";

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
					<Route
						path="MainPage"
						element={<MainPage userInfo={userInfo} />}
					/>
				</Routes>
			</BrowserRouter>
		</>
	);
}

export default App;
