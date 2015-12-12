# We know about rtc
MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc"

BRAND_OEM = "ini"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
	kernel-module-cdfs \
	"

MACHINE_EXTRA_RRECOMMENDS = " \
	gst-plugin-dvbmediasink \
	"

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-pcm"

TARGET_ARCH = "mipsel"
DEFAULTTUNE ?= "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_prepend = " \
	rm -rf ${IMAGE_ROOTFS}/boot/*; \
	rm -rf ${IMAGE_ROOTFS}/tmp/*; \
	"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi 3dtvpli bootsplash kernelwifi"

BOOTLOGO_FILENAME = "splash.bin"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ini-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ini-blindscan-utils"

require conf/machine/include/tune-mips32.inc

