do_install () {
	install -d ${D}/lib
	ln -s libcrypto.so.0.9.8 ${D}/lib/libcrypto.so.0.9.7
	install -d ${D}/usr/lib
	ln -s libssl.so.0.9.8 ${D}/usr/lib/libssl.so.0.9.7
}

FILES_${PN} = "/usr/lib/ /lib/"
