DESCRIPTION = "mgcamd ${PV} softcam"
CAMNAME = "mgcamd"

PR = "r12"

SRC_URI = "http://168.144.9.19/source/mgcamd${PV}.zip"

S = "${WORKDIR}/mgcamd${PV}"

INHIBIT_PACKAGE_STRIP = "1"

CAMSTART = "sleep 3 ; start-stop-daemon -S -b -x /usr/bin/${CAMNAME}"

require softcam.inc


do_install() {
	install -d ${D}/usr/lib
	cp -a ${S}/libcrypto.so.0.9.7 ${D}/usr/lib/libcrypto.so.0.9.7
	
	install -d ${D}/usr/bin
	install -m 0755 ${S}/mgcamd.mips ${D}/usr/bin/mgcamd
	
	install -d ${D}/usr/keys
	install -m 0644 ${S}/mg_cfg ${D}/usr/keys/mg_cfg
	install -m 0644 ${S}/newcamd.list ${D}/usr/keys/newcamd.list
	install -m 0644 ${S}/cccamd.list ${D}/usr/keys/cccamd.list
	install -m 0644 ${S}/priority.list ${D}/usr/keys/priority.list
	install -m 0644 ${S}/replace.list ${D}/usr/keys/replace.list
	install -m 0644 ${S}/peer.cfg ${D}/usr/keys/peer.cfg
	install -m 0644 ${S}/SoftCam.Key ${D}/usr/keys/SoftCam.Key
}

FILES_${PN} += "/usr/keys"
FILES_${PN} += "/usr/lib"

SRC_URI[md5sum] = "e05685f39cc2bfddeea8f322ff6e8ca0"
SRC_URI[sha256sum] = "03865d8acb346bed2c3e6c20973bb06c41c7d674898ced71544572cbdb0c3200"
