package diagram.menu;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.util.LinkedList;

import java.awt.Component;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import diagram.editor.AreaAplicativo;

/**
 * JMenuMouse é uma classe abstrata que utiliza as classes do pacote swing para 
 * permitir a criação de menus Popup acionados sempre que houver um clique com o 
 * botão direito do mouse.
 * 
 * As novas opções de menus podem ser inseridos somente ao final da lista de menus, 
 * ou seja, acrescentados somente abaixo do menu popup. Um exemplo simples de como 
 * criar um menu popup é mostrado abaixo.
 *
 * Quando criar um novo modelo de menu, deve-se sobrescrever o método <B>menu</B> e
 * codificar as informações necessárias para exibir o novo menu no editor. Este método
 * será sempre chamado pela classe AreaAplicativo quando um evento do mouse com o 
 * clique do botão direito ocorrer sobre o editor.
 *<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *&nbsp;public class ExemploMenu extends MenuMouse<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ExemploMenu(AreaAplicativo origem)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(origem);<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//Inserir o código do novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o menu popup<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouseArea
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 * @see JMenuMouseVertice
 */
public abstract class JMenuMouse extends JPopupMenu implements ActionListener, MouseListener, MouseMotionListener
{
	private final int		TAMANHO_FONTE = 10;
	
	private LinkedList		listaMenu;
	
	private AreaAplicativo	origem;
	
	/**
	 * Cria um novo menu.
	 *
	 * Como a classe JMenuMouse é abstrata, os novos menus não podem chamar
	 * está classe diretamente.
	 *
	 * @param origem a área so painel na qual o menu popup será exibido.
	 */
	public JMenuMouse(AreaAplicativo origem) 
	{
		super();
	
		this.origem	= origem;
		listaMenu = new LinkedList();
	}
	
	//Metodos para a adição de novos menus
	
	/**
	 * Acrescenta ao final do popup um novo menu.
	 *
	 * @param menu o novo menu a ser inserido
	 * @see JMenuMouse#addSeparadorMenu
	 */
	public void addNovoMenu(JMenuItem menu)
	{
		int			i;
		JMenu		menuAux;
		JMenuItem	menuItem;
		
		if (menu != null)
		{
			//Tenta fazer uma coersão para um JMenu
			try{
				menuAux = (JMenu) menu;
			}catch(Exception e){
				menuAux = null;	
			}
			
			if (menuAux != null)
			{
				//Objeto JMenu		
				menuAux.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
				listaMenu.add(menuAux);
				
				for (i = 0; i < menuAux.getItemCount(); i++)
				{
					menuItem = menuAux.getItem(i);
					menuItem.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
					menuItem.addActionListener(this);
				}
			}
			else
			{
				//ObjetoJMenuItem
				menu.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
				listaMenu.add(menu);
				menu.addActionListener(this);	
			}
		}
	}
	
	/**
	 * Acrescenta ao final do popup uma linha de separação de menus.
	 *
	 * @see JMenuMouse#addNovoMenu
	 */
	public void addSeparadorMenu()
	{
		listaMenu.add(null);
	}
	
	//Metodos get
	
	/**
	 * Retorna a área aplicativo do pacote swing na qual o menu está sendo exibido.
	 *
	 * @return a área aplicativo do editor
	 */
	public AreaAplicativo getAreaAplicativo()
	{
		return origem;
	}
	
	/**
	 * Retorna o número de menus existente no JMenuMouse incluido os separadores
	 * de menus.
	 *
	 * @return o número de menus
	 */
	public int getNumeroMenus()
	{
		return listaMenu.size();
	}
	
	/**
	 * Retorna o menu localizado na posição passada pelo parâmentro.
	 *
	 * @param localizacao a localização do menu no popup
	 * @return o menu na posição especificada no parâmetro
	 */
	public JMenuItem getMenu(int localizacao)
	{
		return (JMenuItem) listaMenu.get(localizacao);
	}
	
	/**
	 * Remove todos os menus armazenados na classe JMenuMouse.
	 */
	public void limparMenu()
	{
		listaMenu.clear();
	}
	
	//Metodos chamados pela classe para instanciar os menus
	
	/**
	 * Método invocado pela classe AreaAplicativo sempre que o usuário final
	 * clicar com o botão direto do mouse sobre a área do editor.
	 *
	 * Esse método deve ser utilizado pelas subclasses para apresentar o popup 
	 * menu no editor e executar suas tarefas após o clique sobre o
	 * popup menu.
	 */
	public abstract void menu(Object objeto, int x, int y);
}