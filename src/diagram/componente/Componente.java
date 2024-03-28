package diagram.componente;

/**
 * Está é a super classe dos componentes existentes no editor de diagramas. 
 * O Componente está acima da hierarquia de classes e portanto é a classe abstrata 
 * básica utilizada no contexto do pacote diagram para permitir a criação de 
 * novos modelos de componentes. Tais componentes do editor são o vértice 
 * e a aresta.
 *
 * A classe Rótulo não é um componente dentro da modelagem do editor e sim, 
 * um objeto que faz parte de um componente, ou seja, uma agregação. Então 
 * uma classe supertipo de rótulo não existe no pacote.
 *
 * Na criação de um novo modelo de componente, a classe <B>Componente</B> deverá 
 * necessariamente ser herdada. Ela também possui um atributo código disponível 
 * para ser utilizado junto ao banco de dados. Um rótulo também está associado
 * a está classe e consequentemente todos suas subclasses.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Aresta
 * @see Vertice
 */
public abstract class Componente extends Object
{
	/**
	 * O valor padrão do código inicial de um componente.
	 */
	public static int 		CODIGOINICIAL = 0;
	
	private int				codigo;
	
	private boolean			componenteSelecionado,
							componenteVisitado;
	
	private Rotulo			rotuloComponente;
	
	/**
	 * Instância uma novo componente.
	 *
	 * Como a classe Componente é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 */	
	public Componente()
	{
		setCodigo(CODIGOINICIAL);
		setVisitado(false);
		setSelecionado(false);
		
		rotuloComponente = new Rotulo();
	}

	/**
	 * Instância uma novo componente com um código especificado pelo parâmetro.
	 *
	 * Como a classe Componente é abstrata, as aplicações não podem chamar
	 * está classe diretamente.
	 *
	 * @param codigo o código do componente
	 */	
	public Componente(int codigo)
	{
		setCodigo(codigo);
		setSelecionado(false);
		setVisitado(false);
	}
	
	//Metodos Get
	
	/**
	 * Retorna o valor do código atual do componente.
	 *
	 * @return o valor do código
	 */
	public int getCodigo()
	{
		return codigo;	
	}
	
	/**
	 * Retorna se o componente do editor está selecionado. Caso o retorno seja 
	 * verdadeiro o componente está seleiconado e falso se não estiver selecionado.
	 *
	 * @return se o componente está ou não selecionado
	 */
	public boolean getSelecionado()
	{
		return componenteSelecionado;	
	}

	/**
	 * Retorna se o componente já foi marcado como avaliado pelos algoritmos de 
	 * grafos. Algoritmos, como o menor caminho e o percurso em largura e profundidade
	 * utilizam este atributo para facilitar na implementalção do código.
	 *
	 * @return se o componente já foi visitado
	 */
	public boolean getVisitado()
	{
		return componenteVisitado;	
	}
	
	/**
	 * Retorna o rótulo associado ao componente.
	 *
	 * @return o objeto rótulo
	 */
	public Rotulo getRotulo()
	{
		return rotuloComponente;
	}
	
	//Metodos Set
	
	/**
	 * Altera o código atual do componente.
	 *
	 * @param codigo o valor no novo código
	 */
	public void setCodigo(int codigo)
	{
		if (codigo > 0)
			this.codigo = codigo;
		else
			this.codigo = CODIGOINICIAL;
	}
	
	/**
	 * Altera se o componente está selecionado ou não. Um boleano verdadeiro
	 * deverá ser passado caso o componente esteja selecionado e falso caso
	 * contrário.
	 *
	 * @param componenteSelecionado se o componente está ou não selecionado
	 * @see Componente#selecionarComponente
	 * @see Componente#desmarcarComponente
	 */
	public void setSelecionado(boolean componenteSelecionado)
	{
		this.componenteSelecionado = componenteSelecionado;	
	}
	
	/**
	 * Seleciona o componente
	 *
	 * @see Componente#setSelecionado
	 * @see Componente#desmarcarComponente
	 */
	public void selecionarComponente()
	{
		componenteSelecionado = true;
	}
	
	/**
	 * Desmarca o componente.
	 *
	 * @see Componente#setSelecionado
	 * @see Componente#selecionarComponente
	 */
	public void desmarcarComponente()
	{
		componenteSelecionado = false;
	}
	
	/**
	 * Marca o componente como visitado. Algoritmos, como o menor caminho e o 
	 * percurso em largura e profundidade utilizam este atributo para facilitar
	 * na implementalção do código.
	 *
	 * @param componenteVisitado se o componente foi visitado ou não
	 */
	public void setVisitado(boolean componenteVisitado)
	{
		this.componenteVisitado = componenteVisitado;	
	}
	
	/**
	 * Altera o rótulo do componente.
	 *
	 * @param rotuloComponente o novo rótulo
	 */
	public void setRotulo(Rotulo rotuloComponente)
	{
		if (rotuloComponente != null)
			this.rotuloComponente = rotuloComponente;
	}
}