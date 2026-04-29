# Sudoku em JavaFX

Aplicação de Sudoku desenvolvida em Java com interface gráfica em JavaFX, seguindo o padrão arquitetural MVC. O projeto tem como foco principal a implementação do algoritmo de geração de tabuleiros válidos.

## Visão Geral

O sistema gera automaticamente tabuleiros de Sudoku completos e válidos, utilizando um algoritmo de backtracking. Após a geração, células são removidas de acordo com o nível de dificuldade selecionado, criando diferentes desafios para o usuário.

## Algoritmo

O núcleo da aplicação é um algoritmo recursivo de backtracking responsável por:

- Preencher o tabuleiro garantindo as regras do Sudoku  
- Validar números em linhas, colunas e subgrades 3x3  
- Explorar possibilidades até encontrar uma solução válida  
- Gerar um tabuleiro completo antes da remoção de células  

A dificuldade é definida pela quantidade de células removidas do tabuleiro inicial:

- Fácil  
- Médio  
- Difícil  

## Arquitetura

O projeto segue o padrão MVC (Model-View-Controller):

- Model: representa o estado do tabuleiro e regras do jogo  
- View: interface gráfica em JavaFX  
- Controller: gerencia a interação do usuário e controla a geração do jogo  

Essa separação facilita manutenção e evolução do sistema.

## Tecnologias

- Java  
- JavaFX  
- Padrão MVC  

## Funcionalidades

- Geração automática de tabuleiros válidos  
- Definição de níveis de dificuldade  
- Interface gráfica para interação com o usuário  
- Atualização dinâmica do estado do jogo  

## Como Executar

1. Clone o repositório:
   git clone https://github.com/begiudicelli/seu-repositorio

2. Abra o projeto em uma IDE (Eclipse, IntelliJ, etc.)

3. Execute a classe principal da aplicação

## Objetivo

Projeto com foco educacional, desenvolvido para praticar:

- Algoritmos recursivos (backtracking)  
- Estruturação de aplicações com MVC  
- Desenvolvimento de interfaces com JavaFX  


