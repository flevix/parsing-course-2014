import org.antlr.v4.runtime.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main <filename>");
            System.exit(-1);
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(args[0]);
        } catch (FileNotFoundException fnfe) {
            System.err.println(args[0] + " not found");
            System.exit(-1);
        } catch (SecurityException se) {
            System.err.println("check your access for " + args[0]);
            System.exit(-1);
        }
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(fileInputStream);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(-1);
            /* empty */
        }
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        EvalVisitor eval = new EvalVisitor();
        eval.visit(parser.prog());
    }
}
