DESCRIPTION = "ReadyMedia DLNA server (formerly aka MiniDLNA)"
SUMMARY = "lightweight DLNA/UPnP-AV server targeted at embedded systems"
HOMEPAGE = "http://sourceforge.net/projects/minidlna"
SECTION = "multimedia"
MAINTAINER = "OpenPLi"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENCE.miniupnpd;md5=b0dabf9d8e0f871554e309d62ead8d2b"

inherit gitpkgv
SRCREV = "bb9d584a86d058bdc410e81a0e498ee461e64f9b"
PV = "1.1.0+git${SRCPV}"
PKGV = "1.1.0+git${GITPKGV}"
PR = "r0"
DEPENDS = "libexif"

SRC_URI = "git://git.code.sf.net/p/minidlna/git;protocol=git \
			file://readymedia.sh \
			file://minidlna.conf \
"
S = "${WORKDIR}/git"

CONFFILES_${PN} = "/etc/minidlna.conf"

inherit autotools pkgconfig gettext update-rc.d

INITSCRIPT_NAME = "readymedia.sh"
INITSCRIPT_PARAMS = "stop 00 0 6 ."

do_install_append() {
	install -m 755 -d ${D}/etc/
	install -m 644 ${WORKDIR}/minidlna.conf ${D}/etc/
	install -m 755 -d ${D}/etc/init.d/
	install -m 755 ${WORKDIR}/readymedia.sh ${D}/etc/init.d/
	install -m 755 -d ${D}/var/lib/readymedia/
}
