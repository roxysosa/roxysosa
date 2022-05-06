FROM openjdk:18
LABEL mantainer="rsosa@quside.com"
COPY ./out/production/Fixcomments/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","Fixcoment"]