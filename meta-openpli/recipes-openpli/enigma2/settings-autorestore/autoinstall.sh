#! /bin/sh
# This script is run once when your box boots for the first time.
# It installs the things from /hdd/backup/autoinstall
# or from wherever the settings were restored

if [ $(find /media -maxdepth 2 -iname nobackup) ]
then
    echo abort autoinstall
    rm -f /etc/rc?.d/S*autoinstall*
    exit 0
fi

BACKUPDIR=/media/hdd
INSTALLED=/etc/installed
LOGFILE=/var/log/autoinstall.log
MACADDR=`cat /sys/class/net/eth0/address | cut -b 1,2,4,5,7,8,10,11,13,14,16,17`

if [ -f /tmp/backupdir ]
then
    BACKUPDIR=`cat /tmp/backupdir`
else
	for candidate in `cut -d ' ' -f 2 /proc/mounts | grep '^/media/'`
	do
		if [ -d ${candidate}/backup ]
		then
			if [ ! -f ${BACKUPDIR}/backup/.timestamp ]
			then
				BACKUPDIR=${candidate}
			elif [ -f ${BACKUPDIR}/backup/autoinstall${MACADDR} ]
			then
				break
			elif [ ${candidate}/backup/.timestamp -nt ${BACKUPDIR}/backup/.timestamp ]
			then
				BACKUPDIR=${candidate}
			fi
		fi
	done
	LOGFILE=${BACKUPDIR}/backup/autoinstall.log
fi

if [ -f ${BACKUPDIR}/backup/autoinstall${MACADDR} ]
then
	AUTOINSTALL=${BACKUPDIR}/backup/autoinstall${MACADDR}
else
	AUTOINSTALL=${BACKUPDIR}/backup/autoinstall
fi

IPKG=/usr/bin/opkg

${IPKG} list_installed | cut -d ' ' -f 1 > ${INSTALLED}
chmod 444 ${INSTALLED}

# when available, bind the console during autoinstall
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 1 > /sys/class/vtconsole/vtcon1/bind

if [ -f ${AUTOINSTALL} ]
then
	${IPKG} update 2>&1 | tee ${LOGFILE}
	sed 's/,/ /g' $AUTOINSTALL | while read packagefile packageoption ; do
		$IPKG install ${packageoption} $packagefile
	done 2>&1 | tee -a ${LOGFILE}
fi

# done, unbind the console
[ -f /sys/class/vtconsole/vtcon1/bind ] && echo 0 > /sys/class/vtconsole/vtcon1/bind

# suicide...
rm -f /etc/rc?.d/S*autoinstall*
