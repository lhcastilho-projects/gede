package diagram.editor;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import diagram.Grafo;

import diagram.menu.JMenuMouse;
import diagram.menu.JMenuMouseArea;
import diagram.menu.JMenuMouseAresta;
import diagram.menu.JMenuMouseVertice;
import diagram.menu.JMenuMouseRotulo;

import diagram.componente.Aresta;
import diagram.componente.Rotulo;
import diagram.componente.Vertice;

/**
 * Está classe é responsável pelo desenho do grafo ou diagrama dentro do editor.
 * Nela existem todos os métodos implementados para suporte aos eventos de
 * mouse e de teclado do GEDE.
 *
 * Está classe é instânciada sempre que houver a necessidade de cria uma área 
 * para apresentação de grafos no Web browser utilizando as classes do pacote 
 * swing e quando for necessário a criação de um aplicativo Java.
 *
 *
 * @author Luis Henrique Castilho da Silva
 * @see EditorAplicativoGrafo
 * @see EditorAppletGrafo
 */
public class AreaAplicativo extends JPanel implements MouseMotionListener, MouseListener, KeyListener
{
	/**
	 * A cor de fundo padrão utilizada pelo editor GEDE.
	 */
	public static final Color		CORFUNDOPADRAO = Color.white;
	
	private final int				LADO_QUADRADO_SELECAO = 5;
	
	private Cursor					cursorMao, cursorPadrao;
	
	private Grafo					grafo;
	
	private int						distBordaX,
									distBordaY,
									cliqueAresta,
									cliquePontoAresta,
									cliqueRotuloAresta,
									cliqueRotuloVertice,
									cliqueVertice,
									cliqueSelecaoVertice,
									coordX,
									coordY,
									localQuadradoSelecao,
									larguraVertice,
									alturaVertice,
									mouseMoveX,
									mouseMoveY,
									maximoEsquerdaX1,
									maximoDireitaX1,
									maximoSuperiorY,
									maximoInferiorY;
									
	private	boolean					houverArraste,
									teclaShiftPressionada,
									teclaCtrlPressionada,
									teclaCtrlZPressionada;
	
	private EditorAplicativoGrafo	editorGrafo;
	private EditorAppletGrafo		editorAppletGrafo;
	
	private static JMenuMouse		menuMouseAresta,
									menuMouseRotulo,
									menuMouseVertice,
									menuMouse;
	
	/**
	 * Cria uma nova área de apresentação do grafo ou diagrama utilizado pelo
	 * <B>Aplicativo</B> instânciado. Para criar está nova área, deve-se passar 
	 * uma janela aplicativo de edição do pacote GEDE.
	 *
	 * @param editorGrafo o aplicativo que conterá a área de apresentação
	 * @see EditorAplicativoGrafo#EditorAplicativoGrafo
	 */
	public AreaAplicativo(EditorAplicativoGrafo editorGrafo)
	{
		super();
		
		this.editorGrafo = editorGrafo;
		
		distBordaX = 0;
		distBordaY = 0;
		
		coordX = 0;
		coordY = 0;
		localQuadradoSelecao = 0;
		larguraVertice = 0;
		alturaVertice = 0;
		mouseMoveX = 0;
		mouseMoveY = 0;
		maximoEsquerdaX1 = 0;
		maximoDireitaX1 = 0;
		maximoSuperiorY = 0;
		maximoInferiorY = 0;
		
		cliqueAresta = -1;
		cliquePontoAresta = -1;
		cliqueRotuloAresta = -1;
		cliqueRotuloVertice = -1;
		cliqueVertice = -1;
		cliqueSelecaoVertice = -1;
		
		houverArraste = false;
		
		setBackground(CORFUNDOPADRAO);
		
		addMouseMotionListener(this);
		addMouseListener(this);
					
		JMenuMouseAresta menuAresta = new JMenuMouseAresta(this);
		menuMouseAresta = menuAresta;
		
		JMenuMouseRotulo menuRotulo = new JMenuMouseRotulo(this);
		menuMouseRotulo = menuRotulo;
		
		JMenuMouseVertice menuVertice = new JMenuMouseVertice(this);
		menuMouseVertice = menuVertice;
		
		JMenuMouseArea menuArea = new JMenuMouseArea(this);
		menuMouse = menuArea;
				
		cursorMao = new Cursor(Cursor.HAND_CURSOR);
		cursorPadrao = new Cursor(Cursor.DEFAULT_CURSOR);
	}
	
