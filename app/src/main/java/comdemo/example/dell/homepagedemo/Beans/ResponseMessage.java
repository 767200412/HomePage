package comdemo.example.dell.homepagedemo.Beans;


public class ResponseMessage {


        private String Id;
        private String AppId;
        private String UserName;
        private String NickName;
        private String RealName;
        private boolean Gender;
        private String PortraitUrl;
        private String VisualizationImgUrl;
        private String Email;
        private String EmailImgEncodedData;
        private boolean EmailConfirmed;
        private String PhoneRegionCode;
        private String PhoneNumber;
        private String PhoneNumberImgEncodedData;
        private boolean PhoneNumberConfirmed;
        private String Birthday;
        private String LastLoginDeviceCode;
        private String UseLang;
        private String ResidentCountry;
        private String ResidentProvince;
        private String ResidentCity;
        private String ResidentArea;
        private String HometownCountry;
        private String HometownProvince;
        private String HometownCity;
        private String HometownArea;
        private String Address;

        private String Message;
        private String Code;
        private String  FailedCount;
        private String  CanFailedCount;
        private String LockoutEndDate;

        public void setId(String Id) {
            this.Id = Id;
        }
        public String getId() {
            return Id;
        }

        public String getAppId() {
        return AppId;
    }

        public void setAppId(String appId) {
        AppId = appId;
    }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }
        public String getUserName() {
            return UserName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }
        public String getNickName() {
            return NickName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }
        public String getRealName() {
            return RealName;
        }

        public void setGender(boolean Gender) {
            this.Gender = Gender;
        }
        public boolean getGender() {
            return Gender;
        }

        public void setPortraitUrl(String PortraitUrl) {
            this.PortraitUrl = PortraitUrl;
        }
        public String getPortraitUrl() {
            return PortraitUrl;
        }

        public void setVisualizationImgUrl(String VisualizationImgUrl) {
            this.VisualizationImgUrl = VisualizationImgUrl;
        }
        public String getVisualizationImgUrl() {
            return VisualizationImgUrl;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }
        public String getEmail() {
            return Email;
        }

        public void setEmailImgEncodedData(String EmailImgEncodedData) {
            this.EmailImgEncodedData = EmailImgEncodedData;
        }
        public String getEmailImgEncodedData() {
            return EmailImgEncodedData;
        }

        public void setEmailConfirmed(boolean EmailConfirmed) {
            this.EmailConfirmed = EmailConfirmed;
        }
        public boolean getEmailConfirmed() {
            return EmailConfirmed;
        }

        public void setPhoneRegionCode(String PhoneRegionCode) {
            this.PhoneRegionCode = PhoneRegionCode;
        }
        public String getPhoneRegionCode() {
            return PhoneRegionCode;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }
        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumberImgEncodedData(String PhoneNumberImgEncodedData) {
            this.PhoneNumberImgEncodedData = PhoneNumberImgEncodedData;
        }
        public String getPhoneNumberImgEncodedData() {
            return PhoneNumberImgEncodedData;
        }

        public void setPhoneNumberConfirmed(boolean PhoneNumberConfirmed) {
            this.PhoneNumberConfirmed = PhoneNumberConfirmed;
        }
        public boolean getPhoneNumberConfirmed() {
            return PhoneNumberConfirmed;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }
        public String getBirthday() {
            return Birthday;
        }

        public void setLastLoginDeviceCode(String LastLoginDeviceCode) {
            this.LastLoginDeviceCode = LastLoginDeviceCode;
        }
        public String getLastLoginDeviceCode() {
            return LastLoginDeviceCode;
        }

        public void setUseLang(String UseLang) {
            this.UseLang = UseLang;
        }
        public String getUseLang() {
            return UseLang;
        }

        public void setResidentCountry(String ResidentCountry) {
            this.ResidentCountry = ResidentCountry;
        }
        public String getResidentCountry() {
            return ResidentCountry;
        }

        public void setResidentProvince(String ResidentProvince) {
            this.ResidentProvince = ResidentProvince;
        }
        public String getResidentProvince() {
            return ResidentProvince;
        }

        public void setResidentCity(String ResidentCity) {
            this.ResidentCity = ResidentCity;
        }
        public String getResidentCity() {
            return ResidentCity;
        }

        public void setResidentArea(String ResidentArea) {
            this.ResidentArea = ResidentArea;
        }
        public String getResidentArea() {
            return ResidentArea;
        }

        public void setHometownCountry(String HometownCountry) {
            this.HometownCountry = HometownCountry;
        }
        public String getHometownCountry() {
            return HometownCountry;
        }

        public void setHometownProvince(String HometownProvince) {
            this.HometownProvince = HometownProvince;
        }
        public String getHometownProvince() {
            return HometownProvince;
        }

        public void setHometownCity(String HometownCity) {
            this.HometownCity = HometownCity;
        }
        public String getHometownCity() {
            return HometownCity;
        }

        public void setHometownArea(String HometownArea) {
            this.HometownArea = HometownArea;
        }
        public String getHometownArea() {
            return HometownArea;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }
        public String getAddress() {
            return Address;
        }

    public boolean isGender() {
        return Gender;
    }

    public boolean isEmailConfirmed() {
        return EmailConfirmed;
    }

    public boolean isPhoneNumberConfirmed() {
        return PhoneNumberConfirmed;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getFailedCount() {
        return FailedCount;
    }

    public void setFailedCount(String failedCount) {
        FailedCount = failedCount;
    }

    public String getCanFailedCount() {
        return CanFailedCount;
    }

    public void setCanFailedCount(String canFailedCount) {
        CanFailedCount = canFailedCount;
    }

    public String getLockoutEndDate() {
        return LockoutEndDate;
    }

    public void setLockoutEndDate(String lockoutEndDate) {
        LockoutEndDate = lockoutEndDate;
    }
}
