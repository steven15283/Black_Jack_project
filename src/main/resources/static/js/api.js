var maxPlayers = 0;
var curPlayers = 0;
var player_global = [];
var room_global = "";
var user_global = "";
var endpoint = "8080/";
var firstTimeDealer = true;

window.onload = function() {
    var url = window.location.href
    user_global = url.substring(url.search("user/") + 5)
    for(var i=0; i < 4; i++) {
        room_global += url[url.search(endpoint) + 5 + i]
    }

    document.getElementById("room").innerHTML = room_global
}

var checkIfGameStart = setInterval(function(){
    if(curPlayers < maxPlayers && curPlayers != -1 && !firstTimeDealer) {
        console.log("checkifgamestart.curPlayers:",curPlayers);
        console.log("checkifgamestart.player_global:",player_global);
        console.log("checkifgamestart.player_global[curPlayers]:",player_global[curPlayers]);
        showCards(room_global, player_global[curPlayers], false);
    }
    $.ajax({
        // Build environment
        // url : 'http:localhost:8080/api/v1/players/',
        url : '/api/v1/room/status/' + room_global,
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            if(data) {
                document.getElementById("gameButtons").style.display="block";
                 $.ajax({
                        url : '/api/v1/player/players/room/' + room_global,
                        type : 'GET',
                        dataType : 'json',
                        success : function(data) {
                            player_global = data
                            maxPlayers = data.length
                            if(firstTimeDealer) {
                              setTimeout(function () {
                                  getDealer(false);
                                  firstTimeDealer = false;
                              }, 1000);
                            }
                        }
                });
            }
        }
    });
    $.ajax({
        // Build environment
        // url : 'http:localhost:8080/api/v1/players/',
        url : '/api/v1/room/currentPlayer/' + room_global,
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            curPlayers = data
        }
    });
}, 1000);

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
            url : '/api/v1/blackjack/getDealer',
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                showDealerCards(dealersTurn, data);
            }
    });
}

function getWinners(winners){
    document.getElementById('winners').innerHTML = ""
    console.log("getwinners.player_global: ", player_global);
    console.log("getwinners.winners: ", winners);
    var div_winners = document.createElement('div');
    for(var i = 0; i < winners.length; i++) {
        var div_winnersid = document.createElement('div');
        console.log("getwinners.player_global.for:", i);
        console.log("getwinners.maxplayers:", maxPlayers);
        div_winners.className = 'player';
        if(winners[i] == maxPlayers) {

            div_winnersid.innerHTML = player_global[i].username + " lost against Dealer";
        }
        else {

            div_winnersid.innerHTML = player_global[winners[i]].username + " won against Dealer";
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
        "room" : "1"
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
        url : '/api/v1/blackjack/getDealer',
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            getPlayersInRoom(room_global)
        }
    });
}

function startGame() {
     $.ajax({
            url : '/api/v1/player/players/room/' + room_global,
            type : 'GET',
            dataType : 'json',
            success : function(data) {

                  var jsonData = []

                  for(var i=0; i < data.length; i++) {
                    jsonData.push(data[i])
                  }

                  $.ajax({
                        type: 'POST',
                        url: "/api/v1/blackjack/start",
                        data: JSON.stringify(jsonData),
                        dataType: "json",
                        contentType: "application/json",
                  });

            }
    });

    document.getElementById("gameButtons").style.display="block";

    var roomData = {
         "id" : room_global,
         "gameStarted" : true
    }
    $.ajax({
        type: 'POST',
        url: "/api/v1/room/status/",
        data: JSON.stringify(roomData),
        dataType: "json",
        contentType: "application/json"
    });
    setTimeout(function () {
        callDealerApi();
    }, 1000);
}

function getPlayersInRoom(room){
     $.ajax({
            url : '/api/v1/player/players/room/' + room,
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                playerTurn(data);
                console.log("getplayersinroom.curPlayers:",curPlayers);
                console.log("getplayersinroom.player_global:",player_global);
                console.log("getplayersinroom.player_global[curPlayers]:",player_global[curPlayers]);
                if(curPlayers < maxPlayers)
                {
                    showCards(room, player_global[curPlayers], false);
                }
                else
                {
                    //showCards(room, player_global[curPlayers], false);
                }
            }
    });
}

function playerTurn(players) {
    player_global = players
    maxPlayers = players.length
    getDealer(false);
}

function hit(){
    if(curPlayers < maxPlayers && user_global == player_global[curPlayers].username) {
        var data = {
             "username" : player_global[curPlayers].username
        }
        $.ajax({
               url : '/api/v1/blackjack/hit',
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
    if(curPlayers < maxPlayers && curPlayers != -1) {
        if(user_global == player_global[curPlayers].username) {
    //        curPlayers = curPlayers + 1;
            var roomData = {
                 "id" : room_global,
                 "currentPlayer" : curPlayers + 1
            }
            $.ajax({
                type: 'POST',
                url: "/api/v1/room/status/",
                data: JSON.stringify(roomData),
                dataType: "json",
                contentType: "application/json"
            });
        }
        setTimeout(function () {
            if(curPlayers >= maxPlayers) {
              getDealer(true);
              if(curPlayers >= maxPlayers) {
                $.ajax({
                   url : '/api/v1/blackjack/dealerHit',
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
            }
        }, 1000);
    }

}

function reset(){

    $.ajax({
                url : '/api/v1/blackjack/reset/' + room_global,
                type : 'GET',
                dataType : 'json'
        });
        clear_busted_players();
        document.getElementById("winners").innerHTML = ""
        document.getElementById("dealer").innerHTML = ""
        document.getElementById("player").innerHTML = ""
        console.log("reset.players",player_global);
        //callDealerApi();
        setTimeout(function () {
            getDealer(false);
            curPlayers = 0;
            console.log("reset.curPlayers:",curPlayers);
            console.log("reset.player_global:",player_global);
            console.log("reset.player_global[curPlayers]:",player_global[curPlayers]);
            getPlayersInRoom(room_global);
            //showCards(room_global, player_global[curPlayers], false);
        }, 1000);


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