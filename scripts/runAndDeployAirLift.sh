'./deployAirLift.sh'
xterm  -T "Airport Server Side" -hold -e "./runAirport.sh" &
xterm  -T "Plane Server Side" -hold -e "./runPlane.sh" &
xterm  -T "Destination Server Side" -hold -e "./runDestination.sh" &
xterm  -T "Logger Server Side" -hold -e "./runLogger.sh" &
sleep 10
xterm  -T "Pilot Client Side" -hold -e "./runPilot.sh" &
xterm  -T "Hostess Client Side" -hold -e "./runHostess.sh" &
xterm  -T "Passengers Client Side" -hold -e "./runPassenger.sh" &
