package comdemo.example.dell.homepagedemo.Beans;

public class User {
    private String PhoneRegionCode;
    private String Account;
    private String Password;

    public String getPhoneRegionCode() {
        return PhoneRegionCode;
    }

    public void setPhoneRegionCode(String phoneRegionCode) {
        PhoneRegionCode = phoneRegionCode;
    }


    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "Account='" + Account + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
