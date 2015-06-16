DESCRIPTION = "Open BlackHole extra files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Black Hole team"

require conf/license/openblackhole-gplv2.inc

SRC_URI = "file://Ncam_Ci.sh file://StartBhCam file://Delete_all_Crashlogs.sh file://Ifconfig.sh \
	file://openvpn_script.sh file://client.confOff file://clientp2p.confOff file://server.confOff \
	file://serverp2p.conf file://openvpn.log file://ca.crt file://client1.crt file://client1.key \
	file://client2.crt file://client2.key file://client3.crt file://client3.key file://dh1024.pem \
	file://delite.key file://server.crt file://server.key \
	file://Netstat.sh file://Uptime.sh file://inadyn file://inadyn_script.sh"

PR = "r4"

FILES_${PN} = "/"

do_compile() {
	echo "${MACHINE}" > ${WORKDIR}/bpmachine
	echo "${DISTRO_NAME} ${DISTRO_VERSION}" > ${WORKDIR}/bpversion
}


do_install() {

	mkdir -p ${D}/usr/camscript
	mkdir -p ${D}/usr/script
	mkdir -p ${D}/usr/uninstall

	install -d ${D}/etc
	install -m 0644 ${WORKDIR}/bpmachine ${D}/etc/bpmachine
	install -m 0644 ${WORKDIR}/bpversion ${D}/etc/bpversion
	

	install -d ${D}/usr/bin
	for x in /inadyn inadyn_script.sh openvpn_script.sh StartBhCam; do
		install -m 0755 ${WORKDIR}/$x ${D}/usr/bin/$x
	done
	

	install -d ${D}/usr/camscript
	install -m 0755 ${WORKDIR}/Ncam_Ci.sh ${D}/usr/camscript/Ncam_Ci.sh
	
	install -d ${D}/usr/script
	for x in /Delete_all_Crashlogs.sh Ifconfig.sh Netstat.sh Uptime.sh; do
		install -m 0755 ${WORKDIR}/$x ${D}/usr/script/$x
	done
	
	install -d ${D}/etc/openvpn
	for x in /client.confOff clientp2p.confOff openvpn.log server.confOff serverp2p.conf; do
		install -m 0644 ${WORKDIR}/$x ${D}/etc/openvpn/$x
	done

	install -d ${D}/etc/openvpn/keys
	for x in /ca.crt client1.crt client1.key client2.crt client2.key client3.crt \
	client3.key dh1024.pem delite.key server.crt server.key; do
		install -m 0644 ${WORKDIR}/$x ${D}/etc/openvpn/keys/$x
	done
	
	install -d ${D}/etc/rc3.d
	ln -s /usr/bin/openvpn_script.sh ${D}/etc/rc3.d/S40openvpn
	ln -s /usr/bin/inadyn_script.sh ${D}/etc/rc3.d/S40inadyn

	install -d ${D}/etc/rc4.d
	ln -s /usr/bin/openvpn_script.sh ${D}/etc/rc4.d/K40openvpn
	ln -s /usr/bin/inadyn_script.sh ${D}/etc/rc4.d/K40inadyn

}
