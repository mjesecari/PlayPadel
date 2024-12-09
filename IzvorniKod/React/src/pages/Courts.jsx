import "../Open.css";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Layout from "@/components/Layout";

export default function Courts({}) {
	return (
		<>
			<Layout userInfo={userInfo} />

			<Card>
				<CardHeader>
					<CardTitle>PlayPadel</CardTitle>
				</CardHeader>

				<CardContent className="font-mono">
					Logged in successfully :)
				</CardContent>
			</Card>
		</>
	);
}
