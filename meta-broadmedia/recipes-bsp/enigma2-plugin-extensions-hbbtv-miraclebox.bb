DESCRIPTION = "Miraclebox HbbTv"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://enigma2-plugin-extensions-hbbtv-miraclebox.tar.gz"

inherit gitpkgv

PR = "r1"

SRCREV = "${AUTOREV}"

FILES_${PN} = "/usr"

INHIBIT_PACKAGE_STRIP = "1"

ALLOW_EMPTY_${PN} = "1"

S="${WORKDIR}/usr"

do_install() {
	install -d ${D}/usr
	cp -a ${S}/* ${D}/usr/
}