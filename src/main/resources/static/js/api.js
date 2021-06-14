var hitFlag = false;
var standFlag = false;
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
    var x =0;
    console.log(players[curPlayers])
    showCards(players[curPlayers].username);

//
////    while(x < players.length)
////    {
////        await sleep(2000);
//        if(hitFlag == true)
//        {
//            console.log("hitflag")
//            hit(players[x])
////            if(isBust(players[x]))
////            {
////                console.log("player busts")
////                break;
////            }
//            hitFlag = false
//        }
//
//        if(standFlag == true){
//            x = x + 1
//            standFlag = false;
//            console.log("next player")
//        }
//    }
//    var dealer_test = getDealer()
//    console.log(dealer_test)
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


//    var dealer = getDealer();
//    console.log(dealer);
//        for (var i =0; i < players.length; i++)
//        {
//            console.log(players[i]);
//            do
//            {
//                if(hitFlag){
//                    console.log("hitflag");
//                    hit(players[i])
//                    if(isBust(players[i]))
//                    {
//                        console.log("player busts");
//                        break;
//                    }
//                    hitFlag = false;
//                }
//            }while(standFlag == false);
//            standFlag = false;
//            console.log("next player");
//        }
        /*
        dealer_reach_limit(dealer);

        for (player : players)
                {

                    do
                    {
                        if(player.){
                            hit(player)
                            if(isBust(player))
                            {
                                break;
                            }
                            hitFlag = false;
                        }
                    }while(standFlag == false);
                    standFlag = false;
                    console.log("next player");
                }
        */
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
                   showCards(getCurrentPlayer());
               }
           });
}

function standEvent(){
        curPlayers = curPlayers + 1;
        if(curPlayers < maxPlayers) {
            showCards(getCurrentPlayer());
        }
       standFlag = true;
}

function isBust(player){
       var data = {
               "username" : player_global[curPlayers].username
          }
          $.ajax({
                  url : 'api/v1/blackjack/isBust',
                  type : 'POST',
                  data: JSON.stringify(data),
                  dataType : 'json',
                  success : function(data) {
                      console.log(data)
                  }
              });
}

function stand(player){

       var data = {
                    "username" : player.getUsername()
          }
          $.ajax({
                  url : 'api/v1/blackjack/stand',
                  type : 'POST',
                  data: JSON.stringify(data),
                  dataType : 'json',
                  success : function(data) {
                      console.log(data)
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

function dealer_reach_limit(dealer){
      $.ajax({
              url : 'api/v1/blackjack/dealerRL',
              type : 'POST',
              data: JSON.stringify(dealer),
              dataType : 'json',
              success : function(dealer) {
                  console.log(dealer)
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

function dealer_bj_check(dealer){
          $.ajax({
                  url : 'api/v1/blackjack/dealerBJCheck',
                  type : 'POST',
                  data: JSON.stringify(dealer),
                  dataType : 'json',
                  success : function(dealer) {
                      console.log(dealer)
                  }
              });
}

//function sleep(ms) {
//  return new Promise(resolve => setTimeout(resolve, ms));
//}
//
//async function demo() {
//  console.log('Taking a break...');
//  await sleep(20000);
//  console.log('20 seconds later, showing sleep in a loop...');

