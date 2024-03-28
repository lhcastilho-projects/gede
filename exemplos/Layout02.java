import diagram.*;
import diagram.componente.ArestaSimples;
import diagram.componente.VerticeRetangulo;
import diagram.editor.EditorAplicativoGrafo;
import diagram.graphdrawing.SpringModel;

import java.awt.event.*;
import java.awt.*;

public class Layout02
{
	public Layout02()
	{
		VerticeRetangulo vertices[] = new VerticeRetangulo[6];
		
		vertices[0] = new VerticeRetangulo(350, 200);
		vertices[0].setCorFundo(Color.blue);
		vertices[0].setLargura(20);
		
		vertices[1] = new VerticeRetangulo(270, 90);
		vertices[1].setCorFundo(Color.blue);
		vertices[1].setLargura(20);
		
		vertices[2] = new VerticeRetangulo(150, 80);
		vertices[2].setCorFundo(Color.blue);
		vertices[2].setLargura(20);
		
		vertices[3] = new VerticeRetangulo(50, 210);
		vertices[3].setCorFundo(Color.blue);
		vertices[3].setLargura(20);
		
		vertices[4] = new VerticeRetangulo(160, 350);
		vertices[4].setCorFundo(Color.blue);
		vertices[4].setLargura(20);
		
		vertices[5] = new VerticeRetangulo(280, 340);
		vertices[5].setCorFundo(Color.blue);
		vertices[5].setLargura(20);
		
		ArestaSimples arestas[] = new ArestaSimples[6];
		
		arestas[0] = new ArestaSimples(vertices[0], vertices[1]);
		arestas[1] = new ArestaSimples(vertices[0], vertices[2]);
		arestas[2] = new ArestaSimples(vertices[1], vertices[5]);
		arestas[3] = new ArestaSimples(vertices[2], vertices[4]);
		arestas[4] = new ArestaSimples(vertices[3], vertices[4]);
		arestas[5] = new ArestaSimples(vertices[3], vertices[5]);
		
		Grafo gr = new Grafo();
		gr.setVertices(vertices);
		gr.setArestas(arestas);
		
		SpringModel layout02 = new SpringModel(gr);
		layout02.desenharGrafo();
		
		EditorAplicativoGrafo editor = new EditorAplicativoGrafo(gr);
		
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
		new Layout02();
	}
}