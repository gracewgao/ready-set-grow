"use strict";

var five = require("johnny-five"),
  board = new five.Board(),
  servos = null,
  servoPins = [9, 10],
  // servoPins = [9], // if you have only one servo
  express = require('express'),
  app = express(),
  port = 8000;

board.on("ready", function() {
  console.log("### Board ready!");
  // Initialize a Servo collection
  console.log("### initializing servo array: ", servoPins );
  servos = new five.Servos(servoPins);
  console.log("### centering servos in array");
  servos.center();
});

app.get('/servos/:mode/:value?', function (req, res) {
  if(servos) {
    var status = "OK";
    var value = req.params.value; // optional, may be null
    switch(req.params.mode) {
      case "min": // 0 degrees
        servos.min();
        break;
      case "max":
        servos.max();
        break;
      case "stop": // use after sweep
        servos.stop();
        break;
      case "sweep":
        servos.sweep();
        break;
      case "to":
        if(value !== null)


        break;
      default:
        status = "Unknown: " + req.params.mode;
        break;
     }
     console.log(status);
     res.send(status);
   } else {
     res.send('Board NOT ready!')
   }
});

app.listen(port, function () {
 console.log('Listening on port ' + port);
});
