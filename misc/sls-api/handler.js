'use strict';
const AWS = require('aws-sdk');
const db = new AWS.DynamoDB.DocumentClient({ apiVersion: '2012-08-10' });
const postsTable = process.env.POSTS_TABLE;

function uuidv4() { // unused
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

function sortByUsername(a, b) {
  if (a.username > b.username) {
    return -1;
  }
  return 1;
}

// create a response
function response(statusCode, message) {
  return {
    statusCode: statusCode,
    body: JSON.stringify(message)
  };
}

/**********/

// create a post
module.exports.createPost = (event, context, callback) => {
  const reqBody = JSON.parse(event.body);

  if (
    !reqBody.username ||
    reqBody.username.trim() === '' ||
    !reqBody.password ||
    reqBody.password.trim() === ''
  ) {
    return callback(
      null,
      response(400, {
        error: 'Post must have a non-empty username and password.'
      })
    );
  }

  const post = {
    // id: uuidv4(),
    // createdAt: new Date().toISOString(),
    // userId: 1,
    username: reqBody.username,
    password: reqBody.password,
    plant: {}
  };

  return db
    .put({
      TableName: postsTable,
      Item: post
    })
    .promise()
    .then(() => {
      callback(null, response(201, post));
    })
    .catch((err) => response(null, response(err.statusCode, err)));
};

// get all posts
module.exports.getAllPosts = (event, context, callback) => {
  return db
    .scan({
      TableName: postsTable
    })
    .promise()
    .then((res) => {
      callback(null, response(200, res.Items.sort(sortByUsername)));
    })
    .catch((err) => callback(null, response(err.statusCode, err)));
};


// get a single post
module.exports.getPost = (event, context, callback) => {
  const username = event.pathParameters.username;

  const params = {
    Key: {
      username: username
    },
    TableName: postsTable
  };

  return db
    .get(params)
    .promise()
    .then((res) => {
      if (res.Item) callback(null, response(200, res.Item));
      else callback(null, response(404, { error: 'Post not found' }));
    })
    .catch((err) => callback(null, response(err.statusCode, err)));
};

// update a post
module.exports.updatePost = (event, context, callback) => {
  // const usernameAsId = event.pathParameters.username;
  const reqBody = JSON.parse(event.body);
  const { username, password } = reqBody; // ?

  const params = {
    Key: {
      username: username
    },
    TableName: postsTable,
    ConditionExpression: 'attribute_exists(username)',
    UpdateExpression: 'SET username = :username, password = :password, plant = :plant',
    ExpressionAttributeValues: {
      ':username': username,
      ':password': password,
      ':plant': plant
    },
    ReturnValues: 'ALL_NEW'
  };
  console.log('Updating');

  return db
    .update(params)
    .promise()
    .then((res) => {
      console.log(res);
      callback(null, response(200, res.Attributes));
    })
    .catch((err) => callback(null, response(err.statusCode, err)));
};

// delete a post
module.exports.deletePost = (event, context, callback) => {
  const username = event.pathParameters.username;
  const params = {
    Key: {
      username: username
    },
    TableName: postsTable
  };
  return db
    .delete(params)
    .promise()
    .then(() =>
      callback(null, response(200, { message: 'Post deleted successfully' }))
    )
    .catch((err) => callback(null, response(err.statusCode, err)));
};

/*
(serverless deploy)
Service Information
service: sls-api
stage: dev
region: us-east-2
stack: sls-api-dev
resources: 34
api keys:
  None
endpoints:
  POST - https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/user
  GET - https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/users
  GET - https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/user/{username}
  PUT - https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/user/{username}
  DELETE - https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/user/{username}
functions:
  createPost: sls-api-dev-createPost
  getAllPosts: sls-api-dev-getAllPosts
  getPost: sls-api-dev-getPost
  updatePost: sls-api-dev-updatePost
  deletePost: sls-api-dev-deletePost
layers:
  None
*/