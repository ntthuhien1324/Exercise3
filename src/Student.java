import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ho, ten, ngaySinh, email;
	
	Student(){
		
	}
	
	public Student(String string){
		String[] subString = string.split(",");
		this.ho = subString[0];
		this.ten = subString[1];
		this.ngaySinh = subString[2];
		this.email = subString[3];
		
	}

	public Student(String ho, String ten, String ngaySinh, String email) {
		super();
		this.ho = ho;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.email = email;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [ho=" + ho + ", ten=" + ten + ", ngaySinh=" + ngaySinh + ", email=" + email + "]";
	}
	
	
}
