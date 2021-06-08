#!/bin/bash

docker-compose down
docker rmi mihajlonesic/betbull:player-team-service || true
docker rmi mihajlonesic/betbull:transfer-service || true
docker rmi mihajlonesic/betbull:gateway-service || true
docker rmi mihajlonesic/betbull:discovery-service || true