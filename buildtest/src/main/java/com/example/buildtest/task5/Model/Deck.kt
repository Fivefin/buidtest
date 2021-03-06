package com.example.buildtest.task5.Model

import java.util.Random


//构建牌以及抽取牌
class Deck() {
    private val cards = mutableListOf<Card>()
    private val r = Random()

    init {
        //按照组合花色创建了54张花色
        for (suit in Card.validSuits) {
            for (rank in Card.rankStrings) {
                val card = Card(suit = suit,rank = rank)
                    cards.add(card)
            }
        }
    }
//    任意抽取一张已经组合好的牌
    fun drawRandomCard():Card? {
    var randomCard: Card? = null
    if(cards.size > 0) {
        randomCard = cards.removeAt(r.nextInt(cards.size))//r.nextInt(n)产生0-n的随机数
    }
    return randomCard
}

}