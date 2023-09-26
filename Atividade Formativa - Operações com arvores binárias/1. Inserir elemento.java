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
}
