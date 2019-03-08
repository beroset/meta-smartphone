#!/bin/sh -e

PREREQS=""

prereqs() { echo "$PREREQS"; }

case "$1" in
    prereqs)
    prereqs
    exit 0
    ;;
esac

if [ -w /sys/kernel/uevent_helper ]; then
	echo > /sys/kernel/uevent_helper
fi

if [ "$quiet" = "y" ]; then
	log_level=notice
else
	log_level=info
fi

SYSTEMD_LOG_LEVEL=$log_level /lib/systemd/systemd-udevd --daemon --resolve-names=never

udevadm trigger --action=add
udevadm settle || true

# Leave udev running to process events that come in out-of-band (like USB
# connections)
