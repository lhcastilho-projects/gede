package diagram.componente;

import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;

/**
 * É uma classe abstrata básica utilizada no contexto do pacote diagram
 * para permitir a criação de novos modelos de arestas para as aplicações.
 *
 * Na criação de um novo modelo de aresta, a classe <B>Aresta</B> deverá 
 * necessariamente ser herdada e os métodos abstratos desenharAresta e 
 * coordenadaPertenceAresta devem ser sobrescritos. Ela também possui um atributo
 * código disponível para ser utilizado junto ao banco de dados.
 *
 * Para o funcionamento correto do editor, o método desenharAresta deverá implementar
 * todo o código necessário para desenhar uma aresta entre dois vértices e o método
 * coordenadaPertenceAresta deverá implementar a área de ocupada pela aresta. Está
 * área, por exemplo, são os pontos contidos em uma reta. Na classe Line2D, já existe
 * implementações para determinar se um ponto está contido em uma reta.
 *
 * @author Luis Henrique Castilho da Silva
 * @see java.awt.geom.Line2D
 * @see ArestaSimples
 */
public abstract class Aresta extends Componente
{
	/**
	 * O número de quebras padrão de uma instância de aresta.
	 */
	public static final int		NUMEROQUEBRAPADRAO = 0;
	
	/**
	 * O número máximo de quebras da aresta permitido no editor.
	 */
	public static final int		NUMEROMAXIMOQUEBRAS = 50;
	
	/**
	 * A coordenada no eixo X padrão para uma nova instância de aresta.
	 */
	public static final int		COORDENADAPADRAOX = 1;
	
	/**
	 * * A coordenada no eixo Y padrão para uma nova instância de aresta.
	 */
	public static final int		COORDENADAPADRAOY = 1;
	
	private static final Color	COR_FUNDO_SELECAO = Color.blue,
								COR_BORDA_SELECAO = Color.black;
								
	private int					numeroQuebras;
	
	private LinkedList			pontosQuebraX,
								pontosQuebraY;
	
	private Vertice				verticeOrigem,
								verticeDestino;
	
	/**
	 * Instância uma nova aresta.
	 *
	 * Como a classe Aresta é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @param verticeOrigem o vértice inicial na qual a aresta está associada
	 * @param verticeDestino o vértice final na qual a aresta está associada
	 * @see diagram.Grafo#getAresta
	 */
	protected Aresta(Vertice verticeOrigem, Vertice verticeDestino)
	{
		setNumeroQuebras(NUMEROQUEBRAPADRAO);
		
		pontosQuebraX = new LinkedList();
		pontosQuebraY = new LinkedList();
		
		setVerticeOrigem(verticeOrigem);
		setVerticeDestino(verticeDestino);
	}
	
	/**
	 * Instância uma nova aresta alterando seu código inicial.
	 *
	 * Como a classe Aresta é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @param codigo o novo código da aresta
	 * @param verticeOrigem o vértice inicial na qual a aresta está associada
	 * @param verticeDestino o vértice final na qual a aresta está associada
	 * @see diagram.Grafo#getAresta
	 */
	protected Aresta(int codigo, Vertice verticeOrigem, Vertice verticeDestino) 
	{
		this(verticeOrigem, verticeDestino);
		setCodigo(codigo);
	}	

// Métodos Get...

	/**
	 * Retorna o número total de pontos especiais contidos na aresta. Estes pontos
	 * especiais são as quebras existentes em uma aresta.
	 *
	 * @return o inteiro especificando o número de quebras existente na aresta
	 */
	public int getNumeroQuebras()
	{
		return numeroQuebras;
	}
	
	/**
	 * Retorna as coordenadas no eixo X de todas as quebras existentes na aresta.
	 *
	 * @return o vetor de inteiro contendo todos as coordenadas
	 */
	public int[] getTodasQuebrasX()
	{
		int 		i, pontosQuebraX[];
		Integer		coordTemp;
		
		
		pontosQuebraX = new int[this.pontosQuebraX.size()];
		
		for (i = 0;i < this.pontosQuebraX.size(); i++)
		{
			coordTemp = (Integer) this.pontosQuebraX.get(i);
			pontosQuebraX[i] = coordTemp.intValue();
		}
			
		return pontosQuebraX;
	}
	
