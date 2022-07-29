FROM eclipse-temurin:11.0.15_10-jre-alpine

MAINTAINER 1571339199@qq.com

#VOLUME /tmp
#
#COPY target/lib/ ./lib/

RUN mkdir -p /app

WORKDIR /app

COPY ./target/soybean-monitor.jar app.jar

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx512m"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

CMD ["--spring.profiles.active=dev"]
