# Animação do Merge Sort (JavaFX)

Trabalho que mostra, em uma animação em JavaFX, como funciona o Merge Sort (1ª implementação).

## Como o Merge Sort funciona aqui

1. **Partição:** o vetor é dividido em duas metades, `vet1` e `vet2`.
2. **Fusão:** os blocos de `vet1` e `vet2` são intercalados, do menor pro maior, de volta no vetor principal.
3. O tamanho dos blocos (`seq`) começa em 1 e dobra a cada passada, até o vetor ficar ordenado.

O tamanho do vetor precisa ser potência de 2 (na animação é 8).

## Como rodar

Precisa do JavaFX SDK. Arquivo principal: `src/Principal.java`.