	/**
	 * Retorna as coordenadas no eixo Y de todas as quebras existentes na aresta.
	 *
	 * @return o vetor de inteiro contendo todos as coordenadas
	 */
	public int[] getTodasQuebrasY()
	{
		int 		i, pontosQuebraY[];
		Integer		coordTemp;
		
		
		pontosQuebraY = new int[this.pontosQuebraY.size()];
		
		for (i = 0;i < this.pontosQuebraY.size(); i++)
		{
			coordTemp = (Integer) this.pontosQuebraY.get(i);
			pontosQuebraY[i] = coordTemp.intValue();
		}
			
		return pontosQuebraY;
	}
	
	/**
	 * Retorna a coordenada no eixo X de uma quebra especificada no parâmetro.
	 *
	 * @return o inteiro que representa a coordenada
	 */
	public int getCoordenadaQuebraX(int indiceCoordenada)
	{
		Integer coordTemp;
		
		if ((indiceCoordenada >= 0) && (indiceCoordenada < pontosQuebraX.size()))
		{
			coordTemp = (Integer) pontosQuebraX.get(indiceCoordenada);
			return coordTemp.intValue();
		}
		
		return -1;
	}

	/**
	 * Retorna a coordenada no eixo Y de uma quebra especificada no parâmetro.
	 *
	 * @return um inteiro que representa a coordenada
	 */	
	public int getCoordenadaQuebraY(int indiceCoordenada)
	{
		Integer coordTemp;
		
		if ((indiceCoordenada >= 0) && (indiceCoordenada < pontosQuebraY.size()))
		{
			coordTemp = (Integer) pontosQuebraY.get(indiceCoordenada);
			return coordTemp.intValue();
		}
		
		return -1;
	}
	
	/**
	 * Retorna o objeto vértice origem atual da aresta.
	 *
	 * @return o objeto Vertice.
	 * @see Vertice#Vertice
	 */
	public Vertice getVerticeOrigem()
	{
		return verticeOrigem;
	}

	/**
	 * Retorna o objeto vértice destino atual da aresta.
	 *
	 * @return um objeto Vertice.
	 * @see Vertice#Vertice
	 */	
	public Vertice getVerticeDestino()
	{
		return verticeDestino;
	}
	
//Métodos Set...

	/**
	 * Seta o número de quebras da aresta. Este método deverá ser chamado sempre
	 * que houver a necessidade de criar os pontos especiais (quebras) para as arestas.
	 *
	 * @param numeroQuebras o número de ponto especiais na aresta
	 * @see Aresta#setTodasQuebrasX
	 * @see Aresta#setTodasQuebrasY
	 */
	public void setNumeroQuebras(int numeroQuebras)
	{
		if ((numeroQuebras >= 0) && (numeroQuebras < NUMEROMAXIMOQUEBRAS))
			this.numeroQuebras = numeroQuebras;
		else
			this.numeroQuebras = NUMEROQUEBRAPADRAO;
	}
	
	/**
	 * Altera as coordenadas do eixo X dos pontos especiais (quebras da aresta) da aresta. Os valores
	 * que excederem o número máximo de quebras serão descartados. 
	 *
	 * @param pontosQuebraX o vetor contendo as coordenadas do eixo X de todos os pontos
	 * @see Aresta#setNumeroQuebras
	 */
	public void setTodasQuebrasX(int pontosQuebraX[])
	{
		int i;
		
		for (i = 0; i < numeroQuebras; i++)
		{
			if (pontosQuebraX.length > i)
			{
				if (pontosQuebraX[i] > 0)
					this.pontosQuebraX.add(new Integer(pontosQuebraX[i]));
				else
					this.pontosQuebraX.add(new Integer(COORDENADAPADRAOX));
			}
			else
				this.pontosQuebraX.add(new Integer(COORDENADAPADRAOX));
		}
	}

