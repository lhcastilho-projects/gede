package diagram.menu;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import diagram.componente.Vertice;
import diagram.componente.Rotulo;

import diagram.editor.AreaAplicativo;

/**
 * A classe JMenuMouseVertice utiliza as classes do pacote swing para exibir um menu
 * sempre que o usuário final clicar com o botão direito do mouse sobre um vértice 
 * do editor.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas opções de vértice que afetam seu contexto. Um exemplo de reutilização da 
 * classe JMenuMouseVertice é mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o método setMenuMouseVertice
 * da classe EditorAplicativoGrafo ou EditorAppletGrafo para que seja atualizado 
 * o novo menu popup.<br>
 *<br>
 *&nbsp; import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends JMenuMouseVertice<br>
 * {<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe JMenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o código para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das opções do JMenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouse
 * @see JMenuMouseArea
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 */
public class JMenuMouseVertice extends JMenuMouse
{
	private Vertice		vertice;
	
	/**
	 * Cria um novo menu popup utilizado quando o usuário clicar em um vértice
	 * contido dentro do editor.
	 *
	 * @param origem a AreaAplicativo onde será exibido o menu popup
	 */
	public JMenuMouseVertice(AreaAplicativo origem) 
	{
		super(origem);
		
		int 		i;
		JMenuItem	menuTemp;
		
		vertice = null;
		criarMenuVerticePadrao();
		
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
	 * com o botão direito do mouse sobre um vértice contido no editor.
	 *
	 * As informações passadas pelos parâmetros são o objeto rótulo que obteve o 
	 * clique do usuário final e as respectivas coordenadas no momento do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o botão 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see JMenuMouseArea#menu
	 * @see JMenuMouseAresta#menu
 	 * @see JMenuMouseRotulo#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		vertice = (Vertice) objeto;
		show(getAreaAplicativo(), x, y);
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
		JMenuItem	menuTemp[];
		
		menuTemp = new JMenuItem[getNumeroMenus()];
		for (i = 0; i < getNumeroMenus(); i++)
			menuTemp[i] = getMenu(i);
			
		if (e.getSource() == menuTemp[0])
			criarNovoRotulo();
		else if (e.getSource() == menuTemp[2])
			definirNovaAltura();
		else if (e.getSource() == menuTemp[3])
			definirNovaLargura();
		else if (e.getSource() == menuTemp[5])
			removeVertice();
		
		getAreaAplicativo().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see JMenuMouseArea#mouseDragged
	 * @see JMenuMouseAresta#mouseDragged
	 * @see JMenuMouseRotulo#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see JMenuMouseArea#mouseMoved
	 * @see JMenuMouseAresta#mouseMoved
	 * @see JMenuMouseRotulo#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum clique com botão do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see JMenuMouseArea#mouseClicked
	 * @see JMenuMouseAresta#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see JMenuMouseArea#mouseEntered
	 * @see JMenuMouseAresta#mouseEntered
	 * @see JMenuMouseRotulo#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de saída do mouse no editor.
	 *
	 * @param e o evento de saída do mouse
	 * @see JMenuMouseArea#mouseExited
	 * @see JMenuMouseAresta#mouseExited
	 * @see JMenuMouseRotulo#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando um pressionamento de algum botão do mouse.
	 *
	 * @param e o evento pressionamento do botão do mouse
	 * @see JMenuMouseArea#mousePressed
	 * @see JMenuMouseAresta#mousePressed
	 * @see JMenuMouseRotulo#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando uma liberação de algum botão do mouse.
	 *
	 * @param e o evento liberação do botão do mouse
	 * @see JMenuMouseArea#mouseReleased
	 * @see JMenuMouseAresta#mouseReleased
	 * @see JMenuMouseRotulo#mouseReleased
	 */
	public void mouseReleased(MouseEvent e){}
	
	//Metodos privados da classe
	private void criarMenuVerticePadrao()
	{
		//Define os titulos dos menus
		String titulosMenus[] = new String[4];
		titulosMenus[0] = new String("Cria novo rótulo");
		titulosMenus[1] = new String("Definir nova altura");
		titulosMenus[2] = new String("Definir nova largura");
		titulosMenus[3] = new String("Remover vértice");
		
		//Cria os menus
		JMenuItem menus[] = new JMenuItem[4];
		menus[0] = new JMenuItem(titulosMenus[0]);
		menus[1] = new JMenuItem(titulosMenus[1]);
		menus[2] = new JMenuItem(titulosMenus[2]);
		menus[3] = new JMenuItem(titulosMenus[3]);
		
		//Adiciono os menus na lista
		addNovoMenu(menus[0]);
		addSeparadorMenu();
		addNovoMenu(menus[1]);
		addNovoMenu(menus[2]);
		addSeparadorMenu();
		addNovoMenu(menus[3]);
	}
	
	private void criarNovoRotulo()
	{
		String texto;
		
		Rotulo rotulo = new Rotulo("Vazio");
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite o conteúdo do rótulo");
		if (texto != null)
			rotulo.setTexto(texto);
		
		vertice.setRotulo(rotulo);
	}
	
	private void definirNovaAltura()
	{
		String 	texto;
		int		altura;
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite a nova altura do vértice");
		if (texto != null)
		{
			try{
				altura = Integer.parseInt(texto);
				vertice.setAltura(altura);
			}catch(NumberFormatException e){
			}
		}
	}
	
	private void definirNovaLargura()
	{
		String 	texto;
		int		largura;
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite a nova altura do vértice");
		if (texto != null)
		{
			try{
				largura = Integer.parseInt(texto);
				vertice.setLargura(largura);
			}catch(NumberFormatException e){
			}
		}
	}
	
	private void removeVertice()
	{
		getAreaAplicativo().getGrafo().removerVertice(vertice);
	}
}