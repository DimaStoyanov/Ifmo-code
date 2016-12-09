package Mathlog.src;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Blackbird on 05.10.2016.
 * Project : Axioms
 * Start time : 18:46
 */

class Axioms {
    private final String[] axiomsStr = {
            "A->B->A",
            "(A->B)->(A->B->C)->(A->C)",
            "A->B->A&B",
            "A&B->A",
            "A&B->B",
            "A->A|B",
            "B->A|B",
            "(A->C)->(B->C)->(A|B->C)",
            "(A->B)->(A->!B)->!A",
            "!!A->A"
    };

    @NotNull
    ArrayList<ExpressionNode> getAxioms() {
        ArrayList<ExpressionNode> result = new ArrayList<>();
        BinaryParser parser = new BinaryParser();
        Arrays.stream(axiomsStr).forEach(s -> result.add(parser.parse(s)));
        return result;
    }

    @NotNull
    final String[] getAxiomsStr() {
        return axiomsStr;
    }
}
