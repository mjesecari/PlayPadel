import NavBar from "@/components/Navbar";

export default function Layout({ userInfo }) {
	return (
		<div>
			<NavBar userInfo={userInfo} />
		</div>
	);
}
