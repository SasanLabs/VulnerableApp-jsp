FROM gradle:7.1.1-jdk8 as BUILDER
COPY --chown=gradle:gradle . /home/VulnerableApp-jsp
WORKDIR /home/VulnerableApp-jsp
RUN gradle war

FROM tomcat:alpine
MAINTAINER KSASAN preetkaran20@gmail.com
COPY --from=BUILDER /home/VulnerableApp-jsp/build/libs/VulnerableApp-jsp.war /usr/local/tomcat/webapps/