# build package busybox-mdev, needed by initramfs scripts
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://mdev.cfg \
    file://mdev-partlabel.sh \
"

do_install_append() {
    install -m 0755 ${WORKDIR}/mdev-partlabel.sh ${D}${sysconfdir}/mdev
}
