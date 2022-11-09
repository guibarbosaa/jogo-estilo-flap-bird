package com.paradise;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.time.format.TextStyle;
import java.util.Random;

import sun.jvm.hotspot.debugger.win32.coff.TestParser;

public class paradise extends ApplicationAdapter {

	// Aqui estou criando meus atributos/classes, eles são privados , somente a clase paradise pode acessa-los
	private SpriteBatch batch;

	// [] (arry)  significa que agr dentro do objeto avatar podera ser adicionado infinitas variaveis dentro do objeto avatar
	private Texture [] avatar;
 	private Texture fundo;
 	private Texture obstaculoTopo;
 	private Texture obstaculobaixo;
 	private Texture obstaculoMeio;
 	private Texture gameOver;


   // objetos para criar textos, formas e para que os obstaculos venham aleatoriamente
 	private Random numeroRandomico;
 	private BitmapFont font;
 	private BitmapFont mensagemDeMorte;
 	private Circle avatarCirculo;
 	private Circle dragaoObstaculo1;
 	private Circle dragaoObstaculo2;
 	private Circle dragaoObstaculo3;
 	private ShapeRenderer shape;




	// Objetos floats
	private float variacao;
	private float velocidadeQueda;
	private float posicaoIniciaVertical;
	private float posicaoMovimentoobstaculoHorizontal;
	private float lugarDragao1;
	private float lugarDragao2;
	private float lugarDragao3;
	private float deltaTime;
    private float dragaoMovendoAleatorio1;
    private float espacoEntrObstaculos;
    private float alturaAvatar;

    // objetos de verdadeiro ou falso
    private boolean pontos;



	// objetos inteiros
	private int movimento;
	private int alturaTela;
	private int larguraTela;
	private int estadoDoJogo=0;
	private int pontuacao=0;




	// aqui é o meu metodo que cria os elementos
	@Override
	public void create () {

		// aqui eu instancio meus atributos/classes
		batch = new SpriteBatch();
		avatar = new Texture[3];
		avatar[0] = new Texture ("DRAGAO RETO.png");
		avatar[1] = new Texture ("DRAGAO CIMA.png");
		avatar[2] = new Texture ("DRAGAO BAIXO.png");

		fundo = new Texture("backgroud.png");

      obstaculoTopo = new Texture("dragao obstaculo cima.png");
      obstaculobaixo = new Texture("dragao obstaculo baixo.png");
      obstaculoMeio = new Texture("dragao obstaculo cima.png");

      gameOver = new Texture("game over.png");



      numeroRandomico = new Random();

      avatarCirculo = new Circle();
      dragaoObstaculo1 = new Circle();
      dragaoObstaculo2 = new Circle();
      dragaoObstaculo3 = new Circle();
      shape = new ShapeRenderer();


      font = new BitmapFont();
      font.setColor(Color.WHITE);
      font.getData().setScale(5);

      mensagemDeMorte = new BitmapFont();
      mensagemDeMorte.setColor(Color.WHITE);
      mensagemDeMorte.getData().setScale(3);



       // aqui estou pegando todo o tamanha da minha tela

		alturaTela = Gdx.graphics.getHeight();
		larguraTela =Gdx.graphics.getWidth();

		posicaoIniciaVertical = alturaTela/2;

		posicaoMovimentoobstaculoHorizontal = larguraTela - 356;
		lugarDragao1 = alturaTela-356;
		lugarDragao2 = alturaTela-1050;
		lugarDragao3 = alturaTela-1788;

		espacoEntrObstaculos = 20;

		alturaAvatar = 120;





	}


