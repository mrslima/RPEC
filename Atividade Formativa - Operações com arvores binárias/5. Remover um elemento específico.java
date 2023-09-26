class No {
    int valor;
    No esquerda, direita;

    public No(int item) {
        valor = item;
        esquerda = direita = null;
    }
}

class ArvoreBinariaBusca {
    No raiz;

    ArvoreBinariaBusca() {
        raiz = null;
    }

    void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    No inserirRec(No no, int valor) {
        if (no == null) {
            no = new No(valor);
            return no;
        }

        if (valor < no.valor)
            no.esquerda = inserirRec(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = inserirRec(no.direita, valor);

        return no;
    }

    void preOrdem(No no) {
        if (no != null) {
            System.out.print(no.valor + " ");
            preOrdem(no.esquerda);
            preOrdem(no.direita);
        }
    }

    void inOrdem(No no) {
        if (no != null) {
            inOrdem(no.esquerda);
            System.out.print(no.valor + " ");
            inOrdem(no.direita);
        }

    No removerMaiorElemento(No no) {
        if (no == null)
            return no;
        
        if (no.direita == null)
            return no.esquerda;
        
        no.direita = removerMaiorElemento(no.direita);
        return no;
    }

    No removerMenorElemento(No no) {
        if (no == null)
            return no;
        
        if (no.esquerda == null)
            return no.direita;
        
        no.esquerda = removerMenorElemento(no.esquerda);
        return no;
    }

    No removerElemento(No no, int valor) {
        if (no == null)
            return no;
        
        if (valor < no.valor)
            no.esquerda = removerElemento(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = removerElemento(no.direita, valor);
        else {
            if (no.esquerda == null)
                return no.direita;
            else if (no.direita == null)
                return no.esquerda;

            no.valor = encontrarMenorValor(no.direita);
            no.direita = removerElemento(no.direita, no.valor);
        }

        return no;
    }

    int encontrarMenorValor(No no) {
        int menorValor = no.valor;
        while (no.esquerda != null) {
            menorValor = no.esquerda.valor;
            no = no.esquerda;
        }
        return menorValor;
    }
}
