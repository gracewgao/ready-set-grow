var AWS = require("aws-sdk");

let awsConfig = {
    "region": "us-east-2",
    "endpoint": "http://dynamodb.us-east-2.amazonaws.com",
    "accessKeyId": "AKIAZ4AC4ZEBLZWBM5EL",
    "secretAccessKey": "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"
};

AWS.config.update(awsConfig);

let docClient = new AWS.DynamoDB.DocumentClient();

let add_plant = (id, nickname, latin_name) => {
    var params = {
        TableName: "user_info",
        Key: {
            "id": id
        },
        UpdateExpression: "set plants = list_append(plants, :p), last_updated = :timestamp",
        ExpressionAttributeValues: {
            ":p": [`${nickname}: {
                "latin_name": ${latin_name},
                "category": "Misc.",
                "water": 0.0,
                "light": 0.0,
                "humidity": 0.0,
                "last_synced": ${new Date().toString()},
                "streak": 0,
                "last_watered": 0,
                "date_acquired": ${new Date().toString()}
            }`],
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

let nickname = "Planty";
let id = "3ebbb6da-979b-4ea8-ad08-b24a56f7a0ee";
add_plant(id);
/**/