package com.capgemini.me.debtplugintest;

import com.capgemini.csd.debt.Debt;
import com.capgemini.csd.debt.Debts;

/**
 *
 * @author AHARMEL
 */
@Debts({
    @Debt(desc="This is a class level debt test in another package.", count=2000),
    @Debt(desc="This is another class level debt test in another package.", count=20)
})
public class NewClass {
    
}
