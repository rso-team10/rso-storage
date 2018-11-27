if [ ! -f .env ]; then
    echo "Creating docker compose env vars"
    touch .env
    echo DOCKER_HOST_IP=$(ip -4 addr show docker0 | grep -Po 'inet \K[\d.]+') >> .env
fi

