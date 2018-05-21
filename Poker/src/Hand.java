
/**
 * An object of type Hand represents a hand of cards.  The
 * cards belong to the class Card.  A hand is empty when it
 * is created, and any number of cards can be added to it.
 */

import java.util.ArrayList;

public class Hand {

   private Card[] hand;   // The cards in the hand.
   private int count; 
   
   /**
    * Create a hand that is initially empty.
    */
   public Hand() {
      hand = new Card[5];
	  count = 0;
   }
   
   /**
    * Remove all cards from the hand, leaving it empty.
    */
   public void clear() {
      for(int i=0 ; i<hand.length; i++){ hand[i] = null; }
	  count = 0;
   }
   
   /**
    * Add a card to the hand.  It is added at the end of the current hand.
    * @param c the non-null card to be added.
    * @throws NullPointerException if the parameter c is null.
    */
   public void addCard(Card c) {
      
	  for(int i=0 ; i<hand.length; i++){ 
		if (hand[i] == null){
			hand[i] = c;
			count = count + 1;
			break;
		}
	 }

	  
   }
   
   /**
    * Remove a card from the hand, if present.
    * @param c the card to be removed.  If c is null or if the card is not in 
    * the hand, then nothing is done.
    */
   public void removeCard(Card c) {

	for(int i=0 ; i<hand.length; i++){ 
		if (hand[i].equals(c)){
			hand[i] = null;
			count = count-1;
		}
	}

   }
   
   /**
    * Remove the card in a specified position from the hand.
    * @param position the position of the card that is to be removed, where
    * positions are starting from zero.
    * @throws IllegalArgumentException if the position does not exist in
    * the hand, that is if the position is less than 0 or greater than
    * or equal to the number of cards in the hand.
    */
   public void removeCard(int position) {
		if (position < 0 || position >= hand.length)
			throw new IllegalArgumentException("Position does not exist in hand: " + position);
		hand[position] = null;
		count--;
	}

   /**
    * Returns the number of cards in the hand.
    */
   public int getCardCount() {
      return count;
   }
   
   /**
    * Gets the card in a specified position in the hand.  (Note that this card
    * is not removed from the hand!)
    * @param position the position of the card that is to be returned
    * @throws IllegalArgumentException if position does not exist in the hand
    */
   public Card getCard(int position) {
      if (position < 0 || position >= hand.length)
         throw new IllegalArgumentException("Position does not exist in hand: "
               + position);
       return hand[position];
   }
   
