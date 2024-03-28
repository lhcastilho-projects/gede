package diagram;

import java.util.LinkedList;
import java.util.MissingResourceException;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

/**
 * Está classe é responsável pela criação dos grafos apresentados no editor. 
 * É importante observar que vértices e arestas duplicados não são permitidos
 * e portanto descartados quando identificados por esta classe. Então novas
 * instâncias de vértices e arestas devem ser passados para que está classe
 * reconheça a estrutura de grafo. Outra observação importante com relação as 
 * arestas é o fato delas não poderem ser armazenadas caso seus respectivos
 * vértices origem e destino não forem inseridos anteriormente.
 * 
 * A classe grafo utiliza a lista de adjacência como forma de representação 
 * substituindo o vetor de vértices por uma outra lista encadeada.
 * 
 * Um exemplo de como criar um grafo no editor é mostrado no exemplo abaixo. <br>
 * <br>
 * ...<br>
 * ...<br>
 * Grafo exemplo01 = new Grafo();<br>
 * exemplo01.setAresta(new ArestaSimples(new VerticeRetangulo(), new VerticeRetangulo()));<br>
 * ...<br>
 * ...<br>
 * <br>
 * A classe também possui alguns métodos conhecidos dos grafos tais como os percursos
 * em largura e profundidade e o algoritmo de menor caminho muito utilizado pela 
 * classe SpringModel para desenhar um grafo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see diagram.graphdrawing.SpringModel
 */
public class Grafo extends Object
{
	private int					numeroTotalArestas;
	
	private LinkedList 			listaVertice,
								listaAresta[],
								filaVertices,
								pilhaVertices;
	
	/**
	 * Cria um grafo sem nenhum vértice e aresta.
	 */
	public Grafo()
	{
		listaVertice = new LinkedList();
		numeroTotalArestas = 0;
	}
	
	/**
	 * Cria um grafo com um número de vértices e arestas passados pelos parâmetros.
	 *
	 * @param vertices o vetor que contém todos os vértices
	 * @param arestas o vetor que contém todas as arestas
	 */
	public Grafo(Vertice vertices[], Aresta arestas[])
	{
		listaVertice = new LinkedList();
		numeroTotalArestas = 0;
		setVertices(vertices);
		setArestas(arestas);
	}
	
// Métodos Get...

	/**
	 * Retorna todos os vértices conmtidos no grafo.
	 *
	 * @return o vetor contendo todos os vértices
	 * @see Grafo#getTodasArestas
	 */
	public Vertice[] getTodosVertices()
	{
		int i;
		Vertice 	vertices[];
				
		if (listaVertice.size() == 0) 
			return null; //Caso não exista Vértices
			
		vertices = new Vertice[listaVertice.size()];
		
		for (i = 0;i < listaVertice.size(); i++)
			vertices[i] = (Vertice) listaVertice.get(i);
			
		return vertices;
	}

	/**
	 * Retorna o vértice especificado pelo parâmetro posição do vértice.
	 * 
	 * @param posicao a localização do vértice na lista encadeada
	 * @return o vértice especificado na posição
	 * @see Grafo#getAresta
	 */
	public Vertice getVertice(int posicao)
	{
		if ((posicao >= 0) && (posicao < listaVertice.size()))
			return (Vertice) listaVertice.get(posicao);
		
		return null;
	}
	
