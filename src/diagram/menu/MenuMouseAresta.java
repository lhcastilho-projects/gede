package diagram.menu;

import java.awt.MenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

import diagram.editor.AreaApplet;
import diagram.editor.CaixaDialogo;

/**
 * A classe MenuMouseAresta utiliza as classes do pacote awt para exibir um menu
 * sempre que o usuário final clicar com o botão direito do mouse sobre uma aresta 
 * do editor.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas opções de aresta que afetam seu contexto. Um exemplo de reutilização da 
 * classe MenuMouseAresta é mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o método setMenuMouseAresta 
 * da classe EditorAppletGrafo para que seja atualizado o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends MenuMouseAresta<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe MenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o código para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das opções do MenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see MenuMouse
 * @see MenuMouseArea
 * @see MenuMouseRotulo
 * @see MenuMouseVertice
 */
public class MenuMouseAresta extends MenuMouse
{
	private int			indiceQuebraAresta, x , y;
	
	private boolean		criarNovoSegmentoAresta,
						mudarVerticeFinal,
						mudarVerticeInicial;
	
	private Aresta		aresta;
	
	/**
	 * Cria um novo menu popup utilizado quando o usuário clicar em uma aresta
	 * contida dentro do editor.
	 *
	 * @param origem a AreaApplet onde será exibido o menu popup
	 */
	public MenuMouseAresta(AreaApplet origem) 
	{
		super(origem);
		
		int 		i;
		MenuItem	menuTemp;
		
		indiceQuebraAresta = 0;
		x = 1;
		y = 1;
		criarNovoSegmentoAresta = false;
		mudarVerticeFinal = false;
		mudarVerticeInicial = false;
		
		criarMenuArestaPadrao();
		
		for (i = 0; i < getNumeroMenus(); i++)
		{
			menuTemp = getMenu(i);
			
			if (menuTemp != null)
				add(menuTemp);
			else
				addSeparator();
		}
	}
	
