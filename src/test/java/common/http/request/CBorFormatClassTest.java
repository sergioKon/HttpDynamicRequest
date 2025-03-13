package common.http.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CBorFormatClassTest {
    @Test
    public void convertToCBor() throws IOException {

            ObjectMapper objectMapper = new ObjectMapper(new CBORFactory());

            // Original data
            MyData data = new MyData();
            data.setName("John");
            data.setAge(30);
            data.setStudent(false);
            // Encode to CBOR
            byte[] cborData = objectMapper.writeValueAsBytes(data);
            System.out.println("Encoded CBOR: " + bytesToHex(cborData));

            // Decode from CBOR
            MyData decodedData = objectMapper.readValue(cborData, MyData.class);
            System.out.println("Decoded Data: " + decodedData);
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        }
}

     @Getter @Setter
     class MyData {
        private String name;
        private  int age;
        private  boolean isStudent;

        public MyData() {

        }


        @Override
        public String toString() {
            return "MyData{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", isStudent=" + isStudent +
                    '}';
        }
    }
