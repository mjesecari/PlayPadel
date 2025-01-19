import { useState } from "react";

import TournamentsOwner from "@/components/TournamentsOwner";
//import TournamentsPlayer from "@/components/TournamentsPlayer";
import NavBar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";

export default function TournamentsPage() {
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
                <TournamentsOwner userInfo={userInfo}></TournamentsOwner>
            </>
        );
    } else {
        return (
            <>
                <NavBar></NavBar>
                //<TournamentsPlayer userInfo={userInfo}></TournamentsPlayer>
            </>
        );
    }
}
