import { useState } from "react";

import CourtsOwner from "@/components/CourtsOwner";
import CourtsPlayer from "@/components/CourtsPlayer";
import NavBar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";

export default function CourtsPage() {
	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});
	const navigate = useNavigate();

	if (!userInfo) {
		navigate("/", { replace: true });
		return;
	}

	if (userInfo.owner) {
		return (
			<>
				<NavBar></NavBar>
				<CourtsOwner userInfo={userInfo}></CourtsOwner>
			</>
		);
	} else {
		return (
			<>
				<NavBar></NavBar>
				<CourtsPlayer userInfo={userInfo}></CourtsPlayer>
			</>
		);
	}
}
