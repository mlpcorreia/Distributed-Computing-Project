echo "Transfering config.txt to workstation 4."
sshpass -f password scp config.txt server:AirLift
echo "Executing program at the workstation 4."
sshpass -f password ssh server 'killall java'
sshpass -f password ssh server 'cd AirLift ; ./run_local.sh'
echo "Server Shutdown!"