	/**
	 * Altera as coordenadas do eixo Y dos pontos especiais (quebras da aresta) da aresta. Os valores
	 * que excederem o número máximo de quebras serão descartados. 
	 *
	 * @param pontosQuebraY o vetor contendo as coordenadas do eixo Y de todos os pontos
	 * @see Aresta#setNumeroQuebras
	 */
	public void setTodasQuebrasY(int pontosQuebraY[])
	{
		int i;
		
		for (i = 0; i < numeroQuebras; i++)
		{
			if (pontosQuebraY.length > i)
			{
				if (pontosQuebraY[i] > 0)
					this.pontosQuebraY.add(new Integer(pontosQuebraY[i]));
				else
					this.pontosQuebraY.add(new Integer(COORDENADAPADRAOY));	
			}
			else
				this.pontosQuebraY.add(new Integer(COORDENADAPADRAOY));				
		}
	}
	
	/**
	 * Altera a coordenada do eixo X do ponto especial (quebra da aresta) da 
	 * aresta especificada pelo parâmetro. 
	 *
	 * @param indice a localização na lista encadeada do ponto especial a ser alterado
	 * @param coordenadaX a coordenada do eixo X do ponto
	 * @see Aresta#setNumeroQuebras
	 */
	public void setQuebraX(int indice, int coordenadaX)
	{
		if ((indice >= 0) && (indice < numeroQuebras))
			if (coordenadaX > 0)
				pontosQuebraX.add(indice, new Integer(coordenadaX));
	}
	
	/**
	 * Altera a coordenada do eixo Y do ponto especial (quebra da aresta) da 
	 * aresta especificada pelo parâmetro. 
	 *
	 * @param indice a localização na lista encadeada do ponto especial a ser alterado
	 * @param coordenadaY a coordenada do eixo Y do ponto
	 * @see Aresta#setNumeroQuebras
	 */
	public void setQuebraY(int indice, int coordenadaY)
	{
		if ((indice >= 0) && (indice < numeroQuebras))
			if (coordenadaY > 0)
				pontosQuebraY.add(indice, new Integer(coordenadaY));
	}
	
	/**
	 * Altera o vértice origem da aresta.
	 *
	 * @param verticeOrigem o objeto Vertice
	 * @see Vertice#Vertice
	 */
	public void setVerticeOrigem(Vertice verticeOrigem)
	{
		this.verticeOrigem = verticeOrigem;
	}
	
	/**
	 * Altera o vértice destino da aresta.
	 *
	 * @param verticeDestino o objeto Vertice
	 * @see Vertice#Vertice
	 */
	public void setVerticeDestino(Vertice verticeDestino)
	{
		this.verticeDestino = verticeDestino;
	}
	
	/**
	 * Remove o ponto especial (quebra) da aresta especificado no 
	 * parâmetro.
	 *
	 * @param o inteiro que define a localização do ponto na lista
	 */
	public void removerQuebra(int indiceQuebra)
	{
		if ((indiceQuebra >= 0) && (indiceQuebra < numeroQuebras))
		{
			pontosQuebraX.remove(indiceQuebra);
			pontosQuebraY.remove(indiceQuebra);
			numeroQuebras--;
		}
	}
	
	/**
	 * Remove todos os pontos especiais (quebras) da aresta.
	 */
	public void removerTodasQuebras()
	{
		pontosQuebraX.clear();
		pontosQuebraY.clear();
		numeroQuebras = 0;
	}
//Metodo para selecionar a Aresta

