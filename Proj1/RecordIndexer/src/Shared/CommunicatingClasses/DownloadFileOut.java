package Shared.CommunicatingClasses;

public class DownloadFileOut {

	private byte[] fileBytes;
	
	public DownloadFileOut(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
	
}
