echo "Transfering config.txt to workstation 7."
sshpass -f password scp config.txt server7.ua.pt:AirLift
echo "Executing program at the workstation 7."
sshpass -f password ssh server7.ua.pt 'cd AirLift ; ./run_local.sh'
