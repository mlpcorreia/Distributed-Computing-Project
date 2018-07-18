cat config.txt | while read prog host port; do
	if [[ $host = $(hostname) ]]; then
		java $prog
	fi
	done
