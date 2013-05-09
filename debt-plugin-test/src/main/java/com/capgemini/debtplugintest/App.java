package com.capgemini.debtplugintest;

import com.capgemini.csd.debt.Debt;
import com.capgemini.csd.debt.Debts;
import java.util.List;

/**
 * @author Andrew Harmel-Law
 */
@Debts({
    @Debt(desc="This is a class level debt test.", count=2000),
    @Debt(desc="This is another class level debt test.", count=20),
    @Debt(desc="This is a third class level test with < and >", count=10)
})
public class App {

    @Debts({
        @Debt(desc="This is an attribute test.", count=1),
        @Debt(desc="This is a second attribute test.", count=1),
        @Debt(desc="This is a third attribute test with an &", count=10)
    })
    private int val = 0;
    
    /**
     * @param args the command line arguments
     */
    @Debts({
        @Debt(desc="This is a method test", count=10),
        @Debt(desc="This is a second method test", count=10),
        @Debt(desc="This is a third method test with 'speech-marks'", count=10)
    })
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    @Debts({
        @Debt(desc="Generic method test", count=2),
        @Debt(desc="This is a second method test with \"speech-marks\"", count=10)
    })
    public static List<String> stringLister(List<String> list) {
        return list;
    }

}
