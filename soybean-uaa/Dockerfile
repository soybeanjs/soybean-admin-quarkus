FROM amazoncorretto:11.0.15

MAINTAINER 1571339199@qq.com

RUN mkdir -p /soybean/uaa

WORKDIR /soybean/uaa

EXPOSE 10001

ADD ./soybean-uaa.jar ./uaa.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "uaa.jar"]

CMD ["--spring.profiles.active=docker"]
