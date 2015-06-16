DESCRIPTION = "install RT2870STA.dat file for ralink drivers"

PV = "1.0.0"
PR = "r0"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SRC_URI = "file://RT2870STA.dat"

S = "${WORKDIR}/"

do_compile() {
        :
}

do_install() {
	install -d  ${D}/etc/Wireless/RT2870STA/
	install -m 0755 ${WORKDIR}/RT2870STA.dat ${D}/etc/Wireless/RT2870STA/
}
