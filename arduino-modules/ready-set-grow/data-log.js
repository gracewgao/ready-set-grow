// AWS variables
const AWS = require("aws-sdk");
AWS.config.update({ region: "us-east-2" });
const doc = require("dynamodb-doc");
const dynamo = new doc.DynamoDB();

// SerialPort variables
const SerialPort = require("serialport");
const Readline = SerialPort.parsers.Readline;
var port = new SerialPort("COM3", { baudRate: 115200 });
const parser = new Readline();
port.pipe(parser);

const logIntervalMinutes = 30;
var ready = false;

// parses JSON from SerialPort to object
function tryParseJson(str) {
  try {
    JSON.parse(str);
  } catch (e) {
    return false;
  }
  return JSON.parse(str);
}

// when port is opened
port.on("open", function () {
  console.log("Opened port...");
  ready = true;
});

// helper funciton that sends command to SerialPort
function sendCommand(command) {
  if (!ready) {
    return 0;
  }
  port.write(command.toString());
  parser.on("data", function (data) {
    const returnData = tryParseJson(data);
    console.log(returnData.return_value);
    return returnData;
  });
}

// opens water valve
function waterPlant(open) {
  command = "/openValve?params=" + open;
  console.log(open ? "Opening water valve...");
  sendCommand(command);
}

// sends sensor data to DynamoDB
function sendData() {
  port.write("\n");
  parser.on("data", function (data) {
	const arudinoData = tryParseJson(data);
    const sensorData = arduinoData.variables;
    dynamo.putItem(
      {
        TableName: "sensor-data",
        Item: {
          temperature: sensorData.temperature,
          humidity: sensorData.humidity,
		  water_level: sensorData.water_level,
		  light: sensorData.light,
          datetime: moment.getTime(),
        },
      },
      function (err, data) {
        if (err) {
          console.log(err);
        } else {
          console.log(JSON.stringify(data, null, "  "));
        }
      }
    );
  });
}

// uploads data to DynamoDB every 30 minutes
setInterval(sendData, logIntervalMinutes * 60 * 1000);
