import { useState } from "react";
import {
	Disclosure,
	DisclosureButton,
	DisclosurePanel,
	Menu,
	MenuButton,
	MenuItem,
	MenuItems,
} from "@headlessui/react";
import {
	Bars3Icon,
	BellIcon,
	XMarkIcon,
	UserIcon,
} from "@heroicons/react/24/outline";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import EditUserData from "./EditUserData";

const nav = {
	player: [
		{ name: "Početna stranica", href: "/", current: false },
		{ name: "Moje rezervacije", href: "/Reservations", current: false },
		{ name: "Tereni i termini ", href: "/CourtsPage", current: false },
		{ name: "Turniri ", href: "/TournamentsPage", current: false },
	],

	owner: [
		{ name: "Početna stranica", href: "/", current: false },
		{ name: "Moji tereni ", href: "/CourtsPage", current: false },
		{ name: "Moji turniri ", href: "/TournamentsPage", current: false },
	],

	admin: [
		{ name: "Početna stranica", href: "/", current: false },
		{ name: "Popis korisnika", href: "#", current: false },
	],
	admin: [{ name: "Popis korisnika", href: "/AdminPage", current: false }],
};

let navigation;

function classNames(...classes) {
	return classes.filter(Boolean).join(" ");
}

export default function NavBar() {
	const [userInfo, setUserInfo] = useState(() => {
		const savedUserInfo = sessionStorage.getItem("userInfo");
		return savedUserInfo ? JSON.parse(savedUserInfo) : null;
	});

	const navigate = useNavigate();

	if (userInfo.admin) {
		navigation = nav.admin;
	} else if (userInfo.owner) {
		navigation = nav.owner;
	} else {
		navigation = nav.player;
	}

	const handleSignOut = async (event) => {
		try {
			event.preventDefault();
			sessionStorage.clear();
			document.cookie =
			"JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; domain=localhost;";
			localStorage.clear;
			navigate("/Login", { replace: true });
			await axios.post("api/logout");
		} catch (error) {
			console.error("Error signing out:", error);
		}
	};

	return (
		<Disclosure
			as="nav"
			className="bg-gray-800 absolute inset-x-0 top-0 h-16"
		>
			<div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
				<div className="relative flex h-16 items-center justify-between">
					<div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
						{/* Mobile menu button*/}
						<DisclosureButton className="group relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
							<span className="absolute -inset-0.5" />
							<span className="sr-only">Open main menu</span>
							<Bars3Icon
								aria-hidden="true"
								className="block h-6 w-6 group-data-[open]:hidden"
							/>
							<XMarkIcon
								aria-hidden="true"
								className="hidden h-6 w-6 group-data-[open]:block"
							/>
						</DisclosureButton>
					</div>
					<div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
						<div className="flex shrink-0 items-center">
							{/* we can put a logo in here */}
						</div>

						<div className="hidden sm:ml-6 sm:block">
							<div className="flex space-x-4">
								{navigation.map((item) => (
									<Link
										to={item.href}
										key={item.name}
										href={item.href}
										aria-current={item.current ? "page" : undefined}
										className={classNames(
											item.current
												? "bg-gray-900 text-white"
												: "text-gray-300 hover:bg-gray-700 hover:text-white",
											"rounded-md px-3 py-2 text-sm font-medium"
										)}
									>
										{item.name}
									</Link>
								))}
							</div>
						</div>
					</div>
					<div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
						<button
							type="button"
							className="relative rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
						>
							<span className="absolute -inset-1.5" />
							<span className="sr-only">View notifications</span>
							<BellIcon aria-hidden="true" className="h-6 w-6" />
						</button>

						{!userInfo.admin && <EditUserData userInfo={userInfo} />}

						{/* Profile dropdown */}

						<Menu as="div" className="relative ml-3">
							<div>
								<MenuButton className="relative flex rounded-full bg-gray-800 text-md focus:outline-none focus:ring-2  hover:text-white  focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
									<span className="absolute -inset-1.5" />
									<span className="sr-only">Open user menu</span>
									<UserIcon className="h-6 w-6 text-gray-400" />
								</MenuButton>
							</div>
							<MenuItems
								transition
								className="absolute right-0 z-10 mt-2 w-64 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
							>
								<MenuItem>
									<p
										href="#"
										className="block px-4 py-2 text-md text-gray-700"
									>
										{userInfo.email}
									</p>
								</MenuItem>

								<MenuItem>
									<a
										href="api/logout"
										onClick={handleSignOut}
										className="block px-4 py-2 text-md text-gray-700 data-[focus]:bg-gray-100 data-[focus]:outline-none"
									>
										Sign out
									</a>
								</MenuItem>
							</MenuItems>
						</Menu>
					</div>
				</div>
			</div>

			<DisclosurePanel className="sm:hidden bg-gray-700">
				<div className="space-y-1 px-2 pb-3 pt-2">
					{navigation.map((item) => (
						<DisclosureButton
							key={item.name}
							as="a"
							href={item.href}
							aria-current={item.current ? "page" : undefined}
							className={classNames(
								item.current
									? "bg-gray-900 text-white"
									: "text-gray-300 hover:bg-gray-700 hover:text-white",
								"block rounded-md px-3 py-2 text-base font-medium"
							)}
						>
							{item.name}
						</DisclosureButton>
					))}
				</div>
			</DisclosurePanel>
		</Disclosure>
	);
}
