# 此处跟其它模块基础镜像不同的原因
# 一般情况下eclipse-temurin:11.0.15_10-jre-alpine够用
# 但此模块使用了数字验证码生成需要用到sun包下的字体jar包
# 所以需要完整jre基础镜像
# openjdk辣么多 使用那一种还是需要看实际情况 大体上差别不大
# adoptopenjdk在其他开源项目中使用较多
# 但eclipse-temurin作为其延续 在体积上和质量上都相对有些许优势 主要在于体积上原因
# 更多分层镜像打包和graalvm打包方式后续会更新 graalvm仅作为实现性支持 千万别搞生产 否则趁公司没反应过来赶紧提桶跑路
# 另外最后一句某些网上流传的精简打包本质是把jar文件和程序运行时文件分离开来然后吹嘘镜像从几百兆缩减到了几兆
# 只能说不要为了瘦身而瘦身 这种方式了解其本质即可 但项目错综复杂 纵观其他kafka nacos开源组件如果都这样做 那java程序在云原生环境下可太牛了
# 如果非要追求java方式的镜像精简 推荐vertx或者quarkus或者等spring native成熟 否则推荐其他语言
FROM apache/skywalking-java-agent:8.11.0-java11

MAINTAINER 1571339199@qq.com

#VOLUME /tmp
#
#COPY target/lib/ ./lib/

RUN mkdir -p /app

WORKDIR /app

COPY ./target/soybean-uaa.jar app.jar

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx512m" \
    SW_AGENT_COLLECTOR_BACKEND_SERVICES="127.0.0.1:11800" \
    SW_AGENT_NAME="soundforce-uaa"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

CMD ["--spring.profiles.active=dev"]
