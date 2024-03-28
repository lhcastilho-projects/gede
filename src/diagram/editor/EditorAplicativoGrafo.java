package diagram.editor;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import diagram.Grafo;

import diagram.menu.JMenuMouse;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

/**
 * Está é a classe principal do editor de aplicativo. Uma instância simples desta 
 * classe cria-se um novo editor de grafo preparado para editar e apresentar os diagramas.
 * Este novo editor utiliza todos os objetos do pacote Swing.
 *
 * A classe chama automaticamente a classe <B>AreaAplicativo</B> criando uma área padrão
 * para editar o diagrama.
 *
 * @author Luis Henrique Castilho da Silva
 * @see EditorAppletGrafo
 */
public class EditorAplicativoGrafo extends JFrame implements AdjustmentListener, ComponentListener, KeyListener
{
	/**
	 * O valor padrão do canto superior esquerdo do editor no eixo horizontal.
	 */
	public static final int		LOCALEDITORPADRAOHOR = 100;
	
	/**
	 * O valor padrão do canto superior esquerdo do editor no eixo vertical.
	 */
	public static final int		LOCALEDITORPADRAOVER = 100;
	
	/**
	 * A largura padrão da janela do editor.
	 */
	public static final int		TAMANHOEDITORPADRAOHOR = 600;
	
	/**
	 * A altura padrão da janela do editor.
	 */
	public static final int		TAMANHOEDITORPADRAOVER = 400;
	
	/**
	 * O valor de edição do grafo.
	 */
	public static final int		EDITAR = 1;
	
	/**
	 * O valor de não edição do grafo. Este grafo será somente apresentado.
	 */						
	public static final int		NAO_EDITAR = 0;
	
	private static int			DISTANCIA_EIXO_X = 50,
								DISTANCIA_EIXO_Y = 80;
	
	//Tratam o layout do Aplicativo
	private GridBagLayout		layoutJanela;
	private GridBagConstraints	componenteLayout;
	
	//Barras de rolagem Vertical e Horizontal...
	private JScrollBar			barraVertical,
								barraHorizontal;
	
	//Variavel responsavel pela captação da tela...
	private Container			areaLayout;

	//Painel onde será apresentado o grafo...	
	private AreaAplicativo		areaGrafo;
	
	//Variaveis reponsaveis pelo tamanho e localizacao do editor...
	private int					editar,
								posicaoAtualBarraVertical,
								posicaoAtualBarraHorizontal;
								
	private Grafo				grafo;
	
	private	boolean				teclaShiftPressionada,
								teclaCtrlPressionada,
								teclaDeletePressionada,
								teclaCtrlZPressionada;
	
