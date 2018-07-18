echo "Transfering config.txt to workstation 3."
sshpass -f password scp config.txt server3.ua.pt:AirLift
echo "Executing program at the workstation 3."
sshpass -f password ssh server3.ua.pt 'killall java'
sshpass -f password ssh server3.ua.pt 'cd AirLift ; ./run_local.sh'
echo "Server Shutdown!"
