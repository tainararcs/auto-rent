package trcs.autorent.enums;

/**
 * Enumeração que define as categorias comerciais disponíveis para os veículos da frota.
 */
public enum VehicleCategory {
	
	/** Categoria de automóveis de passeio. */
	CAR("Carro"),
	
	/** Categoria de motocicletas. */
    MOTORCYCLE("Moto"),
    
    /** Categoria de veículos utilitários ou de carga leve. */
    UTILITY("Utilitário");
    
	/** Descrição textual legível da categoria. */
	private final String category;

	/**
	 * Construtor do enum VehicleCategory.
	 * 
	 * @param category A descrição textual da categoria
	 */
	private VehicleCategory(String category) {
		this.category = category;
	}

	/**
	 * Retorna a descrição textual legível da categoria.
	 * 
	 * @return A categoria em formato de String
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Retorna um array contendo todas as descrições textuais das categorias disponíveis.
	 * Útil para preenchimento de componentes visuais, como menus de seleção (selects).
	 * 
	 * @return Array de Strings com os nomes das categorias
	 */
	public static String[] valuesString() {
		String[] valuesArray = new String[values().length];
		
		short index = 0;
		for (VehicleCategory category : values()) 
			valuesArray[index++] = category.getCategory();
		
		return valuesArray;
	}
}