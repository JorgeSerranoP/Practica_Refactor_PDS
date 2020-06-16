package Transport4Future.TokenManagement.Store;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokensStore {

	private List<Token> tokensList;

	private static TokensStore token;

	private static final String STOREPATH = System.getProperty("user.dir") + "/Store/tokenStore.json";

	private TokensStore() {
		this.Load();
	}

	public static TokensStore getSingleton() {
		if (token == null) {
			token = new TokensStore();
		}
		/*
		 * else { System.out.println("TokensStore ya creado"); }
		 */

		return token;
	}

	@Override
	public TokensStore clone() {
		try {
			throw new CloneNotSupportedException();
		} catch (CloneNotSupportedException ex) {
			// System.out.println("TokensStore no puede crearse");
		}
		return null;
	}

	private void Load() {
		try {
			JsonReader reader = new JsonReader(new FileReader(STOREPATH));
			Gson gson = new Gson();
			Token[] myArray = gson.fromJson(reader, Token[].class);
			this.tokensList = new ArrayList<Token>();
			for (Token token : myArray) {
				this.tokensList.add(token);
			}
		} catch (Exception ex) {
			this.tokensList = new ArrayList<Token>();
		}
	}

	public void Add(Token newToken) throws TokenManagementException {
		if (Find(newToken.getTokenValue()) == null) {
			tokensList.add(newToken);
			this.Save();
		}
	}

	private void Save() throws TokenManagementException {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonString = gson.toJson(this.tokensList);
		FileWriter fileWriter;
		/* 
		 * System.out.println(jsonString);
		 * Hemos realizado esta impresión por pantalla para comprobar el resultado de jsonString, 
		 * ya que hemos observado que en el tokenStore.json, no se guarda correctamente. 
		 * Sin embargo, en jsonString sí, por lo que creemos que puede tratarse de un error en las
		 * clases internas de java o algo relacionado con gson, de cuyo funcionamiento no tenemos un 
		 * amplio conocimiento.
		 * 
		 */
		try {
			fileWriter = new FileWriter(STOREPATH);
			fileWriter.write(jsonString);
			fileWriter.close();
		} catch (IOException e) {
			throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
		}
	}

	public Token Find(String tokenToFind) {
		Token result = null;
		for (Token token : this.tokensList) {
			if (token.getTokenValue().equals(tokenToFind)) {
				return token;
			}
		}
		return result;
	}
}
