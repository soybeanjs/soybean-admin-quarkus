FROM amazoncorretto:11.0.15

MAINTAINER 1571339199@qq.com

RUN mkdir -p /soybean/monitor

WORKDIR /soybean/monitor

EXPOSE 10001

ADD ./soybean-monitor.jar ./monitor.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "monitor.jar"]

CMD ["--spring.profiles.active=docker"]
