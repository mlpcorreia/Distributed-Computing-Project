echo "Transfering config.txt to workstation 5."
sshpass -f password scp config.txt server:AirLift
echo "Executing program at the workstation 5."
sshpass -f password ssh server 'cd AirLift ; ./run_local.sh'
