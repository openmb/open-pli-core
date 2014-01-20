DESCRIPTION = "OE-A Branding Lib for OE-A 1.0"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "python"

require conf/license/openpli-gplv2.inc

inherit gitpkgv autotools

SRCREV = "${AUTOREV}"
PV = "0.2+git${SRCPV}"
PKGV = "0.2+git${GITPKGV}"
PR = "r${DATETIME}"

SRC_URI="git://github.com/oe-alliance/branding-module.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-distro=${DISTRO_NAME} \
    --with-boxtype=${MACHINEBUILD} \
    --with-machineoem="${MACHINE_OEM}" \
    --with-machinebrand="${MACHINE_BRAND}" \
    --with-machinename="${MACHINE_NAME}" \
    --with-imageversion=${DISTRO_VERSION} \
    --with-imagebuild=${BUILD_VERSION} \
    --with-driverdate=${DRIVERSDATE} \
    "

do_configure_prepend() {
    if [ "${MACHINE}" = "mbmini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/miraclebox/ini-dvb-modules-inihde.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "inihdp" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/miraclebox/ini-dvb-modules-inihdp.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "mbtwin" -o "${MACHINE}" = "mbtwin" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/miraclebox/ini-dvb-modules-inihdx.bb | cut -b 12-19`
    else
        DRIVERSDATE='N/A'
    fi
}

FILES_${PN} += "/usr/lib/enigma2/python/*.so"
FILES_${PN}-dev += "/usr/lib/enigma2/python/*.la"
FILES_${PN}-staticdev += "/usr/lib/enigma2/python/*.a"
FILES_${PN}-dbg += "/usr/lib/enigma2/python/.debug"

