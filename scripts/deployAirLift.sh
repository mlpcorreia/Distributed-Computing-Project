echo "Compressing data to be sent to the workstations."
rm -rf airLift.zip
zip -rq airLift.zip serverSide comInf clientSide run_local.sh
echo "Transfering data to the workstations."
echo ""
echo "Transfering data to the workstation 1."
sshpass -f password ssh server1.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server1.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server1.ua.pt:AirLift
echo "Decompressing data sent to the server side node."
sshpass -f password ssh server1.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at workstation 1."
sshpass -f password ssh server1.ua.pt 'cd AirLift ; javac */*.java'
echo "End of compiling at the workstation 1."
echo ""
sleep 1
echo "Transfering data to the workstation 2."
sshpass -f password ssh server2.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server2.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server2.ua.pt:AirLift
echo "Decompressing data sent to the workstation 2."
sshpass -f password ssh server2.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 2."
sshpass -f password ssh server2.ua.pt 'cd AirLift ; javac */*.java'
echo "End of compiling at the workstation 2."
echo ""
sleep 1
echo "Transfering data to the workstation 3."
sshpass -f password ssh server3.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server3.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server3.ua.pt:AirLift
echo "Decompressing data sent to the workstation 3."
sshpass -f password ssh server3.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 3."
sshpass -f password ssh server3.ua.pt 'cd AirLift ; javac */*.java'
echo "End of compiling at the workstation 3."
echo ""
sleep 1
echo "Transfering data to the workstation 4."
sshpass -f password ssh server4.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server4.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server4.ua.pt:AirLift
echo "Decompressing data sent to the workstation 4."
sshpass -f password ssh server4.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 4."
sshpass -f password ssh server4.ua.pt 'cd AirLift ; javac */*.java'
echo "End of compiling at the workstation 4."
echo ""
sleep 1
echo "Transfering data to the workstation 5."
sshpass -f password ssh server5.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server5.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server5.ua.pt:AirLift
echo "Decompressing data sent to the workstation 5."
sshpass -f password ssh server5.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 5."
sshpass -f password ssh server5.ua.pt 'cd AirLift ; javac */*.java'
echo ""
sleep 3
echo "Transfering data to the workstation 6."
sshpass -f password ssh server6.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server6.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server6.ua.pt:AirLift
echo "Decompressing data sent to the workstation 6."
sshpass -f password ssh server6.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 6."
sshpass -f password ssh server6.ua.pt 'cd AirLift ; javac */*.java'
echo ""
sleep 3
echo "Transfering data to the workstation 7."
sshpass -f password ssh server7.ua.pt 'mkdir -p AirLift'
sshpass -f password ssh server7.ua.pt 'rm -rf AirLift/*'
sshpass -f password scp airLift.zip server7.ua.pt:AirLift
echo "Decompressing data sent to the workstation 7."
sshpass -f password ssh server7.ua.pt 'cd AirLift ; unzip -q airLift.zip'
echo " Compiling program files at the workstation 7."
sshpass -f password ssh server7.ua.pt 'cd AirLift ; javac */*.java'
sleep 3
