package Mathlog.src;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Blackbird on 05.10.2016.
 * Project : ProofChecker
 * Start time : 2:15
 */

class ProofChecker {
    private final int NOT_FOUND = -1;
    private final int ASC_A = (int) 'A';
    private final int MAX_VARS = 3;
    private HashMap<ExpressionNode, Pair<Integer, Integer>> mp;
    private HashMap<ExpressionNode, Integer> proved;
    private HashMap<String, Integer> assumptions;
    private ArrayList<ExpressionNode> axioms;
    private ArrayList<String> proofStr, resultStr;
    private BinaryParser parser;
    private ExpressionNode vars[];

    ProofChecker(@NotNull HashMap<String, Integer> assumptions, @NotNull ArrayList<String> proofStr, long startTime) {
        mp = new HashMap<>();               // For the amortization time O(1) checks the proof of the formula by M.P.
        proved = new HashMap<>();           // Store proof expression
        resultStr = new ArrayList<>();      // Output
        this.proofStr = new ArrayList<>();  // Store all proof in String type
        parser = new BinaryParser();        // Transform expression from String to Tree
        axioms = new Axioms().getAxioms();  // Store all axioms expressions
        this.assumptions = assumptions;     // Store all assumption expressions
        for (int i = 0; i < proofStr.size(); i++) {
            proved.put(parser.parse(proofStr.get(i)), i + 1);
        }
        proofStr.forEach(s -> this.proofStr.add(s.replaceAll("\\s", "")));
        System.out.println("Expressions parsed in " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @NotNull
    ArrayList<String> check() {
        int index, rowIndex = 0;
        for (; rowIndex < proofStr.size(); rowIndex++) {
            ExpressionNode curExpr = parser.parse(proofStr.get(rowIndex));
            if (mp.containsKey(curExpr)) {
                printRow(rowIndex, "(M.P. " + (mp.get(curExpr).getKey() + 1) + ", " + (mp.get(curExpr).getValue() + 1) + ")");
            } else if (assumptions.containsKey(curExpr.toString())) {
                printRow(rowIndex, "(Предп. " + (assumptions.get(curExpr.toString()) + 1) + ")");
            } else if ((index = checkAxioms(curExpr)) != NOT_FOUND) {
                printRow(rowIndex, "(Сх. акс. " + (index + 1) + ")");
            } else {
                resultStr.add("(" + (rowIndex + 1) + ") " + proofStr.get(rowIndex) + " (Не доказано)");
            }
        }
        return resultStr;
    }

    private void printRow(int index, @NotNull String annotation) {
        ExpressionNode curExpr = parser.parse(proofStr.get(index));
        resultStr.add("(" + (index + 1) + ") " + proofStr.get(index) + " " + annotation);
        if (!proved.containsKey(curExpr)) proved.put(curExpr, index);
        if (!mp.containsKey(curExpr.getRight()) && curExpr.getOperation() == ExpressionNode.Operation.IMP
                && proved.containsKey(curExpr.getLeft())) {
            mp.put(curExpr.getRight(), new Pair<>(proved.get(curExpr.getLeft()), index));
        }
    }

    private int checkAxioms(@NotNull ExpressionNode curExpr) {
        for (int i = 0; i < axioms.size(); i++) {
            vars = new ExpressionNode[MAX_VARS];
            if (checkRecursive(axioms.get(i), curExpr)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private boolean checkRecursive(@Nullable ExpressionNode axiom, @Nullable ExpressionNode curExpr) {
        if (axiom == null || curExpr == null) return axiom == curExpr;
        if (axiom.getType() != ExpressionNode.TypeNode.VAR && axiom.getOperation() == curExpr.getOperation()
                && checkRecursive(axiom.getLeft(), curExpr.getLeft())
                && checkRecursive(axiom.getRight(), curExpr.getRight())) {
            return true;
        } else {
            if (axiom.getType() != ExpressionNode.TypeNode.VAR) return false;
            int numberOfArg = (int) axiom.toString().charAt(0) - ASC_A;
            if (Objects.equals(vars[numberOfArg], null)) {
                vars[numberOfArg] = curExpr;
                return true;
            } else {
                return Objects.equals(vars[numberOfArg], curExpr);
            }
        }
    }

}