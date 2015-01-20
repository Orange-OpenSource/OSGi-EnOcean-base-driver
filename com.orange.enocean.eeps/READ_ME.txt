
READ_ME

Pre-requisites:
org.osgi.service.enocean-1.0.0.jar has not been released as a mvn release yet. So it must be manually installed in your local repo.

2 options :
1) mvn clean (and everything will be installed properly).

2) The .jar is available at: cnf\buildrepo\org.osgi.service.enocean\org.osgi.service.enocean-1.0.0.jar
The mvn command line to install it is: 
mvn install:install-file -Dfile=org.osgi.service.enocean-1.0.0.jar -DgroupId=org.osgi.service -DartifactId=enocean -Dversion=1.0.0.201501061602 -Dpackaging=jar
