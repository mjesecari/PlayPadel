import { Button } from "@headlessui/react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import axios from "axios";

import "react-big-calendar/lib/css/react-big-calendar.css";
import { useEffect, useState } from "react";

export default function CalendarApp({ eventsProp, id, userInfo }) {
	const localizer = momentLocalizer(moment);

	const [timeSelected, setTimeSelected] = useState(undefined);
	const [lastSelected, setLastSelected] = useState(undefined);
	const [eventsList, setEventsList] = useState();
	const [overlap, setOverlap] = useState(false);
	const [reservation, setReservation] = useState(undefined);

	const [displayedEvents, setDisplayedEvents] = useState();

	function fetchReservations() {
		console.log("id:", id);
		axios.get("/api/rezervacije?terenId=" + id).then((res) => {
			setEventsList(
				res.data.map((e) => {
					let starttime = moment(
						e.vrijeme.toSpliced(1, 1, e.vrijeme[1] - 1)
					).toDate();
					let endtime = moment(e.vrijeme.toSpliced(1, 1, e.vrijeme[1] - 1))
						.add(1, "hours")
						.toDate();
					return {
						title: "ZAUZETO",
						start: starttime,
						end: endtime,
					};
				})
			);
			setDisplayedEvents(
				res.data.map((e) => {
					let starttime = moment(
						e.vrijeme.toSpliced(1, 1, e.vrijeme[1] - 1)
					).toDate();
					let endtime = moment(e.vrijeme.toSpliced(1, 1, e.vrijeme[1] - 1))
						.add(1, "hours")
						.toDate();
					return {
						title: "ZAUZETO",
						start: starttime,
						end: endtime,
					};
				})
			);
		});
	}

	useEffect(() => {
		fetchReservations();
	}, []);

	// on selected timeframe, update all displayed events
	function dateSelected(slotinfo) {
		let endTime = new Date(slotinfo.start);
		endTime.setHours(endTime.getHours() + 1);
		setTimeSelected({
			id: -1,
			title: "ODABRANO",
			start: slotinfo.start,
			end: endTime,
		});
		setDisplayedEvents([
			...eventsList,
			{
				title: "ODABRANO",
				start: slotinfo.start,
				end: endTime,
			},
		]);

		// setEventsList([
		// 	...eventsList,
		// 	{
		// 		title: "ODABRANO",
		// 		start: slotinfo.start,
		// 		end: endTime,
		// 	},
		// ]);
	}

	// send reservation to database then refetch all reservations
	function sendReservation() {
		if (overlap) return;
		console.log(
			moment(timeSelected.start.toString()).format("YYYY-MM-DDTHH:mm:ss")
		);

		axios
			.post("/api/rezervacije", {
				terenId: id,
				korisnikEmail: userInfo.email,
				vrijeme: moment(timeSelected.start.toString()).format(
					"YYYY-MM-DDTHH:mm:ss"
				),
			})
			.then((res) => {
				console.log("yipee");
				setReservation(
					moment(timeSelected.start.toString()).format(
						"YYYY-MM-DDTHH:mm:ss"
					)
				);
			})
			.then(() => {
				setTimeSelected();
				fetchReservations();
			})
			.catch((error) => console.log(error));
	}

	// add to events list
	useEffect(() => {
		if (lastSelected != undefined) {
			setEventsList([
				...eventsList.filter((e) => e.start != lastSelected.start),
			]);
		}
		setLastSelected(timeSelected);
		if (timeSelected) console.log(timeSelected.start);
	}, [timeSelected]);

	// check if overlap, prevent selection before today
	useEffect(() => {
		console.log("checking ovelar", eventsList, timeSelected);
		setOverlap(false);

		if (!timeSelected) return;
		if (
			eventsList.some((e) => {
				return (
					!e.id &&
					(+e.start == +timeSelected.start ||
						(timeSelected.start > e.start &&
							timeSelected.start < e.end) ||
						(timeSelected.end < e.end && timeSelected.end > e.start))

					// 	+e.start != +timeSelected.start &&
					// 	((timeSelected.end > e.start && timeSelected.end <= e.end) ||
					// 		(timeSelected.start >= e.start &&
					// 			timeSelected.start < e.end) ||
					// 		(timeSelected.start < e.start && timeSelected.end > e.end))
					// );
				);
			})
		)
			setOverlap(true);
		//if (timeSelected < Date.now()) setOverlap(true);
	}, [lastSelected]);

	const MyCalendar = (props) => (
		<div>
			<Calendar
				localizer={localizer}
				events={displayedEvents}
				startAccessor="start"
				endAccessor="end"
				defaultDate={new Date()}
				defaultView="week"
				views={["week", "month"]}
				style={{ height: "70vh", width: "60vw" }}
				// sets time interval 08:00-20:00
				// could be dynamic if necessary
				min={new Date(2024, 1, 0, 8, 0, 0)}
				max={new Date(2024, 1, 0, 21, 0, 0)}
				timeslots={2} //width of box n*30mins
				selectable={true}
				onSelectSlot={(slotinfo) => dateSelected(slotinfo)}
			/>
			{overlap && (
				<p className="p-2 bg-red-500 text-white text-center">
					Zabranjeno preklapanje termina
				</p>
			)}
			{reservation && (
				<p className="p-2 bg-green-500 text-white text-center">
					Uspješno rezervirano
				</p>
			)}
		</div>
	);

	return (
		<div>
			<MyCalendar />

			<div>
				<Button
					className="text-white w-full mt-4"
					onClick={sendReservation}
				>
					Potvrdi
				</Button>
			</div>
		</div>
	);
}
