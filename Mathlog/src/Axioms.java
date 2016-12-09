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
            "A->Discrete_Math.Matroid.B->A",
            "(A->Discrete_Math.Matroid.B)->(A->Discrete_Math.Matroid.B->Discrete_Math.Matroid.C)->(A->Discrete_Math.Matroid.C)",
            "A->Discrete_Math.Matroid.B->A&Discrete_Math.Matroid.B",
            "A&Discrete_Math.Matroid.B->A",
            "A&Discrete_Math.Matroid.B->Discrete_Math.Matroid.B",
            "A->A|Discrete_Math.Matroid.B",
            "Discrete_Math.Matroid.B->A|Discrete_Math.Matroid.B",
            "(A->Discrete_Math.Matroid.C)->(Discrete_Math.Matroid.B->Discrete_Math.Matroid.C)->(A|Discrete_Math.Matroid.B->Discrete_Math.Matroid.C)",
            "(A->Discrete_Math.Matroid.B)->(A->!Discrete_Math.Matroid.B)->!A",
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
