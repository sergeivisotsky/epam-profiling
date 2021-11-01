FROM openjdk:11-jdk
EXPOSE 9010
ENV JAVA_TOOL_OPTIONS "-Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.port=9010 \
 -Dcom.sun.management.jmxremote.rmi.port=9010 \
 -Dcom.sun.management.jmxremote.host=0.0.0.0 \
 -Djava.rmi.server.hostname=0.0.0.0"
COPY target/booking-1.0-SNAPSHOT-fat.jar /usr/lib/contexts/booking-fat.jar
CMD java -jar /usr/lib/contexts/booking-fat.jar