package Transport4Future.TokenManagement.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public abstract class GenericHasher implements IHash {

	protected String algorithm;
	protected String format;

	public GenericHasher() {
		super();
	}

	public String Hash(String input) throws TokenManagementException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new TokenManagementException("Error: no such hashing algorithm.");
		}

		md.update(input.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();

		String hash = String.format(format, new BigInteger(1, digest));
		return hash;
	}
}