FROM amazoncorretto:11.0.15

MAINTAINER 1571339199@qq.com

RUN mkdir -p /soybean/gateway

WORKDIR /soybean/gateway

EXPOSE 10001

ADD ./soybean-gateway.jar ./gateway.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "gateway.jar"]

CMD ["--spring.profiles.active=docker"]
