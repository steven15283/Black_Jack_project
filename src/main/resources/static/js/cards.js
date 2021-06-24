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

function showCards(room, activePlayer) {
     $.ajax({
        url : '/api/v1/player/players/room/' + room,
        type : 'GET',
        dataType : 'json',
        success : function(players) {
            document.getElementById('player').innerHTML = ""
            busted_players = []
            for(var i=0; i < players.length; i++) {
                var div_player = document.createElement('div');
                var div_playerid = document.createElement('div');
                var div_hand = document.createElement('div');
                var div_points = document.createElement('div');

                div_points.className = 'points';
                div_points.id = 'points_' + players[i].username;
                div_player.id = 'player_' + players[i].username;
                div_player.className = 'player';
                div_hand.id = 'hand_' + players[i].username;

                div_playerid.innerHTML = players[i].username;
                div_player.appendChild(div_playerid);
                div_player.appendChild(div_hand);
                div_player.appendChild(div_points);

                document.getElementById('player').appendChild(div_player);

                document.getElementById('player_' + players[i].username).classList.remove('active');
                if(players[i].username == activePlayer.username) {
                    document.getElementById('player_' + activePlayer.username).classList.add('active');
                }

                document.getElementById('points_' + players[i].username).innerHTML = players[i].hand_value

                for(var j=0; j < players[i].hand.length; j++){
                    var icon = '';
                    icon='&hearts;';

                    var hand = document.getElementById('hand_' + players[i].username);

                    div = document.createElement('div');
                    div.innerHTML = players[i].hand[j]._card_name + '<br/><br/>' + icon;
                    div.className = 'card';

                    hand.appendChild(div);
               }
               if(players[i].hand_value > 21) {
                   busted_players.push(players[i].username)
               }
            }

            for(var k=0; k < busted_players.length; k++) {
                document.getElementById('points_' + busted_players[k]).innerHTML = "BUST"
            }
        }
    });
}

function showDealerCards(dealersTurn, dealer) {

    document.getElementById('dealer').innerHTML = ""

    var div_dealer = document.createElement('div');
    var div_dealerid = document.createElement('div');
    var div_hand = document.createElement('div');
    var div_points = document.createElement('div');

    div_points.className = 'points';
    div_points.id = 'points_dealer';
    //            div_player.id = 'player_' + player.username;
    div_dealer.className = 'player';
    div_hand.id = 'hand_dealer';

    div_dealerid.innerHTML = 'Dealer';
    div_dealer.appendChild(div_dealerid);
    div_dealer.appendChild(div_hand);
    div_dealer.appendChild(div_points);
    document.getElementById('dealer').appendChild(div_dealer);

    if(dealersTurn) {
        document.getElementById('points_dealer').innerHTML = dealer.hand_value
    }

    for(var i=0; i < dealer.hand.length; i++){
        var hand = document.getElementById('hand_dealer');

        div = document.createElement('div');
        if(!dealersTurn && i == 1) {
            div.innerHTML = "";
        }
        else {
            div.innerHTML = dealer.hand[i]._card_name;
        }
        div.className = 'card';

        hand.appendChild(div);
    }
}

function getWinners(winners){
    document.getElementById('winners').innerHTML = ""

    var div_winners = document.createElement('div');
    for(var i = 0; i < winners.length; i++) {
        var div_winnersid = document.createElement('div');

        div_winners.className = 'player';
        if(winners[i] == maxPlayers) {
            div_winnersid.innerHTML = player_global[i].username + " lost against Dealer";
        } else {
            div_winnersid.innerHTML = player_global[winners[i]].username + " won against Dealer";
        }

        div_winners.appendChild(div_winnersid);
        document.getElementById('winners').appendChild(div_winners);
    }

}

function showPlayers(players){
    document.getElementById('playersInRoom').innerHTML = ""

    var div_players = document.createElement('div');
    for(var i = 0; i < players.length; i++) {
        var div_playerid = document.createElement('div');

        div_players.className = 'player';

        div_playerid.innerHTML = players[i].username;

        div_players.appendChild(div_playerid);
        document.getElementById('playersInRoom').appendChild(div_players);
    }

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