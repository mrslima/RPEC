import java.util.Random;

class Node {
    int chave, altura;
    Node esquerda, direita;

    Node(int d) {
        chave = d;
        altura = 1;
    }
}

class ArvoreAVL {
    Node raiz;

    int altura(Node N) {
        if (N == null)
            return 0;
        return N.altura;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    int fatorBalanceamento(Node N) {
        if (N == null)
            return 0;
        return altura(N.esquerda) - altura(N.direita);
    }

    Node inserir(Node no, int chave) {
        if (no == null)
            return (new Node(chave));

        if (chave < no.chave)
            no.esquerda = inserir(no.esquerda, chave);
        else if (chave > no.chave)
            no.direita = inserir(no.direita, chave);
        else
            return no;

        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && chave < no.esquerda.chave)
            return rotacaoDireita(no);

        if (balanceamento > 1 && chave > no.esquerda.chave) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && chave > no.direita.chave)
            return rotacaoEsquerda(no);

        if (balanceamento < -1 && chave < no.direita.chave) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    Node encontrarMenorNo(Node no) {
        Node atual = no;
        while (atual.esquerda != null)
            atual = atual.esquerda;
        return atual;
    }

    Node removerNo(Node raiz, int chave) {
        if (raiz == null)
            return raiz;

        if (chave < raiz.chave)
            raiz.esquerda = removerNo(raiz.esquerda, chave);
        else if (chave > raiz.chave)
            raiz.direita = removerNo(raiz.direita, chave);
        else {
            if ((raiz.esquerda == null) || (raiz.direita == null)) {
                Node temp = null;
                if (temp == raiz.esquerda)
                    temp = raiz.direita;
                else
                    temp = raiz.esquerda;

                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else
                    raiz = temp;
            } else {
                Node temp = encontrarMenorNo(raiz.direita);
                raiz.chave = temp.chave;
                raiz.direita = removerNo(raiz.direita, temp.chave);
            }
        }

        if (raiz == null)
            return raiz;

        raiz.altura = max(altura(raiz.esquerda), altura(raiz.direita)) + 1;
        int balanceamento = fatorBalanceamento(raiz);

        if (balanceamento > 1 && fatorBalanceamento(raiz.esquerda) >= 0)
            return rotacaoDireita(raiz);

        if (balanceamento > 1 && fatorBalanceamento(raiz.esquerda) < 0) {
            raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
            return rotacaoDireita(raiz);
        }

        if (balanceamento < -1 && fatorBalanceamento(raiz.direita) <= 0)
            return rotacaoEsquerda(raiz);

        if (balanceamento < -1 && fatorBalanceamento(raiz.direita) > 0) {
            raiz.direita = rotacaoDireita(raiz.direita);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    void preOrdem(Node no) {
        if (no != null) {
            System.out.print(no.chave + " ");
            preOrdem(no.esquerda);
            preOrdem(no.direita);
        }
    }
    
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java ArvoreAVL <inserir> <remover> <seed>");
            return;
        }

        int inserir = Integer.parseInt(args[0]);
        int remover = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);

        ArvoreAVL arvore = new ArvoreAVL();
        Random rand = new Random(seed);

        long startTime, endTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < inserir; i++) {
            int chave = rand.nextInt(inserir);
            arvore.raiz = arvore.inserir(arvore.raiz, chave);
        }
        endTime = System.currentTimeMillis();

        long tempoInsercao = endTime - startTime;

        System.out.println("Travessia pré-ordem da árvore construída: ");
        arvore.preOrdem(arvore.raiz);

        startTime = System.currentTimeMillis();
        arvore.raiz = arvore.removerNo(arvore.raiz, remover);
        endTime = System.currentTimeMillis();

        System.out.println("");
        System.out.println("Travessia pré-ordem após a exclusão do " + remover + ":");
        arvore.preOrdem(arvore.raiz);

        long tempoRemocao = endTime - startTime;

        System.out.println("\n\n\t[Arvore com " + inserir + " elementos]");
        System.out.println("\tTempo de inserção: " + tempoInsercao + " milissegundos");
        System.out.println("\tTempo de remoção do elemento com chave " + remover + ": " + tempoRemocao + " milissegundos");
    }
}
