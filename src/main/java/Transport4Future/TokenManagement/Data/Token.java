package Transport4Future.TokenManagement.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.Store.TokensStore;
import Transport4Future.TokenManagement.Utils.SHA256Hasher;

public class Token {

	private String alg;
	private String typ;
	private Device device;
	private RequestDate requestDate;
	private NotificationEmail notificationEmail;
	private long iat;
	private long exp;
	private String signature;
	private String tokenValue;
	private String hash;

	public Token(String Device, String RequestDate, String NotificationEmail) throws TokenManagementException {
		this.alg = "HS256";
		this.typ = "PDS";
		this.device = new Device(Device);
		this.requestDate = new RequestDate(RequestDate);
		this.notificationEmail = new NotificationEmail(NotificationEmail);
		// this.iat = System.currentTimeMillis();
		// SOLO PARA PRUEBAS
		this.iat = 1584523340892l;
		if ((this.getDevice().startsWith("5"))) {
			this.exp = this.iat + 604800000l;
		} else {
			this.exp = this.iat + 65604800000l;
		}
		this.signature = null;
		this.tokenValue = null;
		this.hash = generateHash();
		this.encodeString();
		this.addInTokenStore();
	}

	private String generateHash() throws TokenManagementException {
		SHA256Hasher myHasher = new SHA256Hasher();
		String signature = myHasher.Hash(this.getHeader() + this.getPayload());
		this.setSignature(signature);
		return signature;
	}

	private void encodeString() {
		String stringToEncode = this.getHeader() + this.getPayload() + this.getSignature();
		String encodedString = Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
		this.setTokenValue(encodedString);
	}

	private void addInTokenStore() throws TokenManagementException {
		TokensStore myStore = TokensStore.getSingleton();
		myStore.Add(this);
	}

	public String getDevice() {
		return device.getValue();
	}

	public String getRequestDate() {
		return requestDate.getValue();
	}

	public String getNotificationEmail() {
		return notificationEmail.getValue();
	}

	private String getHeader() {
		return "Alg=" + this.alg + "\\n Typ=" + this.typ + "\\n";
	}

	private String getPayload() {
		Date iatDate = new Date(this.iat);
		Date expDate = new Date(this.exp);

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		return "Dev=" + this.getDevice() + "\\n iat=" + df.format(iatDate) + "\\n exp=" + df.format(expDate);
	}

	public String getSignature() {
		return this.signature;
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public String getHash() {
		return hash;
	}

	public void setSignature(String value) {
		this.signature = value;
	}

	public void setTokenValue(String value) {
		this.tokenValue = value;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	private boolean isGranted() {
		if (this.iat < System.currentTimeMillis()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isExpired() {
		if (this.exp > System.currentTimeMillis()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isValid() {
		if ((!isExpired()) && (isGranted())) {
			return true;
		} else {
			return false;
		}
	}

}
