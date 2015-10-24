DESCRIPTION = "Imports xmltv files into the EPG cache of enigma2"
MAINTAINER = "MiLo, rytec @ pli-images.org"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../etc/epgimport/readme.txt;startline=1;endline=4;md5=c162054328d930d453543efef81be1d8"

inherit gitpkgv

SRCREV = "828705fbed61f3bfa48a33222957e14be7b2187e"

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

GITHUB_URI ?= "git://github.com"
SRC_URI = "${GITHUB_URI}/OpenPLi/${BPN}.git"

S = "${WORKDIR}/git/src"

inherit distutils

DEPENDS = "python"
RDEPENDS_${PN} = "python-compression python-shell"
RRECOMMENDS_${PN} = "${PN}-rytec"
PACKAGES = "${PN}-dbg ${PN}"

PLUGIN = "EPGImport"

FILES_${PN} = "/usr/bin /usr/lib/enigma2/python"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/.debug /usr/src/debug"

DISTUTILS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=${libdir}/enigma2/python/Plugins/Extensions \
    "

do_install_append() {
	install -m 644 ${S}/EPGImport/plugin.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
	mv ${D}${libdir}/enigma2/python/Plugins/Extensions/*.so ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
	rm ${D}${libdir}/enigma2/python/Plugins/Extensions/*.egg-info
	# Remove files we don't want to deploy
	rm -r ${D}${libdir}/${PYTHON_DIR}
}

# skip this!
install_egg_info() {
}


pkg_postinst_${PN}() {

	if [ ! -f $D/etc/image-version ]
	then
		# when slipstreaming, don't patch enigma
		exit 0
	fi
	if grep -q PLi $D/etc/image-version
	then
		# PLi needs no patch...
		true
	else
		[ -f $D/usr/bin/enigma2.sh.xmltvbak ] || {
			cp $D/usr/bin/enigma2.sh $D/usr/bin/enigma2.sh.xmltvbak
			sed '3ipython $D/usr/lib/enigma2/python/Plugins/Extensions/EPGImport/boot.py' $D/usr/bin/enigma2.sh.xmltvbak > $D/usr/bin/enigma2.sh
		}
	fi
}

pkg_prerm_${PN}() {
	if [ -f /usr/bin/enigma2.sh.xmltvbak ] ; then
		mv -f /usr/bin/enigma2.sh.xmltvbak /usr/bin/enigma2.sh
	fi
}
