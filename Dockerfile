FROM tomcat:alpine
MAINTAINER KSASAN preetkaran20@gmail.com

COPY build/libs/VulnerableApp-jsp.war /usr/local/tomcat/webapps/