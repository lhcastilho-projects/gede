package diagram.componente;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;

/**
 * O Vertice é uma classe abstrata utilizada pelo GEDE para permitir a criação 
 * de novos modelos de vértices para as aplicações.
 *
 * Na criação de um novo modelo de vértice, está classe deverá necessariamente 
 * ser herdada e os métodos abstratos desenharVertice e coordenadaPertenceVertice
 * devem ser sobrescritos. Ela também possui, além dos requisitos básicos do vértice,
 * um atributo código disponível para ser utilizado junto ao banco de dados para
 * facilitar no armazenamento de suas informações.
 *
 * Para o funcionamento correto do editor, o método desenharVertice deverá implementar
 * todo o código necessário para desenhar um vértice e o método coordenadaPertenceVertice
 * deverá implementar a área ocupada pelo vértice. Está área, por exemplo, são todos 
 * os pontos contidos em um retângulo. Entretanto, nas classes do pacote java.awt.geom
 * já existe implementações que calculam se uma coordenada está contida dentro de uma 
 * área ou não.
 *
 * @author Luis Henrique Castilho da Silva
 * @see java.awt.geom.Rectangle2D
 * @see java.awt.geom.Ellipse2D
 * @see java.awt.geom.RoundRectangle2D
 * @see ArestaSimples
 */
public abstract class Vertice extends Componente
{
	/**
	 * A coordenada inicial no eixo X.
	 */
	public static int 		COORDXINICIAL = 1;
	
	/**
	 * A coordenada inicial no eixo Y.
	 */
	public static int		COORDYINICIAL = 1;
	
	/**
	 * A largura padrão do vértice.
	 */
	public static int		LARGURAPADRAO = 60;
	
	/**
	 * A largura mínima do vértice.
	 */
	public static int		LARGURAMINIMA = 5;
	
	/**
	 * A largura máxima do vértice.
	 */
	public static int		LARGURAMAXIMA = 300;
	
	/**
	 * A altura padrão do vértice.
	 */
	public static int		ALTURAPADRAO = 40;
	
	/**
	 * A altura mínima do vértice.
	 */
	public static int		ALTURAMINIMA = 5;
	
	/**
	 * A largura máxima do vértice.
	 */
	public static int		ALTURAMAXIMA = 300;
	
	private static Color	COR_FUNDO_SELECAO = Color.red,
							COR_BORDA_SELECAO = Color.black;
	
	private	int				x,
							y,
							largura,
							altura;
	/**
	 * Instância um novo vértice.
	 *
	 * Como a classe Vertice é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @see diagram.Grafo#getVertice
	 */					
	public Vertice()
	{
		super();
		setX(COORDXINICIAL);
		setY(COORDYINICIAL);
		setLargura(LARGURAPADRAO);
		setAltura(ALTURAPADRAO);
	}
	
	/**
	 * Instância um novo vértice alterando suas coordenadas iniciais.
	 *
	 * Como a classe Vértice é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @param x a coordenada do vértice no eixo X
	 * @param y a coordenada do vértice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int x, int y)
	{
		this();
		setX(x);
		setY(y);
	}
	
	/**
	 * Instância um novo vértice alterando suas coordenadas, largura e altura 
	 * inicial.
	 *
	 * Como a classe Vértice é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @param x a coordenada do vértice no eixo X
	 * @param y a coordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int x, int y, int largura, int altura)
	{
		this();
		setX(x);
		setY(y);
		setLargura(largura);
		setAltura(altura);
	}
	
	/**
	 * Instância um novo vértice alterando suas coordenadas, largura, altura 
	 * inicial. Além disso um interio que representa o código do vértice também
	 * é alterado.
	 *
	 * Como a classe Vértice é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 * 
	 * @param codigo o inteiro que define o código para o vértice
	 * @param x a coordenada do vértice no eixo X
	 * @param y a coordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int codigo, int x, int y, int largura, int altura)
	{
		this();
		setCodigo(codigo);
		setX(x);
		setY(y);
		setLargura(largura);
		setAltura(altura);
	}

//Métodos Get...
	
	/**
	 * Retorna a coordenada do vértice no eixo horizontal ou eixo X.
	 * 
	 * @param a coordenada no eixo X
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Retorna a coordenada do vértice no eixo vertical ou eixo Y..
	 * 
	 * @param a coordenada no eixo Y
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Retorna a largura do vértice.
	 * 
	 * @param o comprimento da largura
	 */
	public int getLargura()
	{
		return largura;
	}
	
