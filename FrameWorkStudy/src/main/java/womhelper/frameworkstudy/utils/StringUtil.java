package womhelper.frameworkstudy.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Walkud on 17/5/23.
 */

public class StringUtil {

    private static double f = 111231.05;

    public static void m1() {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    /**
     * DecimalFormat转换最简便
     */
    public static void m2() {
        DecimalFormat df = new DecimalFormat("0.#");
        System.out.println(df.format(f));
    }

    /**
     * String.format打印最简便
     */
    public static void m3() {
        System.out.println(String.format("%.2f", f));
    }

    public static void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }

    public static void main(String[] args) {
        m1();
        m2();
        m3();
        m4();
    }
}
