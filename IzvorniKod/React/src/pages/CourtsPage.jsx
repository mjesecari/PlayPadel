import { useState } from "react";

import CourtsOwner from "@/components/CourtsOwner";
import CourtsPlayer from "@/components/CourtsPlayer";
import NavBar from "@/components/Navbar";

export default function CourtsPage() {
	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});

	if (!userInfo) return <p>Loading...</p>;

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
