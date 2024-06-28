package ir.sharif.messages;

import ir.sharif.enums.ResultCode;

public class ServerMessage {
	private final ResultCode statusCode;
	private final String additionalInfo;

	public ServerMessage (ResultCode statusCode, String info) {
		this.statusCode = statusCode;
		this.additionalInfo = info;
	}

	public ResultCode getStatusCode() {
		return statusCode;
	}

	public String getAdditionalInfo () {
		return this.additionalInfo;
	}
}