	/**
	 * Cria uma nova área de apresentação do grafo ou diagrama utilizado pela
	 * <B>Applet</B> instânciada. Para criar está nova área, deve-se passar 
	 * uma applet do pacote GEDE.
	 *
	 * @param editorAppletGrafo a applet que conterá a área de apresentação
	 * @see EditorAppletGrafo#EditorAppletGrafo
	 */
	public AreaAplicativo(EditorAppletGrafo editorAppletGrafo)
	{
		super();
		
		this.editorAppletGrafo = editorAppletGrafo;
		grafo = null;
		
		distBordaX = 0;
		distBordaY = 0;
		
		coordX = 0;
		coordY = 0;
		localQuadradoSelecao = 0;
		larguraVertice = 0;
		alturaVertice = 0;
		mouseMoveX = 0;
		mouseMoveY = 0;
		maximoEsquerdaX1 = 0;
		maximoDireitaX1 = 0;
		maximoSuperiorY = 0;
		maximoInferiorY = 0;
		
		cliqueAresta = -1;
		cliquePontoAresta = -1;
		cliqueRotuloAresta = -1;
		cliqueRotuloVertice = -1;
		cliqueVertice = -1;
		cliqueSelecaoVertice = -1;
		
		houverArraste = false;
		
		setBackground(CORFUNDOPADRAO);
		
		editorAppletGrafo.getJEditorApplet().addKeyListener(this);
		editorAppletGrafo.getJEditorApplet().addMouseMotionListener(this);
		editorAppletGrafo.getJEditorApplet().addMouseListener(this);
		
		JMenuMouseAresta menuAresta = new JMenuMouseAresta(this);
		menuMouseAresta = menuAresta;
		
		JMenuMouseRotulo menuRotulo = new JMenuMouseRotulo(this);
		menuMouseRotulo = menuRotulo;
		
		JMenuMouseVertice menuVertice = new JMenuMouseVertice(this);
		menuMouseVertice = menuVertice;
		
		JMenuMouseArea menuArea = new JMenuMouseArea(this);
		menuMouse = menuArea;
				
		cursorMao = new Cursor(Cursor.HAND_CURSOR);
		cursorPadrao = new Cursor(Cursor.DEFAULT_CURSOR);
		
		teclaShiftPressionada = false;
		teclaCtrlPressionada = false;
		teclaCtrlZPressionada = false;
	}
	
	/**
	 * Este método é responsável pelo desenho de todo o grafo existente no editor.
	 * 
	 * @param desenhar o contexto gráfico que desenhará o grafo na área
	 * @see EditorAplicativoGrafo#repintarArea
	 */
	public void paintComponent(Graphics desenhar)
	{
		int			i;
		Aresta 		arestas[];
		Vertice		vertices[];
		Rotulo		rotulo;
		
		//Chama o construtor para limpar a tela...
		super.paintComponent(desenhar);
		
		if (grafo != null)
		{
			//Desenha as arestas
			arestas = grafo.getTodasArestas();
			if (arestas != null)
			{
				for (i = 0; i < arestas.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					arestas[i].desenharAresta(desenhar);
				}	
			}
			
			//Desenha os vertices
			vertices = grafo.getTodosVertices();
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					vertices[i].desenharVertice(desenhar, this);
				}
			}
			
			//Desenha os rotulos e sua selecao. Rotulos relacionados com as arestas
			if (arestas != null)
			{
				for (i = 0; i < arestas.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					rotulo = arestas[i].getRotulo();
					rotulo.desenharRotuloAresta(desenhar, arestas[i], LADO_QUADRADO_SELECAO);
				}	
			}
			
