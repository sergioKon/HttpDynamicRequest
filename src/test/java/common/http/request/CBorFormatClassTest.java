package common.http.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CBorFormatClassTest {
    @Test
    public void convertToCBor() throws IOException {

            ObjectMapper objectMapper = new ObjectMapper(new CBORFactory());

            // Original data
            MyData data = new MyData();
            data.age=30;
            data.is_student=false;
            data.name= "John";
           // MyData data = new MyData("John", 30, false);

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

     class MyData {
        public String name;
        public int age;
        public boolean is_student;


        @Override
        public String toString() {
            return "MyData{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", is_student=" + is_student +
                    '}';
        }
    }