	/**
	 * Identifica se o vértice está ou não contido dentro da estrutura da clase
	 * grafo. Caso isto seja verdadeiro, o método retorna verdadeiro e falso caso
	 * contrário.
	 *
	 * @param vertice o vértice que deseja verificar a sua existência
	 * @return em caso de existir ou não o vértice
	 * @see Grafo#existeAresta
	 */
	public boolean existeVertice(Vertice vertice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size() ; i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna todos os vértices adjacentes em relação ao vértice passado pelo
	 * parâmetro.
	 *
	 * @param vertice o vertice que deseja identificar seus adjacentes
	 * @return o vetor contendo todos os vértices adjacentes
	 */
	public Vertice[] getVerticesAdjacentes(Vertice vertice)
	{
		int 		i;
		Vertice		vertices[];
		Aresta		arestas[];
		
		arestas = getAresta(vertice);
		
		if (arestas == null)
			return null;
		
		vertices = new Vertice[arestas.length];
		
		for (i = 0; i < arestas.length; i++)
		{
			if (arestas[i].getVerticeOrigem().equals(vertice))
				vertices[i] = arestas[i].getVerticeDestino();
			else
				vertices[i] = arestas[i].getVerticeOrigem();
		}
		
		return vertices;
	}
	
	/**
	 * Retorna todos as arestas contidas no grafo.
	 *
	 * @return o vetor contendo todas as arestas
	 */
	public Aresta[] getTodasArestas()
	{
		int 		i, j, k, contador;
		boolean 	existeAresta;
		Vertice 	verticeTemp;
		Aresta		arestas[],
					arestaTemp;
		
		if (numeroTotalArestas <= 0)	
			return null;
		
		contador = 0;
		existeAresta = false;
		arestas = new Aresta[numeroTotalArestas];
		
		for (j = 0; j < listaVertice.size(); j++)
		{
			//Pecorrer todos os Vértices	
			verticeTemp = (Vertice) listaVertice.get(j);

			for (k = 0; k < listaAresta[j].size(); k++)
			{
				arestaTemp = (Aresta) listaAresta[j].get(k);
				
				for (i = 0; i < arestas.length; i++)
				{
					//Percorre o vetor de retorno para identificar as arestas nao incluidas
					if (arestaTemp.equals(arestas[i]))
						existeAresta = true;
				}
				
				if (!existeAresta)
				{
					arestas[contador] = arestaTemp;	
					contador++;
				}
				
				existeAresta = false;
			}
		}
		
		return arestas;
	}
	
	/**
	 * Retorna a(s) aresta(s) na qual o vértice passado pelo parâmetro está 
	 * diretamente conectado a aresta, ou seja, é um vértice origem ou destino.
	 * 
	 * @param vertice o vértice onde a aresta está conectada
	 * @return o vetor contendo as arestas ligadas ao vértice
	 * @see Grafo#getVertice
	 */
	public Aresta[] getAresta(Vertice vertice)
	{
		//Retorna nulo em caso de não existir o vertice, não existir nenhuma arestas em todo o grafo
		//Para o caso de existir o vertice mas não exitir a aresta será retornado nulo.
		int i, j;
		Vertice verticeTemp;
		Aresta	arestas[];
		
		if (numeroTotalArestas <= 0)
			return null;
			
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
			{
				if (listaAresta[i].size() == 0)
					return null;
					
				arestas = new Aresta[listaAresta[i].size()];
				
				for (j = 0; j < arestas.length; j++)
				{
					arestas[j] = (Aresta) listaAresta[i].get(j);
				}
				
				return arestas;
			}
		}
		
		return null;
	}
	
	/**
	 * Retorna a aresta na qual o vértice passado pelo parâmetro está 
	 * diretamente conectado a aresta e o indice identifica a localização extata 
	 * dentro da lista encadeada do vértice.
	 * 
	 * @param vertice o vértice onde a aresta está conectada
	 * @param localização a localização da aresta na lista encadeada do vértice
	 * @return a aresta diretamente ligada ao vértice
	 * @see Grafo#getVertice
	 */
	public Aresta getAresta(Vertice vertice, int indice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
			{
				//O vetice é igual ao passado pelo parâmetro
				if ((indice < listaAresta[i].size()) && (indice >= 0))
					return (Aresta) listaAresta[i].get(indice);
			}
		}
		