			//Desenha os rotulos e sua seleção. Rotulos relacionados com os vertices
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					rotulo = vertices[i].getRotulo();
					rotulo.desenharRotuloVertice(desenhar, vertices[i], LADO_QUADRADO_SELECAO);
				}
			}
			
			//Desenho a seleção das arestas
			if (arestas != null)
			{
				for (i = 0;i < arestas.length; i++)
					arestas[i].selecionarAresta(desenhar, LADO_QUADRADO_SELECAO);
			}
			
			//Desenho a seleção dos vértices
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
					vertices[i].selecionarVertice(desenhar, LADO_QUADRADO_SELECAO);
			}
		}
	}
	
	//Metodos get...
	
	/**
	 * Retorna o editor aplicativo atual da área de desenho do grafo.
	 *
	 * @return o editor aplicativo do GEDE
	 */
	public EditorAplicativoGrafo getEditorAplicativoGrafo()
	{
		return editorGrafo;
	}
	
	/**
	 * Retorna a applet atual da área de desenho do grafo. Está applet
	 * retornada pertence ao pacote Swing. 
	 *
	 * @return o editor aplicativo do GEDE
	 */
	public EditorAppletGrafo getEditorAppletGrafo()
	{
		return editorAppletGrafo;
	}
	
	/**
	 * Retorna o grafo atual desenhado na área do editor aplicativo ou applet.
	 * 
	 * @return o grafo atual desenhado
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
	 * @see JMenuMouseAresta#JMenuMouseAresta
	 */
	public JMenuMouse getJMenuMouseAresta()
	{
		return menuMouseAresta;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um rótulo.
	 *
	 * @return o menu que contém as opções para alteração das informações do rótulo
	 * @see diagram.menu.JMenuMouseRotulo#JMenuMouseRotulo
	 */
	public JMenuMouse getJMenuMouseRotulo()
	{
		return menuMouseRotulo;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um vértice.
	 *
	 * @return o menu que contém as opções para alteração das informações do vértice
	 * @see JMenuMouseVertice#JMenuMouseVertice
	 */
	public JMenuMouse getJMenuMouseVertice()
	{
		return menuMouseVertice;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre nenhum dos 
	 * componentes.
	 *
	 * @return o menu que contém as opções para alteração das informações gerais
	 * @see JMenuMouseArea#JMenuMouseArea
	 */
	public JMenuMouse getJMenuMouse()
	{
		return menuMouse;
	}
	
	//Métodos Set...
	
	/**
	 * Altera o grafo a ser desenhado pela classe. Por padrão não existe nenhum grafo
	 * no início.
	 *
	 * @param grafo o novo grafo a ser desenhado pela área
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre uma aresta.
	 *
	 * @param menuMouseAresta o menu de aresta do editor
	 * @see JMenuMouseAresta#JMenuMouseAresta
	 */
	public void setJMenuMouseAresta(JMenuMouse menuMouseAresta)
	{
		this.menuMouseAresta = menuMouseAresta;
	}

	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um rótulo.
	 *
	 * @param menuMouseRotulo o menu do rótulo do editor
	 * @see JMenuMouseRotulo#JMenuMouseRotulo
	 */
	public void setJMenuMouseRotulo(JMenuMouse menuMouseRotulo)
	{
		this.menuMouseRotulo = menuMouseRotulo;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um vértice.
	 *
	 * @param menuMouseVertice o menu de vértice do editor
	 * @see JMenuMouseVertice#JMenuMouseVertice
	 */
	public void setJMenuMouseVertice(JMenuMouse menuMouseVertice)
	{
		this.menuMouseVertice = menuMouseVertice;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre nenhum dos 
	 * componentes contidos na área de apresentação.
	 *
	 * @param menuMouse o menu do editor
	 * @see JMenuMouseArea#JMenuMouseArea
	 */
	public void setJMenuMouse(JMenuMouse menuMouse)
	{
		this.menuMouse = menuMouse;
	}
	
	//Mouse do KeyListener
	
	/**
	 * Método invocado sempre que uma tecla for pressionada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyPressed(KeyEvent e)
	{
		int		i, resposta;
		Vertice	vertice;
		Aresta	arestas[];
		boolean	selecionado;
		
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
			selecionado = false;
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				if (grafo.getVertice(i).getSelecionado())
				{
					selecionado = true;
				}
				
				if (grafo.getVertice(i).getRotulo().getRotuloSelecionado())
				{
					selecionado = true;
				}
			}
			
			arestas = grafo.getTodasArestas();
			for (i = 0; i < grafo.getNumeroTotalArestas(); i++)
			{
				if (arestas[i].getSelecionado())
				{
					selecionado = true;
				}
				
				if (arestas[i].getRotulo().getRotuloSelecionado())
				{
					selecionado = true;
				}
			}
			
			if (!selecionado) //Para caso não exista componente selecionado
				return;
			
			resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente remover os componentes?", "Caixa de Pergunta", JOptionPane.YES_NO_OPTION);
			
			if (resposta == 0)
			{
				//Remover componente
				for (i = grafo.getNumeroTotalVertices() - 1; i >= 0; i--)
				{
					vertice = grafo.getVertice(i);
					if (vertice.getSelecionado())
					{
						//Deletar componente
						grafo.removerVertice(vertice);
					}
					else
					{
						//Manter componente
						if (vertice.getRotulo().getRotuloSelecionado())	
							vertice.getRotulo().setTexto("");
					}
				}

				arestas = grafo.getTodasArestas();
				for (i = grafo.getNumeroTotalArestas() - 1; i >= 0; i--)
				{
					if (arestas[i].getSelecionado())
					{
						//Deletar aresta
						grafo.removerAresta(arestas[i]);
					}
					else
					{
						//Manter aresta
						if (arestas[i].getRotulo().getRotuloSelecionado())
							arestas[i].getRotulo().setTexto("");
					}
				}
				
				repaint();
				
				if (editorGrafo != null)
					editorGrafo.setBarrasRolagem();
				else
					editorAppletGrafo.setBarrasRolagem();
			}
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
	}
	
	/**
	 * Método invocado sempre que uma tecla for liberada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclaCtrlZPressionada = false;		
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
	
	/**
	 * Método invocado sempre que uma tecla for digitada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyTyped(KeyEvent e){}
	
	//Metodos do MouseMotionListener
	
	/**
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de arraste das classes JMenuMouseArea, JMenuMouseAresta, 
	 * JMenuMouseRotulo e JMenuMouseVertice sempre serão invocados antes de executar
	 * suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseDragged
	 * @see JMenuMouseArea#mouseDragged
	 * @see JMenuMouseRotulo#mouseDragged
	 * @see JMenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e)
	{
		Aresta		arestas[];
		Rotulo		rotulo;
		Vertice 	vertice;
		int 		auxX, auxY, moveEixoX, moveEixoY;
		
		if (grafo == null)
			return;
			
		//Identifica se pode ser editado...
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
				return;
		}
		else if (editorAppletGrafo != null)
		{
			//Applet
			if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
				return;
		}
		
		//Chama o mouseDragged para componente clicado
		menuMouseAresta.mouseDragged(e);
		menuMouseRotulo.mouseDragged(e);
		menuMouseVertice.mouseDragged(e);
		menuMouse.mouseDragged(e);
		
		if (cliqueRotuloVertice != -1)
		{
			//Clique ocorreu no rotulo do vertice
			rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
			
			auxY = e.getY() - rotulo.getCoordenadaY();
			if (auxY >= 0)
				rotulo.setDistanciaEixoCentral(rotulo.getDistanciaEixoCentral() + 1);
			else
				rotulo.setDistanciaEixoCentral(rotulo.getDistanciaEixoCentral() - 1);
		}	
		else if (cliqueVertice != -1)
		{
			//Clique ocorreu no vértice
			vertice = grafo.getVertice(cliqueVertice);	
			auxX = e.getX() - distBordaX;
			auxY = e.getY() - distBordaY;
			
			vertice.setX(auxX);
			vertice.setY(auxY);
		}
		else if(cliqueSelecaoVertice != -1)
		{
			//Clique no quadrado e seleção do vértice
			/*Variavel
			 * localQuadradoSelecao = 1 --> Canto superior esquerdo
			 * localQuadradoSelecao = 2 --> Canto superior direito
			 * localQuadradoSelecao = 3 --> Canto inferior esquerdo
			 * localQuadradoSelecao = 4 --> Canto inferior direito
			*/						
			vertice = grafo.getVertice(cliqueSelecaoVertice);
			
			auxX = e.getX() - coordX;
			auxY = e.getY() - coordY;
			
			moveEixoX = e.getX() - mouseMoveX;
			moveEixoY = e.getY() - mouseMoveY;
			
			if (localQuadradoSelecao == 4)
			{
				//Quadrado de seleção inferior direito do vértice
				vertice.setLargura(larguraVertice + auxX);
				vertice.setAltura(alturaVertice + auxY);
			}
			else if (localQuadradoSelecao == 3)
			{
				//Quadrado de seleção inferior esquerdo
				if (moveEixoX > 0)
				{
					//Movimento para a direita
					if (vertice.getLargura() != Vertice.LARGURAMINIMA)
					{
						if (e.getX() >= maximoEsquerdaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoDireitaX1 = e.getX();
						}
					}
				}
				else
				{
					//Movimento para a esquerda
					if (vertice.getLargura() != Vertice.LARGURAMAXIMA)
					{
						if (e.getX() <= maximoDireitaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoEsquerdaX1 = e.getX();	
						}
					}
				}
				
				vertice.setAltura(alturaVertice + auxY);
			}
			else if (localQuadradoSelecao == 1)
			{
				//Quadrado de seleção superior esquerdo
				if (moveEixoX > 0)
				{
					//Movimento para a direita
					if (vertice.getLargura() != Vertice.LARGURAMINIMA)
					{
						if (e.getX() >= maximoEsquerdaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoDireitaX1 = e.getX();
						}
					}
				}
				else
				{
					//Movimento para a esquerda
					if (vertice.getLargura() != Vertice.LARGURAMAXIMA)
					{
						if (e.getX() <= maximoDireitaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoEsquerdaX1 = e.getX();	
						}
					}
				}
				
				if (moveEixoY > 0)
				{
					//Movimento para baixo
					if (vertice.getAltura() != Vertice.ALTURAMINIMA)
					{
						if (e.getY() >= maximoSuperiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoInferiorY = e.getY();
						}
					}
				}
				else
				{
					//Movimento para cima
					if (vertice.getAltura() != Vertice.ALTURAMAXIMA)
					{
						if (e.getY() <= maximoInferiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoSuperiorY = e.getY();
						}
					}
				}
			}
			else if (localQuadradoSelecao == 2)
			{
				//Quadrado de seleção superior direito
				vertice.setLargura(larguraVertice + auxX);
				
				if (moveEixoY > 0)
				{
					//Movimento para baixo
					if (vertice.getAltura() != Vertice.ALTURAMINIMA)
					{
						if (e.getY() >= maximoSuperiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoInferiorY = e.getY();
						}
					}
				}
				else
				{
					//Movimento para cima
					if (vertice.getAltura() != Vertice.ALTURAMAXIMA)
					{
						if (e.getY() <= maximoInferiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoSuperiorY = e.getY();
						}
					}
				}
			}
			
			mouseMoveX = e.getX();
			mouseMoveY = e.getY();
		}
		else if (cliquePontoAresta != -1)
		{
			//Clique ocorreu na seleção da aresta
			arestas = grafo.getTodasArestas();
			arestas[cliqueAresta].setQuebraX(cliquePontoAresta, e.getX());
			arestas[cliqueAresta].setQuebraY(cliquePontoAresta, e.getY());
		}
		
		houverArraste = true;
		repaint();
	}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de movimentação das classes JMenuMouseArea, JMenuMouseAresta,
	 * JMenuMouseRotulo e JMenuMouseVertice sempre serão invocados antes de 
	 * executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseMoved
	 * @see JMenuMouseArea#mouseMoved
	 * @see JMenuMouseRotulo#mouseMoved
	 * @see JMenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e)
	{
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestasTemp[];
		Rotulo		rotuloTemp;
		
		if (grafo == null)
			return;
			
		//Identifica se pode ser editado...
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
				return;
		}
		else if (editorAppletGrafo != null)
		{
			//Applet
			if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
				return;
		}
		
		//Chama o mouseMoved para componente clicado
		menuMouseAresta.mouseMoved(e);
		menuMouseRotulo.mouseMoved(e);
		menuMouseVertice.mouseMoved(e);
		menuMouse.mouseMoved(e);
		
		if (editorGrafo != null)
		{
			//Aplicativo java
			if (editorGrafo.getTeclaCtrlPressionada() && !editorGrafo.getTeclaCtrlZPressionada())
			{
				//Somente a tecla Ctrl pressionada
				
				//Identifica se o clique ocooreu no rotulo do vertice
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
				{
					verticeTemp = grafo.getVertice(i);
					rotuloTemp = verticeTemp.getRotulo();
					
					if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
					{
						//Gera um evento chamando um metodo da classe EditorAplicativoGrafo
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorGrafo.movimentoMouse(e, rotuloTemp);
						return;
					}
				}
				
				//Identifica se o clique ocooreu no rotulo da aresta
				arestasTemp = grafo.getTodasArestas();
				if (arestasTemp != null)
				{
					for (i = 0; i < arestasTemp.length; i++)
					{
						rotuloTemp = arestasTemp[i].getRotulo();
						
						if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
						{
							desmarcarTodosComponentes();
							repaint();
							setCursor(cursorMao);
							editorGrafo.movimentoMouse(e, rotuloTemp);
							return;
						}
					}
				}
				
				//Identifica se o clique ocooreu no vertice
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
				{
					//Verificar se a coordenada do clique do mouse pertence a area do vertice
					verticeTemp = grafo.getVertice(i);
					if (verticeTemp.coordenadaPertenceVertice(e.getX(), e.getY()))
					{
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorGrafo.movimentoMouse(e, verticeTemp);
						return;
					}
				}
				
				//Identifica se o clique ocooreu na aresta
				if (arestasTemp != null)
				{
					for (i = 0; i < arestasTemp.length; i++)
					{
						//Verificar se a coordenada do clique do mouse pertence a area da aresta
						if (arestasTemp[i].coordenadaPertenceAresta(e.getX(), e.getY()))
						{
							desmarcarTodosComponentes();
							repaint();
							setCursor(cursorMao);
							editorGrafo.movimentoMouse(e, arestasTemp[i]);
							return;
						}
					}
				}
			}
			
			setCursor(cursorPadrao);
		}
		else
		{
			//Applet java
			if (getTeclaCtrlPressionada() && !getTeclaCtrlZPressionada())
			{
				//Somente a tecla Ctrl pressionada
				
				//Identifica se o clique ocooreu no rotulo do vertice
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
				{
					verticeTemp = grafo.getVertice(i);
					rotuloTemp = verticeTemp.getRotulo();
					
					if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
					{
						//Gera um evento chamando um metodo da classe EditorAplicativoGrafo
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorAppletGrafo.movimentoMouse(e, rotuloTemp);
						return;
					}
				}
				
				//Identifica se o clique ocooreu no rotulo da aresta
				arestasTemp = grafo.getTodasArestas();
				if (arestasTemp != null)
				{
					for (i = 0; i < arestasTemp.length; i++)
					{
						rotuloTemp = arestasTemp[i].getRotulo();
						
						if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
						{
							desmarcarTodosComponentes();
							repaint();
							setCursor(cursorMao);
							editorAppletGrafo.movimentoMouse(e, rotuloTemp);
							return;
						}
					}
				}
				
				//Identifica se o clique ocooreu no vertice
				for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
				{
					//Verificar se a coordenada do clique do mouse pertence a area do vertice
					verticeTemp = grafo.getVertice(i);
					if (verticeTemp.coordenadaPertenceVertice(e.getX(), e.getY()))
					{
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorAppletGrafo.movimentoMouse(e, verticeTemp);
						return;
					}
				}
				
				//Identifica se o clique ocooreu na aresta
				if (arestasTemp != null)
				{
					for (i = 0; i < arestasTemp.length; i++)
					{
						//Verificar se a coordenada do clique do mouse pertence a area da aresta
						if (arestasTemp[i].coordenadaPertenceAresta(e.getX(), e.getY()))
						{
							desmarcarTodosComponentes();
							repaint();
							setCursor(cursorMao);
							editorAppletGrafo.movimentoMouse(e, arestasTemp[i]);
							return;
						}
					}
				}
			}
			
			setCursor(cursorPadrao);
		}
	}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de clique com o botão direito do mouse das classes JMenuMouseAresta,
	 * JMenuMouseArea, JMenuMouseRotulo e JMenuMouseVertice sempre serão 
	 * invocados antes de executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseClicked
	 * @see JMenuMouseArea#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 * @see JMenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e)
	{
		int 		i;
		Aresta		arestas[];
		Rotulo		rotulo;
		Vertice		vertice;
		Object		objetoClicado;
		
		
		if (grafo == null)
			return;
			
		objetoClicado = null; //Nenhum objeto clicado no momento
		
		if (!e.isMetaDown())
		{
			//Identifica se pode ser editado...
			if (editorGrafo != null)
			{
				//Aplicativo
				if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
					return;
			}
			else if (editorAppletGrafo != null)
			{
				//Applet
				if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
					return;
			}
			
			//Clique com o botão esquerdo do Mouse
			if (e.getClickCount() == 2)
			{
				//Clique duplo com o botão esquerdo
				desmarcarTodosComponentes();
				
				if (editorGrafo != null)
				{
					//Aplicativo
					if (cliqueRotuloAresta != -1)
					{
						arestas = grafo.getTodasArestas();
						if (arestas != null)
							objetoClicado = arestas[cliqueRotuloAresta].getRotulo();
					}
					else if (cliqueRotuloVertice != -1)
						objetoClicado = grafo.getVertice(cliqueRotuloVertice).getRotulo();
					else if (cliqueVertice != -1)
						objetoClicado = grafo.getVertice(cliqueVertice);
					else if (cliqueAresta != -1)
					{
						arestas = grafo.getTodasArestas();
						if (arestas != null)
							objetoClicado = arestas[cliqueAresta];
					}
					
					editorGrafo.cliqueDuploMouse(e, objetoClicado);
				}
				else
				{
					//Applet
					if (cliqueRotuloAresta != -1)
					{
						arestas = grafo.getTodasArestas();
						if (arestas != null)
							objetoClicado = arestas[cliqueRotuloAresta].getRotulo();
					}
					else if (cliqueRotuloVertice != -1)
						objetoClicado = grafo.getVertice(cliqueRotuloVertice).getRotulo();
					else if (cliqueVertice != -1)
						objetoClicado = grafo.getVertice(cliqueVertice);
					else if (cliqueAresta != -1)
					{
						arestas = grafo.getTodasArestas();
						if (arestas != null)
							objetoClicado = arestas[cliqueAresta];
					}
					
					editorAppletGrafo.cliqueDuploMouse(e, objetoClicado);
				}
			}
			else
			{
				//Clique simples com o botão esquerdo
				if (editorGrafo != null)
				{
					//Aplicativo java
					if (!editorGrafo.getTeclaShiftPressionada())
						desmarcarTodosComponentes();	
				}
				else
				{
					//Applet java
					if (!getTeclaShiftPressionada())
						desmarcarTodosComponentes();
				}				
				
				if (cliqueRotuloVertice != -1)
				{
					
					//Clique sobre o rotulo do vertice
					rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
					rotulo.setRotuloSelecionado(true);
				}
				else if (cliqueRotuloAresta != -1)
				{
					//Clique sobre o rotulo da aresta
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						rotulo = arestas[cliqueRotuloAresta].getRotulo();
						rotulo.setRotuloSelecionado(true);
					}
				}
				else if (cliqueVertice != -1)
				{
					//Clique sobre o vértice
					vertice = grafo.getVertice(cliqueVertice);
					vertice.selecionarComponente();
				}
				else if (cliqueAresta != -1)
				{
					//Clique sobre a aresta
					arestas = grafo.getTodasArestas();
					if (arestas != null)
						arestas[cliqueAresta].selecionarComponente();
				}
				
				repaint();
			}
		}
		else
		{
			//Clique com o botão direito do Mouse
			//Chama os metodos do mouse com o clique do botão direito dos componentes
			menuMouseAresta.mouseClicked(e);
			menuMouseRotulo.mouseClicked(e);
			menuMouseVertice.mouseClicked(e);
			menuMouse.mouseClicked(e);
					
			if (editorGrafo != null)
			{
				if (cliqueRotuloAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						menuMouseRotulo.menu(arestas[cliqueRotuloAresta].getRotulo(), e.getX(), e.getY());
					}
				}
				else if (cliqueRotuloVertice != -1)
				{
					menuMouseRotulo.menu(grafo.getVertice(cliqueRotuloVertice).getRotulo(), e.getX(), e.getY());
				}
				else if (cliqueVertice != -1)
				{
					menuMouseVertice.menu(grafo.getVertice(cliqueVertice), e.getX(), e.getY());
				}
				else if (cliqueAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						menuMouseAresta.menu(arestas[cliqueAresta], e.getX(), e.getY());
					}
				}
				else
				{
					menuMouse.menu(null, e.getX(), e.getY());
				}
			}
			else
			{
				if (cliqueRotuloAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						menuMouseRotulo.menu(arestas[cliqueRotuloAresta].getRotulo(), e.getX(), e.getY());
					}
				}
				else if (cliqueRotuloVertice != -1)
				{
					menuMouseRotulo.menu(grafo.getVertice(cliqueRotuloVertice).getRotulo(), e.getX(), e.getY());
				}
				else if (cliqueVertice != -1)
				{
					menuMouseVertice.menu(grafo.getVertice(cliqueVertice), e.getX(), e.getY());
				}
				else if (cliqueAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						menuMouseAresta.menu(arestas[cliqueAresta], e.getX(), e.getY());
					}
				}
				else
				{
					menuMouse.menu(null, e.getX(), e.getY());
				}
			}
		}
		
		distBordaX = 0;
		distBordaY = 0;
		
		coordX = 0;
		coordY = 0;
		localQuadradoSelecao = 0;
		larguraVertice = 0;
		alturaVertice = 0;
		mouseMoveX = 0;
		mouseMoveY = 0;
		maximoEsquerdaX1 = 0;
		maximoDireitaX1 = 0;
		maximoSuperiorY = 0;
		maximoInferiorY = 0;
		
		cliqueAresta = -1;
		cliqueVertice = -1;
		cliqueSelecaoVertice = -1;
		
		cliqueRotuloAresta = -1;
		cliqueRotuloVertice = -1;
		cliquePontoAresta = -1;
	}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na a área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de entrada do mouse das classes JMenuMouseArea, JMenuMouseAresta,
	 * JMenuMouseRotulo e JMenuMouseVertice sempre serão invocados antes de 
	 * executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseEntered
	 * @see JMenuMouseArea#mouseEntered
	 * @see JMenuMouseRotulo#mouseEntered
	 * @see JMenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e)
	{
		//Chama os metodos do mouse com o clique do botão direito dos componentes
		menuMouseAresta.mouseEntered(e);
		menuMouseRotulo.mouseEntered(e);
		menuMouseVertice.mouseEntered(e);
		menuMouse.mouseEntered(e);	
	}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na a área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de saída do mouse das classes JMenuMouseArea, JMenuMouseAresta,
	 * JMenuMouseRotulo e JMenuMouseVertice sempre serão invocados antes de 
	 * executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseExited
	 * @see JMenuMouseArea#mouseExited
	 * @see JMenuMouseRotulo#mouseExited
	 * @see JMenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e)
	{
		//Chama os metodos do mouse com o clique do botão direito dos componentes
		menuMouseAresta.mouseExited(e);
		menuMouseRotulo.mouseExited(e);
		menuMouseVertice.mouseExited(e);
		menuMouse.mouseExited(e);		
	}

	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de botão do mouse pressionado das classes JMenuMouseArea, 
	 * JMenuMouseAresta, JMenuMouseRotulo e JMenuMouseVertice sempre serão 
	 * invocados antes de executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mousePressed
	 * @see JMenuMouseArea#mousePressed
	 * @see JMenuMouseRotulo#mousePressed
	 * @see JMenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e)
	{
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestasTemp[];
		Rotulo		rotuloTemp;
		
		if (grafo == null)
			return;
			
		//Identifica se pode ser editado...
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
				return;
		}
		else if (editorAppletGrafo != null)
		{
			//Applet
			if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
				return;
		}
		
		//Chama os metodos do mouse com o clique do botão direito dos componentes
		menuMouseAresta.mousePressed(e);
		menuMouseRotulo.mousePressed(e);
		menuMouseVertice.mousePressed(e);
		menuMouse.mousePressed(e);
		
		//Identifica se o clique ocooreu no rotulo do vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			rotuloTemp = verticeTemp.getRotulo();
			
			if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
			{
				cliqueRotuloVertice = i;
				return;
			}
			else
				cliqueRotuloVertice = -1;
		}
		
		//Identifica se o clique ocooreu no rotulo da aresta
		arestasTemp = grafo.getTodasArestas();
		if (arestasTemp != null)
		{
			for (i = 0; i < arestasTemp.length; i++)
			{
				rotuloTemp = arestasTemp[i].getRotulo();
				
				if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
				{
					cliqueRotuloAresta = i;
					return;
				}
				else
					cliqueRotuloAresta = -1;
			}
		}
		
		//Identifica se o clique ocooreu no vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			//Verificar se a coordenada do clique do mouse pertence a area do vertice
			verticeTemp = grafo.getVertice(i);
			if (verticeTemp.coordenadaPertenceVertice(e.getX(), e.getY()))
			{
				cliqueVertice = i;//Determina a localização do vertice na lista
				distBordaX = e.getX() - verticeTemp.getX();
				distBordaY = e.getY() - verticeTemp.getY();
				return;
			}
			else
				cliqueVertice = -1; //Nenhum vertice clicado;
			
		}//Fim do for (contador de vertices)
		
		//Identifica se o clique ocorreu no quadrado de seleção do vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			if (verticeTemp.getSelecionado())
			{
				//Vértice está selecionado
				localQuadradoSelecao = verificarCliqueQuadradoSelecao(verticeTemp, e.getX(), e.getY());
				if (localQuadradoSelecao != 0)
				{
					cliqueSelecaoVertice = i;
					larguraVertice = verticeTemp.getLargura();
					alturaVertice = verticeTemp.getAltura();
					mouseMoveX = e.getX();
					mouseMoveY = e.getY();
					coordX = e.getX();
					coordY = e.getY();
					maximoDireitaX1 = e.getX();
					maximoEsquerdaX1 = e.getX();
					maximoSuperiorY = e.getY();
					maximoInferiorY = e.getY();
					return;
				}
				else
					cliqueSelecaoVertice = -1;//Nenhum quadrado de vertice clicado;
			}
		}
		
		//Identifica se o clique ocooreu na aresta
		if (arestasTemp != null)
		{
			for (i = 0; i < arestasTemp.length; i++)
			{
				//Verifica se a coordenada pertence ao ponto intermediario da aresta
				cliquePontoAresta = arestasTemp[i].cliqueSobreQuebraAresta(e.getX(), e.getY(), LADO_QUADRADO_SELECAO);
				if (cliquePontoAresta != -1)
				{
					//Houve o clique no ponto de seleção da aresta
					cliqueAresta = i;
					break;
				}
				
				///Verificar se a coordenada do clique do mouse pertence a area da aresta
				if (arestasTemp[i].coordenadaPertenceAresta(e.getX(), e.getY()))
				{
					cliqueAresta = i;
					break;
				}
				else
					cliqueAresta = -1;
				
			}//Fim do for (contador de arestas)
		
		}//Fim do if (aresta nula)
		
	}//Fim do metodo mousePressed

	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. 
	 *
	 * Um observação importante dentro do contexto do editor de diagramas é que 
	 * os métodos de botão do mouse liberado das classes JMenuMouseArea, 
	 * JMenuMouseAresta, JMenuMouseRotulo e JMenuMouseVertice sempre serão 
	 * invocados antes de executar suas operações implementadas.
	 *
	 * @param e o evento do mouse
	 * @see JMenuMouseAresta#mouseReleased
	 * @see JMenuMouseArea#mouseReleased
	 * @see JMenuMouseRotulo#mouseReleased
	 * @see JMenuMouseVertice#mouseReleased
	 */
	public void mouseReleased(MouseEvent e)
	{
		if (grafo == null)
			return;
			
		//Identifica se pode ser editado...
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
				return;
		}
		else if (editorAppletGrafo != null)
		{
			//Applet
			if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
				return;
		}
		
		//Chama os metodos do mouse com o clique do botão direito dos componentes
		menuMouseAresta.mouseReleased(e);
		menuMouseRotulo.mouseReleased(e);
		menuMouseVertice.mouseReleased(e);
		menuMouse.mouseReleased(e);
		
		if (houverArraste)
		{
			//Houve um arraste nos componentes do editor
			desmarcarTodosComponentes();
			marcarComponenteSelecinado();
			repaint();
			
			//Setar as barras de rolagem para as novas coordenadas...
			if (editorGrafo != null)
			{
				//Componente Aplicativo
				editorGrafo.setBarrasRolagem();
			}
			else if (editorAppletGrafo != null)
			{
				//Componente Applet
				editorAppletGrafo.setBarrasRolagem();
			}
			
			distBordaX = 0;
			distBordaY = 0;
			
			coordX = 0;
			coordY = 0;
			localQuadradoSelecao = 0;
			larguraVertice = 0;
			alturaVertice = 0;
			mouseMoveX = 0;
			mouseMoveY = 0;
			maximoEsquerdaX1 = 0;
			maximoDireitaX1 = 0;
			maximoSuperiorY = 0;
			maximoInferiorY = 0;
			
			cliqueAresta = -1;
			cliqueVertice = -1;
			cliqueSelecaoVertice = -1;
				
			cliqueRotuloAresta = -1;
			cliqueRotuloVertice = -1;
			cliquePontoAresta = -1;
		}
		
		houverArraste = false;
	}
	
