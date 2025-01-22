import { useState, useEffect } from "react";

import NavBar from "@/components/Navbar";
import { useNavigate } from "react-router-dom";
import {
	Card,
	CardHeader,
	CardTitle,
	CardDescription,
	CardContent,
	CardFooter,
} from "../components/ui/card";
import { Description } from "@headlessui/react";
import { Button } from "@headlessui/react";

import axios from "axios";
import { DatabaseZap } from "lucide-react";

export default function MembershipPage() {
	const [price, setPrice] = useState(null);
	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});
	const navigate = useNavigate();

	if (!userInfo) {
		navigate("/", { replace: true });
		return;
	}

	function getPrice() {
		axios
			.get("api/membership/price")
			.then((res) => setPrice(res.data))
			.catch((e) => console.log(e));
	}


		
	function redirectToPayPal() {
		const data = {
			userId: userInfo.id.toString(),
			description: "membership",
		};

		console.log(data);
		axios
			.post("api/payment/create", null, {
				params: {
					userId: userInfo.id,
					description: "membership",
				},
			})
			.then((res)=>{
				console.log("redirecting to paypal"),
				window.location.href = res.data.approvalUrl;
				console.log(decodeURIComponent("Q09NUExJQU5DRV9WSU9MQVRJT04%3D"));
			})
			.catch((e) => console.log(e));
		
	}

	

	useEffect(() => {
		getPrice();
	}, []);

	return (
		<>
			<NavBar> </NavBar>
			<p>pay up</p>
			<Card className={"mt-10"}>
				<CardHeader>
					<CardTitle>Plaćanje članarine</CardTitle>
				</CardHeader>
				<CardContent className={"text-md"}>
					<p>Članstvo u PlayPadel aplikaciji za vlasnika terena iznosi:</p>
					<p className="text-bold m-4">
						<b>{price} eura</b> mjesečno
					</p>
				</CardContent>
				<CardFooter>
					<Button
						onClick={redirectToPayPal}
						className={"text-white m-auto"}
					>
						Platite putem PayPal-a
					</Button>
				</CardFooter>
			</Card>
		</>
	);
}
