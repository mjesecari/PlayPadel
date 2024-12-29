import { useState, useEffect } from "react";

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
