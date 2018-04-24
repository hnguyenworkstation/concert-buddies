// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/Chatrooms/{chatroomID}').onWrite((change, context) => {

    if (!change.before.exists()) {
        console.log("newest data");
        return null;
    }
    // get the message that was written
    let message = change.after.child("lastMessage").val();
    let chatroomID = context.params.chatroomID;

    console.log('mesesage:', message);
    console.log('chatroomId:', chatroomID);

    return change.after.ref.once('value').then(snap => {
        let data = snap.child('users').val();
        console.log("data:", data);
        // loop through each user in the chatroom
        let tokens = [];
        let i = 0;
        for (var user_id in data) {
            console.log("user_id:", user_id);
            tokens.push(user_id);
            i++;
        }

        if (i == 2) {
            console.log("Constructing the notification message");
            const payload = {
                data : {
                    title: "New message",
                    body: message,
                    chatroomID: chatroomID,
                    senderId: context.auth.uid
                }
            }

            return admin.messaging().sendToDevice(tokens, payload).then((res) => {
                console.log("Successfully sent message: ", res);
            })
            .catch((e) => {
                console.log("Error sending message:", e);
            });
        }
    });
});


// send notification
// exports.sendNotification = functions.database.ref('/Chatrooms/{chatroomId}/lastMessage').onWrite((change, context) => {
//     console.log("getting notification");
//     if (!change.before.exists() || change.before.val().lastMessage == '') {
//         console.log("newest");
//         return null;
//     }
//     if (!change.after.exists()) {
//         console.log("data is deleted");
//         return null;
//     }

//     const valueObj = change.after.val();
//     console.log("Huong", valueObj);
//     console.log("Huong2", change.before.val());
//     const message = {
//         data: {
//             body:valueObj,
//             title:"New message",
//             "senderId":context.auth.uid
//         },
//         topic: 'pushNotifications'
//     };
//     return admin.messaging().send(message).then(res => {
//         console.log("successfully sent message: ", res);
//     }).catch(err => {
//         console.log("error sending msg: ", err);
//     });
// });
