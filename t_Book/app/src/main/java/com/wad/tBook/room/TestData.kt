package com.wad.tBook.room

fun getTestData() :List<Accounting>{
    var accountList: MutableList<Accounting> = mutableListOf()
    accountList.add(Accounting(0,"支出", 50.00,
                                accountingClass = MultilevelClassification("食品", "三餐"),
                                accountingAcconut = MultilevelClassification("虚拟账户", "校园卡"),
                                "Tue Oct 20 10:46:38 GMT", accountingRemark = ""))
    accountList.add(Accounting(0,"支出", 60.00,
                                accountingClass = MultilevelClassification("食品", "饮料"),
                                accountingAcconut = MultilevelClassification("虚拟账户", "银行卡"),
                                "Tue Oct 20 10:46:38 GMT", accountingRemark = ""))
    accountList.add(Accounting(0,"支出", 20.00,
                                accountingClass = MultilevelClassification("娱乐", "电影"),
                                accountingAcconut = MultilevelClassification("现金账户", "现金"),
                                "Tue Oct 20 10:46:38 GMT", accountingRemark = ""))

    return accountList
}