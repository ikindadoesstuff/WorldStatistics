FROM mysql/mysql-server:latest
# Files copied to this folder run when the docker container is started.
# This simply runs the sql file on init
COPY world.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD group21
ENV MYSQL_ROOT_HOST=%