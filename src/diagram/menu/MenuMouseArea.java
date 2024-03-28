package diagram.menu;

import java.awt.MenuItem;
import java.awt.Menu;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import diagram.Grafo;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

import diagram.editor.AreaApplet;
import diagram.editor.EditorAppletGrafo;

import diagram.graphdrawing.SpringModel;

/**
 * A classe MenuMouseArea utiliza as classes do pacote awt para exibir um menu
 * sempre que o usuário final clicar com o botão direito do mouse sobre o editor e
 * fora da área dos componentes vértice, aresta e rótulo.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas opções genericas que afetam o contexto do grafo total. Um exemplo de 
 * reutilização da classe JMenuMouseArea é mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o método setMenuMouse 
 * da classe EditorAppletGrafo para que seja atualizado o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends MenuMouseArea<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe MenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o código para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public vid menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das opções do MenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see MenuMouse
 * @see MenuMouseAresta
 * @see MenuMouseRotulo
 * @see MenuMouseVertice
 */
public class MenuMouseArea extends MenuMouse
{
	/**
	 * Cria um novo menu popup utilizado quando o usuário clicar no editor
	 * e fora da área dos componentes do grafo.
	 *
	 * @param origem a AreaApplet onde será exibido o menu popup
	 */
	public MenuMouseArea(AreaApplet origem) 
	{
		super(origem);
		
		int 		i;
		MenuItem	menuTemp;
		
		criarMenuPadrao();
		
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
	 * com o botão direito do mouse sobre o editor e fora da área dos componentes.
	 * Neste caso o objeto passado pelo parâmetro é nulo, pois o clique deverá ser
	 * fora da área de qualquer objeto e x e y são as coordenadas do mouse no momento
	 * do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o botão 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see MenuMouseAresta#menu
	 * @see MenuMouseRotulo#menu
 	 * @see MenuMouseVertice#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		MenuItem	menus[];
		
		menus = new MenuItem[2];
		menus[0] = getMenu(0);
		menus[1] = getMenu(1);
		
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		//Identifica se o menu bloquear ou desbloquear fica habilitado...
		if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
		{
			//Não deve editar
			menus[0].setEnabled(false);
			menus[1].setEnabled(true);
		}
		else
		{
			//Deve editar
			menus[0].setEnabled(true);
			menus[1].setEnabled(false);
		}
				
		show(getAreaApplet(), x, y);
	}
	
