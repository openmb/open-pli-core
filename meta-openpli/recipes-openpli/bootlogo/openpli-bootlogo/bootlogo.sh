# avoid the console messages clobbering our logo
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind
# and set the correct videomode before showing the bootlogo
[ -f /etc/videomode ] && cat /etc/videomode > /proc/stb/video/videomode

# We only support UNiBOX Official Boxes not others
if ! [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000sv' ] || [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000sv' ] || [ "$(cat /proc/stb/info/boxtype)" == 'ini-8000sv' ]; then
	BOOTLOGO=/usr/share/enigma2/box.mvi
	/usr/bin/showiframe ${BOOTLOGO}
	sleep 5
	flash_erase /dev/mtd/2 0 0
	flash_erase /dev/mtd/4 0 0
	sync
	reboot -f
else
	BOOTLOGO=/usr/share/bootlogo.mvi
fi

[ -f /etc/enigma2/bootlogo.mvi ] && BOOTLOGO=/etc/enigma2/bootlogo.mvi
/usr/bin/showiframe ${BOOTLOGO}
