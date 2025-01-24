import MembershipPage from "@/pages/MembershipPage";
import { Button } from "@headlessui/react";
import { useNavigate } from "react-router-dom";

export default function NoMembership() {
	const navigate = useNavigate();

	function redirect() {
		navigate("/Membership");
	}

	return (
		<>
			<div className="flex-col px-4">
			<p className="text-xl p-2 "> Nemate aktivno PlayPadel članstvo!</p>
			<p className=" p-2">
				Kako biste mogli dodavati terene i turnire molimo Vas da obnovite
				članarinu.
			</p>
			</div>

			<Button className="h-fit text-white m-6" onClick={redirect}>
				Obnovi članarinu
			</Button>
			
		</>
	);
}
