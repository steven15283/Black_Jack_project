var maxPlayers = 0;
var curPlayers = -1;
var player_global = [];
var room_global = "";
var user_global = "";
var endpoint = ".com/";
var firstTimeDealer = true;
var roundEnded = false;

window.onload = function() {
    var url = window.location.href
    user_global = url.substring(url.search("user/") + 5)
    for(var i=0; i < 4; i++) {
        room_global += url[url.search(endpoint) + 5 + i]
    }

    document.getElementById("room").innerHTML = room_global
}

var checkIfGameStart = setInterval(function(){
    $.ajax({
        // Build environment
        // url : 'http:localhost:8080/api/v1/players/',
        url : '/api/v1/room/currentPlayer/' + room_global,
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            curPlayers = data.currentPlayer;
            roundEnded = data.roundEnded;

            $.ajax({
                url : '/api/v1/player/players/room/' + room_global,
                type : 'GET',
                dataType : 'json',
                success : function(data) {
                    player_global = data
                    maxPlayers = data.length
                    showPlayers(data)
//    console.log(curPlayers)
//    console.log(maxPlayers)
                    if(curPlayers < maxPlayers && curPlayers != -1 && !firstTimeDealer) {
//                        setTimeout(function () {
                            showCards(room_global, player_global[curPlayers], false);
                            if(player_global[curPlayers].hand_value == 0) {
                                standEvent();
                            }
//                        }, 1000);
                    }

                    if(curPlayers >= maxPlayers) {//infinite loop here
//                    console.log("here")
                        dealerHit()
                        setTimeout(function () {
                            var roomData = {
                                "id" : room_global,
                                "currentPlayer" : -1
                            }
                            $.ajax({
                                type: 'POST',
                                url: "/api/v1/room/status/",
                                data: JSON.stringify(roomData),
                                dataType: "json",
                                contentType: "application/json"
                            });
                        }, 1000);

                        document.getElementById("gameButtons").style.display="none";
                        document.getElementById("playAgain").style.display="block";
                    }
                }
            });

            if(roundEnded){
                firstTimeDealer = true;
//                console.log("roundEnded")
                var roomData = {
                     "id" : room_global,
                     "roundEnded" : false,
                     "gameStarted" : true
                }
                $.ajax({
                    type: 'POST',
                    url: "/api/v1/room/status/",
                    data: JSON.stringify(roomData),
                    dataType: "json",
                    contentType: "application/json"
                });
            }
        }
    });

    $.ajax({
        // Build environment
        // url : 'http:localhost:8080/api/v1/players/',
        url : '/api/v1/room/status/' + room_global,
        type : 'GET',
        dataType : 'json',
        success : function(data) {
//            console.log("Room Status, ", data)
            if(data) {
                document.getElementById("startButton").style.display="none";
                document.getElementById("playAgain").style.display="none";
                document.getElementById("gameButtons").style.display="block";

                if(firstTimeDealer) {
                    setTimeout(function () {
                        getDealer(false);
                    }, 1000);
                    firstTimeDealer = false;
                }
            }
        }
    });
}, 1000);

function getDealer(dealersTurn) {
    $.ajax({
            url : '/api/v1/blackjack/getDealer/' + room_global,
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                showDealerCards(dealersTurn, data);
            }
    });
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

function callDealerApi() {
    $.ajax({
        url : '/api/v1/blackjack/getDealer/'+ room_global,
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

    callDealerApi();
}

function getPlayersInRoom(room){
     $.ajax({
            url : '/api/v1/player/players/room/' + room,
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                playerTurn(data);
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
                      }, 1000);
                   } else {
                      showCards(room_global, player_global[curPlayers], false);
                   }
               }
        });
    }
}

function dealerHit() {
//    setTimeout(function () {
        if(curPlayers >= maxPlayers) {
          getDealer(true);
          if(curPlayers >= maxPlayers) {
            $.ajax({
               url : '/api/v1/blackjack/dealerHit/' + room_global,
               type : 'POST',
               success : function(data) {
                   getDealer(true);
                   for(var i = 0; i < data.length; i++) {
                     if(data[i] < maxPlayers) {
//                         console.log("Winners are " + player_global[data[i]].username)
                     }
                     else {
//                         console.log("Winners are Dealer")
                     }
                   }
                   getWinners(data)
               }
            });
          }
        }
//    }, 1000);
}

function standEvent(){
    if(curPlayers < maxPlayers && curPlayers != -1)
    {
        if(user_global == player_global[curPlayers].username)
        {
            var roomData =
            {
                 "id" : room_global,
                 "currentPlayer" : curPlayers + 1,
                 "gameStarted" : true
            }
            $.ajax(
            {
                type: 'POST',
                url: "/api/v1/room/status/",
                data: JSON.stringify(roomData),
                dataType: "json",
                contentType: "application/json"
            });
        }
//        dealerHit();
    }

}

function reset(){

    $.ajax({
        url : '/api/v1/blackjack/reset/' + room_global,
        type : 'GET',
        dataType : 'json'
    });
    document.getElementById("winners").innerHTML = ""
    document.getElementById("dealer").innerHTML = ""
    document.getElementById("player").innerHTML = ""
    document.getElementById("gameButtons").style.display="block";

    var roomData =
    {
         "id" : room_global,
         "currentPlayer" : -1,
         "roundEnded" : true,
         "gameStarted" : true
    }
    $.ajax(
    {
        type: 'POST',
        url: "/api/v1/room/status/",
        data: JSON.stringify(roomData),
        dataType: "json",
        contentType: "application/json"
    });
}
//function bj(player){
//       var data = {
//                    "username" : player.getUsername()
//          }
//          $.ajax({
//                  url : '/api/v1/blackjack/bj',
//                  type : 'POST',
//                  data: JSON.stringify(data),
//                  dataType : 'json',
//                  success : function(data) {
//                      console.log(data)
//                  }
//              });
//}

//function dealer_bj_check(){
//          $.ajax({
//                  url : '/api/v1/blackjack/dealerBJCheck' + room_global,
//                  type : 'GET',
//                  data: JSON.stringify(data),
//                  dataType : 'json',
//                  success : function(data) {
//                      console.log(data)
//                      if(data){
//                        //dealer wins
//                      }
//                  }
//              });
//}