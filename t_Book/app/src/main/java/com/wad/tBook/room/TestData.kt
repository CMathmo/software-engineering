package com.wad.tBook.room

fun getTestData() :List<Accounting>{
    var accountList: MutableList<Accounting> = mutableListOf()
    accountList.add(Accounting(0,"支出", 50.00,
                                accountingClass = MultilevelClassification("食品", "三餐"),
                                accountingAcconut = MultilevelClassification("虚拟账户", "饭卡"),
        accountingTime = "2020年10月22日", accountingRemark = ""))
    accountList.add(Accounting(0,"支出", 60.00,
                                accountingClass = MultilevelClassification("食品", "饮料"),
                                accountingAcconut = MultilevelClassification("金融账户", "银行卡"),
        accountingTime = "2020年10月23日", accountingRemark = ""))
    accountList.add(Accounting(0,"支出", 20.00,
                                accountingClass = MultilevelClassification("娱乐", "电影"),
                                accountingAcconut = MultilevelClassification("现金", "现金"),
        accountingTime = "2020年10月23日", accountingRemark = ""))

    return accountList
}