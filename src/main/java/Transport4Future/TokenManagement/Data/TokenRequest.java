package Transport4Future.TokenManagement.Data;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.Utils.MD5Hasher;

public class TokenRequest {

	private DeviceName deviceName;
	private TypeOfDevice typeOfDevice;
	private DriverVersion driverVersion;
	private SupportEmail supportEMail;
	private SerialNumber serialNumber;
	private MacAddress macAddress;
	private String hash;

	public TokenRequest(String deviceName, String typeOfDevice, String driverVersion, String supportEMail,
			String serialNumber, String macAddress) throws TokenManagementException {
		this.deviceName = new DeviceName(deviceName);
		this.typeOfDevice = new TypeOfDevice(typeOfDevice);
		this.driverVersion = new DriverVersion(driverVersion);
		this.supportEMail = new SupportEmail(supportEMail);
		this.serialNumber = new SerialNumber(serialNumber);
		this.macAddress = new MacAddress(macAddress);
		this.hash = generateHash();
	}

	public String getDeviceName() {
		return deviceName.getValue();
	}

	public String getTypeOfDevice() {
		return typeOfDevice.getValue();
	}

	public String getDriverVersion() {
		return driverVersion.getValue();
	}

	public String getSupportEMail() {
		return supportEMail.getValue();
	}

	public String getSerialNumber() {
		return serialNumber.getValue();
	}

	public String getMacAddress() {
		return macAddress.getValue();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	private String generateHash() throws TokenManagementException {
		MD5Hasher myHasher = new MD5Hasher();
		String hash = myHasher.Hash(this.toString());
		return hash;
	}

	@Override
	public String toString() {
		return "TokenRequest [\\n\\Device Name=" + getDeviceName() + ",\n\t\\Type of Device=" + getTypeOfDevice()
				+ ",\n\t\\Driver Version=" + getDriverVersion() + ",\n\t\\Support e-Mail=" + getSupportEMail()
				+ ",\n\t\\Serial Number=" + getSerialNumber() + ",\n\t\\MAC Address=" + getMacAddress() + "\n]";
	}
}
