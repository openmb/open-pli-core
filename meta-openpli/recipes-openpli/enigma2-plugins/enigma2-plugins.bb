DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "OpenPLi team <info@openpli.org>"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PACKAGES_DYNAMIC = "enigma2-plugin-(?!pli-).*"

# This prevents QA warnings because bitbake cannot see the dependencies
# after parsing the recipe due to the PACKAGES_DYNAMIC stuff. It tells
# the system what to build when installing these into an image.
PACKAGES += "\
	enigma2-plugin-extensions-mosaic \
	enigma2-plugin-extensions-fancontrol2 \
	enigma2-plugin-extensions-bonjour \
	"
RDEPENDS_enigma2-plugin-extensions-mosaic = "aio-grab"
RDEPENDS_enigma2-plugin-extensions-fancontrol2 = "smartmontools hdparm"
RDEPENDS_enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
RDEPENDS_enigma2-plugin-extensions-bonjour = "avahi-daemon"

PROVIDES += "\
	${@base_contains("MACHINE_FEATURES", "transcoding","enigma2-plugin-systemplugins-transcodingsetup","",d)} \
"

inherit gitpkgv pythonnative pkgconfig

PV = "y-git${SRCPV}"
PKGV = "y-git${GITPKGV}"

SRCREV = "${AUTOREV}"
GITHUB_URI ?= "git://github.com"
SRC_URI = "${GITHUB_URI}/OpenPLi/${BPN}.git"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--without-debug \
"

# Main package should be empty
FILES_${PN} = ""
# But something makes the packages think they depend on it, so just
# deliver an empty hulk for them.
ALLOW_EMPTY_${PN} = "1"

FILES_enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"
CONFFILES_enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"

FILES_enigma2-plugin-extensions-babelzapper += "${sysconfdir}/babelzapper"

FILES_enigma2-plugin-extensions-netcaster += "${sysconfdir}/NETcaster.conf"
CONFFILES_enigma2-plugin-extensions-netcaster += "${sysconfdir}/NETcaster.conf"

FILES_${PN}-meta = "${datadir}/meta"
PACKAGES += "${PN}-meta"

inherit autotools-brokensep

S = "${WORKDIR}/git"

DEPENDS = " \
	python-pyopenssl \
	streamripper \
	python-mutagen \
	python-twisted \
	python-daap \
	libcddb \
	dvdbackup \
	"

python populate_packages_prepend () {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

    do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True, extra_depends='')

    def getControlLines(mydir, d, package):
        import os
        try:
            src = open(mydir + package + "/CONTROL/control").read()
        except Exception, ex:
            bb.note("Failed to get control lines for package '%s': %s" % (package, ex))
            return
        for line in src.split("\n"):
            full_package = "enigma2-plugin-extensions-" + package
            if line.startswith('Package: '):
                full_package = line[9:]
            elif line.startswith('Depends: '):
                # some plugins still reference twisted-* dependencies, these packages are now called python-twisted-*
                rdepends = []
                for depend in line[9:].split(','):
                    depend = depend.strip()
                    if depend.startswith('twisted-'):
                        rdepends.append(depend.replace('twisted-', 'python-twisted-'))
                    elif depend.startswith('enigma2') and not depend.startswith('enigma2-'):
                        pass # Ignore silly depends on enigma2 with all kinds of misspellings
                    else:
                        rdepends.append(depend)
                rdepends = ' '.join(rdepends)
                bb.data.setVar('RDEPENDS_' + full_package, rdepends, d)
            elif line.startswith('Recommends: '):
                bb.data.setVar('RRECOMMENDS_' + full_package, line[12:], d)
            elif line.startswith('Description: '):
                bb.data.setVar('DESCRIPTION_' + full_package, line[13:], d)
            elif line.startswith('Replaces: '):
                bb.data.setVar('RREPLACES_' + full_package, ' '.join(line[10:].split(', ')), d)
            elif line.startswith('Conflicts: '):
                bb.data.setVar('RCONFLICTS_' + full_package, ' '.join(line[11:].split(', ')), d)
            elif line.startswith('Maintainer: '):
                bb.data.setVar('MAINTAINER_' + full_package, line[12:], d)


    mydir = bb.data.getVar('D', d, 1) + "/../git/"
    for package in bb.data.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-')[-1])
}

do_install_append() {
	# remove unused .pyc files
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
	# remove leftover webinterface garbage
	${@base_contains('MACHINE_FEATURES', 'tpm','','rm -rf ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface',d)}
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
}

# Nothing of this recipe should end up in sysroot, so blank it away.
sysroot_stage_all() {
    :
}
