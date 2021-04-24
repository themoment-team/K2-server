FROM jenkins/jenkins:jdk11

#도커를 실행하기 위한 root 계정으로 전환
USER root

#도커 설치
COPY docker_install.sh /docker_install.sh
RUN chmod +x /docker_install.sh
RUN /docker_install.sh

#설치 후 도커그룹의 jenkins 계정 생성 후 해당 계정으로 변경
RUN groupadd -f docker
RUN usermod -aG docker jenkins
USER jenkins
#ENTRYPOINT sudo chown 666 /var/run/docker.sock