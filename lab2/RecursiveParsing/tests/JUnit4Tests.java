import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ifmo.ctddev.nechaev.syntaxAnalyzer.Parser;
import ru.ifmo.ctddev.nechaev.syntaxAnalyzer.Tree;

import java.text.ParseException;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/14.
 */
public class JUnit4Tests {
    static Parser parser;
    Exception exception;
    static boolean toPrint;

    @BeforeClass
    public static void setUp() throws Exception {
        parser = new Parser();
        toPrint = false;
    }

    @Before
    public void purge() {
        exception = null;
    }

    /**
     * Пример из задания
     */
    @Test
    public void test0() {
        Assert.assertNull(
                execute(toPrint,
                        "int a, *b, ***c, d;"
                )
        );
    }

    /**
     * Тест на правило S -> eps,
     * где eps -- пустая или состоящая только из нетерминалов [' ','\r','\n','\t'] строка
     */
    @Test
    public void test1() {
        Assert.assertNull(
                execute(toPrint,
                        "",
                        " ",
                        " \t ",
                        " \r \n \t   \t \n"
                )
        );
    }

    /**
     * Тест на правило S -> WS
     */
    @Test
    public void test2() {
        Assert.assertNull(
                execute(toPrint,
                        "int x; int y; int z,\nz;",
                        " int x;\n\n\tint y;\t",
                        "int\nx\n,\ny\n;double y;"
                )
        );
    }

    /**
     * Несколько корректных тестов
     */
    @Test
    public void test3() {
        Assert.assertNull(
                execute(toPrint,
                        "int x;",
                        "int x,Y;",
                        "int x,y; double z;",
                        "int x;double z;",
                        "int x,*y,\n**z;\tx*y;"
                )
        );
    }

    /**
     * Тесты на ParseException
     * Некорректный символ
     * то есть не [',',';','*','a'-'z','A'-'Z']
     */
    @Test
    public void test4() {
        checkParseException(
                toPrint,
                "1",
                "int 3",
                "_",
                "int x,)",
                "int x,y&",
                "int x,y;&"
        );
    }

    /**
     * Тесты на ParseException
     * Неожиданный нетерминал
     */
    @Test
    public void test5() {
        checkParseException(
                toPrint,
                "*",
                ",",
                ";",
                "  *",
                "int ,",
                "int ;",
                "int,",
                "int;",
                "int x,;",
                "int x,,",
                "int x,*,"
        );
    }

    private void checkParseException(boolean print, String... sources) {
        int sourceId = 0;
        if (print) {
            for (String source : sources) {
                System.out.println(sourceId++ + "/" + source);
            }
        }
        sourceId = 0;
        for (String source : sources) {
            try {
                parser.parse(source);
            } catch (Exception e) {
                exception = e;
            } finally {
                Assert.assertTrue(exception instanceof ParseException);
                if (print) {
                    System.out.println(sourceId++ + "|" + exception.getMessage() + " at pos " +
                            ((ParseException) exception).getErrorOffset());
                }
                exception = null;
            }
        }
    }

    private Exception execute(boolean print, String... sources) {
        int sourceId = 0;
        if (print) {
            for (String source : sources) {
                System.out.println(sourceId++ + "/" + source);
            }
        }
        sourceId = 0;
        try {
            for (String source : sources) {
                Tree tree = parser.parse(source);
                if (print) {
                    System.out.println(sourceId++ + "|");
                    System.out.println(tree);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            exception = e;
        }
        return exception;
    }

}
