DEPENDS += "libxml2 zlib python swig-native curl"

PV = "0.8.1+gitr${SRCPV}"
PKGV = "0.8.1+gitr${GITPKGV}"
PR = "r2"
PR .= "-bh2"
PRINC = "0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# SRC_REV = "29e25119934893653f098434adc584fc7e84d0d6"
# tag=${SRC_REV}

SRC_URI = "git://github.com/oe-alliance/e2openplugin-CrossEPG.git;protocol=git \
		file://skyit_hotbird_13.0.conf"

# Dunno why, but it sometime fails to build in parallel
PARALLEL_MAKE = ""
CFLAGS_append = " -I${STAGING_INCDIR}/libxml2/ -I${STAGING_INCDIR}/${PYTHON_DIR}/"

S = "${WORKDIR}/git"

do_compile() {
	echo ${PV} > ${S}/VERSION
	oe_runmake SWIG="swig"
}

do_install() {
	oe_runmake 'D=${D}' install
	install -m 0644 ${WORKDIR}/skyit_hotbird_13.0.conf ${D}/usr/crossepg/providers/skyit_hotbird_13.0.conf
}

pkg_postrm_${PN}() {
rm -fr /usr/lib/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
}

# Just a quick hack to "compile" the python parts.
do_compile_append() {
    python -O -m compileall ${S}
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

FILES_${PN}_append = " /usr/crossepg /usr/lib/python2.7"
FILES_${PN}-src_append = " /usr/lib/python2.7/crossepg.py"
FILES_${PN}-dbg_append = " /usr/crossepg/scripts/mhw2epgdownloader/.debug"
FILES_${PN}-dbg += "/usr/crossepg/scripts/mhw2epgdownloader/.debug"
