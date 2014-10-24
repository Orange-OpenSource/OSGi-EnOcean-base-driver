Functional and technical descriptions:

- The OSGi EnOcean Base Driver enables applications running on an OSGi software platform to discover and control EnOcean devices. EnOcean is a radio protocol used in Smart Home and Building markets. OSGi is an execution environment above Java virtual machine to run modular applications.

- The OSGi EnOcean Base Driver implements OSGi standard specification entitled EnOcean Device Service. OSGi EnOcean Device Service specification defines the Java API to discover and control EnOcean devices on the OSGi platform and according to OSGi service design patterns. This API maps the representation model of EnOcean entities defined by EnOcean Equipment Profiles standard into Java classes. OSGi service design patterns are used on the one hand for dynamic discovery, control and eventing of local and networked devices and on the other hand for dynamic network advertising and control of local OSGi services.

- The EnOcean Base Driver is entirely written in Java and uses serial communication drivers usually available on common OS, e.g., Linux, Windows. To work properly, the software needs to be installed on an OSGi platform R4 v4.0 (and above) running on a JVM 1.4 (and above) with RXTX Java library. It runs above Linux and Windows systems connected to an EnOcean TCM300 USB Gateway delivered as an internal chip or a USB dongle.


Tree
----

    .
    ├── rxtx                             ; see https://github.com/Orange-OpenSource/Driver-RX-TX
                                         ; It's a generic interface bundle to the Serial port.
 
    ├── com.orange.impl.service.enocean  ; This bundle is the EnOcean Base Driver itself. This 
                                         ; version contains an EnOceanHost Implementation that 
                                         ; is able to communicate over the serial port.
                                         ; see https://github.com/osgi/design/tree/master/rfcs/rfc0199
 
    ├── com.orange.sample.enocean.client ; This is a very simple sample application for using the 
                                         ; EnOcean base driver. It subscribes to both service and 
                                         ; event notifications regarding EnOcean events on OSGi.

    └── README.md


Authors
-------
- Victor PERRON
- Mailys ROBIN
- Andre BOTTARO
- Antonin CHAZALET


Quick Start
-----------
* Get the code (e.g. in Eclipse).
* Compile it (via bnd).
* Get Apache Felix (e.g. version 4.4.1).
* Add the following bundles in the "bundle" folder of Felix:


```
#!shell
g! lb
START LEVEL 1
   ID|State      |Level|Name
    0|Active     |    0|System Bundle (4.4.1) - System Bundle
    1|Active     |    1|com.orange.sample.enocean.client (0.0.0) - com.orange.sample.enocean.client.jar
    2|Active     |    1|Apache Felix Bundle Repository (2.0.2) - org.apache.felix.bundlerepository-2.0.2.jar
    3|Active     |    1|Apache Felix EventAdmin (1.3.2) - org.apache.felix.eventadmin-1.3.2.jar
    4|Active     |    1|Apache Felix Gogo Command (0.14.0) - org.apache.felix.gogo.command-0.14.0.jar
    5|Active     |    1|Apache Felix Gogo Runtime (0.12.1) - org.apache.felix.gogo.runtime-0.12.1.jar
    6|Active     |    1|Apache Felix Gogo Shell (0.10.0) - org.apache.felix.gogo.shell-0.10.0.jar
    7|Active     |    1|Apache Felix Log Service (1.0.1) - org.apache.felix.log-1.0.1.jar
    8|Active     |    1|org.osgi.impl.service.enocean (0.0.0) - org.osgi.impl.service.enocean.jar
    9|Active     |    1|org.osgi.service.enocean (6.0.0.201407091519) - org.osgi.service.enocean-6.0.0.jar
   10|Active     |    1|org.osgi.test.cases.enoceansimulation (6.0.0.201407091522) - org.osgi.test.cases.enoceansimulation-6.0.0.jar
   11|Active     |    1|osgi.cmpn (4.2.0.200908310645) - osgi.cmpn-4.2.0.jar
   12|Active     |    1|comm.rxtx (0.0.0) - comm.rxtx.jar

```
* Start Felix.
