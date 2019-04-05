FROM openjdk:11-jre-slim

RUN apt-get update && apt-get -y install wget curl

ENV SERVICE_NAME=catalog-searcher
ENV JMETER_VERSION="5.1"
ENV JMETER_HOME /jmeter/apache-jmeter-${JMETER_VERSION}/
ENV JMETER_DIR=${JMETER_HOME}/apache-jmeter-${JMETER_VERSION}
ENV JMETER_PLUGINS_FOLDER ${JMETER_HOME}/lib/ext

RUN mkdir /jmeter \
    && mkdir /output \
    && cd /jmeter/ \
    && wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-${JMETER_VERSION}.tgz \
    && wget -P ${JMETER_DIR}/lib/ext https://repo1.maven.org/maven2/kg/apc/jmeter-plugins-manager/0.10/jmeter-plugins-manager-0.10.jar \
    && tar -xzf apache-jmeter-${JMETER_VERSION}.tgz \
    && rm apache-jmeter-${JMETER_VERSION}.tgz

ENV PATH ${JMETER_HOME}/bin:${PATH}

COPY /jmeter/launcher.sh /
COPY /jmeter/catalog-searcher-test.jmx /catalog-searcher-test.jmx

WORKDIR ${JMETER_HOME}

ENTRYPOINT ["/launcher.sh"]