// import { useCalendarApp, ScheduleXCalendar } from "@schedule-x/react";
// import {
// 	createViewDay,
// 	createViewMonthAgenda,
// 	createViewMonthGrid,
// 	createViewWeek,
// } from "@schedule-x/calendar";
// import { createEventsServicePlugin } from "@schedule-x/events-service";

// import "@schedule-x/theme-default/dist/index.css";

// function CalendarApp({ eventsProp }) {
// 	console.log(eventsProp);
// 	const eventsService = useState(() => createEventsServicePlugin())[0];

// 	const calendar = useCalendarApp({
// 		views: [
// 			createViewDay(),
// 			createViewWeek(),
// 			createViewMonthGrid(),
// 			createViewMonthAgenda(),
// 		],
// 		//[],
// 		events: eventsProp,
// 		plugins: [eventsService],
// 	});

// 	useEffect(() => {
// 		// get all events
// 		eventsService.getAll();
// 	}, []);

// 	return (
// 		<div>
// 			<ScheduleXCalendar calendarApp={calendar} />
// 		</div>
// 	);
// }

// export default CalendarApp;

import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";

import "react-big-calendar/lib/css/react-big-calendar.css";
import { useEffect, useState } from "react";
import { Overlay } from "@radix-ui/react-dialog";

export default function CalendarApp({ eventsProp }) {
	const localizer = momentLocalizer(moment);

	const [timeSelected, setTimeSelected] = useState(undefined);
	const [lastSelected, setLastSelected] = useState(undefined);
	const [eventsList, setEventsList] = useState(eventsProp);
	const [overlap, setOverlap] = useState(false);

	useEffect(() => {
		console.log("last:", lastSelected);
		console.log("time", timeSelected);
		if (lastSelected != undefined) {
			console.log("should  remove");
			setEventsList([
				...eventsList.filter((e) => e.start != lastSelected.start),
			]);
		}
		setLastSelected(timeSelected);
	}, [timeSelected]);

	function dateSelected(slotinfo) {
		// if (timeSelected != undefined) {
		// 	console.log("should  remove");
		// 	setEventsList([
		// 		...eventsList.filter((e) => !timeSelected.start == e.start),
		// 	]);
		// }

		setTimeSelected({
			title: "ODABRAN TERMIN",
			start: slotinfo.start,
			end: slotinfo.end,
		});
		console.log(timeSelected);
		setEventsList([
			...eventsList,
			{
				id: "0",
				title: "ODABRAN TERMIN",
				start: slotinfo.start,
				end: slotinfo.end,
			},
		]);
		console.log(typeof eventsList, eventsList);
		//checkIfAllowed();
	}

	// check if overlap
	useEffect(() => {
		setOverlap(false);

		if (!timeSelected) return;
		if (
			eventsList.filter((e) => {
				return (
					(timeSelected.end > e.start && timeSelected.end < e.end) ||
					(timeSelected.start > e.start && timeSelected.start < e.end)
				);
			}).length != 0
		)
			setOverlap(true);
	}, [timeSelected]);

	const MyCalendar = (props) => (
		<div>
			<Calendar
				localizer={localizer}
				events={eventsList}
				startAccessor="start"
				endAccessor="end"
				defaultDate={new Date()}
				defaultView="week"
				style={{ height: "65vh", width: "50vw" }}
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
		</div>
	);
}
