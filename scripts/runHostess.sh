echo "Transfering config.txt to workstation 6."
sshpass -f password scp config.txt server:AirLift
echo "Executing program at the workstation 6."
sshpass -f password ssh server 'cd AirLift ; ./run_local.sh'
