#!/bin/sh

set -e

export IMAGE_NAME=${IMAGE_NAME:-catalog-searcher}
export IMAGE_TAG=${IMAGE_TAG:-latest}
export CATALOG_SEARCHER_SERVER_URL=catalog-searcher
export CATALOG_SEARCHER_SERVER_PORT=8011

echo "Running image name: ${IMAGE_NAME}"
echo "Running image tag : ${IMAGE_TAG}"

sed -i "s/#SERVER_URL/$CATALOG_SEARCHER_SERVER_URL/g" ./jmeter/catalog-searcher-test.jmx
sed -i "s/#SERVER_PORT/$CATALOG_SEARCHER_SERVER_PORT/g" ./jmeter/catalog-searcher-test.jmx

echo "Building images for service"
docker-compose build