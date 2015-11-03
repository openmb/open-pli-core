DESCRIPTION = "SAT>IP server"
MAINTAINER = "PLi team"
require conf/license/openpli-gplv2.inc

inherit gitpkgv

SRCREV = "81f519fa5346968906c3348d069d0fcddd4b62c2"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"

SRC_URI = "git://github.com/eriksl/minisatip.git;protocol=git"
FILES_${PN} = "/usr/sbin/minisatip"
S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/build"

inherit autotools

do_install() {
	install -m 755 -d ${D}/usr/sbin
	install -m 755 ${BUILD}/minisatip ${D}/usr/sbin
}
