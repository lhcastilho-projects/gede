import diagram.*;
import diagram.componente.ArestaSimples;
import diagram.componente.VerticeElipse;
import diagram.editor.EditorAplicativoGrafo;
import diagram.graphdrawing.SpringModel;

import java.awt.event.*;
import java.awt.*;

public class Layout01
{
	public Layout01()
	{

		VerticeElipse vertices[] = new VerticeElipse[3];
		
		vertices[0] = new VerticeElipse(10, 10);
		vertices[0].setCorFundo(Color.gray);
		vertices[0].setLargura(20);
		
		vertices[1] = new VerticeElipse(50, 20);
		vertices[1].setCorFundo(Color.gray);
		vertices[1].setLargura(20);
		
		vertices[2] = new VerticeElipse(200, 200);
		vertices[2].setCorFundo(Color.gray);
		vertices[2].setLargura(20);
		
		ArestaSimples arestas[] = new ArestaSimples[3];
		
		arestas[0] = new ArestaSimples(vertices[0], vertices[1]);
		arestas[1] = new ArestaSimples(vertices[1], vertices[2]);
		arestas[2] = new ArestaSimples(vertices[0], vertices[2]);
		
		Grafo triangulo = new Grafo();
		triangulo.setVertices(vertices);
		triangulo.setArestas(arestas);

		SpringModel layout01 = new SpringModel(triangulo);
		layout01.desenharGrafo();
		
		EditorAplicativoGrafo editor = new EditorAplicativoGrafo(triangulo);
		
		editor.addWindowListener(new WindowAdapter()
			{	
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
		
			}		
		);
		
	}

	public static void main(String args[])
	{
		new Layout01();
		
	}
			
}