package com.vctapps.starwarscharacters.persistence.map;

/**
 * Interface criada com toda base de métodos para mapeamento em relação a transações
 * com o Banco de dados
 */

public interface Map<T> extends MapToObject<T>, MapToRow<T> {
}
