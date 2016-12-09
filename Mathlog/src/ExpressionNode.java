package Mathlog.src;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Blackbird on 05.10.2016.
 * Project : ExpressionNode
 * Start time : 1:54
 */
class ExpressionNode {
    private Operation operation;
    private ExpressionNode left, right;
    private TypeNode type;
    private String name = "";

    ExpressionNode(@NotNull ExpressionNode left, @NotNull ExpressionNode right, @NotNull Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        type = TypeNode.BIN;
    }

    ExpressionNode(@NotNull ExpressionNode right, @NotNull Operation operation) {
        left = null;
        this.right = right;
        this.operation = operation;
        type = TypeNode.UN;
    }

    ExpressionNode(@NotNull String name) {
        operation = Operation.NOP;
        left = right = null;
        this.type = TypeNode.VAR;
        this.name = name;
    }

    @Nullable
    ExpressionNode getLeft() {
        return left;
    }

    @Nullable
    ExpressionNode getRight() {
        return right;
    }

    @NotNull
    TypeNode getType() {
        return type;
    }

    @NotNull
    Operation getOperation() {
        return operation;
    }

    @Override
    public boolean equals(@NotNull Object obj) {
        if (!(obj instanceof ExpressionNode)) {
            return false;
        }
        ExpressionNode object = (ExpressionNode) obj;
        switch (object.type) {
            case BIN:
                return object.operation.equals(operation) &&
                        object.left.equals(left) && object.right.equals(right);
            case UN:
                return object.operation.equals(operation) && object.right.equals(right);
            case VAR:
                return object.name.equals(name);
            default:
                throw new RuntimeException("Invalid type");
        }
    }

    @Override
    public String toString() {
        switch (type) {
            case VAR:
                return name;
            case UN:
                return toString(operation) + right.toString();
            case BIN:
                return "(" + left.toString() + toString(operation) + right.toString() + ")";
            default:
                throw new RuntimeException("invalid type node");
        }
    }

    private String toString(@NotNull Operation op) {
        switch (op) {
            case IMP:
                return "->";
            case AND:
                return "&";
            case OR:
                return "|";
            case NOT:
                return "!";
            default:
                return "";
        }
    }

    @Override
    public int hashCode() {
        switch (type) {
            case BIN:
                return left.hashCode() * 1492 + right.hashCode() * 31463 + operation.hashCode() * 6379;
            case UN:
                return operation.hashCode() * 67617 + right.hashCode() * 59431;
            case VAR:
                return 961257 * name.hashCode();
            default:
                throw new RuntimeException("Invalid type");
        }
    }


    enum Operation {
        IMP, NOT, AND, OR, NOP

    }

    enum TypeNode {
        BIN, UN, VAR
    }
}
