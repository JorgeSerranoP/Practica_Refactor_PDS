package Transport4Future.TokenManagement.Store;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Data.TokenRequest;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokensRequestStore {

	private static TokensRequestStore tokenRequest;

	private static final String STOREPATH = System.getProperty("user.dir") + "/Store/tokenRequestsStore.json";

	private TokensRequestStore() {
	}

	public static TokensRequestStore getSingleton() {
		if (tokenRequest == null) {
			tokenRequest = new TokensRequestStore();
		}
		/*
		 * else { System.out.println("TokensRequestStore ya creado"); }
		 */

		return tokenRequest;
	}

	@Override
	public TokensRequestStore clone() {
		try {
			throw new CloneNotSupportedException();
		} catch (CloneNotSupportedException ex) {
			// System.out.println("TokensRequestStore no puede crearse");
		}
		return null;
	}

	public void storeJSON(TokenRequest req, String hash) throws TokenManagementException {
		HashMap<String, TokenRequest> clonedMap = this.loadTokenRequestRepository();
		if (clonedMap == null) {
			clonedMap = new HashMap<String, TokenRequest>();
			clonedMap.put(hash, req);
		} else if (!clonedMap.containsKey(hash)) {
			clonedMap.put(hash, req);
		}
		Gson gson = new Gson();
		String jsonString = gson.toJson(clonedMap);
		FileWriter fileWriter;

		/*
		 * En el fichero RequestStore.json inicialmente se almacenaba solo el campo y su
		 * valor correspondiente, pero al hacer el refactor de la clase TokenRequest,
		 * como se nos ha indicado en los videos de ayuda, aparecen campos nuevos como
		 * pattern y errorMessage que no son relevantes.
		 */

		try {
			fileWriter = new FileWriter(STOREPATH);
			fileWriter.write(jsonString);
			fileWriter.close();
		} catch (IOException e) {
			throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
		}
	}

	private HashMap<String, TokenRequest> loadTokenRequestRepository() {
		// Generar un HashMap para guardar los objetos
		// Tengo que cargar el almacen de tokens request en memoria y añadir el nuevo si
		// no existe
		HashMap<String, TokenRequest> clonedMap;
		try {
			Gson gson = new Gson();
			String jsonString;

			Object object = gson.fromJson(new FileReader(STOREPATH), Object.class);
			jsonString = gson.toJson(object);
			Type type = new TypeToken<HashMap<String, TokenRequest>>() {
			}.getType();
			clonedMap = gson.fromJson(jsonString, type);
		} catch (Exception e) {
			clonedMap = null;
		}

		return clonedMap;
	}

	public void checkTokenRequestInTokensStore(Token TokenToVerify) throws TokenManagementException {
		Gson gson = new Gson();
		String jsonString;
		HashMap<String, TokenRequest> clonedMap = null;

		// Cargar el almacen de tokens request en memoria y añadir el nuevo si no existe
		try {
			Object object = gson.fromJson(new FileReader(STOREPATH), Object.class);
			jsonString = gson.toJson(object);
			Type type = new TypeToken<HashMap<String, TokenRequest>>() {
			}.getType();
			clonedMap = gson.fromJson(jsonString, type);
		} catch (Exception e) {
			throw new TokenManagementException("Error: unable to recover Token Requests Store.");
		}
		if (clonedMap == null) {
			throw new TokenManagementException("Error: Token Request Not Previously Registered");
		} else if (!clonedMap.containsKey(TokenToVerify.getDevice())) {
			throw new TokenManagementException("Error: Token Request Not Previously Registered");
		}
	}
}
