#### 构建spring-boot项目
FROM openjdk:8-jdk-alpine AS build

# 设置项目在docker容器中工作目录
WORKDIR /app

# 将 maven 可执行程序复制到容器中
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# 复制pom.xml文件
COPY pom.xml .

# 导入所有maven依赖, 使用-s .mvn/settings.xml指定mirror
RUN ./mvnw dependency:go-offline -B -s .mvn/settings.xml

# 复制项目源代码
COPY src src

# 打包应用程序
RUN ./mvnw package -DskipTests -s .mvn/settings.xml
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### 能运行应用程序的最小docker容器
FROM openjdk:8-jre-slim

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# 等待前置服务启动完成, 拉起spring服务
COPY wait-for-it.sh .
RUN chmod +x ./wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh","rabbitmq:5672","-t","20","--","java","-cp","app:app/lib/*","com.piggy.quincy.QuincyApplication"]