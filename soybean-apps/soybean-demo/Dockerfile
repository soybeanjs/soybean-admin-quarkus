FROM amazoncorretto:11.0.15

MAINTAINER 1571339199@qq.com

RUN mkdir -p /soybean/demo

WORKDIR /soybean/demo

ADD ./soybean-demo.jar ./demo.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "demo.jar"]

CMD ["--spring.profiles.active=docker"]
