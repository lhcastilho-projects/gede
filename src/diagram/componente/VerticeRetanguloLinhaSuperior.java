package diagram.componente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;

/**
 * Cria um novo vértice dentro do contexto do editor de diagramas GEDE no 
 * formato de um retângulo e uma linha horizontal localizada na parte 
 * interna superior do retângulo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeRetanguloLinhaSuperior extends Vertice
{
	/**
	 * A largura padrão da borda do vértice.
	 */
	public static final int		LARGURABORDAPADRAO = 1;
	
	/**
	 * A largura máxima permitida para a borda do vértice.
	 */
	public static final int		LARGURAMAXIMABORDA = 10;
	
	/**
	 * O comprimento, em pixel, da distância entre a linha interna e a borda do 
	 * vértice.
	 */
	public static final int		DISTANCIALINHABORDA = 2;
	
	/**
	 * A cor da borda padrão para o vértice.
	 */				
	public static final Color	CORBORDAPADRAO = Color.black;
	
	/**
	 * A cor de fundo padrão para o vértice.
	 */
	public static final Color	CORFUNDOPADRAO = Color.white;
		
	private int 	larguraBorda;
	
	private Color	corBorda,
					corFundo;
	
	/**
	 * Cria um novo vértice com a forma de um retângulo com uma linha horizontal
	 * localizada internamente próximo a borda superior.
	 *
	 * @see diagram.Grafo#getVertice
	 */		
	public VerticeRetanguloLinhaSuperior() 
	{
		super();
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}	
	
	/**
	 * Cria um novo vértice na forma de um retângulo com uma linha horizontal
	 * interna próximo a borda superior e localizado nas coordenadas passada 
	 * pelo parâmetro.
	 *
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloLinhaSuperior(int x, int y)
	{
		super(x, y);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo vértice na forma de um retângulo com uma linha horizontal
	 * interna próximo a borda superior localizado nas coordenadas passada pelo 
	 * parâmetro, com largura e altura definida além de um código inicial.
	 * 
	 * @param codigo o inteiro que define o código para o vértice
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloLinhaSuperior(int codigo, int x, int y, int largura, int altura)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo vértice na forma de um retângulo com uma linha horizontal
	 * interna próximo a borda superior localizado nas coordenadas passada pelo
	 * parâmetro além de outros atributos definidos neste construtor.
	 * 
	 * @param codigo o inteiro que define o código para o vértice
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @param larguraBorda a largura, em pixels, da borda do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloLinhaSuperior(int codigo, int x, int y, int largura, int altura, int larguraBorda)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(larguraBorda);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
//	Métodos GET...
	
	/**
	 * Retorna a largura da borda atual do vértice.
	 *
	 * @return a largura da borda
	 */
	public int getLarguraBorda()
	{
		return larguraBorda;
	}
	
	/**
	 * Retorna a cor atual utilizada para a borda.
	 *
	 * @return o cor da borda
	 */
	public Color getCorBorda()
	{
		return corBorda;
	}
	
	/**
	 * Retorna a cor atual de fundo utilizada no vértice.
	 *
	 * @return a cor de fundo
	 */
	public Color getCorFundo()
	{
		return corFundo;	
	}
	
	
//	Métodos SET..

	/**
	 * Altera a largura de borda do vértice.
	 *
	 * @param larguraBorda a nova largura
	 */
	public void setLarguraBorda(int larguraBorda)
	{
		if ((larguraBorda > 0) && (larguraBorda <= LARGURAMAXIMABORDA))
			this.larguraBorda = larguraBorda;
		else
			this.larguraBorda = LARGURABORDAPADRAO;	
	}
	
	/**
	 * Altera a cor atual da borda.
	 *
	 * @param corBorda a nova cor
	 */
	public void setCorBorda(Color corBorda)
	{
		this.corBorda = corBorda;	
	}
	
	/**
	 * Altera a cor atual utilizada no fundo do vértice.
	 *
	 * @param corFundo a nova cor
	 */
	public void setCorFundo(Color corFundo)
	{
		this.corFundo = corFundo;	
	}
	
	/**
	 * Desenha um retângulo com uma linha horizontal localizada internamente 
	 * próximo a borda superior no contexto gráfico passado pelo parâmetro.
	 *
	 * @param desenho o contexto gráfico na qual desenhará o vértice
	 * @param componente o componente na qual o contexto gráfico está inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		int i;
		
		//Desenha o fundo do vértice...
		desenho.setColor(corFundo);
		desenho.fillRect(getX(), getY(), getLargura(), getAltura());		
		
		//Desenhar Borda...
		desenho.setColor(corBorda);

		for (i = 0;i < larguraBorda; i++)
			desenho.drawRect(getX() + i, getY() + i, getLargura() - i * 2, getAltura() - i * 2);
		//Desenhar a Linha Interna
		for (i = 0;i < larguraBorda;i++)
		{
			//Verifica se a linha superior não ultrapassa a altura do vértice
			if (getY() + larguraBorda + DISTANCIALINHABORDA + i <= getY() + getAltura())
				desenho.drawLine(getX(), getY() + larguraBorda + DISTANCIALINHABORDA + i, getX() + getLargura(), getY() + larguraBorda + DISTANCIALINHABORDA + i);
		}
	}
	
	/**
	 * Identifica se a coordenada está contida na área do retângulo. O método 
	 * retorna verdadeiro se o ponto estiver dentro dos limites caso contrário 
	 * retorna-se falso.
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada está contida no vértice
	 */
	public final boolean coordenadaPertenceVertice(int x, int y)
	{
		if ((x >= getX()) && (x <= getLargura() + getX()))
			if ((y >= getY()) && (y <= getAltura() + getY()))
				return true;
			
		return false;
	}	
}