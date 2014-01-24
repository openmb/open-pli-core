DESCRIPTION = "mgcamd ${PV} softcam"
CAMNAME = "mgcamd"

RDEPENDS_${PN} = "libcrypto-compat"

PR = "r7"

SRC_URI = "http://168.144.9.19/source/mgcamd${PV}.zip"

S = "${WORKDIR}/mgcamd${PV}"

INHIBIT_PACKAGE_STRIP = "1"

CAMSTART = "sleep 3 ; start-stop-daemon -S -b -x /usr/bin/${CAMNAME}"

require softcam.inc

do_install() {
	install -d ${D}/usr/lib
	
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

SRC_URI[md5sum] = "7bd64396bf439376aed4f2434c85ed07"
SRC_URI[sha256sum] = "64436fab6891aec47a300806f080c36716bc86917e23a6f021f264dd08828519"
