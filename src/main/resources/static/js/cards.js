function card(value, name, suit){
  this.value = value;
  this.name = name;
  this.suit = suit;
}

function deck(){
  this.names = ['A', '2', '3', '4', '5', '6', '7', '8', '9', '10', 'J', 'Q', 'K'];
  this.suits = ['Hearts','Diamonds','Spades','Clubs'];
  var cards = [];

    for( var s = 0; s < this.suits.length; s++ ) {
        for( var n = 0; n < this.names.length; n++ ) {
            cards.push( new card( n+1, this.names[n], this.suits[s] ) );
        }
    }

    return cards;
}

function showCards(username) {
    $.ajax({
        url : 'api/v1/player/' + username,
        type : 'GET',
        dataType : 'json',
        success : function(player) {
            console.log(player)

            document.getElementById('player').innerHTML = ""

            var div_player = document.createElement('div');
            var div_playerid = document.createElement('div');
            var div_hand = document.createElement('div');
            var div_points = document.createElement('div');

            div_points.className = 'points';
            div_points.id = 'points_' + player.username;
//            div_player.id = 'player_' + player.username;
            div_player.className = 'player';
            div_hand.id = 'hand_' + player.username;

            div_playerid.innerHTML = player.username;
            div_player.appendChild(div_playerid);
            div_player.appendChild(div_hand);
            div_player.appendChild(div_points);
            document.getElementById('player').appendChild(div_player);

            document.getElementById('points_' + player.username).innerHTML = player.hand_value

            for(var i=0; i < player.hand.length; i++){
                var hand = document.getElementById('hand_' + player.username);

                div = document.createElement('div');
                div.innerHTML = player.hand[i]._card_name;
                div.className = 'card';

                hand.appendChild(div);
           }
        }
    });
}

//var myDeck = new deck();
//
//window.onload = function() {
//
//  for(var i=0; i < myDeck.length; i++){
//    div = document.createElement('div');
//
//    if(myDeck[i].suit == 'Diamonds'){
//        div.className = 'red-card';
//      var ascii_char = '&diams;';
//    }
//    else if(myDeck[i].suit == 'Hearts'){
//            div.className = 'red-card';
//      var ascii_char = '&' + myDeck[i].suit.toLowerCase() + ';';
//        }
//        else {
//        div.className = 'card';
//      var ascii_char = '&' + myDeck[i].suit.toLowerCase() + ';';
//    }
//
//    div.innerHTML = '<span class="number">' + myDeck[i].name + '</span><span class="suit">' + ascii_char + '</span>';
//    document.body.appendChild(div);
//  }
//
//}