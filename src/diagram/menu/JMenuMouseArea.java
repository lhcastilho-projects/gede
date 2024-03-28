package diagram.menu;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import diagram.Grafo;

import diagram.componente.Aresta;

import diagram.editor.AreaAplicativo;
import diagram.editor.EditorAplicativoGrafo;
import diagram.editor.EditorAppletGrafo;

import diagram.graphdrawing.SpringModel;

/**
 * A classe JMenuMouseArea utiliza as classes do pacote swing para exibir um menu
 * sempre que o usuário final clicar com o botão direito do mouse sobre o editor e
 * fora da área dos componentes vértice, aresta e rótulo.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas opções genericas que afetam o contexto do grafo total. Um exemplo de 
 * reutilização da classe JMenuMouseArea é mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o método setMenuMouse 
 * da classe EditorAplicativoGrafo ou EditorAppletGrafo para que seja atualizado 
 * o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends JMenuMouseArea<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe JMenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o código para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public vid menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das opções do JMenuMouseArea<br>
 *<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouse
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 * @see JMenuMouseVertice
 */
public class JMenuMouseArea extends JMenuMouse
{
	/**
	 * Cria um novo menu popup utilizado quando o usuário clicar no editor
	 * e fora da área dos componentes do grafo.
	 *
	 * @param origem a AreaAplicativo onde será exibido o menu popup
	 */
	public JMenuMouseArea(AreaAplicativo origem) 
	{
		super(origem);
		
		int 		i;
		JMenuItem	menuTemp;
		
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
	 * Método invocado pela classe AreaAplicativo sempre que o usuário final clicar
	 * com o botão direito do mouse sobre o editor e fora da área dos componentes.
	 * Neste caso o objeto passado pelo parâmetro é nulo, pois o clique deverá ser
	 * fora da área de qualquer objeto e x e y são as coordenadas do mouse no momento
	 * do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o botão 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see JMenuMouseAresta#menu
	 * @see JMenuMouseRotulo#menu
 	 * @see JMenuMouseVertice#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		JMenuItem	menus[];
		
		menus = new JMenuItem[2];
		menus[0] = getMenu(0);
		menus[1] = getMenu(1);
		
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		//Identifica se o menu bloquear ou desbloquear fica habilitado...
		//Para o bloquear
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
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
		}	
		else if (editorAppletGrafo != null)
		{
			//Applet
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
		}
		
		show(getAreaAplicativo(), x, y);
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
		JMenuItem	menuTemp[], menuItemSelecionar[];
		JMenu		menuAux;
		
		menuTemp = new JMenuItem[getNumeroMenus()];
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
			menuAux = (JMenu) menuTemp[4];
			
			menuItemSelecionar = new JMenuItem[menuAux.getItemCount()];
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
	}

	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see JMenuMouseAresta#mouseDragged
	 * @see JMenuMouseRotulo#mouseDragged
	 * @see JMenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see JMenuMouseAresta#mouseMoved
	 * @see JMenuMouseRotulo#mouseMoved
	 * @see JMenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum clique com botão do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see JMenuMouseAresta#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 * @see JMenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see JMenuMouseAresta#mouseEntered
	 * @see JMenuMouseRotulo#mouseEntered
	 * @see JMenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de saída do mouse no editor.
	 *
	 * @param e o evento de saída do mouse
	 * @see JMenuMouseAresta#mouseExited
	 * @see JMenuMouseRotulo#mouseExited
	 * @see JMenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando um pressionamento de algum botão do mouse.
	 *
	 * @param e o evento pressionamento do botão do mouse
	 * @see JMenuMouseAresta#mousePressed
	 * @see JMenuMouseRotulo#mousePressed
	 * @see JMenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando uma liberação de algum botão do mouse.
	 *
	 * @param e o evento liberação do botão do mouse
	 * @see JMenuMouseAresta#mouseReleased
	 * @see JMenuMouseRotulo#mouseReleased
	 * @see JMenuMouseVertice#mouseReleased
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
		JMenuItem menus[] = new JMenuItem[3];
		menus[0] = new JMenuItem(titulosMenus[0]);
		menus[1] = new JMenuItem(titulosMenus[1]);
		menus[2] = new JMenuItem(titulosMenus[2]);
				
		JMenu menuSelecionar = new JMenu(titulosMenus[3]);
				
		//Cria os submenus do menu selecionar
		JMenuItem subMenus[] = new JMenuItem[5];
		subMenus[0] = new JMenuItem(subTituloMenuSelecionar[0]);
		subMenus[1] = new JMenuItem(subTituloMenuSelecionar[1]);
		subMenus[2] = new JMenuItem(subTituloMenuSelecionar[2]);
		subMenus[3] = new JMenuItem(subTituloMenuSelecionar[3]);
		subMenus[4] = new JMenuItem(subTituloMenuSelecionar[4]);
		
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
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setEditar(EditorAplicativoGrafo.NAO_EDITAR);
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setEditar(EditorAppletGrafo.NAO_EDITAR);
	}
	
	private void desbloquearDiagrama()
	{
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setEditar(EditorAplicativoGrafo.EDITAR);
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setEditar(EditorAppletGrafo.EDITAR);
	}
	
	private void organizarDiagrama()
	{
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();

		SpringModel organizarDiagrama = new SpringModel(getAreaAplicativo().getGrafo());
		organizarDiagrama.setTamanhoAresta(130);
		organizarDiagrama.desenharGrafo();
		getAreaAplicativo().repaint();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setBarrasRolagem();
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setBarrasRolagem();
	}
	
	private void selecionarArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaAplicativo().getGrafo();
		arestas = gr.getTodasArestas();
		
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].selecionarComponente();
		
		getAreaAplicativo().repaint();
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
		
		gr = getAreaAplicativo().getGrafo();
		arestas = gr.getTodasArestas();
		
		//Seleciona os rotulos das arestas
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].getRotulo().setRotuloSelecionado(true);
		
		getAreaAplicativo().repaint();
	}
	
	private void selecionarRotulosVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaAplicativo().getGrafo();
		
		//Seleciona os rotulos dos vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).getRotulo().setRotuloSelecionado(true);
		
		getAreaAplicativo().repaint();
	}
	
	private void selecionarVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaAplicativo().getGrafo();
		
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).selecionarComponente();
		
		getAreaAplicativo().repaint();
	}
}