	/**
	 * Cria um editor de aplicativo padrão com o título <B>Editor de Grafo</B> sem
	 * nenhum grafo apresentado. Este editor poderá ser editado pelo usuário final.
	 */			
	public EditorAplicativoGrafo() 
	{
		//Título do EditorDiagrama
		super("Editor de Grafo");
		
		//Seta o editor para editar o grafo...
		editar = EDITAR;
		
		//O grafo inicial não existe...
		grafo = null;
		
		layoutJanela = new GridBagLayout();
		componenteLayout = new GridBagConstraints();
		
		//Recupera a área do EditorGrafo e seta o layout de inserção dos objetos...
		areaLayout = getContentPane();
		areaLayout.setLayout(layoutJanela);
		
		//Instanciação da barra de rolagem Vertical
		barraVertical = new JScrollBar();
		barraVertical.setEnabled(false);
		barraVertical.setOrientation(JScrollBar.VERTICAL);
		barraVertical.setUnitIncrement(10);
		barraVertical.addAdjustmentListener(this);
		
		//Instanciação da barra de rolagem Horizontal
		barraHorizontal = new JScrollBar();
		barraHorizontal.setEnabled(false);
		barraHorizontal.setOrientation(JScrollBar.HORIZONTAL);
		barraHorizontal.setUnitIncrement(10);
		barraHorizontal.addAdjustmentListener(this);
		
		areaGrafo = new AreaAplicativo(this);
		
		//Inserção dos objetos no EditorGrafo...
		//Configuração dos parametros da área de plotagem...
		componenteLayout.fill = GridBagConstraints.BOTH;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 2.0;
		componenteLayout.weighty = 2.0;
		layoutJanela.setConstraints(areaGrafo, componenteLayout);
		areaLayout.add(areaGrafo);
		//Configuração dos parametros para a BarraVertical...
		componenteLayout.fill = GridBagConstraints.VERTICAL;
		componenteLayout.gridx = 1;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraVertical, componenteLayout);
		areaLayout.add(barraVertical);
		//Configuração dos parametros para a BarraHorizontal...
		componenteLayout.fill = GridBagConstraints.HORIZONTAL;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 1;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraHorizontal, componenteLayout);
		areaLayout.add(barraHorizontal);
		
		posicaoAtualBarraVertical = 0;
		posicaoAtualBarraHorizontal = 0;
		
		setLocation(LOCALEDITORPADRAOHOR , LOCALEDITORPADRAOVER);
		setSize(TAMANHOEDITORPADRAOHOR , TAMANHOEDITORPADRAOVER);
		show();
		
		teclaShiftPressionada = false;
		teclaCtrlPressionada = false;
		teclaDeletePressionada = false;
		teclaCtrlZPressionada = false;
		
		addComponentListener(this);
		addKeyListener(this);
		setFocusable(true);
	}
	
	/**
	 * Cria um editor de aplicativo padrão com o título <B>Editor de Grafo</B> sem
	 * nenhum grafo apresentado e com tamanho da janela e sua localização definido
	 * pelos parâmetros. Este editor poderá ser editado pelo usuário final.
	 *
	 * @param localJanelaH a coordenada da janela no eixo horizontal
	 * @param localJanelaV a coordenada da janela no eixo vertical
	 * @param tamanhoJanelaH a largura da janela
	 * @param tamanhoJanemaV a altura da janela
	 */	
	public EditorAplicativoGrafo(int localJanelaH, int localJanelaV, int tamanhoJanelaH, int tamanhoJanelaV)
	{
		this();
		
		setSize(tamanhoJanelaH, tamanhoJanelaV);
		setLocation(localJanelaH, localJanelaV);
	}
	
	/**
	 * Cria um editor de aplicativo padrão com o título <B>Editor de Grafo</B> e com
	 * o grafo inicial passado pelo parâmetro. Este editor poderá ser editado 
	 * pelo usuário final.
	 *
	 * @param grafo o grafo a ser apresentado
	 */
	public EditorAplicativoGrafo(Grafo grafo)
	{
		this();
		setGrafo(grafo);
		
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
//Métodos Get...

	/**
	 * Retorna o grafo atual do editor.
	 *
	 * @return o grafo apresentado
	 */
	public Grafo getGrafo()
	{
		return grafo;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * uma aresta.
	 *
	 * @return o menu que contém as opções para alteração das informações da aresta
	 */
	public JMenuMouse getMenuMouseAresta()
	{
		return areaGrafo.getJMenuMouseAresta();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um rótulo.
	 *
	 * @return o menu que contém as opções para alteração das informações do rótulo
	 */
	public JMenuMouse getMenuMouseRotulo()
	{
		return areaGrafo.getJMenuMouseRotulo();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um vértice.
	 *
	 * @return o menu que contém as opções para alteração das informações do vértice
	 */
	public JMenuMouse getMenuMouseVertice()
	{
		return areaGrafo.getJMenuMouseVertice();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre nenhum dos 
	 * componentes.Este menu possui opções genericas do grafo.
	 *
	 * @return o menu que contém as opções para alteração das informações gerais
	 */
	public JMenuMouse getMenuMouse()
	{
		return areaGrafo.getJMenuMouse();
	}
	
	/**
	 * Retorna o valor que define se o grafo pode ser editado pelo usuário final.
	 * 
	 *
	 * @return o valor que definindo se o grafo pode ser editado ou somente apresentado
	 * @see EditorAplicativoGrafo#EDITAR
	 * @see EditorAplicativoGrafo#NAO_EDITAR
	 */
	public int getEditar()
	{
		return editar;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Ctrl está pressionada pelo usuário.
	 * Caso seja verdadeiro, a tecla está pressionada e falso caso contrário.
	 *
	 * @return o valor que indicando se a tecla está pressionada ou não
	 */
	public boolean getTeclaCtrlPressionada()
	{
		return teclaCtrlPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Ctrl + Z está pressionada pelo usuário.
	 * Caso seja verdadeiro, a tecla está pressionada e falso caso contrário.
	 *
	 * @return o valor que indicando se as teclas estão pressionadas ou não
	 */
	public boolean getTeclaCtrlZPressionada()
	{
		return teclaCtrlZPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Shift está pressionada pelo usuário.
	 * Caso seja verdadeiro, a tecla está pressionada e falso caso contrário.
	 *
	 * @return o valor que indicando se a tecla está pressionada ou não
	 */
	public boolean getTeclaShiftPressionada()
	{
		return teclaShiftPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla delete está pressionada pelo usuário.
	 * Caso seja verdadeiro, a tecla está pressionada e falso caso contrário.
	 *
	 * @return o valor que indicando se a tecla está pressionada ou não
	 */
	public boolean getTeclaDeletePressionada()
	{
		return teclaDeletePressionada;
	}
	
//Métodos Set..

	/**
	 * Altera o grafo apresnetado no editor.
	 *
	 * @param grafo o novo grafo a ser apresentado
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
		areaGrafo.setGrafo(grafo);

		setarBarraHorizontal();
		setarBarraVertical();

		areaGrafo.repaint();
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre uma aresta.
	 *
	 * @param menuMouse o menu de aresta do editor
	 */
	public void setMenuMouseAresta(JMenuMouse menuMouse)
	{
		areaGrafo.setJMenuMouseAresta(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um rótulo.
	 *
	 * @param menuMouse o menu do rótulo do editor
	 */
	public void setMenuMouseRotulo(JMenuMouse menuMouse)
	{
		areaGrafo.setJMenuMouseRotulo(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um vértice.
	 *
	 * @param menuMouse o menu de vértice do editor
	 */
	public void setMenuMouseVertice(JMenuMouse menuMouse)
	{
		areaGrafo.setJMenuMouseVertice(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre nenhum dos 
	 * componentes contidos na área de apresentação.
	 *
	 * @param menuMouse o menu do editor
	 */
	public void setMenuMouse(JMenuMouse menuMouse)
	{
		areaGrafo.setJMenuMouse(menuMouse);
	}
	
	/**
	 * Altera a informação do editor para permitir ou não edição do grafo.
	 *
	 * @param editar o valor indicado se o grafo pode ser ou não editado
	 * @see EditorAplicativoGrafo#EDITAR
	 * @see EditorAplicativoGrafo#NAO_EDITAR
	 */
	public void setEditar(int editar)
	{
		if ((editar == NAO_EDITAR) || (editar == EDITAR))
			this.editar = editar;
		else
			this.editar = EDITAR;
	}
	
	/**
	 * Chama o método <B>repaint</B> da classe AreaAplicativo para atualizar a apresentação
	 * do grafo na tela.
	 */
	public void repintarArea()
	{
		areaGrafo.repaint();
	}
	
	/**
	 * Atualiza os valores das barras de rolagem horizontal e vertical. Este método 
	 * é chamado automaticamente pela classe AreaAplicativo quando o usuário final 
	 * arrasta um componente dentro do editor.
	 */
	public void setBarrasRolagem()
	{		
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
// Métodos de tratamento de eventos

	/**
	 * Método chamado pela classe AreaAplicativo sempre que o usuário final pressionar
	 * a tecla CTRL e movimentar o mouse sobre um componente contido dentrio do
	 * editor. Ele é muito util quando deseja-se chamar uma nova URL na Web 
	 * ou executar um determinada tarefa.
	 *
	 * Para o pacote GEDE, nenhuma implementação deste método é feita e portanto 
	 * as novas aplicações deverão sobrescrever este método para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void movimentoMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * Método chamado pela classe AreaAplicativo sempre que usuário final executar
	 * um clique duplo sobre algum componente do editor.
	 *
	 * Para o pacote GEDE, nenhuma implementação deste método é feita e portanto 
	 * as novas aplicações deverão sobrescrever este método para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void cliqueDuploMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * Este método é chamado sempre que o usuário final movimetar as barras de 
	 * rolagem do editor. Ele atualiza a AreaAplicativo onde o grafo está sendo 
	 * apresentado.
	 *
	 * @param e o evento de barra gerado pelo usuário
	 */
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if (e.getSource() == barraHorizontal)
		{
			//Move o painel na Horizontal
			areaGrafo.setBounds(barraHorizontal.getValue() * -1,
								areaGrafo.getY(),
								getWidth()-25 + barraHorizontal.getValue(),
								getHeight()-44 + barraVertical.getValue());
			
			posicaoAtualBarraHorizontal = barraHorizontal.getValue();
		}
		
		if (e.getSource() == barraVertical)
		{
			//Move o painel na Vertical...
			areaGrafo.setBounds(areaGrafo.getX(),
								barraVertical.getValue() * -1,
								getWidth()-25 + barraHorizontal.getValue(),
								getHeight()-44 + barraVertical.getValue());

			posicaoAtualBarraVertical = barraVertical.getValue();
		}
	}
	
	/**
	 * Este método é chamado sempre que o usuário final redimensionar o tamanho
	 * da janela do editor. Ele atualiza a AreaAplicativo onde o grafo está sendo 
	 * apresentado.
	 *
	 * @param e o evento de janela gerado pelo usuário
	 */
	public void componentResized(ComponentEvent e)
	{
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
	/**
	 * Este método é chamado sempre que o usuário mover a janela do editor.
	 *
	 * @param e o evento de janela gerado pelo usuário
	 */
	public void componentMoved(ComponentEvent e){}
	
	/**
	 * Este método é chamado sempre que a janela do editor for mostrada.
	 *
	 * @param e o evento de janela gerado pelo usuário
	 */
	public void componentShown(ComponentEvent e){}
	
	/**
	 * Este método é chamado sempre que a janela do editor ficar escondida.
	 *
	 * @param e o evento de janela gerado pelo usuário
	 */
	public void componentHidden(ComponentEvent e){}
	
//Metodos de evento do teclado
	
	/**
	 * Método invocado sempre que uma tecla for digitada pelo usuário final.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyTyped(KeyEvent e){}
	
	/**
	 * Método invocado sempre que uma tecla for pressionada pelo usuário final.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if (teclaCtrlPressionada)
			{
				//Ctrl + Z
				teclaCtrlZPressionada = true;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			//Captura o tecla delete...
			teclaDeletePressionada = true;
			areaGrafo.keyPressed(e);
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Captura a tecla Shift
			teclaShiftPressionada = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Captura a tecla Control
			teclaCtrlPressionada = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//Tecla seta para a Direita
			if (barraHorizontal.isEnabled())
				barraHorizontal.setValue(barraHorizontal.getValue() + 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//Tecla seta para a Direita
			if (barraHorizontal.isEnabled())
				barraHorizontal.setValue(barraHorizontal.getValue() - 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			//Tecla seta para Cima
			if (barraVertical.isEnabled())
				barraVertical.setValue(barraVertical.getValue() - 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			//Tecla seta para baixo
			if (barraVertical.isEnabled())
				barraVertical.setValue(barraVertical.getValue() + 1);
		}
	}	
	
	/**
	 * Método invocado sempre que uma tecla for liberada pelo usuário final.
	 *
	 * @param e o evento do teclado
	 */
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclaCtrlZPressionada = false;		
		}
		if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			//Captura o tecla delete...
			teclaDeletePressionada = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Libera a tecla Shift
			teclaShiftPressionada = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Libera a tecla CTRL
			teclaCtrlPressionada = false;
			teclaCtrlZPressionada = false;
		}
	}

//	Métodos privados
	private void setarBarraHorizontal()
	{
		int maiorPosicaoX;
		
		maiorPosicaoX = verificarMaiorPosicaoX() + DISTANCIA_EIXO_X;
		
		if (maiorPosicaoX > getWidth())
		{
			barraHorizontal.setEnabled(true);
			barraHorizontal.setMinimum(0);
			barraHorizontal.setMaximum(maiorPosicaoX - getWidth());
			barraHorizontal.setValue(posicaoAtualBarraHorizontal);
			barraHorizontal.setUnitIncrement(20);
		}
		else
		{
			areaGrafo.setBounds(0, 0, getWidth()-25, getHeight()-44);
			barraHorizontal.setEnabled(false);
			posicaoAtualBarraHorizontal = 0;
		}
	}
	
	private void setarBarraVertical()
	{
		int maiorPosicaoY;
		
		maiorPosicaoY = verificarMaiorPosicaoY() + DISTANCIA_EIXO_Y;
		
		if (maiorPosicaoY > getHeight())
		{
			barraVertical.setEnabled(true);
			barraVertical.setMinimum(0);
			barraVertical.setMaximum(maiorPosicaoY - getHeight());
			barraVertical.setValue(posicaoAtualBarraVertical);
			barraVertical.setUnitIncrement(20);
		}
		else
		{
			areaGrafo.setBounds(0, 0, getWidth()-25, getHeight()-44);
			barraVertical.setEnabled(false);
			posicaoAtualBarraVertical = 0;
		}
	}
	
	private int verificarMaiorPosicaoX()
	{
		int 	i, j, pontoFinalX, eixoX, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoX = 0;
		
		if (grafo == null)
			return eixoX;
			
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			pontoFinalX = verticeTemp.getX() + verticeTemp.getLargura();
			
			if (pontoFinalX > eixoX)
				eixoX = pontoFinalX;
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasX();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						pontoFinalX = quebras[j];
						if (pontoFinalX > eixoX)
							eixoX = pontoFinalX;
					}
				}
			}
		}
		
		return eixoX;
	}
	
	private int verificarMaiorPosicaoY()
	{
		int 	i, j, pontoFinalY, eixoY, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoY = 0;
		
		if (grafo == null)
			return eixoY;
			
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			pontoFinalY = verticeTemp.getY() + verticeTemp.getAltura();
			
			if (pontoFinalY > eixoY)
				eixoY = pontoFinalY;
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasY();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						pontoFinalY = quebras[j];
						if (pontoFinalY > eixoY)
							eixoY = pontoFinalY;
					}
				}
			}
		}
		
		return eixoY;
	}
}