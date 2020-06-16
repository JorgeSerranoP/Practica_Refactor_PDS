package Transport4Future.TokenManagement.Data;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class DriverVersion extends Attribute {
	DriverVersion(String Value) throws TokenManagementException {
		this.pattern = "([a-zA-Z0-9]{1}[A-Za-z0-9\\.]{0,24})";
		this.errorMessage = "Error: invalid String length for driver version.";
		this.value = validate(Value);
	}
}
