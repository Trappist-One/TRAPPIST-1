FROM nginx

LABEL name = "t1-vue"
LABEL version = "1.0"

MAINTAINER trappistOne

#RUN rm /etc/nginx/conf.d/service.conf

#ADD service.conf /etc/nginx/conf.d/
COPY dist/ /usr/share/nginx/html/
