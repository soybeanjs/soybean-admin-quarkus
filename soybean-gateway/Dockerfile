FROM eclipse-temurin:11.0.15_10-jre-alpine

MAINTAINER 1571339199@qq.com

RUN mkdir -p /soybean/gateway

WORKDIR /soybean/gateway

ADD ./soybean-gateway.jar ./gateway.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "gateway.jar"]

CMD ["--spring.profiles.active=docker"]
