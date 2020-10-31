const AWS = require('aws-sdk');
AWS.config.update({region: 'us-east-2'});

const doc = require('dynamodb-doc');
const dynamo = new doc.DynamoDB();

const SerialPort = require('serialport');
const Readline = SerialPort.parsers.Readline;
var port = new SerialPort('COM3', {baudRate: 9600});
const parser = new Readline();
port.pipe(parser);

const logIntervalMinutes = 5;
let lastMoment = new Date();

function tryParseJson (str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return JSON.parse(str);
}

port.on('open', function () {
	console.log('Opened port...');
  parser.on('data', function(data) {
		const sensorData = tryParseJson(data);
		const moment = new Date();

		if (moment.getTime() - lastMoment.getTime() > logIntervalMinutes * 60 * 1000) {
			lastMoment = moment;

			dynamo.putItem({
				TableName: 'sensor-test',
				Item: {
					temperature: sensorData.temperature,
					humidity: sensorData.humidity,
          water_level: sensorData.water_level,
					datetime: moment.getTime()
				}
			}, function(err, data) {
				if (err) {
					console.log(err);
				} else {
					console.log(JSON.stringify(data, null, '  '));
				}
			});
		}
	});
});