	/**
	 * Método invocado pela própria classe sempre que o usuário final clicar 
	 * com o botão do mouse sobre algum menu do menu popup.
	 *
	 * @param e o evento gerado pelo clique do mouse sobre o menu
	 */
	public void actionPerformed(ActionEvent e)
	{
		int 		i;
		MenuItem	menuTemp[], menuItemSelecionar[];
		Menu		menuAux;
		
		menuTemp = new MenuItem[getNumeroMenus()];
		for (i = 0; i < getNumeroMenus(); i++)
			menuTemp[i] = getMenu(i);
			
		if (e.getSource() == menuTemp[0])
			bloquearDiagrama();
		else if (e.getSource() == menuTemp[1])
			desbloquearDiagrama();
		else if (e.getSource() == menuTemp[3])
			organizarDiagrama();
		else
		{
			//Para os submenus no menu selecionar
			menuAux = (Menu) menuTemp[4];
			
			menuItemSelecionar = new MenuItem[menuAux.getItemCount()];
			for (i = 0; i < menuAux.getItemCount(); i++)
				menuItemSelecionar[i] = menuAux.getItem(i);
				
			//Identifica qual das opções foi clicada
			if (e.getSource() == menuItemSelecionar[0])
				selecionarArestas();
			else if (e.getSource() == menuItemSelecionar[1])
				selecionarRotulos();
			else if (e.getSource() == menuItemSelecionar[2])
				selecionarRotulosArestas();
			else if (e.getSource() == menuItemSelecionar[3])
				selecionarRotulosVertices();
			else if (e.getSource() == menuItemSelecionar[4])
				selecionarVertices();
		}
		
		getAreaApplet().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see MenuMouseAresta#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 * @see MenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see MenuMouseAresta#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 * @see MenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum clique com botão do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see MenuMouseAresta#mouseClicked
	 * @see MenuMouseRotulo#mouseClicked
	 * @see MenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see MenuMouseAresta#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 * @see MenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de saída do mouse no editor.
	 *
	 * @param e o evento de saída do mouse
	 * @see MenuMouseAresta#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 * @see MenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando um pressionamento de algum botão do mouse.
	 *
	 * @param e o evento pressionamento do botão do mouse
	 * @see MenuMouseAresta#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 * @see MenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando uma liberação de algum botão do mouse.
	 *
	 * @param e o evento liberação do botão do mouse
	 * @see MenuMouseAresta#mouseReleased
	 * @see MenuMouseRotulo#mouseReleased
	 * @see MenuMouseVertice#mouseReleased
	 */
	public void mouseReleased(MouseEvent e){}
	
	//Metodos privados da classe	
	private void criarMenuPadrao()
	{
		//Define os titulos dos menus
		String titulosMenus[] = new String[4];
		titulosMenus[0] = new String("Bloquear diagrama");
		titulosMenus[1] = new String("Desbloquear diagrama");
		titulosMenus[2] = new String("Organizar diagrama automaticamente");
		titulosMenus[3] = new String("Selecionar diagrama");
		
		//Define os subtitulos do titulo selecionar
		String subTituloMenuSelecionar[] = new String[5];
		subTituloMenuSelecionar[0] = new String("Todas as arestas");
		subTituloMenuSelecionar[1] = new String("Todos os rótulos");
		subTituloMenuSelecionar[2] = new String("Todos os rótulos das arestas");
		subTituloMenuSelecionar[3] = new String("Todos os rótulos dos vértices");
		subTituloMenuSelecionar[4] = new String("Todos os vértices");
		
		//Cria os menus
		MenuItem menus[] = new MenuItem[3];
		menus[0] = new MenuItem(titulosMenus[0]);
		menus[1] = new MenuItem(titulosMenus[1]);
		menus[2] = new MenuItem(titulosMenus[2]);
		
		Menu menuSelecionar = new Menu(titulosMenus[3]);
				
		//Cria os submenus do menu selecionar
		MenuItem subMenus[] = new MenuItem[5];
		subMenus[0] = new MenuItem(subTituloMenuSelecionar[0]);
		subMenus[1] = new MenuItem(subTituloMenuSelecionar[1]);
		subMenus[2] = new MenuItem(subTituloMenuSelecionar[2]);
		subMenus[3] = new MenuItem(subTituloMenuSelecionar[3]);
		subMenus[4] = new MenuItem(subTituloMenuSelecionar[4]);
		
		//Adiciona os submenus no menu selecionar
		menuSelecionar.add(subMenus[0]);
		menuSelecionar.add(subMenus[1]);
		menuSelecionar.add(subMenus[2]);
		menuSelecionar.add(subMenus[3]);
		menuSelecionar.add(subMenus[4]);
		
		//Adiciono os menus na lista
		addNovoMenu(menus[0]);
		addNovoMenu(menus[1]);
		addSeparadorMenu();
		addNovoMenu(menus[2]);
		addNovoMenu(menuSelecionar);
	}
	
	private void bloquearDiagrama()
	{
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		editorAppletGrafo.setEditar(EditorAppletGrafo.NAO_EDITAR);
	}
	
	private void desbloquearDiagrama()
	{
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		editorAppletGrafo.setEditar(EditorAppletGrafo.EDITAR);
	}
	
	private void organizarDiagrama()
	{
		int x, y, largura, altura;
		
		largura = 210;
		altura = 80;
		x = (getAreaApplet().getX() + getAreaApplet().getWidth()/2);
		y = (getAreaApplet().getY() + getAreaApplet().getHeight()/2);
		
		Label msg = new Label("Aguarde um momento.");
		msg.setBounds(20, 35, 140, 15);
		
		Dialog cxDialog = new Dialog(new Frame(), false);
		cxDialog.setLayout(null);
		cxDialog.setTitle("Caixa de Dialogo");
		cxDialog.setResizable(false);
		cxDialog.setSize(largura, altura);
		cxDialog.setLocation(x, y);
		cxDialog.add(msg);
		cxDialog.show();		
		
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		
		SpringModel organizarDiagrama = new SpringModel(getAreaApplet().getGrafo());
		organizarDiagrama.setTamanhoAresta(130);
		organizarDiagrama.desenharGrafo();
		getAreaApplet().repaint();
		editorAppletGrafo.setBarrasRolagem();
			
		cxDialog.dispose();
	}
	
	private void selecionarArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaApplet().getGrafo();
		arestas = gr.getTodasArestas();
		
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].selecionarComponente();
		
	}
	
	private void selecionarRotulos()
	{
		selecionarRotulosArestas();
		selecionarRotulosVertices();
	}
	
	private void selecionarRotulosArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaApplet().getGrafo();
		arestas = gr.getTodasArestas();
		
		//Seleciona os rotulos das arestas
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].getRotulo().setRotuloSelecionado(true);
	}
	
	private void selecionarRotulosVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaApplet().getGrafo();
		
		//Seleciona os rotulos dos vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).getRotulo().setRotuloSelecionado(true);
	}
	
	private void selecionarVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaApplet().getGrafo();
		
		//Seleciona todos os vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).selecionarComponente();
	}	
}