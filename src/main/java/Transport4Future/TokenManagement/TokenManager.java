package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Data.TokenRequest;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.IO.TokenJSONParser;
import Transport4Future.TokenManagement.IO.TokenRequestJSONParser;
import Transport4Future.TokenManagement.Store.TokensRequestStore;
import Transport4Future.TokenManagement.Store.TokensStore;

public class TokenManager implements ITokenManagement {

	private static TokenManager manager;

	private TokenManager() {
	}

	public static TokenManager getSingleton() {
		if (manager == null) {
			manager = new TokenManager();
		}
		/*
		 * else { System.out.println("TokenManager ya creado"); }
		 */
		return manager;
	}

	@Override
	public TokenManager clone() {
		try {
			throw new CloneNotSupportedException();
		} catch (CloneNotSupportedException ex) {
			// System.out.println("Token Manager no puede crearse");
		}
		return null;
	}

	public String TokenRequestGeneration(String InputFile) throws TokenManagementException {
		TokenRequest req = null;

		TokenRequestJSONParser myParser = new TokenRequestJSONParser();
		req = (TokenRequest) myParser.parseJSON(InputFile);

		TokensRequestStore myStore = TokensRequestStore.getSingleton();
		myStore.storeJSON(req, req.getHash());

		return req.getHash();
	}

	public String RequestToken(String InputFile) throws TokenManagementException {
		Token myToken = null;

		TokenJSONParser myParser = new TokenJSONParser();
		myToken = (Token) myParser.parseJSON(InputFile);

		TokensRequestStore myStore = TokensRequestStore.getSingleton();
		myStore.checkTokenRequestInTokensStore(myToken);

		return myToken.getTokenValue();
	}

	public boolean VerifyToken(String Token) throws TokenManagementException {
		boolean result = false;

		TokensStore myStore = TokensStore.getSingleton();

		Token tokenFound = myStore.Find(Token);

		if (tokenFound != null) {
			result = tokenFound.isValid();
		}
		return result;
	}
}