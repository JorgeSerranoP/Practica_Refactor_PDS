package Transport4Future.TokenManagement.IO;

import javax.json.JsonObject;

import Transport4Future.TokenManagement.Data.TokenRequest;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokenRequestJSONParser extends JSONParser implements IJSONParser {

	@Override
	public Object parseJSON(String InputFile) throws TokenManagementException {
		JsonObject jsonLicense = (JsonObject) super.parseJSON(InputFile);
		TokenRequest myToken = createTokenRequestObject(jsonLicense);
		return myToken;
	}

	private TokenRequest createTokenRequestObject(JsonObject jsonLicense) throws TokenManagementException {
		TokenRequest req;
		String deviceName = "";
		String typeOfDevice = "";
		String driverVersion = "";
		String supportEMail = "";
		String serialNumber = "";
		String macAddress = "";

		try {
			deviceName = jsonLicense.getString("Device Name");
			typeOfDevice = jsonLicense.getString("Type of Device");
			driverVersion = jsonLicense.getString("Driver Version");
			supportEMail = jsonLicense.getString("Support e-mail");
			serialNumber = jsonLicense.getString("Serial Number");
			macAddress = jsonLicense.getString("MAC Address");
		} catch (Exception pe) {
			throw new TokenManagementException("Error: invalid input data in JSON structure.");
		}

		req = new TokenRequest(deviceName, typeOfDevice, driverVersion, supportEMail, serialNumber, macAddress);
		return req;
	}
}
