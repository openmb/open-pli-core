FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= "-bh1"

do_install_append () {
	ln -s /etc/tuxbox ${D}/var/
}

FILES_${PN} = "/"
