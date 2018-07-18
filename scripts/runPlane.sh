echo "Transfering config.txt to the workstation 2."
sshpass -f password scp config.txt server2.ua.pt:AirLift
echo "Executing program at the workstation 2."
sshpass -f password ssh server2.ua.pt 'killall java'
sshpass -f password ssh server2.ua.pt 'cd AirLift ; ./run_local.sh'
echo "Sever Shutdown"
