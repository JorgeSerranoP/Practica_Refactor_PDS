package Transport4Future.TokenManagement.IO;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface IJSONParser {
	public Object parseJSON(String InputFile) throws TokenManagementException;
}
