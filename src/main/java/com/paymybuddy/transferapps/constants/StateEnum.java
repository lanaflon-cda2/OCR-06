package com.paymybuddy.transferapps.constants;

public enum StateEnum {

    EMAIL("Please enter your email"),
    CONNECTION_EMAIL("Please enter the email of your friend, we hope he is registered with us!"),
    NEW_BANKACCOUNT("Please enter a new bank account name"),
    AMOUNT_DEPOSIT("Please enter the amount you want to deposit on the bank account."),
    AMOUNT_WITHDRAW("Please enter the amount you want to withdraw from your bank and add on your account."),
    AMOUNT_SEND("Please enter the amount you want to send to him."),
    CHOOSE_FRIEND("Please choose the friend by entering the corresponding number."),
    CHOOSE_BANKACCOUNT("Please choose the bank account by entering the corresponding number."),
    DESCRIPTION("You can add a comment for the transfer."),
    PASSWORD("Please enter your password"),

    //Logger messages
    NO_BANKACCOUNT("You don't have any bank account registered, please add one."),
    NO_RELATIVES("Sorry, you have no relatives, please add at least one. He must have an account in PayMyBuddy");

    private String sentence;

    StateEnum(String name) {
        this.sentence = sentence;
    }

    public String getStr() {
        return sentence;
    }
}
