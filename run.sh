#!/usr/bin/env bash

JAR_FILE="target/keycloak-migrate-0.0.1.jar"

OS="$(uname -s)"

RED='\033[0;31m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'

log() {
  echo -e "${CYAN}>> $1${NC}"
}
success() {
  echo -e "${GREEN}✔ $1${NC}"
}
error() {
  echo -e "${RED}✖ $1${NC}"
}

if [ ! -f .env ]; then
  error ".env файл не найден в текущей директории"
  exit 1
else
  success ".env файл найден"
fi


log "Сборка проекта через Maven..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
  error "Ошибка сборки проекта"
  exit 1
else
  success "Проект собран"
fi


if [ ! -f "$JAR_FILE" ]; then
  error "JAR файл не найден: $JAR_FILE"
  exit 1
fi

log "Запуск приложения..."

if [[ "$OS" == *"MINGW"* || "$OS" == *"MSYS"* ]]; then
  cmd //c "java -jar $JAR_FILE"
else
  java -jar "$JAR_FILE"
fi
