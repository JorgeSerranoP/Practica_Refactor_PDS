package Transport4Future.TokenManagement.IO;

import javax.json.JsonObject;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokenJSONParser extends JSONParser implements IJSONParser {

	@Override
	public Object parseJSON(String InputFile) throws TokenManagementException {
		JsonObject jsonLicense = (JsonObject) super.parseJSON(InputFile);
		Token myToken = createTokenObject(jsonLicense);
		return myToken;
	}

	private Token createTokenObject(JsonObject jsonLicense) throws TokenManagementException {
		Token myToken;
		String tokenRquest = "";
		String email = "";
		String date = "";

		try {
			tokenRquest = jsonLicense.getString("Token Request");
			email = jsonLicense.getString("Notification e-mail");
			date = jsonLicense.getString("Request Date");
		} catch (Exception pe) {
			throw new TokenManagementException("Error: invalid input data in JSON structure.");
		}

		myToken = new Token(tokenRquest, date, email);
		return myToken;
	}
}
