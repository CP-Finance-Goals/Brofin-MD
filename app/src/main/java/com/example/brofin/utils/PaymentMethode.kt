package com.example.brofin.utils

enum class PaymentMethode(val code: Int) {
    MYCASH(0),
    CREDIT_CARD(1),
    DEBIT_CARD(2),
    E_WALLET(3),
    BANK_TRANSFER(4),
    OTHER(5);

    companion object {
        fun fromCode(code: Int?): PaymentMethode? {
            return entries.find { it.code == code }
        }
    }
}
