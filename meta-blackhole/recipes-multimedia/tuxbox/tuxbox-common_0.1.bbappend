PR .= "-bh1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://cables.xml"




do_install_append() {
	install -d ${D}/etc/tuxbox/
	install -d ${D}/usr/share/tuxbox
	install -m 0644 ${S}/root/share/tuxbox/cables.xml ${D}/etc/tuxbox/cables.xml
	ln -sf /etc/tuxbox/cables.xml ${D}/etc/;
	ln -sf /etc/tuxbox/cables.xml ${D}/usr/share/;
	ln -sf /etc/tuxbox/cables.xml ${D}/usr/share/tuxbox/;
	
}
