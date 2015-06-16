# Creates the "feed", packages not required for the image
# but that should be built for the feed so that other
# components may use them and install on demand.

# Trick: We want to create the package index, and we don't actually
# package anything, so we "inherit" the package indexer recipe.
require recipes-core/meta/package-index.bb

# We have a GPLv2 license for this recipe...
require conf/license/openblackhole-gplv2.inc

# Depend on the image, so that it gets build
DEPENDS = "openblackhole-image"

OPTIONAL_PACKAGES_BROKEN = "samba"
OPTIONAL_PACKAGES ?= ""
OPTIONAL_BSP_PACKAGES ?= ""
OPTIONAL_PACKAGES += " \
	autofs \
	autossh \
	ctorrent \
	cups \
	dvbsnoop \
	dvdfs \
	evtest \
	gdb \
	hddtemp \
	hdparm \
	iperf \
	joe \
	mpd \
	nano \
	ntp \
	ofgwrite \
	openresolv \
	openssh \
	openvpn \
	procps \
	pyload \
	python-requests \
	python-mechanize \
	rsync \
	rtorrent \
	sabnzbd \
	sshpass \
	smartmontools \
	smbnetfs \
	strace \
	tcpdump \
	transmission \
	udpxy \
	vim \
	xfsprogs \
	zeroconf \
	${OPTIONAL_BSP_PACKAGES} \
	"

OPTIONAL_BSP_ENIGMA2_PACKAGES ?= ""
ENIGMA2_OPTIONAL = " \
	channelsettings-enigma2-meta \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-extensions-modifyplifullhd \
	enigma2-plugin-security-firewall \
	enigma2-plugins \
	picons-enigma2-meta \
	packagegroup-openplugins \
	cdfs cdtextinfo \
	meta-enigma2-dvdburn \
	${@base_contains("MACHINE_FEATURES", "hbbtv", "enigma2-plugin-extensions-hbbtv", "", d)} \
	${OPTIONAL_BSP_ENIGMA2_PACKAGES} \
	"

DEPENDS += "${OPTIONAL_PACKAGES} ${ENIGMA2_OPTIONAL}"