	/**
	 * Retorna a altura do vértice.
	 * 
	 * @param o comprimento da altura
	 */
	public int getAltura()
	{
		return altura;
	}
	
//Métodos Set...

	/**
	 * Altera a coordenada do vértice no eixo X.
	 *
	 * @param x o inteiro que define a coordenada
	 */
	public void setX(int x)
	{
		if (x > 0)
			this.x = x;
		else
			this.x = COORDXINICIAL;
	}
	
	/**
	 * Altera a coordenada do vértice no eixo Y.
	 *
	 * @param y o inteiro que define a coordenada
	 */
	public void setY(int y)
	{
		if (y > 0)
			this.y = y;
		else
			this.y = COORDYINICIAL;	
	}
	
	/**
	 * Altera a largura do vértice.
	 *
	 * @param x o inteiro que define a largura
	 */
	public void setLargura(int largura)
	{
		if ((largura >= LARGURAMINIMA) && (largura <= LARGURAMAXIMA))
			this.largura = largura;
		else
		{
			if (largura < LARGURAMINIMA)
				this.largura = LARGURAMINIMA;
			else
				this.largura = LARGURAMAXIMA;
		}
	}
	
	/**
	 * Altera a altura do vértice.
	 *
	 * @param x o inteiro que define a altura
	 */
	public void setAltura(int altura)
	{
		if ((altura >= ALTURAMINIMA) && (altura <= ALTURAMAXIMA))
			this.altura = altura;
		else
		{
			if (altura < ALTURAMINIMA)
				this.altura = ALTURAMINIMA;
			else
				this.altura = ALTURAMAXIMA;
		}
	}
	
//Metodo para selecionar o vértice

 	/**
 	 * Método utilizado pela classe AreaAplicativo e AreaApplet do pacote GEDE 
 	 * para desenhar os quadrados de seleção do vértice. Este método é invocado
 	 * pela pelas classe sempre que o vértice estiver selecionado.
 	 *
 	 * @param desenho o contexto gráfico na qual o quadrados serão desenhados
 	 * @param ladoQuadradoSelecao o comprimento do lado do quadrado de seleção
 	 */
	public void selecionarVertice(Graphics desenho, int ladoQuadradoSelecao)
	{
		if (getSelecionado())
		{
			desenho.setColor(COR_FUNDO_SELECAO);
			//Canto Superior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Superior Direito
			desenho.fillRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Direito
			desenho.fillRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			
			//Desenha a borda da seleção do vértice
			desenho.setColor(COR_BORDA_SELECAO);
			//Canto Superior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Superior Direito
			desenho.drawRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Direito
			desenho.drawRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
		}
	}
	
	//Metodos abstratos
	
	/**
	 * Desenha o vértice no contexto gráfico passado pelo parâmetro. No editor
	 * este método e chamado sempre que for necessário desenhar o vértice.
	 *
	 * @param desenho o contexto gráfico na qual desenhará o vértice
	 * @param componente o componente na qual o contexto gráfico está inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public abstract void desenharVertice(Graphics desenho, Component componente);
	
	/**
	 * Identifica se a coordenada está contida na área do vértice. Ele retorna 
	 * verdadeiro se o ponto estiver dentro dos limites do vértice caso contrário
	 * retorna-se falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada está contida no vértice
	 */
	public abstract boolean coordenadaPertenceVertice(int x, int y);
}