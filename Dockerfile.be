# 베이스 이미지로 ubuntu:latest를 사용
FROM ubuntu:latest AS build

# OS 업데이트 및 필요한 패키지(여기서는 Java 11 JDK)를 설치
RUN apt update && apt -y install openjdk-11-jdk

# 로컬에서 빌드된 JAR 파일을 컨테이너의 /be/ 디렉토리로 복사
COPY /second-hand-b/build/libs/*.jar /be/app.jar

# 컨테이너가 시작될 때 실행할 명령어를 지정
# 여기서는 JAR 파일을 실행하는 명령어를 설정
# 추가적으로 스프링의 설정 위치와 프로파일도 함께 지정
ENTRYPOINT ["java","-Dspring.config.location=file:/be/config/","-jar","/be/app.jar","--spring.profiles.active=dev"]
