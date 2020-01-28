FROM adoptopenjdk:8-jre-hotspot
WORKDIR /usr/src
COPY target/service-token-1.0-SNAPSHOT.jar /usr/src
CMD java -Xmx64m \
        -Djava.net.preferIPv4Stack=true \
        -Djava.net.preferIPv4Addresses=true \
        -jar service-token-1.0-SNAPSHOT.jar