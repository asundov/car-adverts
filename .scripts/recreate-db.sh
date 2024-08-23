#!/bin/bash

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "$script_dir/../.database"

docker-compose down

docker volume rm car-adverts-postgres_car-adverts_data

docker-compose up -d

docker-compose logs -f
