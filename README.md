# ![VulnerableApp-jsp](https://raw.githubusercontent.com/SasanLabs/VulnerableApp/master/docs/logos/Coloured/iconColoured.png) VulnerableApp-jsp
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![](https://img.shields.io/twitter/follow/sasan_karan?style=flat&logo=twitter)](https://twitter.com/intent/follow?screen_name=sasan_karan)

VulnerableApp-jsp is a Vulnerable Application containing vulnerabilities specific to JSP technology stack. It is part of the farm of Vulnerable Applications provided by [SasanLabs](https://github.com/SasanLabs). This Vulnerable Application utilises the facilities provided by [Owasp VulnerableApp-Facade](https://github.com/SasanLabs/VulnerableApp-facade) and it is just exposing bunch of Api's which are vulnerable to various attacks.
User Interface for VulnerableApp-jsp is provided by [Owasp VulnerableApp-Facade](https://github.com/SasanLabs/VulnerableApp-facade).

## How to run the project
As VulnerableApp-jsp doesn't provide user interface and relies on [Owasp VulnerableApp-facade](https://github.com/SasanLabs/VulnerableApp-facade) hence you need to start it using instructions: [VulnerableApp-Facade simple start](https://github.com/SasanLabs/VulnerableApp-facade#simple-start)

## How to contribute to the project
There are 2 ways to build and run this project:
1. For building the project from source code:
```
1. Install [Apache Tomcat Server](https://tomcat.apache.org/download-90.cgi)
2. Execute following command for project root: `./gradlew war` 
3. Navigate to `build/libs/`
4. Move the *.war file to `tomcat/webapps/` directory
5. Start Apache Tomcat Server
```
2. For building the docker image and then using [VulnerableApp-Facade](https://github.com/SasanLabs/VulnerableApp-facade#simple-start) to test the working of the application.
```
1. Build the docker image with command: docker buildx build --platform linux/amd64,linux/arm64,linux/ppc64le -t  sasanlabs/owasp-vulnerableapp-jsp:latest . --push
2. Navigate to VulnerableApp-Facade and run it as described in VulnerableApp-Facade#simple-start
```
### Api structure for testing:
General URL pattern we follow for api's is:
`http://<baseurl>:<port>/VulnerableApp-jsp/<Vulnerability type>/<Vulnerability level>`
#### Example URL:
`http://<baseurl>:<port>/VulnerableApp-jsp/FileUpload/LEVEL_1`
  
## Contact
Please raise a github issue for enhancement/issues in VulnerableApp-jsp or send email to karan.sasan@owasp.org regarding queries
we will try to resolve issues asap.