	//aqui é o meu metodo que desenha no celular meus elementos criados
	@Override
	public void render () {

		// ++ (incremento) significa que ira ser adicionado de 1 em um em nossa varialvel no caso float
		// variação será igual a zero
		variacao += 0.075;

		//Criando condição para se a variação for maior que três
		//variação sera igual a zero
		if (variacao > 3) {

			variacao = 0;
		}




		if (estadoDoJogo == 0){

			if(Gdx.input.justTouched()){

				estadoDoJogo = 1;
			}


		}

		else { //jogo iniciado

			// incrementa velocidade da queda do mascote
			velocidadeQueda++;



			if (posicaoIniciaVertical > 0) {

				posicaoIniciaVertical -= velocidadeQueda;

			}
           // aqui eu defino que quando o jogador cair ele morrer
			if (posicaoIniciaVertical < 0) {
				estadoDoJogo = 2;

			}
			// aqui eu defino que quando o jogador ultrapassar o limite de altura da tela ele ira morrer
			if (posicaoIniciaVertical >= alturaTela) {

				estadoDoJogo =2;


			}



            // aqui é onde quando houver um toque o personagem subir
			if (estadoDoJogo == 1){

				if (Gdx.input.justTouched()){

					velocidadeQueda = -16;

				}


			//pega variação de frame na tela
			deltaTime = Gdx.graphics.getDeltaTime();
			posicaoMovimentoobstaculoHorizontal -= deltaTime * 350;
			//movimento++;

			// aqui eu defino que a velocidade dos obstaculos ira almentar depois de passar do nivel 5
				if(pontuacao >= 5 ) {

					posicaoMovimentoobstaculoHorizontal -= deltaTime * 350+ deltaTime++;
				}



			// condicionamento para quando o obstaculo sumir da tela voltar de novo
			// e tbm condicionamos para gerar randomicamente onde meus proximos obstaculos vao nascer

			if (posicaoMovimentoobstaculoHorizontal < -obstaculoTopo.getWidth()) {

				posicaoMovimentoobstaculoHorizontal = larguraTela;
				dragaoMovendoAleatorio1 = numeroRandomico.nextInt(400) - 200;
				pontos = false;

			}


         // aqui estou incrementando a pontuação para que quando os obstaculos passem de 120 na horizontal a pontuação acrecente em 1
		 // tbm defino verdadeiro ou falso, quando os obstaculos passar de 120 ele ira incrementar e como incremento ele ja não é mais falso
         if (posicaoMovimentoobstaculoHorizontal < 120){

         	if(!pontos){

				pontuacao++;
				pontos=true;


			}




		 }

         // aqui estou fazendo a repetição para que quando o jogador morrer o jogo volte ao estado inicial
			} else {
				if(Gdx.input.justTouched()){

					estadoDoJogo=0;
					pontuacao=0;
					velocidadeQueda=0;
					posicaoIniciaVertical = alturaTela/2;
					posicaoMovimentoobstaculoHorizontal = larguraTela -356;
					dragaoMovendoAleatorio1 = 0;
					pontos = false;


				}
			}


		} //fechamento else


		//aqui defino o inicio e o final da execução do desenho no meu app
		batch.begin();

        batch.draw(fundo,0,0,larguraTela,alturaTela); //comportamento da imagem de fundo
		batch.draw(avatar[(int) variacao],120,posicaoIniciaVertical); //comportamento possição eixo x e y
        batch.draw(obstaculoTopo,posicaoMovimentoobstaculoHorizontal,lugarDragao1 +dragaoMovendoAleatorio1 -espacoEntrObstaculos);
        batch.draw(obstaculobaixo,posicaoMovimentoobstaculoHorizontal,lugarDragao2 + dragaoMovendoAleatorio1 -espacoEntrObstaculos);
        batch.draw(obstaculoMeio,posicaoMovimentoobstaculoHorizontal,lugarDragao3 + dragaoMovendoAleatorio1 -espacoEntrObstaculos) ;
        font.draw(batch,String.valueOf(pontuacao),larguraTela/2,alturaTela -50);


        if (estadoDoJogo ==2) {
			batch.draw(gameOver, larguraTela / 2 -gameOver.getWidth()/2, alturaTela/ 2 );
			mensagemDeMorte.draw(batch,"clique para jogar novamente", larguraTela/2 -fundo.getWidth()/2,alturaTela/2 -gameOver.getHeight());

		}



         batch.end();

		avatarCirculo.set(120 + avatar[0].getWidth()/2,posicaoIniciaVertical + avatar[0].getWidth()/2,avatar[0].getWidth()/2);
		dragaoObstaculo1.set(posicaoMovimentoobstaculoHorizontal + obstaculoTopo.getWidth()/2 ,lugarDragao1 + dragaoMovendoAleatorio1 -espacoEntrObstaculos +obstaculoTopo.getWidth()/2,obstaculoTopo.getWidth()/2);
        dragaoObstaculo2.set(posicaoMovimentoobstaculoHorizontal +obstaculoMeio.getWidth()/2,lugarDragao2 +dragaoMovendoAleatorio1 -espacoEntrObstaculos+obstaculoMeio.getWidth()/2,obstaculoMeio.getWidth()/2);
        dragaoObstaculo3.set(posicaoMovimentoobstaculoHorizontal +obstaculobaixo.getWidth()/2,lugarDragao3 +dragaoMovendoAleatorio1 - espacoEntrObstaculos+obstaculobaixo.getWidth()/2,obstaculobaixo.getWidth()/2);

         //desenhando formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(avatarCirculo.x,avatarCirculo.y,avatarCirculo.radius);
		shape.circle(dragaoObstaculo1.x,dragaoObstaculo1.y,dragaoObstaculo1.radius);
		shape.circle(dragaoObstaculo2.x,dragaoObstaculo2.y,dragaoObstaculo2.radius);
		shape.circle(dragaoObstaculo3.x,dragaoObstaculo3.y,dragaoObstaculo3.radius);
		shape.setColor(Color.WHITE);

		shape.end();*/

		//teste de colisão
		if (Intersector.overlaps(avatarCirculo,dragaoObstaculo3) || Intersector.overlaps(avatarCirculo,dragaoObstaculo1 ) || Intersector.overlaps(avatarCirculo,dragaoObstaculo2)){
            //Codigo para testar o log
			//Gdx.app.log("Contato", "Houve colisão");
				estadoDoJogo = 2;

		}


	}
	

}

