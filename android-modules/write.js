// RFC4122 version 4 compliant solution from https://www.ietf.org/rfc/rfc4122.txt
let getUUIDv4 = () => {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
}

var AWS = require("aws-sdk");

let awsConfig = {
    "region": "us-east-2",
    "endpoint": "http://dynamodb.us-east-2.amazonaws.com",
    "accessKeyId": "AKIAZ4AC4ZEBLZWBM5EL", "secretAccessKey": "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"
};

AWS.config.update(awsConfig);

let docClient = new AWS.DynamoDB.DocumentClient();

let save = (id, f_name, l_name) => {

    var input = {
        "id": id, "name": `{"first" : ${f_name}, "last": ${l_name}}`, "joined": new Date().toString(), "last_updated": new Date().toString()//, "is_deleted": false
    };
    var params = {
        TableName: "kw_test",
        Item: input
    };
    docClient.put(params, function (err, data) {

        if (err) {
            console.log("users::save::error - " + JSON.stringify(err, null, 2));                      
        } else {
            console.log("users::save::success");                      
        }
    });
}

/* -- testing -- */

/*
let id = 30;
let f_name = "Tahani";
let l_name = "Al-Jamil";

save(id, f_name, l_name);
*/

let id = getUUIDv4();
let f_name = "Chidi";
let l_name = "Anagonye";

save(id, f_name, l_name);