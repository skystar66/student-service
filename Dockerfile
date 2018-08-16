# 基于哪个镜像
FROM java:8
ADD target/*.jar app.jar
RUN bash -c 'touch /app.jar'
RUN mkdir -p /var/logs/student-service
# 开放8080端口
EXPOSE 8087
# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF8","-Duser.timezone=GMT+08","-jar","/app.jar"]