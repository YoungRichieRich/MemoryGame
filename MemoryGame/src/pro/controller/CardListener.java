package pro.controller;

import pro.view.Game;
import pro.model.Card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class CardListener implements ActionListener {

    private Card uncoveredCard;
    private int cardAmount;
    private int solvedCards;
    private boolean isGameBlocked;
    private Game game;

    private Set<Card> seenCards = new HashSet<>();

    public CardListener(Game game, int cardAmount) {
        this.cardAmount = cardAmount;
        this.solvedCards = 0;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Card clickedCard = (Card)e.getSource();

        if(isGameBlocked)
            return;

        if(uncoveredCard == null) {
            clickedCard.uncoverCard();
            uncoveredCard = clickedCard;
        }
        else if(uncoveredCard != clickedCard) {
           clickedCard.uncoverCard();

           if(uncoveredCard.getId() == clickedCard.getId()) {
               System.out.println("brawo, karty odkryte");
               clickedCard.setBlocked();
               uncoveredCard.setBlocked();
               solvedCards += 2;

               if(!seenCards.contains(clickedCard)) {
                   game.addAdditionalPoints();
                   System.out.println("Odgadles karty za 1 razem!");
               }

               if(solvedCards == cardAmount) {
                   System.out.println("Gratulacje, wygrales");
                   game.getTimeCounter().stop();
                   game.finishGame();
                   //....
               }
               uncoveredCard = null;
           }
           else {
               System.out.println("Karty nie sa takie same.. zakrywam");

               seenCards.add(uncoveredCard);
               seenCards.add(clickedCard);

               isGameBlocked = true;
               Thread thread = new Thread(() -> {
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException interruptedException) {
                       interruptedException.printStackTrace();
                   }

                   uncoveredCard.coverCard();
                   clickedCard.coverCard();
                   isGameBlocked = false;
                   uncoveredCard = null;
               });

               thread.start();
           }


        }
    }
}
