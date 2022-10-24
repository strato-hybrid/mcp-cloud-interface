package kr.strato.cloudinterface.common.config;

public class CommonEnums {
    public enum RequestType {
       SDK("SDK"),
        API("API");

       String requestType;

       RequestType(String value) {
           this.requestType = value;
       }

       public String getValue() {
           return requestType;
       }
    }

    public enum CloudType {
        AWS("AWS"),
        AZURE("AZURE"),
        GOOGLE("GOOGLE"),
        NAVER("NAVER");

        String cloudType;

        CloudType(String value) {
            this.cloudType = value;
        }

        public String getValue() {
            return cloudType;
        }
    }
}
