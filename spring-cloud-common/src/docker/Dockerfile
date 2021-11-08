FROM openjdk:8-jre-slim

# 环境变量
ENV WORK_PATH /homw/project/cloud
ENV APP_NAME @project.build.finalName@.@project.packaging@
ENV APP_VERSION @project.version@

EXPOSE 10080

#USER
#USER user:group

#VLOUME
VOLUME ["/home.porject", "/tmp/data"]

#ADD

#COPY
COPY $APP_NAME $WORK_PATH/

#LABEL
#STOPSIGNAL
#ARG
#ONBUILD

#WORKDIR
WORKDIR $WORK_PATH

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghia /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone

#ENTRYPOINT
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom"]

CMD ["-jar", "-Xmx512m", "@project.build.finalName@.@project.packaging@"]