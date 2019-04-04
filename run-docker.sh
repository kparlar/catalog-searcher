#!/bin/sh

set -e

export IMAGE_NAME=${IMAGE_NAME:-catalog-searcher}
export IMAGE_TAG=${IMAGE_TAG:-latest}

echo "Running image name: ${IMAGE_NAME}"
echo "Running image tag : ${IMAGE_TAG}"

echo "Building docker-image"
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .

echo "Building images for service"
docker-compose build