   /**
    * Sorts the cards in the hand so that cards of the same suit are
    * grouped together, and within a suit the cards are sorted by value.
    * Note that aces are considered to have the lowest value, 1.
    */
   public void sortBySuit() {
	  int size = count;
	  int nonnull = 0;
	  int index = 0;
	  
      Card[] newHand = new Card[5];
      while (size > 0) {
		 if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
         int pos = nonnull;  // Position of minimal card.
         Card c = hand[nonnull];  // Minimal card.
		 
         for (int i = nonnull+1; i < hand.length; i++) {
            Card c1 = hand[i];
			if (c1 != null){
				if ( c1.getSuit() < c.getSuit() ||
						(c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
					pos = i;
					c = c1;
				}
			}
         }
         hand[pos] = null;
		 size = size - 1;
         newHand[index++] = c;
		 nonnull = 0;
      }
      hand = newHand;
   }
   
   /**
    * Sorts the cards in the hand so that cards of the same value are
    * grouped together.  Cards with the same value are sorted by suit.
    * Note that aces are considered to have the lowest value, 1.
    */
   public void sortByValue() {
	  int size = count;
	  int nonnull = 0;
	  int index = 0;
	  
      Card[] newHand = new Card[5];
      while (size > 0) {
		 if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
         int pos = nonnull;  // Position of minimal card.
         Card c = hand[nonnull];  // Minimal card.
		 
         for (int i = nonnull+1; i < hand.length; i++) {
            
			Card c1 = hand[i];
            if (c1 != null){
				if ( c1.getValue() < c.getValue() ||
						(c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
					pos = i;
					c = c1;
				}
			}
         }
         hand[pos] = null;
		 size = size - 1;
         newHand[index++] = c;
		 nonnull = 0;
      }
      hand = newHand;
   }
   
   public void printHand(){
	   
	   for(int i=0; i<hand.length; i++){
		   if (hand[i] != null){
			   System.out.println(hand[i]);
		   }
	   }
	   System.out.println();
   }
   

   /******************************** Implement your methods here ****************************************/


   //Returns the number of pairs this hand contains
   public int numPairs() {
	   this.sortByValue();
	   int pairs = 0; 
	   for (int i = 1; i < hand.length; i++) {
		   
	           if (hand[i-1].getValue() == hand[i].getValue()) {
	        	   pairs += 1; 
	        	   i++; 
	           }
	   }
	   // 3 3 3 4 5 
	   return pairs; 
   }
   //Returns true if this hand has 3 cards that are of the same value
   public boolean hasTriplet() {
	   boolean triplet = false; 
	   
	   for (int i = 0; i < hand.length; i++) {
		   for (int j = 0; j < hand.length; j++) {
			   for (int k = 0; k < hand.length; k++) {
				   
				   if(i != j && i != k && j!=k) {
					   if(hand[i].getValue() == hand[j].getValue() && hand[k].getValue() == hand[i].getValue()) {
						   triplet = true;
					   } 		   
				   }
			   }  
		   }   
	  
	   }   
	   return triplet; 
   }
 //Returns true if this hand has all cards that are of the same suit 
   public boolean hasFlush() {
	   
	   boolean flush = true; 
	   
	   for(int i = 1; i < hand.length; i++) {
		  
		 if  (hand[0].getSuit() != hand[i].getSuit()){
			 flush = false; 
		   }
	   }
	   
	   
	   return flush; 
   }
   
 //Returns true if this hand has 5 consecutive cards of any suit   
   public boolean hasStraight() {
	   boolean straight = false; 
	   int count = 0; 
	   this.sortByValue();
	   
	   for(int i = 1; i < hand.length; i++) {
			  if (hand[i-1].getValue() == hand[i].getValue() - 1) {
				   count++; 
			  }
			  
			   
	   }
	   if (count == 4) {
		   
		   straight = true; 
	   }
	   for (int j = 0; j < hand.length; j++) {
		   if (count == 3 && hand[j].getValue() == 1) {
			   straight = true; 
		   }
	   }
	  
	   
	   return straight; 
   }
  // Returns true if this hand has a triplet and a pair of different values 
   public boolean hasFullHouse() {
	   int count = 0; 
	  
	   boolean fullHouse = false; 
	   this.sortByValue();
	  
	   for (int i = 1; i < hand.length; i++) {
		    if (hand[i-1].getValue() == hand[i].getValue()) {
	   			count++; 
	   		} 
	   }
	   if (this.hasTriplet() == true && this.numPairs() == 2 && count == 3 && this.hasFourOfAKind() == false) {
		   fullHouse = true; 
		   
	   }
	   return fullHouse; 
	   
	   
   }
 //Returns true if this hand has 4 cards that are of the same value
   public boolean hasFourOfAKind() {
	   boolean fourOfAKind = false; 
	   this.sortByValue(); 
	   int count = 0; 
	   int set = 0; 
	   
	   for(int i = 1; i < hand.length; i++) {
		   if (hand[i-1].getValue() == hand[i].getValue()) {
	   			count++; 
	   		} 
		   if (hand[i-1].getValue() != hand[i].getValue() || i == hand.length - 1) {
	   			set = count; 
			   	count = 0; 
	   		} 
		   if (set == 3) {
			   break; 
		   }
	   }
	   if (set == 3) {
		   fourOfAKind = true; 
	   }
	   
	   return fourOfAKind; 
   }
   
   public Card fourOfAKindValue() {
	   Card x = null; 
	   this.sortByValue(); 
	 if (this.hasFourOfAKind() == true) {
		 for (int i = 1; i < hand.length; i++) {
			 if (hand[i-1].getValue() == hand[i].getValue()) {
				x = hand[i]; 
				break; 
			 }
		 }
		 
	 }  
	 return x; 
   }
   
   public Card tripletValue() {
	   Card x = null; 
	   this.sortByValue();
	   if (this.hasTriplet() == true) {
		   for (int i = 1; i < hand.length; i++) {
				 if (hand[i-1].getValue() == hand[i].getValue() && hand[i-1].getValue() == hand[i+1].getValue()) {
					x = hand[i]; 
					break; 
				 }
			 }	   
	   }
	   return x; 
   }
   
    
 //Returns the card with the highest value in the hand. When there is
 //more than one highest value card, you may return any one of them
 public Card highestValue() {
	 this.sortByValue();
	 
	 
	if (this.hasStraight() == false) { 
	if (hand[0].getValue() == 1) {
		return hand[0]; 
	} else {
	
	 
	return hand[hand.length-1];
	}
	} else {
		return hand[4]; 
	}
	
	 
 }
 public Card secondHighestValue() {
	 this.sortByValue();
	if (this.hasStraight() == false) {
	Card[] hand1 = new Card[5]; 
	int count = 0; 
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].getValue() == 1) {
				hand1[hand.length -1- i] = hand[i]; 
				count++; 
				
			} else hand1[i-count] = hand[i]; 
		}
		 
	 
	return hand1[hand1.length-2];
	} else {
		return hand[3]; 
	}

 }
 public Card thirdHighestValue() {
	 this.sortByValue();
		if (this.hasStraight() == false) {
	 Card[] hand1 = new Card[5]; 
		int count = 0; 
			for (int i = 0; i < hand.length; i++) {
				if (hand[i].getValue() == 1) {
					hand1[hand.length -1- i] = hand[i]; 
					count++; 
					
				} else hand1[i-count] = hand[i]; 
			}
			 
		 
		return hand1[hand1.length-3];
	
		} else {
			return hand[2]; 
		}

 }
 
 public Card fourthHighestValue() {
	 this.sortByValue();
		if (this.hasStraight() == false) {

	 Card[] hand1 = new Card[5]; 
		int count = 0; 
			for (int i = 0; i < hand.length; i++) {
				if (hand[i].getValue() == 1) {
					hand1[hand.length -1- i] = hand[i]; 
					count++; 
					
				} else hand1[i-count] = hand[i]; 
			}
			 
		 
		return hand1[hand1.length-4];
		} else {
			return hand[1]; 
		}
 }
 
 public Card fifthHighestValue() {
	 this.sortByValue();
		if (this.hasStraight() == false) {

	 Card[] hand1 = new Card[5]; 
		int count = 0; 
			for (int i = 0; i < hand.length; i++) {
				if (hand[i].getValue() == 1) {
					hand1[hand.length -1- i] = hand[i]; 
					count++; 
					
				} else hand1[i-count] = hand[i]; 
			}
			 
		 
		return hand1[hand1.length-5];
		} else {
			return hand[0]; 
		}
 }
  
 //Returns the highest valued Card of any pair or triplet found, null if
 // none. When values are equal, you may return either
 public Card highestDuplicate() {
	 	this.sortByValue();
	 	 Card[] hand1 = new Card[2]; 
	 	
		 hand1[0] = null; 
		 hand1[1] = null; 
		 int x = 0; 
		 if (this.numPairs() > 0 || this.hasTriplet() == true) {
			 for (int i = 1; i < hand.length; i++) {
				
					 if (hand[i-1].getValue() == hand[i].getValue() ) {
					 		hand1[x] = hand[i]; 
					 		
					 		i++; 
					 		if (x < 2) {
					 		x++; 
					 		}
					 	}
			 }
		 
		 if (hand1[1] == null) {
			 return hand1[0]; 
		 }
		 if (hand1[0].getValue() >= hand1[1].getValue()) {
			 if (hand1[1].getValue() == 1) {
				 return hand1[1]; 
			 }else {
				 return hand1[0]; 
			 }
		 } else {
			 if (hand1[0].getValue() == 1) {
				 return hand1[0];
			 }
			 return hand1[1]; 
			 
		 }
	 
	 
 	} else return null; 
 }
	 
 //Returns -1 if the instance hand loses to the parameter hand, 0 if the hands are equal, +1 if the instance hand wins over the parameter hand. 
 // Hint: you might want to use some of the methods above
 public int compareTo(Hand h) {
	 int x = 0; 
	if (h.hasFourOfAKind() == true) {
		if (this.hasFourOfAKind() == true) {
			x = 0; 
		} else {
			x = -1; 
		}	
	} else if (h.hasFullHouse() == true){
		if (this.hasFullHouse() == true) {
			x = 0; 
		} if (this.hasFullHouse() == false){
			x = -1; 
		} if (this.hasFourOfAKind() == true){
			x = 1; 
		}
	} else if (h.hasFlush() == true){
		if (this.hasFlush() == true) {
			x = 0; 
		} if (this.hasFlush() == false){
			x = -1; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true){
			x = 1; 
		}
	} else if (h.hasStraight() == true){
		if (this.hasStraight() == true) {
			x = 0; 
		} if (this.hasStraight() == false){
			x = -1; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true || this.hasFlush() == true){
			x = 1; 
		}
	} else if (h.hasTriplet() == true){
		if (this.hasTriplet() == true) {
			x = 0; 
		} if (this.hasTriplet() == false){
			x = -1; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true || this.hasFlush() == true || this.hasStraight() == true){
			x = 1; 
		}
	} else if (h.numPairs() == 2) {
		if (this.numPairs() == 2) {
			x = 0; 
		} if (this.numPairs() <= 1) {
			x = -1; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true || this.hasFlush() == true || this.hasStraight() == true || this.hasTriplet() == true){
			x = 1; 
		} 
	}else if (h.numPairs() == 1) {
		if (this.numPairs() == 1) {
			x = 0; 
		} if (this.numPairs() < 1) {
			x = -1; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true || this.hasFlush() == true || this.hasStraight() == true || this.hasTriplet() == true || this.numPairs() == 2){
			x = 1; 
		} 
	}else if (h.numPairs() == 0) {
		if (this.numPairs() == 0) {
			x = 0; 
		} if (this.hasFourOfAKind() == true || this.hasFullHouse() == true || this.hasFlush() == true || this.hasStraight() == true || this.hasTriplet() == true || this.numPairs() >= 1 ){
			x = 1; 
		} 
	}
	// four of a kind
	// full house
	// flush
	// straight
	// three of a kind
	// two pair
	// one pair
	// high card 
	if (x == 0) {
		this.sortByValue();
		h.sortByValue();
		if (this.highestDuplicate() != null && h.highestDuplicate() != null) {
			if (this.highestDuplicate().getValue() > h.highestDuplicate().getValue()) {
				x = 1; 
				if (h.highestDuplicate().getValue() == 1) {
					x = -1; 
				}
				System.out.println("11");

			} else if (this.highestDuplicate().getValue() < h.highestDuplicate().getValue()) {
				x = -1; 
				if (this.highestDuplicate().getValue() == 1) {
					x = 1; 
				}
				System.out.println("12");

			} else if (this.highestDuplicate().getValue() == h.highestDuplicate().getValue()) {
				x = 0; 
				System.out.println("13    " + this.highestDuplicate());

			}
		}
		if (this.highestDuplicate() == null && h.highestDuplicate() == null) {
				if (this.highestValue().getValue() > h.highestValue().getValue()) {
					x = 1; 
					System.out.println("1");
					if (h.highestValue().getValue() == 1) {
						x = -1; 
					}
				} 
				else if (this.highestValue().getValue() < h.highestValue().getValue()) {
					x = -1; 
					System.out.println("2            " + this.highestValue().getValue() + "            " + h.highestValue().getValue());
					if (this.highestValue().getValue() == 1) {
						x = 1; 
					}
				} else if ((this.secondHighestValue().getValue() < h.secondHighestValue().getValue())){
							x = -1; 
							System.out.println("3");
							if (this.secondHighestValue().getValue() == 1) {
								x = 1; 
							}
							
						} else if ((this.secondHighestValue().getValue() > h.secondHighestValue().getValue())){
							x = 1; 
							System.out.println("3");
							if (h.secondHighestValue().getValue() == 1) {
								x = -1; 
							}
							
						}else if (this.thirdHighestValue().getValue() > h.thirdHighestValue().getValue()) {
								x = 1; 
								System.out.println("4");
								if (h.thirdHighestValue().getValue() == 1) {
									x = -1; 
								}
		
							} else if ((this.thirdHighestValue().getValue() < h.thirdHighestValue().getValue())){
								x = -1; 
								System.out.println("5");
								if (this.thirdHighestValue().getValue() == 1) {
									x = 1; 
								}
		
							} else if (this.fourthHighestValue().getValue() > h.fourthHighestValue().getValue()) {
								x = 1; 
								System.out.println("6");
								if (h.fourthHighestValue().getValue() == 1) {
									x = -1; 
								}
		
							} else if ((this.fourthHighestValue().getValue() < h.fourthHighestValue().getValue())){
								x = -1; 
								System.out.println("7");
								if (this.fourthHighestValue().getValue() == 1) {
									x = 1; 
								}
		
							} else if (this.fifthHighestValue().getValue() > h.fifthHighestValue().getValue()) {
								x = 1; 
								System.out.println("8");
								if (h.fifthHighestValue().getValue() == 1) {
									x = -1; 
								}
		
							} else if ((this.fifthHighestValue().getValue() < h.fifthHighestValue().getValue())){
								x = -1; 
								System.out.println("9");
								if (this.fifthHighestValue().getValue() == 1) {
									x = 1; 
								}
		
							} else x = 0; 
		}
		}
	if (this.hasFourOfAKind() == true && h.hasFourOfAKind() == true) {
		if (this.fourOfAKindValue().getValue() > h.fourOfAKindValue().getValue()) {
			x = 1; 
		} else if (this.fourOfAKindValue().getValue() < h.fourOfAKindValue().getValue()) {
			x = -1; 
		} else {
			x = 0; 
		}
		
	} else if (this.hasFullHouse() == true && h.hasFullHouse() == true) {
		if (this.tripletValue().getValue() > h.tripletValue().getValue()) {
			x = 1; 
		} else if (this.tripletValue().getValue() < h.tripletValue().getValue()) {
			x = -1; 
		} else {
			x = 0; 
		}
	}
	
	
	
	 return x; 
 }
  
   
}
