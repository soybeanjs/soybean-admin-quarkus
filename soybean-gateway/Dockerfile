FROM apache/skywalking-java-agent:8.11.0-java11

MAINTAINER 1571339199@qq.com

#VOLUME /tmp
#
#COPY target/lib/ ./lib/

RUN mkdir -p /app

WORKDIR /app

COPY ./target/soybean-gateway.jar app.jar

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx512m" \
    SW_AGENT_COLLECTOR_BACKEND_SERVICES="127.0.0.1:11800" \
    SW_AGENT_NAME="soundforce-gateway"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

CMD ["--spring.profiles.active=dev"]
