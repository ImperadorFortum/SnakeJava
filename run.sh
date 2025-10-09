#!/bin/bash

# Script para compilar e executar o jogo Snake
# Este script garante que o programa seja executado do diretório correto

# Ir para o diretório do projeto
cd "$(dirname "$0")"

echo "=== Compilando o projeto Snake ==="
echo "Diretório atual: $(pwd)"
echo ""

# Criar diretório para os arquivos compilados
mkdir -p bin

# Compilar todos os arquivos Java
echo "Compilando arquivos Java..."
javac -d bin src/*.java controller/*.java

if [ $? -eq 0 ]; then
    echo "✓ Compilação concluída com sucesso!"
    echo ""
    echo "=== Executando o jogo ==="
    echo ""
    
    # Executar o programa a partir do diretório bin
    cd bin
    java main
else
    echo "✗ Erro na compilação!"
    exit 1
fi

