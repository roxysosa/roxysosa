FROM openjdk:18
LABEL mantainer="rsosa@quside.com"
COPY . /scr
WORKDIR /scr
ENTRYPOINT ["java","./Fixcoment"]