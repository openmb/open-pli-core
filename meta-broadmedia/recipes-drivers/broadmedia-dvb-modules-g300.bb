SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "3.17.8"
SRCDATE = "20150518"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "be648e09c4a65337b427a00da5ea48e3"
SRC_URI[sha256sum] = "7c274e86a5ae8b5e4c24440bd0d3ddc3f5504d4428fcab91bbf6e69cf64c4be9"

SRC_URI = "http://source.mynonpublic.com/broadmedia/g300-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0755 ${WORKDIR}/linuxtv.ko ${D}/lib/modules/${KV}/extra
    echo linuxtv >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