	/**
	 * Este método deverá ser chamado sempre que houver a necessidade de desenhar
	 * quadrados vermelhos indicando que a aresta está selecionada.
	 *
	 * @param desenho o contexto gráfico onde desenhará os simbolos que representam a seleção da aresta
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public void selecionarAresta(Graphics desenho, int ladoQuadradoSelecao)
	{
		int 		i, pontoX, pontoY, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal;
		
		if (getSelecionado())
		{
			pontoXInicial = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
			pontoYInicial = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
			if (numeroQuebras == 0)
			{
				//O ponto está relacionado com o vértice destino
				pontoXFinal = verticeDestino.getX() + verticeDestino.getLargura() / 2;
				pontoYFinal = verticeDestino.getY() + verticeDestino.getAltura() / 2;
			}
			else
			{
				//O ponto está relacionado com um ponto intermediário
				pontoXFinal = getCoordenadaQuebraX(0);
				pontoYFinal = getCoordenadaQuebraY(0);
			}
			desenharQuadradoSelecao(desenho, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal, verticeOrigem.getLargura(), verticeOrigem.getAltura(), ladoQuadradoSelecao);
			
			pontoXInicial = verticeDestino.getX() + verticeDestino.getLargura() / 2;
			pontoYInicial = verticeDestino.getY() + verticeDestino.getAltura() / 2;
			if (numeroQuebras == 0)
			{
				//O ponto está relacionado com o vértice origem
				pontoXFinal = verticeOrigem.getX() + verticeOrigem.getLargura() / 2;
				pontoYFinal = verticeOrigem.getY() + verticeOrigem.getAltura() / 2;
			}
			else
			{
				//O ponto está relacionado com um ponto intermediario
				pontoXFinal = getCoordenadaQuebraX(numeroQuebras - 1);
				pontoYFinal = getCoordenadaQuebraY(numeroQuebras - 1);
			}
			desenharQuadradoSelecao(desenho, pontoXInicial, pontoYInicial, pontoXFinal, pontoYFinal, verticeDestino.getLargura(), verticeDestino.getAltura(), ladoQuadradoSelecao);			
			
			for (i = 0; i < numeroQuebras; i++)	
			{
				pontoX = getCoordenadaQuebraX(i);
				pontoY = getCoordenadaQuebraY(i);
				
				//Desenha o fundo dos quadrados de seleção
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoX - ladoQuadradoSelecao/2, pontoY - ladoQuadradoSelecao/2, ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de seleção
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoX - ladoQuadradoSelecao/2, pontoY - ladoQuadradoSelecao/2, ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
		}
	}
	
	/**
	 * Define se ocorreu um clique sobre os desenho dos quadrados que representam
	 * a aresta selecionada. Estes são os quadrados azuis que aparecem quando o
	 * usuário clica sobre uma aresta com o botão esquerdo do mouse.
	 *
	 * Caso as coordenadas não pertecem a área de qualquer quadrado de seleção
	 * um interio -1 é retornado, caso contrário a localização na lista encadeada
	 * é retornado.
	 *
	 * @param x a coordenada do ponto no eixo x
	 * @param y a coordenada do ponto no eixo y 
	 * @param ladoQuadradoSelecao o comprimento do lado quadrado de seleção
	 * @return a localização do quadrado clicado na lista encadeada
	 */
	public int cliqueSobreQuebraAresta(int x, int y, int ladoQuadradoSelecao)
	{
		int			i, pontoQuebraX, pontoQuebraY;
		
		for (i = 0; i < getNumeroQuebras(); i++)
		{
			pontoQuebraX = getCoordenadaQuebraX(i) ;
			pontoQuebraY = getCoordenadaQuebraY(i) - ladoQuadradoSelecao / 2;
			
			if ((x >= (pontoQuebraX - ladoQuadradoSelecao / 2)) && (x <= (pontoQuebraX + ladoQuadradoSelecao / 2)))
				if ((y >= (pontoQuebraY - ladoQuadradoSelecao / 2)) && (y <= (pontoQuebraY + ladoQuadradoSelecao / 2)))
					return i;
		}
		
		return -1;
	}
	
	/*
	 * Desenha a aresta no contexto gráfico do editor.
	 *
	 * @param desenho o contexto gráfico na qual desenhará a aresta
	 * @see AreaApplet#paint
	 * @see AreaAplicativo#paintComponent
	 */
	public abstract void desenharAresta(Graphics desenho);
	
	/**
	 * Identifica se a coordenada está contida na aresta, ou seja, na reta.
	 * Retorna verdadeiro se o ponto estiver sobre a aresta e falso caso contrário. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada está contida na aresta
	 */
	public abstract boolean coordenadaPertenceAresta(int x, int y);
	
