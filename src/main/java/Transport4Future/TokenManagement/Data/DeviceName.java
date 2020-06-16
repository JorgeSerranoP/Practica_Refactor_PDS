package Transport4Future.TokenManagement.Data;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class DeviceName extends Attribute {
	DeviceName(String Value) throws TokenManagementException {
		this.pattern = "([A-Za-z0-9\\s]{1,20})";
		this.errorMessage = "Error: invalid String length for device name.";
		this.value = validate(Value);
	}
}
