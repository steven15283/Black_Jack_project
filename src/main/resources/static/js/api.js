var dealer_global;
var maxPlayers = 0;
var curPlayers = 0;
var player_global = [];

function getCurrentPlayer() {
    return player_global[curPlayers].username;
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

function callDealerApi() {
    $.ajax({
        url : 'api/v1/blackjack/getDealer',
        type : 'GET',
        dataType : 'json',
        success : function(data) {
            setDealer(data)
            getPlayersInRoom()

        }
    });


}

function setDealer(dealer) {
    dealer_global = dealer;
}

function getDealer() {
    return dealer_global;
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
            success: function(resultData) { console.log("Save Complete") }
      });
}

function playerTurn(players)
{
    player_global = players
    maxPlayers = players.length
    console.log("current player:", player_global[curPlayers].username)
    var x =0;
    dealer_bj_check(dealer_global)
    console.log(players[curPlayers])
    showCards(players[curPlayers].username);
}



function getPlayersInRoom(){
     $.ajax({
            url : 'api/v1/player/players/room/1',
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                playerTurn(data)
            }
    });
}

function hit(){
        console.log(player_global[curPlayers].username)
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
               console.log("hit function:", player_global[curPlayers].username)
               showCards(getCurrentPlayer());

               }

           });
       isBust()

}

function standEvent(){

       curPlayers = curPlayers + 1;

        if(curPlayers < maxPlayers) {
            showCards(getCurrentPlayer());
        }
        if(curPlayers == maxPlayers){
            dealer_reach_limit(dealer_global)
            for(var i = 0; i< maxPlayers;i++){
                checkHand(player_global[curPlayers].username)
            }
              dealer_reach_limit()
        }
       standFlag = true;

}

function isBust(){
       console.log("isbustcheck: ", player_global[curPlayers].username)
       var data = {
               "username" : player_global[curPlayers].username
       }
          $.ajax({
                  url : 'api/v1/blackjack/isBust',
                  type : 'POST',
                  data: JSON.stringify(data),
                  contentType: "application/json",
                  dataType : 'json',
                  success : function(data) {
                      console.log("player bust check-functin success")
                      console.log(data)
                      if(data)
                      {
                        console.log("player bust")
                        curPlayers += 1
                      }
                 }
          });
}

function bj(player){
       var data = {
                    "username" : player.getUsername()
          }
          $.ajax({
                  url : 'api/v1/blackjack/bj',
                  type : 'POST',
                  data: JSON.stringify(data),
                  dataType : 'json',
                  success : function(data) {
                      console.log(data)
                  }
              });
}

function dealerWon(players){
       players = $.ajax({
               url : 'api/v1/blackjack/dealerWon',
               type : 'GET',
               dataType : 'json',
               success : function(data) {
                   console.log(data)
               }
           });
}

function dealer_reach_limit(){
      var data = {
                    dealer_global
           }
           $.ajax({
              url : 'api/v1/blackjack/dealerRL',
              type : 'POST',
              data: JSON.stringify(data),
              dataType : 'json',
              success : function(data) {
                  console.log(data)
              }
          });
}

function checkHand(player){
       players = $.ajax({
               url : 'api/v1/blackjack/checkHand',
               type : 'GET',
               dataType : 'json',
               success : function(data) {
                   console.log(data)
               }
           });
}

function dealer_bj_check(){
        var data = {
                       dealer_global
                   }
          $.ajax({
                  url : 'api/v1/blackjack/dealerBJCheck',
                  type : 'POST',
                  data: JSON.stringify(data),
                  dataType : 'json',
                  success : function(data) {
                      console.log(data)
                  }
              });
}