	//Métodos privados da classe aresta
	private void desenharQuadradoSelecao(Graphics desenho, int pontoXInicial, int pontoYInicial, int pontoXFinal, int pontoYFinal, int larguraInicial, int alturaInicial, int ladoQuadradoSelecao)
	{
		int 	auxX, auxY, distPontoInicial;
		double	anguloVertice, anguloAresta;
		
		auxX = pontoXInicial - pontoXFinal;
		auxY = pontoYInicial - pontoYFinal;
		
		if (auxX == 0)
		{
			//Aresta na vertical
			if (pontoYInicial > pontoYFinal)
			{
				//Pontos na mesma vertical com inicial abaixo do final
				
				//Desenha o fundo dos quadrados de seleção
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de seleção
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
			else
			{
				//Pontos na mesma vertical com inicial acima do final
				
				//Desenha o fundo dos quadrados de seleção
				desenho.setColor(COR_FUNDO_SELECAO);
				desenho.fillRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				
				//Desenha a borda dos quadrados de seleção
				desenho.setColor(COR_BORDA_SELECAO);
				desenho.drawRect(pontoXInicial - ladoQuadradoSelecao / 2, pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
			}
		}
		else
		{
			if (auxY == 0)
			{
				//Aresta na horizontal
				if (pontoXInicial > pontoXFinal)
				{
					//Ponto na mesma horizontal mas com inicial a direita de final
					
					//Desenha o fundo dos quadrados de seleção
					desenho.setColor(COR_FUNDO_SELECAO);
					desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
					
					//Desenha a borda dos quadrados de seleção
					desenho.setColor(COR_BORDA_SELECAO);
					desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				}
				else
				{
					//Ponto na mesma horizontal mas com final a direita de inicial
					
					//Desenha o fundo dos quadrados de seleção				
					desenho.setColor(COR_FUNDO_SELECAO);
					desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
					
					//Desenha a borda dos quadrados de seleção
					desenho.setColor(COR_BORDA_SELECAO);
					desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
				}
			}
			else
			{
				//Aresta inclinada
				anguloVertice = (double) (alturaInicial / 2) / (double) (larguraInicial / 2);
				anguloAresta = (double) auxY / (double) auxX;
				
				if (pontoXInicial > pontoXFinal)
				{
					//Ponto inicial está a direita e o ponto final a esquerda
					if (pontoYInicial > pontoYFinal)
					{
						//Ponto inicial está abaixo e o ponto final acima (4 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
					else
					{
						//Ponto inicial está acima e o ponto final abaixo (1 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus
						anguloAresta *= -1;
						
						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial - distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
				}
				else
				{
					//Ponto inicial está a esquerda e o ponto final a direita
					if (pontoYInicial > pontoYFinal)
					{
						//Ponto inicial está abaixo e o ponto final acima (3 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus
						anguloAresta *= -1; 

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial - distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial - (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
					}
					else
					{
						//Ponto inicial está acima e o ponto final abaixo (2 Quadrante para o ponto Inicial)
						//Cobrir a faixa de 0 a 90 graus

						if (anguloVertice > anguloAresta)
						{
							//Angulo da aresta menor do que o vertice
							distPontoInicial = (auxY * (larguraInicial / 2)) / auxX;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + (larguraInicial / 2) - (ladoQuadradoSelecao / 2), pontoYInicial + distPontoInicial - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
						}
						else
						{
							//Angulo da aresta maior do que o vertice
							distPontoInicial = (auxX * (alturaInicial / 2)) / auxY;
							
							//Desenha o fundo dos quadrados de seleção
							desenho.setColor(COR_FUNDO_SELECAO);
							desenho.fillRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);
							
							//Desenha a borda dos quadrados de seleção
							desenho.setColor(COR_BORDA_SELECAO);
							desenho.drawRect(pontoXInicial + distPontoInicial - (ladoQuadradoSelecao / 2), pontoYInicial + (alturaInicial / 2) - (ladoQuadradoSelecao / 2), ladoQuadradoSelecao, ladoQuadradoSelecao);							
						}
						
					}//Fim do if (verificar o quadrante)
					
				}//Fim do if (verificar o lado dos pontos inicial e final)

			}//Fim do if (verificar aresta horizontal e inclinada)

		}//Fim do if (verificar aresta vertical)
	
	}//Fim do método desenharQuadradoSelecao
}