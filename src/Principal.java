import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    Button botao_gerar;
    Vetor v;
    Button[] vetBotoes;
    Button[] vet1Botoes;
    Button[] vet2Botoes;
    Button[] idxVet;
    Button[] idxVet1;
    Button[] idxVet2;
    Button marcI, marcJ, marcK;
    final int TL = 8;
    final double X_BASE = 100;
    final double X_PASSO = 60;
    final double Y_VET = 100;
    final double Y_SUB = 250;
    final double GAP = 60;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Merge Sort - Animacao");
        pane = new AnchorPane();

        botao_inicio = new Button();
        botao_inicio.setLayoutX(10); botao_inicio.setLayoutY(20);
        botao_inicio.setText("Iniciar");
        botao_inicio.setOnAction(e -> {
            botao_inicio.setDisable(true);
            botao_gerar.setDisable(true);
            animarMerge();
        });
        pane.getChildren().add(botao_inicio);

        botao_gerar = new Button();
        botao_gerar.setLayoutX(80); botao_gerar.setLayoutY(20);
        botao_gerar.setText("Gerar novos valores");
        botao_gerar.setOnAction(e -> { gerarValores(); });
        pane.getChildren().add(botao_gerar);

        gerarValores();

        idxVet = new Button[TL];
        for(int i = 0; i < TL; i++)
        {
            idxVet[i] = criarRotulo(String.valueOf(i), xPos(i), Y_VET + 45);
            pane.getChildren().add(idxVet[i]);
        }
        idxVet1 = new Button[TL / 2];
        idxVet2 = new Button[TL / 2];
        for(int i = 0; i < TL / 2; i++)
        {
            idxVet1[i] = criarRotulo(String.valueOf(i), xPos(i), Y_SUB + 45);
            idxVet2[i] = criarRotulo(String.valueOf(i), xPos(i + TL / 2) + GAP, Y_SUB + 45);
            idxVet1[i].setVisible(false);
            idxVet2[i].setVisible(false);
            pane.getChildren().add(idxVet1[i]);
            pane.getChildren().add(idxVet2[i]);
        }
        marcI = criarRotulo("i", 0, Y_SUB + 65);
        marcJ = criarRotulo("j", 0, Y_SUB + 65);
        marcK = criarRotulo("k", 0, Y_VET + 65);
        marcI.setVisible(false); marcJ.setVisible(false); marcK.setVisible(false);
        pane.getChildren().addAll(marcI, marcJ, marcK);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void gerarValores()
    {
        if(vetBotoes != null)
            for(Button b : vetBotoes)
                pane.getChildren().remove(b);

        v = new Vetor(TL);
        vetBotoes = new Button[TL];
        for(int i = 0; i < TL; i++)
        {
            vetBotoes[i] = criarBotao(String.valueOf(v.getValor(i)), xPos(i), Y_VET);
            pane.getChildren().add(vetBotoes[i]);
        }
    }

    public double xPos(int indice)
    {
        return (X_BASE + indice * X_PASSO);
    }

    public Button criarBotao(String texto, double x, double y)
    {
        Button botao = new Button(texto);
        botao.setLayoutX(x); botao.setLayoutY(y);
        botao.setMinHeight(40); botao.setMinWidth(40);
        botao.setFont(new Font(14));
        return (botao);
    }

    public Button criarRotulo(String texto, double x, double y)
    {
        Button rotulo = new Button(texto);
        rotulo.setLayoutX(x); rotulo.setLayoutY(y);
        rotulo.setMinWidth(40);
        rotulo.setFont(new Font(13));
        rotulo.setStyle("-fx-background-color: transparent;");
        return (rotulo);
    }

    public void mostrarRotulos(Button[] rotulos, boolean visivel)
    {
        Platform.runLater(() -> {
            for (Button r : rotulos) r.setVisible(visivel);
        });
    }

    public void posicionarMarcador(Button marcador, double x, double y, boolean visivel)
    {
        Platform.runLater(() -> {
            marcador.setLayoutX(x);
            marcador.setLayoutY(y);
            marcador.setVisible(visivel);
        });
    }

    public void moverBotoes(Button[] botoes, double[] origX, double[] origY, double[] destX, double[] destY)
    {
        int passos = 20;
        for(int p = 1; p <= passos; p++)
        {
            int passoAtual = p;
            for(int b = 0; b < botoes.length; b++)
            {
                int idx = b;
                double novoX = origX[idx] + (destX[idx] - origX[idx]) * passoAtual / passos;
                double novoY = origY[idx] + (destY[idx] - origY[idx]) * passoAtual / passos;
                Platform.runLater(() -> {
                    botoes[idx].setLayoutX(novoX);
                    botoes[idx].setLayoutY(novoY);
                });
            }
            pausar(30);
        }
    }

    public void pausar(long ms)
    {
        try
        {
            Thread.sleep(ms);
        } catch (InterruptedException e) { }
    }

    public void animarMerge()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                int seq = 1;
                while(seq < v.getTL())
                {
                    int tam = v.getTL() / 2;
                    int[] aux1 = new int[tam];
                    int[] aux2 = new int[tam];

                    v.particao(aux1, aux2);

                    Button[] novoVet1 = new Button[tam];
                    Button[] novoVet2 = new Button[tam];
                    double[] origX1 = new double[tam], origY1 = new double[tam];
                    double[] destX1 = new double[tam], destY1 = new double[tam];
                    double[] origX2 = new double[tam], origY2 = new double[tam];
                    double[] destX2 = new double[tam], destY2 = new double[tam];
                    for(int i = 0; i < tam; i++)
                    {
                        novoVet1[i] = criarBotao(String.valueOf(aux1[i]), xPos(i), Y_VET);
                        origX1[i] = xPos(i); origY1[i] = Y_VET;
                        destX1[i] = xPos(i); destY1[i] = Y_SUB;

                        novoVet2[i] = criarBotao(String.valueOf(aux2[i]), xPos(i + tam), Y_VET);
                        origX2[i] = xPos(i + tam); origY2[i] = Y_VET;
                        destX2[i] = xPos(i + tam) + GAP; destY2[i] = Y_SUB;
                    }
                    Platform.runLater(() -> {
                        for (Button b : novoVet1) pane.getChildren().add(b);
                        for (Button b : novoVet2) pane.getChildren().add(b);
                    });
                    mostrarRotulos(idxVet1, true);
                    mostrarRotulos(idxVet2, true);
                    pausar(50);

                    Button[] botoesParticao = new Button[v.getTL()];
                    double[] origXP = new double[v.getTL()];
                    double[] origYP = new double[v.getTL()];
                    double[] destXP = new double[v.getTL()];
                    double[] destYP = new double[v.getTL()];
                    for(int i = 0; i < tam; i++)
                    {
                        botoesParticao[i] = novoVet1[i];
                        origXP[i] = origX1[i]; origYP[i] = origY1[i];
                        destXP[i] = destX1[i]; destYP[i] = destY1[i];

                        botoesParticao[i + tam] = novoVet2[i];
                        origXP[i + tam] = origX2[i]; origYP[i + tam] = origY2[i];
                        destXP[i + tam] = destX2[i]; destYP[i + tam] = destY2[i];
                    }
                    moverBotoes(botoesParticao, origXP, origYP, destXP, destYP);
                    vet1Botoes = novoVet1;
                    vet2Botoes = novoVet2;
                    pausar(800);

                    Button[] vetAntigo = vetBotoes;
                    v.fusao(aux1, aux2, seq);

                    Button[] novoVet = new Button[v.getTL()];
                    double[] origXF = new double[v.getTL()];
                    double[] origYF = new double[v.getTL()];
                    double[] destXF = new double[v.getTL()];
                    double[] destYF = new double[v.getTL()];
                    int[] iPasso = new int[v.getTL()];
                    int[] jPasso = new int[v.getTL()];
                    int i = 0, j = 0, k = 0, seqAtual = seq, t_seq = seq;
                    while(k < v.getTL())
                    {
                        while(i < seqAtual && j < seqAtual)
                        {
                            iPasso[k] = i; jPasso[k] = j;
                            if(aux1[i] < aux2[j])
                            {
                                novoVet[k] = vet1Botoes[i];
                                origXF[k] = xPos(i); origYF[k] = Y_SUB;
                                i++;
                            } else
                            {
                                novoVet[k] = vet2Botoes[j];
                                origXF[k] = xPos(j + tam) + GAP; origYF[k] = Y_SUB;
                                j++;
                            }
                            k++;
                        }
                        while(i < seqAtual)
                        {
                            iPasso[k] = i; jPasso[k] = j;
                            novoVet[k] = vet1Botoes[i];
                            origXF[k] = xPos(i); origYF[k] = Y_SUB;
                            i++; k++;
                        }
                        while(j < seqAtual)
                        {
                            iPasso[k] = i; jPasso[k] = j;
                            novoVet[k] = vet2Botoes[j];
                            origXF[k] = xPos(j + tam) + GAP; origYF[k] = Y_SUB;
                            j++; k++;
                        }
                        seqAtual += t_seq;
                    }
                    for(int p = 0; p < v.getTL(); p++)
                    {
                        destXF[p] = xPos(p); destYF[p] = Y_VET;
                    }

                    Platform.runLater(() -> {
                        for(Button b : vetAntigo) pane.getChildren().remove(b);
                    });

                    for(int p = 0; p < v.getTL(); p++)
                    {
                        posicionarMarcador(marcI, xPos(iPasso[p]), Y_SUB + 65, iPasso[p] < tam);
                        posicionarMarcador(marcJ, xPos(jPasso[p] + tam) + GAP, Y_SUB + 65, jPasso[p] < tam);
                        posicionarMarcador(marcK, xPos(p), Y_VET + 65, true);
                        pausar(500);
                        moverBotoes(new Button[]{novoVet[p]},
                                    new double[]{origXF[p]}, new double[]{origYF[p]},
                                    new double[]{destXF[p]}, new double[]{destYF[p]});
                        pausar(150);
                    }
                    posicionarMarcador(marcI, 0, 0, false);
                    posicionarMarcador(marcJ, 0, 0, false);
                    posicionarMarcador(marcK, 0, 0, false);
                    mostrarRotulos(idxVet1, false);
                    mostrarRotulos(idxVet2, false);
                    vetBotoes = novoVet;
                    pausar(400);

                    seq *= 2;
                }
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            botao_inicio.setDisable(false);
            botao_gerar.setDisable(false);
        });
        task.setOnFailed(e -> {
            botao_inicio.setDisable(false);
            botao_gerar.setDisable(false);
        });
        Thread thread = new Thread(task);
        thread.start();
    }
}
