package Transport4Future.TokenManagement.Data;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class MacAddress extends Attribute {
	MacAddress(String Value) throws TokenManagementException {
		this.pattern = "([a-fA-F0-9]{2}[:-]){5}[a-fA-F0-9]{2}$";
		this.errorMessage = "Error: invalid MAC Address data in JSON structure.";
		this.value = validate(Value);
	}
}
