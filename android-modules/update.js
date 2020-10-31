var AWS = require("aws-sdk");
let awsConfig = {
    "region": "us-east-2",
    "endpoint": "http://dynamodb.us-east-2.amazonaws.com",
    "accessKeyId": "AKIAZ4AC4ZEBLZWBM5EL", "secretAccessKey": "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"
};
AWS.config.update(awsConfig);

let docClient = new AWS.DynamoDB.DocumentClient();

let modify = id => {
    var params = {
        TableName: "kw_test",
        Key: { "id": id },
        UpdateExpression: "set last_updated = :timestamp",
        ExpressionAttributeValues: {
            ":timestamp": new Date().toString(),
        },
        ReturnValues: "UPDATED_NEW"

    };
    docClient.update(params, function (err, data) {

        if (err) {
            console.log("users::update::error - " + JSON.stringify(err, null, 2));
        } else {
            console.log("users::update::success " + JSON.stringify(data));
        }
    });
}

/* -- testing -- */

/*
let id = 30;
modify(id);
*/