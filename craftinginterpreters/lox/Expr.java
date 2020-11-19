package craftinginterpreters.lox;

import java.util.List;
import craftinginterpreters.lox.Token;

abstract class Expr {
    interface Visitor<R> {
        R visitBinaryExpr(Binary expr);

        R visitGroupingExpr(Grouping expr);

        R visitLiteralExpr(Literal expr);

        R visitUnaryExpr(Unary expr);

        R visitTrinomialExpr(Trinomial expr);
    }

    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }

    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        final Expr expression;
    }

    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        final Object value;
    }

    static class Unary extends Expr {
        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        final Token operator;
        final Expr right;
    }

    static class Trinomial extends Expr {
        Trinomial(Expr condition, Token operator1, Expr expr1, Token operator2, Expr expr2) {
            this.condition = condition;
            this.operator1 = operator1;
            this.expr1 = expr1;
            this.operator2 = operator2;
            this.expr2 = expr2;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitTrinomialExpr(this);
        }

        final Expr condition;
        final Token operator1;
        final Expr expr1;
        final Token operator2;
        final Expr expr2;
    }

    boolean endWithSemi = false;

    abstract <R> R accept(Visitor<R> visitor);
}
