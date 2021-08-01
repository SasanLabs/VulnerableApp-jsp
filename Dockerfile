FROM gradle:7.1.1-jdk8 as BUILDER
COPY --chown=gradle:gradle . /home
WORKDIR /home
RUN gradle war

FROM tomcat:alpine
MAINTAINER KSASAN preetkaran20@gmail.com
COPY --from=BUILDER /home/build/libs/VulnerableApp-jsp.war /usr/local/tomcat/webapps/