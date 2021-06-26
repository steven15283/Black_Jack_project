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
        url : '/api/v1/room/currentPlayer/' + room_global,
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            curPlayers = data.currentPlayer;
            roundEnded = data.roundEnded;
            if(data.gameStarted && firstTimeDealer) {
                firstTimeDealer = false;
            }
            $.ajax({
                url : '/api/v1/player/players/room/' + room_global,
                type : 'GET',
                dataType : 'json',
                success : function(data) {
                    player_global = data
                    maxPlayers = data.length
                    showPlayers(data)

                    if(curPlayers < maxPlayers && curPlayers != -1 && !firstTimeDealer) {
                        playersTurn();
                    } else if(curPlayers >= maxPlayers) {
                        dealersTurn();
                    }
                }
            });

            if(roundEnded){
                firstTimeDealer = true;
                var roomData = {
                     "id" : room_global,
                     "roundEnded" : false,
                     "gameStarted" : true,
                     "currentPlayer" : 0
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

}, 500);

function dealersTurn() {
    dealerHit()
    showCards(room_global, player_global[maxPlayers-1]);

    document.getElementById("startButton").style.display="none";
    document.getElementById("gameButtons").style.display="none";
    document.getElementById("playAgain").style.display="block";
}

function playersTurn() {
    document.getElementById("winners").innerHTML = ""
    document.getElementById("startButton").style.display="none";
    document.getElementById("playAgain").style.display="none";
    document.getElementById("gameButtons").style.display="block";
    getDealer(false);
    showCards(room_global, player_global[curPlayers]);
    if(player_global[curPlayers].hand_value == 0) {
        standEvent();
    }
}

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
         "gameStarted" : true,
         "currentPlayer" : 0
    }
    $.ajax({
        type: 'POST',
        url: "/api/v1/room/status/",
        data: JSON.stringify(roomData),
        dataType: "json",
        contentType: "application/json"
    });
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
                  setTimeout(function () {
                      standEvent()
                  }, 1000);
               }
           }
        });
    }
}

function dealerHit() {
    if(curPlayers >= maxPlayers) {
        getDealer(true);
        if(curPlayers >= maxPlayers) {
            $.ajax({
               url : '/api/v1/blackjack/dealerHit/' + room_global,
               type : 'POST',
               success : function(data) {
                   getDealer(true);
                   getWinners(data)
               }
            });
        }
    }
}

function standEvent(){
    if(curPlayers < maxPlayers && curPlayers != -1)
    {
        if(user_global == player_global[curPlayers].username)
        {
            var roomData = {
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
    }

}

function reset(){
    $.ajax({
        url : '/api/v1/blackjack/reset/' + room_global,
        type : 'GET',
        dataType : 'json'
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