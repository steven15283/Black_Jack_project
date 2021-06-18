var maxPlayers = 0;
var curPlayers = 0;
var player_global = [];
var room_global = 0;

function createTestPlayers() {
    createPlayer("steven")
    createPlayer("mike")
    createPlayer("ken")
}
function getPlayers() {
    $.ajax({
        // Build environment
        // url : 'http:localhost:8080/api/v1/players/',
        url : '/api/v1/player/players/',
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            console.log(data)
        }
    });
}

function getCurrentPlayer() {

    return player_global[curPlayers].username;
}

function getDealer(dealersTurn) {
    $.ajax({
            url : 'api/v1/blackjack/getDealer',
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                showDealerCards(dealersTurn, data);
            }
    });
}

function getWinners(winners){
    document.getElementById('winners').innerHTML = ""

    var div_winners = document.createElement('div');
    for(var i = 0; i < winners.length; i++) {
        var div_winnersid = document.createElement('div');

        div_winners.className = 'player';
        if(winners == maxPlayers) {
//            div_winnersid.innerHTML = player_global[winners] + " won against Dealer";
        } else {
            div_winnersid.innerHTML = player_global[winners].username + " won against Dealer";
        }

        div_winners.appendChild(div_winnersid);
        document.getElementById('winners').appendChild(div_winners);
    }

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

function createPlayer(username) {
      var jsonData =
      {
        "username" : username,
      }

      saveData = $.ajax({
            type: 'POST',
            url: "/api/v1/register/newPlayer/",
            data: JSON.stringify(jsonData),
            dataType: "json",
            contentType: "application/json",
            success: function(resultData) { console.log("Save Complete") }
      });

}

function callDealerApi() {
    $.ajax({
        url : 'api/v1/blackjack/getDealer',
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            getPlayersInRoom(1)
        }
    });
}

function startGame() {
      var jsonData = [
          {
                "username" : "steven"
          },
          {
                "username" : "mike"
          },
          {
                "username" : "ken"
          },
      ]

      saveData = $.ajax({
            type: 'POST',
            url: "api/v1/blackjack/start",
            data: JSON.stringify(jsonData),
            dataType: "json",
            contentType: "application/json",
      });

      document.getElementById("gameButtons").style.display="block";
}

function getPlayersInRoom(room){
     $.ajax({
            url : 'api/v1/player/players/room/' + room,
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                playerTurn(data)
                showCards(room, player_global[curPlayers], false);
                room_global = room
            }
    });
}

function playerTurn(players) {
    player_global = players
    maxPlayers = players.length
    getDealer(false);
}

function hit(){
    console.log(curPlayers)
    if(curPlayers >= maxPlayers) {
       $.ajax({
              url : 'api/v1/blackjack/dealerHit',
              type : 'POST',
              success : function(data) {
                  getDealer(true);
                  for(var i = 0; i < data.length; i++) {
                    if(data[i] < maxPlayers) {
                        console.log("Winners are " + player_global[data[i]].username)
                    }
                    else {
                        console.log("Winners are Dealer")
                    }
                  }
                  getWinners(data)
              }
       });
    }
    else {
        var data = {
             "username" : player_global[curPlayers].username
        }
        $.ajax({
               url : 'api/v1/blackjack/hit',
               type : 'POST',
               data: JSON.stringify(data),
               contentType: "application/json",
               dataType : 'json',
               success : function(data) {
                   if(data == 1) {
                      showCards(room_global, player_global[curPlayers], true);
                      setTimeout(function () {
                          standEvent()
                      }, 2000);
                   } else {
                      showCards(room_global, player_global[curPlayers], false);
                   }
               }
        });
    }
}

function standEvent(){
    curPlayers = curPlayers + 1;
    if(curPlayers < maxPlayers) {
        showCards(room_global, player_global[curPlayers], false);
    }
    else {
       getDealer(true);
    }
}

function reset(){
    $.ajax({
                url : 'api/v1/blackjack/reset/' + room,
                type : 'GET',
                dataType : 'json'

        });
}
//function bj(player){
//       var data = {
//                    "username" : player.getUsername()
//          }
//          $.ajax({
//                  url : 'api/v1/blackjack/bj',
//                  type : 'POST',
//                  data: JSON.stringify(data),
//                  dataType : 'json',
//                  success : function(data) {
//                      console.log(data)
//                  }
//              });
//}
//
//function dealerWon(players){
//       players = $.ajax({
//               url : 'api/v1/blackjack/dealerWon',
//               type : 'GET',
//               dataType : 'json',
//               success : function(data) {
//                   console.log(data)
//               }
//           });
//}
//
//function dealer_reach_limit(dealer){
//      $.ajax({
//              url : 'api/v1/blackjack/dealerRL',
//              type : 'POST',
//              data: JSON.stringify(dealer),
//              dataType : 'json',
//              success : function(dealer) {
//                  console.log(dealer)
//              }
//          });
//}
//
//function checkHand(player){
//       players = $.ajax({
//               url : 'api/v1/blackjack/checkHand',
//               type : 'GET',
//               dataType : 'json',
//               success : function(data) {
//                   console.log(data)
//               }
//           });
//}
//
//function dealer_bj_check(dealer){
//          $.ajax({
//                  url : 'api/v1/blackjack/dealerBJCheck',
//                  type : 'POST',
//                  data: JSON.stringify(dealer),
//                  dataType : 'json',
//                  success : function(dealer) {
//                      console.log(dealer)
//                  }
//              });
//}