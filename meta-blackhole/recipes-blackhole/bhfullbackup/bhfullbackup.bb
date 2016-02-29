DESCRIPTION = "Black Hole Full Backup"
MAINTAINER = "Meo <lupomeo@gmail.com>"
require conf/license/openblackhole-gplv2.inc

RDEPENDS_${PN} += "mtd-utils mtd-utils-ubifs enigma2"

SRC_URI = " \
	file://__init__.py file://plugin.py file://bh_backup_full.sh \
"
PV = "3.1"
PR = "r11"

S = "${WORKDIR}/"

FILES_${PN} = "/"



do_install() {
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/BhFullBackup

	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/BhFullBackup
	install -m 0644 ${WORKDIR}/__init__.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/BhFullBackup/__init__.py
	install -m 0644 ${WORKDIR}/plugin.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/BhFullBackup/plugin.py

	install -d ${D}/usr/bin
	install -m 0755 ${WORKDIR}/bh_backup_full.sh ${D}/usr/bin/bh_backup_full.sh

}