	/**
	 * Método invocado pela classe AreaApplet sempre que o usuário final clicar
	 * com o botão direito do mouse sobre uma aresta contida no editor.
	 *
	 * As informações passadas pelos parâmetros são o objeto aresta que obteve o 
	 * clique do usuário final e as respectivas coordenadas no momento do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o botão 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see MenuMouseArea#menu
	 * @see MenuMouseRotulo#menu
 	 * @see MenuMouseVertice#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		aresta = (Aresta) objeto;
		this.x = x;
		this.y = y;
		
		show(getAreaApplet(), x, y);
	}
	
	/**
	 * Método invocado pela própria classe para tratar os eventos de clique 
	 * gerado pelo usuário final na escolha das opções do menu popup.
	 *
	 * @param e o evento gerado pelo clique do mouse sobre o menu
	 */
	public void actionPerformed(ActionEvent e)
	{
		int 		i;
		MenuItem	menuTemp[];
		
		menuTemp = new MenuItem[getNumeroMenus()];
		for (i = 0; i < getNumeroMenus(); i++)
			menuTemp[i] = getMenu(i);
			
		if (e.getSource() == menuTemp[0])
			criarNovoRotulo();
		else if (e.getSource() == menuTemp[1])
			criarNovoSegmento();
		else if (e.getSource() == menuTemp[3])
			definirVerticeFinal();
		else if (e.getSource() == menuTemp[4])
			definirVerticeInicial();
		else if (e.getSource() == menuTemp[6])
			removerAresta();
		
		getAreaApplet().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see MenuMouseArea#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 * @see MenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see MenuMouseArea#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 * @see MenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e)
	{
		//Usado para criar um novo segmento de aresta
		if (criarNovoSegmentoAresta)
		{
			//Seta as coordenadas do segmento da aresta
			aresta.setQuebraX(indiceQuebraAresta, e.getX());
			aresta.setQuebraY(indiceQuebraAresta, e.getY());
			
			getAreaApplet().repaint();
		}
	}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum clique com botão do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see JMenuMouseArea#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 * @see JMenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see MenuMouseArea#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 * @see MenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de saída do mouse no editor.
	 *
	 * @param e o evento de saída do mouse
	 * @see MenuMouseArea#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 * @see MenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando um pressionamento de algum botão do mouse.
	 *
	 * @param e o evento pressionamento do botão do mouse
	 * @see MenuMouseArea#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 * @see MenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e)
	{
		int 	i;
		Vertice	vertice;
		
		//Usado para criar um novo segmento de aresta
		criarNovoSegmentoAresta = false;
		
		//Usado para a mudança do vertice final
		if (mudarVerticeFinal)
		{
			for (i = 0; i < getAreaApplet().getGrafo().getNumeroTotalVertices(); i++)
			{
				vertice = getAreaApplet().getGrafo().getVertice(i);
				
				if (vertice.coordenadaPertenceVertice(e.getX(), e.getY()))
				{
					//Clique sobre um vertice..
					if ((!aresta.getVerticeOrigem().equals(vertice)) && (!aresta.getVerticeDestino().equals(vertice)))
						aresta.setVerticeDestino(vertice);
				}
			}
			
			getAreaApplet().getGrafo().removerAresta(aresta);
			getAreaApplet().getGrafo().setAresta(aresta);
			mudarVerticeFinal = false;
		}
		
		//Usado para a mudança do vertice inicial
		if (mudarVerticeInicial)
		{
			for (i = 0; i < getAreaApplet().getGrafo().getNumeroTotalVertices(); i++)
			{
				vertice = getAreaApplet().getGrafo().getVertice(i);
				
				if (vertice.coordenadaPertenceVertice(e.getX(), e.getY()))
				{
					//Clique sobre um vertice..
					if ((!aresta.getVerticeOrigem().equals(vertice)) && (!aresta.getVerticeDestino().equals(vertice)))
						aresta.setVerticeOrigem(vertice);
				}
			}
			
			getAreaApplet().getGrafo().removerAresta(aresta);
			getAreaApplet().getGrafo().setAresta(aresta);
			mudarVerticeInicial = false;
		}
	}
	
	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseAresta esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando uma liberação de algum botão do mouse.
	 *
	 * @param e o evento liberação do botão do mouse
	 * @see MenuMouseArea#mouseReleased
	 * @see MenuMouseRotulo#mouseReleased
	 * @see MenuMouseVertice#mouseReleased
	 */
	public void mouseReleased(MouseEvent e){}
	
	//Metodos privados da classe
	private void criarMenuArestaPadrao()
	{
		//Define os titulos dos menus
		String titulosMenus[] = new String[5];
		titulosMenus[0] = new String("Criar novo rótulo");
		titulosMenus[1] = new String("Criar novo segmento para aresta");
		titulosMenus[2] = new String("Definir novo vértice final");
		titulosMenus[3] = new String("Definir novo vértice inicial");
		titulosMenus[4] = new String("Remover aresta");
		
		//Cria os menus
		MenuItem menus[] = new MenuItem[5];
		menus[0] = new MenuItem(titulosMenus[0]);
		menus[1] = new MenuItem(titulosMenus[1]);
		menus[2] = new MenuItem(titulosMenus[2]);
		menus[3] = new MenuItem(titulosMenus[3]);
		menus[4] = new MenuItem(titulosMenus[4]);
		
		//Adiciono os menus na lista
		addNovoMenu(menus[0]);
		addNovoMenu(menus[1]);
		addSeparadorMenu();
		addNovoMenu(menus[2]);
		addNovoMenu(menus[3]);
		addSeparadorMenu();
		addNovoMenu(menus[4]);
	}
	
	private void criarNovoRotulo()
	{
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet());
		caixa.showCaixaDialogo(aresta.getRotulo());
	}
	
	private void criarNovoSegmento()
	{
		criarNovoSegmentoAresta = true;
		indiceQuebraAresta = aresta.getNumeroQuebras();
		aresta.setNumeroQuebras(aresta.getNumeroQuebras() + 1);
		aresta.setQuebraX(indiceQuebraAresta, x);
		aresta.setQuebraY(indiceQuebraAresta, y);
	}
	
	private void definirVerticeFinal()
	{
		mudarVerticeFinal = true;
	}
	
	private void definirVerticeInicial()
	{
		mudarVerticeInicial = true;
	}
	
	private void removerAresta()
	{
		getAreaApplet().getGrafo().removerAresta(aresta);
	}
}