public class Vetor {
    private int [] vet;
    private int TL;

    public Vetor(int tamanho) {
        TL = tamanho;
        vet = new int[TL];
        for (int i = 0; i < TL; i++)
            vet[i] = (int) (Math.random() * 100);
    }

    public int[] getVet() {
        return vet;
    }

    public int getTL() {
        return TL;
    }

    public int getValor(int pos) {
        return vet[pos];
    }

    public void setValor(int pos, int valor) {
        vet[pos] = valor;
    }

    public void particao(int [] vet1, int [] vet2) {
        int tam = TL/2;
        for(int i =0; i < TL/2; i++) {
            vet1[i] = vet[i];
            vet2[i] = vet[i + tam];
        }
    }

    public void fusao(int [] vet1, int [] vet2, int seq) {
        int i =0, j = 0, k = 0, t_seq = seq;
        while(k < TL) {
            while(i <seq && j < seq) {
                if(vet1[i] < vet2[j])
                    vet[k++] = vet1[i++];
                else
                    vet[k++] = vet2[j++];
            }
            while(i < seq)
                vet[k++] = vet1[i++];
            while(j < seq)
                vet[k++] = vet2[j++];
            seq += t_seq;
        }
    }

    public void merge(){
        int vet1[] = new int[TL/2];
        int vet2[] = new int[TL/2];
        int seq = 1;
        while(seq < TL) {
            particao(vet1, vet2);
            fusao(vet1, vet2, seq);
            seq *= 2;
        }
    }
}
