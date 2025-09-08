package quru.qa.files.data;

public enum File {
    PDF ("test.pdf", "pdf"),
    XLSX ("Generated.xlsx", "xlsx"),
    CSV ("LoginAndPassword.csv", "csv");

    public String fileName;
    public String fileType;

    File (String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
