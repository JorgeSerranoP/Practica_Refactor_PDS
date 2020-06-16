package Transport4Future.TokenManagement.Utils;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface IHash {
	public String Hash(String text) throws TokenManagementException;
}
