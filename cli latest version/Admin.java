

public class Admin {
    private String adpassword;
    public Admin(String password) { this.adpassword = password; }
    public void setPassword(String password) { this.adpassword = password; }
    public boolean checkAuthentication(String input) {
        return input != null && input.equals(adpassword);
    }
}
