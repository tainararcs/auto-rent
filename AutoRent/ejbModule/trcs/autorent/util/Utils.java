package trcs.autorent.util;

/**
 * Classe utilitária com funções de apoio, como algoritmos de criptografia e hash.
 */
public class Utils {

	/**
	 * Gera o hash de uma palavra-passe utilizando o método hash code básico.
	 * 
	 * @param password A palavra-passe em texto limpo
	 * @return O hash da palavra-passe em formato de String
	 */
	public static String getHashPassword(String password) {
		return String.valueOf(password.hashCode());
	}
}