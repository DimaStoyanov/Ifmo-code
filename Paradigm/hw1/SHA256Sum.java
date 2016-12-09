package Paradigm.hw1;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Created by Blackbird on 04.03.2016.
 * Project : Paradigm.hw1.SHA256Sum
 * Start time : 17:05
 */

public class SHA256Sum {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            DigestInputStream sha = new DigestInputStream(System.in, MessageDigest.getInstance("SHA-256"));
            byte[] buf = new byte[128];
            while (sha.read(buf) > -1) ;
            System.out.println(DatatypeConverter.printHexBinary(sha.getMessageDigest().digest()) + " *-");
            sha.close();
        } else {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            String fileName;
            while ((fileName = in.readLine()) != null) {
                System.out.print(DatatypeConverter.printHexBinary(sha.digest(Files.readAllBytes(Paths.get(fileName))))
                        + " *" + Paths.get(fileName).getFileName().toString());
            }
            in.close();
        }

    }
}
