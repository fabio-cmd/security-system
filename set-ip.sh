#!/bin/bash

echo "Buscando seu IP público..."
USER_IP=$(curl -s ifconfig.me)

if [[ -z "$USER_IP" ]]; then
  echo "Não foi possível detectar o IP público. Verifique sua conexão com a internet."
  exit 1
fi

echo "IP detectado: $USER_IP"

# Atualiza SPRING_DATASOURCE_URL nos arquivos de staging e production
for ENV in staging production; do
  FILE="k8s/$ENV/deployment.yaml"
  if [[ -f "$FILE" ]]; then
    echo "Atualizando arquivo: $FILE"
    # Substitui o valor atual do IP por IP detectado
    sed -i.bak -E "s|jdbc:oracle:thin:@[^:]+:9445|jdbc:oracle:thin:@$USER_IP:9445|g" "$FILE"
    echo "IP atualizado no arquivo: $FILE"
  else
    echo "Arquivo não encontrado: $FILE"
  fi
done

echo "Todos os arquivos foram atualizados com sucesso."
