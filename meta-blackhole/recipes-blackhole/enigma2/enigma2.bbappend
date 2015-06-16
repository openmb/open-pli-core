FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= "-bh9"

RDEPENDS_${PN} += " \
		bhfullbackup \
		enigma2-plugin-extensions-openmultiboot \
	"

ENIGMA2_BRANCH = "master"

SRC_URI = "git://github.com/openmb/openblackhole-enigma2.git;protocol=git;branch=${ENIGMA2_BRANCH} \
			file://skindefault.tgz \
			"

EXTRA_OECONF += "${@base_contains("MACHINE_FEATURES", "uianimation", "--with-libvugles2" , "", d)}"

DEPENDS += "${@base_contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)}"
RDEPENDS_append_vuplus += "${@base_contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)}"
RDEPENDS_${PN} += "${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "virtual/blindscan-dvbs" , "", d)}"


