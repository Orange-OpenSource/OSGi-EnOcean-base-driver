Functional and technical descriptions:

- The OSGi EnOcean Base Driver enables applications running on an OSGi software platform to discover and control EnOcean devices. EnOcean is a radio protocol used in Smart Home and Building markets. OSGi is an execution environment above Java virtual machine to run modular applications.

- The OSGi EnOcean Base Driver implements OSGi standard specification entitled EnOcean Device Service. OSGi EnOcean Device Service specification defines the Java API to discover and control EnOcean devices on the OSGi platform and according to OSGi service design patterns. This API maps the representation model of EnOcean entities defined by EnOcean Equipment Profiles standard into Java classes. OSGi service design patterns are used on the one hand for dynamic discovery, control and eventing of local and networked devices and on the other hand for dynamic network advertising and control of local OSGi services.

- The EnOcean Base Driver is entirely written in Java and uses serial communication drivers usually available on common OS, e.g., Linux, Windows. To work properly, the software needs to be installed on an OSGi platform R4 v4.0 (and above) running on a JVM 1.4 (and above) with RXTX Java library. It runs above Linux and Windows systems connected to an EnOcean TCM300 USB Gateway delivered as an internal chip or a USB dongle.
