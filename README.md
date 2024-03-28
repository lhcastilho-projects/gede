<div align="center">
  <h1>GeDE</h1>
  <h2>Generic Diagram Editor</h2>
</div>

![openJDK] ![license] ![spring.model]



<br />

# Introdução
Grafos são objetos matemáticos que consistem de um conjunto finito de vértices e arestas e aplicáveis em sistemas que possuem um número finito de estados discretos e um processo de transição. O GeDE é uma biblioteca de classe, escrita em linguagem Java, que fornece recursos de edição e apresentação de diagramas. Ela foi desenvolvida em novembro de 2002 como trabalho de iniciação científica, executada e testada na JVM 1.4.2 (se não me falhe a memória).  <br />
O Spring Model foi o algoritmo eleito e implementado na sua biblioteca de classes como recurso para modelar um diagrama automaticamente.



<br />

# Exemplo de Captura da tela
O figura abaixo mostra a captura da tela após executar a classe Layout02.java da pasta exemplos.

<div align="center">
  <img src="https://github.com/lhcastilho-projects/gede/blob/main/img/screenshot.png"/>
</div>



<br />

# Instalação
A biblioteca foi desenvolvido utilizado a IDE Eclipse e não possui um processo de instalação. Todo o código fonte está disponível para download e não há dependências externas de outros bibliotecas. Veja as próximas seções para mais detalhes de como utilizá=lo. <br /> <br />
![eclipse]



<br />

# Como utilizar
O exemplo abaixo mostra como utilizar a biblioteca GeDE. O desenvolvedor precisa instanciar uma lista de vértices e arestas e criar um grafo. Ele deverá escolher um editor e adicionar o grafo a ele.
A pasta exemplos na raiz do projeto possui alguns códigos fontes criados em 2002 com detalhes de como criar os elementos do grafo e inseri-lo no editor. Consulte ela para mais detalhes. Todos os exemplos possuem um método main para facilitar a execução e apresentação do diagrama.

```java
import diagram.*;
import java.awt.event.*;
import java.awt.*;

public class Exemplo01
{
	public Exemplo01()
	{
		
		VerticeCirculo vertices[] = new VerticeCirculo[3];
		
		vertices[0] = new VerticeCirculo(10, 10);
		vertices[0].setCorFundo(Color.gray);
		vertices[0].setLargura(20);
		
		vertices[1] = new VerticeCirculo(50, 20);
		vertices[1].setCorFundo(Color.gray);
		vertices[1].setLargura(20);
		
		vertices[2] = new VerticeCirculo(200, 200);
		vertices[2].setCorFundo(Color.gray);
		vertices[2].setLargura(20);
		
		ArestaSimples arestas[] = new ArestaSimples[3];
		
		arestas[0] = new ArestaSimples(vertices[0], vertices[1]);
		arestas[1] = new ArestaSimples(vertices[1], vertices[2]);
		arestas[2] = new ArestaSimples(vertices[0], vertices[2]);
		
		Grafo triangulo = new Grafo();
		triangulo.setVertices(vertices);
		triangulo.setArestas(arestas);
		
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
		new Exemplo01();
		
	}
			
}
```

<br />

# Status
O projeto foi encerrado em 2002 e desde então não recebeu novas atualizações. 



<br />

# Contribuição
O projeto foi desenvolvido por lhcs. <br />
Meu contato no Linkedin <br /> <br />
<a href="https://www.linkedin.com/in/lhcs/"><img src="https://img.shields.io/static/v1?label=Linkedin&message=lhcs&color=blue&style=flat&logo=linkedin" alt="Linkedin Follow" /></a>


[openJDK]: https://img.shields.io/static/v1?label=OpenJDK&message=1.8&color=blue&style=flat&logo=openjdk
[license]: https://img.shields.io/static/v1?label=License&message=MIT&color=red&style=flat
[spring.model]: https://img.shields.io/static/v1?label=Graph%20Drawing&message=Spring%20Model&color=red&style=flat
[eclipse]: https://img.shields.io/static/v1?label=Eclipse%20&message=4.29.0&color=blue&style=flat&logo=eclipse

