###############################################################################
#                      FULL BACKUP UYILITY FOR  MIRACLEBOX                    #
#        Tools original by scope34 with additions by Dragon48 & DrData        #
#               modified by Pedro_Newbie (pedro.newbie@gmail.com)             #
#                       modified by meo & dpeddi                              #
###############################################################################
#
#!/bin/sh

START=$(date +%s)

DIRECTORY=$1
DATE=`date +%Y%m%d_%H%M`
IMAGEVERSION=`date +%Y%m%d`
ROOTFSTYPE=ubifs
MKFS=/usr/sbin/mkfs.ubifs
UBINIZE=/usr/sbin/ubinize
NANDDUMP=/usr/sbin/nanddump
WORKDIR=$DIRECTORY/bi
TARGET="XX"

MKUBIFS_ARGS="-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS="-m 2048 -p 128KiB"
	
if [ ! -f /usr/lib/libbz2.so.1.0 ] ; then
	#hack
	cp /usr/lib/libbz2.so.0 /usr/lib/libbz2.so.1.0
fi

if [ -f /proc/stb/info/boxtype ] ; then
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-8000sv' ]; then
		TYPE=miraclebox
		MODEL=ultra
		MTD=mtd2
		MKUBIFS_ARGS="-m 4096 -e 1040384 -c 1984"
		UBINIZE_ARGS="-m 4096 -p 1024KiB"
	elif [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000sv' ]; then
		TYPE=miraclebox
		MODEL=twin
		MTD=mtd2
	elif [ "$(cat /proc/stb/info/boxtype)" == 'ini-2000sv' ]; then
		TYPE=miraclebox
		MODEL=miniplus
		MTD=mtd2
	elif [ "$(cat /proc/stb/info/boxtype)" == '7000S' ]; then
		TYPE=miraclebox
		MODEL=mbmicro
		MTD=mtd2
	elif [ "$(cat /proc/stb/info/boxtype)" == 'g300' ]; then
		TYPE=miraclebox
		MODEL=twinplus
		MTD=mtd1
	elif [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000sv' ]; then
		TYPE=miraclebox
		MODEL=mini
		MTD=mtd2
	else
		echo "No supported receiver found!"
		exit 0
	fi
	SHOWNAME="$MODEL"
	MAINDEST=$DIRECTORY/$MODEL
	EXTRA=$DIRECTORY/OpenBlackHole_fullbackup_$MODEL/$DATE
else
	echo "No supported receiver found!"
	exit 0
fi

## START THE REAL BACK-UP PROCESS
echo "$SHOWNAME" | tr  a-z A-Z
echo "BACK-UP TOOL, FOR MAKING A COMPLETE BACK-UP"

echo " "
echo "Please be patient, ... will take about 5-7 minutes for this system."

echo " "

## TESTING IF ALL THE TOOLS FOR THE BUILDING PROCESS ARE PRESENT
if [ ! -f $MKFS ] ; then
	echo $MKFS; echo "not found."
	exit 0
fi
if [ ! -f $NANDDUMP ] ; then
	echo $NANDDUMP ;echo "not found."
	exit 0
fi

## PREPARING THE BUILDING ENVIRONMENT
rm -rf $WORKDIR
mkdir -p $WORKDIR
mkdir -p /tmp/bi/root
sync
mount --bind / /tmp/bi/root

echo "Create: root.ubifs"
echo \[ubifs\] > $WORKDIR/ubinize.cfg
echo mode=ubi >> $WORKDIR/ubinize.cfg
echo image=$WORKDIR/root.ubi >> $WORKDIR/ubinize.cfg
echo vol_id=0 >> $WORKDIR/ubinize.cfg
echo vol_type=dynamic >> $WORKDIR/ubinize.cfg
echo vol_name=rootfs >> $WORKDIR/ubinize.cfg
echo vol_flags=autoresize >> $WORKDIR/ubinize.cfg
touch $WORKDIR/root.ubi
chmod 644 $WORKDIR/root.ubi

$MKFS -r /tmp/bi/root -o $WORKDIR/root.ubi $MKUBIFS_ARGS
$UBINIZE -o $WORKDIR/root.ubifs $UBINIZE_ARGS $WORKDIR/ubinize.cfg

chmod 644 $WORKDIR/root.$ROOTFSTYPE

echo "Create: kerneldump"
$NANDDUMP /dev/$MTD -o > $WORKDIR/vmlinux.bin
gzip -f -9c $WORKDIR/vmlinux.bin > $WORKDIR/vmlinux.gz

echo " "
echo "Almost there... Now building the USB-Image!"

if [ $TYPE = "miraclebox" ] ; then
	echo "Detected Miraclebox, creating miraclbox dir"
	MAINDEST=$DIRECTORY/$TYPE/$MODEL
fi

rm -rf $MAINDEST
mkdir -p $MAINDEST

if [ $TYPE = "miraclebox" ] ; then
	mkdir -p $EXTRA/$TYPE/$MODEL
	mv $WORKDIR/root.ubifs $MAINDEST/rootfs.bin
	mv $WORKDIR/vmlinux.gz $MAINDEST/kernel.bin
	touch noforce $MAINDEST/
	cp -r $MAINDEST $EXTRA/$TYPE #copy the made back-up to images
	cp -r /etc/version $EXTRA/$TYPE/$MODEL/imageversion
	touch $EXTRA/$TYPE/$MODEL/noforce
	cd $EXTRA
	zip $DIRECTORY/OpenBlackHole_fullbackup_$MODEL/$DATE/openblackhole-$MODEL-image-$DATE-usb.zip $TYPE/$MODEL/*
fi

if [ -f $MAINDEST/rootfs.bin -a -f $MAINDEST/kernel.bin ] ; then
	echo " "
	echo "ZIP USB Image created in:";echo $EXTRA
	echo " "
	echo "To restore the image:"
	echo "Place the USB-flash drive in the (front) USB-port and switch the receiver off and on with the powerswitch on the back of the receiver."
	echo "Follow the instructions on the front-display."
	echo "Please wait.... almost ready!"

else
	echo "Image creation FAILED!"
	echo "Probable causes could be:"
	echo "-> no space left on back-up device"
	echo "-> no writing permission on back-up device"
	echo " "
fi

rm -rf $MAINDEST
umount /tmp/bi/root
rmdir /tmp/bi/root
rmdir /tmp/bi
rm -rf $WORKDIR
sleep 5
END=$(date +%s)
DIFF=$(( $END - $START ))
MINUTES=$(( $DIFF/60 ))
SECONDS=$(( $DIFF-(( 60*$MINUTES ))))
echo "Time required for this process:" ; echo "$MINUTES:$SECONDS"
exit 