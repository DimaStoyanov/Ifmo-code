package Paradigm.hw1;

/**
 * Created by Blackbird on 14.02.2016.
 * Project : Paradigm.hw1.CalcSHA256
 * Start time : 12:25
 **/

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class CalcSHA256 {
    public static void main(String[] args) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String fileName;
        while ((fileName = in.readLine()) != null) {
            byte[] messageDigest = Files.readAllBytes(Paths.get(fileName));
            String result = DatatypeConverter.printHexBinary(md5.digest(messageDigest));
            System.out.println(result);
        }
    }
}
