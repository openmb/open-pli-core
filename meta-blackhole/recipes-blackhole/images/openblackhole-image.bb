require ${OPENPLI_BASE}/meta-blackhole/recipes-blackhole/images/openblackhole-base-image.bb

KERNEL_WIFI_DRIVERS = " \
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-rt2870 \
	firmware-rt73 \
	firmware-rtl8712u \
	firmware-zd1211 \
	\
	kernel-module-ath9k-htc \
	kernel-module-carl9170 \
	kernel-module-r8712u \
	kernel-module-rt2500usb \
	kernel-module-rt2800usb \
	kernel-module-rt73usb \
	kernel-module-rtl8187 \
	kernel-module-zd1211rw \
	kernel-module-prism2-usb \
	kernel-module-hostap \
	"

EXTRA_KERNEL_WIFI_DRIVERS = " \
	firmware-rtl8192cu \
	\
	kernel-module-r8188eu \
	kernel-module-rtl8192cu \
	"

EXTERNAL_WIFI_DRIVERS = " \
	firmware-rtl8192cu \
	\
	rtl8192cu \
	rtl8188eu \
	"

ENIGMA2_PLUGINS = " \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-dlnaserver \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-autobackup \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-extensions-pictureplayer \
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-skinselector \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-videomode \
	enigma2-plugin-systemplugins-autoresolution \
  	enigma2-plugin-systemplugins-commoninterfaceassignment \
	enigma2-plugin-systemplugins-osd3dsetup \
	enigma2-plugin-systemplugins-hdmicec \
	enigma2-plugin-systemplugins-osdpositionsetup \
	enigma2-plugin-systemplugins-wirelesslan \
	${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan", "", d)} \
	${@base_contains("MACHINE_FEATURES", "xbmc", "enigma2-plugin-extensions-xbmc", "", d)} \
	\
	${@base_conditional("MACHINE", "vusolo2", "enigma2-plugin-extensions-hbbtv", "", d)} \
	${@base_conditional("MACHINE", "vuduo2", "enigma2-plugin-extensions-hbbtv", "", d)} \
	${@base_conditional("MACHINE", "vusolose", "enigma2-plugin-extensions-hbbtv", "", d)} \
  	${@base_conditional("MACHINE", "vuzero", "enigma2-plugin-extensions-hbbtv", "", d)} \
	"

DVB_USB_DRIVERS = " \
	enigma2-plugin-drivers-dvb-usb-dib0700 \
	enigma2-plugin-drivers-dvb-usb-af9015 \
	enigma2-plugin-drivers-dvb-usb-af9035 \
	enigma2-plugin-drivers-dvb-usb-siano \
	enigma2-plugin-drivers-dvb-usb-em28xx \
	enigma2-plugin-drivers-dvb-usb-dw2102 \
	enigma2-plugin-drivers-dvb-usb-as102 \
	${@base_contains('PREFERRED_VERSION_linux-ceryon', '3.14.2', 'enigma2-plugin-drivers-dvb-usb-it913x', '', d)} \
	enigma2-plugin-drivers-dvb-usb-pctv452e \
	enigma2-plugin-drivers-dvb-usb-dtt200u \
	enigma2-plugin-drivers-dvb-usb-rtl2832 \
	${@base_contains('PREFERRED_VERSION_linux-vuplus', '3.13.5', 'kernel-module-cypress-firmware', '', d)} \
	${@base_contains('PREFERRED_VERSION_linux-vuplus', '3.9.6', 'kernel-module-dvb-usb-cypress-firmware', '', d)} \
	"

IMAGE_INSTALL += " \
	3rd-party-feed-configs \
	aio-grab \
	blackhole-base \
	blackholesocker \
	blackhole-bootlogo \
	oe-alliance-branding \
	mc \
	openvpn \
	parted \
	enigma2 \
	libavahi-client \
	tuxbox-common \
	ntfs-3g \
	libcrypto-compat \
	\
	python-shell python-compression python-json python-html python-textutils python-subprocess \
	\
	${ENIGMA2_PLUGINS} \
	\
	${@base_contains("MACHINE_FEATURES", "tpm", "tpmd", "", d)} \
	${@base_contains("MACHINE_FEATURES", "transcoding", "streamproxy", "", d)} \
	${@base_contains('MACHINE_FEATURES', 'ctrlrc', "enigma2-plugin-systemplugins-remotecontrolcode", "", d)} \
	${@base_contains("MACHINE_FEATURES", "kernelwifi", "${KERNEL_WIFI_DRIVERS}", "", d)} \
	${@base_contains("MACHINE_FEATURES", "extrakernelwifi", "${EXTRA_KERNEL_WIFI_DRIVERS}", "", d)} \
	${@base_contains("MACHINE_FEATURES", "externalwifi", "${EXTERNAL_WIFI_DRIVERS}", "", d)} \
	${DVB_USB_DRIVERS} \
	\
	${@base_contains('MACHINE_FEATURES', 'dvd', 'cdfs cdtextinfo kernel-module-isofs kernel-module-udf', '', d)} \
	python-argparse \
	"

ENIGMA2_PLUGINS += " \
	enigma2-plugin-systemplugins-crossepg \
	"

export IMAGE_BASENAME = "openblackhole"
