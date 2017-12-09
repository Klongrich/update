ticks="../../spxticks.txt"

while read -r line
	do
		mkdir "$line"
done < "$ticks"