//Metodos privados da classe AreaAplicativo
	private boolean getTeclaCtrlZPressionada()
	{
		return teclaCtrlZPressionada;
	}
	
	private boolean getTeclaCtrlPressionada()
	{
		return teclaCtrlPressionada;
	}
	
	private boolean getTeclaShiftPressionada()
	{
		return teclaShiftPressionada;
	}
	
	private void desmarcarTodosComponentes()
	{
		int 	i;
		Vertice vertice;
		Rotulo	rotulo;
		Aresta	arestas[];
		
		//desmarcar todos os vértices e seus rotulos respectivos
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			vertice = grafo.getVertice(i);
			vertice.desmarcarComponente();
			
			rotulo = vertice.getRotulo();
			rotulo.setRotuloSelecionado(false);
		}
		
		//desmarcar todas as arestas e seus rotulos respectivos
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				arestas[i].desmarcarComponente();
				
				rotulo = arestas[i].getRotulo();
				rotulo.setRotuloSelecionado(false);
			}	
		}
	}
	
	private void marcarComponenteSelecinado()
	{
		int 	i;
		Aresta	arestas[];
		Rotulo 	rotulo;
		Vertice vertice;
		
		if (cliqueRotuloAresta != -1)
		{
			arestas = grafo.getTodasArestas();
			if (arestas != null)
			{
				rotulo = arestas[cliqueRotuloAresta].getRotulo();
				rotulo.setRotuloSelecionado(true);
			}
		}
		else if (cliqueRotuloVertice != -1)
		{
			rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
			rotulo.setRotuloSelecionado(true);
		}
		else if (cliqueVertice != -1)
		{
			grafo.getVertice(cliqueVertice).selecionarComponente();
		}
		else if (cliqueAresta != -1)
		{
			arestas = grafo.getTodasArestas();
			if (arestas != null)
				arestas[cliqueAresta].selecionarComponente();
		}
	}
	
	private int verificarCliqueQuadradoSelecao(Vertice vertice, int cliqueX, int cliqueY)
	{
		//Verifica o quadrado superior esquerdo do vertice
		if ((cliqueX >= vertice.getX() - LADO_QUADRADO_SELECAO) && (cliqueX <= vertice.getX()))
			if ((cliqueY >= vertice.getY() - LADO_QUADRADO_SELECAO) && (cliqueY <= vertice.getY()))
				return 1;
				
		//Verifica o quadrado superior direito do vertice
		if ((cliqueX >= vertice.getX() + vertice.getLargura()) && (cliqueX <= vertice.getX() + vertice.getLargura() + LADO_QUADRADO_SELECAO))
			if ((cliqueY >= vertice.getY() - LADO_QUADRADO_SELECAO) && (cliqueY <= vertice.getY()))
				return 2;
				
		//Verifica o quadrado inferior esquerdo do vertice
		if ((cliqueX >= vertice.getX() - LADO_QUADRADO_SELECAO) && (cliqueX <= vertice.getX()))
			if ((cliqueY >= vertice.getY() + vertice.getAltura()) && (cliqueY <= vertice.getY() + vertice.getAltura() + LADO_QUADRADO_SELECAO))
				return 3;
				
		//Verifica o quadrado inferior direito do vertice
		if ((cliqueX >= vertice.getX() + vertice.getLargura()) && (cliqueX <= vertice.getX() + vertice.getLargura() + LADO_QUADRADO_SELECAO))
			if ((cliqueY >= vertice.getY() + vertice.getAltura()) && (cliqueY <= vertice.getY() + vertice.getAltura() + LADO_QUADRADO_SELECAO))
				return 4;
				
		return 0;	
	}
}