		return null;
	}
	
	/**
	 * Identifica se a aresta está ou não contido dentro da estrutura da clase
	 * grafo. Caso isto seja verdadeiro, o método retorna verdadeiro e falso caso
	 * contrário.
	 *
	 * @param aresta a aresta que deseja verificar a sua existência
	 * @return em caso de existir ou não a aresta
	 * @see Grafo#existeVertice
	 */
	public boolean existeAresta(Aresta aresta)
	{
		int i, j;
		Aresta arestaTemp;
		
		if (listaAresta == null)
			return false;
			
		for (i = 0; i < listaAresta.length; i++)
		{
			for (j = 0; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				
				if (arestaTemp.equals(aresta))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Retorna o número total de aresta conectadas ao vértice passado pelo
	 * parâmetro.
	 *
	 * @param vertice o vértice contido no grafo
	 * @return o número de arestas conectadas ao vértice
	 * @see Grafo#getNumeroTotalArestas
	 * @see Grafo#getNumeroTotalVertices
	 */
	public int getNumeroTotalArestasDoVertice(Vertice vertice)
	{
		int i;
		Vertice verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			if (verticeTemp.equals(vertice))
				return listaAresta[i].size();
		}
		
		return -1;
	}
	
	/**
	 * Retorna a localização extaa do vértice na estrutura de lista encadeada.
	 *
	 * @param vertice o vértice que deseja verficar sua posição na lista
	 * @return a localização do vértice na lista encadeada
	 */
	public int getLocalizacaoVertice(Vertice vertice)
	{
		//Retorna a localização do vértice na lista de vértices
		int i;
		Vertice		verticeTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			
			if (verticeTemp.equals(vertice))
				return i;
		}
		
		return 0;
	}

	/**
	 * Retorna o número total de arestas contidas no grafo.
	 *
	 * @return o número total de arestas
	 * @see Grafo#getNumeroTotalVertices
	 * @see Grafo#getNumeroTotalArestasDoVertice
	 */
	public int getNumeroTotalArestas()
	{
		return numeroTotalArestas;
	}
	
	/**
	 * Retorna o número total de vértices contido no grafo.
	 *
	 * @return o número total de vértices
	 * @see Grafo#getNumeroTotalArestas
	 */
	public int getNumeroTotalVertices()
	{
		return listaVertice.size();
	}
	
// Métodos Set...

	/**
	 * Acrescenta um conjunto de vértice ao grafo.
	 *
	 * @param vertices o vetor de vértices
	 */
	public void setVertices(Vertice vertices[]) throws NullPointerException
	{
		int i;
		
		if (vertices == null)
			throw new NullPointerException("**Excecao**\ndiagram.GrafoException: Vertice nulo.");

		for (i = 0;i < vertices.length; i++)
		{
			if (!existeVertice(vertices[i]))
				listaVertice.add(vertices[i]);
		}
		
		criarListaAresta();
	}
	
	/**
	 * Acrescenta somente um vértice ao grafo.
	 *
	 * @param vertice o vértices a ser inserido
	 */
	public void setVertice(Vertice vertice) throws NullPointerException
	{
		if (vertice == null)
			throw new NullPointerException("**Excecao**\ndiagram.GrafoException: Vertice nulo.");
			
		if (!existeVertice(vertice))
		{	
			listaVertice.add(vertice);
			criarListaAresta();
		}
	}
	
	/**
	 * Acrescenta um conjunto de arestas ao grafo.
	 *
	 * @param arestas o vetor de arestas ser inserida
	 */	
	public void setArestas(Aresta arestas[]) throws MissingResourceException
	{
		int i;
		
		if (arestas != null)
		{
			i = 0;
			try{
				for (i = 0; i < arestas.length; i++)
					setAresta(arestas[i]);
			}catch (MissingResourceException e){
				MissingResourceException faltaVertice = new MissingResourceException("\n*** Excecao ***\ndiagram.GrafoException: O vertice de origem ou destino nao existe.", "Grafo", arestas[i].toString());	
				throw faltaVertice;	
			}
		}
	}
	
	/**
	 * Acrescenta uma arestas ao grafo.
	 *
	 * @param aresta a nova aresta a ser inserida
	 */	
	public void setAresta(Aresta aresta) throws MissingResourceException
	{
		int i;
		
		if (!existeAresta(aresta))
		{
			if (existeVertice(aresta.getVerticeOrigem()) && existeVertice(aresta.getVerticeDestino()))
			{
				//O vertice de Origem e o vertice de Destino estão contidos na lista...
				//Pode ser inserido a aresta...
				for (i = 0; i < listaVertice.size(); i++)
				{
					if (getVertice(i).equals(aresta.getVerticeOrigem()))
					{
						listaAresta[i].add(aresta);
						numeroTotalArestas++;
					}
					
					if (getVertice(i).equals(aresta.getVerticeDestino()))
						listaAresta[i].add(aresta);
				}
			}
			else
			{
				//Gera uma exceção identificando que não existe uma aresta...
				MissingResourceException faltaVertice = new MissingResourceException("\n*** Excecao ***\ndiagram.GrafoException: O vertice de origem ou destino nao existe.", "Grafo", aresta.toString());	
				throw faltaVertice;
			}
		}
	}
	
	/**
	 * Remove um vértice do grafo especificado pelo parâmetro.
	 *
	 * @param vertice o vértice a ser removido
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerGrafo
	 */
	public void removerVertice(Vertice vertice)
	{
		int			i,
					contTemp,
					localVertice;
		LinkedList	listaArestaTemp[];
		
		contTemp = 0;
		localVertice = getLocalizacaoVertice(vertice);
		listaArestaTemp = new LinkedList[getNumeroTotalVertices() - 1];
				
		//Deve-se remover todas as arestas antes
		removerTodasArestas(vertice);
		
		//Remover o campo da lista de aresta referente ao vertice
		for (i = 0;i < listaAresta.length; i++)
		{
			if (i != localVertice)
			{
				listaArestaTemp[contTemp] = listaAresta[i];
				contTemp++;
			}
		}
		
		listaAresta = listaArestaTemp;
		
		//Remover o vértice		
		listaVertice.remove(vertice);
	}
	
	/**
	 * Remove uma aresta do grafo especificado pelo parâmetro.
	 *
	 * @param aresta a aresta a ser removida
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerVertice
	 * @see Grafo#removerGrafo
	 */
	public void removerAresta(Aresta aresta)
	{
		boolean 	removido;
		int 		i, j;
		Aresta 		arestaTemp;
		
		removido = false;
		for (i = 0; i < listaVertice.size(); i++)
		{
			for (j = 0 ; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				if (arestaTemp.equals(aresta))
				{
					listaAresta[i].remove(j);
					removido = true;	
				}
			}
		}

		if (removido)
			numeroTotalArestas--;
	}
	
	/**
	 * Remove todas as arestas que estão diretamente conectadas ao vértice 
	 * especificado pelo parâmetro.
	 *
	 * @param vertice o vértice na qual deseja remover suas respectivas arestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerVertice
	 * @see Grafo#removerGrafo
	 */
	public void removerTodasArestas(Vertice vertice)
	{
		int 	i,
				contMax,
				localVertice;
		Aresta	arestaTemp;
		
		localVertice = getLocalizacaoVertice(vertice);
		contMax = listaAresta[localVertice].size();
		
		for (i = 0; i < contMax; i++)
		{
			arestaTemp = (Aresta) listaAresta[localVertice].get(0);
			removerAresta(arestaTemp);
		}
	}
	
	/**
	 * Remove todos os componentes do grafo, ou seja, os vértice e as arestas.
	 * 
	 * @see Grafo#removerTodasArestas
	 * @see Grafo#removerAresta
	 * @see Grafo#removerVertice
	 */
	public void removerGrafo()
	{
		listaVertice.clear();
		listaAresta = null;
		numeroTotalArestas = 0;
	}
	
	/**
	 * Retorna a distância do menor caminho entre dois vértices quaisquer contidos
	 * no grafo. Caso não exista um caminho entre estes vértice, o valor 0 será 
	 * retornado. 
	 *
	 * A distância do menor caminho é calculada baseado na quantidade de arestas
	 * mínimas para alcançar o vértice de destino.
	 *
	 * @param origem o vértice de inicial do caminho
	 * @param destino o vértice final do caminho
	 * @return o número da distância do menor caminho
	 */
	public int distanciaMenorCaminho(Vertice origem, Vertice destino)
	{
		int			i, contador, custo[];
		LinkedList	lista;
		Vertice		verticesAdj[], verticeAtual, verticePosterior;
		boolean 	adjacente;
		
		lista = new LinkedList();
		contador = 1;
		
		desmarcarTodosVerticeVisitados();
		
		lista.add(origem);
		origem.setVisitado(true);

		//Insere todos os vertice em uma lista encadeada pelo percurso em largura
		verticeAtual = origem;
		while (!existeVertice(lista, destino))
		{
			verticesAdj = getVerticesAdjacentes(verticeAtual);
			
			if (verticesAdj != null)
			{
				for (i = 0; i < verticesAdj.length; i++)	
				{
					if (!verticesAdj[i].getVisitado())
					{
						verticesAdj[i].setVisitado(true);
						lista.add(verticesAdj[i]);
					}	
				}
			}
			else
				break;
			
			if (contador == lista.size())
				return 0;
				
			verticeAtual = (Vertice) lista.get(contador);
			contador++;
		}
		
		custo = new int[getNumeroTotalVertices()];
		for (i = 0; i < getNumeroTotalVertices(); i++)
			custo[i] = 1000000; //custo muito alto infinito
		
		contador = 0;
		verticeAtual = (Vertice) lista.get(contador);
		custo[getLocalizacaoVertice(verticeAtual)] = contador;
		contador++;
		i = 1;
		adjacente = false;
		while (lista.size() > i)
		{
			//Altera o vetor de custo
			verticePosterior = (Vertice) lista.get(i);
			
			if (adjacencia(verticePosterior, verticeAtual))
			{
				custo[getLocalizacaoVertice(verticePosterior)] = custo[getLocalizacaoVertice(verticeAtual)] + 1;
				adjacente = false;
			}
			else
			{
				verticeAtual = (Vertice) lista.get(contador);
				contador++;
				adjacente = true;
			}
			
			if (!adjacente)
				i++;
		}		
		
		return custo[getLocalizacaoVertice(destino)];
	}
	
	/**
	 * Retorna todos os vértice visitados durante um percurso em largura
	 * considerando o sentido da aresta. A forma como esses vértices estão dispostos 
	 * no vetor é relativo ao forma como eles são visitados, ou seja, o vértice
	 * localizado na posição 0 do vetor foi o primeiro vértice visitado enquanto que
	 * o vértice localizado na posição 1 foi o segundo vértice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localização na lista do vértice inicial do percurso
	 */
	public Vertice[] percursoLarguraOrientado(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice está correto
			
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de Vértices
			inicializarFila();
			
			//Visita o vértice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o vértice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir vértice na fila
			inserirFila(verticeTemp);
			
			while(!filaVazia())
			{
				//Remover da fila
				verticeTemp = removerFila();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
					
				for (i = 0; i < listaAresta[contadorVertice].size(); i++)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);

					if (!(arestaTemp.getVerticeDestino()).getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na fila
						inserirFila(verticeTemp);
					}
				}
			}
			
			destruirFila();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
						
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os vértice visitados durante um percurso em largura não
	 * considerando o sentido da aresta. A forma como esses vértices estão dispostos 
	 * no vetor é relativo ao forma como eles são visitados, ou seja, o vértice
	 * localizado na posição 0 do vetor foi o primeiro vértice visitado enquanto que
	 * o vértice localizado na posição 1 foi o segundo vértice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localização na lista do vértice inicial do percurso
	 */
	public Vertice[] percursoLargura(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice está correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de Vértices
			inicializarFila();
			
			//Visita o vértice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o vértice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir vértice na fila
			inserirFila(verticeTemp);
			
			while(!filaVazia())
			{
				//Remover da fila
				verticeTemp = removerFila();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
					
				for (i = 0; i < listaAresta[contadorVertice].size(); i++)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);

					if (!(arestaTemp.getVerticeOrigem()).getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeOrigem();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na fila
						inserirFila(verticeTemp);
					}
					
					if (!(arestaTemp.getVerticeDestino()).getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na fila
						inserirFila(verticeTemp);
					}
				}
			}
			
			destruirFila();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os vértice visitados durante um percurso em profundidade
	 * considerando o sentido da aresta. A forma como esses vértices estão dispostos 
	 * no vetor é relativo ao forma como eles são visitados, ou seja, o vértice
	 * localizado na posição 0 do vetor foi o primeiro vértice visitado enquanto que
	 * o vértice localizado na posição 1 foi o segundo vértice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localização na lista do vértice inicial do percurso
	 */
	public Vertice[] percursoProfundidadeOrientado(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice está correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de Vértices
			inicializarPilha();
			
			//Visita o vértice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o vértice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir vértice na pilha
			inserirPilha(verticeTemp);
			inserirPilha(verticeTemp);
			
			while(!pilhaVazia())
			{
				//Remover da pilha
				verticeTemp = removerPilha();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
				i = 0;
				
				while (listaAresta[contadorVertice].size() > i)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);
					i++;
					
					if (!arestaTemp.getVerticeDestino().getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
				}
			}
			
			destruirPilha();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}
	
	/**
	 * Retorna todos os vértice visitados durante um percurso em profuindidade não
	 * considerando o sentido da aresta. A forma como esses vértices estão dispostos 
	 * no vetor é relativo ao forma como eles são visitados, ou seja, o vértice
	 * localizado na posição 0 do vetor foi o primeiro vértice visitado enquanto que
	 * o vértice localizado na posição 1 foi o segundo vértice visitado a assim ocorre
	 * com todos as outros.
	 *
	 * @param indiceInicial a localização na lista do vértice inicial do percurso
	 */
	public Vertice[] percursoProfundidade(int indiceInicial)
	{
		int			i,
					contadorVertice;
		Vertice		verticeTemp,
					verticesRetorno[];
		Aresta		arestaTemp;
		LinkedList	listaRetorno;
		
		listaRetorno = new LinkedList();
		contadorVertice = indiceInicial;
		
		if ((indiceInicial >= 0) && (indiceInicial < listaVertice.size()))
		{
			//O valor do indice está correto
			desmarcarTodosVerticeVisitados();
			
			//Inicializa a fila de Vértices
			inicializarPilha();
			
			//Visita o vértice...
			verticeTemp = (Vertice) listaVertice.get(indiceInicial);
			
			//Marcar o vértice
			verticeTemp.setVisitado(true);
			listaRetorno.add(verticeTemp);
			
			//Inserir vértice na pilha
			inserirPilha(verticeTemp);
			inserirPilha(verticeTemp);
			
			while(!pilhaVazia())
			{
				//Remover da pilha
				verticeTemp = removerPilha();
				contadorVertice = getLocalizacaoVertice(verticeTemp);
				i = 0;
				
				while (listaAresta[contadorVertice].size() > i)
				{
					arestaTemp = (Aresta) listaAresta[contadorVertice].get(i);
					i++;
					
					if (!arestaTemp.getVerticeOrigem().getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeOrigem();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
					
					if (!arestaTemp.getVerticeDestino().getVisitado())
					{
						//Visita o vértice...
						verticeTemp = (Vertice) arestaTemp.getVerticeDestino();
						
						//Marcar o vértice
						verticeTemp.setVisitado(true);
						listaRetorno.add(verticeTemp);
						
						//Inserir vértice na pilha
						inserirPilha(verticeTemp);
						
						contadorVertice = getLocalizacaoVertice(verticeTemp);
						i = 0;
					}
				}
			}
			
			destruirPilha();
			verticesRetorno = new Vertice[listaRetorno.size()];
			for (i = 0; i < listaRetorno.size(); i++)
				verticesRetorno[i] = (Vertice) listaRetorno.get(i);
			
			return verticesRetorno;
		}
		
		return null;
	}

//Métodos privados da classe Grafo
	private boolean existeVertice(LinkedList lista, Vertice vertice)
	{
		int 		i;
		Vertice		verticeTemp;
		
		for (i = 0; i < lista.size(); i++)
		{
			verticeTemp = (Vertice) lista.get(i);
			
			if (verticeTemp.equals(vertice))
				return true;
		}
		
		return false;
	}
	
	private boolean adjacencia(Vertice origem, Vertice destino)
	{
		int 	i;
		Vertice	vertices[];
		
		vertices = getVerticesAdjacentes(origem);
		
		if (vertices == null)
			return false;
			
		for (i = 0; i < vertices.length; i++)
		{
			if (vertices[i].equals(destino))
				return true;	
		}
		
		return false;
	}
	
	private void criarListaAresta()
	{
		int 		i;
		LinkedList 	listaArestaTemp[];
		
		if (listaAresta == null)
		{
			//Criado pela primeira vez uma lista de arestas
			listaAresta = new LinkedList[listaVertice.size()];
			
			for (i = 0; i < listaAresta.length; i++)
				listaAresta[i] = new LinkedList();
		}
		else
		{
			if (listaVertice.size() > listaAresta.length)
			{
				//Foram criados novos vértices que não possuem uma lista de arestas
				listaArestaTemp = new LinkedList[listaVertice.size()];
				
				for (i = 0; i < listaArestaTemp.length; i++)
				{
					listaArestaTemp[i] = new LinkedList();
					
					if (i < listaAresta.length)
						listaArestaTemp[i] = listaAresta[i];
				}
				
				listaAresta = null; //Seta para o coletor de lixo limpar da memória
				
				listaAresta = listaArestaTemp;
			}
		}
	}
	
	private void desmarcarTodosVerticeVisitados()
	{
		//Ele percorre por todo o grafo desmarca os vertices visitados.
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestaTemp;
		
		for (i = 0; i < listaVertice.size(); i++)
		{
			verticeTemp = (Vertice) listaVertice.get(i);
			verticeTemp.setVisitado(false);
			
			for (j = 0; j < listaAresta[i].size(); j++)
			{
				arestaTemp = (Aresta) listaAresta[i].get(j);
				arestaTemp.setVisitado(false);
			}	
		}
	}
	
//Metodos da fila interna para o percurso em amplitude
	private void inicializarFila()
	{
		filaVertices = new LinkedList();
	}
	
	private void inserirFila(Vertice vertice)
	{
		filaVertices.addLast(vertice);
	}
	
	private Vertice removerFila()
	{
		return (Vertice) filaVertices.removeFirst();
	}
	
	private boolean filaVazia()
	{
		if (filaVertices.size() > 0)
			return false;
		
		return true;
	}
	
	private void destruirFila()
	{
		filaVertices = null;	
	}
	
//Métdos da pilha interna para o percurso em profundidade
	private void inicializarPilha()
	{
		pilhaVertices = new LinkedList();
	}
	
	private void inserirPilha(Vertice vertice)
	{
		pilhaVertices.addLast(vertice);
	}
	
	private Vertice removerPilha()
	{
		return (Vertice) pilhaVertices.removeLast();	
	}
	
	private boolean pilhaVazia()
	{
		if (pilhaVertices.size() > 0)
			return false;
		
		return true;	
	}
	
	private void destruirPilha()
	{
		pilhaVertices = null;
	}
}