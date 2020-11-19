package craftinginterpreters.lox;

import craftinginterpreters.lox.Expr.Binary;
import craftinginterpreters.lox.Expr.Grouping;
import craftinginterpreters.lox.Expr.Literal;
import craftinginterpreters.lox.Expr.Trinomial;
import craftinginterpreters.lox.Expr.Unary;

public class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right) + endSime(expr.endWithSemi);
    }

    @Override
    public String visitGroupingExpr(Grouping expr) {
        return parenthesize("group", expr.expression) + endSime(expr.endWithSemi);
    }

    @Override
    public String visitLiteralExpr(Literal expr) {
        if (expr.value == null)
            return "nil";
        return expr.value.toString() + endSime(expr.endWithSemi);
    }

    @Override
    public String visitUnaryExpr(Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right) + endSime(expr.endWithSemi);
    }

    @Override
    public String visitTrinomialExpr(Trinomial expr) {
        return "( " + expr.condition.accept(this) + " ? " + expr.expr1.accept(this) + " : " + expr.expr2.accept(this) + " )" + endSime(expr.endWithSemi);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }
    
    private String endSime(boolean sime) {
        return sime ? ";" : "";
    }

    public static void main(String[] args) {
        Expr expr = new Expr.Binary(new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(123)),
                new Token(TokenType.STAR, "+", null, 1), new Expr.Grouping(new Expr.Literal(45.67)));
        System.out.println(new AstPrinter().print(expr));
    }
}
