const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// send notification
exports.sendNotification = functions.database.ref('/Chatrooms/{chatroomId}').onWrite((change, context) => {
    if (!change.before.exists() || change.before.val().lastMessage == '') {
        console.log("newest");
        return null;
    }
    if (!change.after.exists()) {
        console.log("data is deleted");
        return null;
    }

    const valueObj = change.after.val();
    console.log("Huong", valueObj);
    console.log("Huong2", change.before.val());
    const message = {
        notification: {
            body:valueObj.lastMessage,
            title:"New message"
        },
        data: {
            "senderId":valueObj.senderId
        },
        topic: 'pushNotifications'
    };
    return admin.messaging().send(message).then(res => {
        console.log("successfully sent message: ", res);
    }).catch(err => {
        console.log("error sending msg: ", err);
    });
});

