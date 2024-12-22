import { useState, useEffect } from "react";
import { useCalendarApp, ScheduleXCalendar } from "@schedule-x/react";
import {
	createViewDay,
	createViewMonthAgenda,
	createViewMonthGrid,
	createViewWeek,
} from "@schedule-x/calendar";
import { createEventsServicePlugin } from "@schedule-x/events-service";

import "@schedule-x/theme-default/dist/index.css";

function CalendarApp({ eventsProp }) {
	console.log(eventsProp);
	const eventsService = useState(() => createEventsServicePlugin())[0];

	const calendar = useCalendarApp({
		views: [
			createViewDay(),
			createViewWeek(),
			createViewMonthGrid(),
			createViewMonthAgenda(),
		],
		//[],
		events: eventsProp,
		plugins: [eventsService],
	});

	useEffect(() => {
		// get all events
		eventsService.getAll();
	}, []);

	return (
		<div>
			<ScheduleXCalendar calendarApp={calendar} />
		</div>
	);
}

export default CalendarApp;
