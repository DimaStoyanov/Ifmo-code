package Mathlog.src;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.runtime.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 05.10.2016.
 * Project : Main
 * Start time : 13:26
 */

public class Main {
    public static void main(@Nullable String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        print(new ProofChecker(getAssumptions(br), getProofStrings(br), startTime).check());
        showTimeWork(startTime);
    }

    @NotNull
    private static HashMap<String, Integer> getAssumptions(@NotNull BufferedReader br) throws IOException {
        String header = br.readLine();
        BinaryParser parser = new BinaryParser();
        int i = 0;
        if (!header.contains("|-")) {
            throw new ParserException("Invalid header\n" + header);
        }
        HashMap<String, Integer> assumptions = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(header.substring(0, header.indexOf("|-")), ",");
        while (tokenizer.hasMoreTokens()) {
            ExpressionNode expr = parser.parse(tokenizer.nextToken().replaceAll("\\s", ""));
            assumptions.put(expr.toString(), i++);
        }
        return assumptions;
    }

    @NotNull
    private static ArrayList<String> getProofStrings(@NotNull BufferedReader br) throws IOException {
        ArrayList<String> proofStrings = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            proofStrings.add(line);
        }
        return proofStrings;
    }

    private static void print(@NotNull ArrayList<String> check) throws IOException {
        PrintWriter out = new PrintWriter(new File("output.txt"));
        check.forEach(out::println);
        out.close();
    }

    private static void showTimeWork(long startTime) {
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + " ms");
    }

}
