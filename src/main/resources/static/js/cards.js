var busted_players = []

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

function showCards(room, activePlayer, busted) {
     $.ajax({
        url : '/api/v1/player/players/room/' + room,
        type : 'GET',
        dataType : 'json',
        success : function(players) {
            document.getElementById('player').innerHTML = ""
            console.log("showcards.players:",  players);
            //console.log("showcards.activePlayer:", activePlayer);
            for(var i=0; i < players.length; i++) {
            console.log("showcards.players: ", i)
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
            }
            if(busted) {
                busted_players.push(activePlayer.username)
            }
            for(var k=0; k < busted_players.length; k++) {
                document.getElementById('points_' + busted_players[k]).innerHTML = "BUST"
            }
        }
    });
}

function clear_busted_players(){
busted_players = [];
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