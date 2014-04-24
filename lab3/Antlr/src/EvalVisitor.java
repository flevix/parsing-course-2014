import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Integer> {
    Map<String, Integer> variables = new HashMap<String, Integer>();

    /** ID '=' expr ; */
    @Override
    public Integer visitFAssign(ExprParser.FAssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        variables.put(id, value);
        System.out.println(id + " = " + value + ";");
        return 0;
    }

    /** expr ; */
    @Override
    public Integer visitEval(ExprParser.EvalContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    /** expr ('*'|'/') expr */
    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.MUL) {
            return left * right;
        }
        return left / right;
    }

    /** expr ('+'|'-') expr */
    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.ADD) {
            return left + right;
        }
        return left - right;
    }

    /** INT */
    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /** - INT */
    @Override
    public Integer visitSubInt(ExprParser.SubIntContext ctx) {
        return -Integer.valueOf(ctx.INT().getText());
    }

    /** ID '=' expr */
    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        variables.put(id, value);
        System.out.println(id + " = " + value);
        return value;
    }

    /** ID */
    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (variables.containsKey(id)) {
            return variables.get(id);
        }
        return 0;
    }

    /** - ID */
    @Override
    public Integer visitSubId(ExprParser.SubIdContext ctx) {
        String id = ctx.ID().getText();
        if (variables.containsKey(id)) {
            return -variables.get(id);
        }
        return 0;
    }

    /** '(' expr ')' */
    @Override
    public Integer visitParens(ExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

}
