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
			<h2>Nemate aktivno članstvo u aplikaciji.</h2>
			<p>
				Kako biste mogli dodavati terene i turnire molimo Vas da obnovite
				članarinu.
			</p>

			<Button className="h-fit text-white ml-10" onClick={redirect}>
				Obnovi članarinu
			</Button>
		</>
	);
}
