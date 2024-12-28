import { Button } from "@headlessui/react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";

import "react-big-calendar/lib/css/react-big-calendar.css";
import { useEffect, useState } from "react";

export default function CalendarApp({ eventsProp }) {
	const localizer = momentLocalizer(moment);

	const [timeSelected, setTimeSelected] = useState(undefined);
	const [lastSelected, setLastSelected] = useState(undefined);
	const [eventsList, setEventsList] = useState(eventsProp);
	const [overlap, setOverlap] = useState(false);

	function dateSelected(slotinfo) {
		let endTime = new Date(slotinfo.start);
		endTime.setHours(endTime.getHours() + 1);
		setTimeSelected({
			title: "ODABRANO",
			start: slotinfo.start,
			end: endTime,
		});
		setEventsList([
			...eventsList,
			{
				title: "ODABRANO",
				start: slotinfo.start,
				end: endTime,
			},
		]);
	}

	function sendReservation() {
		if (overlap) return;
		// TODO
		// axios
		// 	.post("/api/tereni/s")
		// 	.then((res) => {
		// 		setCourts(res.data);
		// 	})
		// 	.catch((error) => console.log(error));
		// CLOSE DIALOG (by sending signal to parent? or click escape)
	}

	// add to events list
	useEffect(() => {
		if (lastSelected != undefined) {
			setEventsList([
				...eventsList.filter((e) => e.start != lastSelected.start),
			]);
		}
		setLastSelected(timeSelected);
	}, [timeSelected]);

	// check if overlap
	useEffect(() => {
		setOverlap(false);

		if (!timeSelected) return;
		if (
			eventsList.filter((e) => {
				return (
					e.id != timeSelected.id &&
					((timeSelected.end > e.start && timeSelected.end <= e.end) ||
						(timeSelected.start >= e.start &&
							timeSelected.start < e.end) ||
						(timeSelected.start < e.start && timeSelected.end > e.end))
				);
			}).length != 0
		)
			setOverlap(true);
	}, [lastSelected]);

	const MyCalendar = (props) => (
		<div>
			<Calendar
				localizer={localizer}
				events={eventsList}
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
