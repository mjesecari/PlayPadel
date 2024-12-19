import { useEffect, useState } from "react";
import {
	Card,
	CardHeader,
	CardTitle,
	CardDescription,
	CardContent,
	CardFooter,
} from "./ui/card";
import { Button } from "@headlessui/react";
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "@/components/ui/dialog";

export default function Popup({ open, title, desc }) {
	return (
		<>
			{/* title and desc should be text only */}
			{/* 
			<Card>
				<CardHeader>
					<CardTitle>{title}</CardTitle>
					<CardDescription>{desc}</CardDescription>
				</CardHeader>
				<CardFooter></CardFooter>
			</Card> */}

			<Dialog open={open}>
				<DialogHeader>
					<DialogTitle>{title}</DialogTitle>
					<DialogDescription>{desc}</DialogDescription>
				</DialogHeader>
			</Dialog>
		</>
	);
}
