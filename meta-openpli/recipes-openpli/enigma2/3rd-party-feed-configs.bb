DESCRIPTION = "Configuration files for 3rd-party feeds"
PR = "r3"

require conf/license/openpli-gplv2.inc

# Use the PLi download server, regardless of where we are. Even for "private" feeds,
# the 3rd party plugins originate here.
DISTRO_HOST = "downloads.pli-images.org"
FEEDS = "3rd-party 3rd-party-${MACHINE}"

do_compile() {
    [ ! -d ${S}/${sysconfdir}/opkg ] && mkdir -p ${S}/${sysconfdir}/opkg
    echo "src/gz openpli-3rd-party http://downloads.pli-images.org/feeds/openpli-4/3rd-party" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
    echo "src/gz openpli-3rd-party-et10000 http://downloads.pli-images.org/feeds/openpli-4/3rd-party-et10000" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
    echo "src/gz openpli-3rdparty http://feeds.mynonpublic.com/5.0/inihdp/3rdparty" > ${S}/${sysconfdir}/opkg/3rdparty-feed.conf
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

CONFFILES_${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "${FEEDS}".split() ] ) }'
