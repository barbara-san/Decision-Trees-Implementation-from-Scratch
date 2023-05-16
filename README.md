# Inteligência Artificial - 2022/2023

## Projeto 3 - Árvores de Decisão

### Índice
  
  [Vista Geral](#vista-geral)  
  [Instruções de Compilação](#instruções-de-compilação)  
  [Comandos de Execução](#comandos-de-execução)  
  [Notas Importantes](#notas-importantes)


### Vista Geral

Este projeto foi desenvolvido no âmbito da Unidade Curricular *Inteligência Artificial*, durante o ano letivo 2022/2023, na Faculdade de Ciências da Universidade do Porto, pela aluna Bárbara Santos.

O seu principal objetivo é a implementação completa de um desenvolvedor de uma [Árvore de Decisão](https://en.wikipedia.org/wiki/Decision_tree "Descrição de Árvores de Decisão - Wikipédia (Inglês)") que consiga, posteriormente, prever o resultado de um determinado atributo quando em falta, recebendo dados sobre os outros atributos. Para tal, foi escolhida a linguagem Java, devido à sua relação com Programação Orientada a Objetos, o facto de ser *strongly-typed*, o seu "*Garbage Collector*", entre outros motivos.

O programa foi compilado tanto em *Ubuntu 20.04.5 LTS* com *javac 11.0.17*.

### Instruções de Compilação

Para poder executar cada problema é primeiro necessário compilar todos os ficheiros java. Para tal, utilize a seguinte instrução:

`javac *.java`

Após a compilação de todos os ficheiros, é possível, finalmente, executá-los.  
Para leitura e desenvolvimento da árvore de decisão para cada um dos datasets disponibilizados, utilizar o formato de entrada `java Program path`, onde "path" deve ser substituido pelo caminho até ao dataset desejado (em formato csv):  
- `java Program datasets/restaurant`
- `java Program datasets/weather`
- `java Program datasets/iris`

Após a escolha do dataset, é esperado que o programa desenvolva a respetiva decision tree, que será impressa no terminal.

### Comandos de Execução

Após a criação da Decision Tree, é possível aplicar uma série de comandos para impressão e predição:

- `print dt`: imprime a decision tree criada.
- `print ds used for training`: imprime o dataset fornecido para treino da Decision Tree.
- `predict path`: "path" deve ser substituido pelo caminho até ao dataset cuja target se pretende prever (este comando irá imprimir sozinho a previsão para cada linha do csv no formato de uma lista).
- `print ds used for prediction`: imprime o último dataset fornecido para previsão.
- `print prediction`: imprime novamente a última previsão feita.
- `exit`: termina o programa.

### Notas Importantes

Todos os datasets fornecidos **devem** possuir uma coluna com o ID e uma linha com o nome dos atributos, independentemente de ser um dataset de treino ou previsão.